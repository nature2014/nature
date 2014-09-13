<%@ include file="../commonHeader.jsp" %>

<!--main content start-->
<section class="panel">
    <header class="panel-heading">
        <s:if test="productLevel.id.length() > 0">
            修改产品分类
        </s:if>
        <s:else>
            添加产品分类
        </s:else>
    </header>
    <div class="panel-body">
        <s:actionerror/><s:actionmessage/>
        <form id="productLevel" class="form-horizontal tasi-form" action="${rootPath}/backend/productlevel/save.action"
              method="post">
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">分类名称</label>

                <div class="col-lg-10">
                    <input name="productLevel.id" type="hidden" value="${productLevel.id}"/>
                    <input type="text" placeholder="分类名称" name="productLevel.name" class="form-control"
                           autofocus="autofocus"
                           required="required" value="${productLevel.name}"
                           <s:if test="productLevel.id != null">readonly="readonly"</s:if>
                            />
                </div>
            </div>

            <div class="form-group has-success">
                <label class="col-lg-2 control-label">分类编码</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="分类编码" name="productLevel.code" class="form-control"
                           required="required" value="${productLevel.code}"/>
                </div>
            </div>

            <div class="form-group has-success">
                <label class="col-lg-2 control-label">分类描述</label>

                <div class="col-lg-10">
                    <textarea placeholder="分类描述" name="productLevel.description" class="form-control"
                              required="required" rows="10">${productLevel.description}</textarea>
                </div>
            </div>

            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-10">
                    <button class="btn btn-info" type="submit">保存</button>
                    <button class="btn btn-info" type="button"
                            onclick="window.location.href='${rootPath}/backend/productlevel/index.action'">取消
                    </button>
                </div>
            </div>
        </form>
    </div>
</section>
<script type="text/javascript">
</script>
