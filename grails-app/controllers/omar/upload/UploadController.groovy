package omar.upload

import org.apache.tika.Tika
import org.apache.tika.exception.TikaException
import java.text.SimpleDateFormat
import org.springframework.beans.factory.annotation.Value
import groovy.json.*
import grails.web.JSONBuilder
import grails.converters.JSON

class UploadController {

  @Value('${upload.path:/Users/benbuzzelli/Desktop/omar-upload-imagery}')
  def imageUploadPath

  @Value('${upload.base.url:http://localhost:8080}')
  def baseUrl

  @Value('${upload.wfs.base.url:http://localhost:8080}')
  def wfsBaseUrl

  @Value('${upload.stager.url:http}')
  def stagerUrl

  @Value('${upload.suffix:suffix}')
  def stagerSuffix

  @Value('${upload.wfs.query:%27%20AND%20entry_id%3D0&outputFormat=JSON&request=GetFeature&service=WFS&typeName=omar:raster_entry&version=1.1.0}')
  def wfsSuffix

  @Value('${upload.wfs.url:/omar-wfs/wfs?filter=filename%3D%27}')
  def wfsUrl

  @Value('${upload.extensions:nitf,ntf,tiff,tif}')
  def extensionsString

  def maxFileSize = grailsApplication.config.grails.controllers.upload.maxFileSize

  def emptyErrorMessage = "File cannot be empty"
  def badTypeMessage = "File is of an incorrect type"
  def fileSizeExceededMessage = "File limit of ${maxFileSize} bytes exceeded"
  def fileAlreadyExists = "File already exists"

  def filename = ""
  def path = ""

  class Image {
    def filename
    def path
    def size

    def getSizeString(size) {
      switch (true) {
        case size < 1000:
          return "${size} B";
        case (size < 1000000):
          return "${(size / 1000).round(1)} KB";
        case size < 1000000000:
          return "${(size / 1000000).round(1)} MB";
        case size < 1000000000000:
          return "${(size / 1000000000).round(1)} GB";
        default:
          return "${size}";
      }
    }

    Image(def filename, def path, def size) {
      this.filename = filename
      this.path = path
      this.size = getSizeString(size)
    }
  }

  def getSizeString(size) {
    switch (true) {
      case size < 1000:
        return "${size} B";
      case (size < 1000000):
        return "${(size / 1000).round(1)} KB";
      case size < 1000000000:
        return "${(size / 1000000).round(1)} MB";
      case size < 1000000000000:
        return "${(size / 1000000000).round(1)} GB";
      default:
        return "${size}";
    }
  }

  def index() {
    render(view:'index', model: [maxFileSize: maxFileSize, sizeLimitString: getSizeString(maxFileSize), extensions: extensionsString.split(',').join(' | ')])
    println("Upload path: " + imageUploadPath)
  }

  def uploadImage() {
    println("Trying upload:")
    def file = request.getFile('file')

    // def file = request.getFile('uploadedFile')
    String path = getPath()
    def validationString = getImageValidation(file, "${path}/${file.filename}")
    try {
      if(validationString == 'valid') {
          println "*"*80
          println "Uploaded: ${path}/${file.filename}"

          makeImageDirectories(path)

          File fileDest = new File("${path}/${file.filename}")
          file.transferTo(fileDest)

          this.filename = file.filename
          this.path = path

          def image = new Image(file.filename, path, file.size)
          render(view:'uploadImage', model: [image: image, baseUrl: baseUrl, filename: image.filename, path: image.path, wfsSuffix: wfsSuffix, wfsUrl: wfsUrl,  message: null])
      } else {
          println "!"*80
          println validationString

          this.filename = file.filename
          this.path = path

          render(view:'uploadImage', model: [image: null, message: validationString, baseUrl: baseUrl, filename: file.filename, path: path, wfsSuffix: wfsSuffix, wfsUrl: wfsUrl])
      }
    } catch(Exception e){
      log.error("File upload failed",e)
      this.filename = file.filename
      this.path = path
      render(view:'uploadImage', model: [image: null, message: e.getClass().getCanonicalName(), baseUrl: baseUrl, filename: file.filename, path: path, wfsSuffix: wfsSuffix, wfsUrl: wfsUrl])
    }
  }

  def validFileType(def file) {
    def type = new Tika().detect( file )
    def extensions = extensionsString.split(",")

    for (ext in extensions) 
      if (type.contains(ext)) return "valid";
    return "File type, ${type}, is not allowed";
  }

  def validSize(def size, def maxSize) {
    if (size > maxSize)
      return "File size of ${getSizeString(size)} exceeds the limit of ${getSizeString(maxSize)}"
    
    return "valid"
  }

  def getImageValidation(def file, def filepath) {
    if (!file || file.empty)
      return emptyErrorMessage

    if (new File(filepath).exists())
      return fileAlreadyExists
    
    def message = validFileType(file.getBytes())
    if (message != "valid")
      return message

    message = validSize(file.size, maxFileSize)
    if (message != "valid")
      return message

    return 'valid'
  }

  String getPath() {
    def date = new Date()
    def sdf = new SimpleDateFormat("yyyy-MM-dd")
    println("UPLOAD PATH: " + imageUploadPath)
    return "${imageUploadPath}/${sdf.format(date)}"
  }

  def makeImageDirectories(def dirPath) {
    File directory = new File(dirPath)
    if (!directory.exists()) {
      directory.mkdirs()
    }
  }

  def stageImage() {
    def path = request.JSON.path
    def url = baseUrl + stagerUrl + path + stagerSuffix

    println(url);
    try {
      def post = new URL(url).openConnection();
      post.setRequestMethod("POST");
      def postRC = post.getResponseCode();
      println(postRC);
      if (postRC.equals(200)) {
        def res = post.getInputStream().getText();
        println("RESPONSE: " + res)
        render(contentType: "text/json", text: res)
      } else {
        def res = post.getInputStream().getText()
        render(contentType: "text/json", text: res)
      } 
    } catch(Exception e){
        log.error("File staging failed",e)
        render(status: 500, contentType: "text/json", text: e.getClass().getCanonicalName())  
    }
  }

  def checkStagingStatus() {
    def obj = request.JSON
    def url = wfsBaseUrl + wfsUrl + obj.name + wfsSuffix

    def get = new URL(url).openConnection()
    get.setRequestMethod("GET")
    println(url)
    def getRC = get.getResponseCode()
    println(getRC)
    if (getRC.equals(200)) {
      render(contentType: "text/json", text: get.getInputStream().getText())
    } else {
      render(contentType: "text/json", text: get.getInputStream().getText())
    }
  }
}
