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
        服务地点
    </header>

    <div class="panel-body">
        <div class="adv-table dataTables_wrapper form-inline">

            <div class="row">
                <div class="col-lg-6 col-md-6">
                    <a class="btn btn-success" href="${rootPath}/backend/serviceplace/serviceplaceaddedit.action?type=<s:property value="type"/>">
                        <i class="fa fa-plus"></i>
                        新服务地点
                    </a>
                </div>
            </div>

            <table cellspacing="0" cellpadding="0" border="0" class="mt15 table table-striped table-advance table-hover table-bordered" id="userList">
                <thead>
                <tr>
                    <th class="column-name">地点名称</th>
                    <th class="column-name">地点描述</th>
                    <th class="column-datetime">创建日期</th>
                    <th class="column-datetime">更新日期</th>
                    <th style="width: 120px;" class="center">操作</th>
                </tr>
                </thead>
                <tbody>
                  <s:iterator value="servicePlaces" var="sp">
                    <tr>
                        <td><s:property value="%{#sp.name}"/> </td>
                        <td><s:property value="%{#sp.description}"/></td>
                        <td><s:date name="#sp.createTime" format="yyyy-MM-dd HH:mm:ss"></s:date></td>
                        <td><s:date name="#sp.modifyTime" format="yyyy-MM-dd HH:mm:ss"></s:date></td>
                        <td class="center">
                            <a title="编辑服务地点" href="${rootPath}/backend/serviceplace/serviceplaceaddedit.action?servicePlace.id=${sp.id}&type=<s:property value="type"/>" class="btn btn-info  btn-xs">
                                <i class="fa fa-edit"></i>
                            </a>

                            <a title="删除服务地点" href="${rootPath}/backend/serviceplace/serviceplacedelete.action?servicePlace.id=${sp.id}&type=<s:property value="type"/>" class="btn btn-info  btn-xs">
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
