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
                <div class="row fileupload-buttonbar">
                    <div class="col-lg-7">
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
                <table role="presentation" class="table table-striped"><tbody class="files" data-toggle="modal-gallery" data-target="#modal-gallery"></tbody></table>
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
            <div id="dropbox_data">
                <c:if test="${not empty dropboxFiles}">
                    <c:forEach var="listValue" items="${dropboxFiles}">
                        ${listValue}
                    </c:forEach>
                </c:if>
            </div>
            <div class="well">
                <h3>Information sur l'upload</h3>
                <ul>
                    <li>The maximum file size for uploads is <strong>1 GB</strong></li>
                    <li>Only video files (<strong>".avi", "mp4", "mp3", ".aac", ".wav", ".wma", ".wmv", ".ogg", ".flv",".swf",".dv",".mov"</strong>) are allowed.</li>
                    <li>Uploaded files will be deleted automatically after <strong>5 minutes</strong>.</li>
                    <li>You can <strong>drag &amp; drop</strong> files from your desktop on this webpage with Google Chrome, Mozilla Firefox and Apple Safari.</li>
                </ul>
            </div>

        </div>
        <!-- modal-gallery is the modal dialog used for the image gallery -->
        <div id="modal-gallery" class="modal modal-gallery hide fade" data-filter=":odd">
            <div class="modal-header">
                <a class="close" data-dismiss="modal">&times;</a>
                <h3 class="modal-title"></h3>
            </div>
            <div class="modal-body"><div class="modal-image"></div></div>
            <div class="modal-footer">
                <a class="btn modal-download" target="_blank">
                    <i class="icon-download"></i>
                    <span>Download</span>
                </a>
                <a class="btn btn-success modal-play modal-slideshow" data-slideshow="5000">
                    <i class="icon-play icon-white"></i>
                    <span>Slideshow</span>
                </a>
                <a class="btn btn-info modal-prev">
                    <i class="icon-arrow-left icon-white"></i>
                    <span>Previous</span>
                </a>
                <a class="btn btn-primary modal-next">
                    <span>Next</span>
                    <i class="icon-arrow-right icon-white"></i>
                </a>
            </div>
        </div>
        <!-- The template to display files available before upload -->
        <script id="template-upload" type="text/x-tmpl">
            {% for (var i=0, file; file=o.files[i]; i++) { %}
        <tr class="template-upload fade">
            <td class="preview"><span class="fade"></span></td>
            <td class="name"><span>{%=file.name%}</span></td>
            <td class="duration"><span>{%=file.duration%}</span></td>
            <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
            {% if (file.error) { %}
            <td class="error" colspan="2"><span class="label label-important">Error</span> {%=file.error%}</td>
            {% } else if (o.files.valid && !i) { %}
            <td class="progress_style">
                <div class="progress">
                            <div class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar"
                                 aria-valuemin="0" aria-valuemax="100">
                            </div>
                </div>
            </td>
            <td class="start">{% if (!o.options.autoUpload) { %}
                <button class="btn btn-primary">
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>Start</span>
                </button>
                {% } %}</td>
            {% } else { %}
            <td colspan="2"></td>
            {% } %}
            <td class="cancel">{% if (!i) { %}
                <button class="btn btn-warning">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>Cancel</span>
                </button>
                {% } %}</td>
        </tr>
        {% } %}
    </script>
    <!-- The template to display files before conversion -->
    <script id="template-download" type="text/x-tmpl">
        {% for (var i=0, file; file=o.files[i]; i++) { %}
        <tr class="template-download fade">
            {% if (file.error) { %}
            <td></td>
            <td class="name"><span>{%=file.name%}</span></td>
            <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
            <td class="error" colspan="2"><span class="label label-important">Error</span> {%=file.error%}</td>
            {% } else { %}
            <td class="preview">{% if (file.thumbnail_url) { %}
                <a href="{%=file.url%}" title="{%=file.name%}" rel="gallery" download="{%=file.name%}"><img src="{%=file.thumbnail_url%}"></a>
                {% } %}</td>
            <td class="name">
                <a href="{%=file.url%}" title="{%=file.name%}" rel="{%=file.thumbnail_url&&'gallery'%}" download="{%=file.name%}">{%=file.name%}</a>
            </td>
            <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
            <td class="duration"><span>{%=file.duration%}</span></td>
            <td class="price"><span>{%=file.price%} &euro;</span></td>
            <td>
                <select fileName="{%=file.name%}" class="formats" onchange="setFormat()">
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
            </td>
            <td colspan="2"></td>
            {% } %}
            <td class="delete">
                <button class="btn btn-danger" data-type="{%=file.delete_type%}" data-url="{%=file.delete_url%}"{% if (file.delete_with_credentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                        <i class="glyphicon glyphicon-trash"></i>
                    <span>Delete</span>
                </button>
                <input type="checkbox" name="delete" value="1">
            </td>
        </tr>
        {% } %}
    </script>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="<spring:url value='/resources/js/vendor/jquery.ui.widget.js'/>"></script>
    <script src="<spring:url value='/resources/js/tmpl.min.js'/>"></script>
    <script src="<spring:url value='/resources/js/load-image.min.js'/>"></script>
    <script src="<spring:url value='/resources/js/canvas-to-blob.min.js'/>"></script>
    <script src="<spring:url value='/resources/js/bootstrap.min.js'/>"></script>
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
            var format = $(".formats option:selected").val();
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