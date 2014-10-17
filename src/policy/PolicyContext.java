package policy;

import java.util.List;

/**
 * Created by peter on 2014/10/16.
 */
public class PolicyContext {
    private EventIf event;
    private List<PolicyRuleIf> policyRuleList;

    public EventIf getEvent() {
        return event;
    }

    public void setEvent(EventIf event) {
        this.event = event;
    }

    public List<PolicyRuleIf> getPolicyRuleList() {
        return policyRuleList;
    }

    public void setPolicyRuleList(List<PolicyRuleIf> policyRuleList) {
        this.policyRuleList = policyRuleList;
    }
}
