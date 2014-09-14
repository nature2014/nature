<!-- blueimp Gallery styles -->
<link rel="stylesheet" href="${rootPath}/jslib/flatlab/assets/loadImage-1.12.0/blueimp-gallery.min.css">
<!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
<link rel="stylesheet" href="${rootPath}/jslib/flatlab/assets/file-uploader/css/jquery.fileupload.css">
<link rel="stylesheet" href="${rootPath}/jslib/flatlab/assets/file-uploader/css/jquery.fileupload-ui.css">

<!-- The file upload form used as target for the file upload widget -->
<div id="fileupload">

    <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
    <div class="row fileupload-buttonbar">
        <div class="col-lg-7">
            <!-- The fileinput-button span is used to style the file input field as button -->
                                        <span class="btn btn-success fileinput-button">
                                        <i class="glyphicon glyphicon-plus"></i>
                                        <span>添加</span>
                                        <input type="file" name="images" multiple>
                                        </span>
            <button type="submit" class="btn btn-primary start">
                <i class="glyphicon glyphicon-upload"></i>
                <span>开始上传</span>
            </button>
            <button type="button" class="btn btn-danger delete">
                <i class="glyphicon glyphicon-trash"></i>
                <span>删除所有</span>
            </button>
            <input type="checkbox" class="toggle">
            <!-- The global file processing state -->
            <span class="fileupload-process"></span>
        </div>
        <!-- The global progress state -->
        <div class="col-lg-5 fileupload-progress fade">
            <!-- The global progress bar -->
            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
                <div class="progress-bar progress-bar-success" style="width:0%;">
                </div>
            </div>
            <!-- The extended global progress state -->
            <div class="progress-extended">
                &nbsp;
            </div>
        </div>
    </div>
    <!-- The table listing the files available for upload/download -->
    <table role="presentation" class="table table-striped">
        <tbody class="files">
        </tbody>
    </table>

    <!-- The blueimp Gallery widget -->
    <div id="blueimp-gallery" class="blueimp-gallery blueimp-gallery-controls" data-filter=":even">
        <div class="slides">
        </div>
        <h3 class="title"></h3>
        <a class="prev">‹</a>
        <a class="next">›</a>
        <a class="close">×</a>
        <a class="play-pause"></a>
        <ol class="indicator">
        </ol>
    </div>
</div>


<script type="text/javascript">
</script>
<!--Multiuple File Uploader-->
<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
<script src="${rootPath}/jslib/flatlab/assets/file-uploader/js/vendor/jquery.ui.widget.js"></script>
<!-- The Templates plugin is included to render the upload/download listings -->
<script src="${rootPath}/jslib/flatlab/assets/templates-2.5.4/js/tmpl.min.js"></script>
<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
<script src="${rootPath}/jslib/flatlab/assets/loadImage-1.12.0/js/load-image.min.js"></script>
<!-- The Canvas to Blob plugin is included for image resizing functionality -->
<script src="${rootPath}/jslib/flatlab/assets/loadImage-1.12.0/canvas-to-blob.min.js"></script>

<!-- blueimp Gallery script -->
<script src="${rootPath}/jslib/flatlab/assets/loadImage-1.12.0/jquery.blueimp-gallery.min.js"></script>
<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
<script src="${rootPath}/jslib/flatlab/assets/file-uploader/js/jquery.iframe-transport.js"></script>
<!-- The basic File Upload plugin -->
<script src="${rootPath}/jslib/flatlab/assets/file-uploader/js/jquery.fileupload.js"></script>
<!-- The File Upload processing plugin -->
<script src="${rootPath}/jslib/flatlab/assets/file-uploader/js/jquery.fileupload-process.js"></script>
<!-- The File Upload image preview & resize plugin -->
<script src="${rootPath}/jslib/flatlab/assets/file-uploader/js/jquery.fileupload-image.js"></script>
<!-- The File Upload validation plugin -->
<script src="${rootPath}/jslib/flatlab/assets/file-uploader/js/jquery.fileupload-validate.js"></script>
<!-- The File Upload user interface plugin -->
<script src="${rootPath}/jslib/flatlab/assets/file-uploader/js/jquery.fileupload-ui.js"></script>
<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE 8 and IE 9 -->
<!--[if (gte IE 8)&(lt IE 10)]>
<script src="${rootPath}/jslib/flatlab/assets/file-uploader/js/cors/jquery.xdr-transport.js"></script>
<![endif]-->


