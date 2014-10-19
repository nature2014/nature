package policy;

import java.util.Map;

/**
 * Created by limin.llm on 2014/10/19.
 * Action对象
 */
public class PolicyAction {
    private String id;
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
