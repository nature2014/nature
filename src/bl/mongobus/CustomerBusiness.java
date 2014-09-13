package bl.mongobus;

import bl.beans.CustomerBean;
import bl.common.BeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerBusiness extends MongoCommonBusiness<BeanContext, CustomerBean> {
    private static Logger LOG = LoggerFactory.getLogger(CustomerBean.class);

    public CustomerBusiness() {
        this.clazz = CustomerBean.class;
    }
}
