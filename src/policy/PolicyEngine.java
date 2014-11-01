package policy;

import bl.beans.PolicyBean;
import bl.beans.TaskInstance;
import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.function.CommonFunction;
import com.greenpineyu.fel.function.Function;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import policy.impl.DefaultPolicy;
import policy.schema.ActionEntry;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by peter on 2014/10/16.
 */
public class PolicyEngine {
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
        if (eventif == null || CollectionUtils.isEmpty(eventif.getPolicyObjects())) {
            return null;
        }
        List<PolicyResult> policyResults = new ArrayList<>(eventif.getPolicyObjects().size());
        if (eventif != null) {
            List<PolicyObject> policyObjectList = eventif.getPolicyObjects();
            if (policyObjectList != null) {
                for (PolicyObject policyObject : policyObjectList) {
                    //调用builder模式构建上下文
                    PolicyContext policyContext = builderPolicy(eventif, policyObject);
                    List<PolicyResult> policyResultAll = processPolicy(policyContext);
                    //结果放入结果集
                    if (CollectionUtils.isNotEmpty(policyResultAll)) {
                        policyResults.addAll(policyResultAll);
                    }
                }
            }

        }
        return policyResults;
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
                for (PolicyCondition policyCondition : policyConditions) {
                    policyCondition.setParameters(eventif.getParameters());
                }
                for (PolicyAction policyAction : policyActions) {
                    policyAction.setParameters(eventif.getParameters());
                }
                return policyContext;
            }
        }
        return null;
    }

    private List<PolicyResult> processPolicy(PolicyContext policyContext) {
        if (processPolicyCondition(policyContext)) {
            return processPolicyAction(policyContext);
        }
        return null;
    }

    private boolean processPolicyCondition(PolicyContext policyContext) {
        DefaultPolicy defaultPolicy = new DefaultPolicy();
        return defaultPolicy.checkCondition(policyContext);
    }

    /**
     * 在这个处理方法中，一个Policy包含了 组合的Conditions和组合的Actions，
     * 所以返回是执行的结果集列表
     *
     * @param policyContext
     * @return List<PolicyResult>
     */
    private List<PolicyResult> processPolicyAction(PolicyContext policyContext) {
        List<PolicyAction> policyActions = policyContext.getPolicyActions();
        List<PolicyResult> policyResults = new ArrayList<>();
        for (PolicyAction policyAction : policyActions) {
            String actionId = policyAction.getId();
            ActionEntry actionEntry = policyXmlManager.queryActionById(actionId);
            if (actionEntry != null) {
                String classPath = actionEntry.getClassPath();
                try {
                    Policy policy = getPolicy(actionId, classPath);
                    PolicyResult policyResult = policy.execute(policyContext);
                    //一个Policy执行结果放入结果集里
                    policyResults.add(policyResult);
                } catch (Exception en) {
                    LOG.error("锻造对象失败{}", en.getMessage());
                }
            }
        }
        return policyResults;
    }

    private Policy getPolicy(String actionId, String classPath) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
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
        return policy;
    }

    public void simplePolicyTest() {
        EventIf eventIf = new TaskInstance();
        eventIf.setEmployeeId("1001");
        List<PolicyObject> policyObjects = new ArrayList<>(1);
        eventIf.setPolicyObjects(policyObjects);
        PolicyBean policyBean = new PolicyBean();
        policyObjects.add(policyBean);
        //设定condition
        {
            List<String> policyConditions = new ArrayList<>(1);
            policyConditions.add("condition_1");
            policyBean.setConditions(policyConditions);
        }
        //设定action
        {
            List<String> policyActions = new ArrayList<>(1);
            policyActions.add("action_score");
            policyBean.setActions(policyActions);
        }
        //设定Params
        {
            Map<String, Object> parameters = new HashMap<>();
            eventIf.setParameters(parameters);
            parameters.put("day", 7);
        }

        List<PolicyResult> policyResults = processPolicies(eventIf);
        System.out.println(policyResults.get(0).getMessage());
    }

    public static void main(String[] args) {
        PolicyEngine policyEngine = new PolicyEngine();
        policyEngine.simplePolicyTest();
    }
}