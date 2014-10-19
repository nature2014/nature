package policy;

import java.util.List;

/**
 * Created by peter on 2014/10/16.
 * Policy核心执行接口，什么是Policy?
 * Policy = Condition + Action
 */
public interface Policy {
    /**
     * 目前，列表中的条件都是且的关系，将来以后支持and,or,!,运算符
     *
     * @param policyRuleIf
     * @return
     */
    public boolean checkCondition(List<PolicyConditionIf> policyRuleIf);

    public PolicyResult execute(PolicyContext policyContext);
}
