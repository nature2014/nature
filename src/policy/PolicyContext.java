package policy;

import java.util.List;

/**
 * Created by peter on 2014/10/16.
 * 这个是用来设计作为百宝箱的，里面含有很多东西
 */
public class PolicyContext {
    private EventIf event;
    private List<PolicyConditionIf> policyConditions;

    public EventIf getEvent() {
        return event;
    }

    public void setEvent(EventIf event) {
        this.event = event;
    }

    public List<PolicyConditionIf> getPolicyConditions() {
        return policyConditions;
    }

    public void setPolicyConditions(List<PolicyConditionIf> policyConditions) {
        this.policyConditions = policyConditions;
    }
}
