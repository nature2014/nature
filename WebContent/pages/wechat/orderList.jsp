<%--
  Created by IntelliJ IDEA.
  User: wangronghua
  Date: 14-3-19
  Time: 下午9:32
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/pages/miniwechatHeader.jsp" %>

    <script type="text/javascript">

        function custom_close(){
            WeixinJSBridge.call('closeWindow');
        }
    </script>
</head>
<body>
<section class="panel" style="margin-bottom: 0px;">
    <header class="panel-heading">
        订单查询
        <span class="tools pull-right">
            <a class="fa fa-chevron-up" href="javascript:void(0)" onclick="$('#query_body').toggle();$(this).toggleClass('fa-chevron-up fa-chevron-down');">
                搜索
            </a>
        </span>
    </header>
    <section id="query_body" class="panel-body" style="display: none;">
        <form action="${rootPath}/wechat/queryOrders.action" class="form-horizontal  tasi-form">
            <div class="form-group">
                <div class="col-xs-8">
                    <input class="form-control" placeholder="手机号" name="customerCellPhone" value="<s:property value='customerCellPhone'/>">
                </div>
                <label class="col-xs-4">
                    <button type="submit" class="btn btn-info btn-block form-control">查询</button>
                </label>
            </div>

        </form>
    </section>
</section>
<section class="panel" style="margin-bottom: 0px;">
    <section class="panel-body">
    <table class="table table-striped">
        <thead>
        <tr>
            <th>订单号</th>
            <th>客户名称</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="orderBeanList" var="bean">
            <tr>
                <td>
                    <a href="${rootPath}/wechat/getOrderDetails.action?orderBean.id=<s:property value="#bean.id" />"><s:property value="#bean.id" /></a>
                </td>
                <td><s:property value="#bean.customerName" /></td>
            </tr>
        </s:iterator>

        </tbody>
    </table>
    </section>
</section>

</body>
</html>
