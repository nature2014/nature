package actions.backend;

import actions.BaseAction;
import actions.BaseTableAction;
import actions.QueryTableAction;
import actions.upload.UploadMultipleImageAction;
import bl.beans.CustomerBean;
import bl.beans.ImageInfoBean;
import bl.beans.OrderBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.CustomerBusiness;
import bl.mongobus.ReportOrderBusiness;
import bl.mongobus.VolunteerBusiness;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ClientQQMail;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pli
 * @since $Date:2014-09-13$
 */
public class ReportOrderAction extends QueryTableAction<ReportOrderBusiness> {
    private static Logger LOG = LoggerFactory.getLogger(ReportOrderAction.class);
    protected final static ReportOrderBusiness reportOrderBusiness = (ReportOrderBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_REPORTORDER);

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
        return "/backend/report/reportOrderData.action";
    }

    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("company", "公司名称").enableSearch());
        return init;
    }

    public String reportOrderData() {
        JSONObject jsonObject = null;
        jsonObject = reportOrderBusiness.getReportOrderData(orderBean);
        //临时代码，用于前台数据展现
        if (jsonObject == null) {
            LOG.error("没有找到报表的数据，出默认数据");
            jsonObject = JSONObject.fromObject("{\"list\":[{\"data\":{\"title\":{\"text\":\"血常规研究报告\",\"subtext\":\"红细胞和血红蛋白量化分析\"},\"tooltip\":{\"trigger\":\"axis\"},\"legend\":{\"data\":[\"红细胞g/L\",\"血红蛋白g/L\"]},\"toolbox\":{\"show\":true,\"feature\":{\"mark\":{\"show\":true},\"dataView\":{\"show\":true,\"readOnly\":false},\"magicType\":{\"show\":true,\"type\":[\"line\",\"bar\"]},\"restore\":{\"show\":true},\"saveAsImage\":{\"show\":true}}},\"calculable\":true,\"xAxis\":[{\"type\":\"category\",\"boundaryGap\":false,\"data\":[\"2014/01/12随访\",\"2014/03/01随访\",\"2014/05/18随访\",\"2014/05/29随访\",\"2014/06/12随访\",\"2014/08/02随访\",\"2014/01/12随访\"]}],\"yAxis\":[{\"type\":\"value\",\"axisLabel\":{\"formatter\":\"{value}g/L\"}}],\"series\":[{\"name\":\"红细胞g/L\",\"type\":\"line\",\"data\":[11,11,15,13,12,13,10],\"markPoint\":{\"data\":[{\"type\":\"max\",\"name\":\"历史最大值\"},{\"type\":\"min\",\"name\":\"历史最小值\"}]},\"markLine\":{\"data\":[{\"type\":\"average\",\"name\":\"历史平均值\"}]}},{\"name\":\"血红蛋白g/L\",\"type\":\"line\",\"data\":[2,4,3,6,4,12,10],\"markPoint\":{\"data\":[{\"type\":\"max\",\"name\":\"历史最大值\"},{\"type\":\"min\",\"name\":\"历史最小值\"}]},\"markLine\":{\"data\":[{\"type\":\"average\",\"name\":\"历史平均值\"}]}}]}},{\"data\":{\"title\":{\"text\":\"血常规研究报告\",\"subtext\":\"红细胞和血红蛋白量化分析\"},\"tooltip\":{\"trigger\":\"axis\"},\"legend\":{\"data\":[\"红细胞g/L\",\"血红蛋白g/L\"]},\"toolbox\":{\"show\":true,\"feature\":{\"mark\":{\"show\":true},\"dataView\":{\"show\":true,\"readOnly\":false},\"magicType\":{\"show\":true,\"type\":[\"line\",\"bar\"]},\"restore\":{\"show\":true},\"saveAsImage\":{\"show\":true}}},\"calculable\":true,\"xAxis\":[{\"type\":\"category\",\"boundaryGap\":false,\"data\":[\"2014/01/12随访\",\"2014/03/01随访\",\"2014/05/18随访\",\"2014/05/29随访\",\"2014/06/12随访\",\"2014/08/02随访\",\"2014/01/12随访\"]}],\"yAxis\":[{\"type\":\"value\",\"axisLabel\":{\"formatter\":\"{value}g/L\"}}],\"series\":[{\"name\":\"红细胞g/L\",\"type\":\"line\",\"data\":[11,11,15,13,12,13,10],\"markPoint\":{\"data\":[{\"type\":\"max\",\"name\":\"历史最大值\"},{\"type\":\"min\",\"name\":\"历史最小值\"}]},\"markLine\":{\"data\":[{\"type\":\"average\",\"name\":\"历史平均值\"}]}},{\"name\":\"血红蛋白g/L\",\"type\":\"line\",\"data\":[2,4,3,6,4,12,10],\"markPoint\":{\"data\":[{\"type\":\"max\",\"name\":\"历史最大值\"},{\"type\":\"min\",\"name\":\"历史最小值\"}]},\"markLine\":{\"data\":[{\"type\":\"average\",\"name\":\"历史平均值\"}]}}]}},{\"data\":{\"title\":{\"text\":\"血常规研究报告\",\"subtext\":\"红细胞和血红蛋白量化分析\"},\"tooltip\":{\"trigger\":\"axis\"},\"legend\":{\"data\":[\"红细胞g/L\",\"血红蛋白g/L\"]},\"toolbox\":{\"show\":true,\"feature\":{\"mark\":{\"show\":true},\"dataView\":{\"show\":true,\"readOnly\":false},\"magicType\":{\"show\":true,\"type\":[\"line\",\"bar\"]},\"restore\":{\"show\":true},\"saveAsImage\":{\"show\":true}}},\"calculable\":true,\"xAxis\":[{\"type\":\"category\",\"boundaryGap\":false,\"data\":[\"2014/01/12随访\",\"2014/03/01随访\",\"2014/05/18随访\",\"2014/05/29随访\",\"2014/06/12随访\",\"2014/08/02随访\",\"2014/01/12随访\"]}],\"yAxis\":[{\"type\":\"value\",\"axisLabel\":{\"formatter\":\"{value}g/L\"}}],\"series\":[{\"name\":\"红细胞g/L\",\"type\":\"line\",\"data\":[11,11,15,13,12,13,10],\"markPoint\":{\"data\":[{\"type\":\"max\",\"name\":\"历史最大值\"},{\"type\":\"min\",\"name\":\"历史最小值\"}]},\"markLine\":{\"data\":[{\"type\":\"average\",\"name\":\"历史平均值\"}]}},{\"name\":\"血红蛋白g/L\",\"type\":\"line\",\"data\":[2,4,3,6,4,12,10],\"markPoint\":{\"data\":[{\"type\":\"max\",\"name\":\"历史最大值\"},{\"type\":\"min\",\"name\":\"历史最小值\"}]},\"markLine\":{\"data\":[{\"type\":\"average\",\"name\":\"历史平均值\"}]}}]}},{\"data\":{\"title\":{\"text\":\"血常规研究报告\",\"subtext\":\"红细胞和血红蛋白量化分析\"},\"tooltip\":{\"trigger\":\"axis\"},\"legend\":{\"data\":[\"红细胞g/L\",\"血红蛋白g/L\"]},\"toolbox\":{\"show\":true,\"feature\":{\"mark\":{\"show\":true},\"dataView\":{\"show\":true,\"readOnly\":false},\"magicType\":{\"show\":true,\"type\":[\"line\",\"bar\"]},\"restore\":{\"show\":true},\"saveAsImage\":{\"show\":true}}},\"calculable\":true,\"xAxis\":[{\"type\":\"category\",\"boundaryGap\":false,\"data\":[\"2014/01/12随访\",\"2014/03/01随访\",\"2014/05/18随访\",\"2014/05/29随访\",\"2014/06/12随访\",\"2014/08/02随访\",\"2014/01/12随访\"]}],\"yAxis\":[{\"type\":\"value\",\"axisLabel\":{\"formatter\":\"{value}g/L\"}}],\"series\":[{\"name\":\"红细胞g/L\",\"type\":\"line\",\"data\":[11,11,15,13,12,13,10],\"markPoint\":{\"data\":[{\"type\":\"max\",\"name\":\"历史最大值\"},{\"type\":\"min\",\"name\":\"历史最小值\"}]},\"markLine\":{\"data\":[{\"type\":\"average\",\"name\":\"历史平均值\"}]}},{\"name\":\"血红蛋白g/L\",\"type\":\"line\",\"data\":[2,4,3,6,4,12,10],\"markPoint\":{\"data\":[{\"type\":\"max\",\"name\":\"历史最大值\"},{\"type\":\"min\",\"name\":\"历史最小值\"}]},\"markLine\":{\"data\":[{\"type\":\"average\",\"name\":\"历史平均值\"}]}}]}}]}");
        }
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
