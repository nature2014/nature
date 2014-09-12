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
        审核员工
    </title>
  </head>
<body>

<!--main content start-->
  <section class="panel">
    <header class="panel-heading">
        审核员工
    </header>
    <div class="panel-body">
    <s:actionerror/><s:actionmessage/>
     <form id="volunteerForm" class="form-horizontal tasi-form" action="${rootPath}/backend/volunteerVerify/save.action" method="post">
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
         <div class="form-group has-error">
             <label class="col-lg-2 control-label">审核结果</label>
             <div class="col-lg-10">
                <select name="volunteer.status" class="form-control">
                  <option value="0" <s:if test="volunteer.status == 0">selected="selected"</s:if>>未知</option>
                  <option value="1" <s:if test="volunteer.status == 1">selected="selected"</s:if>>通过</option>
                  <option value="3" <s:if test="volunteer.status == 3">selected="selected"</s:if>>不通过</option>
                </select>
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

         <div class="form-group">
             <div class="col-lg-offset-2 col-lg-10">
                 <button class="btn btn-info" type="submit">保存</button>
                 <button class="btn btn-info" type="button" onclick="window.location.href='${rootPath}/backend/volunteerVerify/index.action'">取消</button>
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
	 });
    </script>
  </s:if>
</body>
</html>