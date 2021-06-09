<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Upload Page</title>
    <g:set var="phrase" value="rosberto" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <asset:javascript library='jquery' src="components/upload.js"/>
    <g:javascript>
        var baseUrl = "${baseUrl}" 
        var ajaxAction = "${createLink(controller:'upload',action:'uploadImage')}"
        var ajaxWfs = "${createLink(controller:'upload',action:'checkStagingStatus')}"
        var ajaxStager = "${createLink(controller:'upload',action:'stageImage')}"
        var extensions = "${extensions}" 
        var sizeLimit = "${maxFileSize}"
        var sizeLimitString = "${sizeLimitString}"
    </g:javascript>
</head>
<body>
    <div class="form" id="upload-container">
        <div hidden id="top" class="textBox info-container" style="padding-top:15px;">
            <div class="p2 color3 header1" style="font-weight: bold;">Welcome to the Omar Upload Service!</div>
            <div class="color1 p1 align1">
                Choose an image to be uploaded directly into Omar's filesystem and automatically staged. 
            </div>
            <div class="color1 p1 textBox" style="margin-bottom:15px;">
                <span style="font-weight: bold; color: #f6f6f6;">File types:</span> ${extensions} 
                <span style="font-weight: bold; color: #f6f6f6;"> Size limit:</span> ${sizeLimitString}
            </div>
        </div>
    </div>
    <div id="spacer"></div>
    <div hidden class="form" id="input-container">
        <input onChange="ValidateImage(event)" type="file" id="uploadedFile" name="uploadedFile" onClick="this.blur();">
        <input hidden id="submit" class="submit" type="submit" value="Upload Image" onClick="this.blur(); upload();">

        <div class="p1 color1 div1" id="imageInfo"></div>
        <div class="p1 colorError div1" id="imageError">${this.message}</div>
        <div class="color1 p align1 textBox">
            <span class="colorError">DO NOT</span> close out of your browser until the file is fully uploaded.
        </div>
    </div>

    <div id="status-container" hidden class="form">
        <div id="progress-container" class="color1 p align1 textBox">
            <div id="progress-text" style="text-align:left;"></div>
            <div id="progress" class="bar">
                <div class="bar-background"></div>
            </div>
        </div>
    </div>
    <div class="align-vert" id="uploadStatus"></div>
</body>
</html>