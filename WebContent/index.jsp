<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- js placed at the end of the document so the pages load faster -->
    <script src="${rootPath}/jslib/flatlab/js/jquery.js"></script>
    <link href="${rootPath}/jslib/flatlab/assets/fancybox/source/jquery.fancybox.css" rel="stylesheet"/>
    <script src="${rootPath}/jslib/flatlab/assets/fancybox/source/jquery.fancybox.js"></script>
    <title>桐城市大自然艺术广告有限责任公司</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link href="./css/styleindex.css" rel="stylesheet" type="text/css" media="all"/>

</head>
<body class="nature">
<!--start header-->
<div class="h_bg">
    <div class="wrap">
        <div class="wrapper">
            <div class="header">
                <div class="logo">
                    <a href="{rootPath}/index.jsp"><img src="${rootPath}/img/logo.png"/> </a>
                </div>
                <div class="cssmenu">
                    <ul>
                        <li class="active" style="min-width:100px"><a href="${rootPath}/index.action"><span>前台管理</span></a></li>

                        <li class="last" style="min-width:100px"><a href="${rootPath}/backend/login.action"><span>后台管理</span></a></li>
                        <div class="clear"></div>
                    </ul>
                </div>
                <div class="clear"></div>
            </div>
        </div>
    </div>
</div>

<!-- start slider -->
<div class="slider_bg">
    <div class="wrap">
        <div class="wrapper">
            <div class="slider">
                <!-- #分类 -->
                <div class="fluid_container">
                    <div class="camera_wrap camera_azure_skin">
                        <div class="container"><!-- start container -->
                            <div>
                                <img src="${rootPath}/img/nature.png" style="width:980px;height:280px">
                                <div style="position: absolute; top:5px; right: 5px; z-index: 1000;">
                                    <img src="${rootPath}/img/wechat_account.jpg" style="width:100px;height:100px">
                                    <p style="margin-left: 12px;">微信关注我</p>
                                </div>
                            </div>
                            <ul id="filters" class="clearfix">
                            </ul>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
                <!-- end #分类-->
                <div class="clear"></div>
            </div>
        </div>
    </div>
</div>
<!-- start content -->
<div class="content_bg">
    <div class="wrap">
        <div class="wrapper">
            <div class="main" id="productgallery">
            </div>
            <div class="clear"></div>
        </div>
    </div>
</div>
</div>
<!-- start footer -->
<div class="footer_bg">
    <div class="wrap">
        <div class="wrapper">
            <div class="footer">
                <div class="copy">
                    <p class="w3-link">© weicuizi| copyright by&nbsp; <a href="http://www.ahdzrgg.com"> 大自然广告</a></p>
                </div>

                <div class="clear"></div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

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
                    var imageCounter = 0;
                    for (var j = 0; j < data.length; j++) {
                        var imageArray = data[j].image;
                        for (var i = 0; i < imageArray.length; i++) {
                            if (imageArray[i].fileType == "image") {
                                var imageUrl = '${rootPath}/upload/getImage.action?getfile=' + imageArray[i].fileName;
                                var imageThumb = '${rootPath}/upload/getImage.action?targetSize=380&getthumb=' + imageArray[i].fileName;
                                if (imageCounter % 3 == 0) {
                                    image.push('<div class="grids_1_of_3">');
                                }
                                image.push('<div class="grid_1_of_3 images_1_of_3 bg">');
                                image.push('<h3>' + data[j].name + '</h3>');
                                image.push('<a class="imagelink fancybox" rel="group" href="' + imageUrl + '" title="' + data[j].name + '">');
                                image.push('<img class="imagethumb" src="' + imageThumb + '"/>');
                                image.push('</a>');
                                image.push('<div class="plus_btn">');
                                image.push('<span class="imagecavas">');
                                if (data[j].volunteerBean == null) {
                                    image.push('<img class="imagedesigner" src="${rootPath}/img/starheader.png"/>');
                                    image.push('<div class="imagedesignerback">');
                                    image.push('<div style="margin-top:20px">【公司艺术作品】</div>');
                                    image.push('<div></div></div>');
                                } else {
                                    image.push('<img class="imagedesigner" src="' + data[j].volunteerBean.iconpath + '"/>');
                                    image.push('<div class="imagedesignerback">');
                                    image.push('<div style="margin-top:20px">【设计师】:' + data[j].volunteerBean.name + '</div></div>');
                                }
                                image.push('</span>');
                                image.push('</div><div class="clear"></div>');
                                image.push('<p class="para">');
                                image.push('<span class="imageprice">【参考价格】¥' + data[j].price + '</span>');
                                if (data[j].modifyTime != null) {
                                    image.push('【发布时间】' + formatDateTime(data[j].modifyTime.time));
                                } else if (data[j].createTime != null) {
                                    image.push('【发布时间】' + formatDateTime(data[j].createTime.time));
                                }
                                image.push('<br>');
                                image.push('【摘要】' + data[j].summary);
                                image.push('</p>');
                                image.push('</div>');
                                if (imageCounter % 3 == 2) {
                                    image.push('<div class="clear"></div>');
                                    image.push('</div>');
                                }
                                imageCounter++;
                            }
                        }
                    }

                    document.getElementById('productgallery').innerHTML = image.join("");
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
                level.push('<li><span class="filter active" data-filter="app card icon web">&nbsp;&nbsp;全&nbsp;&nbsp;&nbsp;&nbsp;部&nbsp;&nbsp;</span></li>');
                for (var j = 0; j < data.length; j++) {
                    level.push('<li><span class="filter" data-filter="logo web" data-level="' + data[j].id + '">' + data[j].name + '</span></li>');
                }
                $('#filters').html(level.join(""));

                $(".filter").on("click", function () {
                    $(".filter").removeClass('active');
                    $(this).addClass('active');
                    //刷新产品数据
                    ajaxProduct($(this).attr('data-level'));
                });
            }
        }
    });
</script>