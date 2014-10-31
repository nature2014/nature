package actions.backend;

import bl.beans.PolicyBean;
import bl.mongobus.PolicyBusiness;
import org.apache.commons.lang.StringUtils;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;

/**
 * Created by wangronghua on 14-10-20.
 */
public class BackendPolicyAction extends BaseBackendAction<PolicyBusiness>  {

    private PolicyBean policy;

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

}
