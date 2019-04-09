package omar.upload

import grails.converters.JSON
import io.swagger.annotations.*
import omar.core.HttpStatus

@Api(
        value = "upload",
        description = "API operations for Upload IO",
        produces = 'application/json',
        consumes = 'multipart/form-data'
)

class UploadController {

    def uploadService

    @ApiOperation(
            value = "Upload files",
            consumes= 'multipart/form-data',
            produces='application/json',
            httpMethod="POST",
            notes="""
                Upload a file you wish to be staged by omar  
                """)

    @ApiImplicitParams([
        @ApiImplicitParam(
                name = 'uploadedFile',
                value = 'The multipart file',
                dataType = 'java.io.File',
                paramType = 'form',
                required=true
            )
    ])

    def upload() {
        def uploadedFile = params.uploadedFile
        def cmd = new FileUploadCommand(uploadedFile)

        def result = uploadService.upload(response, cmd)

//        response.setContentType("application/json")
//        response.status = result.status
//        String jsonData = "${result as JSON}"
//
//        response.outputStream.write(jsonData.bytes)
//
//        response.outputStream.close()

        if (result.status == HttpStatus.ACCEPTED) {
            redirect(uri:"/success?filename=${cmd.uploadedFile.filename}&location=${result.location}")
        }
    }

    def checkStatus() {

    }
}
