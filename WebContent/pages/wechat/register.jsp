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
            <form id="volunteerForm" action="/wechat/user/register.action" method="post" class="form-horizontal">
                <input type="hidden" name="openID" value="<s:property value='openID'/>">
                <!-- 
                <div class="form-group">
                    <label class="col-xs-4  control-label" for="volCode">工号</label>
                    <div class="col-xs-8">
                        <input class="form-control" id="volCode" name="vol.code" value="${vol.code}" readonly="readonly"/>
                    </div>
                </div>
                 -->
                <div class="form-group">
                    <label class="col-xs-4  control-label" for="volName">姓名</label>
                    <div class="col-xs-8">
                        <input class="form-control" id="volName" name="vol.name" value="${vol.name}" required="required"/>
                    </div>
                </div>
                  
                <div class="form-group">
                    <label class="col-xs-4  control-label" for="volIdentityCard">身份证号</label>
                    <div class="col-xs-8">
                        <input class="form-control" id="volIdentityCard" name="vol.identityCard" value="${vol.identityCard}" required="required"/>
                    </div>
                </div> 
                 
                <div class="form-group">
                    <label class="col-xs-4  control-label" for="volCellPhone">手机号</label>
                    <div class="col-xs-8">
                        <input class="form-control" id="volCellPhone" name="vol.cellPhone" value="${vol.cellPhone}" required="required"/>
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
    
    <script type="text/javascript" src="js/checkUtil.js"></script>
<script type="text/javascript">
    //please refer to form-validation-script.js
    $(document).ready(function() {
        $("#volunteerForm").validate({
            rules: {
				'vol.identityCard':{ 
				   required:true, 
                   idCardNo:true 
				}, 
				'vol.cellPhone':{ 
				   required:true, 
                   cellPhone:true 
				}
            },
            messages: {
                'vol.name': {
                    required: "请输入用户名"
                },
                'vol.identityCard': {
                    required: "请输入身份证号",
                    idCardNo: "请输入正确的身份证号"
                },
                'vol.cellPhone': {
                    required: "请输入手机",
                    cellPhone: "请输入正确的手机号, 例如：13912332122"
                }
            }
        }); 
    });
    </script>
</body>
</html>
