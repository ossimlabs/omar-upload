<!doctype html>
<html>
<head>
    <title>Success</title>

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

    function checkStagingStatus() {
        var val = "hello&there";
        console.log("${grailsApplication.config.omarWfsUrl}" + '${raw(grailsApplication.config.baseWfsQuery)}' + "&filter=filename+LIKE+%27%25${params.filename}%25%27");
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var wfsQueryResults = JSON.parse(this.responseText);
                var omarUiLink = "${grailsApplication.config.omarUiUrl}?filename=${params.filename}"

                document.getElementById("checkStagingResults").innerHTML = "" +
                    "<br />" +
                    "The image has been staged!" +
                    "<br /><br />" +
                    "Omar-ui link: <a href=\"" + omarUiLink + "\">" + omarUiLink + "</a>" +
                    "<br /><br />" +
                    "Here is the result of the WFS query:" +
                    "<br />" +
                    "<pre>" + JSON.stringify(wfsQueryResults, null, 2) + "</pre>" +
                    "";
            } else if (this.readyState == 4) {
                document.getElementById("checkStagingResults").innerHTML = "The image has not yet been staged, please wait a few seconds and then try again."
            } else {
                document.getElementById("checkStagingResults").innerHTML = "<img id='spinner' src=\"${createLinkTo(dir: 'images', file: 'spinner.gif')}\" alt='Spinner'/>"
            }
        };
        xhttp.open("GET", "${raw(grailsApplication.config.omarWfsUrl)}${raw(grailsApplication.config.baseWfsQuery)}&filter=filename+LIKE+%27%25${params.filename}%25%27", true);
        xhttp.send();
    }

</script>
<body>

    <div>
        <h1>File upload successful</h1>
        Filename: ${params.filename}
        <br />
        Placed location: ${params.location}
        <br />
        <br />
        Check if the image has been staged here:
        <button type="button" onClick="checkStagingStatus()">Check Status</button>
        <br />
    </div>

    <div id="checkStagingResults">

    </div>

</body>
</html>
