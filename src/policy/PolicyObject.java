package policy;

import java.util.List;

/**
 * Created by limin.llm on 2014/10/19.
 * Policy = 多个Condition + 1个 Action
 */
public interface PolicyObject {

    public String getPolicyId();


    public List<PolicyCondition> getPolicyConditionList();


    public List<PolicyAction> getPolicyActions();

}
