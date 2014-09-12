<%@ include file="/pages/commonHeader.jsp" %>
<section id="container">

    <!--sidebar start-->
    <aside>
        <div id="sidebar" class="nav-collapse ">
            <!-- sidebar menu start-->
            <ul class="sidebar-menu" id="nav-accordion">
                <li>
                    <a href="">
                        <i class="fa fa-tasks"></i>
                        <span>客户管理</span>
                        <span class="label label-danger pull-right mail-info"></span>
                    </a>
                </li>

                <li>
                    <a href="">
                        <i class="fa fa-tasks"></i>
                        <span>产品分类管理</span>
                        <span class="label label-danger pull-right mail-info"></span>
                    </a>
                </li>
                <li>
                    <a href="">
                        <i class="fa fa-tasks"></i>
                        <span>订单管理</span>
                        <span class="label label-danger pull-right mail-info"></span>
                    </a>
                </li>
                <li>
                    <a href="user/index.action">
                        <i class="fa fa-tasks"></i>
                        <span>用户管理</span>
                        <span class="label label-danger pull-right mail-info"></span>
                    </a>
                </li>

                <li>
                    <a href="">
                        <i class="fa fa-bar-chart-o"></i>
                        <span>订单报表</span>
                        <span class="label label-danger pull-right mail-info"></span>
                    </a>
                </li>
                <li>
                    <a href="${rootPath}/logout.action">
                        <i class="fa fa-user"></i>
                        <span>用户退出</span>
                    </a>
                </li>

                <!--multi level menu end-->

            </ul>
            <!-- sidebar menu end-->
        </div>
    </aside>
    <!--sidebar end-->
</section>