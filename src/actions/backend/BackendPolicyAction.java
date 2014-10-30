package actions.backend;

import bl.beans.SourceCodeBean;
import bl.mongobus.PolicyBusiness;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;
import vo.table.TableQueryVo;

import java.util.List;

/**
 * Created by wangronghua on 14-10-20.
 */
public class BackendPolicyAction extends BaseBackendAction<PolicyBusiness>  {



    @Override
    public String getActionPrex() {
        return getRequest().getContextPath() + "/backend/policy";
    }

    @Override
    public String getCustomJs() {
        return getRequest().getContextPath() + "/js/policy.js";
    }

    public String getCustomJsp() {
        return "/pages/volunteer/policy.jsp";
    };

    @Override
    public String getTableTitle() {
        return "<li>策略管理</li><li class=\"active\">策略</li>";
    }


    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("name", "策略名称").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("priority", "优先级"));
        init.getAoColumns().add(new TableHeaderVo("description", "策略描述"));
        return init;
    }

    /**
     * 创建或保存Policy
     * @return
     */
    public String savePolicy() {
        return SUCCESS;
    }
}
