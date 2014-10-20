package bl.mongobus;

import bl.beans.PolicyBean;
import bl.common.BeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangronghua on 14-10-20.
 */
public class PolicyBusiness extends MongoCommonBusiness<BeanContext, PolicyBean> {
    private static Logger log = LoggerFactory.getLogger(PolicyBusiness.class);

    public PolicyBusiness() {
        //this.dbName = "form";
        this.clazz = PolicyBean.class;
    }
}
