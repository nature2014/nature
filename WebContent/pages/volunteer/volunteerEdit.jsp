<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <%@ include file="../metrouiHeader.jsp" %>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="${rootPath}/css/train.css" rel="stylesheet">
    <title>员工注册</title>

    <style type="text/css">
        .face {
            #border: 1px solid #CCCCCC;
            margin: 0 0 10px;
            min-height: 100px;
            padding: 20px 40px 20px 60px;
        }

        .error {
            color: red;
        }

        .required {
            color: red;
        }
    </style>
</head>

<body>
<div class="home2">
    <div class="bg-user">
        <div class="bg-fh">
            <a href="index.action">
                <img src="img/back.png" width="35" height="35" />
            </a>
        </div>
        <div class="bg-top">我的注册</div>
        <div class="bg-username">${volunteer.name}</div>
        <div class="bg-touxiang"><img src="${volunteer.iconpath}"  width="50" height="50" /></div>
        <s:property value="#volunteer.iconpath"/>
    </div>

    <div class="bg-left">
        <div class="face">
            <img id="personicon" src="${volunteer.iconpath}" >
        </div>
        <div id="cameraDialog">
            <%@ include file="../frontend_service/flashcamera.jsp" %>
        </div>
    </div>
    <form  id="volunteerForm" action="${rootPath}/volunteer/save.action" method="post">
        <input name="volunteer.iconpath" id="iconpath" type="hidden" value="${volunteer.iconpath}"/>
        <input name="volunteer.id" type="hidden" value="${volunteer.id}"/>
        <input name="volunteer.registerFrom" type="hidden" value="${volunteer.registerFrom}"/>
        <input name="volunteer.status" type="hidden" value="${volunteer.status}"/>

        <div class="bg-right">
            <div class="bg-title">个人信息</div>

            <s:include value="../strutsMessage.jsp"/>

            <div class="bg-table">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td><span class="required">*</span>工号<span class="bg-tishi"></span> <br/>
                            <input type="text" name="volunteer.code" id="code" value="${volunteer.code}" class="zc-input" readonly="readonly"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <s:if test="%{!#request['struts.request_uri'].contains('view.action')}">
                                指纹信息
                                <span id="console_message" style="color:red;"></span>
                                <div style="margin-bottom: 20px;">
                                    <img id="fingerjpg" src="${volunteer.fingerpath}" style="width:100px;height:80px;margin-bottom:120px">
                                    <script>
                                        window.figureNumber = "${volunteer.code}";
                                        function  printMessage(message){
                                            jQuery("#console_message").html(message);
                                        }
                                    </script>
                                    <%@include file="../finger_function/fingerregister.jsp"%>
                                </div>
                                <div style="margin:-100px 0 20px 0px;">
                                    <input id="fingerpath" name="volunteer.fingerpath" type="hidden" value="${volunteer.fingerpath}">
                                    <input name="volunteer.id" class="Infor-btn" type="button" value="指纹录入" onclick="beginRegister()">
                                </div>
                            </s:if>
                            <s:else>
                                <label>指纹信息</label>
                                <div class="input-control text" data-role="input-control">
                                    <img id="fingerjpg" src="${volunteer.fingerpath}" style="width:100px;height:80px;margin-bottom:120px">
                                </div>
                            </s:else>
                        </td>
                    </tr>
                    <tr>
                        <td><span class="required">*</span>姓名<span class="bg-tishi"></span><br />
                            <input type="text" name="volunteer.name" id="name" value="${volunteer.name}" class="zc-input" readonly="readonly"/></td>
                    </tr>
                    <tr>
                        <td><span class="required">*</span>性别<span class="bg-tishi"></span>
                            <input name="volunteer.sex" type="radio" value="1" checked = "checked" class="Radio" /> 男
                            <input name="volunteer.sex" type="radio" value="2"
                                   <s:if test="volunteer.sex == 2">
                                       checked = "checked"
                                   </s:if>
                                   class="Radio" /> 女
                        </td>
                    </tr>
                    <tr>
                        <td><span class="required">*</span>职称<span class="bg-tishi"></span><br />
                            <s:select name="volunteer.occupation" list="listSource" listKey="code" listValue="name" value="%{volunteer.occupation}"/>
                    </tr>
                    <tr>
                        <td><span class="required">*</span>证件类型<span class="bg-tishi"></span><br />
                            <s:select id="identityType" disabled="true" cssClass="form-control" list="#{'-1':'其他','0':'身份证','1':'护照','2':'港澳台'}" listKey="key" listValue="value" name="volunteer.identityType" value="%{volunteer.identityType}"/>
                            <input type="hidden" name="volunteer.identityType" value="${volunteer.identityType}"/>
                    </tr>
                    <tr>
                        <td><span class="required">*</span>证件号<span class="bg-tishi"></span><br />
                            <input type="text" name="volunteer.identityCard" id="identityCard" value="${volunteer.identityCard}" class="zc-input" readonly="readonly"/></td>
                    </tr>
                    <tr>
                        <td><span class="required">*</span>手机<span class="bg-tishi"></span><br />
                            <input type="text" name="volunteer.cellPhone" id="cellPhone" value="${volunteer.cellPhone}" class="zc-input" required="required"/></td>
                    </tr>
                    <tr>
                        <td>微信<br />
                            <input type="text" name="volunteer.wechat" id="wechat" class="zc-input" /></td>
                    </tr>
                    <tr>
                        <td>邮箱<span class="bg-tishi"></span><br />
                            <input type="text" name="volunteer.email" id="email" value="${volunteer.email}" class="zc-input"/></td>
                    </tr>

                </table>
            </div>
            <div class="bg-btn">
                <input class="Infor-btn" type="submit" value="修改"/>
                <input class="Infor-btn" type="button" value="修改密码" onclick="window.location.href='${rootPath}/volunteer/changePassword.action'" style="cursor: pointer;"/>
                <input class="Infor-btn" type="button" value="取消" onclick="window.location.href='${rootPath}/index.action'" />
            </div>
        </div>

        <%@ include file="volunteerFieldsValidation.jsp"%>

    </form>
</div>
</body>
</html>

