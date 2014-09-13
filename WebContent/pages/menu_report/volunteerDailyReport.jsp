
<%@ include file="../commonHeader.jsp"%>

<html lang="en">
<html>
<head>
    <title>签到管理</title>

    <link href="${rootPath}/jslib/flatlab/assets/morris.js-0.4.3/morris.css" rel="stylesheet" />

    <script src="${rootPath}/jslib/flatlab/assets/morris.js-0.4.3/morris.min.js" type="text/javascript"></script>
    <script src="${rootPath}/jslib/flatlab/assets/morris.js-0.4.3/raphael-min.js" type="text/javascript"></script>


    <style type="text/css">
        .mt15 {
            margin-top: 15px;
        }

    </style>

    <script type="text/javascript">
        function checkForm(){
            return true;
        }

        $(document).ready(function(){

            //morris chart
            $(function () {
                // data stolen from http://howmanyleft.co.uk/vehicle/jaguar_'e'_type
                var tax_data = <s:property value="jsonData" escape="false"/>;
                Morris.Line({
                    element: 'graph',
                    data: tax_data,
                    xkey: 'time',
                    ykeys: <s:property value="jsonYKeys" escape="false"/>,
                        labels: <s:property value="jsonLabels" escape="false"/>
            });
        });
        });
    </script>
</head>
<body>
<section class="panel">
    <ul class="breadcrumb">
        <li>工时管理</li>
        <li class="active">员工日工时统计</li>
    </ul>

    <div class="panel-body">
        <div class="adv-table dataTables_wrapper form-inline">
            <form class="form-horizontal filter-panel" method="post" action="backend/report/volunteerDailyReport.action">
                <div class="row mt15">
                    <div class="col-lg-3 col-md-3 filter-column">
                        <label class="pull-left control-label" for="selectYearDate">年月</label>

                        <div class="col-lg-9 col-md-9 filter-component-column">
                            <input class="form-control" id="selectYearDate" name="selectYearDate" value="<s:property value='selectYearDate'/>" data-date-format="yyyy-mm">
                            <script>
                                $("#selectYearDate").datepicker();
                            </script>
                        </div>
                    </div>

                    <div class="col-lg-3 col-md-3 filter-column">
                        <label class="pull-left control-label" for="name">姓名</label>

                        <div class="col-lg-9 col-md-9 filter-component-column">
                            <input type="text" value="<s:property value='name'/>" placeholder="姓名" class="form-control input-sm filter-component" name="name" id="name">
                        </div>
                    </div>

                    <div class="col-lg-3 col-md-3 filter-column">
                        <label class="pull-left control-label" for="code">工号</label>

                        <div class="col-lg-9 col-md-9 filter-component-column">
                            <input type="text" value="<s:property value='code'/>" placeholder="工号" class="form-control input-sm filter-component" name="code" id="code">
                        </div>
                    </div>

                    <div class="col-lg-3 col-md-3">
                        <button class="btn btn-success pull-right" type="submit">
                            查询
                        </button>
                    </div>
                </div>
            </form>
            <!-- page start-->
            <div id="morris">
                <div class="row">
                    <div class="col-lg-12">
                        <section class="panel">
                            <header class="panel-heading">
                            </header>
                            <div class="panel-body">
                                <%@ include file="../strutsMessage.jsp"%>
                                <div id="graph" class="graph"></div>
                            </div>
                        </section>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>