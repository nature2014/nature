<%@ include file="../commonHeader.jsp" %>
<script type="application/javascript">
    jQuery.fn.dataTableExt.oPagination.iFullNumbersShowPages = 20;
</script>

<%-- 先去掉，为了更多的展示空间 --%>
<%--<s:if test="tableTitle != null && tableTitle.length() > 0"> <!--导航地址 -->--%>
<%--<div class="row">--%>
<%--<div class="col-lg-12">--%>
<%--<ul class="breadcrumb">--%>
<%--<li><a href="${rootPath}/backend/index.action"><i class="fa fa-home"></i>首页</a></li>--%>
<%--${tableTitle}--%>
<%--</ul>--%>
<%--<!--breadcrumbs end -->--%>
<%--</div>--%>
<%--</div>--%>
<%--</s:if>--%>
<!--end导航地址 -->
<!DOCTYPE html>
<html lang="en">
<head>
    <style>
        .main {
            height: 250px;
            overflow: hidden;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #e3e3e3;
            -webkit-border-radius: 4px;
            -moz-border-radius: 4px;
            border-radius: 4px;
            -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
            -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
            box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
        }
    </style>
</head>
<body>
<section class="panel">
    <!-- page start-->
    <section class="panel">
        <div class="panel-body" id="panelbody">
            <form class="form-horizontal tasi-form" action="${rootPath}${customPath}" method="get">
                <div class="form-group">
                    <s:set id="counter" value="0"/>
                    <s:iterator value="tableInit.aoColumns" var="column">
                        <s:if test="%{#column.isbSearchable()==true}">
                            <s:set id="counter" value="%{#counter+1}"/>
                            <s:if test="#counter % 3 ==1">
                                <div class="col-lg-12" >
                            </s:if>
                            <s:if test="#column.searchOptions==null">
                                <label class="col-lg-1 control-label"
                                       style="margin-bottom:10px;text-align:right;">${column.sTitle}</label>
                                <s:if test="#column.sClass=='cdate'">
                                    <div class="col-lg-3" style="margin-bottom:10px;">
                                        <input id="${column.mData}" type="text" class="form-control ${column.sClass}"
                                               value="" name="filter['${column.mData}']"  data-date-format="yyyy-mm-dd"
                                               placeholder="${column.sTitle}">
                                        <script>
                                            $("#${column.mData}").datepicker();
                                        </script>
                                    </div>
                                </s:if>
                                <s:else>
                                    <div class="col-lg-3" style="margin-bottom:10px;">
                                        <input id="${column.mData}" type="text" class="form-control ${column.sClass}"
                                               value="" name="filter['${column.mData}']"
                                               placeholder="${column.sTitle}">
                                    </div>
                                </s:else>
                            </s:if>
                            <s:else>
                                <label class="col-lg-1 control-label"
                                       style="margin-bottom:10px;text-align:right;">${column.sTitle}</label>

                                <div class="col-lg-3" style="margin-bottom:10px;">
                                    <select id="${column.mData}" type="text" class="form-control ${column.sClass}"
                                            value="" name="filter['${column.mData}']"
                                            placeholder="${column.sTitle}">
                                        <option value="">&nbsp;</option>
                                        <s:iterator value="#column.searchOptions[0]" status="rowstatus">
                                            <option value="<s:property value="#column.searchOptions[0][#rowstatus.index]"/>">
                                                <s:property
                                                        value="#column.searchOptions[1][#rowstatus.index]"/></option>
                                        </s:iterator>
                                    </select>
                                </div>
                            </s:else>
                            <s:if test="#counter % 3 ==0">
                                </div>
                            </s:if>
                        </s:if>
                    </s:iterator>
                    <s:if test="#counter % 3 !=0">
                </div>
                </s:if>

                <%--<a class="btn btn-info pull-right" style="margin-right:30px;margin-top: 15px;"--%>
                <%--onclick="$('#errorarea').html('');$('#${tableId}').dataTable()._fnAjaxUpdate()">--%>
                <%--<i class="fa fa-search"></i>--%>
                <%--查询--%>
                <%--</a>--%>
                <button id="query" class="btn btn-info" type="submit" style="margin-left:80px;margin-right:80px">
                    查询
                </button>
            </form>
        </div>

    </section>
</section>
</div>
</div>

<!-- custom js and jsp start-->
<s:if test="customJs != null && customJs.length() > 0">
    <script src="${customJs}" type="text/javascript"></script>
</s:if>

<s:if test="customJsp != null && customJsp.length() > 0">
    <s:include value="%{customJsp}"/>
</s:if>
<!-- custom js and jsp end-->

<!-- page end-->
</body>
<script>
    //没有CRUD操作
    operationButtons = [];

    //没有操作按钮
    options=[];
    var idName;
    var tableId = "${tableId}";
    var actionPrex = "${actionPrex}";
    var cellFormatter = {};
    window.exportExcel = false;


    $(function () {
        //图片快速浮优
        jQuery(".fancybox").fancybox();
    });


    // 格式化js时间
    var formatDateTime = function (obj, IsMi) {
        var myDate = new Date(obj);
        var year = myDate.getFullYear();
        var month = ("0" + (myDate.getMonth() + 1)).slice(-2);
        var day = ("0" + myDate.getDate()).slice(-2);
        var h = ("0" + myDate.getHours()).slice(-2);
        var m = ("0" + myDate.getMinutes()).slice(-2);
        var s = ("0" + myDate.getSeconds()).slice(-2);
        var mi = ("00" + myDate.getMilliseconds()).slice(-3);
        if (IsMi == true) {
            return year + "-" + month + "-" + day;
        }
        else {
            return year + "-" + month + "-" + day + " " + h + ":" + m + ":" + s;
        }
    };

    //格式化createTime  modifyTime
    cellFormatter["modifyTime"] = cellFormatter["createTime"] = function (data, type, full) {
        if (data != null) {
            return formatDateTime(data.time, false);
        } else {
            return "";
        }
    }

</script>

</html>