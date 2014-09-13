<%--
  User: peter
  Date: 14-3-19
  Time: 下午9:32
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
        <button type="button" class="btn btn-info btn-block" onclick="custom_close()">点击此处，返回微信！</button>
    </div>

    <div style="width:100%;height:100%;;padding:15px;color:green;font:14px">
     <p>
         此功能正在开发中，敬请等待！
     </p>
    </div>
</section>
</body>
</html>
