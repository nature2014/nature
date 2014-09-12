<%@ include file="../commonHeader.jsp"%>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Mosaddek">
    <meta name="keyword" content="FlatLab, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
    <link rel="shortcut icon" href="${rootPath}/img/favicon.png">

    <style type="text/css">
        table tbody tr.even.row_selected td{
            background-color: #B0BED9 !important;
        }
    </style>
    <!--external css-->
    <title>后台用户修改密码</title>
</head>
<body>

<!--main content start-->
<section class="panel">
    <header class="panel-heading">
        后台用户修改密码
    </header>
    <div class="panel-body">
        <s:actionerror/><s:actionmessage/>
        <form id="changePwdForm" class="form-horizontal tasi-form" action="${rootPath}/user/changePassword.action" method="post">
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">旧密码</label>
                <div class="col-lg-10">
                    <input type="password" name="user.password" class="form-control" placeholder="请输入旧密码" required="required">
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">新密码</label>
                <div class="col-lg-10">
                    <input type="password" id="newPassword" name="newPassword" class="form-control" placeholder="请输入新密码" required="required">
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">再次输入新密码</label>
                <div class="col-lg-10">
                    <input type="password" name="confirm_password" class="form-control" placeholder="请再次输入新密码" required="required">
                </div>
            </div>

            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-10">
                    <button class="btn btn-info" type="submit">保存</button>
                    <button class="btn btn-info" type="button" onclick="history.go(-1);">取消</button>
                </div>
            </div>
        </form>
    </div>
</section>
<script type="text/javascript">
    //please refer to form-validation-script.js
    $(document).ready(function() {
        $("#changePwdForm").validate({
            rules: {
                confirm_password: {
                    equalTo: "#newPassword"
                }
            },
            messages: {
                'user.password': {
                    required: "请输入旧密码"
                },
                'newPassword': {
                    required: "请输入新密码"
                },
                confirm_password: {
                    required: "请再次输入新密码",
                    equalTo: "新密码两次输入不一致"
                }
            }
        });
    });
</script>