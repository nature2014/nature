package policy;

import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.function.CommonFunction;
import com.greenpineyu.fel.function.Function;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import policy.schema.ActionEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by peter on 2014/10/16.
 */
public abstract class PolicyEngine {
    private PolicyXmlManager policyXmlManager = PolicyXmlManager.getInstance();
    private final static Logger LOG = LoggerFactory.getLogger(PolicyXmlManager.class);
    protected final static ConcurrentHashMap<String, Policy> cachedPolicy = new ConcurrentHashMap<String, Policy>();

    /**
     * 给外部调用此消息接口,依赖于正在开发的领域对象关系
     * Task关联多个Policies , 一个Policy映射多个Condition和1个Action
     *
     * @param eventif
     * @return
     */
    public List<PolicyResult> processPolicies(EventIf eventif) {
        List<PolicyResult> policyResults = new ArrayList<PolicyResult>();
        if (eventif != null) {
            List<PolicyObject> policyObjectList = eventif.getPolicyObjects();
            if (policyObjectList != null) {
                for (PolicyObject policyObject : policyObjectList) {
                    //调用builder模式构建上下文
                    PolicyContext policyContext = builderPolicy(eventif, policyObject);
                    List<PolicyResult> policyResultAll = processPolicy(policyContext);
                    //结果放入结果集
                    policyResults.addAll(policyResultAll);
                }
            }

        }
        return null;
    }

    /**
     * 构建者模式
     *
     * @return
     */
    public PolicyContext builderPolicy(EventIf eventif, PolicyObject policyObject) {
        /**
         *  在炉子里锻造PolicyContext，基本上从eventIf可以推断出必要的PolicyCondition和PolicyAction
         *
         *  需要根据taskid去查找对应的关联关系表
         */
        if (eventif != null) {
            List<PolicyCondition> policyConditions = policyObject.getPolicyConditionList();
            List<PolicyAction> policyActions = policyObject.getPolicyActions();
            if (eventif != null && policyConditions != null && policyActions != null) {
                PolicyContext policyContext = new PolicyContext();
                policyContext.setPolicyConditions(policyConditions);
                policyContext.setPolicyActions(policyActions);
                policyContext.setEvent(eventif);
                return policyContext;
            }
        }
        return null;
    }

    /**
     * 在这个处理方法中，一个Policy包含了 组合的Conditions和组合的Actions，
     * 所以返回是执行的结果集列表
     *
     * @param policyContext
     * @return List<PolicyResult>
     */
    private List<PolicyResult> processPolicy(PolicyContext policyContext) {
        List<PolicyAction> policyActions = policyContext.getPolicyActions();
        List<PolicyResult> policyResults = new ArrayList<PolicyResult>();
        for (PolicyAction policyAction : policyActions) {
            String actionId = policyAction.getId();
            ActionEntry actionEntry = policyXmlManager.queryActionById(actionId);
            if (actionEntry != null) {
                String classPath = actionEntry.getClassPath();
                try {
                    Policy policy = null;
                    //读取缓存对象
                    if (cachedPolicy.contains(actionId)) {
                        policy = cachedPolicy.get(actionId);
                    } else {
                        policy = (Policy) Class.forName(classPath).newInstance();
                        Policy oldPolicy = cachedPolicy.putIfAbsent(actionId, policy);
                        if (oldPolicy != null) {
                            //还是用旧的对象，保持缓存一致
                            policy = oldPolicy;
                        }
                    }

                    if (policy.checkCondition(policyContext)) {
                        PolicyResult policyResult = policy.execute(policyContext);
                        //一个Policy执行结果放入结果集里
                        policyResults.add(policyResult);
                    }
                } catch (Exception en) {
                    LOG.error("锻造对象失败{}", en.getMessage());
                }
            }
        }
        return policyResults;
    }
}