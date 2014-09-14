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
        .thumb {
            display: inline-block;
            margin: 0;
            padding: 5px;
            position: relative;
            text-align: left;
        }

        .product-table {
            border-collapse: collapse;
            table-layout: fixed;
            width: 100%;
            border-spacing: 0;
        }

        .product-image {
            /*width: 90%;*/
        }

        .product-price {
            /*width: 10%;*/
        }

        .product-table tbody td {
            border: 1px solid #f5f5f5;
        }

        .product-table td {
            line-height: 1.5;
            padding-left: 20px;
            padding-right: 0;
            text-align: left;
            overflow: hidden;
            vertical-align: top;
        }

        .product-table td.column {
            border-left: 0 none;
            border-right: 0 none;
            text-align: left;
            background: none repeat scroll 0 0 #f5f5f5;
            border-bottom-color: #f5f5f5;
        }

        .product-table .sep-row {
            height: 10px;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#" + '<s:property value="servicePlaceId"/>').css("background", "url('../img/accept_item.png') no-repeat scroll 180px 0px #F77B6F");
        });

        function selectProductLevel(selectedId, selectedName) {
            //$(".selection").css("background", "none repeat scroll 0 0 #505B71");
            //$("#"+selectedId).css("background", "url('../img/accept_item.png') no-repeat scroll 180px 0px #F77B6F");

            $("#productLevelId").val(selectedId);
            //$("#selectionDiv").css("display", "none");
            $("#queryForm").submit();
        }

        function showSlection(){
            $("#selectionDiv").css("display", "");
        }

        function cancelSelection(){
            $("#selectionDiv").css("display", "none");
        }

    </script>
</head>
<body>
<section class="panel" style="margin-bottom: 0px;">
    <header class="panel-heading">
        产品分类
        <span class="tools pull-right">>
            <a href="javascript:showSlection()" class="fa fa-pencil-square-o"><s:property value="productLevelBean.name" default="所有产品" />[点击切换]</a>
        </span>
    </header>

    <table class="product-table">
        <col class="product-image" width="*">
        <col class="product-price" width="100px">
        <s:iterator value="productList" var="bean">
            <tbody>
                <tr class="sep-row"><td colspan="2"></td></tr>
                <tr>
                    <td class="column">
                        <span><s:property value="#bean.name" /></span>
                    </td>
                    <td class="column">
                        <span>单价</span>
                    </td>
                </tr>
                <tr>
                    <td class="product-image">
                        <a href="#" title="查看产品详情" hidefocus="true">
                            <s:iterator value="#bean.image" var="image" status="st">
                                <s:if test="#st.index < 3">
                                    <div class="thumb">
                                        <img src="/upload/getImage.action?getthumb=<s:property value='%{#image.fileName}'/>">
                                    </div>
                                </s:if>
                            </s:iterator>
                        </a>
                        <%--<div class="desc">--%>
                            <%--<p class="baobei-name">--%>
                                <%--<a data-point-url="http://gm.mmstat.com/listbought.2.6" class="J_MakePoint" href="http://item.taobao.com/item.htm?id=38642577049&amp;_u=e5ragbfaad8" target="_blank">--%>
                                    <%--红玫瑰七夕情人节鲜花速递全国花店送花上海北京杭州广州武汉昆明--%>
                                <%--</a>--%>
                                <%--<a data-point-url="http://gm.mmstat.com/listbought.2.7" class="J_MakePoint" target="_blank" href="http://trade.taobao.com/trade/detail/tradeSnap.htm?tradeID=750905701044381&amp;snapShot=true">[交易快照]</a>--%>
                            <%--</p>--%>
                        <%--</div>--%>
                    </td>
                    <td class="product-price">
                        <%--<em class="origin-price special-num">409.00</em><br>--%>
                        <i class="special-num"><s:property value="#bean.price" /></i>
                    </td>
                </tr>
            </tbody>
        </s:iterator>
    </table>
</section>

<div id="selectionDiv" style="position:absolute; top:0px; left:0px; width:100%; height:100%; background-color: #ffffff; display: none;">
    <div class="container">
        <div class="row">
            <div class="col-xs-12">
                <section class="panel" style="margin-bottom: 0px;">
                    <header class="panel-heading">
                        大自艺术广告
                    </header>
                    <div class="panel-body" style="display: block; ">
                        <form action="#" class="form-horizontal  tasi-form">
                            <div class="form-group" style="margin-bottom: 0px;">
                                <ul class="social-link-footer list-unstyled">
                                    <s:iterator value="productLevelList" var="productLevel">
                                        <li>
                                            <a id="<s:property value="#productLevel.id"></s:property>" class="selection" style="width: 200px;"
                                            href="javascript:selectProductLevel('<s:property value="#productLevel.id"/>', '<s:property value="#productLevel.name"/>')">
                                            <s:property value="#productLevel.name" />
                                            </a>
                                        </li>
                                    </s:iterator>
                                </ul>
                            </div>
                        </form>
                    </div>
                </section>
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
    <form id="queryForm" action="wechat/productList.action" method="post">
        <input type="hidden" name="openID" value="<s:property value='openID'/>" >
        <input type="hidden" id="productLevelId" name="productLevelBean.id" value="<s:property value='productLevelBean.id'/>">
    </form>
</div>
</body>

<script src="jslib/flatlab/js/common-scripts.js"></script>

</html>
