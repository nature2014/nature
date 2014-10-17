package policy;

/**
 * Created by peter on 2014/10/16.
 * Policy核心执行接口
 */
public interface Policy {
    public PolicyResult execute(PolicyContext policyContext, PolicyRuleIf policyRuleIf);
}
