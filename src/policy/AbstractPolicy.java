package policy;

import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.ArrayCtxImpl;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.function.CommonFunction;
import com.greenpineyu.fel.function.Function;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import policy.schema.ConditionEntry;
import policy.schema.Parameter;
import util.PackageUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by peter on 2014/10/16.
 * 实现一些基本的方法或者扩展方法,同时实现扩展的函数，采用标准的方法：Policy.xml中的定义
 */
public abstract class AbstractPolicy implements Policy {
    private final static Logger LOG = LoggerFactory.getLogger(PolicyXmlManager.class);

    protected PolicyXmlManager policyXmlManager = PolicyXmlManager.getInstance();

    protected FelEngine fel = new FelEngineImpl();

    public AbstractPolicy() {
        initFelFunc();
    }

    protected void initFelFunc() {
        List<String> classNameList = PackageUtil.getClassName("policy.function");
        if (CollectionUtils.isNotEmpty(classNameList)) {

            for (String className : classNameList) {
                try {
                    Class cls = Class.forName(className);
                    if (cls.getAnnotation(PolicyFunc.class) != null) {
                        Object obj = cls.newInstance();
                        if (obj instanceof Function) {
                            fel.addFun((Function) obj);
                            LOG.error("add func:" + className);
                        } else {
                            LOG.debug("skip class because not implement Function:" + className);
                        }
                    } else {
                        LOG.error("skip class because not use PolicyFunc annotation:" + className);
                    }
                } catch (Exception e) {
                    LOG.error(className + "方法加载异常:", e);
                }
            }
        }
    }

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
            FelContext context = new ArrayCtxImpl();
            for (int j = 0; j < parameters.size(); j++) {
                Parameter parameter = parameters.get(i);
                String name = parameter.getName();
                Map<String, Object> activeParameter = policyCondition.getParameters();
                Object value = activeParameter.get(name) != null ? activeParameter.get(name) : parameter.getDefault();
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
