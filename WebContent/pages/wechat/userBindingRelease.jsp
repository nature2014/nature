<%--
  Created by IntelliJ IDEA.
  User: wangronghua
  Date: 14-3-30
  Time: 上午9:32
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

        function submitForm() {
            $("#userBindingForm").submit();
        }
    </script>
</head>
<body>

<section class="panel">
    <div class="panel-body">
        <form id="userBindingForm" action="wechat/userBindingRelease.action" method="post" class="form-horizontal">
            <input type="hidden" name="openID" value="<s:property value='openID'/>">
            <input type="hidden" name="wechatUser" value="<s:property value='wechatUser'/>">
            <label class="col-xs-12  control-label">您的微信账号已绑定用户(<s:property value='volunteer.name'/>)，
                <a href="javascript:void(0)" onclick="submitForm()">点击此处</a>解除绑定后继续！</label>
            <button type="button" class="btn btn-info btn-block" onclick="custom_close()">点击此处，返回微信！</button>
        </form>
    </div>
</section>
</body>
</html>