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
    <title>我的工时</title>

    <style type="text/css">
        .mt15 {
            margin-top: 15px;
        }

    </style>

    <script type="text/javascript">
        function cancel() {
            window.location.href = "index.action";
        }
    </script>
</head>
<body>
    <section class="panel">
        <header class="panel-heading">
            我的工时
        </header>
        <section class="container">
            <div class="wrapper">
                <div class="row state-overview">
                    <div class="col-lg-2 col-md-2 col-sm-3">
                        <a class="btn btn-success btn-block" href="index.action">
                            返回主页
                        </a>
                    </div>
                </div>

                <%-- 消息引用 --%>
                <s:include value="../strutsMessage.jsp"/>


                <section class="panel">
                    <div class="panel-body">
                        <div class="row state-overview">
                            <div class="col-lg-4 col-sm-6">
                                <a href="${rootPath}/userFront/myMonthlyTimeReport.action?year=<s:property value='yearValues.thisYear'/>">
                                    <section class="panel">
                                        <div class="symbol terques">
                                            <h4>今年</h4>
                                        </div>
                                        <div class="value">
                                            <h1 class="count"><s:property value="yearValues.thisYearValue"/></h1>
                                            <p>小时</p>
                                        </div>
                                    </section>
                                </a>
                            </div>
                            <div class="col-lg-1 col-sm-0">
                            </div>
                            <div class="col-lg-4 col-sm-6">
                                <a href="${rootPath}/userFront/myMonthlyTimeReport.action?year=<s:property value='yearValues.lastYear'/>">
                                    <section class="panel">
                                        <div class="symbol red">
                                            <h4>去年</h4>
                                        </div>
                                        <div class="value">
                                            <h1 class="count"><s:property value="yearValues.lastYearValue"/></h1>
                                            <p>小时</p>
                                        </div>
                                    </section>
                                </a>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </section>
    </section>
</body>
</html>
