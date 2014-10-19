package policy;

import java.util.List;
import java.util.Map;

/**
 * Created by peter on 2014/10/16.
 * 引用task信息，承载任务的事件载体
 */
public interface EventIf {
    /**
     * 任务的id
     *
     * @return
     */
    String getTaskId();

    void setTaskId(String taskId);

    /**
     * 任务对象，可以是Bean或者其他任何对象，在PolicyEngine中需要instanceof判断，
     * 将来可以把任务对象接口化，实现不同的类型
     *
     * @return
     */
    Object getTask();

    void setTask(Object task);

    /**
     * 此任务事件的参数,参考Policy.xml每个Condition定义的变量作为key
     * 任何key都是有namespace,为了适配one task mapping multiple Condition.
     *
     * @param parameters
     */
    void setParameters(Map<String, Object> parameters);

    Map<String, Object> getParameters();

    /**
     * 承载员工的id
     *
     * @return
     */
    String getEmployeeId();

    void setEmployeeId(String id);

    /**
     * 任务和对应的ConditionId
     *
     * @return
     */
    List<String> getPolicyConditionId();

    void setPolicyConditionId();

    /**
     * 任务和对应的ActionId
     *
     * @return
     */
    String getPolicyActionId();

    void setPolicyActionId();
}
