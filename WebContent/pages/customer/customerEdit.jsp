<%@ include file="../commonHeader.jsp" %>

<!--main content start-->
<section class="panel">
    <header class="panel-heading">
        <s:if test="customer.id.length() > 0">
            修改客户
        </s:if>
        <s:else>
            添加客户
        </s:else>
    </header>
    <div class="panel-body">
        <s:actionerror/><s:actionmessage/>
        <form id="customer" class="form-horizontal tasi-form" action="${rootPath}/backend/customer/save.action"
              method="post">
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">客户名称</label>

                <div class="col-lg-10">
                    <input name="customer.id" type="hidden" value="${customer.id}"/>
                    <input type="text" placeholder="客户名称" name="customer.name" class="form-control"
                           autofocus="autofocus"
                           required="required" value="${customer.name}"
                           <s:if test="customer.id != null">readonly="readonly"</s:if>
                            />
                </div>
            </div>

            <div class="form-group has-success">
                <label class="col-lg-2 control-label">客户图标</label>

                <div class="col-lg-10">
                    <img id="customer_logo" style="width:120px;height:80px;float:left" src="${customer.logo}"/>
                    <input type="text" placeholder="客户图标" name="customer.logo" class="form-control"
                           required="required" value="${customer.logo}" onkeyup="jQuery('#customer_logo').attr('src',this.value);"/>

                </div>
            </div>

            <div class="form-group has-success">
                <label class="col-lg-2 control-label">手机号码</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="手机号码" name="customer.cellPhone" class="form-control"
                           required="required" value="${customer.cellPhone}"/>
                </div>
            </div>


            <div class="form-group has-success">
                <label class="col-lg-2 control-label">固定电话</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="固定电话" name="customer.fixedPhone" class="form-control"
                           required="required" value="${customer.fixedPhone}"/>
                </div>
            </div>

            <div class="form-group has-success">
                <label class="col-lg-2 control-label">QQ帐号</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="QQ帐号" name="customer.qq" class="form-control"
                           required="required" value="${customer.qq}"/>
                </div>
            </div>

            <div class="form-group has-success">
                <label class="col-lg-2 control-label">邮箱地址</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="邮箱地址" name="customer.email" class="form-control"
                           required="required" value="${customer.email}"/>
                </div>
            </div>

            <div class="form-group has-success">
                <label class="col-lg-2 control-label">微信</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="微信" name="customer.wechat" class="form-control"
                           required="required" value="${customer.wechat}"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">客户地址</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="客户地址" name="customer.address" class="form-control"
                           required="required" value="${customer.address}"/>
                </div>
            </div>

            <div class="form-group has-success">
                <label class="col-lg-2 control-label">客户附件资料</label>

                <div class="col-lg-10">
                    <%@ include file="/pages/imageUploadPlugin.jsp" %>
                </div>
            </div>

            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-10">
                    <button class="btn btn-info" type="submit">保存</button>
                    <button class="btn btn-info" type="button"
                            onclick="window.location.href='${rootPath}/backend/customer/index.action'">取消
                    </button>
                </div>
            </div>
        </form>
    </div>
</section>
<script type="text/javascript">
</script>
