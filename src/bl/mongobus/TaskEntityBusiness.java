package bl.mongobus;

import bl.beans.TaskEntityBean;
import bl.common.BeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangronghua on 14-10-20.
 */
public class TaskEntityBusiness extends MongoCommonBusiness<BeanContext, TaskEntityBean> {

    private static Logger log = LoggerFactory.getLogger(TaskEntityBusiness.class);

    public TaskEntityBusiness() {
        //this.dbName = "form";
        this.clazz = TaskEntityBean.class;
    }
}
