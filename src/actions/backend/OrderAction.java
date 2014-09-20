package actions.backend;

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

    private List<VolunteerBean> listVolunteerBean;

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
        init.getAoColumns().add(new TableHeaderVo("createTime", "接单日期").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("company", "公司名称").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("name", "客户姓名").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("cellPhone", "手机号码").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("customerCellPhone", "手机号码").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("customerBean.qq", "QQ号码", false));
        init.getAoColumns().add(new TableHeaderVo("customerBean.email", "邮箱", false));
        init.getAoColumns().add(new TableHeaderVo("customerBean.wechat", "微信", false));
        init.getAoColumns().add(new TableHeaderVo("customerBean.address", "地址", false));

        init.getAoColumns().add(new TableHeaderVo("state", "订单状态").addSearchOptions(new String[][]{{"0", "1", "2", "3", "4", "5", "6", "7", "8"}, {"测量报价", "设计", "看稿", "修改定稿", "定价金额", "预付款下单", "制作", "安装", "付清余款"}}).enableSearch());
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
            getBusiness().updateLeaf(origCustomer, newCustomer);
        }
        return SUCCESS;
    }

    @Override
    public String edit() throws Exception {
        order = (OrderBean) getBusiness().getLeaf(getId()).getResponseData();
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
