<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/pages/miniwechatHeader.jsp" %>
<title>员工服务微信平台</title>

<script type="text/javascript">
    function custom_close() {
        WeixinJSBridge.call('closeWindow');
    }
</script>
</head>
<body>

	<section class="panel">
		<s:include value="../strutsMessage.jsp" />
		<section class="panel">
			<header class="panel-heading">我的培训课程</header>
			<div class="adv-table dataTables_wrapper form-inline">
				<table id="myTrainCourse"
					class="table table-striped table-advance table-hover display  table-bordered">
                  <thead>
                    <tr>
                      <th>课程名称</th>
                      <th>状态</th> 
                    </tr>
                  </thead>
                  <tbody>
                  <s:iterator value="myTrainCourseList" var="myTrainCourse">
                    <tr>
                      <td>${myTrainCourse.trainCourse.name}</td>
                      <td>
                         <s:if test="#myTrainCourse.status == 1">
                            通过
                         </s:if>
                         <s:else>
                           未通过
                         </s:else>  
                      </td>
                    </tr> 
                  </s:iterator>               
                  </tbody>
				</table>
			</div>
		</section>
		<div class="panel-body">
			<button type="button" class="btn btn-info btn-block"
				onclick="custom_close()">返回微信</button>
		</div>
	</section>
</body>
</html>
