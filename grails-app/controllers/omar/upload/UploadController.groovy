package omar.upload
import java.text.SimpleDateFormat
import org.springframework.beans.factory.annotation.Value
import groovy.json.*

class UploadController {

  @Value('${upload.path:/Users/benbuzzelli/Desktop/omar-upload-imagery}')
  def imageUploadPath

  @Value('${upload.base.url:base}')
  def baseUrl

  @Value('${upload.stager.url:http}')
  def stagerUrl

  @Value('${upload.suffix:suffix}')
  def stagerSuffix

  @Value('${upload.wfs.query:wfs}')
  def wfsSuffix

  @Value('${upload.wfs.url:wfsUrl}')
  def wfsUrl

  def maxFileSize = grailsApplication.config.grails.controllers.upload.maxFileSize

  def emptyErrorMessage = "File cannot be empty"
  def badTypeMessage = "File is of an incorrect type"
  def fileSizeExceededMessage = "File limit of ${maxFileSize} bytes exceeded"
  def fileAlreadyExists = "File already exists"

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

  def index() {
    println("Upload path: " + imageUploadPath)
  }

  def uploadImage() {
    println("upload.test: " + grailsApplication.config.upload.test)
    def file = request.getFile('uploadedFile')
    String path = makeImageDirectories()
    def validationString = getImageValidation(file, "${path}/${file.filename}")
    try {
      if(validationString == 'valid') {
          println "*"*80
          println "Uploaded: ${path}/${file.filename}"

          File fileDest = new File("${path}/${file.filename}")
          file.transferTo(fileDest)
          flash.message="your.sucessful.file.upload.message"

          def image = new Image(file.filename, path, file.size)
          render(view:'uploadImage', model: [image: image, baseUrl: baseUrl, filename: image.filename, path: image.path, wfsSuffix: wfsSuffix, wfsUrl: wfsUrl,  message: null])
          stageImage("${path}/${file.filename}")
      } else {
          println "*"*80
          println validationString
          flash.message="your.unsucessful.file.upload.message"
          render(view:'uploadImage', model: [image: null, message: validationString])
      }
    }
      catch(Exception e){
          log.error("Your exception message goes here",e)   
      }
  }

  def validFileType(def name) {
    return (name.endsWith('.tif') || name.endsWith('.ntf'))
  }

  def validSize(def size, def maxSize) {
    return size <= maxSize
  }

  def getImageValidation(def file, def filepath) {
    if (!file || file.empty)
      return emptyErrorMessage
    if (!validFileType(file.filename))
      return badTypeMessage
    if (!validSize(file.size, maxFileSize))
      return fileSizeExceededMessage
    if (new File(filepath).exists())
      return fileAlreadyExists

    return 'valid'
  }

  String makeImageDirectories() {
    def date = new Date()
    def sdf = new SimpleDateFormat("yyyy-MM-dd")
    def dirPath = "${imageUploadPath}/${sdf.format(date)}"

    File directory = new File(dirPath)
    if (!directory.exists()) {
      directory.mkdirs()
    }
    return dirPath
  }

  def stageImage(def path) {
    def url = baseUrl + stagerUrl + path + stagerSuffix

    def post = new URL(url).openConnection();
    post.setRequestMethod("POST");
    println(url);
    def postRC = post.getResponseCode();
    println(postRC);
    if (postRC.equals(200)) {
        println(post.getInputStream().getText());
    }
  }

  def checkStagingStatus() {
    println("Image:");
  }
}
