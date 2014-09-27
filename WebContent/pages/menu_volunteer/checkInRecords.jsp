<!DOCTYPE html>
<%@ include file="../bootstrapHeader.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="${rootPath}/css/train.css" rel="stylesheet">
    <title>员工服务签入签出</title>
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
        <div class="bg-fh"><img src="${rootPath}/img/back.png" width="35" height="35" onclick="location='index.action'"
                                style="cursor: pointer"/></div>
        <s:if test="aub!=null">
            <div class="bg-top">我的签出</div>
        </s:if>
        <s:else>
            <div class="bg-top">我的签入</div>
        </s:else>

        <div class="bg-username"><s:property value="#session['sessionUser'].name"/></div>
        <div class="bg-touxiang"><img src="${rootPath}/<s:property value="#session['sessionUser'].iconpath"/>" width="50"
                                      height="50" /></div>
    </div>

    <div class="bg-right2">
        <div class="bg-title2" style="font-size:30px">院内</div>
        <div class="bg-time" id="timewatcher">加载当前时间</div>
        <s:iterator value="places" var="parent">
            <s:if test="#parent.id!='out'">

                <div class="bg-title2" id="<s:property value="#parent.id"/>" style="display:none"><s:property value="#parent.name"/></div><div style="clear:both"></div>
                <s:iterator value="#parent.selections" var="us">
                    <s:if test="aub.servicePlaceId==#us.id">
                        <div class="hosp-green hosp-focus">
                            <div class="plase-img"><img src="<s:property value="#us.serviceicon"/>"></div>
                            <div class="plase-font"><s:property value="#us.name"/></div>
                            <form action="${rootPath}/userFront/checkOut.action" method="post">
                                <input type="hidden" name="servicePlaceId" value="<s:property value="#us.id"/>"/>
                            </form>
                        </div>
                        <script>jQuery('#'+'<s:property value="#parent.id"/>').css({display:''});</script>
                    </s:if>
                    <s:else>
                        <s:if test="aub==null">
                            <div class="hosp-green"
                                 <s:if test="aub==null">onclick="jQuery('.hosp-green').removeClass('hosp-focus');jQuery(this).addClass('hosp-focus');"</s:if>>
                                <div class="plase-img"><img src="<s:property value="#us.serviceicon"/>"></div>
                                <div class="plase-font"><s:property value="#us.name"/></div>
                                <form action="${rootPath}/userFront/checkInSubmit.action" method="post">
                                    <input type="hidden" name="servicePlaceId" value="<s:property value="#us.id"/>"/>
                                </form>
                                <script>jQuery('#'+'<s:property value="#parent.id"/>').css({display:''});</script>
                            </div>
                        </s:if>
                    </s:else>
                </s:iterator>
                <div style="clear:both">
            </s:if>
        </s:iterator>
        <%--<s:iterator value="servicePlaces" var="parent">--%>
           <%--<s:if test="#parent.type==1 && #parent.area==0">--%>
                <%--<div class="bg-title2" id="<s:property value="#parent.id"/>" style="display:none"><s:property value="#parent.name"/></div><div style="clear:both"></div>--%>
               <%--<s:iterator value="servicePlaces" var="us">--%>
                    <s:if test="#us.type==0 && #parent.id==#us.parentid">
                        <s:if test="aub.servicePlaceId==#us.id">
                            <div class="hosp-green hosp-focus">
                                <div class="plase-img"><img src="<s:property value="#us.serviceicon"/>"></div>
                                <div class="plase-font"><s:property value="#us.name"/></div>
                                <form action="${rootPath}/userFront/checkOut.action" method="post">
                                    <input type="hidden" name="servicePlaceId" value="<s:property value="#us.id"/>"/>
                                </form>
                            </div>
                            <script>jQuery('#'+'<s:property value="#parent.id"/>').css({display:''});</script>
                        </s:if>
                        <s:else>
                          <s:if test="aub==null">
                            <div class="hosp-green"
                                 <s:if test="aub==null">onclick="jQuery('.hosp-green').removeClass('hosp-focus');jQuery(this).addClass('hosp-focus');"</s:if>>
                                <div class="plase-img"><img src="<s:property value="#us.serviceicon"/>"></div>
                                <div class="plase-font"><s:property value="#us.name"/></div>
                                <form action="${rootPath}/userFront/checkInSubmit.action" method="post">
                                    <input type="hidden" name="servicePlaceId" value="<s:property value="#us.id"/>"/>
                                </form>
                                <script>jQuery('#'+'<s:property value="#parent.id"/>').css({display:''});</script>
                            </div>
                          </s:if>
                        </s:else>
                    </s:if>
               <%--</s:iterator>--%>
               <%--<div style="clear:both"></div>--%>
           <%--</s:if>--%>
        <%--</s:iterator>--%>
    </div>

    <div class="hosp-hr">
        <script>
            function checkIn() {
                var focusNode = jQuery(".hosp-focus");
                if (focusNode.length == 0) {
                    alert("请先选择签入地点");
                } else {
                    focusNode.find("form").submit();
                }
            }

            function checkOut() {
                var focusNode = jQuery(".hosp-focus");
                if (focusNode.length == 0) {
                    alert("没有有效的签出地点");
                } else {
                    focusNode.find("form").submit();
                }
            }
        </script>
        <s:if test="aub!=null">
            <input name="" class="Infor-btn" type="button" value="确定签出" onclick="checkOut()"/>
        </s:if>
        <s:else>
            <input name="" class="Infor-btn" type="button" value="确定签入" onclick="checkIn()"/>
        </s:else>
        <input name="" class="Infor-btn" type="button" value="取消" onclick="location='index.action'"
               style="cursor: pointer"/>
        <input name="" class="Infor-btn" type="button" value="签到历史"
               onclick="$('#panelbody').toggle();$('#panelbodybullet').toggleClass('fa fa-chevron-up');$('#panelbodybullet').toggleClass('fa fa-chevron-down');"
               style="cursor: pointer"/>
    </div>
    <div style="clear: both"></div>
    <section class="panel">
        <header class="panel-heading"
                onclick="$('#panelbody').toggle();$('#panelbodybullet').toggleClass('fa fa-chevron-up');$('#panelbodybullet').toggleClass('fa fa-chevron-down');"
                style="cursor: pointer">
              <span class="tools pull-right">
                <span id="panelbodybullet" class="fa fa-chevron-up" style="cursor: pointer"></span>
              </span>
        </header>
        <div class="panel-body" id="panelbody" style="display: none">
            <div class="row state-overview">
                <table cellspacing="0" cellpadding="0" border="0" class="table table-striped table-advance table-hover"
                       id="userList">
                    <thead>
                    <tr>
                        <th class="column-name">服务地点</th>
                        <th class="column-datetime">签到时间</th>
                        <th class="column-datetime">签出时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <s:iterator value="userServices" var="us">
                        <tr>
                            <td><s:property value="%{#us.servicePlaceBean.name}"/></td>
                            <td><s:date name="#us.checkInTime" format="yyyy-MM-dd HH:mm:ss"></s:date></td>
                            <td><s:date name="#us.checkOutTime" format="yyyy-MM-dd HH:mm:ss"></s:date></td>
                        </tr>
                    </s:iterator>
                    </tbody>
                </table>
            </div>
            <div class="row state-overview">
                仅显示最近10条记录
            </div>
        </div>
    </section>
</div>
</body>
</html>
