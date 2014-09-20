package actions.backend;

import actions.upload.UploadMultipleImageAction;
import bl.beans.CustomerBean;
import bl.beans.ImageInfoBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.CustomerBusiness;
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
public class CustomerAction extends BaseBackendAction<CustomerBusiness> {
    private static final CustomerBusiness customerBusiness = (CustomerBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_CUSTOMER);
    private static final long serialVersionUID = -5222876000116738224L;
    private static Logger LOG = LoggerFactory.getLogger(CustomerAction.class);
    private String jsonInitImage;
    private List<ImageInfoBean> image;

    private CustomerBean customer;

    private List<CustomerBean> customerBeanList;

    public List<CustomerBean> getCustomerBeanList() {
        return customerBeanList;
    }

    public void setCustomerBeanList(List<CustomerBean> customerBeanList) {
        this.customerBeanList = customerBeanList;
    }

    public String getJsonInitImage() {
        return jsonInitImage;
    }

    public void setJsonInitImage(String jsonInitImage) {
        this.jsonInitImage = jsonInitImage;
    }

    public List<ImageInfoBean> getImage() {
        return image;
    }

    public void setImage(List<ImageInfoBean> image) {
        this.image = image;
    }

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
    public String getCustomJsp() {
        return "/pages/customer/makeImage.jsp";
    }

    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("logo", "公司Logo").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("company", "公司名称").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("name", "客户姓名").enableSearch());
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
        //暂时这样配置，否则需要配置struts映射规则支持二级字段映射
        customer.setImage(image);
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
            //空值的时候,参考NullAwareBeanUtilsBean.java
            newCustomer.setImage(customer.getImage());
            getBusiness().updateLeaf(origCustomer, newCustomer);
        }
        return SUCCESS;
    }

    @Override
    public String edit() throws Exception {
        customer = (CustomerBean) getBusiness().getLeaf(getId()).getResponseData();
        //初始化图片列表
        this.jsonInitImage = UploadMultipleImageAction.jsonFromImageInfo(customer != null ? customer.getImage() : null);

        return SUCCESS;
    }

    @Override
    public String delete() throws Exception {
        if (getId() != null) {
            getBusiness().deleteLeaf(getId());
        }
        return SUCCESS;
    }

    /**
     * 客户的位置视图，基于百度地图
     *
     * @return
     */
    public String customerPosition() {
        customerBeanList = (List<CustomerBean>) getBusiness().getAllLeaves().getResponseData();
        return SUCCESS;
    }

    /**
     * 短息配置控制中心
     *
     * @return
     */
    public String smsSend() {
        super.addActionError("请检查短信网关，并且核对账户余额是否充足！");
        return SUCCESS;
    }

    /**
     * 短息配置控制中心
     *
     * @return
     */
    public String emailSend() {
        ArrayList<String> emails = new ArrayList<>();
        if (this.customerBeanList != null) {
            for (CustomerBean cb : this.customerBeanList) {
                if (cb != null && cb.getEmail() != null) {
                    emails.add(cb.getEmail());
                }
            }
            if (emails.size() > 0) {
                try {
                    ClientQQMail.sendEmail(emails.toArray(new String[emails.size()]), mailTitle, content);
                    super.addActionMessage("群发邮件发送成功！");
                } catch (Exception e) {
                    LOG.error("发送群发邮件错误 {}", e.getMessage());
                    super.addActionMessage("发送群发邮件错误！");
                }
                return SUCCESS;
            }
        }
        super.addActionError("请选择正确的客户邮箱来发送邮件！");
        return SUCCESS;
    }

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String mailTitle;

    public String getMailTitle() {
        return mailTitle;
    }

    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }
}
