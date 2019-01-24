package omar.upload

// import com.wordnik.swagger.annotations.Api
// import com.wordnik.swagger.annotations.ApiImplicitParam
// import com.wordnik.swagger.annotations.ApiImplicitParams
// import com.wordnik.swagger.annotations.ApiOperation

import io.swagger.annotations.*

import groovy.json.JsonSlurper
import omar.core.BindUtil

import javax.xml.ws.Response
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@Api(
        value = "archive",
        description = "API operations for Upload IO",
        produces = 'application/json',
        consumes = 'multipart/form-data'
)

class ArchiveController {

    static allowedMethods = [
                              upload:["POST"]
                            ]
    def archiveService

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

        archiveService.upload(response, cmd)

        null
    }
}
