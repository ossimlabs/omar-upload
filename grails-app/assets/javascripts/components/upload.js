function GetSizeString(size) {
  
  switch (true) {
    case size < 1000:
      return `${size} B`;
    case (size < 1000000):
      return `${(size / 1000).toFixed(1)} KB`;
    case size < 1000000000:
      return `${(size / 1000000).toFixed(1)} MB`;
    case size < 1000000000000:
      return `${(size / 1000000000).toFixed(1)} GB`;
    default:
      return `${size}`;
  }
}

function ValidSize(mySize, sizeLimit) {
  console.log(`${mySize} | ${sizeLimit}`);
  return mySize < sizeLimit;
}

function ValidateImage(event, sizeLimit) {
  let info = document.getElementById("imageInfo");
  let error = document.getElementById("imageError");
  error.hidden = true;
  if (!event.target.files[0]) {
    info.innerHTML = '';
    $("#submit").hide();
    return;
  }
  //Get reference of File.
  let fileUpload = document.getElementById("uploadedFile");

  //Check whether the file is valid Image.
  let regex = new RegExp("([a-zA-Z0-9\s_\\.\-:])+(.tif|.ntf)$");
  let size = event.target.files[0].size;

  if (regex.test(fileUpload.value.toLowerCase()) && ValidSize(size, sizeLimit)) {
    $("#submit").show();
    info.innerHTML = `<span class="p1 color2" id="imageInfo">File size: </span>${GetSizeString(size)}`;
  } else if (!ValidSize(size, sizeLimit)) {
    $("#uploadedFile").empty();
    $("#submit").hide();
    info.innerHTML = `<span class="p1 colorError" id="imageInfo">File size exceeds the limit of ${GetSizeString(sizeLimit)}</span>`;
  } else {
    $("#uploadedFile").empty();
    $("#submit").hide();
    info.innerHTML = `<span class="p1 colorError" id="imageInfo">Incorrect file type</span>`;
  }
}

function showSpinner() {
  document.getElementById('spinner').style.display = 'inline';
  document.getElementById('error').style.display = 'none';
}

function hideSpinner() {
  document.getElementById('spinner').style.display = 'none';
  document.getElementById('error').style.display = 'none';
}

function checkStagingStatus() {
  let filenameObj = document.getElementById('filename');
  let pathObj = document.getElementById('path');

  if (filenameObj == undefined || filenameObj == null)
    return;

  let wfsQuery = `${baseUrl}${wfsUrl}&filter=filename+LIKE+%27%25/3pa-blacksky/upload-test/2021-05-24/${uploadedFilename}%25%27`;
  
  console.log(`Wfs query: ${wfsQuery}`);
  // $.ajax({
  //   url: ajaxAction,
  //   data: {path: `${path.innerText}/${filenameObj.innerText}`},
  //   success: function(resp){
  //       console.log(resp);
  //   }
  // });

  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
      // readyState 4: Done
      // status 200: HTTP code 200: success
      if (this.readyState == 4 && this.status == 200) {
          var wfsQueryResults = JSON.parse(this.responseText);
          if (wfsQueryResults != null && wfsQueryResults["features"].length != 0) {
              console.log(wfsQueryResults);
              console.log(wfsQueryResults["features"]);
              console.log(wfsQueryResults["features"][0]["properties"]);
          } else{
            console.log("NOT YET STAGED");
              // document.getElementById("checkStagingResults").innerHTML = "The image has not yet been staged, please wait a few seconds and then try again."
          }
      } else if (this.readyState == 4) {
        console.log("NOT YET STAGED");
          // document.getElementById("checkStagingResults").innerHTML = "The image has not yet been staged, please wait a few seconds and then try again."
      } else {
        console.log("NOT YET STAGED");
          // document.getElementById("checkStagingResults").innerHTML = "<img id='spinner' src=\"${createLinkTo(dir: 'images', file: 'spinner.gif')}\" alt='Spinner'/>"
      }
  };
  xhttp.open("GET", wfsQuery, true);
  xhttp.send();
}