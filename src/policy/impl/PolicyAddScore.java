package policy.impl;

import policy.AbstractPolicy;
import policy.PolicyContext;
import policy.PolicyResult;

/**
 * Created by limin.llm on 2014/10/19.
 * 这个是加积分的计算器
 */
public class PolicyAddScore extends AbstractPolicy {

    @Override
    public PolicyResult execute(PolicyContext policyContext) {
        PolicyResult policyResult = new PolicyResult();
        policyResult.setStatus(0);
        policyResult.setMessage("恭喜你本次任务加" + 30 + "分");
        System.out.println("Sysout 恭喜你本次任务加" + 30 + "分");
        return policyResult;
    }
}
