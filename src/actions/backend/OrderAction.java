package actions.backend;

import bl.beans.CustomerBean;
import bl.beans.OrderBean;
import bl.beans.VolunteerBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.CustomerBusiness;
import bl.mongobus.OrderBusiness;
import bl.mongobus.VolunteerBusiness;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;
import vo.table.TableQueryVo;

import java.util.Date;
import java.util.List;

/**
 * @author pli
 * @since $Date:2014-09-13$
 */
public class OrderAction extends BaseBackendAction<OrderBusiness> {
    private static final CustomerBusiness orderBusiness = (CustomerBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_CUSTOMER);
    private static final long serialVersionUID = -5222876000116738224L;
    private static Logger LOG = LoggerFactory.getLogger(OrderAction.class);
    protected final static VolunteerBusiness VTB = (VolunteerBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_VOLUNTEER);
    protected final static CustomerBusiness CTB = (CustomerBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_CUSTOMER);
    private List<CustomerBean> listCustomerBean;
    private List<VolunteerBean> listVolunteerBean;

    public List<CustomerBean> getListCustomerBean() {
        return listCustomerBean;
    }

    public void setListCustomerBean(List<CustomerBean> listCustomerBean) {
        this.listCustomerBean = listCustomerBean;
    }

    public List<VolunteerBean> getListVolunteerBean() {
        return listVolunteerBean;
    }

    public void setListVolunteerBean(List<VolunteerBean> listVolunteerBean) {
        this.listVolunteerBean = listVolunteerBean;
    }

    private OrderBean order;

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    @Override
    public String getActionPrex() {
        return getRequest().getContextPath() + "/backend/order";
    }

    @Override
    public String getCustomJsp() {
        return "/pages/menu_order/makeOrder.jsp";
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

    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("createTime", "接单日期").disableSearch());
        init.getAoColumns().add(new TableHeaderVo("payDate", "结算日期").disableSearch());
        init.getAoColumns().add(new TableHeaderVo("createTime_gteq", "接单日期&gt;=").setHiddenColumn(true).enableSearch());
        init.getAoColumns().add(new TableHeaderVo("createTime_lteq", "接单日期&lt;=").setHiddenColumn(true).enableSearch());
        init.getAoColumns().add(new TableHeaderVo("customerCompany", "公司名称").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("payDate_gteq", "结算日期&gt;=").setHiddenColumn(true).enableSearch());
        init.getAoColumns().add(new TableHeaderVo("payDate_lteq", "结算日期&lt;=").setHiddenColumn(true).enableSearch());
        listCustomerBean = (List<CustomerBean>) CTB.getAllLeaves().getResponseData();
        String[][] listCustomerCodes = new String[2][listCustomerBean.size()];
        if (listCustomerBean.size() > 0) {
            for (int i = 0; i < listCustomerBean.size(); i++) {
                listCustomerCodes[0][i] = listCustomerBean.get(i).getId();
                listCustomerCodes[1][i] = listCustomerBean.get(i).getName();
            }
        } else {
            listCustomerCodes = null;
        }
        init.getAoColumns().add(new TableHeaderVo("customerId", "联系人").addSearchOptions(listCustomerCodes).enableSearch());
        init.getAoColumns().add(new TableHeaderVo("customerCellPhone", "手机号码").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("name", "业务名称").enableSearch());
        //init.getAoColumns().add(new TableHeaderVo("offerPrice", "测量报价(元)").disableSearch());
        //init.getAoColumns().add(new TableHeaderVo("price", "订单价格(元)").disableSearch());
        //init.getAoColumns().add(new TableHeaderVo("prePayment", "预付款(元)").disableSearch());
        //init.getAoColumns().add(new TableHeaderVo("actualIncome", "实际收入(元)").disableSearch());
        //init.getAoColumns().add(new TableHeaderVo("unPayment", "欠款(元)").disableSearch());
        //init.getAoColumns().add(new TableHeaderVo("closePayment", "付清余款(元)").disableSearch());
        init.getAoColumns().add(new TableHeaderVo("state", "订单状态").addSearchOptions(new String[][]{{"0", "1", "2", "3", "4", "5", "6", "7", "8"}, {"测量报价", "设计", "看稿", "修改定稿", "金额", "预付款下单", "制作", "安装", "付清余款"}}).enableSearch());
        listVolunteerBean = (List<VolunteerBean>) VTB.getPassedInterviewedVolunteers();
        String[][] listVolunteerCodes = new String[2][listVolunteerBean.size()];
        if (listVolunteerBean.size() > 0) {
            for (int i = 0; i < listVolunteerBean.size(); i++) {
                listVolunteerCodes[0][i] = listVolunteerBean.get(i).getId();
                listVolunteerCodes[1][i] = listVolunteerBean.get(i).getName();
            }
        } else {
            listVolunteerCodes = null;
        }
        init.getAoColumns().add(new TableHeaderVo("resOfficer", "负责人").addSearchOptions(listVolunteerCodes).enableSearch());

        return init;
    }

    @Override
    public String getTableTitle() {
        return "<ul class=\"breadcrumb\"><li>订单管理</li><li class=\"active\">订单记录</li></ul>";
    }

    @Override
    public String save() throws Exception {
        if (StringUtils.isBlank(order.getId())) {
            order.set_id(ObjectId.get());
            getBusiness().createLeaf(order);
        } else {
            OrderBean origCustomer = (OrderBean) getBusiness().getLeaf(order.getId().toString()).getResponseData();
            OrderBean newCustomer = (OrderBean) origCustomer.clone();
            BeanUtils.copyProperties(newCustomer, order);
            //结账日期
            if (newCustomer.getPayDate() == null && order.getState() == OrderBean.OState.Close.getValue()) {
                //自动完成结账日期
                newCustomer.setPayDate(new Date(System.currentTimeMillis()));
            }
            getBusiness().updateLeaf(origCustomer, newCustomer);
        }
        return SUCCESS;
    }

    @Override
    public String add() {
        listVolunteerBean = (List<VolunteerBean>) VTB.getPassedInterviewedVolunteers();
        listCustomerBean = (List<CustomerBean>) CTB.getAllLeaves().getResponseData();
        order = new OrderBean();
        return SUCCESS;
    }

    @Override
    public String edit() throws Exception {
        order = (OrderBean) getBusiness().getLeaf(getId()).getResponseData();
        listVolunteerBean = (List<VolunteerBean>) VTB.getPassedInterviewedVolunteers();
        listCustomerBean = (List<CustomerBean>) CTB.getAllLeaves().getResponseData();
        return SUCCESS;
    }

    @Override
    public String delete() throws Exception {
        if (getId() != null) {
            getBusiness().deleteLeaf(getId());
        }
        return SUCCESS;
    }


}
