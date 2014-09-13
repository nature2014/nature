<%--
  Created by IntelliJ IDEA.
  User: wangronghua
  Date: 14-3-15
  Time: 下午9:11
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="../bootstrapHeader.jsp" %>
<html>
<head>
    <link href="css/train.css" rel="stylesheet">
    <title>我的日工时</title>
    <style type="text/css">


        .time-train2{
            width:700px;
            float:left;
            margin-top:15px;
            margin-right:10px;
        }


        .time-train-right2{
            width:420px;
            height:60px;
            background:#FFF;
            float:left;
            color:#666666;
            font-family:"黑体","宋体" !important;
        }
        .time-date{
            width:250px;
            height:60px;
            background:#0671DE;
            font-family:"黑体","宋体" !important;
            font-size:24px;
            font-weight:bold;
            color:#FFF;
            float:left;
            line-height:60px;
            text-align:center;
            border-right:2px solid #C6C1C0;
            margin-right:5px;

        }
        .time-num{
            width:160px;
            height:60px;
            float:left;
            line-height:60px;
            padding-left:30px;
            text-align:right;
            font-family:"黑体","宋体" !important;
            font-size:24px;
            font-weight:bold;
            margin-right:10px;

        }
        .time-phone{
            width:160px;
            height:60px;
            float:left;
            line-height:60px;

            text-align:left;
            padding-right:5px;
            font-family:"黑体","宋体" !important;
            font-size:24px;
            font-weight:bold;
        }
    </style>

    <script type="text/javascript">
        window.onload=function (){
            setInterval("document.getElementById('timewatcher').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",1000);
        }

        function handlePrev(yearMonth) {
            //year = year - 1;
            window.location.href = "${rootPath}/userFront/myDailyTimeReport.action?step=-1&yearMonth=" + yearMonth;
        }

        function handleNext(yearMonth) {
            //year = year + 1;
            window.location.href = "${rootPath}/userFront/myDailyTimeReport.action?step=1&yearMonth=" + yearMonth;
        }
    </script>
</head>
<body>
    <div class="home2">
        <div class="bg-user">
            <div class="bg-fh">
                <a href="${rootPath}/userFront/myMonthlyTimeReport.action?year=<s:property value="year" />">
                    <img src="${rootPath}/img/back.png" width="35" height="35" />
                </a>
            </div>
            <div class="bg-top">我的日工时</div>
            <div class="bg-username">${volunteer.name}</div>
            <div class="bg-touxiang"><img src="${volunteer.iconpath}"  width="50" height="50" /></div>
        </div>
        <div class="bg-right2">
            <div class="bg-title2" style="font-size:30px"></div>
            <div class="bg-time" id="timewatcher">加载当前时间</div>
        </div>
        <div class="bg-center">

            <div class="bg-title3">
                <div class="time-pre" style="cursor: pointer;" onclick="handlePrev('<s:property value="yearMonth" />')"></div>
                <s:property value="yearMonth" />
            </div>
            <div class="bg-time"> <div class="time-next" style="cursor: pointer;" onclick="handleNext('<s:property value="yearMonth" />')"></div></div>

            <div style="float: left;">
                <s:include value="../strutsMessage.jsp"/>
            </div>
            <s:iterator value="dayValues.valueList" var="valueBean">
                <div class="time-train2">
                    <div class="time-date"><s:property value="%{#valueBean.name}"/></div>
                    <div class="time-train-right2">
                        <div class="time-num"><s:property value="%{#valueBean.value}"/></div>
                        <div  class="time-phone">小时</div>
                    </div>
                </div>
            </s:iterator>
        </div>
    </div>


</body>
</html>
