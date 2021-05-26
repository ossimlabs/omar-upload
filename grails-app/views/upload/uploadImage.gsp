<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Upload Page</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <asset:javascript library='jquery' src="components/upload.js"/>
    <g:javascript>
        var ajaxAction = "${createLink(controller:'upload',action:'checkStagingStatus')}"
        var uploadedFilename = "${filename}"
        var uploadedPath = "${path}"
        var baseUrl = "${baseUrl}"
        var wfsUrl = "${wfsUrl}"
        var wfsSuffix = "${wfsSuffix}"
    </g:javascript>
</head>
<body>
    <div class="form">
        <g:if test="${this.image}" >
            <i class="large material-icons colorSuccess" style="margin-left: 5px;">mood</i><br><br>
            <div class="info-container">
                <div class="form-title colorSuccess">Image upload success!</div>
                <table>
                    <tr><td class="p1 color2 td-h">Filename:</td><td class="p1 color1" id="filename">${this.image.filename}</td></tr>
                    <tr><td class="p1 color2 td-h">Upload path:</td><td class="p1 color1" id="path">${this.image.path}</td></tr>
                    <tr><td class="p1 color2 td-h">Size:</td><td class="p1 color1">${this.image.size}</td></tr>
                </table>
            </div>
        </g:if>
        <g:if test="${this.message}">
            <i class="large material-icons colorError" style="margin-left: 5px;">mood_bad</i><br><br>
            <div class="info-container">
                <div class="form-title colorError">Image upload failed!</div>
                <table>
                    <tr><td class="p1 color2 td-h">Reason:</td><td class="p1 color1">${this.message}</td></tr>
                </table>
            </div>
        </g:if>
        <br>
        <div class="nav-btn">
            <g:link controller="upload"><button>Return to Uploader</button></g:link>
        </div>

        <%-- <div>
            Check if the image has been staged here:
            <button type="button" onClick="checkStagingStatus()">Check Status</button>
            <br />
        </div> --%>
    </div>
    <br>
</body>
</html>