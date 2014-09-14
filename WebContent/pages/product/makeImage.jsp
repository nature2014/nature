<%--
  User: peter
  Date: 14-4-15
  Time: 下午8:32
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<script>
    cellFormatter["image"] = function (data, type, full) {
        var image = '<div style="width:250px">';
        if ($.isArray(data)) {
            //只显示前4个图片，忽略非图片文件
            var j = 0;
            for (var i = 0; j < 4 && i < data.length; i++) {
                if (data[i].fileType == "image") {
                    var imageUrl = '${rootPath}/upload/getImage.action?getfile=' + data[i].fileName;
                    var imageThumb = '${rootPath}/upload/getImage.action?getthumb=' + data[i].fileName;
                    image += '<a class="fancybox" rel="group" href="' + imageUrl + '"><img style="width:120px;height:80px;float:left" src="' + imageThumb + '"/></a>';
                    j++;
                }
            }
        }
        image += '</div>';
        return image;
    };
</script>