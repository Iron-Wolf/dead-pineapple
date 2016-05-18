<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
    Document   : upload
    Created on : 03/03/16
    Author     : Sofiane
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <div class="container">
            <br/>
            <h1>
                Conversion
            </h1>
            <hr/>
            <!-- The file upload form used as target for the file upload widget -->
            <form id="fileupload" action="<spring:url value='/upload/add'/>" method="POST" enctype="multipart/form-data">
                <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
                <div class="fileupload-buttonbar">
                    <div class="col-lg-7" style="padding-left: 0px;">
                        <!-- The fileinput-button span is used to style the file input field as button -->
                        <span class="btn btn-success fileinput-button">
                            <i class="glyphicon glyphicon-plus"></i>
                            <span>Ajouter un fichier</span>
                            <input type="file" name="files[]" multiple>
                        </span>
                        <button type="submit" class="btn btn-primary start">
                            <i class="glyphicon glyphicon-upload"></i>
                            <span>Uploader</span>
                        </button>
                        <button class="btn btn-info" onclick="location.href='${dropboxUrl}'" >
                            <i class="glyphicon glyphicon-upload"></i>
                            <span>Depuis dropbox</span>
                        </button>
                        <button type="reset" class="btn btn-warning cancel">
                            <i class="glyphicon glyphicon-ban-circle"></i>
                            <span>Annuler</span>
                        </button>
                        <button type="button" class="btn btn-danger delete">
                            <i class="glyphicon glyphicon-trash"></i>
                            <span>Supprimer</span>
                        </button>
                        <button class="btn btn-info" class="btn btn-info btn-lg" data-toggle="modal" data-target="#ModalDropBox" >
                            <i class="glyphicon glyphicon-upload"></i>
                            <span>Parcourir votre Dropbox</span>
                        </button>

                        <!-- Modal -->
                        <div id="ModalDropBox" class="modal fade" role="dialog">
                            <div class="modal-dialog">

                                <!-- Modal content-->
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        <img class="etp" src="/resources/img/dropbox2.svg" height="30" >
                                        <h4 class="modal-title">Choix d'upload depuis Cloud Dropbox</h4>
                                    </div>
                                    <div class="modal-body">
                                        <div id="dropbox_data">
                                            <c:if test="${not empty dropboxFiles}">
                                                <c:forEach var="listValue" items="${dropboxFiles}">
                                                    ${listValue}
                                                </c:forEach>
                                            </c:if>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                    <!-- The global progress information -->
                    <div class="col-lg-5 fileupload-progress fade">
                        <!-- The global progress bar -->
                        <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
                            <div class="progress-bar progress-bar-success" style="width:0%;"></div>
                        </div>


                        <!-- The extended global progress state -->
                        <div class="progress-extended">&nbsp;</div>
                    </div>
                </div>
                <!-- The loading indicator is shown during file processing -->
                <div class="fileupload-loading"></div>
                <br>
                <!-- The table listing the files available for upload/download -->
                <table role="presentation" id="uploadedFiles" class="table table-striped"><tbody class="files" data-toggle="modal-gallery" data-target="#modal-gallery"></tbody></table>
            </form>
            <br>
            <button class="btn btn-primary start" data-url="/upload/convert">
                <i class="icon-upload icon-white"></i>
                <span>Convertir & payer</span>
            </button>
            <button class="btn btn-info" onclick="location.href='${dropboxUrl}'" >
                <i class="glyphicon glyphicon-upload"></i>
                <span>Depuis dropbox</span>
            </button>
            <div id="dropbox">

            </div>

            <div class="well">
                <h3>Information sur l'upload</h3>
                <ul>
                    <li>The maximum file size for uploads is <strong>1 GB</strong></li>
                    <li>Only video files (<strong>".avi", "mp4", ".ogg", ".flv",".swf",".dv",".mov"</strong>) are allowed.</li>
                    <li>Uploaded files will be deleted automatically after <strong>5 minutes</strong>.</li>
                    <li>You can <strong>drag &amp; drop</strong> files from your desktop on this webpage with Google Chrome, Mozilla Firefox and Apple Safari.</li>
                </ul>
            </div>

        </div>

        <!-- The template to display files available before upload -->
        <script id="template-upload" type="text/x-tmpl">
            {% for (var i=0, file; file=o.files[i]; i++) { %}
        <div class="row template-upload fade">
            <div class="col-sm-4 name"><span>{%=file.name%}</span></div>
            <div class="col-sm-1 size"><span>{%=o.formatFileSize(file.size)%}</span></div>
            {% if (file.error) { %}
            <td class="error" colspan="2"><span class="label label-important">Error</span> {%=file.error%}</td>
            {% } else if (o.files.valid && !i) { %}
            <div class="col-sm-3 progress_style">
                <div class="progress">
                            <div class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar"
                                 aria-valuemin="0" aria-valuemax="100">
                            </div>
                </div>
            </div>

            <div class="col-sm-2 start">{% if (!o.options.autoUpload) { %}
                <button class="btn btn-primary">
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>Démarrer</span>
                </button>
                {% } %}</div>
            {% } else { %}
            {% } %}
            <div class="col-sm-2 cancel">{% if (!i) { %}
                <button class="btn btn-warning">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>Annuler</span>
                </button>
                {% } %}
            </div>
        </div>
        {% } %}
    </script>
    <!-- The template to display files before conversion -->
    <script id="template-download" type="text/x-tmpl">
        {% for (var i=0, file; file=o.files[i]; i++) { %}
        <div class="row template-download fade">
            {% if (file.error) { %}
            <td ><span class="name">{%=file.name%}</span></td>
            <td ><span class="size">{%=o.formatFileSize(file.size)%}</span></td>
            <td class="error" colspan="2"><span class="label label-important">Error</span> {%=file.error%}</td>
            {% } else { %}
            <div class="col-sm-1">
            <span class="preview">{% if (file.thumbnail_url) { %}
                <a href="{%=file.url%}" title="{%=file.name%}" rel="gallery" download="{%=file.name%}"><img src="{%=file.thumbnail_url%}" style="margin-top: 5px;"></a>
                {% } %}</span></div>
            <div class="col-sm-3 filename">
                <span class="name">
                    <a href="{%=file.url%}" title="{%=file.name%}" rel="{%=file.thumbnail_url&&'gallery'%}" download="{%=file.name%}">{%=file.name%}</a>
                </span>
            </div>
            <div class="col-sm-1"><span class="size">{%=o.formatFileSize(file.size)%}</span></div>
            <div class="col-sm-1"><span class="duration">{%=file.duration%}</span></div>
            <div class="col-sm-1"><span class="price">{%=file.price%} &euro;</span></div>
            <div class="col-sm-1">
                 <div class="form-group">
                      <label for="sel1">Formats</label>
                      <select class="form-control formats" onchange="setFormat()">
                        <option value="avi">.avi</option>
                        <option value="mp4">.mp4</option>
                        <option value="mp3">.mp3</option>
                        <option value="aac">.aac</option>
                        <option value="wav">.wav</option>
                        <option value="wma">.wma</option>
                        <option value="wmv">.wmv</option>
                        <option value="ogg">.ogg</option>
                        <option value="flv">.flv</option>
                        <option value="swf">.swf</option>
                        <option value="dv">.dv</option>
                        <option value="mov">.mov</option>
                     </select>
                </div>
            </div>
            <div class="col-sm-2">
                  <div class="form-group">
                      <label for="sel1">Encodage :</label>
                      <select class="form-control" onchange="setEncodage()">
                        <option>mpeg4</option>
                        <option>x264</option>
                        <option>x265</option>
                      </select>
                  </div>
            </div>
            {% } %}
            <div class="col-sm-2 delete">
                <button class="btn btn-danger" data-type="{%=file.delete_type%}" data-url="{%=file.delete_url%}"{% if (file.delete_with_credentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                        <i class="glyphicon glyphicon-trash"></i>
                    <span>Delete</span>
                </button>
                <input type="checkbox" name="delete" value="1">
            </div>
        </div>
        {% } %}
    </script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script src="<spring:url value='/resources/js/vendor/jquery.ui.widget.js'/>"></script>
    <script src="<spring:url value='/resources/js/tmpl.min.js'/>"></script>
    <script src="<spring:url value='/resources/js/load-image.min.js'/>"></script>
    <script src="<spring:url value='/resources/js/canvas-to-blob.min.js'/>"></script>
    <script src="<spring:url value='/resources/js/bootstrap-image-gallery.min.js'/>"></script>
    <script src="<spring:url value='/resources/js/jquery.iframe-transport.js'/>"></script>
    <script src="<spring:url value='/resources/js/jquery.fileupload.js'/>"></script>
    <script src="<spring:url value='/resources/js/jquery.fileupload-fp.js'/>"></script>
    <script src="<spring:url value='/resources/js/jquery.fileupload-ui.js'/>"></script>
    <script src="<spring:url value='/resources/js/locale.js'/>"></script>
    <script src="<spring:url value='/resources/js/main.js'/>"></script>
    <script src="<spring:url value='/resources/js/jquery.easing.js'/>"></script>
    <script src="<spring:url value='/resources/js/jqueryFileTree.js"'/>"></script>
   <script type="text/javascript">

        function setFormat(){
            // When user choose a format for the file, send it to the bdd
            console.log("Envoi format vers serveur ");
            var format = $(".formats a:selected").val();
            var fileName = $(".formats :selected").parent().attr("fileName");
            console.log(format, fileName);
            $.ajax({
            type:"GET",
            url: "/upload/setFormat",
            data: {     format: format,
                        file: fileName}
            }).done(function(msg){
                // Format set (display price)
            });

        };
        $(".formats ul.dropdown-menu li a").click(function (e) {
            e.preventDefault();
            console.log(this.val());
            setFormat();
        });
        $(document).on('click', '.formats li a', function() { alert('test'); });
        function setEncodage(){
            // When user choose a format for the file, send it to the bdd
            console.log("Envoi format vers serveur ");
            var format = $(".encodage a:selected").val();
            var fileName = $(".encodage :selected").parent().attr("fileName");
            console.log(format, fileName);
            $.ajax({
                type:"GET",
                url: "/upload/setFormat",
                data: {     format: format,
                    file: fileName}
            }).done(function(msg){
                // Format set (display price)
            });

        };
        $(document).ready( function() {
            function openFile(file) {
                if (confirm("Souhaitez-vous uploader le fichier :"+ file+" ?")) {
                    // Save it!
                    $.ajax({
                        type:"GET",
                        url: "/upload/setFormat",
                        data: { fileName: file}
                    }).done(function(msg){
                        // Format set (display price)
                    });
                } else {
                    // Do nothin…
                }

            }

            $('#dropbox_data').fileTree({root: 'dropbox/'}, function (file) {
                openFile(file);
            });
        });


        </script>