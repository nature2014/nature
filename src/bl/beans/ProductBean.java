/**
 * @author pli
 * @since 2014/09/13
 */
package bl.beans;

import actions.IgnoreJsonField;
import org.mongodb.morphia.annotations.Entity;

import java.util.Collections;
import java.util.List;

/**
 * @author pli
 */
@Entity(value = "backend_product")
public class ProductBean extends Bean {
    //产品编码
    private String code;
    //摘要信息
    private String summary;
    //产品图片可以是多张
    private String[] image;
    //价格
    private float price;

    //产品状态，上架，下架
    private int state;

    public static enum PState {
        OnShelves(0), OffShelves(1);
        private int value;

        PState(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String[] getImage() {
        return image;
    }

    public void setImage(String[] image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getProductLevelId() {
        return productLevelId;
    }

    public void setProductLevelId(String productLevelId) {
        this.productLevelId = productLevelId;
    }

    //关联产品类别,暂时一个产品只属于一个类别
    private String productLevelId;

    private ProductLevelBean productLevelBean;

    public void setProductLevelBean(ProductLevelBean productLevelBean) {
        this.productLevelBean = productLevelBean;
    }

    @IgnoreJsonField
    public ProductLevelBean getProductLevelBean() {
        if (this.productLevelBean != null) {
            return this.productLevelBean;
        }
        this.productLevelBean = super.getParentBean(ProductLevelBean.class, this.productLevelId);
        return this.productLevelBean;
    }
}
