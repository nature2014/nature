package bl.beans;

import org.apache.commons.collections.CollectionUtils;
import org.mongodb.morphia.annotations.Entity;
import policy.PolicyAction;
import policy.PolicyCondition;
import policy.PolicyObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangronghua on 14-10-19.
 */
@Entity(value = "policy")
public class PolicyBean extends Bean implements PolicyObject {

    private int priority = 0;
    private List<String> conditions;
    private List<String> actions;

    public List<String> getConditions() {
        return conditions;
    }

    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    @Override
    public String getPolicyId() {
        return null;
    }

    @Override
    public List<PolicyCondition> getPolicyConditionList() {
        if (CollectionUtils.isNotEmpty(conditions)) {
            List<PolicyCondition> policyConditions = new ArrayList<>(conditions.size());
            for (String condId : conditions) {
                PolicyCondition policyCondition = new PolicyCondition();
                policyCondition.setId(condId);
                policyConditions.add(policyCondition);
            }
            return policyConditions;
        }
        return null;
    }

    @Override
    public List<PolicyAction> getPolicyActions() {
        if (CollectionUtils.isNotEmpty(actions)) {
            List<PolicyAction> policyActions = new ArrayList<>(actions.size());
            for (String actionId : actions) {
                PolicyAction policyAction = new PolicyAction();
                policyAction.setId(actionId);
                policyActions.add(policyAction);
            }
            return policyActions;
        }
        return null;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
