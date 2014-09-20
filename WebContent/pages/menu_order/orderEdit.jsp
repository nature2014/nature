<%@ include file="../commonHeader.jsp" %>
<!--main content start-->
<section class="panel">
    <header class="panel-heading">
        <s:if test="product.id.length() > 0">
            修改订单
        </s:if>
        <s:else>
            添加订单
        </s:else>
    </header>
    <div class="panel-body">
        <s:actionerror/><s:actionmessage/>
        <form id="product" class="form-horizontal tasi-form" action="${rootPath}/backend/order/save.action"
              method="post">
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">业务名称</label>

                <div class="col-lg-10">
                    <input name="order.id" type="hidden" value="${order.id}"/>
                    <input type="text" placeholder="业务名称" name="order.name" class="form-control"
                           autofocus="autofocus"
                           required="required" value="${order.name}"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">客户</label>

                <div class="col-lg-10">
                    <s:if test="listCustomerBean!=null">
                        <s:select name="order.customerId" list="listCustomerBean" listKey="id" listValue="name"
                                  value="order.customerId" cssClass="form-control"/>
                    </s:if>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">负责人</label>

                <div class="col-lg-10">
                    <s:if test="listVolunteerBean!=null">
                        <s:select name="order.resOfficer" list="listVolunteerBean" listKey="id" listValue="name"
                                  value="order.resOfficer" cssClass="form-control"/>
                    </s:if>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">订单状态</label>

                <div class="col-lg-10">
                    <s:select cssClass="form-control" name="order.state"
                              list="#{'0':'测量报价', '1':'设计','2':'看稿', '3':'修改定稿','4':'金额', '5':'预付款下单','6':'制作', '7':'安装','8':'付清余款'}"
                              value="order.state"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">测量报价(元)</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="测量报价(元)" class="form-control" name="order.offerPrice"
                           value="${order.offerPrice}"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">金额(元)</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="金额(元)" class="form-control" name="order.price"
                           value="${order.price}"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">预付款(元)</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="预付款(元)" class="form-control" name="order.prePayment"
                           value="${order.prePayment}"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">结算金额(元)</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="结算金额(元)" class="form-control" name="order.closePayment"
                           value="${order.closePayment}"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">其它</label>

                <div class="col-lg-10">
                    <textarea placeholder="其它" name="order.comments" class="form-control"
                              required="required" rows="5">${order.comments}</textarea>
                </div>
            </div>

            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-10">
                    <button class="btn btn-info" type="submit">保存</button>
                    <button class="btn btn-info" type="button"
                            onclick="window.location.href='${rootPath}/backend/order/index.action'">取消
                    </button>
                </div>
            </div>
        </form>
    </div>
</section>

