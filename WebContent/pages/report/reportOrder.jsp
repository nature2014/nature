<!--main content start-->
<script src="${rootPath}/jslib/esl/esl.js" type="text/javascript" charset="utf-8"></script>

<script>
    require.config({
        packages: [
            {
                name: 'echarts',
                location: '${rootPath}/jslib/echart-2.0.1',
                main: 'echarts'
            },
            {
                name: 'zrender',
                location: '${rootPath}/jslib/zrender-2.0.2',
                main: 'zrender'
            }
        ]
    });
</script>

<script src="${rootPath}/js/inlinereadable.js"></script>
<div id="graphic" class="col-md-12">
</div>
<table class="table table-striped table-hover table-bordered" id="orderGridId">
</table>
</div>


<script type="text/javascript">
    function drawChart(ec, divName, optionData) {
        // 基于准备好的dom，初始化echarts图表
        $("#graphic").append('<div class="col-md-6" ><div id="' + divName + '" class="main"></div></div>');
        var myChart = ec.init(document.getElementById(divName));
        myChart.setOption(optionData);
    }
    // 使用
    require(
            [
                'echarts',
                'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
                'echarts/chart/pie' // 使用饼图就加载pie模块，按需加载
            ],
            function (ec) {
                $.ajax({
                    type: "get",//使用get方法访问后台
                    dataType: "json",//返回json格式的数据
                    contentType: "application/json",
                    url: "${rootPath}/backend/orderreport/reportOrderData.action?filter['customerId']=${model.filter['customerId'][0]}&filter['resOfficer']=${model.filter['resOfficer'][0]}&filter['customerCellPhone']=${model.filter['customerCellPhone'][0]}&filter['createTime_gteq']=${model.filter['createTime_gteq'][0]}&filter['createTime_lteq']=${model.filter['createTime_lteq'][0]}",
                    success: function (msg) {//msg为返回的数据，在这里做数据绑定
                        window.orderGridId.fnAddData(msg.dataList);
                        if (msg.list.length > 0) {
                            $.each(msg.list, function (i, obj) {
                                drawChart(ec, 'main' + i, obj.data);
                            });
                        }
                    }
                });
            }
    );
    //没有CRUD操作
    operationButtons = [];

    //没有操作按钮
    options = [];
    //表格处理
    window.orderGridId = jQuery.editTable.init({tableId: 'orderGridId', headerTypes: [
        { type: 'text', mData: 'customerCompany', sTitle: "客户公司名称"},
        { type: 'text', mData: 'customerName', sTitle: "客户名称"},
        { type: 'text', mData: 'customerCellPhone', sTitle: "手机号码"},
        { type: 'text', mData: 'resOfficerName', sTitle: "负责人"},
        { type: 'text', mData: 'offerPrice', sTitle: "测量报价"},
        { type: 'text', mData: 'price', sTitle: "订单价格"},
        { type: 'text', mData: 'prePayment', sTitle: "预付款"},
        { type: 'text', mData: 'closePayment', sTitle: "已付余款"},
        { type: 'text', mData: 'actualIncome', sTitle: "实际收入"},
        { type: 'text', mData: 'unPayment', sTitle: "未付款"},
        { type: 'date', mData: 'createTime', sTitle: "接单日期"}
    ]});


</script>

