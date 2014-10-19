package policy;

import com.greenpineyu.fel.Expression;
import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.function.CommonFunction;
import com.greenpineyu.fel.function.Function;
import org.apache.commons.lang.math.NumberUtils;

import java.util.*;

/**
 * Created by peter on 2014/10/16.
 */
public abstract class PolicyEngine {

    public List<PolicyResult> sendToEngine(PolicyContext policyContext) {
        List<PolicyResult> policyResults = Arrays.<PolicyResult>asList();
        EventIf eventIf = policyContext.getEvent();
        List<PolicyConditionIf> policyRuleIfs = policyContext.getPolicyRuleList();
        if (policyResults != null && eventIf != null && policyRuleIfs != null) {
            for (int i = 0; i < policyRuleIfs.size(); i++) {
            }
        }
        return policyResults;
    }

    public static void main(String[] args) {
        FelEngine fel = new FelEngineImpl();
        //添加今天是周几的函数
        fel.addFun(PolicyEngine.caculateDayOfWeek());
        FelContext context = fel.getContext();
        context.set("a", true);
        context.set("b", true);
        Expression exp = fel.compile("a && b && caculateDayOfWeek(7)", context);

        if ((boolean) exp.eval(context)) {
            System.out.println("执行Action");
        }
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
