package bl.mongobus;

import bl.beans.TaskInstance;
import bl.common.BeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangronghua on 14-10-20.
 */
public class TaskInstanceBusiness extends MongoCommonBusiness<BeanContext, TaskInstance> {

    private static Logger log = LoggerFactory.getLogger(TaskInstanceBusiness.class);

    public TaskInstanceBusiness() {
        //this.dbName = "form";
        this.clazz = TaskInstance.class;
    }
}
