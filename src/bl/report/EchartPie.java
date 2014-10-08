package bl.report;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import util.StringUtil;

import java.util.Map;

/**
 * Created by fanxin.wfx on 14-10-7.
 */
public class EchartPie implements EchartIntf{


    private JSONObject pieJsonTemplate = JSONObject.fromObject(ReportConstants.ECHARTS_PIE_TEMPLATE);

    public EchartPie(Map<String, String> configMap, Map<String, Object> valueMap){
        initConfig(configMap);
        transData(valueMap);
    }

    private void transData(Map<String, Object> valueMap) {
        JSONArray dataJsonArray = pieJsonTemplate.getJSONArray("series").getJSONObject(0).getJSONArray("data");
        for(int i = 0; i < dataJsonArray.size(); i++){
            JSONObject jsonObject = dataJsonArray.getJSONObject(i);
            String name = jsonObject.getString("name");
            if(StringUtils.isEmpty(name) || valueMap.get(name) == null){
                throw new RuntimeException("饼图的子项" + name + "缺少数据");
            }
            jsonObject.put("value", valueMap.get(name));
        }
    }

    @Override
    public JSONObject toEchartJsonObject() {
        return pieJsonTemplate;
    }

    public void initConfig(Map<String, String> configMap) {
        if(MapUtils.isNotEmpty(configMap)){
            if(StringUtils.isNotEmpty(configMap.get("title"))){
                pieJsonTemplate.getJSONObject("title").put("text", configMap.get("title"));
            }else{
                throw new RuntimeException("需要设置饼图的名称");
            }
            if(StringUtils.isNotEmpty(configMap.get("dataEnums"))){
                String[] dataEnums = configMap.get("dataEnums").split(",");
                JSONArray dataEnumJsonArray = new JSONArray();
                JSONArray dataEnumValueJsonArray = new JSONArray();
                for(String dataEnum : dataEnums){
                    dataEnumJsonArray.add(dataEnum);
                    JSONObject dataNameJsonObject = new JSONObject();
                    dataNameJsonObject.put("name", dataEnum);
                    dataEnumValueJsonArray.add(dataNameJsonObject);
                }
                pieJsonTemplate.getJSONObject("legend").put("data", dataEnumJsonArray);
                pieJsonTemplate.getJSONArray("series").getJSONObject(0).put("data", dataEnumValueJsonArray);
            }else{
                throw new RuntimeException("需要设置饼图的具体数据名称，以英文','符号隔开");
            }
            if(StringUtils.isNotEmpty(configMap.get("dataName"))){
                pieJsonTemplate.getJSONArray("series").getJSONObject(0).put("name", configMap.get("dataName"));
            }else{
                throw new RuntimeException("需要设置饼图的整体数据名称");
            }
        }
    }
}
