<!DOCTYPE html>
<%--
  Created by IntelliJ IDEA.
  User: wangronghua
  Date: 14-3-22
  Time: 下午1:34
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <%@ include file="/pages/miniwechatHeader.jsp" %>

    <link href="jslib/flatlab/css/style-front.css" rel="stylesheet">

    <script type="text/javascript">

        function selectServicePlace(selectedId, selectedName) {
            $(".selection").css("background", "none repeat scroll 0 0 #505B71");
            $("#"+selectedId).css("background", "url('../img/accept_item.png') no-repeat scroll 180px 2px #F77B6F");

            $("#servicePlaceId").val(selectedId);
            $("#servicePlaceName").text(selectedName);
            $("#selectionDiv").css("display", "none");
        }

        function showSlection(){
            $("#selectionDiv").css("display", "");
        }

        function cancelSelection(){
            $("#selectionDiv").css("display", "none");
        }

        function checkForm() {
            if($("#servicePlaceId").val()){
                return true;
            }
            showSlection();
            alert("请选择您要参与服务的地点！");
            return false;
        }
    </script>
</head>
<body>
    <section class="panel">
        <%-- 消息引用 --%>
        <s:include value="../strutsMessage.jsp"/>

        <div class="panel-body">
            <form action="wechat/checkInSubmit.action" method="post" class="form-horizontal" onsubmit="return checkForm()">
                <input type="hidden" name="openID" value="<s:property value='openID'/>">
                <input type="hidden" name="userID" value="<s:property value='userID'/>">
                <input type="hidden" id="servicePlaceId" name="servicePlaceId" value="<s:property value='servicePlaceId'/>">
                <div class="form-group">
                    <label class="col-xs-4  control-label">微信用户</label>
                    <div class="col-xs-8">
                        <p class="form-control-static"><s:property value='wechatUser'/></p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4  control-label" >姓名</label>
                    <div class="col-xs-8">
                        <p class="form-control-static"><s:property value='userName'/></p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4  control-label" >时间</label>
                    <div class="col-xs-8">
                        <p class="form-control-static">
                            <s:date name="currentTime" format="yyyy-MM-dd HH:mm:ss"/>
                        </p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4  control-label">地点</label>
                    <div class="col-xs-8">
                        <button id="servicePlaceName" name="servicePlaceName" type="button" class="btn btn-white" style="border-style: dashed;" onclick="showSlection()">请选择</button>
                    </div>
                </div>
                <button type="submit" class="btn btn-info btn-block">我要签入</button>
                <%--<button type="button" class="btn btn-info btn-block" onclick="custom_close()">返回微信</button>--%>
            </form>
        </div>
    </section>

    <div id="selectionDiv" style="position:absolute; top:0px; left:0px; width:100%; height:100%; background-color: #ffffff; display: none;">
        <div class="container">
            <div class="row">
                <div class="col-xs-12">

                    <s:iterator value="places" var="servicePlace">
                        <section class="panel" style="margin-bottom: 0px;">
                            <header class="panel-heading">
                                <s:property value="#servicePlace.name"></s:property>
                            </header>
                            <div class="panel-body" style="display: block; ">
                                <form action="#" class="form-horizontal  tasi-form">
                                    <div class="form-group" style="margin-bottom: 0px;">
                                        <ul class="social-link-footer list-unstyled">
                                            <s:iterator value="%{#servicePlace.selections}" var="selection">
                                                <li>
                                                    <a id="<s:property value="#selection.id"></s:property>" class="selection" style="width: 200px;"
                                                                                    href="javascript:selectServicePlace('<s:property value="#selection.id"/>', '<s:property value="#selection.name"/>')">
                                                        <s:property value="#selection.name" />&nbsp;[<s:property value="#selection.count" />]
                                                    </a>
                                                </li>
                                            </s:iterator>
                                        </ul>
                                    </div>
                                </form>
                            </div>
                        </section>
                    </s:iterator>
                </div>
            </div>
            <div class="row" style="margin-bottom: 20px; text-align: center;">
                <div class="col-xs-12" >
                    <button type="button" class="btn btn-info btn-block" onclick="cancelSelection()">返回上一页</button>
                </div>
            </div>

        </div>
    </div>
</body>
</html>
