<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp"%>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Mosaddek">
    <meta name="keyword" content="FlatLab, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
    <link rel="shortcut icon" href="img/favicon.png">

    <style type="text/css">
		 table tbody tr.even.row_selected td{
			background-color: #B0BED9 !important;
		 }
    </style>
    <!--external css-->
    <title>
     <s:if test="volunteer.id.length() > 0">
        修改培训记录
      </s:if>
      <s:else>
        添加培训记录
      </s:else>
    </title>
  </head>
<body>

<!--main content start-->
  <section class="panel">
    <header class="panel-heading">
      <s:if test="volunteer.id.length() > 0">
        修改培训记录
      </s:if>
      <s:else>
        添加培训记录
      </s:else>
    </header>
    <div class="panel-body">
        <div style="width: 500px;"><s:actionerror/><s:actionmessage/></div>
        <form id="volunteerForm" class="form-horizontal tasi-form" action="${rootPath}/backend/volunterTrainCourse/save.action" method="post">
        <div class="form-group has-success">
            <label class="col-lg-2 control-label">员工名</label>
            <div class="col-lg-10">
                <input type="hidden" name="volunteerTrainCourse.id" value="${volunteerTrainCourse.id}"/>
                <s:if test="volunteerTrainCourse.id.length() > 0 || volunteerId.length() > 0">
                </s:if>
                <s:else>
                    <input type="text" id="volunteerName" placeholder="输入 员工名, 点查询"  class="form-control pull-left" style=" width: 200px;  "/>
                    <input type="button" class="btn btn-success" onclick="queryVolunteer()" value="查询" >
                    <div style="clear: both;"></div>
                </s:else>
                <s:if test="%{#request['struts.request_uri'].contains('edit.action')}">
                    <select id="volunteerIdSelect" name="volunteerId" class="form-control input-lg m-bot15">
                        <s:if test="volunteerTrainCourse.id.length() > 0 || volunteerId.length() > 0">
                         <option value="${volunteerTrainCourse.volunteer.id}">${volunteerTrainCourse.volunteer.name}</option>
                        </s:if>
                    </select>
                </s:if>
                <s:else>
                    <select id="volunteerIdSelect" name="volunteerId" class="form-control input-lg m-bot15" style=";height:300px" multiple="multiple" size="200">
                        <s:if test="volunteerTrainCourse.id.length() > 0 || volunteerId.length() > 0">
                            <option value="${volunteerTrainCourse.volunteer.id}">${volunteerTrainCourse.volunteer.name}</option>
                        </s:if>
                    </select>
                </s:else>
            </div>
        </div>

        <div class="form-group has-success">
            <label class="col-lg-2 control-label">状态</label>
            <div class="col-lg-10">
                <select name="volunteerTrainCourse.status" class="form-control input-lg m-bot15" style="width: 200px;">
                  <option value="0"
                     <s:if test="volunteerTrainCourse.status == 0">selected="selected"</s:if>
                     >未通过</option>
                  <option value="1"
                     <s:if test="volunteerTrainCourse.status == 1">selected="selected"</s:if>
                     >通过</option>
                </select>
            </div>
        </div>

        <div class="form-group has-success">
            <label class="col-lg-2 control-label">培训课程</label>
            <div class="col-lg-10">
                <s:iterator value="trainCourseList" id="item" status="st">
                  <div class="radio">
                    <label>
                        <input type="radio" name="traincourseId" value="${item.id}"
                          <s:if test="#st.index == 0 || #item.id == traincourseId">checked="checked"</s:if>
                        >
                        ${item.name}
                    </label>
                 </div>
                </s:iterator>
            </div>
        </div>
         <div class="form-group  has-success">
             <div class="col-lg-offset-2 col-lg-10">
                 <button class="btn btn-info" type="submit">保存</button>
                 <button class="btn btn-info" type="button" onclick="history.go(-1);">取消</button>
             </div>
         </div>
       </form>
    </div>
  </section>
  <script type="text/javascript">
    function queryVolunteer(){
        var volunteerSelect = document.getElementById('volunteerIdSelect');
        while(volunteerSelect.length > 0){
            volunteerSelect.remove(0);
        }
        if($('#volunteerName').val() == ''){
            return;
        }
        var url = "${rootPath}/backend/volunteer/search.action";
        var param = {'volunteer.name':$('#volunteerName').val()};
        $.post( url, param, function (volunteerArray) { 
           if(volunteerArray == null || volunteerArray.length ==0){
               return;
           }
           for(var i=0; i < volunteerArray.length;i++){
               var optionObj =document.createElement( 'option' );
               optionObj.text = "姓名:"+volunteerArray[i].name+"  工号:"+volunteerArray[i].code+"  证件号:"+volunteerArray[i].identityCard;
               optionObj.value = volunteerArray[i].id;
               volunteerSelect.add(optionObj);
           }
        });
    }
    
    
    function queryTrainCourse(){
        var traincourseSelect = document.getElementById('traincourseIdSelect');
        while(traincourseSelect.length > 0){
            traincourseSelect.remove(0);
        }
        if($('#trainCourseName').val() == ''){
            return;
        }
        var url = "${rootPath}/backend/traincourse/search.action";
        var param = {'trainCourse.name':$('#trainCourseName').val()};
        $.post( url, param, function (trainCourseArray) { 
            if(trainCourseArray == null || trainCourseArray.length ==0){
                return;
            }
            for(var i=0; i < trainCourseArray.length;i++){
                var optionObj =document.createElement( 'option' );
                optionObj.text = trainCourseArray[i].name;
                optionObj.value = trainCourseArray[i].id;
                traincourseSelect.add(optionObj);
            }
        });
    }
  </script>
</body>
</html>