package bl.mongobus;

import bl.beans.CustomerBean;
import bl.beans.OrderBean;
import bl.common.BeanContext;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportOrderBusiness extends MongoCommonBusiness<BeanContext, OrderBean> {
    private static Logger LOG = LoggerFactory.getLogger(CustomerBean.class);
    protected final static OrderBusiness orderBusiness = (OrderBusiness) SingleBusinessPoolManager.getBusObj
            (BusTieConstant.BUS_CPATH_ORDER);

    private static String ECHARTS_PIE_TEMPLATE = "{title:{text:'用户付费情况',x:'center'},tooltip:{trigger:'item',formatter:\"{a} <br/>{b} : {c}元 ({d}%)\"},legend:{orient:'vertical',x:'left',data:['未付款','已付款']},calculable:true,series:[{name:'付款状态',type:'pie',radius:'55%',center:['50%','60%'],data:[{value:425,name:'未付款'},{value:1024,name:'已付款'}]}]}";

    public ReportOrderBusiness() {
        this.clazz = OrderBean.class;
    }

    public JSONObject getReportOrderData(OrderBean orderBean) {
        Map filter = new HashMap();
        if (orderBean != null) {
            if (StringUtils.isNotEmpty(orderBean.getCustomerId())) {
                filter.put("customerId_=", orderBean.getCustomerId());
            }
        }
        List<OrderBean> orderBeanList = orderBusiness.queryDataByCondition(filter, null);
        JSONObject rootObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<Map> reportDataMap = new ArrayList<Map>();
        {
            Map paymentStatus = culPaymentStatus(orderBeanList);
            JSONObject tmpObject = new JSONObject();
            JSONObject echartObject = translate2PieCharts(paymentStatus);
            tmpObject.put("data", echartObject);
            jsonArray.add(tmpObject);
        }
        rootObject.put("list", jsonArray);
        try{
            rootObject.put("dataList", JSONArray.fromObject(orderBeanList));
        }catch(Exception e){
            e.printStackTrace();
        }
        return rootObject;
    }

    private JSONObject translate2PieCharts(Map paymentStatus) {
        JSONObject echartObject = JSONObject.fromObject(ECHARTS_PIE_TEMPLATE);
        echartObject.getJSONArray("series").getJSONObject(0).getJSONArray("data").getJSONObject(0).put("value",
                paymentStatus.get("unpay"));
        echartObject.getJSONArray("series").getJSONObject(0).getJSONArray("data").getJSONObject(1).put("value",
                paymentStatus.get("pay"));
        return echartObject;
    }

    private JSONObject translateToEchartLine(JSONObject jsonObject) {
        JSONObject rootObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < jsonObject.getJSONArray("dataList").size(); i++) {
            JSONObject dataObject = jsonObject.getJSONArray("dataList").getJSONObject(i);
            if (dataObject.getJSONArray("data").size() > 0) {
                JSONObject echartObject = JSONObject.fromObject(ECHARTS_PIE_TEMPLATE);
                echartObject.getJSONObject("title").put("text", dataObject.getString("title"));
                echartObject.getJSONObject("legend").getJSONArray("data").add(dataObject.getString("title"));
                echartObject.getJSONArray("yAxis").getJSONObject(0).getJSONObject("axisLabel").put("formatter", "{value}" + dataObject.getString("pseudoReferenceUnitCode"));
                echartObject.getJSONArray("series").getJSONObject(0).put("name", dataObject.getString("title"));
                for (int j = 0; j < dataObject.getJSONArray("data").size(); j++) {
                    JSONObject originDataObject = dataObject.getJSONArray("data").getJSONObject(j);
                    //此处可增加随访字样
                    echartObject.getJSONArray("xAxis").getJSONObject(0).getJSONArray("data").add(originDataObject.getString("checkTime"));
                    echartObject.getJSONArray("series").getJSONObject(0).getJSONArray("data").add(originDataObject.getString("value"));
                }
                JSONObject tmpObject = new JSONObject();
                tmpObject.put("data", echartObject);
                jsonArray.add(tmpObject);
            }
        }
        rootObject.put("list", jsonArray);
        return rootObject;
    }

    private Map culPaymentStatus(List<OrderBean> orderBeanList) {
        Map map = new HashMap();
        float unpay = 0.00f;
        float pay = 0.00f;
        for (OrderBean orderBean : orderBeanList) {
            pay += orderBean.getActualIncome();
            unpay += orderBean.getUnPayment();
        }
        map.put("unpay", unpay);
        map.put("pay", pay);
        return map;
    }
}