<!-- The template to display files available for upload -->
<script id="template-upload" type="text/x-tmpl">
      {% for (var i=0, file; file=o.files[i]; i++) { %}
      <tr class="template-upload fade">
          <td>
              <span class="preview"></span>
          </td>
          <td>
              <p class="name">{%=file.name%}</p>
              <strong class="error text-danger"></strong>
          </td>
          <td>
              <p class="size">正在上传...</p>
              <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
          </td>
          <td>
              {% if (!i && !o.options.autoUpload) { %}
              <button class="btn btn-primary start" disabled>
                  <i class="glyphicon glyphicon-upload"></i>
                  <span>开始上传</span>
              </button>
              {% } %}
              {% if (!i) { %}
              <button class="btn btn-warning cancel">
                  <i class="glyphicon glyphicon-ban-circle"></i>
                  <span>取消</span>
              </button>
              {% } %}
          </td>
      </tr>
      {% } %}









</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
      {% for (var i=0, file; file=o.files[i]; i++) { %}
      <tr class="template-download fade">
          <td>
            <input type="hidden" name="image.fileName" value="{%=file.fileName%}">
            <input type="hidden" name="image.name" value="{%=file.name%}">
            <input type="hidden" name="image.size" value="{%=file.size%}">
            <span class="preview">
                {% if (file.thumbnailUrl) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img src="{%=file.thumbnailUrl%}"></a>
                {% } %}
            </span>
          </td>
          <td>
              <p class="name">
                  {% if (file.url) { %}
                  <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.name%}</a>
                  {% } else { %}
                  <span>{%=file.name%}</span>
                  {% } %}
              </p>
              {% if (file.error) { %}
              <div><span class="label label-danger">文件出错</span> {%=file.error%}</div>
              {% } %}
          </td>
          <td>
              <span class="size">{%=o.formatFileSize(file.size)%}</span>
          </td>
          <td>
              {% if (file.deleteUrl) { %}
              <button class="btn btn-danger delete" data-type="{%=file.deleteType%}" data-url="${rootPath}/{%=file.deleteUrl%}"{% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
              <i class="glyphicon glyphicon-trash"></i>
              <span>删除</span>
              </button>
              <input type="checkbox" name="delete" value="1" class="toggle">
              {% } else { %}
              <button class="btn btn-warning cancel">
                  <i class="glyphicon glyphicon-ban-circle"></i>
                  <span>Cancel</span>
              </button>
              {% } %}
          </td>
      </tr>
      {% } %}









</script>

<script type="application/javascript">
    $(function () {
        'use strict';

        // Initialize the jQuery File Upload widget:
        $('#fileupload').fileupload({
            // Uncomment the following to send cross-domain cookies:
            xhrFields: {withCredentials: true},
            url: '${rootPath}/upload/uploadImage.action'
        });

        // Enable iframe cross-domain access via redirect option:
        $('#fileupload').fileupload(
                'option',
                'redirect',
                window.location.href.replace(
                        /\/[^\/]*$/,
                        '${rootPath}/jslib/flatlab/assets/file-uploader/cors/result.html?%s'
                )
        );


        // Load existing files:
        $('#fileupload').fileupload('option', 'done').call($('#fileupload'), $.Event('done'), {result: ${jsonInitImage}});

    });

    /**这是一个坑： 由于图片上传采用了Javascript-Template技术，所以导致每次增加行的时候，
     * 索引i不会全局自增，所以为了传递多对象到后台strut2.0，采用wrap方式
     */
    jQuery("form").on("submit", function () {
        jQuery("input[name='image.fileName']").each(function (i, ref) {
            jQuery(ref).attr('name', 'image[' + i + '].fileName');
        });
        jQuery("input[name='image.name']").each(function (i, ref) {
            jQuery(ref).attr('name', 'image[' + i + '].name');
        });
        jQuery("input[name='image.size']").each(function (i, ref) {
            jQuery(ref).attr('name', 'image[' + i + '].size');
        });
    });

</script>

