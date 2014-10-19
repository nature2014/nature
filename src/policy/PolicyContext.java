package policy;

import java.util.List;

/**
 * Created by peter on 2014/10/16.
 * 这个是用来设计作为百宝箱的，承载策略对象和任务对象
 * 策略规则：应该是多个条件+1个动作
 */
public class PolicyContext {
    private EventIf event;
    private List<PolicyCondition> policyConditions;
    private PolicyAction policyActions;

    public EventIf getEvent() {
        return event;
    }

    public void setEvent(EventIf event) {
        this.event = event;
    }

    public List<PolicyCondition> getPolicyConditions() {
        return policyConditions;
    }

    public void setPolicyConditions(List<PolicyCondition> policyConditions) {
        this.policyConditions = policyConditions;
    }

    public PolicyAction getPolicyActions() {
        return policyActions;
    }

    public void setPolicyActions(PolicyAction policyActions) {
        this.policyActions = policyActions;
    }
}
