package bl.mongobus;

import bl.beans.ProductLevelBean;
import bl.common.BeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductLevelBusiness extends MongoCommonBusiness<BeanContext, ProductLevelBean> {
    private static Logger LOG = LoggerFactory.getLogger(ProductLevelBean.class);

    public ProductLevelBusiness() {
        this.clazz = ProductLevelBean.class;
    }
}
