<!DOCTYPE html>

<html lang="en">
<head>
    <%@ include file="../commonHeader.jsp" %>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="大自然信息管理系统">
    <meta name="author" content="大自然信息管理系统">
    <meta name="keyword" content="大自然信息管理系统">
    <link rel="shortcut icon" href="img/favicon.png">
    <title>大自然信息管理系统--用户登录</title>

    <!-- Bootstrap core CSS -->
    <link href="${rootPath}/jslib/flatlab/css/bootstrap.min.css" rel="stylesheet">
    <link href="${rootPath}/jslib/flatlab/css/bootstrap-reset.css" rel="stylesheet">
    <!--external css-->
    <link href="${rootPath}/jslib/flatlab/assets/font-awesome/css/font-awesome.css" rel="stylesheet"/>
    <!-- Custom styles for this template -->
    <link href="${rootPath}/jslib/flatlab/css/style.css" rel="stylesheet">
    <link href="${rootPath}/jslib/flatlab/css/style-responsive.css" rel="stylesheet"/>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
    <!--[if lt IE 9]>
    <script src="${rootPath}/jslib/flatlab/js/html5shiv.js"></script>
    <script src="${rootPath}/jslib/flatlab/js/respond.min.js"></script>
    <![endif]-->
</head>

<body class="login-body">

<div class="container">

    <form class="form-signin" action="${rootPath}/login.action" method="post">
        <h2 class="form-signin-heading">大自然信息管理系统</h2>

        <div class="login-wrap">
            <input type="text" name="user.name" class="form-control" placeholder="用户名" autofocus>
            <input type="password" name="user.password" class="form-control" placeholder="密码">
            <button class="btn btn-lg btn-login btn-block" type="submit">登录</button>
        </div>
    </form>

</div>


<!-- js placed at the end of the document so the pages load faster -->
<script src="${rootPath}/jslib/flatlab/js/jquery.js"></script>
<script src="${rootPath}/jslib/flatlab/js/bootstrap.min.js"></script>


</body>
</html>
