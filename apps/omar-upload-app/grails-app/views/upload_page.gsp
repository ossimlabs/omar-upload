<!doctype html>
<html>
<head>
    <title>Upload Page</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>

<script type="text/javascript">
    function showSpinner() {
        document.getElementById('spinner').style.display = 'inline';
        document.getElementById('error').style.display = 'none';
    }

    function hideSpinner() {
        document.getElementById('spinner').style.display = 'none';
        document.getElementById('error').style.display = 'none';
    }

    function showError(e) {
        var errorDiv = document.getElementById('error')
        errorDiv.innerHTML = '<ul><li>'
            + e.responseText + '</li></ul>';
        errorDiv.style.display = 'block';
    }
</script>
<body>

    <div>
        <h1>Welcome to the Omar Upload Service</h1>
        <g:form method="post" enctype="multipart/form-data" controller="archive" action="upload">
            <input type="file" id="uploadedFile" name="uploadedFile" />
            <g:actionSubmit controller="archive" action="upload" value="Upload File" onClick="showSpinner();" />
        </g:form>
        <img id="spinner" style="display:none;" src="${createLinkTo(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/>
    </div>

</body>
</html>
