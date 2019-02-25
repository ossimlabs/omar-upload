package omar.upload

import io.swagger.annotations.*

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

        uploadService.upload(response, cmd)
    }
}
