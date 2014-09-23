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

                                <div class="col-lg-3" style="margin-bottom:10px;">
                                    <input id="${column.mData}" type="text" class="form-control ${column.sClass}"
                                           value="" name="filter['${column.mData}']"
                                           placeholder="${column.sTitle}">
                                </div>
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

    $(function () {
        //图片快速浮优
        jQuery(".fancybox").fancybox();
    });

    $(document).ready(function () {
        //intial opration button
        $("#operationbutton").html(operationButtons.join("&nbsp;"));
        $(".btn.btn-info").click(function (event) {
            event.stopPropagation();
        });

        //获取来自Dashborad发送过来的URL上的参数，然后设置查询条件.
        var foundSearch = false;
        <s:iterator value="#parameters">
        {
            var searcher = $(".form-horizontal.tasi-form [name='<s:property value="key"/>']");
            searcher.val('<s:property value="value"/>');
            if (searcher.length > 0) {
                foundSearch = true;
            }
        }
        </s:iterator>
        if (foundSearch) {
            //打开查询搜索条件框
            $('.form-horizontal.tasi-form .panel-heading').click();
        }

        var tableUrl = "${actionPrex}/initTable.action?${addButtonParameter}";
        var param = {};

        $.getJSON(tableUrl, param, function (initParam) {
            if (initParam.disableTools) {
                $('#tableTools').css('display', 'none');
            }
            idName = initParam.idName;
            var columns = [];
            for (var i = 0; i < initParam.aoColumns.length; i++) {
                if (typeof cellFormatter[initParam.aoColumns[i].mData] == "function") {
                    initParam.aoColumns[i].mRender = cellFormatter[initParam.aoColumns[i].mData];
                }
                if (initParam.aoColumns[i].hiddenColumn != true) {
                    columns.push(initParam.aoColumns[i]);
                }
            }
            /*
             * Initialse DataTables, with no sorting on the 'details' column
             */

            var oTable = $('#${tableId}').dataTable({
                "bProcessing": initParam.bProcessing,
                "bServerSide": initParam.bServerSide,
                "iDisplayLength": initParam.iDisplayLength,
                "aLengthMenu": [
                    [ "10", "25", "50", "100", "500"],
                    ["10", "25", "50", "100", "500"]
                ],
                "bStateSave": true, //save state that keep page in cookie.
                bSortClasses: false,
                "sPaginationType": 'full_numbers',
                "aoColumns": columns,
                "sAjaxSource": "${actionPrex}/queryTable.action?${addButtonParameter}",
                //"sDom": '<"H"lT><"clear">rt<"F"ip>',
                "sDom": 'lrt<"F"ip>',
                "oLanguage": {
                    "oPaginate": {
                        "sPrevious": "上一页",
                        'html': '<button title="删除" style="margin-left:5px" class="btn btn-danger btn-xs" onclick="options[\'delete\'].onClick(this)"><i class="fa fa-trash-o "></i></button>',
                        "sNext": "下一页",
                        "sLast": "末页",
                        "sFirst": "首页"
                    },
                    sProcessing: '正在努力加载，请稍后！',
                    "sLengthMenu": "每页显示 _MENU_ 条",
                    "sZeroRecords": "无数据",
                    "sInfo": "显示第 _START_ 到 _END_ 条, 共 _TOTAL_ 条.",
                    "sInfoEmpty": "无数据",
                    "sInfoFiltered": "(filtered from _MAX_ total records)"
                },
                "fnDrawCallback": function (oSettings) {
                    if (initParam.hasDetails > 0) {
                        // show details
                        if ($('#${tableId} thead tr th:first[arias="showDetails"]').length == 0) {
                            $('#${tableId} thead tr').each(function () {
                                var thObj = document.createElement('th');
                                thObj.setAttribute("arias", "showDetails");
                                this.insertBefore(thObj, this.childNodes[0]);
                            });
                        }

                        var nCloneTd = document.createElement('td');
                        nCloneTd.innerHTML = '<img class="operation" src="${rootPath}/jslib/flatlab/assets/advanced-datatable/examples/examples_support/details_open.png">';
                        //nCloneTd.className = "center";
                        $(nCloneTd).css("width", "50px");
                        $('#${tableId} tbody tr').each(function (i) {
                            this.insertBefore(nCloneTd.cloneNode(true), this.childNodes[0]);
                        });
                        $('#${tableId} tbody td').on('click', 'img.operation', function () {
                            var nTr = $(this).parents('tr')[0];
                            if (oTable.fnIsOpen(nTr)) {
                                // This row is already open - close it
                                $(this).attr("src", "${rootPath}/jslib/flatlab/assets/advanced-datatable/examples/examples_support/details_open.png");
                                oTable.fnClose(nTr);
                            } else {
                                //   Open this row
                                $(this).attr("src", "${rootPath}/jslib/flatlab/assets/advanced-datatable/examples/examples_support/details_close.png");
                                oTable.fnOpen(nTr, fnFormatDetails(oTable, nTr), 'details');
                                $('td.details', $(nTr).next()).attr("colspan", nTr.childNodes.length);
                            }
                        });
                    }
                    // add options
                    var totalOptions = 0;
                    for (var p in options) {
                        totalOptions++;
                    }
                    if (totalOptions > 0 && $('#${tableId} thead tr th:last[arias="options"]').length == 0) {
                        $('#${tableId} thead tr').each(function () {
                            var thObj = document.createElement('th');
                            thObj.setAttribute("arias", "options");
                            thObj.innerHTML = "操作";
                            $(this).append(thObj);
                            $(thObj).css({width: '' + totalOptions * 40 + 'px'});
                        });
                    }
                    if (totalOptions > 0) {
                        $('#${tableId} tbody tr').each(function (i) {
                            var nCloneTd = document.createElement('td');
                            $(this).append(nCloneTd);
                            for (var p in options) {
                                $(nCloneTd).append(options[p].html);
                            }
                        });
                    }
                },
                "fnServerData": function (sSource, aoData, fnCallback, oSettings) {
                    /* //======= method one===========
                     // Add some extra data to the sender
                     aoData.push( { "name": "more_data", "value": "my_value" } );
                     $.getJSON( sSource, aoData, function (json) {
                     // Do whatever additional processing you want on the callback, then tell DataTables
                     fnCallback(json)
                     } );
                     //======= method one END=========== */

                    //========method two==================
                    var mDataObj = {};
                    var sortObj = {};
                    var iMax = 0;
                    for (var n = 0; n < aoData.length; n++) {
                        if (aoData[n].name == "iColumns") {
                            iMax = aoData[n].value;
                        }
                        if (aoData[n].name == "mDataProp_0") {
                            for (var i = 0; i < iMax; i++) {
                                mDataObj[aoData[n].name] = aoData[n].value;
                                n++;
                            }
                        }
                        if (aoData[n].name == "iSortCol_0") {
                            for (var i = 0; i < iMax; i++) {
                                if (aoData[n].name == "iSortCol_" + i) {
                                    sortObj[mDataObj["mDataProp_" + aoData[n].value]] = aoData[++n].value;
                                    n++;
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                    for (var p in sortObj) {
                        aoData.push({ "name": "sort['" + p + "']", "value": sortObj[p] });
                    }
                    <s:iterator value="tableInit.aoColumns" var="column">
                    <s:if test="%{#column.isbSearchable()==true}">
                    if ($('#${column.mData}').val() != '') {
                        aoData.push({ "name": "filter['${column.mData}']", "value": $('#${column.mData}').val() });
                    }
                    </s:if>
                    </s:iterator>

                    if (window.exportExcel == true) {
                        window.exportExcel = false;
                        var form = $("#exportForm");
                        //removed all hidden element in this exportForm.
                        form.empty();
                        for (var i = 0; i < aoData.length; i++) {
                            var hiddenElement = $("<input>");
                            hiddenElement.attr("type", "hidden");
                            hiddenElement.attr("name", aoData[i].name);
                            hiddenElement.attr("value", aoData[i].value);
                            form.append(hiddenElement);
                        }
                        var parameters = "${addButtonParameter}";
                        if (parameters != null) {
                            var splits = parameters.split("&");
                            for (var i = 0; i < splits.length; i++) {
                                var splitEqual = splits[i].split("=");
                                var hiddenElement = $("<input>");
                                hiddenElement.attr("type", "hidden");
                                hiddenElement.attr("name", splitEqual[0]);
                                hiddenElement.attr("value", splitEqual[1]);
                                form.append(hiddenElement);
                            }
                        }
                        this.oApi._fnProcessingDisplay(oSettings, false);
                        form.submit();
                    } else {
                        oSettings.jqXHR = $.ajax({
                            "dataType": 'json',
                            "type": "POST",
                            "url": sSource,
                            "data": aoData,
                            "success": function (result, status, response) {
                                // Do whatever additional processing you want on the callback, then tell DataTables
                                fnCallback(result);
                                $('#${tableId}').css("width", "100%");
                            }
                        });
                    }
                    //========method two END==================
                }
            });
        });
    });

</script>

</html>