package actions.front;

/**
 * Created by wangronghua on 14-10-20.
 */
public class TaskAction extends BaseFrontAction {

    /**
     * 员工点击完成任务，根据任务类型是否需要审批，或进入后面流程进行审批，或直接完成
     * @return
     */
    public String submitTask() {

        return SUCCESS;
    }
}
