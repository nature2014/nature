package policy.function;

import com.greenpineyu.fel.function.CommonFunction;
import org.apache.commons.lang.math.NumberUtils;
import policy.PolicyFunc;

import java.util.Calendar;

/**
 * Created by limin.llm on 2014/10/21.
 */
@PolicyFunc
public class CaculateDayOfWeek extends CommonFunction {
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
}
