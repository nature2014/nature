package actions.backend;

import actions.BaseAction;
import actions.BaseTableAction;
import actions.QueryTableAction;
import actions.upload.UploadMultipleImageAction;
import bl.beans.CustomerBean;
import bl.beans.ImageInfoBean;
import bl.beans.OrderBean;
import bl.beans.VolunteerBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.CustomerBusiness;
import bl.mongobus.ReportOrderBusiness;
import bl.mongobus.VolunteerBusiness;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ClientQQMail;
import util.StringUtil;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;
import vo.table.TableQueryVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pli
 * @since $Date:2014-09-13$
 */
public class ReportOrderAction extends QueryTableAction<ReportOrderBusiness> {
    private static Logger LOG = LoggerFactory.getLogger(ReportOrderAction.class);
    protected final static ReportOrderBusiness reportOrderBusiness = (ReportOrderBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_REPORTORDER);
    protected final static CustomerBusiness customerBusiness = (CustomerBusiness) SingleBusinessPoolManager
            .getBusObj(BusTieConstant.BUS_CPATH_CUSTOMER);
    protected final static VolunteerBusiness volunteerBusiness = (VolunteerBusiness) SingleBusinessPoolManager.getBusObj
            (BusTieConstant.BUS_CPATH_VOLUNTEER);

    private OrderBean orderBean;

    @Override
    public String getActionPrex() {
        return null;
    }

    public String getCustomJsp() {
        return "../report/reportOrder.jsp";
    }

    @Override
    public String getCustomPath() {
        return "/backend/orderreport/order.action";
    }

    @Override
    public TableQueryVo getModel() {
        if (model == null) {
            model = new TableQueryVo();
        }
        //默认按着订单接单时间的降序显示在TableIndex.jsp
        model.getSort().remove("userName");
        model.getSort().put("createTime", "desc");
        model.getFilter().put("isDeleted_!=", true);
        return model;
    }

    public String getTableId() {
        return this.getClass().getSimpleName() + "_table";
    }

    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        //初始化公司名称
        {
            List<CustomerBean> customerBeanList = (List<CustomerBean>) customerBusiness.getAllLeaves()
                    .getResponseData();
            String[][] searchOptions = new String[2][customerBeanList.size()];
            for (int i = 0; i < customerBeanList.size(); i++) {
                CustomerBean customerBean = customerBeanList.get(i);
                searchOptions[0][i] = customerBean.getId();
                if (StringUtils.isNotEmpty(customerBean.getName())) {
                    searchOptions[1][i] = customerBean.getName();
                } else {
                    searchOptions[1][i] = customerBean.getCompany();
                }
            }
            init.getAoColumns().add(new TableHeaderVo("customerId", "客户名称").addSearchOptions(searchOptions)
                    .enableSearch());
        }
        //初始化业务人员
        {
            List<VolunteerBean> volunteerBeanList = (List<VolunteerBean>) volunteerBusiness.getAllLeaves().getResponseData();
            String[][] searchOptions = new String[2][volunteerBeanList.size()];
            for (int i = 0; i < volunteerBeanList.size(); i++) {
                VolunteerBean volunteerBean = volunteerBeanList.get(i);
                searchOptions[0][i] = volunteerBean.getId();
                searchOptions[1][i] = volunteerBean.getName();
            }
            init.getAoColumns().add(new TableHeaderVo("resOfficer", "负责人").addSearchOptions(searchOptions)
                    .enableSearch());
        }
        //联系号码
        init.getAoColumns().add(new TableHeaderVo("customerCellPhone", "手机号码").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("createTime_gteq", "起始时间").setHiddenColumn(true).enableSearch()
                .setSClass("cdate"));
        init.getAoColumns().add(new TableHeaderVo("createTime_lteq", "结束时间").setHiddenColumn(true).enableSearch()
                .setSClass("cdate"));

        init.getAoColumns().add(new TableHeaderVo("offerPrice", "测量报价").setHiddenColumn(false).disableSearch());
        init.getAoColumns().add(new TableHeaderVo("price", "订单价格").setHiddenColumn(false).disableSearch());
        init.getAoColumns().add(new TableHeaderVo("prePayment", "预付款").setHiddenColumn(false).disableSearch());
        init.getAoColumns().add(new TableHeaderVo("closePayment", "已付余款").setHiddenColumn(false).disableSearch());
        init.getAoColumns().add(new TableHeaderVo("actualIncome", "实际收入").setHiddenColumn(false).disableSearch());
        init.getAoColumns().add(new TableHeaderVo("unPayment", "未付款").setHiddenColumn(false).disableSearch());
        init.getAoColumns().add(new TableHeaderVo("createTime", "接单日期").setHiddenColumn(false).disableSearch()
                .setSClass("cdate"));

