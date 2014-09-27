<#list imageList as bean>
<tbody>
<tr>
    <td class="product-image">
        <a class="fancybox" rel="group" href="${rootPath}/upload/getImage.action?getfile=${bean.fileName}" title="${bean.product.name!}"
           hidefocus="true">
            <div class="thumb">
                <img style="width:120px;height:80px" src="${rootPath}/upload/getImage.action?targetSize=300&getthumb=${bean.fileName}">
            </div>
            </s:if>
        </a>
    </td>
    <td class="product-price">
        <span>${bean.product.name!}</span><br>
        <i class="special-num" style="color:orange;font-size:18px">¥${bean.product.price!}</i><br>
        <#if bean.product.volunteerBean??>
            <span>【设计师】:${bean.product.volunteerBean.name!}</span>
            <img src="${bean.product.volunteerBean.iconpath!}" style="border-radius: 40px;-moz-border-radius: 40px;width: 40px;height: 40px;"/><br>
        </#if>
    </td>
</tr>
</tbody>
</#list>

<script type="text/javascript">
    index = ${index!0};
    totalCount = ${totalCount!0};
</script>