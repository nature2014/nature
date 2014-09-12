<html:html>
<%@ include file="/pages/commonHeader.jsp"%>
<head>
<title>generalError.jsp</title>
</head>

<body>

	<h3>系统后台发了异常错误，请联系系统管理员并且把相关信息告知管理员，以便更快地诊断问题。</h3>
	<pre style="color: red">
		<s:property value="exception.message" />
	</pre>
</body>
</html:html>
