<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../frontHeader.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${rootPath}/css/train.css" rel="stylesheet">
<title>我的培训</title>

<style type="text/css">
a {
	color: #003547;
}

.selected {
	border: 2px solid #0671DE;
}
</style>
<script type="text/javascript">
    window.onload=function (){
        setInterval("document.getElementById('timewatcher').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",1000);
    }

    function receive(){
    var selectedRows = $('.selected');
    if(selectedRows.length == 0){
       alert("请选择要培训的课程!");
    }else{
      if (confirm("您确定要接受 "+ selectedRows.length +" 培训课程吗?")){
	      var par = "";
	      $('.selected').each(function(i){
	         par = par + "ids="+$(this).attr("id") + "&";
	      });
	      window.location.href = "${rootPath}/frontTraincourse/receive.action?" + par;
      }
    }
}

</script>
</head>

<body>
	<div class="home2">
		<div class="bg-user">
			<div class="bg-fh" onclick="window.location.href = '<%=request.getContextPath() %>/index.action'" style="cursor: pointer;">
				<img src="${rootPath}/img/back.png" width="35" height="35" />
			</div>
			<div class="bg-top">我的培训</div>
            <div class="bg-user">
                <div class="bg-username"><s:property value="#session['sessionUser'].name"/></div>
                <div class="bg-touxiang"><img src="<s:property value="#session['sessionUser'].iconpath"/>" width="50"
                                              height="50" onerror="this.src='${rootPath}/person/img/<s:property value="@util.DBUtils@getDBFlag()"/>/volunteer.png'"/></div>
            </div>
		</div>
        <div class="bg-right2">
            <div class="bg-title2" style="font-size:30px"></div>
            <div class="bg-time" id="timewatcher">加载当前时间</div>
        </div>
		<div class="bg-left3">
			<div class="bg-title3">已接受的培训</div>
			<s:iterator value="myTraincourse" var="item">
				<div class="bg-train">
					<div class="bg-train-left"></div>
					<div class="bg-train-right">
						<div class="kc-name">
							<s:property value="#item.trainCourse.name" />
						</div>
						<div class="kc-zt">
							<s:if test="#item.status == 1">
	                        通过
	                      </s:if>
							<s:else>
	                        未通过
	                      </s:else>
						</div>
					</div>
				</div>
			</s:iterator>
		</div>

		<div class="bg-right3">
			<div class="bg-title3">未接受的培训</div>
			<s:iterator value="allTraincourse" var="item">
				<div class="bg-train2" onclick="$(this).toggleClass('selected')"
					id="<s:property value='#item.id'/>">
					<div class="bg-train-left2"></div>
					<div class="bg-train-right2">
						<div class="kc-name">
							<s:property value="#item.name" />
						</div>
						<div class="kc-zt2">
							<s:if test="#item.status == 1">
	                        开始
	                      </s:if>
							<s:elseif test="#item.status == 2">
	                        结束
	                      </s:elseif>
							<s:else>
	                        创建 
	                      </s:else>
						</div>
						<div class="kc-time">
							<s:date name="#item.createTime" format="yyyy-MM-dd" />
						</div>
					</div>
				</div>
			</s:iterator>
		</div>

		<div class="train-hr">
			<div class="train-hr-left">
				<s:if test="start > 0">
					<a class="pre"
						href="${rootPath}/frontTraincourse/index.action?start=${start-length}">上一页</a>
				</s:if>
				<s:else>
					<a class="pre" onclick="javascript:return;">上一页</a>
				</s:else>
				<s:if test="(start + length) < count">
					<a class="pre"
						href="${rootPath}/frontTraincourse/index.action?start=${start+length}">下一页</a>
				</s:if>
				<s:else>
					<a class="pre" onclick="javascript:return;">下一页</a>
				</s:else>
			</div>
			<div class="train-hr-right">
				<input class="train-button" type="button" onclick="receive()"
					value="接受任务" />
			</div>
		</div>
	</div>
</body>
</html>
