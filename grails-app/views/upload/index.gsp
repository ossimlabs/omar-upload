<html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Upload Page</title>
</head>
<body>

<div id="content" role="main">
    <section class="row colset-2-its">
        <h1>Welcome to the Image Upload Page...</h1>
        <g:uploadForm controller="upload" action="uploadImage">
          <input type="file" id="uploadedFile" name="uploadedFile">
          <input type="submit" value="Upload Image">
        </g:uploadForm>
    </section>
</div>

</body>
</html>