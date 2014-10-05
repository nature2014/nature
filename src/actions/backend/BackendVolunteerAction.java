/**
 * 
 */
package actions.backend;

import actions.upload.UploadMultipleImageAction;
import bl.beans.ImageInfoBean;
import bl.beans.SourceCodeBean;
import bl.beans.SystemSettingBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.SourceCodeBusiness;
import bl.mongobus.SystemSettingBusiness;
import com.opensymphony.xwork2.ActionContext;
import common.Constants;
import net.sf.json.JSONArray;
import util.ServerContext;
import util.StringUtil;
import vo.table.TableDataVo;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;
import vo.table.TableQueryVo;
import webapps.WebappsConstants;
import bl.beans.VolunteerBean;
import bl.common.BusinessResult;
import bl.mongobus.SequenceUidGenerator;
import bl.mongobus.VolunteerBusiness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author gudong
 * @since $Date:2014-02-10$
 */
public class BackendVolunteerAction extends BaseBackendAction<VolunteerBusiness> {
  private static Logger log = LoggerFactory.getLogger(BackendVolunteerAction.class);
  protected static SourceCodeBusiness SOURBUS = (SourceCodeBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_SOURCECODE);
  private static SystemSettingBusiness ssb = (SystemSettingBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_SYSTEMSETTING);


  protected List<SourceCodeBean> listSource;
  private VolunteerBean volunteer;
  private String jsonInitImage;
  private List<ImageInfoBean> image;

    public List<ImageInfoBean> getImage() {
        return image;
    }

    public void setImage(List<ImageInfoBean> image) {
        this.image = image;
    }

    public String getJsonInitImage() {
        return jsonInitImage;
    }

    public void setJsonInitImage(String jsonInitImage) {
        this.jsonInitImage = jsonInitImage;
    }

    public List<SourceCodeBean> getListSource() {
    return listSource;
  }

  public void setListSource(List<SourceCodeBean> listSource) {
    this.listSource = listSource;
  }

  public VolunteerBean getVolunteer() {
    return volunteer;
  }

  public void setVolunteer(VolunteerBean volunteer) {
    this.volunteer = volunteer;
  }

  @Override
  public VolunteerBusiness getBusiness() {
    if (business == null) {
      business = new VolunteerBusiness();
    }
    return (VolunteerBusiness) business;
  }

  /**
   * 
   */
  private static final long serialVersionUID = -5222876000116738224L;

  @Override
  public String getActionPrex() {
    return getRequest().getContextPath() + "/backend/volunteer";
  }

  @Override
  public String getCustomJs() {
    return getRequest().getContextPath() + "/js/volunteer.js";
  }

  public String getCustomJsp() {
    return "/pages/volunteer/volunteer.jsp";
  };

  @Override
  public String getTableTitle() {
    return "<ul class=\"breadcrumb\"><li>员工管理</li><li class=\"active\">员工</li></ul>";
  }

  @Override
  public TableQueryVo getModel() {
    TableQueryVo model = super.getModel();
    model.getSort().remove("name");
    model.getSort().put("code", "desc");
    return model;
  }

