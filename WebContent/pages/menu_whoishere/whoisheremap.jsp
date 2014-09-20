<%--
  User: peter
  Date: 14-3-16
  Time: 上午10:07
--%>
<!DOCTYPE html>
<html lang="en">
<html>
<head>
    <title>公司外服务地点</title>
    <%@ include file="../bootstrapHeader.jsp" %>
</head>
<body>
<section class="panel">
    <header class="panel-heading">
        公司外服务地点
    </header>

    <section class="container">

            <div class="row state-overview">
                <div class="col-lg-2 col-md-2 col-sm-3">
                    <a class="btn btn-success btn-block" href="${rootPath}/pages/menu_whoishere/serviceplaceview.jsp">
                        返回谁在这里
                    </a>
                </div>
            </div>
            <style type="text/css">
                #allmap {width:100%;height:580px;overflow: hidden;margin:20px;}
            </style>
            <s:if test="servicePlaceVolunteer.size()==0">
                <p style="color:red">公司外服务地点没有员工服务！</p>
            </s:if>
            <%@ include file="../menu_serviceplace/foundpositionmap.jsp"%>
            <script type="text/javascript">
                jQuery(document).ready(function(){
                    <s:iterator value="servicePlaceVolunteer">
                    var buffer = [];
                    buffer.push('<ul class="nav top-menu">');
                    buffer.push('<li class="dropdown">');
                    buffer.push('<div class="dropdown-toggle" data-toggle="dropdown">');
                    buffer.push('地点：<s:property value="key.name"/><span class="badge bg-success"><s:property value="value.size"/></span>');
                    buffer.push('</div>');
                    buffer.push('<ul class="dropdown-menu extended tasks-bar" style="width:500px !important;max-width:500px !important;">');
                    buffer.push('<div class="notify-arrow notify-arrow-green"></div>');
                    buffer.push('<li>');
                    buffer.push('<p class="green">总共<s:property value="value.size"/>个员工在此服务</p>');
                    buffer.push('</li>');
                    buffer.push('<s:iterator value="value" var="volunteer" status="id">');
                    buffer.push('<li>');
                    buffer.push('<span class="photo"><img src="<s:property value="#volunteer.iconpath"/>"');
                    buffer.push('width="50px" height="50px"');
                    buffer.push('onerror=\'this.src="${rootPath}/img/nature.png;"\'/></span>');
                    buffer.push('<span>姓名：<s:property value="#volunteer.name"/></span>');
                    buffer.push('<span>&nbsp;工号：<s:property value="#volunteer.code"/></span>');
                    buffer.push('<span>&nbsp;手机：<s:property value="#volunteer.cellPhone"/></span>');
                    buffer.push('</li>');
                    buffer.push('</s:iterator>');
                    buffer.push('</ul>');
                    buffer.push('</li>');
                    buffer.push('</ul>');
                        mapMutiplePosition(buffer.join(""),"<s:property value="key.longitude"/>","<s:property value="key.latitude"/>");
                    </s:iterator>
                    <s:if test="servicePlaceVolunteer.size()==0">
                       //初始化地图
                       initializePosition("","","");
                    </s:if>
                });

            </script>
    </section>
</section>
</body>
</html>
