package omar.upload
import java.text.SimpleDateFormat

class UploadController {
  
  def imageUploadPath = grailsApplication.config.upload.imageUploadPath
  def baseUrl = grailsApplication.config.upload.baseUrl
  def suffix = grailsApplication.config.upload.suffix
  def maxFileSize = grailsApplication.config.grails.controllers.upload.maxFileSize
  def phrase = "Hello"
  def index() { }

  def emptyErrorMessage = "File cannot be empty"
  def badTypeMessage = "File is of an incorrect type"
  def fileSizeExceededMessage = "File limit of ${maxFileSize} bytes exceeded"

  def validFileType(def name) {
    return name.endsWith('.tif')
  }

  def validSize(def size, def maxSize) {
    return size <= maxSize
  }

  def getImageValidation(def file) {
    if (!file || file.empty)
      return emptyErrorMessage
    if (!validFileType(file.filename))
      return badTypeMessage
    if (!validSize(file.size, maxFileSize))
      return fileSizeExceededMessage

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
    def url = baseUrl + path + suffix

    def post = new URL(url).openConnection();
    def postRC = post.getResponseCode();
    println(postRC);
    if (postRC.equals(200)) {
        println(post.getInputStream().getText());
    }
  }

  def uploadImage() {
    def file = request.getFile('uploadedFile')
    String path = makeImageDirectories()
    def validationString = getImageValidation(file)
    try {
      if(validationString == 'valid') {
          println "*"*80
          println "Uploaded: ${path}/${file.filename}"

          File fileDest = new File("${path}/${file.filename}")
          file.transferTo(fileDest)
          flash.message="your.sucessful.file.upload.message"
          stageImage("${path}/${file.filename}")
          render(view:'uploadImage')
      } else {
          println "*"*80
          println validationString
          flash.message="your.unsucessful.file.upload.message"
          render(view:'index')
      }
    }
      catch(Exception e){
          log.error("Your exception message goes here",e)   
      }
  }
}
