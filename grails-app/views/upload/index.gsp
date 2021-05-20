<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Upload Page</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <asset:javascript library='jquery' src="components/upload.js"/>
</head>
<body>
    <div class="form">
        <g:uploadForm  name="uploadForm" controller="upload" action="uploadImage">
            <input onChange="ValidateImage(event)" type="file" id="uploadedFile" name="uploadedFile" onClick="this.blur();">
            <input class="submit" type="submit" value="Upload Image" onClick="this.blur();">
        </g:uploadForm>
        <div class="p1 color1 div1" id="imageInfo"></div>
    </div>
</body>
</html>