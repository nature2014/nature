package policy;

import java.util.Map;

/**
 * Created by peter on 2014/10/16.
 */
public interface PolicyRuleIf {
    Map<String, Object> getParameters();

    void setParameters(Map<String, Object> parameters);

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    AbstractPolicy getPolicy();

    AbstractPolicy setPolicy(AbstractPolicy policy);
}
