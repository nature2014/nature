package bl.mongobus;

import bl.beans.CustomerBean;
import bl.beans.OrderBean;
import bl.common.BeanContext;
import dao.MongoDBConnectionFactory;
import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderBusiness extends MongoCommonBusiness<BeanContext, OrderBean> {
    private static Logger LOG = LoggerFactory.getLogger(OrderBean.class);

    public OrderBusiness() {
        this.clazz = OrderBean.class;
    }

    public void updateCustomerInformation(String customerId, CustomerBean customerBean) {
        if (StringUtils.isEmpty(customerId)) {
            return;
        }
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
        UpdateOperations<OrderBean> ops
                = dc.createUpdateOperations(OrderBean.class)
                .set("customerCompany", customerBean.getCompany())
                .set("customerName", customerBean.getName())
                .set("customerFixedPhone", customerBean.getFixedPhone())
                .set("customerCellPhone", customerBean.getCellPhone());

        Query query = dc.createQuery(this.clazz);
        query.filter("customerId", customerId);
        dc.update(query, ops);
    }
}
