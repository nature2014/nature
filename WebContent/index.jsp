<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<%@ include file="/pages/bootstrapHeader.jsp" %>
<link href="${rootPath}/jslib/flatlab/assets/fancybox/source/jquery.fancybox.css" rel="stylesheet"/>
<script src="${rootPath}/jslib/flatlab/assets/fancybox/source/jquery.fancybox.js"></script>
<style>
    #productgallery a, span, p {
        font-family: "Microsoft Yahei", "雅黑";
        text-decoration: none;
        color: #666;
        font-size: 14px;
        line-height: 19px;
    }

    .btn.btn-round {
        margin: 2px
    }

    .imagelink {
        width: 308px;
        heigth: 250px;
        float: left;
        margin: 5px;
        border: 2px solid #abe5b7;
        padding: 2px;
        position: relative;
    }

    .imagedesigner {
        left: 220px;
        top: 5px;
        border-radius: 40px;
        -moz-border-radius: 40px;
        width: 80px;
        height: 80px;
        border: 2px solid gray;
    }

    .imagecavas {
        position: absolute;
        left: 220px;
        top: 5px;
        width: 200px;
        height: 200px;
    }

    .imagedesignerback {
        background-color: #8dffe6;
        display: none;
        padding-top: 40px;
        border-radius: 80px;
        -moz-border-radius: 80px;
        width: 160px;
        height: 160px;
        border: 2px solid gray;
    }

    .imagebackrotate {
        position: absolute;
        display: block;
        left: 0px;
        top: 0px;
        z-index: 100;
    }

    .imagethumb {
        width: 300px;
        height: 190px;
        display: block
    }

    .imagetitle {
        display: block;
        font-size: 18px;
        height: 30px;
        font-family: "Microsoft Yahei", "雅黑";;
        font-weight: bold;
    }

    .imageprice {
        display: block;
        color: #fd8e51;
        font-size: 14px
    }

</style>
<head><title>大自然信息管理系统</title></head>
<body>
<h1 align="center" style="color:deepskyblue">欢迎使用大自然信息管理系统</h1>

<div style="margin: 0px auto; width: 980px;position:relative">
    <div><img src="${rootPath}/img/nature.png" style="width:980px;height:280px"></div>
    <div style="margin: 0px auto; width: 200px;position:absolute;left:800px;top:5px">
        <a href="${rootPath}/index.action">
            <button type="button" class="btn btn-round btn-success">前台管理</button>
        </a>
        <a href="${rootPath}/backend/login.action">
            <button type="button" class="btn btn-round btn-success">后台管理</button>
        </a>
    </div>
</div>
<section class="panel" style="padding:10px; margin: 10px auto;width:980px;">
    <header class="panel-heading">
        产品分类
    </header>
    <div id="productlevel" class="panel-body">
    </div>
</section>
<div id="productgallery" style="padding:10px; margin: 10px auto;width:980px;height:100%"></div>
<script>
    // 格式化js时间
    var formatDateTime = function (obj, IsMi) {
        var myDate = new Date(obj);
        var year = myDate.getFullYear();
        var month = ("0" + (myDate.getMonth() + 1)).slice(-2);
        var day = ("0" + myDate.getDate()).slice(-2);
        var h = ("0" + myDate.getHours()).slice(-2);
        var m = ("0" + myDate.getMinutes()).slice(-2);
        var s = ("0" + myDate.getSeconds()).slice(-2);
        var mi = ("00" + myDate.getMilliseconds()).slice(-3);
        if (IsMi == true) {
            return year + "-" + month + "-" + day;
        }
        else {
            return year + "-" + month + "-" + day + " " + h + ":" + m + ":" + s;
        }
    };
    function ajaxProduct(levelId) {
        $.ajax({
            type: "GET",
            url: "${rootPath}/backend/product/queryTable.action",
            data: {"filter['productLevelId']": typeof levelId == 'undefined' ? '' : levelId, "filter['state']": 0},
            dataType: "json",
            success: function (result) {
                var image = [];
                var data = result.aaData;
                if ($.isArray(data)) {
                    //处理图片信息
                    for (var j = 0; j < data.length; j++) {
                        var imageArray = data[j].image;
                        for (var i = 0; i < imageArray.length; i++) {
                            if (imageArray[i].fileType == "image") {
                                var imageUrl = '${rootPath}/upload/getImage.action?getfile=' + imageArray[i].fileName;
                                var imageThumb = '${rootPath}/upload/getImage.action?targetSize=380&getthumb=' + imageArray[i].fileName;
                                image.push('<a class="imagelink fancybox" rel="group" href="' + imageUrl + '" title="' + data[j].name + '">');
                                image.push('<img class="imagethumb" src="' + imageThumb + '"/>');
                                image.push('<span class="imagetitle">【标题】' + data[j].name + '</span>');
                                image.push('<div class="imagecavas">');
                                if (data[j].volunteerBean == null) {
                                    image.push('<img class="imagedesigner" src="${rootPath}/img/starheader.png"/>');
                                    image.push('<div class="imagedesignerback">');
                                    image.push('<div style="margin-top:20px">【公司艺术作品】</div>');
                                    image.push('<div></div></div>');
                                } else {
                                    image.push('<img class="imagedesigner" src="' + data[j].volunteerBean.iconpath + '"/>');
                                    image.push('<div class="imagedesignerback">');
                                    image.push('<div style="margin-top:20px">【设计师】:' + data[j].volunteerBean.name + '</div>');
                                    image.push('<div>【手机】: ' + data[j].volunteerBean.cellPhone + ' </div></div>');
                                }
                                image.push('</div>');
                                if (data[j].modifyTime != null) {
                                    image.push('<span>【发布时间】' + formatDateTime(data[j].modifyTime.time) + '</span>');
                                } else if (data[j].createTime != null) {
                                    image.push('<span>【发布时间】' + formatDateTime(data[j].createTime.time) + '</span>');
                                }
                                image.push('<span class="imageprice">【参考价格】¥' + data[j].price + '</span>');
                                image.push('<br>');
                                image.push('<p class="imagesummary">【摘要】' + data[j].summary + '</p>');

                                image.push('</a>');
                            }
                        }
                    }

                    $('#productgallery').html(image.join(""));
                    //图片快速浮优
                    jQuery(".fancybox").fancybox();

                    $('.imagecavas').hover(
                            function () {
                                $(this).find(".imagedesignerback").addClass('imagebackrotate');
                            },
                            function () {
                                $(this).find(".imagedesignerback").removeClass('imagebackrotate');
                            }
                    );
                }
            }
        });
    }
    ajaxProduct();
    $.ajax({
        type: "GET",
        url: "${rootPath}/backend/productlevel/queryTable.action",
        data: {},
        dataType: "json",
        success: function (result) {
            var level = [];
            var data = result.aaData;
            if ($.isArray(data)) {
                //处理分类信息
                level.push('<button type="button" class="btn btn-round btn-primary" style="margin-left:5px" data-level="">全部</button>');
                for (var j = 0; j < data.length; j++) {
                    level.push('<button type="button" class="btn btn-round btn-default" style="margin-left:5px" data-level="' + data[j].id + '">' + data[j].name + '</button>');
                }
                $('#productlevel').html(level.join(""));

                $(".btn.btn-round").on("click", function () {
                    $(".btn.btn-round").removeClass('btn-primary');
                    $(".btn.btn-round").addClass('btn-default');
                    $(this).addClass('btn-primary');
                    //刷新产品数据
                    ajaxProduct($(this).attr('data-level'));
                });
            }
        }
    });
</script>
</body>
</html>
