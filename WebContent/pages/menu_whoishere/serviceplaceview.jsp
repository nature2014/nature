<%--
  User: peter
  Date: 14-3-16
  Time: 上午10:07
--%>
<!DOCTYPE html>
<html lang="en">
<html>
<head>
    <title>签到管理</title>
    <%@ include file="../bootstrapHeader.jsp" %>
</head>
<body>
<section class="panel">
    <header class="panel-heading">
        谁在这里
    </header>

    <section class="container">

        <div class="row state-overview">
            <div class="col-lg-2 col-md-2 col-sm-3">
                <a class="btn btn-success btn-block" href="index.action">
                    返回主页
                </a>
            </div>
        </div>
        <div style="text-align:center;margin-top:20px">
            <div class="col-lg-5" style="height:100px">

            </div>

            <div class="col-lg-5" style="height:100px">

            </div>

            <div class="stepy-tab">
                <ul id="default-titles" class="stepy-titles clearfix">
                    <li id="default-title-0" class="current-step">
                        <div style="cursor:default;color:white">
                            <a href="${rootPath}/userFront/whoishere.action?type=0">
                                院内服务地点
                            </a>
                        </div>
                    </li>
                    <li id="default-title-1" class="current-step">
                        <div style="cursor:default;color:white">
                            <a href="${rootPath}/userFront/whoishere.action?type=1">
                                公司外服务地点
                            </a>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </section>
</section>
</body>
</html>
