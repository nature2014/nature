<%--
  Created by IntelliJ IDEA.
  User: wangronghua
  Date: 15/3/24
  Time: 下午9:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,user-scalable=no, initial-scale=1">
    <title>点击进入霞珍集团官网</title>
    <!-- 模板公用样式 -->
    <link rel="stylesheet" type="text/css" href="http://fanersai.wxshidai.com/static/assets/css/wsdTpl.css?_=201402180" /><!-- 模板公用样式 -->
    <!-- 模板私有样式 -->
    <link rel="stylesheet" type="text/css" href="http://fanersai.wxshidai.com/static/css/home/t22.css" /><!-- /模板私有样式 -->
    <!-- 模板自定义样式 -->
    <link rel="stylesheet" type="text/css" href="/tplcss" /><!-- /模板自定义样式 -->
    <!-- webJQ -->
    <script type="text/javascript" src="http://fanersai.wxshidai.com/static/assets/common/jmquery.js" charset="utf-8"></script><!-- /webJQ -->
    <!-- 公用JS -->
    <script type="text/javascript" src="http://fanersai.wxshidai.com/static/js/public.js" charset="utf-8"></script><!-- /公用JS -->
    <!-- 我的加载器 -->
    <script type="text/javascript" src="http://fanersai.wxshidai.com/static/assets/somjs/som-min.js" charset="utf-8"></script><!-- /我的加载器 -->
