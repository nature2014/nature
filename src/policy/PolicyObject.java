package policy;

import java.util.List;

/**
 * Created by limin.llm on 2014/10/19.
 * Policy = 多个Condition + 1个 Action
 */
public class PolicyObject {
    private String policyId;
    private List<PolicyCondition> policyConditionList;
    private PolicyAction policyAction;

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public List<PolicyCondition> getPolicyConditionList() {
        return policyConditionList;
    }

    public void setPolicyConditionList(List<PolicyCondition> policyConditionList) {
        this.policyConditionList = policyConditionList;
    }

    public PolicyAction getPolicyAction() {
        return policyAction;
    }

    public void setPolicyAction(PolicyAction policyAction) {
        this.policyAction = policyAction;
    }
}
