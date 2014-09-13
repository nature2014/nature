<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/pages/miniwechatHeader.jsp" %>
</head>
<body>
    <section class="panel">
        <%-- 消息引用 --%>
        <s:include value="../strutsMessage.jsp"/>

        <div class="panel-body">
            <form action="/wechat/user/save.action" method="post" class="form-horizontal">
                <input type="hidden" name="openID" value="<s:property value='openID'/>">
                <div class="form-group">
                    <input type="hidden" name="vol.id" value="${volunteer.id}"/>
                    <label class="col-xs-4  control-label" for="volWechat">微信用户</label>
                    <div class="col-xs-8">
                        <input class="form-control" id="volWechat" name="vol.wechat" value="${volunteer.wechat}" readonly="readonly"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4  control-label" for="volCode">工号</label>
                    <div class="col-xs-8">
                        <input class="form-control" id="volCode" name="vol.code" value="${volunteer.code}" readonly="readonly"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4  control-label" for="volName">姓名</label>
                    <div class="col-xs-8">
                        <input class="form-control" id="volName" name="vol.name" value="${volunteer.name}" readonly="readonly"/>
                    </div>
                </div>
                 
                <div class="form-group">
                    <label class="col-xs-4  control-label" for="volCellPhone">手机号</label>
                    <div class="col-xs-8">
                        <input class="form-control" id="volCellPhone" name="vol.cellPhone" value="${volunteer.cellPhone}"/>
                    </div>
                </div> 
                <div class="form-group">
		             <div class="col-lg-offset-2 col-lg-10">
		                 <button class="btn btn-info btn-block" type="submit">保存</button>
		                 <button class="btn btn-info btn-block" type="button" onclick="WeixinJSBridge.call('closeWindow')">取消</button>
		             </div>
		        </div> 
            </form>
        </div>
    </section>
</body>
</html>
