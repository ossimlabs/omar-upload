<!doctype html>
<html>
<head>
    <title>Upload Page</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>
<body>

    <div>
        <h1>Welcome to the Omar Upload Service</h1>
        <g:form method="post" enctype="multipart/form-data" controller="archive" action="upload">
            <input type="file" id="uploadedFile" name="uploadedFile" />
            <g:actionSubmit controller="archive" action="upload" value="Upload File" />
        </g:form>
    </div>

</body>
</html>
