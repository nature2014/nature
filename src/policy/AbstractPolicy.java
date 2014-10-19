package policy;

import com.greenpineyu.fel.function.CommonFunction;
import com.greenpineyu.fel.function.Function;
import org.apache.commons.lang.math.NumberUtils;

import java.util.Calendar;

/**
 * Created by peter on 2014/10/16.
 * 实现一些基本的方法或者扩展方法,同时实现扩展的函数，采用标准的方法：Policy.xml中的定义
 */
public abstract class AbstractPolicy implements Policy {
    public boolean checkCondition(PolicyContext policyContext) {

        return false;
    }

    public static Function caculateDayOfWeek() {
        return new CommonFunction() {

            @Override
            public Object call(Object[] objects) {
                if (objects != null && objects.length > 0) {
                    Long passValue = NumberUtils.createLong(objects[0].toString());
                    int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                    if (day == passValue) {
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
