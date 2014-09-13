<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <%@ include file="../frontHeader.jsp" %>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="${rootPath}/css/train.css" rel="stylesheet">
    <title>我的工时</title>
    <script language="javascript" type="text/javascript">
        window.onload=function (){
            setInterval("document.getElementById('timewatcher').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",1000);
        }

        function dailyReport(yearMonth) {
            window.location.href = "${rootPath}/userFront/myDailyTimeReport.action?yearMonth=" + yearMonth;
        }

        function handlePrev(year) {
            year = year - 1;
            window.location.href = "${rootPath}/userFront/myMonthlyTimeReport.action?year=" + year;
        }

        function handleNext(year) {
            year = year + 1;
            window.location.href = "${rootPath}/userFront/myMonthlyTimeReport.action?year=" + year;
        }
    </script>

    <style type="text/css">

    </style>
</head>

<body>
    <div class="home2">
        <div class="bg-user">
            <div class="bg-fh">
                <a href="index.action">
                    <img src="${rootPath}/img/back.png" width="35" height="35" />
                </a>
            </div>
            <div class="bg-top">我的工时</div>
            <div class="bg-username">${volunteer.name}</div>
            <div class="bg-touxiang"><img src="${volunteer.iconpath}" onerror="this.src='person/img/<s:property value="@util.DBUtils@getDBFlag()"/>/volunteer.png'" width="50" height="50" /></div>
        </div>
        <div class="bg-right2">
            <div class="bg-title2" style="font-size:30px"></div>
            <div class="bg-time" id="timewatcher">加载当前时间</div>
        </div>
        <div class="bg-center">
            <div class="bg-title3">
                <div class="time-pre" style="cursor: pointer;" onclick="handlePrev(<s:property value="year" />)"></div>
                <s:property value="year" />
            </div>
            <div class="bg-time"> <div class="time-next" style="cursor: pointer;" onclick="handleNext(<s:property value="year" />)"></div></div>

            <div class="time-bule" onclick="dailyReport('<s:property value='monthValues.valueList[0].name'/>')">
                <div class="time-font"><s:property value="monthValues.valueList[0].value"/></div>
                <div class="month-font">1月</div>
            </div>
            <div class="time-bule" onclick="dailyReport('<s:property value='monthValues.valueList[1].name'/>')">
                <div class="time-font"><s:property value="monthValues.valueList[1].value"/></div>
                <div class="month-font">2月</div>
            </div>
            <div class="time-bule" onclick="dailyReport('<s:property value='monthValues.valueList[2].name'/>')">
                <div class="time-font"><s:property value="monthValues.valueList[2].value"/></div>
                <div class="month-font">3月</div>
            </div>
            <div class="time-bule" onclick="dailyReport('<s:property value='monthValues.valueList[3].name'/>')">
                <div class="time-font"><s:property value="monthValues.valueList[3].value"/></div>
                <div class="month-font">4月</div>
            </div>
            <div class="time-bule" onclick="dailyReport('<s:property value='monthValues.valueList[4].name'/>')">
                <div class="time-font"><s:property value="monthValues.valueList[4].value"/></div>
                <div class="month-font">5月</div>
            </div>
            <div class="time-bule" onclick="dailyReport('<s:property value='monthValues.valueList[5].name'/>')">
                <div class="time-font"><s:property value="monthValues.valueList[5].value"/></div>
                <div class="month-font">6月</div>
            </div>
            <div class="time-bule" onclick="dailyReport('<s:property value='monthValues.valueList[6].name'/>')">
                <div class="time-font"><s:property value="monthValues.valueList[6].value"/></div>
                <div class="month-font">7月</div>
            </div>
            <div class="time-bule" onclick="dailyReport('<s:property value='monthValues.valueList[7].name'/>')">
                <div class="time-font"><s:property value="monthValues.valueList[7].value"/></div>
                <div class="month-font">8月</div>
            </div>
            <div class="time-bule" onclick="dailyReport('<s:property value='monthValues.valueList[8].name'/>')">
                <div class="time-font"><s:property value="monthValues.valueList[8].value"/></div>
                <div class="month-font">9月</div>
            </div>
            <div class="time-bule" onclick="dailyReport('<s:property value='monthValues.valueList[9].name'/>')">
                <div class="time-font"><s:property value="monthValues.valueList[9].value"/></div>
                <div class="month-font">10月</div>
            </div>
            <div class="time-bule" onclick="dailyReport('<s:property value='monthValues.valueList[10].name'/>')">
                <div class="time-font"><s:property value="monthValues.valueList[10].value"/></div>
                <div class="month-font">11月</div>
            </div>
            <div class="time-bule" onclick="dailyReport('<s:property value='monthValues.valueList[11].name'/>')">
                <div class="time-font"><s:property value="monthValues.valueList[11].value"/></div>
                <div class="month-font">12月</div>
            </div>
        </div>
        <div class="hosp-hr"></div>
    </div>
</body>
</html>
