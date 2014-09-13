<%--
  User: peter
  Date: 14-3-19
  Time: 下午9:32
--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/pages/miniwechatHeader.jsp" %>
    <script type="text/javascript" src="js/checkUtil.js"></script>
    <title>员工服务微信平台</title>


    <script type="text/javascript">

        function custom_close(){
            WeixinJSBridge.call('closeWindow');
        }
    </script>
</head>
<body>

<section class="panel">

    <div class="panel-body">
        <button type="button" class="btn btn-info btn-block" onclick="custom_close()">点击此处，返回微信！</button>

    <form action="wechat/volunteerRecruitSave.action" method="post" class="form-horizontal">
        <%@include file="../strutsMessage.jsp"%>
        <br>
        <input type="hidden" name="register.openID" value="${openID}">
        <div class="form-group">
            <label class="col-xs-4  control-label" >姓名</label>
            <div class="col-xs-8">
                <p class="form-control-static"><input class="form-control" type="text" name="register.name" value=""/></p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4  control-label" >性别</label>
            <div class="col-xs-8" data-role="input-control">
                <label class="inline-block">
                    <input type="radio" name="register.sex" value="1"/>
                    男
                </label>
                <label class="inline-block">
                    <input type="radio" name="register.sex" value="2"/>
                    女
                </label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4  control-label" >证件号</label>
            <div class="col-xs-8">
                <p class="form-control-static"><s:select id="identityType" list="#{'-1':'其他','0':'身份证','1':'护照','2':'港澳台'}" listKey="key" listValue="value" name="register.identityType" value="%{register.identityType}"/></p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-4  control-label" >证件号</label>
            <div class="col-xs-8">
                <p class="form-control-static"><input class="form-control" type="text" name="register.identityCard" value=""/></p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4  control-label" >手机号码</label>
            <div class="col-xs-8">
                <p class="form-control-static"><input class="form-control" type="text" name="register.cellPhone" value=""/></p>
            </div>
        </div>
        <script>
            $(document).ready(function() {
                $("form").validate({
                    rules: {
                        "register.name": {
                            required:true
                        },
                        "register.identityCard":{
                            required:true,
                            idCardNo:true
                        },
                        'volunteer.cellPhone':{
                            required:true,
                            cellPhone:true
                        }
                    },
                    messages: {
                        'register.name': {
                            required: "请输入姓名"
                        },
                        'register.identityCard': {
                            required: "请输入证件号",
                            idCardNo: "请输入正确的证件号"
                        },
                        'volunteer.cellPhone': {
                            required: "请输入手机",
                            cellPhone: "请输入正确的手机号, 例如：13912332122"
                        }
                    }
                });
            });
        </script>
         <button type="submit" class="btn btn-info btn-block">提交申请</button>
     </form>
    </div>
</section>
</body>
</html>
