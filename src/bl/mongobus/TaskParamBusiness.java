package bl.mongobus;

import bl.beans.TaskParamBean;
import bl.common.BeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangronghua on 14-10-20.
 */
public class TaskParamBusiness extends MongoCommonBusiness<BeanContext, TaskParamBean>{
    private static Logger log = LoggerFactory.getLogger(TaskParamBusiness.class);

    public TaskParamBusiness() {
        //this.dbName = "form";
        this.clazz = TaskParamBean.class;
    }
}
