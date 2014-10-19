package bl.beans;

import org.mongodb.morphia.annotations.Entity;
import policy.EventIf;

import java.util.Map;

/**
 * Created by wangronghua on 14-10-19.
 */
@Entity(value = "task_instance")
public class TaskInstance extends Bean implements EventIf {
    private String taskId;      //关联的TaskEntity的ID
    private String employeeId;
    private Map<String, Object> parameters;     //用户在页面上填写的参数，和管理员在TaskEntity中定义的不开放给用户填写的参数

    @Override
    public String getTaskId() {
        return taskId;
    }

    @Override
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public Object getTask() {
        return null;
    }

    @Override
    public void setTask(Object task) {

    }

    @Override
    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;

    }

    @Override
    public Map<String, Object> getParameters() {
        return parameters;
    }

    @Override
    public String getEmployeeId() {
        return employeeId;
    }

    @Override
    public void setEmployeeId(String id) {
        this.employeeId = employeeId;
    }
}
