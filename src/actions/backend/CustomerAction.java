package actions.backend;

import bl.beans.CustomerBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.CustomerBusiness;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;

/**
 * @author pli
 * @since $Date:2014-09-13$
 */
public class CustomerAction extends BaseBackendAction<CustomerBusiness> {
    private static final CustomerBusiness customerBusiness = (CustomerBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_CUSTOMER);
    private static final long serialVersionUID = -5222876000116738224L;
    private static Logger LOG = LoggerFactory.getLogger(CustomerAction.class);

    private CustomerBean customer;

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

    @Override
    public String getActionPrex() {
        return getRequest().getContextPath() + "/backend/customer";
    }

    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("name", "客户名称").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("cellPhone", "手机号码").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("fixedPhone", "固定电话").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("qq", "QQ帐号").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("email", "邮箱地址").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("wechat", "微信").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("address", "客户地址").enableSearch());

        return init;
    }

    @Override
    public String getTableTitle() {
        return "<ul class=\"breadcrumb\"><li>客户管理</li><li class=\"active\">客户信息</li></ul>";
    }

    @Override
    public String save() throws Exception {
        if (StringUtils.isBlank(customer.getId())) {
            CustomerBean userTmp = (CustomerBean) getBusiness().getLeafByName(customer.getName()).getResponseData();
            if (userTmp != null) {
                addActionError("客户已经存在");
                return FAILURE;
            } else {
                customer.set_id(ObjectId.get());
                getBusiness().createLeaf(customer);
            }
        } else {
            CustomerBean origCustomer = (CustomerBean) getBusiness().getLeaf(customer.getId().toString()).getResponseData();
            CustomerBean newCustomer = (CustomerBean) origCustomer.clone();
            BeanUtils.copyProperties(newCustomer, customer);
            getBusiness().updateLeaf(origCustomer, newCustomer);
        }
        return SUCCESS;
    }

    @Override
    public String edit() throws Exception {
        customer = (CustomerBean) getBusiness().getLeaf(getId()).getResponseData();
        return SUCCESS;
    }


}
