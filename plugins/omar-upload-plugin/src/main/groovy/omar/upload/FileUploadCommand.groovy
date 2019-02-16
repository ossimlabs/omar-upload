package omar.upload

import grails.validation.Validateable
import groovy.transform.ToString

/**
 * Created by nroberts on 7/8/16.
 */
@ToString(includeNames = true)
class FileUploadCommand implements Validateable
{
    String type = "Upload"
    def uploadedFile

    // Used by Validateable interface
    static constraints = {
        uploadedFile(nullable:false)
    }

    FileUploadCommand(uploadedFile) {
        this.uploadedFile = uploadedFile
    }

}
