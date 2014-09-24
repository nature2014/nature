package bl.mongobus;

import actions.QueryTableAction;
import bl.beans.CustomerBean;
import bl.beans.OrderBean;
import bl.common.BeanContext;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vo.table.TableQueryVo;

import java.util.*;

public class ReportOrderBusiness extends MongoCommonBusiness<BeanContext, OrderBean> {
    private static Logger LOG = LoggerFactory.getLogger(CustomerBean.class);
    protected final static OrderBusiness orderBusiness = (OrderBusiness) SingleBusinessPoolManager.getBusObj
            (BusTieConstant.BUS_CPATH_ORDER);

    private static String ECHARTS_PIE_TEMPLATE = "{title:{text:'用户付费情况',x:'center'},tooltip:{trigger:'item',formatter:\"{a} <br/>{b} : {c}元 ({d}%)\"},legend:{orient:'vertical',x:'left',data:['未付款','已付余款']},calculable:true,series:[{name:'付款状态',type:'pie',radius:'55%',center:['50%','60%'],data:[{value:425,name:'未付款'},{value:1024,name:'已付余款'}]}]}";
    private static String ECHARTS_INPAY_TOP5_TEMPLATE = "{\"title\":{\"text\":\"前5名客户收入\"},\"tooltip\":{\"trigger\":\"axis\"},\"legend\":{\"data\":[\"订单价格\",\"实际收入\"]},\"calculable\":true,\"xAxis\":[{\"type\":\"value\",\"boundaryGap\":[0,0.01]}],\"yAxis\":[{\"type\":\"category\",\"data\":[]}],\"series\":[{\"name\":\"订单价格\",\"type\":\"bar\",\"data\":[]},{\"name\":\"实际收入\",\"type\":\"bar\",\"data\":[]}]}";

    public ReportOrderBusiness() {
        this.clazz = OrderBean.class;
    }

    public JSONObject getReportOrderData(TableQueryVo model) {
        Map map = new HashMap<>();
        try {
            for (Object key : model.getFilter().keySet()) {
                Object value = model.getFilter().get(key);
                if (value instanceof String[]) {
                    if (value != null && StringUtils.isNotEmpty(((String[]) value)[0])) {
                        map.put(key, value);
                    }
                } else {
                    if (value != null) {
                        map.put(key, value);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<OrderBean> orderBeanList = orderBusiness.queryDataByCondition(map, null);
        JSONObject rootObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<Map> reportDataMap = new ArrayList<Map>();
        {
            JSONObject tmpObject = new JSONObject();
            JSONObject echartObject = culPaymentStatus(orderBeanList);
            if (echartObject != null) {
                tmpObject.put("data", echartObject);
                jsonArray.add(tmpObject);
            }
        }
        {
            JSONObject tmpObject = new JSONObject();
            JSONObject echartObject = culInpayTop5(orderBeanList);
            if (echartObject != null) {
                tmpObject.put("data", echartObject);
                jsonArray.add(tmpObject);
            }
        }
        rootObject.put("list", jsonArray);
        try {
            rootObject.put("dataList", JSONArray.fromObject(orderBeanList, QueryTableAction.config));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootObject;
    }

    private JSONObject culInpayTop5(List<OrderBean> orderBeanList) {
        if (CollectionUtils.isEmpty(orderBeanList)) {
            return null;
        }
        float[] actualIncomes = new float[5];
        float[] prices = new float[5];
        Map<String, Float> priceFlaotMap = new HashMap<>();
        Map<String, Float> actualIncomeFlaotMap = new HashMap<>();
        Map<String, String> idNameMap = new HashMap<>();
        for (OrderBean orderBean : orderBeanList) {
            if (priceFlaotMap.get(orderBean.getCustomerId()) != null) {
                priceFlaotMap.put(orderBean.getCustomerId(), orderBean.getPrice() + priceFlaotMap.get(orderBean.getCustomerId()));
            } else {
                priceFlaotMap.put(orderBean.getCustomerId(), orderBean.getPrice());
            }
            if (actualIncomeFlaotMap.get(orderBean.getCustomerId()) != null) {
                actualIncomeFlaotMap.put(orderBean.getCustomerId(), orderBean.getActualIncome() + actualIncomeFlaotMap.get(orderBean
                        .getCustomerId()));
            } else {
                actualIncomeFlaotMap.put(orderBean.getCustomerId(), orderBean.getActualIncome());
            }
            if (idNameMap.get(orderBean.getCustomerId()) == null) {
                idNameMap.put(orderBean.getCustomerId(), orderBean.getCustomerBean().getName());
            }
        }

        //计算Top5
        List<Map.Entry<String, Float>> actualIncomeFlaotList = sortFloat(actualIncomeFlaotMap);
        JSONObject echartObject = JSONObject.fromObject(ECHARTS_INPAY_TOP5_TEMPLATE);
        for (int i = 0; i < 5 && i < actualIncomeFlaotList.size(); i++) {
            Map.Entry<String, Float> entry = actualIncomeFlaotList.get(i);
            try {
                String name = idNameMap.get(entry.getKey());
                //界面上只能展示6个字符长度
                if (name.length() > 6) {
                    name = name.substring(0, 6);
                }
                echartObject.getJSONArray("yAxis").getJSONObject(0).getJSONArray("data").add(name);
                echartObject.getJSONArray("series").getJSONObject(0).getJSONArray("data").add(priceFlaotMap.get(entry.getKey()));
                echartObject.getJSONArray("series").getJSONObject(1).getJSONArray("data").add(entry.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return echartObject;
    }

    public static List<Map.Entry<String, Float>> sortFloat(Map<String, Float> actualIncomeFlaotMap) {
        List<Map.Entry<String, Float>> actualIncomeFlaotList = new ArrayList<>(actualIncomeFlaotMap.entrySet());
        Collections.sort(actualIncomeFlaotList, new Comparator<Map.Entry<String, Float>>() {
            public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
                if (o2.getValue() - o1.getValue() > 0) {
                    return 1;
                } else if (o2.getValue() - o1.getValue() < 0) {
                    return -1;
                }
                return 0;
            }
        });
        return actualIncomeFlaotList;
    }

    private JSONObject culPaymentStatus(List<OrderBean> orderBeanList) {
        if (CollectionUtils.isEmpty(orderBeanList)) {
            return null;
        }
        Map paymentStatus = new HashMap();
        float unpay = 0.00f;
        float pay = 0.00f;
        for (OrderBean orderBean : orderBeanList) {
            pay += orderBean.getActualIncome();
            unpay += orderBean.getUnPayment();
        }
        paymentStatus.put("unpay", unpay);
        paymentStatus.put("pay", pay);

        JSONObject echartObject = JSONObject.fromObject(ECHARTS_PIE_TEMPLATE);
        echartObject.getJSONArray("series").getJSONObject(0).getJSONArray("data").getJSONObject(0).put("value",
                paymentStatus.get("unpay"));
        echartObject.getJSONArray("series").getJSONObject(0).getJSONArray("data").getJSONObject(1).put("value",
                paymentStatus.get("pay"));
        return echartObject;
    }

    public static void main(String[] args) {
        Map<String, Float> actualIncomeFlaotMap = new HashMap<>();
        actualIncomeFlaotMap.put("a", 123f);
        actualIncomeFlaotMap.put("b", 456f);
        actualIncomeFlaotMap.put("c", 234f);
        List<Map.Entry<String, Float>> actualIncomeFlaotList = sortFloat(actualIncomeFlaotMap);
        System.out.println(actualIncomeFlaotList);
    }
}
