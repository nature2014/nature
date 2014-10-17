package policy;

/**
 * Created by peter on 2014/10/16.
 * 引用task信息
 */
public interface EventIf {

    int getTaskId();

    void setTaskId(int taskId);

    Object getTask();

    void setTask(Object task);
}
