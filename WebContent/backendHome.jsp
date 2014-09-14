<!DOCTYPE html>
<%@ include file="pages/commonHeader.jsp"%>
<html lang="en">
<head>
    <title>员工服务平台 后台管理系统</title>
    <link href="${rootPath}/jslib/flatlab/assets/morris.js-0.4.3/morris.css" rel="stylesheet" />

    <script src="${rootPath}/jslib/flatlab/assets/morris.js-0.4.3/morris.min.js" type="text/javascript"></script>
    <script src="${rootPath}/jslib/flatlab/assets/morris.js-0.4.3/raphael-min.js" type="text/javascript"></script>
</head>
<body>
<!--state overview start-->
<div class="row state-overview">
    <div class="col-lg-3 col-sm-6">
        <a href="javascript:selectMenu('volunteerManagement','?status=2')">
            <section class="panel">
                <div class="symbol terques">
                    <i class="fa fa-user"></i>
                </div>
                <div class="value">
                    <h1 class="count"><s:property value="dashBoardBean.volunteerCount" default="0" /> </h1>
                    <p>员工</p>
                </div>
            </section>
        </a>
    </div>
    <div class="col-lg-3 col-sm-6">
        <a href="javascript:selectMenu('courseManagement')">
            <section class="panel">
                <div class="symbol red">
                    <i class="fa fa-tags"></i>
                </div>
                <div class="value">
                    <h1 class=" count2"><s:property value="dashBoardBean.courseCount" default="0" /></h1>
                    <p>课程</p>
                </div>
            </section>
        </a>
    </div>
    <div class="col-lg-3 col-sm-6">
        <a href="javascript:selectMenu('trainManagement')">
            <section class="panel">
                <div class="symbol yellow">
                    <i class="fa fa-shopping-cart"></i>
                </div>
                <div class="value">
                    <h1 class=" count3"><s:property value="dashBoardBean.trainCount" default="0" /></h1>
                    <p>培训人次</p>
                </div>
            </section>
        </a>
    </div>
    <div class="col-lg-3 col-sm-6">
        <section class="panel">
            <div class="symbol blue">
                <i class="fa fa-bar-chart-o"></i>
            </div>
            <div class="value">
                <h1 class=" count4"><s:property value="dashBoardBean.bindingCount" default="0" /></h1>
                <p>微信绑定</p>
            </div>
        </section>
    </div>
</div>
<!--state overview end-->

<div class="row">
    <div class="col-lg-8">
        <!--custom chart start-->
        <div class="border-head">
            <h3>服务工时统计</h3>
        </div>
        <div class="custom-bar-chart">
            <div id="graph" class="graph"></div>
        </div>
        <!--custom chart end-->
    </div>
    <div class="col-lg-4">
        <!--new earning start-->
        <section class="panel">
            <header class="panel-heading">
            </header>
            <div class="panel-body">
                <div id="hero-donut1" class="bio-chart" style="height: 120px;"></div>
                <div class="bio-desk" style="height: 120px;">
                    <h4 class="red">员工职称</h4>
                    <p>院内注册 : <s:property value="dashBoardBean.registerByClient" default="0" />  </p>
                    <p>微信注册 : <s:property value="dashBoardBean.registerByWechat" default="0" /> </p>
                    <p>总计 : <s:property value="%{dashBoardBean.registerByClient + dashBoardBean.registerByWechat}" default="0"/> </p>
                </div>
            </div>
        </section>
        <section class="panel">
            <header class="panel-heading">
            </header>
            <div class="panel-body">
                <div id="hero-donut2" class="bio-chart" style="height: 120px;"></div>
                <div class="bio-desk" style="height: 120px;">
                    <h4 class="red">本月新增员工</h4>
                    <p>未绑定微信 : <s:property value="dashBoardBean.newUnbindingCount" default="0" /></p>
                    <p>绑定微信 : <s:property value="dashBoardBean.newBindingCount" default="0" /></p>
                    <p>总计 : <s:property value="%{dashBoardBean.newUnbindingCount + dashBoardBean.newBindingCount}" default="0" /></p>
                </div>
            </div>
        </section>
        <!--total earning end-->
    </div>
</div>
<script type="text/javascript">
    function checkForm(){
        return true;
    }

    $(document).ready(function(){

        // data stolen from http://howmanyleft.co.uk/vehicle/jaguar_'e'_type
        var tax_data = <s:property value="dashBoardBean.monthlyWorkingHours" escape="false"/>;

        Morris.Line({
            element: 'graph',
            data: tax_data,
            xkey: 'time',
            ykeys: ['hours'],
            labels: ['工时总计']
        });

        Morris.Donut({
            element: 'hero-donut1',
            data: [
                {label: '院内注册', value: <s:property value="dashBoardBean.registerByClient" /> },
                {label: '微信注册', value: <s:property value="dashBoardBean.registerByWechat" /> }
            ],
            colors: ['#41cac0', '#B0CCE1']
        });

        Morris.Donut({
            element: 'hero-donut2',
            data: [
                {label: '未绑定', value: <s:property value="dashBoardBean.newUnbindingCount" /> },
                {label: '已绑定', value: <s:property value="dashBoardBean.newBindingCount" /> }
            ],
            colors: ['#41cac0', '#B0CCE1']
        });
    });
</script>
<script type="text/javascript">

</script>
</body>
</html>