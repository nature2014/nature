/**
 * @author pli
 * @since 2014/09/13
 */
package bl.beans;

import org.mongodb.morphia.annotations.Entity;

import java.util.List;

/**
 * @author pli
 */
@Entity(value = "backend_customer")
public class CustomerBean extends Bean {

    //客户的图标
    private String logo;
    private String address;
    private String cellPhone;
    private String fixedPhone;
    private String qq;
    private String email;
    private String wechat;
    private List<ImageInfoBean> image;

    public List<ImageInfoBean> getImage() {
        return image;
    }

    public void setImage(List<ImageInfoBean> image) {
        this.image = image;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getFixedPhone() {
        return fixedPhone;
    }

    public void setFixedPhone(String fixedPhone) {
        this.fixedPhone = fixedPhone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }
}
