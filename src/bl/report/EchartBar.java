package bl.report;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fanxin.wfx on 14-10-7.
 */
public class EchartBar implements EchartIntf {
    private static Logger LOG = LoggerFactory.getLogger(EchartBar.class);

    private JSONObject barJsonTemplate = JSONObject.fromObject(ReportConstants.ECHARTS_BAR_TEMPLATE);

    private boolean isInit = false;

    private void transData(Map<String, Object> valueMap) {
        if (MapUtils.isEmpty(valueMap)) {
            barJsonTemplate = null;
            return;
        }
        Map<String, Map<String, Float>> dataValueMap = (Map<String, Map<String, Float>>) valueMap.get("dataValueMap");
        Map<String, String> dataNameMap = (Map<String, String>) valueMap.get("dataNameMap");
        for (Map.Entry<String, String> entry : dataNameMap.entrySet()) {
            String dataId = entry.getKey();
            String dataName = entry.getValue();
            barJsonTemplate.getJSONArray("xAxis").getJSONObject(0).getJSONArray("data").add(dataName);
            Map<String, Float> tvalueMap = dataValueMap.get(dataId);
            for (Map.Entry<String, Float> valueEntry : tvalueMap.entrySet()) {
                JSONArray seriesArray = barJsonTemplate.getJSONArray("series");
                for (int i = 0; i < seriesArray.size(); i++) {
                    JSONObject seriesObject = seriesArray.getJSONObject(i);
                    if (seriesObject.getString("name").equals(valueEntry.getKey())) {
                        seriesObject.getJSONArray("data").add(valueEntry.getValue());
                    }
                }
            }
        }
    }

    @Override
    public boolean initData(Map<String, String> configMap, Map<String, Object> valueMap) {
        try {
            initConfig(configMap);
            transData(valueMap);
            isInit = true;
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("echarPie init error:", e);
        }
        return isInit;
    }

    @Override
    public JSONObject toEchartJsonObject() {
        if (isInit) {
            return barJsonTemplate;
        } else {
            return null;
        }
    }

    public void initConfig(Map<String, String> configMap) {
        if (MapUtils.isNotEmpty(configMap)) {
            if (StringUtils.isNotEmpty(configMap.get("title"))) {
                barJsonTemplate.getJSONObject("title").put("text", configMap.get("title"));
            } else {
                throw new RuntimeException("需要设置柱图的名称");
            }
            if (StringUtils.isNotEmpty(configMap.get("dataBarEnums"))) {
                String[] dataEnums = configMap.get("dataBarEnums").split(",");
                JSONArray dataEnumJsonArray = new JSONArray();
                JSONArray dataEnumValueJsonArray = new JSONArray();
                for (String dataEnum : dataEnums) {
                    dataEnumJsonArray.add(dataEnum);
                    JSONObject templateDataObject = barJsonTemplate.getJSONArray("series").getJSONObject(0);
                    templateDataObject.put("name", dataEnum);
                    dataEnumValueJsonArray.add(templateDataObject);
                }
                barJsonTemplate.getJSONObject("legend").put("data", dataEnumJsonArray);
                barJsonTemplate.put("series", dataEnumValueJsonArray);
            } else {
                throw new RuntimeException("需要设置柱图的具体数据名称，以英文','符号隔开");
            }
        }
    }
}
