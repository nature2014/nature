package policy;

import java.util.List;

/**
 * Created by peter on 2014/10/16.
 * 这个是用来设计作为百宝箱的，承载策略对象和任务对象
 * 任务---1：n---映射策略
 * 策略---1:n---(多个Conditions + 1 Action)
 * 我们把Condition最小粒度化
 */
public class PolicyContext {
    private EventIf event;
    private List<List<PolicyCondition>> policyConditions;
    private List<PolicyAction> policyActions;

    public EventIf getEvent() {
        return event;
    }

    public void setEvent(EventIf event) {
        this.event = event;
    }

    public List<List<PolicyCondition>> getPolicyConditions() {
        return policyConditions;
    }

    public void setPolicyConditions(List<List<PolicyCondition>> policyConditions) {
        this.policyConditions = policyConditions;
    }

    public List<PolicyAction> getPolicyActions() {
        return policyActions;
    }

    public void setPolicyActions(List<PolicyAction> policyActions) {
        this.policyActions = policyActions;
    }
}
