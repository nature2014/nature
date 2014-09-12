<%@ include file="/pages/commonHeader.jsp" %>
<!--header start-->

<style type="text/css">
    .dropdown-menu.extended {
        max-width: 180px !important;
    }
</style>
<header class="header white-bg">
    <div class="sidebar-toggle-box">
        <div class="fa fa-bars tooltips" data-placement="right"></div>
    </div>
    <!--logo start-->
    <a href="${rootPath}/index.action" class="logo"><span>大自然信息管理系统</span></a>
    <!--logo end-->
    <div class="nav notify-row" id="top_menu">
        <!--  notification start -->
        <ul class="nav top-menu">
            <!-- settings end -->
            <li class="dropdown">
                <a data-toggle="dropdown" class="dropdown-toggle" href="">
                    <i class="fa fa-bell-o"></i>
                            <span class="badge bg-success">
                                10
                             </span>
                </a>
                <ul class="dropdown-menu extended notification">
                    <div class="notify-arrow notify-arrow-green"></div>
                    <li>
                        <p class="green">你有10个未完成的订单</p>
                    </li>
                    <li>
                        <a href="">
                            <span class="label label-warning"><i class="fa fa-bell"></i></span>
                            <span>20140912-110</span>
                            <span class="small italic">华联超市</span>
                        </a>
                    </li>
                    <li>
                        <a href="">
                            <span class="label label-warning"><i class="fa fa-bell"></i></span>
                            <span>20140912-110</span>
                            <span class="small italic" style="padding-left: 10px;">物美服装厂</span>
                        </a>
                    </li>
                    <li>
                        <a href="">
                            <span class="label label-warning"><i class="fa fa-bell"></i></span>
                            <span>20140912-110</span>
                            <span class="small italic" style="padding-left: 10px;">家乐福超市</span>
                        </a>
                    </li>
                    <s:if test="true">
                        <li>
                            <a href="">查看所有未完成的订单</a>
                        </li>
                    </s:if>
                </ul>
            </li>
            <!-- notification dropdown end -->
        </ul>
        <!--  notification end -->
    </div>
    <div class="top-nav ">
        <!--search & user info start-->
        <ul class="nav pull-right top-menu">
            <!-- user login dropdown start-->
            <li class="dropdown">
                <a data-toggle="dropdown" class="dropdown-toggle" href="">
                    <span class="username">${sessionUser.name }</span>
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu extended logout">
                    <li style="width: 50%;"><a href="${rootPath}/user/changePassword.action"><i class=" fa fa-cog"></i>修改密码</a>
                    </li>
                    <li style="width: 50%;"><a href="${rootPath}/logout.action"><i class="fa fa-key"></i>退出</a></li>
                    <li></li>
                </ul>
            </li>
            <!-- user login dropdown end -->
        </ul>
        <!--search & user info end-->
    </div>
</header>
<!--header end-->