<!--main content start-->
<div class="container-fluid">
    <div class="row-fluid example">
        <div id="graphic" class="col-md-12">
        </div>
    </div>
</div>

<script type="text/javascript">
    function drawChart(ec, divName, optionData){
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
                    url: "${rootPath}/backend/report/reportOrderData.action",//要访问的后台地址
                    success: function (msg) {//msg为返回的数据，在这里做数据绑定
                        $.each(msg.list, function (i, obj) {
                            drawChart(ec, 'main' + i, obj.data);
                        });
                    }
                });
            }
    );
</script>

