package policy;

import java.util.Map;

/**
 * Created by peter on 2014/10/16.
 * 为了分离Condition和Action，采用类为了不直接使用Policy.xsd产生的对象
 */
public class PolicyCondition {
    private String id;
    /**
     * 来自交互传来的参数
     */
    private Map<String, Object> parameters;

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
