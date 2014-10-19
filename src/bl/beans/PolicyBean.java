package bl.beans;

import org.mongodb.morphia.annotations.Entity;
import policy.PolicyAction;
import policy.PolicyCondition;
import policy.PolicyObject;

import java.util.List;

/**
 * Created by wangronghua on 14-10-19.
 */
@Entity(value = "policy")
public class PolicyBean extends Bean implements PolicyObject {

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
        return null;
    }

    @Override
    public List<PolicyAction> getPolicyActions() {
        return null;
    }
}
