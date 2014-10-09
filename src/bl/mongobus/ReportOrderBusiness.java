package bl.mongobus;

import actions.QueryTableAction;
import bl.beans.CustomerBean;
import bl.beans.OrderBean;
import bl.common.BeanContext;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.report.EchartBar;
import bl.report.EchartIntf;
import bl.report.EchartPie;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vo.table.TableQueryVo;

import java.util.*;

public class ReportOrderBusiness extends MongoCommonBusiness<BeanContext, OrderBean> {
    private static Logger LOG = LoggerFactory.getLogger(CustomerBean.class);
    protected final static OrderBusiness orderBusiness = (OrderBusiness) SingleBusinessPoolManager.getBusObj
            (BusTieConstant.BUS_CPATH_ORDER);


    private static String ECHARTS_INPAY_TOP5_TEMPLATE = "{\"title\":{\"text\":\"前5名客户收入\"},\"tooltip\":{\"trigger\":\"axis\"},\"legend\":{\"data\":[\"订单价格\",\"实际收入\"]},\"calculable\":true,\"xAxis\":[{\"type\":\"value\",\"boundaryGap\":[0,0.01]}],\"yAxis\":[{\"type\":\"category\",\"data\":[]}],\"series\":[{\"name\":\"订单价格\",\"type\":\"bar\",\"data\":[]},{\"name\":\"实际收入\",\"type\":\"bar\",\"data\":[]}]}";

    public ReportOrderBusiness() {
        this.clazz = OrderBean.class;
    }

    public JSONObject getReportOrderData(TableQueryVo model) {
        Set<String> sortedMappingMongo = transeSortMap(model);

        List<OrderBean> orderBeanList = orderBusiness.queryDataByCondition(model.getFilter(), sortedMappingMongo);
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
        {
            Map map = new HashMap();
            Date startDate = DateUtils.truncate(new Date(), Calendar.DATE);
            if(LOG.isDebugEnabled()){
                LOG.debug("startDate:", startDate);
            }
            map.put("createTime_gteq", startDate);
            List<OrderBean> todayOrderBeanList = orderBusiness.queryDataByCondition(map, null);
            JSONObject tmpObject = new JSONObject();
            JSONObject echartObject = culTodayPayment(todayOrderBeanList);
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

    private Set<String> transeSortMap(TableQueryVo model) {
        Set<String> sortedMappingMongo = new HashSet<String>();
        LinkedHashMap<String, String> lhm = model.getSort();
        if (lhm != null) {
            Iterator<String> it = lhm.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = lhm.get(key);
                if (value != null && !value.isEmpty()) {
                    if (value.equals("asc")) {
                        sortedMappingMongo.add(key);
                    } else {
                        sortedMappingMongo.add("-" + key);
                    }
                }
            }
        }
        return sortedMappingMongo;
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
                idNameMap.put(orderBean.getCustomerId(), orderBean.getCustomerName());
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
        float unpay = 0.00f;
        float pay = 0.00f;
        for (OrderBean orderBean : orderBeanList) {
            pay += orderBean.getActualIncome();
            unpay += orderBean.getUnPayment();
        }

        Map<String, String> configMap = new HashMap<>();
        configMap.put("title", "用户付费情况");
        configMap.put("dataEnums", "未付款,已付余款");
        configMap.put("dataName", "付款状态");

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("未付款", unpay);
        valueMap.put("已付余款", pay);
        EchartIntf echartPie = new EchartPie();
        boolean result = echartPie.initData(configMap, valueMap);
        return echartPie.toEchartJsonObject();
    }

    private JSONObject culTodayPayment(List<OrderBean> orderBeanList) {
        if (CollectionUtils.isEmpty(orderBeanList)) {
            return null;
        }
        String PAY_KEY = "已付余款";
        String UNPAY_KEY = "未付款";
        StringBuilder customerNames = new StringBuilder();
        Map<String, Map<String, Float>> customerPaymentMap = new HashMap<>();
        Map<String, String> customerNameMap = new HashMap<>();
        for (OrderBean orderBean : orderBeanList) {
            String customerId = orderBean.getCustomerId();
            String customerName = orderBean.getCustomerName() == null ? orderBean.getCustomerCompany() : orderBean.getCustomerName();
            Map<String, Float> paymentMap = customerPaymentMap.get(customerId);
            if(paymentMap == null){
                paymentMap = new HashMap<>();
                customerNameMap.put(customerId, customerName);
                customerPaymentMap.put(customerId, paymentMap);
                if(customerNames.length() == 0){
                    customerNames.append(customerName);
                }else{
                    customerNames.append(",").append(customerName);
                }
            }
            float pay = paymentMap.get(PAY_KEY) == null ? 0 : paymentMap.get("已付余款");
            float unpay = paymentMap.get(UNPAY_KEY) == null ? 0 : paymentMap.get("已付余款");
            paymentMap.put(PAY_KEY, pay + orderBean.getActualIncome());
            paymentMap.put(UNPAY_KEY, unpay + orderBean.getUnPayment());
        }

        Map<String, String> configMap = new HashMap<>();
        configMap.put("title", "当日客户付费情况");
        configMap.put("dataBarEnums", PAY_KEY + "," + UNPAY_KEY);
        configMap.put("dataEnums", customerNames.toString());

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("dataNameMap", customerNameMap);
        valueMap.put("dataValueMap", customerPaymentMap);
        EchartIntf echartPie = new EchartBar();
        echartPie.initData(configMap, valueMap);
        return echartPie.toEchartJsonObject();
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
