package actions.backend;

import bl.beans.ProductLevelBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.ProductLevelBusiness;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;

/**
 * @author pli
 * @since $Date:2014-09-13$
 */
public class ProductLevelAction extends BaseBackendAction<ProductLevelBusiness> {
    private static final long serialVersionUID = -5222876000116738224L;
    private static Logger LOG = LoggerFactory.getLogger(ProductLevelAction.class);

    private ProductLevelBean productLevel;

    public ProductLevelBean getProductLevel() {
        return productLevel;
    }

    public void setProductLevel(ProductLevelBean productLevel) {
        this.productLevel = productLevel;
    }

    @Override
    public String getActionPrex() {
        return getRequest().getContextPath() + "/backend/productlevel";
    }

    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("name", "分类名称").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("code", "分类编码").enableSearch());
        //.init.getAoColumns().add(new TableHeaderVo("description", "分类描述", false));

        return init;
    }

    @Override
    public String getTableTitle() {
        return "<ul class=\"breadcrumb\"><li>产品管理</li><li class=\"active\">产品分类</li></ul>";
    }

    @Override
    public String save() throws Exception {
        if (StringUtils.isBlank(productLevel.getId())) {
            ProductLevelBean userTmp = (ProductLevelBean) getBusiness().getLeafByName(productLevel.getName()).getResponseData();
            if (userTmp != null) {
                addActionError("产品分类存在");
                return FAILURE;
            } else {
                productLevel.set_id(ObjectId.get());
                getBusiness().createLeaf(productLevel);
            }
        } else {
            ProductLevelBean origProductLevel = (ProductLevelBean) getBusiness().getLeaf(productLevel.getId().toString()).getResponseData();
            ProductLevelBean newProductLevel = (ProductLevelBean) origProductLevel.clone();
            BeanUtils.copyProperties(newProductLevel, productLevel);
            getBusiness().updateLeaf(origProductLevel, newProductLevel);
        }
        return SUCCESS;
    }

    @Override
    public String edit() throws Exception {
        productLevel = (ProductLevelBean) getBusiness().getLeaf(getId()).getResponseData();
        return SUCCESS;
    }

    @Override
    public String delete() throws Exception {
        if (getId() != null) {
            getBusiness().deleteLeaf(getId());
        }
        return SUCCESS;
    }
}
