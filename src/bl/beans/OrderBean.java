package bl.beans;

import actions.IgnoreJsonField;
import org.mongodb.morphia.annotations.Transient;

/**
 * Created by pli on 14-9-18.
 */
public class OrderBean extends Bean {
    //客户公司名称
    private String customerCompany;
    //客户名称
    private String customerName;
    //外键客户表
    private String customerId;
    //订单价格
    private float price;

    //订单状态 0 看稿  1 修改定稿 2 预付款下单 3 制作 4安装  5付清余款
    private int state;

    public static enum OState {
        Look(0), Modification(1), Advance(2), Make(3), Install(4), Close(5);
        private int value;

        OState(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    //负责人，外键字段，指向员工表的主键
    private String ResOfficer;

    //订单备注
    private String comments;

    @Transient
    private CustomerBean customerBean;

    @IgnoreJsonField
    public CustomerBean getCustomerBean() {
        if (this.customerBean != null) {
            return this.customerBean;
        }
        this.customerBean = super.getParentBean(CustomerBean.class, this.customerId);
        return this.customerBean;
    }

    public String getCustomerCompany() {
        return customerCompany;
    }

    public void setCustomerCompany(String customerCompany) {
        this.customerCompany = customerCompany;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getResOfficer() {
        return ResOfficer;
    }

    public void setResOfficer(String resOfficer) {
        ResOfficer = resOfficer;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
