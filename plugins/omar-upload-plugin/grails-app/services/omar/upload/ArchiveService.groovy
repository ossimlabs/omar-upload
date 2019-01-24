package omar.upload

import grails.transaction.Transactional
import omar.core.HttpStatus
import grails.converters.JSON
import java.net.URL
import groovy.json.JsonSlurper

@Transactional
class ArchiveService {

    def grailsApplication
    def upload(def response, FileUploadCommand cmd)
    {
        Integer maxFiles = grailsApplication.config?.omar?.upload?.maxFiles?:10
        String destinationPath = grailsApplication.config?.omar?.upload?.destinationPath?:'./'
        if (!destinationPath?.endsWith("/")) {
            destinationPath = destinationPath + System.getProperty("file.separator")
        }

        HashMap result = [
                status:HttpStatus.OK,
                message:"Uploading Files"
        ]

        if (cmd.validate())
        {
            try
            {
                if ((cmd.type?.toLowerCase() == "upload") || (cmd.type == null))
                {
                    if(cmd.uploadedFile)
                    {
                        File destinationFile = new File(destinationPath + cmd.uploadedFile.filename)
                        println("Placing file at: ${destinationFile.getCanonicalPath()}")
                        // getCanonicalFile is needed for some reason to get relative paths to work
                        cmd.uploadedFile.transferTo(destinationFile.getCanonicalFile())
                        result.message = "File placed at ${destinationFile.getCanonicalPath()}"

                    } else{
                        result.status =  omar.core.HttpStatus.NOT_ACCEPTABLE
                        result.message = "No file attached"
                    }
                }
                else
                {
                    result.status =  omar.core.HttpStatus.NOT_ACCEPTABLE
                    result.message = "Request Type Not Recognized"
                }
            }
            catch (e)
            {
                result.status = omar.core.HttpStatus.BAD_REQUEST
                result.message = e.message
            }
        }
        else {
            def messages = []
            cmd.errors.allErrors.each {
                messages += "${it.field} cannot be ${it.rejectedValue}"
            }
            String message = "Invalid parameters - ${messages.join(', ')}"
            result = [status : omar.core.HttpStatus.BAD_REQUEST,
                      message: message.toString()]
        }


        response.setContentType("application/json")
        response.status = result.status
        String jsonData = "${result as JSON}"

        response.outputStream.write(jsonData.bytes)
        if(result.status != HttpStatus.OK)
        {
            log.error(jsonData)
        }

        response.outputStream.close()

        result
    }
}
