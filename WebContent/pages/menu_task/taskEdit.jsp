<%@ include file="../commonHeader.jsp" %>
<!--main content start-->
<section class="panel">
    <header class="panel-heading">
        <s:if test="taskEntityBean.id.length() > 0">
            修改积分任务
        </s:if>
        <s:else>
            添加积分任务
        </s:else>
    </header>
    <div class="panel-body">
        <s:actionerror/><s:actionmessage/>
        <form id="policy" class="form-horizontal tasi-form" action="${rootPath}/backend/task/save.action"
              method="post">

            <input name="taskEntityBean.id" type="hidden" value="${taskEntityBean.id}">

            <div class="form-group has-success">
                <label class="col-lg-2 control-label">任务名称</label>
                <div class="col-lg-10">
                    <input type="text" placeholder="任务名称" name="taskEntityBean.name" class="form-control"
                           required="required" value="${taskEntityBean.name}">
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">任务类型</label>
                <div class="col-lg-10">
                    <input type="text" placeholder="任务名称" name="taskEntityBean.type" class="form-control"
                           required="required" value="${taskEntityBean.type}">
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">分配类型</label>
                <div class="col-lg-10">
                    <input type="text" placeholder="分配类型" name="taskEntityBean.dispatchType" class="form-control"
                           required="required" value="${taskEntityBean.dispatchType}">
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">审批类型</label>、
                <div class="col-lg-10">
                    <input type="text" placeholder="审批类型" name="taskEntityBean.approveType" class="form-control"
                           required="required" value="${taskEntityBean.approveType}">
                </div>
            </div>






            <div class="form-group has-success">
                <label class="col-lg-2 control-label">分配类型</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="分配类型" name="policy.description" class="form-control"
                           required="required" value="${policy.description}">
                </div>
            </div>

            <div class="form-group has-success">
                <label class="col-lg-2 control-label">审批类型</label>

                <div class="col-lg-10">
                    <s:iterator value="conditions" var="condition">
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" name="policy.conditions" value="<s:property value='%{#condition.id}' />">
                                <s:property value="%{#condition.expression}" />(<s:property value="%{#condition.description}" />)
                            </label>
                        </div>
                    </s:iterator>
                </div>
            </div>

            <div class="form-group has-success">
                <label class="col-lg-2 control-label">描述</label>

                <div class="col-lg-10">
                    <s:iterator value="actions" var="action">
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" name="policy.actions" value="<s:property value='%{#action.id}' />">
                                <s:property value="%{#action.classPath}" />(<s:property value="%{#action.description}" />)
                            </label>
                        </div>
                    </s:iterator>
                </div>
            </div>

            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-10">
                    <button class="btn btn-info" type="submit">保存</button>
                    <button class="btn btn-info" type="button"
                            onclick="window.location.href='${rootPath}/backend/policy/index.action'">取消
                    </button>
                </div>
            </div>
        </form>
    </div>
</section>

<script type="text/javascript">
    //please refer to form-validation-script.js
    $(document).ready(function () {
        $("#policy").validate({
            rules: {
                'product.price': {
                    required: true,
                    min: 0,
                    max: 999999
                }
            },
            messages: {
                'product.price': {
                    required: "请输入价格",
                    min: "金额必须大于等于0",
                    max: "金额必须小于等于999999"
                }
            }
        });

        <s:iterator value="policy.conditions" var="condition" >
        $("input[type=checkbox][value=<s:property value="%{#condition}"/>]").attr("checked", "checked");
    </s:iterator>

    <s:iterator value="policy.actions" var="action" >
    $("input[type=checkbox][value=<s:property value="%{#action}"/>]").attr("checked", "checked");
    </s:iterator>

    });
</script>