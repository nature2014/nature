package policy.impl;

import policy.AbstractPolicy;
import policy.PolicyContext;
import policy.PolicyResult;

/**
 * Created by limin.llm on 2014/10/19.
 * 这个是加积分的计算器
 */
public class DefaultPolicy extends AbstractPolicy {
    @Override
    public PolicyResult execute(PolicyContext policyContext) {
        throw new RuntimeException("此方法不应该被调用，此类是为了调用checkCondition方法使用");
    }
}
