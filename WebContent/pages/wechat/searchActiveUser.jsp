<!DOCTYPE html>
<%--
  Created by IntelliJ IDEA.
  User: wangronghua
  Date: 14-3-22
  Time: 下午1:34
  To change this template use File | Settings | File Templates.
--%>
<head>
    <%@ include file="/pages/miniwechatHeader.jsp" %>

    <%--<link href="jslib/flatlab/css/style-front.css" rel="stylesheet">--%>
    <%--<link href="jslib/flatlab/css/style.css" rel="stylesheet">--%>

    <%--<script src="jslib/flatlab/js/jquery.scrollTo.min.js"></script>--%>
    <%--<script src="jslib/flatlab/js/jquery.nicescroll.js" type="text/javascript"></script>--%>
    <%--<script class="include" type="text/javascript" src="jslib/flatlab/js/jquery.cookie.js"></script>--%>
    <%--<script class="include" type="text/javascript" src="jslib/flatlab/js/jquery.dcjqaccordion.2.7.js"></script>--%>
    <style type="text/css">

        .social-link-footer li {
            float: left;
            margin: 0 10px 10px 0px;

        }

        .social-link-footer li a {
            color: #fff;
            background:#505b71;
            padding: 5px;
            width: 50px;
            #height: 50px;
            float: left;
            text-align: center;
            font-size: 20px;
            -webkit-transition: all .3s ease;
            -moz-transition: all .3s ease;
            -ms-transition: all .3s ease;
            -o-transition: all .3s ease;
            transition: all .3s ease;
        }

        .social-link-footer li a:hover {
            background: #F77B6F;
            -webkit-transition: all .3s ease;
            -moz-transition: all .3s ease;
            -ms-transition: all .3s ease;
            -o-transition: all .3s ease;
            transition: all .3s ease;
        }

        /*personal task*/

        .task-thumb {
            width: 90px;
            float: left;
        }

        .task-thumb img {
            border-radius: 4px;
            -webkit-border-radius: 4px;
        }

        .task-thumb-details {
            display: inline-block;
            margin: 25px 0 0 10px;
        }

        .task-progress {
            float: left;
        }

        .task-thumb-details h1, .task-thumb-details h1 a, .task-progress h1, .task-progress h1 a {
            color: #39b5aa;
            font-size: 18px;
            margin: 0;
            padding: 0;
            font-weight: 400;
        }

        .task-thumb-details p, .task-progress p {
            padding-top: 5px;
            color: #a4aaba;
        }

        .boder {
            border-top: 1px solid #DDDDDD;
        }

        p {
            margin: 0 0;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#" + '<s:property value="servicePlaceId"/>').css("background", "url('../img/accept_item.png') no-repeat scroll 180px 0px #F77B6F");
        });

        function selectServicePlace(selectedId, selectedName) {
            //$(".selection").css("background", "none repeat scroll 0 0 #505B71");
            //$("#"+selectedId).css("background", "url('../img/accept_item.png') no-repeat scroll 180px 0px #F77B6F");

            $("#servicePlaceId").val(selectedId);
            //$("#selectionDiv").css("display", "none");
            $("#queryForm").submit();
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
<section class="panel" style="margin-bottom: 0px;">
    <header class="panel-heading">
        地点
        <span class="tools pull-right">
            <a href="javascript:showSlection()" class="fa fa-pencil-square-o"><s:property value="servicePlaceBean.name" default="请选择地点" />[点击切换]</a>
        </span>
    </header>

    <s:iterator value="activeVolunteers" var="bean">
        <div class="panel-body boder" style="position:relative; padding-top: 0px; padding-bottom: 0px;">

            <a class="task-thumb col-xs-4" href="#">
                <img width="60px" height="50px" src="<s:property value='%{#bean.iconpath}'/>" class="" style="margin-right: 10px; width: 60px; height: 50px; position: static;" disable="">
            </a>
            <div class="task-thumb-details" style="margin: 0 0 0 0;">
                <p style="padding: 0px"><s:property value='%{#bean.name}'/></p>
                <%--<p style="padding: 0px"><s:property value="servicePlaceBean.name" /></p>--%>
                <p style="padding: 0px"><s:property value='%{#bean.cellPhone}'/></p>
                <p style="padding: 0px;"><s:date name="%{#bean.checkInTime}" format="yyyy/MM/d  d HH:mm:ss"/></p>
            </div>

            <p style="position: absolute; right:2px; top:0px;"><s:property value="%{#bean.description}" /> </p>

            <s:if test="#bean.status == 1">
                <img style="position: absolute; right:2px; top:20px;" src="img/wechat.jpg">
            </s:if>
            <s:if test="#bean.status == 0">
                <img style="position: absolute; right:2px; top:20px;" src="img/screen.jpg">
            </s:if>

        </div>
    </s:iterator>
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

<div style="display: none">
    <form id="queryForm" action="wechat/peopleHere.action" method="post">
        <input type="hidden" name="openID" value="<s:property value='openID'/>" >
        <input type="hidden" id="servicePlaceId" name="servicePlaceId" value="<s:property value='servicePlaceId'/>">
    </form>
</div>
</body>

<script src="jslib/flatlab/js/common-scripts.js"></script>

</html>