</head>
<body class="wsd_type_home wsd_tpl_t22" wsd-title="点击进入霞珍集团官网" wsd-icon="http://img.wxshidai.com/static/uploads/fanersai/image/image_52bb959d040ca120540276.jpg" wsd-link="http://fanersai.wxshidai.com/index?wxsd=mp.weixin.qq.com" wsd-desc="        凡尔赛建材馆是由宣威傲龙投资建设，主要用于建材家居销售。经营品类涵盖陶瓷、卫浴、洁具、灯具、电器、木地板、家居、窗帘、橱柜、集成吊顶、窗帘、壁纸等，真正打造了方便消费者一站式采购的大型建材家居服务中心。">
<!-- 音乐控件 -->
<style type="text/css">
    .music_play{ width:40px; height:30px; position:absolute; top:20px; left:15px; z-index:9999; background:url(http://img.wxshidai.com/static/img/playvoice.png) no-repeat; background-size:100%;  background-position: 0 0 ; }
    .posi{ background-position: 0 -36px;}
</style>
<div class="music_play" id="music_play">
    <audio  preload="auto" id="musicBoxtwo" src="http://img.wxshidai.com/static/uploads/fanersai/voice/voice_52bbf210a949b476214841.mp3" loop autoplay></audio>
</div>
<script type="text/javascript">
    var flag = true;
    var music = document.getElementById("musicBoxtwo");

    (function fBrowserRedirect(){
        var sUserAgent = navigator.userAgent.toLowerCase();
        var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
        var bIsAndroid = sUserAgent.match(/android/i) == "android";
        if(bIsAndroid){
            $('#music_play').addClass('posi');
            flag = false;
        }
    })();

    $('#music_play').click(function(){
        if(flag==true){
            music.play();
            $('#music_play').addClass('posi');
            flag = false;
        }else{
            $('#music_play').removeClass('posi');
            music.pause();
            flag = true;
        }
    })
</script>
<!-- /音乐控件 -->
<!-- 模板布局 -->
<div class="wsd_page">
    <!-- 模板主体内容 -->
    <!--banner幻灯片-->
    <div class="wsdbannerbox">
        <div class="wsdbanner" id="wsdBanner" data-img-list="" style="height: 500px;">
            <div class="ui-txt">
                <p class="fn-hide" data-img-list="http://www.ahxz.com.cn/UpLoadFile/Ad/20140718153735862.jpg"  data-img-link="">霞珍集团</p>
                <p class="fn-hide" data-img-list="http://www.ahxz.com.cn/UpLoadFile/Ad/20140718153806580.jpg"  data-img-link="">霞珍集团</p>
                <p class="fn-hide" data-img-list="http://www.ahxz.com.cn/UpLoadFile/Ad/20140913150553448.jpg"  data-img-link="">霞珍集团</p>
            </div>
            <div class="ui-btn"></div>
        </div>
    </div>
    <script type="text/javascript">
        "use strict";
        som.use(['module_phone/wsd3dBanenr.js'], function(wsd3dBanenr){
            wsd3dBanenr();
        })
    </script>
    <div class="weishidai-content tpl-catelist" id="wsdBodyer" style="overflow:hidden;">
        <div class="tpl-catelist-item">
            <a href="http://www.cyznj.com/wechat/serviceDescription.action" class="weishidai_item tpl-catelist-item-body c0">
                <div class="weishidai_img">
                    <img src="http://www.ahxz.com.cn/images/Logo.png" class="tpl-cate-icon" />
                </div>
                <div class="weishidai_text">
                    <span class="weishidai_title tpl-cate-title">关于霞珍</span>
                    <span class="weishidai_info tpl-cate-summary">安徽霞珍集团</span>
                </div>
            </a>
            <div class="shadow"></div>
        </div><div class="tpl-catelist-item">
        <a href="http://www.cyznj.com/wechat/productList.action" class="weishidai_item tpl-catelist-item-body c1">
            <div class="weishidai_img">
                <img src="http://img.wxshidai.com/static/uploads/fanersai/image/image_52bbdce701046067416082.jpg" class="tpl-cate-icon" />
            </div>
            <div class="weishidai_text">
                <span class="weishidai_title tpl-cate-title">产品介绍</span>
                <span class="weishidai_info tpl-cate-summary">以品质为根本，以口碑为追求</span>
            </div>
        </a>
        <div class="shadow"></div>
    </div>
    <%--<div class="tpl-catelist-item">--%>
        <%--<a href="http://xiazhenjf.tmall.com/" class="weishidai_item tpl-catelist-item-body c2">--%>
            <%--<div class="weishidai_img">--%>
                <%--<img src="http://img.wxshidai.com/static/uploads/fanersai/image/image_52c0db200937a866855758.jpg" class="tpl-cate-icon" />--%>
            <%--</div>--%>
            <%--<div class="weishidai_text">--%>
                <%--<span class="weishidai_title tpl-cate-title">优惠活动</span>--%>
                <%--<span class="weishidai_info tpl-cate-summary">优惠活动</span>--%>
            <%--</div>--%>
        <%--</a>--%>
        <%--<div class="shadow"></div>--%>
    <%--</div>--%>
    <%--<div class="tpl-catelist-item">--%>
        <%--<a href="#" class="weishidai_item tpl-catelist-item-body c3">--%>
            <%--<div class="weishidai_img">--%>
                <%--<img src="http://img.wxshidai.com/static/uploads/fanersai/image/image_534ceb2ef15e7552853080.jpg" class="tpl-cate-icon" />--%>
            <%--</div>--%>
            <%--<div class="weishidai_text">--%>
                <%--<span class="weishidai_title tpl-cate-title">服务项目</span>--%>
                <%--<span class="weishidai_info tpl-cate-summary">服务项目</span>--%>
            <%--</div>--%>
        <%--</a>--%>
        <%--<div class="shadow"></div>--%>
    <%--</div>--%>
    <div class="tpl-catelist-item">
        <a href="http://www.cyznj.com/pages/wechat/contact.jsp" class="weishidai_item tpl-catelist-item-body c4">
            <div class="weishidai_img">
                <img src="http://img.wxshidai.com/static/uploads/fanersai/image/image_52bbdbb8b171a042007120.jpg" class="tpl-cate-icon" />
            </div>
            <div class="weishidai_text">
                <span class="weishidai_title tpl-cate-title">联系我们</span>
                <span class="weishidai_info tpl-cate-summary">我们的联系方式</span>
            </div>
        </a>
        <div class="shadow"></div>
    </div></div><!-- /模板主体内容 -->
    <!-- 底部 -->
    <div class="wsd-footer" id="wsdFooter">
        <a class="ui-support fn-clear" href="">
            <span class="fn-left" data-href="" style="width:40%"  >技术支持:cyznj</span>
            <span class="fn-right" data-href="http://fanersai.wxshidai.com" style="width:60%;text-align:right;"  >©2014-2015cyznj版权所有</span>
        </a>
    </div>
    <script type="text/javascript">
        /** 计算wsdBodyer的高度，让底部对其 */
        "use strict";
        som.use(['jmquery', 'tool'], function($, tool){
            var wsdBanner = $('#wsdBanner'),
                    wsdBodyer = $('#wsdBodyer'),
                    wsdFooter = $('#wsdFooter'),
                    wsdLogo = $('#wsdLogo'),
                    mainNav = $('#mainNav'),
                    toolWin = tool.win,
                    winHeight = toolWin.innerHeight(),
                    winWidth = toolWin.innerWidth(),
                    height = 0;
            //logo
            if(wsdLogo.length){
                height += wsdLogo.height();
            }
            if(mainNav.length){
                height += mainNav.innerHeight();
            }
            if(wsdBanner.length){
                height += winWidth/2;
            }
            //footer
            if(wsdFooter.length){
                height += wsdFooter.height();
            }
            wsdBodyer.css({
                'min-height': winHeight - height + 'px',
                'opacity': 1
            });
            wsdFooter.css({
                'opacity': 1
            });
            //版权、技术支持的URL
            wsdFooter.find('span').click(function(){
                $(this).parent('a').attr('href',$(this).data('href'));
            });
        })
    </script>
    <!-- /底部 -->
</div><!-- /模板布局 -->
<!-- 快捷菜单 -->
<!-- 微时代快捷菜单 -->
<!-- /快捷菜单 -->
<!-- 首页动画 -->
<script type="text/javascript">
    som.use(['module_phone/phineFlower.js', 'tool'], function(phineFlower, tool){
        phineFlower(10, 'http://img.wxshidai.com/static/img/bg_animation/lh.png');
    });
</script>
<!-- /首页动画 -->
</body>
</html>
