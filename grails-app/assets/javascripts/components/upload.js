console.log("Loaded upload.js");

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

function ValidateImage(event) {
  //Get reference of File.
  let fileUpload = document.getElementById("uploadedFile");

  //Check whether the file is valid Image.
  let regex = new RegExp("([a-zA-Z0-9\s_\\.\-:])+(.jpg|.png|.gif|.tif)$");
  if (regex.test(fileUpload.value.toLowerCase())) {
    let size = event.target.files[0].size;
    let info = document.getElementById("imageInfo");
    info.innerHTML = `<span class="p1 color2" id="imageInfo">File size: </span>${GetSizeString(size)}`;
  } else {

  }
}