<%@ include file="../commonHeader.jsp" %>
<!--main content start-->
<section class="panel">
    <header class="panel-heading">
        <s:if test="policy.id.length() > 0">
            修改积分策略
        </s:if>
        <s:else>
            添加积分策略
        </s:else>
    </header>
    <div class="panel-body">
        <s:actionerror/><s:actionmessage/>
        <form id="policy" class="form-horizontal tasi-form" action="${rootPath}/backend/policy/save.action"
              method="post">

            <input name="policy.id" type="hidden" value="${policy.id}">

            <div class="form-group has-success">
                <label class="col-lg-2 control-label">策略名称</label>
                <div class="col-lg-10">
                    <input type="text" placeholder="策略名称" name="policy.name" class="form-control"
                           required="required" value="${policy.name}">
                </div>
            </div>

            <div class="form-group has-success">
                <label class="col-lg-2 control-label">策略优先级</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="策略优先级" name="policy.priority" class="form-control"
                           required="required" value="${policy.priority}">
                </div>
            </div>

            <div class="form-group has-success">
                <label class="col-lg-2 control-label">策略描述</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="策略描述" name="policy.description" class="form-control"
                           required="required" value="${policy.description}">
                </div>
            </div>

            <div class="form-group has-success">
                <label class="col-lg-2 control-label">条件</label>

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
                <label class="col-lg-2 control-label">动作</label>

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