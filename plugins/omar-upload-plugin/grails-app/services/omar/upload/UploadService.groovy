package omar.upload

import omar.core.HttpStatus
import grails.converters.JSON

class UploadService {

    def grailsApplication
    def upload(def response, FileUploadCommand cmd)
    {
        Map result = [
                status: HttpStatus.OK,
                message: "Uploading Files"
        ]

        if (cmd.validate())
        {
            try
            {
                if ((cmd.type?.toLowerCase() == "upload") || (cmd.type == null))
                {
                    if(cmd.uploadedFile)
                    {
                        String destinationPath = withTrailingPathSeparator(
                                grailsApplication.config?.omar?.upload?.destinationPath ?: './')

                        File destinationFile = new File(destinationPath + cmd.uploadedFile.filename)
                        log.info("Placing file at: ${destinationFile.getCanonicalPath()}")

                        // getCanonicalFile is needed for some reason to get relative paths to work
                        cmd.uploadedFile.transferTo(destinationFile.getCanonicalFile())
                        result.message = "File placed at ${destinationFile.getCanonicalPath()}"
                        result.location = destinationFile.getCanonicalPath()
                        result.status = omar.core.HttpStatus.ACCEPTED
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

        if(result.status != HttpStatus.OK && result.status != HttpStatus.ACCEPTED)
        {
            log.error(jsonData)
        }

        result
    }

    private static withTrailingPathSeparator(String inputStr){
        String outputStr = inputStr
        if (!inputStr?.endsWith(System.getProperty("file.separator"))) {
            outputStr = inputStr + System.getProperty("file.separator")
        }
        return outputStr
    }
}
