
    <div class="form">
        <g:if test="${this.image}" >
            <i class="large material-icons colorSuccess" style="margin-left: 5px;">mood</i><br><br>
            <div class="info-container">
                <div class="form-title">Image upload success!</div>
                <table>
                    <tr><td class="p1 color2 td-h">Filename:</td><td class="p1 color1" id="filename">${this.image.filename}</td></tr>
                    <tr><td class="p1 color2 td-h">Upload path:</td><td class="p1 color1" id="path">${this.image.path}</td></tr>
                    <tr><td class="p1 color2 td-h">Size:</td><td class="p1 color1">${this.image.size}</td></tr>
                </table>
            </div><br>

            <span hidden id="status-path">${this.path}/${this.filename}</span>
            <div hidden id="alert" class="alert-container">
                <div class="alert-sub-container">
                    <div class="info-container-bottom ingest-alert">
                        <div id="statusText" class="color3 alert-text">
                            <span style="float:left;">Image ingested!</span><span style="float:right;">
                                <a target="_blank" rel="noopener noreferrer" id="imageLink">
                                    <i class="large material-icons color-alt color-alt-btn" style="line-height:45px;">link</i>
                                </a>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
            <div hidden class="pending-container p1 color1 div1 fadeIn" id="pending">
                Ingest pending<br>
                <div class="loading-ball delay-1"></div>
                <div class="loading-ball delay-2"></div>
                <div class="loading-ball delay-3"></div>
            </div>
            <div hidden id="alert-stage-error" class="alert-container">
                <div class="alert-sub-container">
                    <div class="info-container-bottom ingest-alert">
                        <div id="stageErrorText" class="colorError alert-text">
                            <span style="float:left;">Error staging image</span>
                            <span style="float:right;">
                                <div class="tooltip">
                                    <i class="large material-icons colorError" style="line-height:45px; cursor: default;">
                                        info
                                    </i>
                                    <span id="stagingErrorText" class="tooltiptext"></span>
                                </div>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </g:if>
        <g:if test="${this.message}">
            <i class="large material-icons colorError" style="margin-left: 5px;">mood_bad</i><br><br>
            <div id="upload-fail" class="info-container">
                <div class="form-title">Image upload failed!</div>
                <table>
                    <tr><td class="p1 color2 td-h">Reason:</td><td class="p1 color1">${this.message}</td></tr>
                </table>
            </div><br>
        </g:if>
        <br>
        <div class="nav-btn">
            <g:link controller="upload"><button>Return to Uploader</button></g:link>
        </div>

        <br>
        <br>
        <div id="stagerResponse"></div>
    </div>