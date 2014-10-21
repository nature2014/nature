package policy;

import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.function.CommonFunction;
import com.greenpineyu.fel.function.Function;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;
import policy.schema.ConditionEntry;
import policy.schema.Parameter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by peter on 2014/10/16.
 * 实现一些基本的方法或者扩展方法,同时实现扩展的函数，采用标准的方法：Policy.xml中的定义
 */
public abstract class AbstractPolicy implements Policy {
    protected PolicyXmlManager policyXmlManager = PolicyXmlManager.getInstance();

    public boolean checkCondition(PolicyContext policyContext) {
        List<PolicyCondition> policyConditions = policyContext.getPolicyConditions();
        //迭代每个Condition
        boolean globalBool = true;
        for (int i = 0; i < policyConditions.size(); i++) {
            PolicyCondition policyCondition = policyConditions.get(i);
            //查询Policy.xml模版定义的条件
            ConditionEntry conditionEntry = policyXmlManager.queryConditionById(policyCondition.getId());

            //参数化，用交互的参数覆盖缺省的
            List<Parameter> parameters = conditionEntry.getParameter();
            boolean checkCondition = true;
            FelEngine fel = new FelEngineImpl();
            FelContext context = fel.getContext();

            for (int j = 0; j < parameters.size(); j++) {
                Parameter parameter = parameters.get(i);
                String name = parameter.getName();
                Map<String, Object> activeParameter = policyCondition.getParameters();
                Object value = activeParameter.get(name) != null ? activeParameter.get(name) != null : parameter.getDefault();
                //数据类型转化
                if (value != null) {
                    String dateType = parameter.getDataType();
                    if (dateType.equals("string")) {
                        value = value.toString();
                    } else if (dateType.equals("int")) {
                        value = NumberUtils.toInt(value.toString());
                    } else if (dateType.equals("long")) {
                        value = NumberUtils.toLong(value.toString());
                    } else if (dateType.equals("float")) {
                        value = NumberUtils.toFloat(value.toString());
                    } else if (dateType.equals("boolean")) {
                        value = BooleanUtils.toBoolean(value.toString());
                    }
                } else {
                    //数据错误，也直接是false
                    checkCondition = false;
                    break;
                }
                context.set(name, value);
            }

            //表示上面无解析错误
            if (checkCondition) {
                //利用FEL评估最后的表达式结果
                Boolean returnBoolean = (Boolean) fel.eval(conditionEntry.getExpression(), context);
                globalBool = globalBool && returnBoolean;
                if (!globalBool) {
                    //有一个false,就直接跳出
                    break;
                }
            }
        }
        return globalBool;
    }
}
