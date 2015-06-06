package actions.backend;

import bl.beans.PolicyBean;
import bl.mongobus.PolicyBusiness;
import org.apache.commons.lang.StringUtils;
import policy.PolicyXmlManager;
import policy.schema.ActionEntry;
import policy.schema.ConditionEntry;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;

import java.util.Collections;
import java.util.List;

/**
 * Created by wangronghua on 14-10-20.
 */
public class BackendPolicyAction extends BaseBackendAction<PolicyBusiness>  {

    private PolicyBean policy;

    private ConditionEntry[] conditions;
    private ActionEntry[] actions;

    @Override
    public String getActionPrex() {
        return getRequest().getContextPath() + "/backend/policy";
    }

    @Override
    public String getCustomJs() {
        return getRequest().getContextPath() + "/js/policy.js";
    }

    public String getCustomJsp() {
        return "/pages/menu_policy/policy.jsp";
    };

    @Override
    public String getTableTitle() {
        return "<li>积分管理</li><li class=\"active\">策略</li>";
    }


    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("name", "策略名称").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("priority", "优先级"));
        init.getAoColumns().add(new TableHeaderVo("description", "策略描述"));
        return init;
    }

    public String save() {
        if(null != policy) {
            if(StringUtils.isNotEmpty(policy.getId())) {
                getBusiness().updateLeaf(policy, policy);
            } else {
                getBusiness().createLeaf(policy);
            }
        }
        return SUCCESS;
    }

    public String edit() throws Exception {
        String policyId = getId();
        if(StringUtils.isNotEmpty(policyId)) {
            policy = (PolicyBean) getBusiness().getLeaf(policyId).getResponseData();
        }

        actions = PolicyXmlManager.getInstance().getActionEntryMap().values().toArray(new ActionEntry[0]);
        conditions = PolicyXmlManager.getInstance().getConditionEntryMap().values().toArray(new ConditionEntry[0]);
        return SUCCESS;
    }

    /**
     * 创建或保存Policy
     * @return
     */
    public String savePolicy() {
        return SUCCESS;
    }

    public PolicyBean getPolicy() {
        return policy;
    }

    public void setPolicy(PolicyBean policy) {
        this.policy = policy;
    }


    public ConditionEntry[] getConditions() {
        return conditions;
    }

    public void setConditions(ConditionEntry[] conditions) {
        this.conditions = conditions;
    }

    public ActionEntry[] getActions() {
        return actions;
    }

    public void setActions(ActionEntry[] actions) {
        this.actions = actions;
    }
}
