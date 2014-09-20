<#list imageList as bean>
    <tbody>
        <tr class="sep-row"><td colspan="2"></td></tr>
        <tr>
            <td class="column">
                <span>${bean.product.name!}</span>
            </td>
            <td class="column">
                <span>单价</span>
            </td>
        </tr>
        <tr>
            <td class="product-image">
                <a class="fancybox" rel="group" href="${rootPath}/upload/getImage.action?getfile=${bean.fileName}" title="${bean.product.name!}" hidefocus="true">
                    <div class="thumb">
                        <img src="${rootPath}/upload/getImage.action?getthumb=${bean.fileName}">
                    </div>
                </s:if>
                </a>
            </td>
            <td class="product-price">
                <i class="special-num">￥${bean.product.price!}</i>
            </td>
        </tr>
    </tbody>
</#list>

<script type="text/javascript">
    index = ${index!0};
    totalCount = ${totalCount!0};
</script>