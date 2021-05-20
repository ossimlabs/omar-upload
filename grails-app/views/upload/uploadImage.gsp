<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Upload Page</title>
    <g:set var="phrase" value="rosberto" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <asset:javascript library='jquery' src="components/upload.js"/>
</head>
<body>
    <div class="form">
        <div class="info-container">
            <div class="form-title">Image upload success!</div>
            <table>
                <tr><td class="p1 color2 td-h">Filename:</td><td class="p1 color1">${this.image.filename}</td></tr>
                <tr><td class="p1 color2 td-h">Upload path:</td><td class="p1 color1">${this.image.path}</td></tr>
                <tr><td class="p1 color2 td-h">Size:</td><td class="p1 color1">${this.image.size}</td></tr>
            </table>
        </div>
    </div>
</body>
</html>