<!DOCTYPE html>
    <style type="text/css">
        #webcam, #canvas {
            border: 20px solid #333;
            background: #eee;
            -webkit-border-radius: 20px;
            -moz-border-radius: 20px;
            border-radius: 20px;
        }
        #bar{
            position: relative;
        }

        #webcam {
            position: relative;
            margin-top: 50px;
            margin-bottom: 50px;
        }

        #webcam > img {
            z-index: 1;
            position: absolute;
            border: 0px none;
            padding: 0px;
            bottom: -40px;
            left: 89px;
        }

        #webcam > img {
            border: 0px none;
        }
        .icon.icon-camera{
            border: 4px solid #1019c8;
            position: absolute;
            left: 150px;
            top: 320px;
            width:25px;
            height:25px;
            -webkit-border-radius: 8px;
            -moz-border-radius: 8px;
            border-radius: 8px;
            cursor: pointer;
            z-index:100;
            background: #15ceff;
        }
        #canvas {
            border: 20px solid #ccc;
            background: #eee;
        }

        #flash {
            position: absolute;
            top: 0px;
            left: 0px;
            z-index: 5000;
            width: 100%;
            height: 100%;
            background-color: #fff404;
            display: none;
        }

        object {
            display: block; /* HTML5 fix */
            position: relative;
            z-index: 1;
        }

    </style>
    <script src="${rootPath}/jslib/jquery-webcam-master/jquery.webcam.js"></script>

<div id="bar">
    <div id="webcam" style="display: inline-block">
    </div>

    <div class="icon icon-camera" onclick="javascript:webcam.capture();">
    </div>
    <div style="display: none">
        <canvas id="canvas" height="240" width="320"></canvas>
    </div>
    <div id="flash"></div>

    <script type="text/javascript">

        var pos = 0;
        var ctx = null;
        var image = null;

        var filter_on = false;
        var filter_id = 0;

        function changeFilter() {
            if (filter_on) {
                filter_id = (filter_id + 1) & 7;
            }
        }

        function toggleFilter(obj) {
            if (filter_on = !filter_on) {
                obj.parentNode.style.borderColor = "#c00";
            } else {
                obj.parentNode.style.borderColor = "#333";
            }
        }

        jQuery("#webcam").webcam({
            width: 320,
            height: 240,
            mode: "callback",
            swffile: "${rootPath}/jslib/jquery-webcam-master/jscam_canvas_only.swf",

            onTick: function (remain) {

                if (0 == remain) {
                    jQuery("#status").text("Cheese!");
                } else {
                    jQuery("#status").text(remain + " seconds remaining...");
                }
            },

            onSave: function (data) {

                var col = data.split(";");
                var img = image;

                if (false == filter_on) {

                    for (var i = 0; i < 320; i++) {
                        var tmp = parseInt(col[i]);
                        img.data[pos + 0] = (tmp >> 16) & 0xff;
                        img.data[pos + 1] = (tmp >> 8) & 0xff;
                        img.data[pos + 2] = tmp & 0xff;
                        img.data[pos + 3] = 0xff;
                        pos += 4;
                    }

                } else {

                    var id = filter_id;
                    var r, g, b;
                    var r1 = Math.floor(Math.random() * 255);
                    var r2 = Math.floor(Math.random() * 255);
                    var r3 = Math.floor(Math.random() * 255);

                    for (var i = 0; i < 320; i++) {
                        var tmp = parseInt(col[i]);

                        /* Copied some xcolor methods here to be faster than calling all methods inside of xcolor and to not serve complete library with every req */

                        if (id == 0) {
                            r = (tmp >> 16) & 0xff;
                            g = 0xff;
                            b = 0xff;
                        } else if (id == 1) {
                            r = 0xff;
                            g = (tmp >> 8) & 0xff;
                            b = 0xff;
                        } else if (id == 2) {
                            r = 0xff;
                            g = 0xff;
                            b = tmp & 0xff;
                        } else if (id == 3) {
                            r = 0xff ^ ((tmp >> 16) & 0xff);
                            g = 0xff ^ ((tmp >> 8) & 0xff);
                            b = 0xff ^ (tmp & 0xff);
                        } else if (id == 4) {

                            r = (tmp >> 16) & 0xff;
                            g = (tmp >> 8) & 0xff;
                            b = tmp & 0xff;
                            var v = Math.min(Math.floor(.35 + 13 * (r + g + b) / 60), 255);
                            r = v;
                            g = v;
                            b = v;
                        } else if (id == 5) {
                            r = (tmp >> 16) & 0xff;
                            g = (tmp >> 8) & 0xff;
                            b = tmp & 0xff;
                            if ((r += 32) < 0) r = 0;
                            if ((g += 32) < 0) g = 0;
                            if ((b += 32) < 0) b = 0;
                        } else if (id == 6) {
                            r = (tmp >> 16) & 0xff;
                            g = (tmp >> 8) & 0xff;
                            b = tmp & 0xff;
                            if ((r -= 32) < 0) r = 0;
                            if ((g -= 32) < 0) g = 0;
                            if ((b -= 32) < 0) b = 0;
                        } else if (id == 7) {
                            r = (tmp >> 16) & 0xff;
                            g = (tmp >> 8) & 0xff;
                            b = tmp & 0xff;
                            r = Math.floor(r / 255 * r1);
                            g = Math.floor(g / 255 * r2);
                            b = Math.floor(b / 255 * r3);
                        }

                        img.data[pos + 0] = r;
                        img.data[pos + 1] = g;
                        img.data[pos + 2] = b;
                        img.data[pos + 3] = 0xff;
                        pos += 4;
                    }
                }

                if (pos >= 0x4B000) {
                    ctx.putImageData(img, 0, 0);
                    var canvas = document.getElementById("canvas");
                    jQuery.ajax({
                        async:false,
                        type: "POST",
                        url: "${rootPath}/personpicture/uploadpngdata.action",
                        cache: false,
                        data: {image:canvas.toDataURL("image/png"),code:window.figureNumber},
                        success : function(data, status) {
                             jQuery("#personicon").attr("src",data+"?time="+new Date().getTime());
                             jQuery("#iconpath").val(data+"?time="+new Date().getTime());
                        },
                        error: function(){

                        }
                    });
                    pos = 0;
                }
            },

            onCapture: function () {

                jQuery("#flash").css("display", "block");
                jQuery("#flash").fadeOut("fast", function () {
                    jQuery("#flash").css("opacity", 1);
                });
                webcam.save();
            },

            debug: function (type, string) {
                jQuery("#status").html(type + ": " + string);
            },

            onLoad: function () {

                var cams = webcam.getCameraList();
                for (var i in cams) {
                    jQuery("#cameraId").append("<li>" + cams[i] + "</li>");
                }
            }
        });

        jQuery(document).ready(function () {

            var canvas = document.getElementById("canvas");

            if (canvas.getContext) {
                ctx = document.getElementById("canvas").getContext("2d");
                ctx.clearRect(0, 0, 320, 240);

                var img = new Image();
                img.src = "/image/logo.gif";
                img.onload = function () {
                    ctx.drawImage(img, 129, 89);
                }
                image = ctx.getImageData(0, 0, 320, 240);
            }

        });

    </script>
</div>
