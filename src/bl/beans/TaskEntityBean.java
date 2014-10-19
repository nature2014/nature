package bl.beans;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Transient;

import java.util.List;

/**
 * Created by wangronghua on 14-10-18.
 * 后台管理员创建的Task
 */
@Entity(value = "task_entity")
public class TaskEntityBean extends Bean{

    private String description;

    private int type;               //0:日常任务，1:一般任务
    private int dispatchType;       //0:自由领取，1:仅可分配
    private int approveType;        //0:需审批，1:无需审批

    @Transient
    private List<PolicyBean> policies;
    @Transient
    private List<TaskParamBean> taskParams;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDispatchType() {
        return dispatchType;
    }

    public void setDispatchType(int dispatchType) {
        this.dispatchType = dispatchType;
    }

    public int getApproveType() {
        return approveType;
    }

    public void setApproveType(int approveType) {
        this.approveType = approveType;
    }

    public List<PolicyBean> getPolicies() {
        return policies;
    }

    public void setPolicies(List<PolicyBean> policies) {
        this.policies = policies;
    }

    public List<TaskParamBean> getTaskParams() {
        return taskParams;
    }

    public void setTaskParams(List<TaskParamBean> taskParams) {
        this.taskParams = taskParams;
    }


}
