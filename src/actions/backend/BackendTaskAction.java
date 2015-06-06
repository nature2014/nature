package actions.backend;

import bl.beans.TaskEntityBean;
import policy.PolicyObject;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;

/**
 * Created by wangronghua on 14-10-20.
 */
public class BackendTaskAction extends BaseBackendAction {

    private TaskEntityBean taskEntityBean;
    private PolicyObject policyObject;

    @Override
    public String getActionPrex() {
        return getRequest().getContextPath() + "/backend/task";
    }

    @Override
    public String getCustomJs() {
        return getRequest().getContextPath() + "/js/task.js";
    }

    public String getCustomJsp() {
        return "/pages/menu_task/task.jsp";
    };

    @Override
    public String getTableTitle() {
        return "<li>积分管理</li><li class=\"active\">任务</li>";
    }

    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("name", "任务名称").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("type", "任务类型"));
        init.getAoColumns().add(new TableHeaderVo("dispatchType", "分配类型"));
        init.getAoColumns().add(new TableHeaderVo("approveType", "审批类型"));
        return init;
    }

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

    public TaskEntityBean getTaskEntityBean() {
        return taskEntityBean;
    }

    public void setTaskEntityBean(TaskEntityBean taskEntityBean) {
        this.taskEntityBean = taskEntityBean;
    }
}
