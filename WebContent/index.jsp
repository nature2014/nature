<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<%@ include file="/pages/bootstrapHeader.jsp" %>
<head><title>大自然信息管理系统</title></head>
<body>
<h4 align="center">欢迎使用大自然信息管理系统</h4>

<div style="margin: 0px auto; width: 980px;">
    <div><img src="${rootPath}/img/nature.png" style="width:980px;height:280px"></div>
    <div style="margin: 0px auto; width: 200px;">
        <a href="${rootPath}/index.action">
            <button type="button" class="btn btn-round btn-success">前台管理</button>
        </a>
        <a href="${rootPath}/backend/login.action">
            <button type="button" class="btn btn-round btn-success">后台管理</button>
        </a>
    </div>
</div>
</body>
</html>
