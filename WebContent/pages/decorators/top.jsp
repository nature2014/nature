<%@ include file="/pages/commonHeader.jsp"%>
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
            <a href="${rootPath}/backend/index.action" class="logo"><span>大自然信息管理系统</span></a>
            <!--logo end-->
            <div class="nav notify-row" id="top_menu">
                <!--  notification start -->
                <ul class="nav top-menu">
                    <!-- settings end -->
                    <li id="header_notification_bar" class="dropdown">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="${rootPath}/">
                            <i class="fa fa-bell-o"></i>
                            <span class="badge bg-success">
                            <s:property value="#request.unVerifiedVolunnteer.getiTotalRecords()"/>
                             </span>
                        </a>
                        <ul class="dropdown-menu extended notification">
                            <div class="notify-arrow notify-arrow-green"></div>
                            <li>
                                <p class="green">你有 <s:property value="#request.unVerifiedVolunnteer.getiTotalRecords()"/> 个员工未审核</p>
                            </li>
                            <s:iterator value="#request.unVerifiedVolunnteer.aaData" var="item">
                               <li>
                                <a href="${rootPath}/backend/volunteerVerify/index.action">
                                    <span class="label label-warning"><i class="fa fa-bell"></i></span>
                                    <span  style="padding-left: 10px;">${item.name}</span>
                                    <span class="small italic" style="padding-left: 10px;">${item.code}</span>
                                </a>
                              </li>
                            </s:iterator>
                            <s:if test="#request.unVerifiedVolunnteer.getiTotalRecords() > #request.unVerifiedVolunnteer.aaData.size()">
                               <li>
                                <a href="${rootPath}/backend/volunteerVerify/index.action">查看所有未审核的员工</a>
                               </li>
                            </s:if>
                        </ul>
                    </li>
                    <li id="header_notification_bar" class="dropdown">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="${rootPath}/">
                            <i class="fa fa-bell-o"></i>
                            <span class="badge bg-warning">
                            <s:property value="#request.unInterviewedVolunnteer.getiTotalRecords()"/>
                            </span>
                        </a>
                        <ul class="dropdown-menu extended notification">
                            <div class="notify-arrow notify-arrow-yellow"></div>
                            <li>
                                <p class="yellow">你有 <s:property value="#request.unInterviewedVolunnteer.getiTotalRecords()"/> 个员工未面试</p>
                            </li>
                            <s:iterator value="#request.unInterviewedVolunnteer.aaData" var="item">
                              <li>
                                <a href="${rootPath}/backend/volunteerInterview/index.action">
                                    <span class="label label-warning"><i class="fa fa-bell"></i></span>
                                    <span  style="padding-left: 10px;">${item.name}</span>
                                    <span class="small italic" style="padding-left: 10px;">${item.code}</span>
                                </a>
                              </li>
                            </s:iterator>
                            <s:if test="#request.unInterviewedVolunnteer.getiTotalRecords() > #request.unInterviewedVolunnteer.aaData.size()">
                               <li>
                                <a href="${rootPath}/backend/volunteerInterview/index.action">查看所有未面试的员工</a>
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
                        <a data-toggle="dropdown" class="dropdown-toggle" href="${rootPath}/">
                            <span class="username">${backendSessionUser.name }</span>
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu extended logout">
                            <li style="width: 50%;"><a href="${rootPath}/backend/user/changePassword.action"><i class=" fa fa-cog"></i>修改密码</a></li>
                            <li style="width: 50%;"><a href="${rootPath}/backend/logout.action"><i class="fa fa-key"></i>退出</a></li>
                            <li> </li>
                        </ul>
                    </li>
                    <!-- user login dropdown end -->
                </ul>
                <!--search & user info end-->
            </div>
        </header>
      <!--header end-->