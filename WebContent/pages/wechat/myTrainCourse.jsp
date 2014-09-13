<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pages/commonHeader.jsp"%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="dynamic manager technique">
<meta name="author" content="LiLimin,GuDong,WangRonghua">
<title>员工服务微信平台</title>

<link rel="shortcut icon" href="jslib/flatlab/img/favicon.png">

<!-- Bootstrap core CSS -->
<link href="jslib/flatlab/css/bootstrap.min.css" rel="stylesheet">
<link href="jslib/flatlab/css/bootstrap-reset.css" rel="stylesheet">
<!--external css-->
<link href="jslib/flatlab/assets/font-awesome/css/font-awesome.css"
	rel="stylesheet" />
<link
	href="jslib/flatlab/assets/bootstrap-datepicker/css/datepicker.css"
	rel="stylesheet" />
<!-- Custom styles for this template -->
<link href="jslib/flatlab/css/style.css" rel="stylesheet">
<link href="jslib/flatlab/css/style-responsive.css" rel="stylesheet" />
<link
	href="jslib/jquery-ui-1.10.4.custom/css/start/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet" />

<!-- js placed at the end of the document so the pages load faster -->
<script src="jslib/flatlab/js/jquery.js"></script>
<script
	src="jslib/jquery-ui-1.10.4.custom/js/jquery-ui-1.10.4.custom.min.js"></script>
<script src="jslib/flatlab/js/bootstrap.min.js"></script>
<script src="jslib/flatlab/js/jquery.scrollTo.min.js"></script>
<script src="jslib/flatlab/js/jquery.nicescroll.js"
	type="text/javascript"></script>
<script src="jslib/flatlab/js/respond.min.js"></script>

<!--common script for all pages-->
<script src="jslib/flatlab/js/jquery.validate.min.js"
	type="text/javascript"></script>

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
