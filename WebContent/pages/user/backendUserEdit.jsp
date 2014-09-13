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
     <s:if test="user.id.length() > 0">
        修改用户
      </s:if>
      <s:else>
        添加用户
      </s:else>
    </title>
  </head>
<body>

<!--main content start-->
  <section class="panel">
    <header class="panel-heading">
       <s:if test="user.id.length() > 0">
        修改用户
      </s:if>
      <s:else>
        添加用户
      </s:else>
    </header>
    <div class="panel-body">
        <s:actionerror/><s:actionmessage/>
         <form id="userForm" class="form-horizontal tasi-form" action="${rootPath}/backend/user/save.action" method="post">
             <div class="form-group has-success">
                 <label class="col-lg-2 control-label">用户名</label>
                 <div class="col-lg-10">
                     <input name="user.id" type="hidden" value="${user.id}"/>
                     <input type="text" placeholder="用户名" name="user.name" class="form-control" autofocus="autofocus"
                            required="required" value="${user.name}"
                            <s:if test="user.id != null">readonly="readonly"</s:if>
                            />
                 </div>
             </div>
             <s:if test="user.id.length() > 0">
             </s:if>
            <s:else>
              <div class="form-group has-error">
               <label for="password" class="control-label col-lg-2">密码</label>
               <div class="col-lg-10">
                   <input class="form-control " id="password" name="user.password" type="password" placeholder="密码"  required="required"/>
               </div>
              </div>

              <div class="form-group has-error">
                <label for="confirm_password" class="control-label col-lg-2">再次输入密码</label>
               <div class="col-lg-10">
                   <input class="form-control "  name="confirm_password" type="password" placeholder="请再次输入密码"  required="required"/>
               </div>
              </div>
            </s:else>

             <div class="form-group">
                 <div class="col-lg-offset-2 col-lg-10">
                     <button class="btn btn-info" type="submit">保存</button>
                     <s:if test="user.id.length() > 0 && #session.backendSessionUser != null && #session.backendSessionUser.name == 'admin'">
                     <button class="btn btn-info" type="button" onclick="window.location.href='backend/user/resetPassword.action?id=${user.id}'">重置密码</button>
                     </s:if>
                     <button class="btn btn-info" type="button" onclick="window.location.href='backend/user/index.action'">取消</button>
                 </div>
             </div>
         </form>
    </div>
  </section>
  <script type="text/javascript">
    //please refer to form-validation-script.js
    $(document).ready(function() {
        $("#userForm").validate({
            rules: {
                confirm_password: {
                    equalTo: "#password"
                }
            },
            messages: {
                'user.name': {
                    required: "请输入用户名"
                },
                'user.password': {
                    required: "请输入密码"
                },
                confirm_password: {
                    required: "请再次输入密码",
                    equalTo: "密码两次输入不一致"
                }
            }
        }); 
    });
    </script>
</body>
</html>