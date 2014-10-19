package policy;

import java.util.Map;

/**
 * Created by limin.llm on 2014/10/19.
 */
public interface PolicyActionIf {

    String getId();

    void setId(String id);

    /**
     * 需要的执行参数，参考Policy.xml
     *
     * @return
     */
    Map<String, Object> getParameters();

    void setParameters(Map<String, Object> parameters);

    /**
     * 执行的的接口
     *
     * @param policyContext
     * @return
     */
    public PolicyResult execute(PolicyContext policyContext);
}
