<ul class="breadcrumb">
    <li>客户管理</li>
    <li class="active">群发短信</li>
</ul>
<!--main content start-->
<section class="panel">
    <header class="panel-heading">
        客户群发短信
    </header>
    <%@include file="../strutsMessage.jsp" %>
    <form id="customer" class="form-horizontal tasi-form" action="${rootPath}/backend/customer/smsSendSave.action" method="post">
        <div class="panel-body">
            <label class="col-lg-2 control-label">请选择客户</label>
            <input type="button" class="btn btn-info" value="全选" id="selectAll"/>
            <input type="button" class="btn btn-info" value="全不选" id="unSelect"/>
            <input type="button" class="btn btn-info" value="反选" id="reverse"/>
        </div>
        <script>
            $('#selectAll').on("click", function () {//全选
                $("#customerPanel :checkbox").each(function () {
                    this.checked = true;
                });
            });

            $('#unSelect').on("click", function () {//全不选
                $("#customerPanel :checkbox").each(function () {
                    this.checked = false;
                });
            });

            $('#reverse').on("click", function () {//反选
                $("#customerPanel :checkbox").each(function () {
                    this.checked = !this.checked;
                });
            });
        </script>
        <div class="form-group">
            <div class="col-lg-10" id="customerPanel">
            </div>
        </div>
        <div class="form-group has-success">
            <label class="col-lg-2 control-label">短消息内容</label>

            <div class="col-lg-10">
                <textarea placeholder="请输入短信内容" name="content" class="form-control"
                          rows="10"></textarea>
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-offset-2 col-lg-10">
                <button class="btn btn-info" type="submit">发送短消息</button>
            </div>
        </div>
    </form>
    </div>
</section>

<script>
    function ajaxCustomer() {
        $.ajax({
            type: "GET",
            url: "${rootPath}/backend/customer/queryTable.action",
            dataType: "json",
            success: function (result) {
                var data = result.aaData;
                var checkboxList = [];
                if ($.isArray(data)) {
                    for (var j = 0; j < data.length; j++) {
                        checkboxList.push('<div class="col-lg-4">');
                        checkboxList.push('<input type="checkbox" name="customerBeanList.cellPhone" value = "' + data[j].cellPhone + '" >');
                        checkboxList.push('<span>' + data[j].name + '</span>');
                        checkboxList.push('<span>【' + data[j].cellPhone + '】</span>');
                        checkboxList.push('</div>');
                    }
                    $("#customerPanel").html(checkboxList.join(""));
                }
            }
        });
    }
    ajaxCustomer();

    //please refer to form-validation-script.js
    $(document).ready(function () {
        $("#customer").validate({
            rules: {
                'customer.cellPhone': {
                    required: true
                },
                'content': {
                    required: true
                }
            },
            messages: {
                'customer.cellPhone': {
                    required: "请至少选择一个客户发送短信!"
                },
                'content': {
                    required: "请输入短信内容!"
                }
            }
        });
    });
</script>