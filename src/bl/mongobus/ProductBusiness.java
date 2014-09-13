package bl.mongobus;

import bl.beans.ProductBean;
import bl.common.BeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductBusiness extends MongoCommonBusiness<BeanContext, ProductBean> {
    private static Logger LOG = LoggerFactory.getLogger(ProductBean.class);

    public ProductBusiness() {
        this.clazz = ProductBean.class;
    }
}
