package bl.beans;

import actions.IgnoreJsonField;
import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Transient;

/**
 * Created by pli on 14-9-18.
 */
@Entity(value = "backend_order")
public class OrderBean extends Bean {
    //客户公司名称
    private String customerCompany;
    //客户姓名
    private String customerName;

    //客户姓名
    private String customerFixedPhone;
    //客户手机号码
    private String customerCellPhone;
    //外键客户表
    private String customerId;

    //测量报价
    private float offerPrice = 0;

    //订单价格
    private float price = 0;

    //0、预付定金  1未付定金 2、报价未做
    private int prePaymentState = 0;

    //预付款
    private float prePayment = 0;

    //实际收入prePayment+closePayment
    private float actualIncome = 0;

    //已付余款
    private float closePayment = 0;

    //未付款  price-prePayment-closePayment
    private float unPayment = 0;

    public String getCustomerFixedPhone() {
        return this.getCustomerBean().getFixedPhone();
    }

    public void setCustomerFixedPhone(String customerFixedPhone) {
        this.customerFixedPhone = customerFixedPhone;
    }

    public String getCustomerCellPhone() {
        String result = this.getCustomerBean().getCellPhone();
        if(StringUtils.isEmpty(result)){
            result = this.customerCellPhone;
        }
        return result;
    }

    public void setCustomerCellPhone(String customerCellPhone) {
        this.customerCellPhone = customerCellPhone;
    }

    public int getPrePaymentState() {
        return prePaymentState;
    }

    public void setPrePaymentState(int prePaymentState) {
        this.prePaymentState = prePaymentState;
    }

    public float getPrePayment() {
        return prePayment;
    }

    public void setPrePayment(float prePayment) {
        this.prePayment = prePayment;
    }

    public float getActualIncome() {
        return this.prePayment + this.closePayment;
    }

    public void setActualIncome(float actualIncome) {
        this.actualIncome = actualIncome;
    }

    public float getClosePayment() {
        return closePayment;
    }

    public void setClosePayment(float closePayment) {
        this.closePayment = closePayment;
    }

    public float getUnPayment() {
        return this.price - this.prePayment - this.closePayment;
    }

    public void setUnPayment(float unPayment) {
        this.unPayment = unPayment;
    }

    public void setCustomerBean(CustomerBean customerBean) {
        this.customerBean = customerBean;
    }

    //-1表示缺省值，起始状体  订单状态 0 测量报价 1设计 2看稿 3修改定稿 4金额 5预付款下单 6制作 7安装  8付清余款
    private int state = -1;

    public static enum OState {
        offPrice(0), Design(1), LookStuff(2), Modification(3), Price(4), PrePayment(5), Make(6), Install(7), Close(8);
        private int value;

        OState(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    //负责人，外键字段，指向员工表的主键
    private String resOfficer;

    //订单备注
    private String comments;

    @Transient
    private CustomerBean customerBean;

    public CustomerBean getCustomerBean() {
        if (this.customerBean != null) {
            return this.customerBean;
        }
        this.customerBean = super.getParentBean(CustomerBean.class, this.customerId);
        if (this.customerBean == null) {
            return new CustomerBean();
        }
        return this.customerBean;
    }

    public String getCustomerCompany() {
        return this.getCustomerBean().getCompany();
    }

    public void setCustomerCompany(String customerCompany) {
        this.customerCompany = customerCompany;
    }

    public String getCustomerName() {
        return this.getCustomerBean().getName();
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
        return resOfficer;
    }

    public void setResOfficer(String resOfficer) {
        this.resOfficer = resOfficer;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public float getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(float offerPrice) {
        this.offerPrice = offerPrice;
    }
}
