package bl.mongobus;

import bl.beans.CustomerBean;
import bl.common.BeanContext;
import bl.common.BusinessResult;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerBusiness extends MongoCommonBusiness<BeanContext, CustomerBean> {
    private static Logger LOG = LoggerFactory.getLogger(CustomerBean.class);
    protected final static OrderBusiness ORB = (OrderBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_ORDER);

    public CustomerBusiness() {
        this.clazz = CustomerBean.class;
    }

    public BusinessResult updateLeaf(BeanContext origBean, BeanContext newBean) {
        String customerId = ((CustomerBean) newBean).getId();
        ORB.updateCustomerInformation(customerId, (CustomerBean) newBean);
        return super.updateLeaf(origBean, newBean);
    }
}
