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
  if (!event.target.files[0]) {
    info.innerHTML = '';
    $("#submit").hide();
    return;
  }
  //Get reference of File.
  let fileUpload = document.getElementById("uploadedFile");

  //Check whether the file is valid Image.
  let regex = new RegExp("([a-zA-Z0-9\s_\\.\-:])+(.tif)$");
  let size = event.target.files[0].size;

  if (regex.test(fileUpload.value.toLowerCase()) && ValidSize(size, sizeLimit)) {
    $("#submit").show();
    info.innerHTML = `<span class="p1 color2" id="imageInfo">File size: </span>${GetSizeString(size)}`;
  } else if (!ValidSize(size, sizeLimit)) {
    $("#uploadedFile").empty();
    $("#submit").show();
    info.innerHTML = `<span class="p1 colorError" id="imageInfo">File size exceeds the limit of ${GetSizeString(sizeLimit)}</span>`;
  } else {
    $("#uploadedFile").empty();
    $("#submit").show();
    info.innerHTML = `<span class="p1 colorError" id="imageInfo">Incorrect file type</span>`;
  }
}