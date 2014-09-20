<head>
    <%@ include file="../commonHeader.jsp" %>
</head>
<ul class="breadcrumb">
    <li>客户管理</li>
    <li class="active">客户在哪里</li>
</ul>
<style type="text/css">
    #allmap {
        width: 100%;
        height: 800px;
        padding: 20px
    }
</style>
<%@ include file="../menu_serviceplace/foundpositionmap.jsp" %>
<s:set name="found" value="false"/>
<script type="text/javascript">
    jQuery(document).ready(function () {
        <s:iterator value="customerBeanList" id="service">
        <s:set name="found" value="true"/>
        var buffer = [];
        buffer.push('<span>客户姓名：<s:property value="#service.name"/></span></a>');
        var detail = [];
        detail.push('<div style="width:200px;"><span style="float:left;">固定电话：<s:property value="#service.fixedPhone"/></span>');
        detail.push('<span style="float:left;">手机号码：<s:property value="#service.cellPhone"/></span>');
        detail.push('<span style="float:left;">详细地址：<s:property value="#service.address"/></span>');
        detail.push('<span style="float:left;"><a href="${rootPath}/backend/customer/index.action?name=<s:property value="#service.name"/>">详细信息</a></span>');
        detail.push('</div>');
        mapMutiplePosition(buffer.join(""), "<s:property value="#service.longitude"/>", "<s:property value="#service.latitude"/>", detail.join(""));
        </s:iterator>
        setTimeout(function () {
            map.setZoom(10);
        }, 100);
    });

</script>

<s:if test="#found==false">
    <script>
        jQuery("#notfoundmessage").html('没有客户信息!');
        //初始化地图
        initializePosition("", "", "");
    </script>
</s:if>