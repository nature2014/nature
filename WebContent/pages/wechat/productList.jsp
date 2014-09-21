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


        #filters {
            padding:0;
            list-style:none;
        }
        #filters li {
            display:inline-block;
            margin-right: 5px;
            margin-bottom: 10px;
        }
        #filters li span {
            font-size:12px;
            display: block;
            padding: 10px 10px;
            color: #455670;
            background: #F2F2F2;
            border: 1px solid #EEE;
            text-decoration: none;
            cursor: pointer;
            text-transform: capitalize;
            transition: 0.5s all;
            -webkit-transition: 0.5s all;
            -moz-transition: 0.5s all;
            -o-transition: 0.5s all;
        }
        #filters li span.active,#filters li span:hover {
            background: #6AD1DD;
            border: 1px solid #53C4D1;
            color:#fff;
        }

    </style>
    <script type="text/javascript">
        $(document).ready(function(){
            $('#query_body').toggle();

            loadNextNRecords();

            $(".fancybox").fancybox();

            $(".filter").on("click", function () {
                $(".filter").removeClass('active');
                $(this).addClass('active');
                //刷新产品数据
                selectProductLevel($(this).attr('data-level'));
            });
        });

        var index = 0;
        var totalCount = <s:property value="totalCount" />;
        function selectProductLevel(selectedId) {
            //$(".selection").css("background", "none repeat scroll 0 0 #505B71");
            //$("#"+selectedId).css("background", "url('../img/accept_item.png') no-repeat scroll 180px 0px #F77B6F");

            $("#productLevelId").val(selectedId);
            //$("#selectionDiv").css("display", "none");
            $("#queryForm").submit();
        }

        function loadNextNRecords() {
            var actionUrl = "${rootPath}/wechat/loadRecords.action?productLevelBean.id=<s:property value='productLevelBean.id' />&index=" + index;
            $.ajax(
            {
                type        : "get",
                dataType    : "json",
                url         : actionUrl,
                complete    : function(data) {
                    console.log(data.responseText);
                    $("#productTable").append(data.responseText);
                    if(index >= totalCount) {
                        $("#loadButton").text("总共（"+totalCount+"）条记录！");
                        $("#loadButton").attr("disabled","disabled");
                    }
                }
            });
        }

    </script>
</head>
<body>
<section class="panel" style="margin-bottom: 0px;">
    <header class="panel-heading">
        产品分类
        <span class="tools pull-right">
            <a class="fa fa-chevron-up" href="javascript:void(0)" onclick="$('#query_body').toggle();$(this).toggleClass('fa-chevron-up fa-chevron-down');">
                <s:property value="productLevelBean.name" default="所有产品" />
            </a>
        </span>
    </header>
    <section id="query_body" class="panel-body">
        <form action="#" class="form-horizontal  tasi-form">
            <ul id="filters">
                <li>
                    <span data-filter="app card icon web" class="filter">&nbsp;&nbsp;全&nbsp;&nbsp;&nbsp;&nbsp;部&nbsp;&nbsp;</span>
                </li>
                <s:iterator value="productLevelList" var="productLevel">
                    <li>
                        <span data-level="<s:property value='#productLevel.id' />" data-filter="logo web" class="filter">
                            <s:property value="#productLevel.name" />
                        </span>
                    </li>
                </s:iterator>
            </ul>
        </form>
    </section>
</section>

<table id="productTable" class="product-table">
    <col class="product-image" width="*">
    <col class="product-price" width="100px">

</table>

<div class="row" style="margin: 5px; text-align: center;">
    <div class="col-xs-12" >
        <button type="button" id="loadButton" class="btn btn-info btn-block" onclick="loadNextNRecords()">加载更多...</button>
    </div>
</div>

</div>

<div style="display: none">
    <form id="queryForm" action="${rootPath}/wechat/productList.action" method="post">
        <input type="hidden" name="openID" value="<s:property value='openID'/>" >
        <input type="hidden" id="productLevelId" name="productLevelBean.id" value="<s:property value='productLevelBean.id'/>">
    </form>
</div>
</body>

<%--<script src="${rootPath}/jslib/flatlab/js/common-scripts.js"></script>--%>

</html>
