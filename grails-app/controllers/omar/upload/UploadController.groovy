package omar.upload
import java.text.SimpleDateFormat

class UploadController {

    def index() { }

    def validFileType(def name) {
      return name.endsWith('.tif')
    }

    String makeImageDirectories() {
      def date = new Date()
      def sdf = new SimpleDateFormat("yyyy-MM-dd")
      def dirPath = "/Users/benbuzzelli/Desktop/omar-upload-imagery/${sdf.format(date)}"

      File directory = new File(dirPath)
      if (!directory.exists()) {
        directory.mkdirs()
      }
      return dirPath
    }

    def uploadImage() {
      def file = request.getFile('uploadedFile')
      String imageUploadPath = makeImageDirectories()
    //   String imageUploadPath = grailsApplication.config.imageUpload.path
      try{
        if(file && !file.empty && validFileType(file.filename)) {
            println "*"*80
            println "Uploaded: ${file.filename}"

            File fileDest = new File("${imageUploadPath}/${file.filename}")
            file.transferTo(fileDest)
            flash.message="your.sucessful.file.upload.message"
            render(view:'uploadImage')
        } else {
            println "*"*80
            println "Did not upload"
            flash.message="your.unsucessful.file.upload.message"
            render(view:'index')
        }
      }
        catch(Exception e){
            log.error("Your exception message goes here",e)   
        }
    }
}
