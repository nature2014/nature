<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="../metrouiHeader.jsp" %>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="../css/train.css" rel="stylesheet">
    <title>修改密码</title>

    <style type="text/css">

    </style>
</head>

<body>
<div class="home2">
    <div class="bg-user">
        <div class="bg-fh">
            <a href="${rootPath}/volunteer/edit.action?id=${sessionUser.id}">
                <img src="../img/back.png" width="35" height="35" />
            </a>
        </div>
        <div class="bg-top">修改密码</div>
        <div class="bg-username">${volunteer.name}</div>
        <div  class="bg-touxiang"><img src="${volunteer.iconpath}" onerror="this.src='${rootPath}/person/img/<s:property value="@util.DBUtils@getDBFlag()"/>/volunteer.png'" width="50" height="50" /></div>


    <div class="bg-right">

        <s:include value="../strutsMessage.jsp"/>

        <form  id="volunteerForm" action="${rootPath}/volunteer/changePassword.action" method="post">
            <div class="bg-table">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td><span class="required">*</span>旧密码<span class="bg-tishi"></span><br />
                            <input type="password" name="oldPassword" id="oldPassword"  class="zc-input" required="required"/>
                        </td>
                    </tr>

                    <tr>
                        <td><span class="required">*</span>新密码<span class="bg-tishi"></span><br />
                            <input type="password" name="volunteer.password" id="password"  class="zc-input" required="required"/>
                        </td>
                    </tr>

                    <tr>
                        <td><span class="required">*</span>确认密码<span class="bg-tishi"></span><br />
                            <input type="password" name="confirm_password" id="confirm_password" class="zc-input" required="required"/>
                        </td>
                    </tr>

                </table>
            </div>
            <div class="bg-btn">
                <input name="" class="Infor-btn" type="submit" value="修改" />
                <input name="" class="Infor-btn" type="button" value="取消" onclick="window.location.href='${rootPath}/volunteer/edit.action?id=${sessionUser.id}'" />
            </div>
        </form>
    </div>
</div>

<%@ include file="volunteerFieldsValidation.jsp"%>

</body>
</html>