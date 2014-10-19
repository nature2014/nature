package policy;

import java.util.Map;

/**
 * Created by peter on 2014/10/16.
 * 所有的条件接口，这些信息来自于前面传入进来的
 */
public interface PolicyConditionIf {
    String getId();

    void setId(String id);

    /**
     * 条件判断的参数，参考Policy.xml
     *
     * @return
     */
    Map<String, Object> getParameters();

    void setParameters(Map<String, Object> parameters);
}
