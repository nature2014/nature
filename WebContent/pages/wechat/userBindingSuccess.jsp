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
        <div class="panel-body">
            <button type="button" class="btn btn-info btn-block" onclick="custom_close()">绑定成功，返回微信！</button>
        </div>
    </section>
</body>
</html>
