<%--
  User: peter
  Date: 14-09-20
  Time: 下午8:32
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<script>
    cellFormatter["customerBean.qq"] = function (data, type, full) {
        return full.customerBean.qq != null ? full.customerBean.qq : '';
    };
    cellFormatter["customerBean.email"] = function (data, type, full) {
        return full.customerBean.email != null ? full.customerBean.email : '';
    };
    cellFormatter["customerBean.wechat"] = function (data, type, full) {
        return full.customerBean.wechat != null ? full.customerBean.wechat : '';
    };
    cellFormatter["customerBean.address"] = function (data, type, full) {
        return full.customerBean.address != null ? full.customerBean.address : '';
    };
</script>