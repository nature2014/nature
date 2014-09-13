<%@ page import="util.ServerContext" %>
<%@ page import="wechat.utils.URLManager" %>
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

<%
    String url = ServerContext.getDomainName() + "/wechat/userBinding.action";
    String redirectUrl = URLManager.getUrl_OAuthRedirect(url, ServerContext.getAppID(), "snsapi_userinfo");
%>
<section class="panel">
    <div class="panel-body">
        <label class="col-xs-12  control-label">还未绑定微信账号，<a href="<%=redirectUrl%>">点击此处</a>进行绑定！</label>
        <button type="button" class="btn btn-info btn-block" onclick="custom_close()">点击此处，返回微信！</button>
    </div>
</section>
</body>
</html>
