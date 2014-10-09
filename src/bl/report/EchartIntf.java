package bl.report;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by fanxin.wfx on 14-10-7.
 */
public interface EchartIntf {
    public boolean initData(Map<String, String> configMap, Map<String, Object> valueMap);
    public JSONObject toEchartJsonObject();
}
