package policy;

import java.util.Arrays;
import java.util.List;

/**
 * Created by peter on 2014/10/16.
 */
public abstract class PolicyEngine {

    public List<PolicyResult> sendToEngine(PolicyContext policyContext) {
        List<PolicyResult> policyResults = Arrays.<PolicyResult>asList();
        EventIf eventIf = policyContext.getEvent();
        List<PolicyRuleIf> policyRuleIfs = policyContext.getPolicyRuleList();
        if (policyResults != null && eventIf != null && policyRuleIfs != null) {
            for (int i = 0; i < policyRuleIfs.size(); i++) {
                AbstractPolicy policy = policyRuleIfs.get(i).getPolicy();
                policyResults.add(policy.execute(policyContext, policyRuleIfs.get(i)));
            }
        }
        return policyResults;
    }
}
