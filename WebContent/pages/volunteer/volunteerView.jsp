<!DOCTYPE html>
<%@ include file="../frontHeader.jsp"%>
<html>
<head>
<title>我的注册</title>

<style type="text/css">
</style>
</head>

<body>
	<div class="home2">
		<div class="bg-user">
			<div class="bg-username">${volunteer.name}</div>
			<div class="bg-touxiang">
				<img src="img/touxiang.png" width="50" height="50" />
			</div>
		</div>

		<div class="bg-regist">
			<div class="regist-banner">
				<div class="regist-home">
					<img src="img/home.png" width="40" height="40" />
				</div>
				<div class="regist-font">当前位置：我的注册</div>
			</div>
			<div class="Information-font">个人信息</div>
			<div class="Information-table">
				<form id="form1" name="form1" method="post" action="">
					<table width="680" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="15%">个人照片：</td>
							<td width="40%"><img src="${volunteer.iconpath}" width="80"
								height="80" /></td>
							<td width="15%">指纹信息：</td>
							<td width="30%"><img src="${volunteer.fingerpath}" width="80"
								height="80" /></td>
						</tr>
						<tr>
							<td>工号：</td>
							<td><label for="textfield"></label>${volunteer.code}</td>
							<td>姓名：</td>
							<td>${volunteer.name}</td>
						</tr>

						<tr>
							<td>性别：</td>
							<td><input name="" type="radio" value="nan"
								  <s:if test="volunteer.sex==1">
						            checked = "checked"
						          </s:if>
                               class="Radio" /> 男 
                               <input name="" type="radio"
								value="nan" class="Radio" 
								<s:if test="volunteer.sex==2">
						            checked = "checked"
						          </s:if>
								/> 女</td>
							<td>身份证号：</td>
							<td>${volunteer.identityCard}</td>
						</tr>
						<tr>
							<td>手机：</td>
							<td>${volunteer.cellPhone}</td>
							<td>微信：</td>
							<td>${volunteer.wechat}</td>
						</tr>
						<tr>
							<td>邮箱：</td>
							<td colspan="3">${volunteer.email}</td>
						</tr>

					</table>
				</form>
			</div>
			<div class="Information-font2"></div>
			<div class="Information-button">
				<input type="button" class="Information-btn" value="修改" onclick="window.location.href='volunteer/edit.action?id=${volunteer.id}'" style="cursor: pointer;"/>
				<input type="button" class="Information-btn" value="修改密码" onclick="window.location.href='volunteer/changePassword.action'" style="cursor: pointer;"/>
				<input type="button" class="Information-btn" value="取消" onclick="window.location.href='index.action'" style="cursor: pointer;"/>
			</div>
		</div>
	</div>
</body>
</html>