package policy;

import com.greenpineyu.fel.Expression;
import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.function.CommonFunction;
import com.greenpineyu.fel.function.Function;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import policy.schema.ActionEntry;

import java.util.*;
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

        return null;
    }

    /**
     * 构建者模式
     *
     * @param policyCondition
     * @param policyAction
     * @return
     */
    public PolicyContext builderPolicy(List<PolicyCondition> policyCondition, PolicyAction policyAction) {
        /**
         *  在炉子里锻造PolicyContext，基本上从eventIf可以推断出必要的PolicyCondition和PolicyAction
         *
         *  需要根据taskid去查找对应的关联关系表
         */
        return new PolicyContext();
    }

    private PolicyResult processPolicy(PolicyContext policyContext) {
        PolicyAction policyAction = policyContext.getPolicyActions();
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
                    return policyResult;
                }
            } catch (Exception en) {
                LOG.error("锻造对象失败{}", en.getMessage());
            }
        }
        return null;
    }

    public static void main(String[] args) {
        FelEngine fel = new FelEngineImpl();
        //添加今天是周几的函数
        fel.addFun(PolicyEngine.caculateDayOfWeek());
        FelContext context = fel.getContext();
        context.set("a", true);
        context.set("b", true);
        //Expression exp = fel.compile("a && b && caculateDayOfWeek(1)", null);

        if ((boolean) fel.eval("caculateDayOfWeek(1) && a==b", context)) {
            System.out.println("执行Action");
        }
        System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
    }

    public static Function caculateDayOfWeek() {
        return new CommonFunction() {

            @Override
            public Object call(Object[] objects) {
                if (objects != null && objects.length > 0) {
                    Long passValue = NumberUtils.createLong(objects[0].toString());
                    int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                    if (day == passValue) {
                        System.out.println("Today is day: " + day);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }

            }

            @Override
            //函数名称
            public String getName() {
                return "caculateDayOfWeek";
            }
        };
    }

}
