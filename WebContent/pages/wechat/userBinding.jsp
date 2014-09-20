<%--
  Created by IntelliJ IDEA.
  User: wangronghua
  Date: 14-3-19
  Time: 下午9:32
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/pages/miniwechatHeader.jsp" %>

    <script type="text/javascript">

        function custom_close(){
            WeixinJSBridge.call('closeWindow');
        }
    </script>
</head>
<body>
    <section class="panel">
        <%-- 消息引用 --%>
        <s:include value="../strutsMessage.jsp"/>

        <div class="panel-body">
            <form action="wechat/userBindingSubmit.action" method="post" class="form-horizontal">
                <input type="hidden" name="openID" value="<s:property value='openID'/>">
                <div class="form-group">
                    <label class="col-xs-4  control-label" for="wechatUser">微信号码</label>
                    <div class="col-xs-8">
                        <input class="form-control" id="wechatUser" name="wechatUser" readonly="true" value="<s:property value='wechatUser'/>">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4  control-label" for="name">姓名</label>
                    <div class="col-xs-8">
                        <input class="form-control" id="name" name="name" value="<s:property value='name'/>">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4  control-label" for="identityCardNumber">证件号</label>
                    <div class="col-xs-8">
                        <input class="form-control" id="identityCardNumber" name="identityCardNumber" value="<s:property value='identityCardNumber'/>">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4  control-label" for="cellPhone">手机号</label>
                    <div class="col-xs-8">
                        <input class="form-control" id="cellPhone" name="cellPhone" value="<s:property value='cellPhone'/>">
                    </div>
                </div>
                <button type="submit" class="btn btn-info btn-block">点击绑定</button>
                <button type="button" class="btn btn-info btn-block" onclick="custom_close()">返回微信</button>
            </form>
        </div>
    </section>
</body>
</html>
