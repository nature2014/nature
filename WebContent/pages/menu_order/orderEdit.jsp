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
                    <input type="text" placeholder="业务名称" name="order.name" class="form-control"
                           autofocus="autofocus"
                           required="required" value="${order.name}"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">负责人</label>

                <div class="col-lg-10">
                    <s:if test="listVolunteerBean!=null">
                        <s:select name="order.resOfficer" list="listVolunteerBean" listKey="id" listValue="name"
                                  value="%{order.resOfficer}" cssClass="form-control"/>
                    </s:if>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">订单状态</label>

                <div class="col-lg-10">
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

