package actions.backend;

import actions.upload.UploadMultipleImageAction;
import bl.beans.ImageInfoBean;
import bl.beans.ProductBean;
import bl.beans.ProductLevelBean;
import bl.beans.SourceCodeBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.ProductBusiness;
import bl.mongobus.ProductLevelBusiness;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;

import java.util.List;

/**
 * @author pli
 * @since $Date:2014-09-13$
 */
public class ProductAction extends BaseBackendAction<ProductBusiness> {
    private static final long serialVersionUID = -5222876000116738224L;
    private static Logger LOG = LoggerFactory.getLogger(ProductAction.class);
    protected final static ProductLevelBusiness PLS = (ProductLevelBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_PRODUCTLEVEL);
    private String jsonInitImage;
    private List<ImageInfoBean> image;
    private ProductBean product;

    private List<ProductLevelBean> listProductLevel;

    public List<ProductLevelBean> getListProductLevel() {
        return listProductLevel;
    }

    public void setListProductLevel(List<ProductLevelBean> listProductLevel) {
        this.listProductLevel = listProductLevel;
    }

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public String getJsonInitImage() {
        return jsonInitImage;
    }

    public void setJsonInitImage(String jsonInitImage) {
        this.jsonInitImage = jsonInitImage;
    }

    public List<ImageInfoBean> getImage() {
        return image;
    }

    public void setImage(List<ImageInfoBean> image) {
        this.image = image;
    }

    @Override
    public String getActionPrex() {
        return getRequest().getContextPath() + "/backend/product";
    }

    @Override
    public String getCustomJsp() {
        return "/pages/product/makeImage.jsp";
    }

    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("image", "产品效果图"));
        init.getAoColumns().add(new TableHeaderVo("name", "产品名称").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("code", "编码").enableSearch());
        List<ProductLevelBean> productLevelBeans = (List<ProductLevelBean>) PLS.getAllLeaves().getResponseData();
        String[][] productLevelCodes = new String[2][productLevelBeans.size()];
        if (productLevelBeans.size() > 0) {
            for (int i = 0; i < productLevelBeans.size(); i++) {
                productLevelCodes[0][i] = productLevelBeans.get(i).getId();
                productLevelCodes[1][i] = productLevelBeans.get(i).getName();
            }
        } else {
            productLevelCodes = null;
        }
        init.getAoColumns().add(new TableHeaderVo("productLevelId", "分类").addSearchOptions(productLevelCodes).enableSearch());
        init.getAoColumns().add(new TableHeaderVo("state", "状态").enableSearch().addSearchOptions(new String[][]{{"0", "1"}, {"上架", "下架"}}));
        init.getAoColumns().add(new TableHeaderVo("price", "价格(元)", true));

        return init;
    }

    @Override
    public String getTableTitle() {
        return "<ul class=\"breadcrumb\"><li>产品管理</li><li class=\"active\">产品发布</li></ul>";
    }

    @Override
    public String save() throws Exception {
        //暂时这样配置，否则需要配置struts映射规则支持二级字段映射
        product.setImage(image);
        if (StringUtils.isBlank(product.getId())) {
            ProductBean userTmp = (ProductBean) getBusiness().getLeafByName(product.getName()).getResponseData();
            if (userTmp != null) {
                addActionError("产品存在");
                return FAILURE;
            } else {
                product.set_id(ObjectId.get());
                getBusiness().createLeaf(product);
            }
        } else {
            ProductBean origProductLevel = (ProductBean) getBusiness().getLeaf(product.getId().toString()).getResponseData();
            ProductBean newProductLevel = (ProductBean) origProductLevel.clone();
            BeanUtils.copyProperties(newProductLevel, product);
            //空值的时候,参考NullAwareBeanUtilsBean.java
            newProductLevel.setImage(product.getImage());
            getBusiness().updateLeaf(origProductLevel, newProductLevel);
        }
        return SUCCESS;
    }

    @Override
    public String add() throws Exception {
        listProductLevel = (List<ProductLevelBean>) PLS.getAllLeaves().getResponseData();
        return SUCCESS;
    }

    @Override
    public String edit() throws Exception {
        listProductLevel = (List<ProductLevelBean>) PLS.getAllLeaves().getResponseData();
        product = (ProductBean) getBusiness().getLeaf(getId()).getResponseData();
        //初始化图片列表
        this.jsonInitImage = UploadMultipleImageAction.jsonFromImageInfo(product.getImage());
        return SUCCESS;
    }


}
