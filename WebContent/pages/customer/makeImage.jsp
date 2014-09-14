<%--
  User: peter
  Date: 14-4-15
  Time: 下午8:32
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<script>
    cellFormatter["logo"] = function (data, type, full) {
        var image = '<img style="width:240px;height:80px;float:left" src="' + data + '"/>';
        return image;
    };
</script>