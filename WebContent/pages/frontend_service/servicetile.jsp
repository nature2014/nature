<!DOCTYPE html>
<%@ include file="../frontHeader.jsp"%>
<script language="javascript" type="text/javascript">
    window.onload = function () {
        setInterval("document.getElementById('timewatcher').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());", 1000);
    }
</script>
<html>
<head>
<title>列表</title>
 <style>
     a img{border:none}
 </style>
</head>

<body>
	<div class="home2">
		<div class="bg-user">
            <div class="bg-username"><s:property value="#session['sessionUser'].name"/></div>
            <div class="bg-touxiang"><img src="${rootPath}/<s:property value="#session['sessionUser'].iconpath"/>" width="50"
                                          height="50" onerror="this.src='${rootPath}/img/nature.png'"/></div>
		</div>
        <div class="bg-right2">
            <div class="bg-time" id="timewatcher" style="float:right;color:white;font-size:20px">加载当前时间</div>
        </div>
        <div style="clear:both"></div>
		<div class="bg-volu">
			<img src="${rootPath}/person/img/<s:property value="@util.DBUtils@getDBFlag()"/>/volunteer_small.png" width="410" height="200" />
		</div>
        <a href="${rootPath}/volunteer/edit.action?id=${sessionUser.id}">
		<div class="bg-blue">
			<div class="bg-img">
				<img src="${rootPath}/img/zc.png" width="100" height="100" />
			</div>
			<div class="bg-font">
				我的注册
			</div>
		</div></a>

        <a href="${rootPath}/userFront/getCheckInRecords.action">
		<div class="bg-green">
			<div class="bg-img">
				<img src="${rootPath}/img/qd.png" width="100" height="100" />
			</div>
			<div class="bg-font">
				 我的签到
			</div>
		</div></a>

        <a href="${rootPath}/userFront/whoishere.action">
		<div class="bg-gay">
			<div class="bg-img">
				<img src="${rootPath}/img/location.png" width="100" height="100" />
			</div>
			<div class="bg-font">
				 谁在这里
			</div>
		</div></a>

        <a href="${rootPath}/frontTraincourse/index.action">
		<div class="bg-green2">
			<div class="bg-img">
				<img src="${rootPath}/img/px.png" width="100" height="100" />
			</div>
			<div class="bg-font">
				 我的培训
			</div>
		</div></a>

        <a href="${rootPath}/userFront/myMonthlyTimeReport.action">
		<div class="bg-black">
	      <div class="bg-img">
	        <img src="${rootPath}/img/clock.png" width="100" height="100" />
	      </div>
	      <div class="bg-font">
	        我的工时
	      </div>
		</div></a>

        <a href="${rootPath}/logout.action">
		<div class="bg-gray">
			<div class="bg-img">
				<img src="${rootPath}/img/exit.png" width="100" height="100" />
			</div>
			<div class="bg-font">
				退出
			</div>
		</div></a>
	</div>
</body>
</html>