package actions.wechat;

import bl.beans.OrderBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.CustomerBusiness;
import bl.mongobus.OrderBusiness;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created by wangronghua on 14-9-21.
 */
public class WechatOrderAction extends WechatBaseAuthAction {
    private static final OrderBusiness orderBusiness = (OrderBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_ORDER);

    private String customerCellPhone;
    private String customerName;
    private OrderBean orderBean;
    private List<OrderBean> orderBeanList;

    public String query() {
        if(StringUtils.isNotEmpty(customerCellPhone)) {
            Map<String, String> query = new HashMap<String, String>();
            query.put("customerCellPhone_=", customerCellPhone);
            Set<String> sorted = new HashSet();
            sorted.add("-createTime");
            orderBeanList = orderBusiness.queryDataByCondition(query, sorted);
        }
        return SUCCESS;
    }

    public String getOrder() {
        if(null != orderBean && null != orderBean.getId()) {
            orderBean = (OrderBean) orderBusiness.getLeaf(orderBean.getId()).getResponseData();
        }
        return SUCCESS;
    }

    public String getCustomerCellPhone() {
        return customerCellPhone;
    }

    public void setCustomerCellPhone(String customerCellPhone) {
        this.customerCellPhone = customerCellPhone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public OrderBean getOrderBean() {
        return orderBean;
    }

    public void setOrderBean(OrderBean orderBean) {
        this.orderBean = orderBean;
    }

    public List<OrderBean> getOrderBeanList() {
        return orderBeanList;
    }

    public void setOrderBeanList(List<OrderBean> orderBeanList) {
        this.orderBeanList = orderBeanList;
    }
}
