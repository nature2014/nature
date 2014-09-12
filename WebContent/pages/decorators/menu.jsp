<%@ include file="/pages/commonHeader.jsp" %>
<section id="container">

    <!--sidebar start-->
    <aside>
        <div id="sidebar" class="nav-collapse ">
            <!-- sidebar menu start-->
            <ul class="sidebar-menu" id="nav-accordion">
                <li class="sub-menu">
                    <a href="javascript:;">
                        <i class="fa fa-heart"></i>
                        <span>员工管理</span>
                    </a>
                    <ul class="sub">
                        <li><a id="volunteerManagement" href="${rootPath}/backend/volunteer/index.action">员工</a></li>
                        <li><a href="${rootPath}/backend/volunteerVerify/index.action">审核</a></li>
                        <li><a href="${rootPath}/backend/volunteerInterview/index.action">面试</a></li>
                    </ul>
                </li>
                <li class="sub-menu">
                    <a href="javascript:;">
                        <i class="fa fa-gavel"></i>
                        <span>客户管理</span>
                    </a>
                    <ul class="sub">
                        <li><a href="">客户信息</a></li>
                    </ul>
                </li>

                <li class="sub-menu">
                    <a href="javascript:;">
                        <i class="fa fa-gavel"></i>
                        <span>产品管理</span>
                    </a>
                    <ul class="sub">
                        <li><a href="">产品分类</a></li>
                        <li><a href="">产品发布</a></li>
                    </ul>
                </li>
                <li class="sub-menu">
                    <a href="javascript:;">
                        <i class="fa fa-tasks"></i>
                        <span>订单管理</span>
                    </a>
                    <ul class="sub">
                        <li><a href="">订单记录</a></li>
                        <li><a href="">订单报表</a></li>
                    </ul>
                </li>
                <li class="sub-menu">
                    <a href="javascript:;">
                        <i class="fa fa-gavel"></i>
                        <span>服务管理</span>
                    </a>
                    <ul class="sub">
                        <li><a href="${rootPath}/backend/serviceplace/index.action?type=0">公司</a></li>
                        <li><a href="${rootPath}/backend/serviceplace/index.action?type=1">地图地点</a></li>
                    </ul>
                </li>
                <li class="sub-menu">
                    <a href="javascript:;">
                        <i class="fa fa-volume-up"></i>
                        <span>培训管理</span>
                    </a>
                    <ul class="sub">
                        <li><a id="courseManagement" href="${rootPath}/backend/traincourse/index.action">培训课程</a></li>
                        <li><a id="trainManagement" href="${rootPath}/backend/volunterTrainCourse/index.action">培训记录</a>
                        </li>
                    </ul>
                </li>
                <li class="sub-menu">
                    <a href="javascript:;">
                        <i class="fa fa-clock-o"></i>
                        <span>工时管理</span>
                    </a>
                    <ul class="sub">
                        <li><a href="${rootPath}/backend/userservice/index.action">签到记录</a></li>
                        <li><a href="${rootPath}/backend/report/activeTimeReport.action">工时实时统计</a></li>
                        <li><a href="${rootPath}/backend/report/volunteerDailyReport.action">员工日工时统计</a></li>
                        <li><a href="${rootPath}/backend/report/volunteerMonthlyReport.action">员工月工时统计</a></li>
                        <%--<li><a  href="backend/report/serviceDailyReport.action">服务地点日工时统计</a></li>--%>
                        <%--<li><a  href="backend/report/serviceMonthlyReport.action">服务地点月工时统计</a></li>--%>
                        <%--<li><a  href="boxed_page.html">工时排名</a></li>--%>
                    </ul>
                </li>
                <s:if test="#session.backendSessionUser != null && #session.backendSessionUser.name == 'admin'">
                    <li class="sub-menu">
                        <a href="javascript:;">
                            <i class="fa fa-cogs"></i>
                            <span>系统管理</span>
                        </a>
                        <ul class="sub">
                            <li><a href="${rootPath}/backend/user/index.action">用户管理</a></li>
                            <li><a href="${rootPath}/backend/systemsetting.action">参数设定</a></li>
                            <li><a href="${rootPath}/backend/sourcecode/index.action">职称编码</a></li>
                        </ul>
                    </li>
                </s:if>

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