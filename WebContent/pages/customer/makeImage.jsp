<%--
  User: peter
  Date: 14-4-15
  Time: 下午8:32
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<script>
    cellFormatter["logo"] = function (data, type, full) {
        var image = '<a class="fancybox" rel="group" href="' + data + '"><img style="width:120px;height:80px;float:left" src="' + data + '"/></a>';
        return image;
    };
</script>