<!DOCTYPE html>
<%@ include file="../commonHeader.jsp"%>
<%--
  User: peter
  Date: 14-3-18
  Time: 下午11:02
--%>
<html>
<head>
    <title>参数设定</title>
</head>
<body>
<!--main content start-->
<section class="panel">
    <ul class="breadcrumb">
        <li>系统管理</li>
        <li class="active">参数设定</li>
    </ul>
    <div class="panel-body">
    <form role="form" method="post" class="form-horizontal tasi-form" action="${rootPath}/backend/systemsettingsave.action">
        <%@ include file="../strutsMessage.jsp"%>
        <input type="hidden" name="systemSetting.id" value="${systemSetting.id}"/>
        <%--<div class="form-group has-success">--%>
            <%--<label class="col-lg-2 control-label">测试模式</label>--%>
            <%--<div class="col-lg-10">--%>
                <%--<input name="systemSetting.maptoken" type="text" class="form-control" value="${systemSetting.maptoken}"/>--%>
            <%--</div>--%>
        <%--</div>--%>
        <div class="form-group has-success">
            <label class="control-label col-lg-2">缺省密码</label>
            <div class="col-lg-10">
                <input name="systemSetting.defaultPassword" type="text" class="form-control" value="${systemSetting.defaultPassword}"/>
            </div>
        </div>
        <div class="form-group has-success">
            <label class="col-lg-2 control-label">百度地图序列号</label>
            <div class="col-lg-10">
                <input name="systemSetting.maptoken" type="text" class="form-control" value="${systemSetting.maptoken}"/>
            </div>
        </div>

        <div class="form-group has-success">
            <label class="control-label col-lg-2">默认城市</label>
            <div class="col-lg-10">
                <input name="systemSetting.city" type="text" class="form-control" value="${systemSetting.city}"/>
            </div>
        </div>
        <div class="form-group has-success">
            <label class="control-label col-lg-2">微信配置</label>
            <div class="col-lg-10">
                <div class="row">
                    <label class="control-label col-lg-2">AppID</label>
                    <div class="col-lg-4">
                        <input name="systemSetting.appID" type="text" class="form-control" placeholder="请输入AppID" value="${systemSetting.appID}"/>
                    </div>
                    <label class="control-label col-lg-2">AppSecret</label>
                    <div class="col-lg-4">
                        <input name="systemSetting.appsecret" type="text" class="form-control" placeholder="请输入AppSecret" value="${systemSetting.appsecret}"/>
                    </div>
                </div>
                <div class="row" style="margin-top: 10px;">
                    <label class="control-label col-lg-2">AppToken</label>
                    <div class="col-lg-4">
                        <input name="systemSetting.apptoken" type="text" class="form-control" placeholder="请输入AppToken" value="${systemSetting.apptoken}"/>
                    </div>
                    <label class="control-label col-lg-2">OAuth域名</label>
                    <div class="col-lg-4">
                        <input name="systemSetting.domainname" type="text" class="form-control" placeholder="请输入Oauth域名" value="${systemSetting.domainname}"/>
                    </div>
                </div>
                <div class="row" style="margin-top: 10px;">
                    <div class="col-lg-4">
                        <button id="pushButton" type="button" class="btn btn-info" onclick="pushMenu()">发送菜单</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="form-group has-success">
            <label class="control-label col-lg-2">欢迎词</label>
            <div class="col-lg-10">
                <input name="systemSetting.welcomeMsg" type="text" class="form-control" value="${systemSetting.welcomeMsg}"/>
            </div>
        </div>

        <div class="form-group has-success">
            <label class="control-label col-lg-2">服务介绍</label>
            <div class="col-lg-10">
                <textarea class="form-control" name="systemSetting.introduction" rows="20">${systemSetting.introduction}</textarea>
            </div>
        </div>

        <div class="form-group">
            <div class="col-lg-offset-2 col-lg-10">
                <button class="btn btn-info" type="submit">保存</button>
            </div>
        </div>
    </form>
    </div>
</section>
<script type="text/javascript" src="${rootPath}/jslib/flatlab/assets/ckeditor/ckeditor.js"></script>
<script>
    jQuery(document).ready(function()
    {
        CKEDITOR.replace( 'systemSetting.introduction',{
            language:'zh-cn',//简体中文
            width : "100%", //宽度
            height:400,  //高度
            toolbar ://工具栏设置
                    [
                        ['Maximize','-','Save','NewPage','Preview','-','Templates'],
                        ['Cut','Copy','Paste','PasteText','PasteFromWord'],
                        ['Undo','Redo','-','Find','Replace','-',,'Table','HorizontalRule','-','SelectAll','RemoveFormat'],
                        ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
                        ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
                        ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
                        ['Link','Unlink','Anchor'],
                        ['Image','Flash','Smiley','SpecialChar','PageBreak'],
                        ['Styles','Format','Font','FontSize'],
                        ['TextColor','BGColor']
                    ]
        });
    });

    function pushMenu() {
        $("#pushButton").attr("disabled",true)
        jQuery.ajax({
            type: "POST",
            async:false,
            url:"backend/pushMenu.action",
            cache: false,
            complete: function(data) {
                alert(data.responseText);
            }
        });
        $("#pushButton").attr("disabled",false)
    }
</script>
</body>
</html>
