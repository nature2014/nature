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
    private String code;
    private String summary;
    private List<ImageInfoBean> image;
    private float price;

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

    public List<ImageInfoBean> getImage() {
        return image;
    }

    public void setImage(List<ImageInfoBean> image) {
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

    private String volunteerBeanId;

    private VolunteerBean volunteerBean;

    public String getVolunteerBeanId() {
        return volunteerBeanId;
    }

    public void setVolunteerBeanId(String volunteerBeanId) {
        this.volunteerBeanId = volunteerBeanId;
    }

    public void setVolunteerBean(VolunteerBean volunteerBean) {
        this.volunteerBean = volunteerBean;
    }
    public VolunteerBean getVolunteerBean() {
        if (this.volunteerBean != null) {
            return this.volunteerBean;
        }
        this.volunteerBean = super.getParentBean(VolunteerBean.class, this.volunteerBeanId);
        return this.volunteerBean;
    }
}
