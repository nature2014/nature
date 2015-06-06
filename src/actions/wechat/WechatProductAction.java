package actions.wechat;

import bl.beans.ImageInfoBean;
import bl.beans.ProductBean;
import bl.beans.ProductLevelBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.ProductBusiness;
import bl.mongobus.ProductLevelBusiness;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangronghua on 14-9-14.
 */
public class WechatProductAction extends WechatBaseAuthAction {

    private static final ProductLevelBusiness productLevelBus = (ProductLevelBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_PRODUCTLEVEL);
    private static final ProductBusiness productBus = (ProductBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_PRODUCT);

    private List<ProductLevelBean> productLevelList;
    private List<ProductBean> productList;
    private List<ImageInfoBean> imageList;
    private ProductLevelBean productLevelBean;
    private ProductBean productBean;
    private int index;
    private int count = 10;
    private int totalCount;
    private String rootPath;

    public String main() {
        return SUCCESS;
    }
    public String search() {
        productLevelList = (List<ProductLevelBean>) productLevelBus.getAllLeaves().getResponseData();
        if(null != productLevelBean && StringUtils.isNotEmpty(productLevelBean.getId())) {
            productLevelBean = (ProductLevelBean) productLevelBus.getLeaf(productLevelBean.getId()).getResponseData();
            if(null != productLevelBean) {
                productList = productBus.getProductsByProductLevelId(productLevelBean.getId());
            }
        } else {
            productList = (List<ProductBean>) productBus.getAllLeaves().getResponseData();
        }
        if(null == productList) return SUCCESS;
        List<ImageInfoBean> images = new ArrayList<ImageInfoBean>();
        for(ProductBean product : productList) {
            for(ImageInfoBean productImage: product.getImage()){
                productImage.setProduct(product);
                product.setVolunteerBean(product.getVolunteerBean());
                images.add(productImage);
            }
        }
        int start = index;
        index = Math.min(images.size(), index + 10);
        imageList = images.subList(start, index);
        totalCount = images.size();
        return SUCCESS;
    }

    public String loadRecords() {
        productLevelList = (List<ProductLevelBean>) productLevelBus.getAllLeaves().getResponseData();
        if(null != productLevelBean && StringUtils.isNotEmpty(productLevelBean.getId())) {
            productLevelBean = (ProductLevelBean) productLevelBus.getLeaf(productLevelBean.getId()).getResponseData();
            if(null != productLevelBean) {
                productList = productBus.getProductsByProductLevelId(productLevelBean.getId());
            }
        } else {
            productList = (List<ProductBean>) productBus.getAllLeaves().getResponseData();
        }
        if(null == productList) return SUCCESS;
        List<ImageInfoBean> images = new ArrayList<ImageInfoBean>();
        for(ProductBean product : productList) {
            for(ImageInfoBean productImage: product.getImage()){
                productImage.setProduct(product);
                images.add(productImage);
            }
        }
        int start = index;
        index = Math.min(images.size(), index + 10);
        imageList = images.subList(start, index);
        totalCount = images.size();
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ImageInfoBean> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageInfoBean> imageList) {
        this.imageList = imageList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getRootPath() {
        ServletContext s = ServletActionContext.getServletContext();
        return (String) s.getAttribute("rootPath");
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }


}