  @Override
  public TableInitVo getTableInit() {
    TableInitVo init = new TableInitVo();
    init.getAoColumns().add(new TableHeaderVo("name", "员工").enableSearch());
    init.getAoColumns().add(new TableHeaderVo("code", "工号").enableSearch());
    init.getAoColumns().add(new TableHeaderVo("identityCard", "证件号").enableSearch());
    init.getAoColumns().add(new TableHeaderVo("status", "状态").enableSearch().
    addSearchOptions(new String[][] { { "0", "1", "2", "3", "4"}, { "已注册", "通过审核", "通过面试" , "未通过审核", "未通过面试"} }));

     List<SourceCodeBean> sourceList = (List<SourceCodeBean>) SOURBUS.getAllLeaves().getResponseData();
      String[][] sources = new String[2][sourceList.size()];
      if (sourceList.size()>0) {
          for (int i = 0; i < sourceList.size(); i++) {
              sources[0][i] = sourceList.get(i).getCode();
              sources[1][i] = sourceList.get(i).getName();
          }
      } else {
          sources = null;
      }
    init.getAoColumns().add(new TableHeaderVo("occupation", "职称").addSearchOptions(sources).enableSearch());
    init.getAoColumns().add(new TableHeaderVo("registerFrom", "注册来源").addSearchOptions(new String[][] { { "1", "2"}, { "网站", "微信"}}));
    init.getAoColumns().add(new TableHeaderVo("sex", "性别").addSearchOptions(new String[][] { { "1", "2"}, { "男", "女"}}));

    init.getAoColumns().add(new TableHeaderVo("iconpath", "图像").setHiddenColumn(true).enableSearch().addSearchOptions(new String[][] { { "null", "!null"}, { "没有", "有"}}));
    init.getAoColumns().add(new TableHeaderVo("fingerpath", "指纹").setHiddenColumn(true).enableSearch().addSearchOptions(new String[][] { { "null", "!null"}, { "没有", "有"}}));

    init.getAoColumns().add(new TableHeaderVo("cellPhone", "手机", false));
    init.getAoColumns().add(new TableHeaderVo("wechat", "微信", false));
    init.getAoColumns().add(new TableHeaderVo("email", "邮箱", false));
    return init;
  }

  /**
   * 
   * @return
   * @throws Exception
   */
  public String index() throws Exception {
    this.listSource = (List<SourceCodeBean>) SOURBUS.getAllLeaves().getResponseData();
    return INDEX_SUCCESS;
  }

  @Override
  public String save() throws Exception {

    this.listSource = (List<SourceCodeBean>) SOURBUS.getAllLeaves().getResponseData();
    volunteer.setImage(this.image);
    BusinessResult result = getBusiness().save(getRequest(), volunteer);
    if (result.getErrors().size() > 0) {
      for (Object error : result.getErrors()) {
        addActionError(error.toString());
      }
        //初始化图片列表
        this.jsonInitImage = UploadMultipleImageAction.jsonFromImageInfo(this.image != null ? this.image : null);

        return INPUT;
    }
    if (result.getMessages().size() > 0) {
      for (Object message : result.getMessages()) {
        addActionMessage(message.toString());
      }
      return SUCCESS;
    }
    return SUCCESS;
  }

  @Override
  public String edit() throws Exception {
    this.listSource = (List<SourceCodeBean>) SOURBUS.getAllLeaves().getResponseData();
    volunteer = (VolunteerBean) getBusiness().getLeaf(getId()).getResponseData();
      //初始化图片列表
      this.jsonInitImage = UploadMultipleImageAction.jsonFromImageInfo(volunteer != null ? volunteer.getImage() : null);

      return SUCCESS;
  }

  @Override
  public String delete() throws Exception {
    if (getIds() != null) {
      for (String id : getIds()) {
        getBusiness().deleteLeaf(getRequest(), id);
      }
    }
    return SUCCESS;
  }

  /**
   * 
   * @return
   */
  public String resetPassword() {
    this.volunteer = (VolunteerBean) getBusiness().getLeaf(getId()).getResponseData();
    this.listSource = (List<SourceCodeBean>) SOURBUS.getAllLeaves().getResponseData();
    if (volunteer != null) {
      SystemSettingBean systemSetting = ssb.getLeaf();
      volunteer.setPassword(StringUtil.toMD5(systemSetting.getDefaultPassword()));
      getBusiness().updateLeaf(volunteer, volunteer);
      addActionMessage("密码重置成功！");
    } else {
      addActionError("获取用户失败！重置密码失败！");
    }
    return SUCCESS;
  }

  @Override
  public String add() {
    this.listSource = (List<SourceCodeBean>) SOURBUS.getAllLeaves().getResponseData();
    volunteer = new VolunteerBean();
    return SUCCESS;
  }

  public String search() {
    TableQueryVo param = new TableQueryVo();
    param.getFilter().put("name", volunteer.getName());
    param.setIDisplayLength(5000);
    param.setIDisplayStart(0);
    TableDataVo dataVo = getBusiness().query(param);
    JSONArray jsonArray = JSONArray.fromObject(dataVo.getAaData());
    writeJson(jsonArray);
    return null;
  }
}
