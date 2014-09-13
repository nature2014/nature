<!DOCTYPE html>
<%@ include file="../commonHeader.jsp"%>
<html lang="en">
<html>
<head>
    <title>服务地点</title>
</head>
<body>
<section class="panel">
    <header class="panel-heading">
        培训课程
    </header>

    <div class="panel-body">
        <div class="adv-table dataTables_wrapper form-inline">

            <div class="row">
                <div class="col-lg-6 col-md-6">
                    <a class="btn btn-success" href="backend/traincourse/traincourseaddedit.action">
                        <i class="fa fa-plus"></i>
                        新培训课程
                    </a>
                </div>
            </div>

            <table cellspacing="0" cellpadding="0" border="0" class="mt15 table table-striped table-advance table-hover table-bordered" id="userList">
                <thead>
                <tr>
                    <th class="column-name">课程名称</th>
                    <th class="column-name">课程状态</th>
                    <th class="column-datetime">创建日期</th>
                    <th class="column-datetime">更新日期</th>
                    <th style="width: 120px;" class="center">操作</th>
                </tr>
                </thead>
                <tbody>
                  <s:iterator value="trainCourses" var="sp">
                    <tr>
                        <td><s:property value="%{#sp.name}"/> </td>
                        <td><s:if test="%{#sp.status==0}">创建</s:if><s:elseif test="%{#sp.status==1}">开始</s:elseif><s:else>结束</s:else></td>
                        <td><s:date name="#sp.createTime" format="yyyy-MM-dd HH:mm:ss"></s:date></td>
                        <td><s:date name="#sp.modifyTime" format="yyyy-MM-dd HH:mm:ss"></s:date></td>
                        <td class="center">
                            <a title="编辑培训课程" href="backend/traincourse/traincourseaddedit.action?trainCourse.id=${sp.id}" class="btn btn-info  btn-xs">
                                <i class="fa fa-edit"></i>
                            </a>

                            <a title="删除培训课程" href="backend/traincourse/traincoursedelete.action?trainCourse.id=${sp.id}" class="btn btn-info  btn-xs">
                                <i class="fa fa-trash-o"></i>
                            </a>

                        </td>
                    </tr>
                  </s:iterator>
                </tbody>
            </table>

        </div>
    </div>
</section>
</body>
</html>
