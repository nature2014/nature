<%--
  User: peter
  Date: 14-4-15
  Time: 下午8:32
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<script>
    cellFormatter["parentid"] = function ( data, type, full ) {
        <s:iterator value="innerHospital">
        if(data =='<s:property value="id"/>'){
            return '<s:property value="name"/>';
        }
        </s:iterator>
        return "错误信息";
    };
</script>