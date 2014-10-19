package policy;

import java.util.Map;

/**
 * Created by peter on 2014/10/16.
 * 所有的条件抽象类，为了分离Condition和Action，采用派生类。
 */
public abstract class PolicyCondition extends AbstractPolicy {
    /**
     * 目前，列表中的条件都是且的关系，将来以后支持and,or,!,运算符
     */
    public abstract boolean checkCondition(PolicyContext policyContext);
}
