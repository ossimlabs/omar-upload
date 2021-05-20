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

    def stageImage(def path) {
      def prefix = "https://omar-3pa-dev.ossim.io/omar-stager/dataManager/addRaster?filename="
      def suffix = "&background=true&buildThumbnails=true&buildOverviews=true&buildHistograms=true&buildHistogramsWithR0=true&useFastHistogramStaging=false&overviewType=ossim_tiff_box&overviewCompressionType=NONE&thumbnailSize=512&thumbnailType=jpeg&thumbnailStretchType=auto-minmax"
      def url = prefix + path + suffix

      def post = new URL(url).openConnection();
      def postRC = post.getResponseCode();
      println(postRC);
      if (postRC.equals(200)) {
          println(post.getInputStream().getText());
      }
    }

    def uploadImage() {
      def file = request.getFile('uploadedFile')
      String imageUploadPath = makeImageDirectories()
      
      try{
        if(file && !file.empty && validFileType(file.filename)) {
            println "*"*80
            println "Uploaded: ${file.filename}"

            File fileDest = new File("${imageUploadPath}/${file.filename}")
            file.transferTo(fileDest)
            flash.message="your.sucessful.file.upload.message"
            stageImage("${imageUploadPath}/${file.filename}")
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
