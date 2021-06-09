
<div class="form">
    <g:if test="${this.image}" >
        <i class="large material-icons colorSuccess" style="margin-left: 5px;">mood</i><br><br>
        <div class="info-container">
            <div class="form-title colorSuccess">Image upload success!</div>
            <table>
                <tr><td class="p1 color2 td-h">Filename:</td><td class="p1 color1" id="filename">${this.image.filename}</td></tr>
                <tr><td class="p1 color2 td-h">Upload path:</td><td class="p1 color1" id="path">${this.image.path}</td></tr>
                <tr><td class="p1 color2 td-h">Size:</td><td class="p1 color1">${this.image.size}</td></tr>
            </table>
        </div>
    </g:if>
    <g:if test="${this.message}">
        <i class="large material-icons colorError" style="margin-left: 5px;">mood_bad</i><br><br>
        <div class="info-container">
            <div class="form-title colorError">Image upload failed!</div>
            <table>
                <tr><td class="p1 color2 td-h">Reason:</td><td class="p1 color1">${this.message}</td></tr>
            </table>
        </div>
    </g:if>
    <br>
    <div class="nav-btn">
        <g:link controller="upload"><button>Return to Uploader</button></g:link>
    </div>

    <div>
        Ingest status: 
        <i class="large material-icons color2" style="margin-left: 5px;">refresh</i>
        <button type="button" onClick="checkStagingStatus()">Check Status</button>
        <br />
    </div>

    <br>
    <br>
    <div id="stagerResponse"></div>
</div>