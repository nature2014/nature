<style type="text/css">
    .mt15 {
        margin-top: 15px;
    }

</style>

<section class="panel">
    <header class="panel-heading">
        员工列表

    </header>

    <div class="panel-body">
        <div class="adv-table dataTables_wrapper form-inline">

            <div class="row">
                <div class="col-lg-6 col-md-6">
                    <a class="btn btn-success" href="/bh/person/create">
                        <i class="fa fa-plus"></i>
                        新增员工
                    </a>
                </div>
            </div>

            <form class="form-horizontal filter-panel" method="post" action="volunteer/queryVolunteer.action">
                <div class="row mt15">
                    <div class="col-lg-2 col-md-2 filter-column">
                        <label class="pull-left control-label" for="code">编号</label>

                        <div class="col-lg-9 col-md-9 filter-component-column">
                            <input type="text" value="" placeholder="编号" class="form-control input-sm filter-component" name="code" id="code">
                        </div>
                    </div>

                    <div class="col-lg-2 col-md-2 filter-column">
                        <label class="pull-left control-label" for="name">姓名</label>

                        <div class="col-lg-9 col-md-9 filter-component-column">
                            <input type="text" value="" placeholder="姓名" class="form-control input-sm filter-component" name="name" id="name">
                        </div>
                    </div>

                    <div class="col-lg-2 col-md-2 filter-column">
                        <label class="pull-left control-label" for="selfName">状态</label>

                        <div class="col-lg-9 col-md-9 filter-component-column">
                            <input type="text" value="" placeholder="状态" class="form-control input-sm filter-component" name="state" id="state">
                        </div>
                    </div>

                    <div class="col-lg-5 col-md-5">
                        <button class="btn btn-default pull-right" type="submit">
                            查询
                        </button>
                    </div>
                </div>
            </form>
            <table cellspacing="0" cellpadding="0" border="0" class="mt15 table table-striped table-advance table-hover table-bordered" id="userList">
                <thead>
                <tr>
                    <th style="width: 60px;">编号</th>
                    <th class="column-name">姓名</th>
                    <th style="width: 120px;">性别</th>
                    <th style="width: 100px;">出生年月</th>
                    <th style="width: 120px;">当前状态</th>
                    <th class="column-datetime">更新日期</th>
                    <th style="width: 120px;" class="center">操作</th>
                </tr>
                </thead>
                <tbody>

                <tr>
                    <td>025-00006</td>
                    <td>测试123</td>
                    <td>未说明的性别</td>
                    <td>2016-01-01</td>
                    <td>待审批</td>
                    <td>2014-03-03 10:58:39</td>
                    <td class="center">

                        <a title="新建体检" href="#confirmStartPhysicalCheck" data-toggle="modal" data-person-id="19497" class="btn btn-primary btn-xs start-physical-check">
                            <i class="fa fa-check"></i>
                        </a>


                        <a title="体检结果" href="/bh/physicalCheck/index?personId=19497" class="btn btn-info  btn-xs">
                            <i class="fa fa-tasks"></i>
                        </a>


                        <a title="编辑档案" href="/bh/person/edit/19497" class="btn btn-success btn-xs">
                            <i class="fa fa-edit"></i>
                        </a>

                    </td>
                </tr>

                </tbody>
            </table>

            <div class="row"><div class="col-lg-6"><div class="dataTables_info">共 4 条，30 条/页</div></div><div class="col-lg-6"><div class="dataTables_paginate paging_bootstrap pagination"><ul></ul></div></div></div>
        </div>
    </div>
</section>