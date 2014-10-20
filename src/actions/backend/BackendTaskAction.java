package actions.backend;

/**
 * Created by wangronghua on 14-10-20.
 */
public class BackendTaskAction extends BaseBackendAction {

    /**
     * 保存任务配置信息，包括关联的Policy和任务的参数
     * @return
     */
    public String saveTaskEntity() {
        return SUCCESS;
    }

    /**
     * 审核Task是否完成
     * @return
     */
    public String processTask() {
        return SUCCESS;
    }
}
