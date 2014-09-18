<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp"%>
<head>
    <style type="text/css">
		 table tbody tr.even.row_selected td{
			background-color: #B0BED9 !important;
		 }
    </style>
    <!--external css-->
    <title>
     <s:if test="volunteer.id.length() > 0">
        修改员工
      </s:if>
      <s:else>
        添加员工
      </s:else>
    </title>
  </head>
<body>

<!--main content start-->
  <section class="panel">
    <header class="panel-heading">
       <s:if test="volunteer.id.length() > 0">
        修改员工
      </s:if>
      <s:else>
        添加员工
      </s:else>
    </header>
    <div class="panel-body">
    <s:actionerror/><s:actionmessage/>
     <form id="volunteerForm" class="form-horizontal tasi-form" action="${rootPath}/backend/volunteer/save.action" method="post">
         <div class="form-group has-error">
             <label class="col-lg-2 control-label">员工名</label>
             <div class="col-lg-10">
                 <input name="volunteer.id" type="hidden" value="${volunteer.id}"/>
                 <input name="volunteer.registerFrom" type="hidden" value="${volunteer.registerFrom}"/>
                 <input type="text" placeholder="员工名" name="volunteer.name" class="form-control"
                        required="required" value="${volunteer.name}"/>
             </div>
         </div> 
         <div class="form-group has-error">
             <label class="col-lg-2 control-label">工号</label>
             <div class="col-lg-10">
                <input name="volunteer.code" type="text" value="${volunteer.code}" class="form-control" required="required" placeholder="请输入工号"/>
             </div>
         </div>
         <div class="form-group has-success">
             <label class="col-lg-2 control-label">用户头像</label>

             <div class="col-lg-10">
                 <img id="volunteer_logo" class="imagedesigner" src="${volunteer.iconpath}"/>
                 <input type="text" placeholder="用户头像地址" name="volunteer.iconpath" class="form-control"
                        required="required" value="${volunteer.iconpath}" onkeyup="jQuery('#volunteer_logo').attr('src',this.value);"/>

             </div>
         </div>
         <div class="form-group has-error">
             <label class="col-lg-2 control-label">状态</label>
             <div class="col-lg-10">
                <s:if test="volunteer.status==0">
                    <input id="status" type="text" value="已注册"  class="form-control" required="required"/>
                    <input name="volunteer.status" value="0" type="hidden"/>
                </s:if>
                <s:elseif test="volunteer.status==1">
                    <input id="status" type="text" value="通过审核" class="form-control" required="required"/>
                    <input name="volunteer.status" value="1" type="hidden"/>
                </s:elseif>
                 <s:elseif test="volunteer.status==2">
                     <input id="status" type="text" value="通过面试" class="form-control" required="required"/>
                     <input name="volunteer.status" value="2" type="hidden"/>
                 </s:elseif>
                 <s:elseif test="volunteer.status==3">
                     <input id="status" type="text" value="未通过审核" class="form-control" required="required"/>
                     <input name="volunteer.status" value="3" type="hidden"/>
                 </s:elseif>
                 <s:elseif test="volunteer.status==4">
                     <input id="status" type="text" value="未通过面试" class="form-control" required="required"/>
                     <input name="volunteer.status" value="4" type="hidden"/>
                 </s:elseif>
                 <s:else>
                     <input id="status" type="text" value="未知状态" class="form-control" required="required"/>
                 </s:else>
             </div>
         </div>
         <s:if test="volunteer.id.length() > 0">
         </s:if>
        <s:else>
          <div class="form-group has-error">
           <label for="password" class="control-label col-lg-2">密码</label>
           <div class="col-lg-10">
               <input class="form-control " id="password" name="volunteer.password" type="password" placeholder="密码"  required="required"/>
           </div>
          </div>

          <div class="form-group has-error">
            <label for="confirm_password" class="control-label col-lg-2">再次输入密码</label>
           <div class="col-lg-10">
               <input class="form-control "  id="confirm_password" name="confirm_password" type="password" placeholder="请再次输入密码"  required="required"/>
           </div>
          </div>
        </s:else>

        <div class="form-group has-error">
             <label class="col-lg-2 control-label">性别</label>
             <div class="col-lg-10">
                 <label class="inline-block">
			        <input type="radio" name="volunteer.sex" value="1" checked
			          <s:if test="volunteer.sex==1">
			            checked
			          </s:if>
			         />
			        <span class="check"></span>
			        男
			    </label>
			    <label class="inline-block">
			        <input type="radio" name="volunteer.sex" value="2"
			          <s:if test="volunteer.sex==2">
			            checked
			          </s:if>
			        />
			        <span class="check"></span>
			        女
			    </label>   
             </div>
         </div>
         <div class="form-group has-error">
             <label class="col-lg-2 control-label">职称</label>
             <div class="col-lg-10">
                 <s:select name="volunteer.occupation" list="listSource" listKey="code" listValue="name" value="%{volunteer.occupation}" cssClass="form-control"/>
             </div>
         </div>

         <div class="form-group has-error">
             <label class="col-lg-2 control-label">证件类型</label>
             <div class="col-lg-10">
                 <s:select id="identityType" cssClass="form-control" list="#{'-1':'其他','0':'身份证','1':'护照','2':'港澳台'}" listKey="key" listValue="value" name="volunteer.identityType" value="%{volunteer.identityType}"/>
             </div>
         </div>

        <div class="form-group has-error">
             <label class="col-lg-2 control-label">证件号</label>
             <div class="col-lg-10">
                 <input type="text" class="form-control" placeholder="请输入证件号" name="volunteer.identityCard" value="${volunteer.identityCard}" required="required"/>
             </div>
         </div>

         <div class="form-group has-error">
             <label class="col-lg-2 control-label">手机</label>
             <div class="col-lg-10">
                 <input type="text" class="form-control" placeholder="请输入手机" name="volunteer.cellPhone" value="${volunteer.cellPhone}" required="required"/>
             </div>
         </div>
         
         <div class="form-group has-success">
             <label class="col-lg-2 control-label">微信</label>
             <div class="col-lg-10">
                 <input type="text" class="form-control" placeholder="微信" name="volunteer.wechat" class="form-control" 
                        value="${volunteer.wechat}"/>
             </div>
         </div>
         
         <div class="form-group has-success">
             <label class="col-lg-2 control-label">邮箱</label>
             <div class="col-lg-10">
                 <input type="text" placeholder="邮箱" name="volunteer.email" class="form-control" 
                         value="${volunteer.email}"/>
             </div>
         </div>
         
         <div class="form-group">
             <div class="col-lg-offset-2 col-lg-10">
                 <button class="btn btn-info" type="submit">保存</button>
                 <s:if test="volunteer.id.length() > 0">
                 <button class="btn btn-info" type="button" onclick="window.location.href='${rootPath}/backend/volunteer/resetPassword.action?id=${volunteer.id}'">重置密码</button>
                 </s:if>
                 <button class="btn btn-info" type="button" onclick="window.location.href='${rootPath}/backend/volunteer/index.action'">取消</button>
             </div>
         </div>
         
     </form>
    </div>
  </section>
   <%@ include file="backendVolunteerFieldsValidation.jsp"%>
  <s:if test="volunteer.id.length() > 0">
	 <script type="text/javascript">
	 $(document).ready(function() {
	     $("form input[name='volunteer.name']").attr("readonly","readonly");
         $("form input[name='volunteer.code']").attr("readonly","readonly");
         $("form #status").attr("readonly","readonly");
	 });
    </script>
  </s:if>
</body>
</html>