        if (LOG.isDebugEnabled()) {
            LOG.debug(JSONObject.fromObject(init, config).toString());
        }
        return init;
    }

    public String reportOrderData() {
        JSONObject jsonObject = new JSONObject();
        if (getModel() != null) {
            filterEmptyValue(getModel());
            jsonObject = reportOrderBusiness.getReportOrderData(getModel());
        } else {
            jsonObject.put("data", new JSONArray());
            jsonObject.put("dataList", new JSONArray());
        }
//        //临时代码，用于前台数据展现
//        if (jsonObject == null) {
//            LOG.error("没有找到报表的数据，出默认数据");
//            jsonObject = JSONObject.fromObject("{\"list\":[{\"data\":{\"title\":{\"text\":\"血常规研究报告\",\"subtext\":\"红细胞和血红蛋白量化分析\"},\"tooltip\":{\"trigger\":\"axis\"},\"legend\":{\"data\":[\"红细胞g/L\",\"血红蛋白g/L\"]},\"toolbox\":{\"show\":true,\"feature\":{\"mark\":{\"show\":true},\"dataView\":{\"show\":true,\"readOnly\":false},\"magicType\":{\"show\":true,\"type\":[\"line\",\"bar\"]},\"restore\":{\"show\":true},\"saveAsImage\":{\"show\":true}}},\"calculable\":true,\"xAxis\":[{\"type\":\"category\",\"boundaryGap\":false,\"data\":[\"2014/01/12随访\",\"2014/03/01随访\",\"2014/05/18随访\",\"2014/05/29随访\",\"2014/06/12随访\",\"2014/08/02随访\",\"2014/01/12随访\"]}],\"yAxis\":[{\"type\":\"value\",\"axisLabel\":{\"formatter\":\"{value}g/L\"}}],\"series\":[{\"name\":\"红细胞g/L\",\"type\":\"line\",\"data\":[11,11,15,13,12,13,10],\"markPoint\":{\"data\":[{\"type\":\"max\",\"name\":\"历史最大值\"},{\"type\":\"min\",\"name\":\"历史最小值\"}]},\"markLine\":{\"data\":[{\"type\":\"average\",\"name\":\"历史平均值\"}]}},{\"name\":\"血红蛋白g/L\",\"type\":\"line\",\"data\":[2,4,3,6,4,12,10],\"markPoint\":{\"data\":[{\"type\":\"max\",\"name\":\"历史最大值\"},{\"type\":\"min\",\"name\":\"历史最小值\"}]},\"markLine\":{\"data\":[{\"type\":\"average\",\"name\":\"历史平均值\"}]}}]}},{\"data\":{\"title\":{\"text\":\"血常规研究报告\",\"subtext\":\"红细胞和血红蛋白量化分析\"},\"tooltip\":{\"trigger\":\"axis\"},\"legend\":{\"data\":[\"红细胞g/L\",\"血红蛋白g/L\"]},\"toolbox\":{\"show\":true,\"feature\":{\"mark\":{\"show\":true},\"dataView\":{\"show\":true,\"readOnly\":false},\"magicType\":{\"show\":true,\"type\":[\"line\",\"bar\"]},\"restore\":{\"show\":true},\"saveAsImage\":{\"show\":true}}},\"calculable\":true,\"xAxis\":[{\"type\":\"category\",\"boundaryGap\":false,\"data\":[\"2014/01/12随访\",\"2014/03/01随访\",\"2014/05/18随访\",\"2014/05/29随访\",\"2014/06/12随访\",\"2014/08/02随访\",\"2014/01/12随访\"]}],\"yAxis\":[{\"type\":\"value\",\"axisLabel\":{\"formatter\":\"{value}g/L\"}}],\"series\":[{\"name\":\"红细胞g/L\",\"type\":\"line\",\"data\":[11,11,15,13,12,13,10],\"markPoint\":{\"data\":[{\"type\":\"max\",\"name\":\"历史最大值\"},{\"type\":\"min\",\"name\":\"历史最小值\"}]},\"markLine\":{\"data\":[{\"type\":\"average\",\"name\":\"历史平均值\"}]}},{\"name\":\"血红蛋白g/L\",\"type\":\"line\",\"data\":[2,4,3,6,4,12,10],\"markPoint\":{\"data\":[{\"type\":\"max\",\"name\":\"历史最大值\"},{\"type\":\"min\",\"name\":\"历史最小值\"}]},\"markLine\":{\"data\":[{\"type\":\"average\",\"name\":\"历史平均值\"}]}}]}},{\"data\":{\"title\":{\"text\":\"血常规研究报告\",\"subtext\":\"红细胞和血红蛋白量化分析\"},\"tooltip\":{\"trigger\":\"axis\"},\"legend\":{\"data\":[\"红细胞g/L\",\"血红蛋白g/L\"]},\"toolbox\":{\"show\":true,\"feature\":{\"mark\":{\"show\":true},\"dataView\":{\"show\":true,\"readOnly\":false},\"magicType\":{\"show\":true,\"type\":[\"line\",\"bar\"]},\"restore\":{\"show\":true},\"saveAsImage\":{\"show\":true}}},\"calculable\":true,\"xAxis\":[{\"type\":\"category\",\"boundaryGap\":false,\"data\":[\"2014/01/12随访\",\"2014/03/01随访\",\"2014/05/18随访\",\"2014/05/29随访\",\"2014/06/12随访\",\"2014/08/02随访\",\"2014/01/12随访\"]}],\"yAxis\":[{\"type\":\"value\",\"axisLabel\":{\"formatter\":\"{value}g/L\"}}],\"series\":[{\"name\":\"红细胞g/L\",\"type\":\"line\",\"data\":[11,11,15,13,12,13,10],\"markPoint\":{\"data\":[{\"type\":\"max\",\"name\":\"历史最大值\"},{\"type\":\"min\",\"name\":\"历史最小值\"}]},\"markLine\":{\"data\":[{\"type\":\"average\",\"name\":\"历史平均值\"}]}},{\"name\":\"血红蛋白g/L\",\"type\":\"line\",\"data\":[2,4,3,6,4,12,10],\"markPoint\":{\"data\":[{\"type\":\"max\",\"name\":\"历史最大值\"},{\"type\":\"min\",\"name\":\"历史最小值\"}]},\"markLine\":{\"data\":[{\"type\":\"average\",\"name\":\"历史平均值\"}]}}]}},{\"data\":{\"title\":{\"text\":\"血常规研究报告\",\"subtext\":\"红细胞和血红蛋白量化分析\"},\"tooltip\":{\"trigger\":\"axis\"},\"legend\":{\"data\":[\"红细胞g/L\",\"血红蛋白g/L\"]},\"toolbox\":{\"show\":true,\"feature\":{\"mark\":{\"show\":true},\"dataView\":{\"show\":true,\"readOnly\":false},\"magicType\":{\"show\":true,\"type\":[\"line\",\"bar\"]},\"restore\":{\"show\":true},\"saveAsImage\":{\"show\":true}}},\"calculable\":true,\"xAxis\":[{\"type\":\"category\",\"boundaryGap\":false,\"data\":[\"2014/01/12随访\",\"2014/03/01随访\",\"2014/05/18随访\",\"2014/05/29随访\",\"2014/06/12随访\",\"2014/08/02随访\",\"2014/01/12随访\"]}],\"yAxis\":[{\"type\":\"value\",\"axisLabel\":{\"formatter\":\"{value}g/L\"}}],\"series\":[{\"name\":\"红细胞g/L\",\"type\":\"line\",\"data\":[11,11,15,13,12,13,10],\"markPoint\":{\"data\":[{\"type\":\"max\",\"name\":\"历史最大值\"},{\"type\":\"min\",\"name\":\"历史最小值\"}]},\"markLine\":{\"data\":[{\"type\":\"average\",\"name\":\"历史平均值\"}]}},{\"name\":\"血红蛋白g/L\",\"type\":\"line\",\"data\":[2,4,3,6,4,12,10],\"markPoint\":{\"data\":[{\"type\":\"max\",\"name\":\"历史最大值\"},{\"type\":\"min\",\"name\":\"历史最小值\"}]},\"markLine\":{\"data\":[{\"type\":\"average\",\"name\":\"历史平均值\"}]}}]}}]}");
//        }
        writeJson(jsonObject);
        return null;
    }

    public OrderBean getOrderBean() {
        return orderBean;
    }

    public void setOrderBean(OrderBean orderBean) {
        this.orderBean = orderBean;
    }
}
