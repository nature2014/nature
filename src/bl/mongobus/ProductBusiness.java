package bl.mongobus;

import bl.beans.ProductBean;
import bl.common.BeanContext;
import bl.common.BusinessResult;
import dao.MongoDBConnectionFactory;
import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ProductBusiness extends MongoCommonBusiness<BeanContext, ProductBean> {
    private static Logger LOG = LoggerFactory.getLogger(ProductBean.class);

    public ProductBusiness() {
        this.clazz = ProductBean.class;
    }

    public List<ProductBean> getProductsByProductLevelId(String productLevelId) {
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
        return dc.find(this.clazz, "productLevelId", productLevelId)
            .filter("isDeleted", false).asList();
    }
}
