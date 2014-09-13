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

    <s:include value="../strutsMessage.jsp"/>

    <div class="panel-body">
        <form action="wechat/checkOutSubmit.action" method="post" class="form-horizontal">
            <input type="hidden" name="openID" value="<s:property value='openID'/>">
            <input type="hidden" name="userID" value="<s:property value='userID'/>">

            <button type="submit" class="btn btn-info btn-block">我要签出</button>
            <button type="button" class="btn btn-info btn-block" onclick="custom_close()">返回微信</button>
        </form>
    </div>
</section>
</body>
</html>
