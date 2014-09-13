<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pages/frontHeader.jsp"%>
<title>首页</title>
<style>
    a img{border:none}
</style>
</head>
<body>
	<div class="home">
		<div class="leftimg">
			<img src='${rootPath}/img/nature.png' width="400" height="300" />
		</div>
        <a href='login.action'>
		<div class="nav-banner1">
			<div class="nav-img">
				<img src="${rootPath}/img/contacts.png" width="100" height="100" />
			</div>
			<div class="nav-font">我要登陆</div>
		</div></a>
        <a href='register.action'>
		<div class="nav-banner2">
				<div class="nav-img">
					<img src="${rootPath}/img/sign_up.png" width="100" height="100" />
				</div>
				<div class="nav-font">我要注册</div>
		</div></a>
	</div>
</body>
</html>
