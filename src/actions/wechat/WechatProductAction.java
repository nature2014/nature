package actions.wechat;

import bl.beans.ProductBean;
import bl.beans.ProductLevelBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.ProductBusiness;
import bl.mongobus.ProductLevelBusiness;

import java.util.List;

/**
 * Created by wangronghua on 14-9-14.
 */
public class WechatProductAction extends WechatBaseAuthAction {

    private static final ProductLevelBusiness productLevelBus = (ProductLevelBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_PRODUCTLEVEL);
    private static final ProductBusiness productBus = (ProductBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_PRODUCT);

    private List<ProductLevelBean> productLevelList;
    private List<ProductBean> productList;
    private ProductLevelBean productLevelBean;
    private ProductBean productBean;


    public String search() {

        if(null == volunteer) {
            return "redirectBinding";
        }
        productLevelList = (List<ProductLevelBean>) productLevelBus.getAllLeaves().getResponseData();
        if(null != productLevelBean && null != productLevelBean.getId()) {
            productLevelBean = (ProductLevelBean) productLevelBus.getLeaf(productLevelBean.getId()).getResponseData();
            productList = productBus.getProductsByProductLevelId(productLevelBean.getId());
        } else {
            productList = (List<ProductBean>) productBus.getAllLeaves().getResponseData();
        }
        return SUCCESS;
    }

    public List<ProductLevelBean> getProductLevelList() {
        return productLevelList;
    }

    public void setProductLevelList(List<ProductLevelBean> productLevelList) {
        this.productLevelList = productLevelList;
    }

    public List<ProductBean> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductBean> productList) {
        this.productList = productList;
    }

    public ProductLevelBean getProductLevelBean() {
        return productLevelBean;
    }

    public void setProductLevelBean(ProductLevelBean productLevelBean) {
        this.productLevelBean = productLevelBean;
    }

    public ProductBean getProductBean() {
        return productBean;
    }

    public void setProductBean(ProductBean productBean) {
        this.productBean = productBean;
    }

}
