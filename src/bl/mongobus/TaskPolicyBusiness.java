package bl.mongobus;

import bl.beans.TaskPolicyBean;
import bl.common.BeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangronghua on 14-10-20.
 */
public class TaskPolicyBusiness extends MongoCommonBusiness<BeanContext, TaskPolicyBean> {
    private static Logger log = LoggerFactory.getLogger(TaskPolicyBusiness.class);

    public TaskPolicyBusiness() {
        //this.dbName = "form";
        this.clazz = TaskPolicyBean.class;
    }
}
