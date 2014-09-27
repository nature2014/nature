<%--
  User: peter
  Date: 14-3-16
  Time: 上午10:07
--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="../bootstrapHeader.jsp" %>
    <link href="../css/train.css" rel="stylesheet">
    <title>谁在这里</title>
    <script language="javascript" type="text/javascript">
        window.onload = function () {
            setInterval("document.getElementById('timewatcher').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());", 1000);
        }
    </script>

    <style type="text/css">

    </style>
</head>

<body>
<div class="home2">
    <div class="bg-user">
        <div class="bg-fh">
            <a href='${rootPath}/userFront/whoishere.action'>
                <img src="${rootPath}/img/back.png" width="35" height="35"/>
            </a>
        </div>
        <div class="bg-top">谁在这里</div>
        <div class="bg-username"><s:property value="#session['sessionUser'].name"/></div>
        <div class="bg-touxiang"><img src="<s:property value="#session['sessionUser'].iconpath"/>" width="50"
                                      height="50" onerror="this.src='${rootPath}/person/img/<s:property value="@util.DBUtils@getDBFlag()"/>/volunteer.png'"/></div>
     </div>
     
     <div class="bg-center2">
        <div class="bg-title2"></div>
        <div class="bg-time" id="timewatcher">加载当前时间</div>
        <div class="bg-left2" style="width: 200px;">
            <div class="hosp-green">
                <div class="plase-img">
                    <img src="<s:property value="servicePlaceBean.serviceicon"/>" style="width:100px;height:80px"/>
                </div>
                <div class="plase-font" style="width:150px"><s:property value="servicePlaceBean.name"/>
                   <span style="float:right;color:red;font-weight:900;font-size:20px">
                        <s:property value="servicePlaceBean.activeUserBeanList.size()"/>
                   </span>
                </div>
            </div>
        </div>     
        <div class="bg-right2" style="width: 500px;">    
          <s:iterator value="servicePlaceBean.activeUserBeanList" var="vol">
                <div class="person-train2">
                    <div class="person-train-left2"><img src="<s:property value="#vol.volunteer.iconpath"/>" onerror="this.src='person/img/<s:property value="@util.DBUtils@getDBFlag()"/>/volunteer.png'"/></div>
                    <div class="person-train-right2">
                       <div style="padding-left:10px; float: left;">
                          <div style="font-size: large;color: blue;"><s:property value="#vol.volunteer.name"/></div>
                          <div style="color:black;"><s:property value="#vol.volunteer.code"/></div>
                          <div style="color:gray;"><s:property value="#vol.volunteer.cellPhone"/></div>
                       </div>
                       <div style="float: right;padding-top: 30px;">
                          <s:if test="#vol.status == 1">
                             <img src="${rootPath}/img/wechat.jpg"/>
                          </s:if>
                          <s:else>
                             <img src="${rootPath}/img/screen.jpg"/>
                          </s:else>
                       </div>
                       <s:if test="#vol.status == 1">
                           <div style="color:gray;padding-top: 30px;float: right;margin-right:10px"><s:if test="#vol.description ==null || #vol.description ==''">未知距离</s:if><s:else><s:property value="#vol.description"/></s:else></div>
                       </s:if>
                    </div>
                </div>
          </s:iterator>
          </div>
        </div>
     </div>
  <div class="person-hr"></div>
</body>
</html>
