package bl.mongobus;

import bl.beans.OrderBean;
import bl.common.BeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderBusiness extends MongoCommonBusiness<BeanContext, OrderBean> {
    private static Logger LOG = LoggerFactory.getLogger(OrderBean.class);

    public OrderBusiness() {
        this.clazz = OrderBean.class;
    }
}
