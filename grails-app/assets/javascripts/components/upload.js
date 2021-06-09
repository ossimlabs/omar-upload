let taskBarH = 61;
let topH = 0;
let middleH = 0;
let spacer = null;
let interval = null;

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
  return mySize < sizeLimit;
}

function ValidateImage(event) {
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

  let regexExtensions = extensions.split(' | ').join('|');
  //Check whether the file is valid Image.
  let regex = new RegExp(`([a-zA-Z0-9\s_\\.\-:])+(${regexExtensions})$`);
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

function upload() {
  let progress = $("#progress");
  let progressText = document.getElementById('progress-text');
  let top = $("#top");
  let inputContainer = $("#input-container");
  let statusContainer = $("#status-container");
  let input = document.getElementById("uploadedFile");

  let data = new FormData();
  let request = new XMLHttpRequest();

  let file = input.files[0];
  let filename = file.name;
  let filesizeString = GetSizeString(file.size);

  data.append("file", file);

  let setHeightOnLoad = false;

  request.upload.addEventListener("progress", function(e) {
    top.hide();
    inputContainer.hide();
    statusContainer.show();
    let loaded = e.loaded;
    let total = e.total;

    let percentage = (loaded / total) * 100;
    let curSizeString = GetSizeString(loaded);
    progress.show();
    progress.attr('style',  `width: ${Math.floor(percentage)}%;`);
    progressText.innerHTML = `${curSizeString} / ${filesizeString}`

    if (!setHeightOnLoad) {
      setHeightOnLoad = true;
      topH = 0;
      // middleH = progress.height();
      setSpacerHeight();
    }
  })

  request.addEventListener("load", function(e) {
    if (request.status == 200) {
      let element = $(request.responseText);
      progress.hide();
      statusContainer.hide();
      $('#uploadStatus').empty();
      $('#uploadStatus').append(element);
      topH = 0;
      middleH = $('#uploadStatus').height();
      setSpacerHeight();

      if (document.getElementById('upload-fail') == null)
        postToStager();
    } else {
      progress.hide();
      statusContainer.hide();
      let errorText = request.responseText;
    }
  })

  request.open("post", ajaxAction);
  request.send(data);
}

function postToStager() {
  let xhttp = new XMLHttpRequest();
  let data = JSON.stringify({path: ($("#path").text() + "/" + $("#filename").text())});
  xhttp.open("post", ajaxStager, true);
  xhttp.send(data);
  xhttp.addEventListener("load", function(e) {
    if (xhttp.status == 200) {
      $('#pending').show();
      interval = setInterval(function() { checkStagingStatus(); }, 3000);
    } else {
      let errorText = xhttp.responseText;
      document.getElementById('stagingErrorText').innerHTML = errorText;
      $('#alert-stage-error').show();
    }
  })
}

function checkStagingStatus() {
  if (document.getElementById('status-path') == null) 
    return;

  let path = $('#status-path').text();
  var xhttp = new XMLHttpRequest();
  let data = JSON.stringify({name: path});

  xhttp.addEventListener("load", function(e) {
    if (xhttp.status == 200) {
      let res = JSON.parse(xhttp.responseText);
      let total = res.totalFeatures;
      if (total > 0) {
        let id = res.features[0].properties.id;
        let tlvLink = `${window.location.origin}/tlv/?filter=in(${id})`;
        document.getElementById("imageLink").setAttribute("href", tlvLink);
        $('#pending').hide();
        $('#alert').show();
        clearInterval(interval);
      }
    }
  })

  xhttp.open("post", ajaxWfs, true);
  xhttp.send(data);
}

function setSpacerHeight() {
  let windowH = $(document).height();

  let newHeight = windowH/2 - (taskBarH + topH + middleH);
  spacer.attr('style',  `height: ${newHeight}px;`);
}

$(document).ready(function() {
  topH = $("#upload-container").height();
  middleH = $("#input-container").height();
  spacer = $("#spacer");

  setSpacerHeight();

  $("#top").show();
  $("#input-container").show();
})

$(window).resize(function() {
  setSpacerHeight();
})

