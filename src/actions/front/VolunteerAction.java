package actions.front;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bl.beans.SourceCodeBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.SourceCodeBusiness;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import util.DBUtils;
import util.MultiTenancyManager;
import util.ServerContext;
import util.StringUtil;
import webapps.WebappsConstants;
import bl.beans.VolunteerBean;
import bl.common.BusinessResult;
import bl.mongobus.SequenceUidGenerator;
import bl.mongobus.VolunteerBusiness;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author gudong
 * @since $Date:2014-03-22$
 */
public class VolunteerAction extends BaseFrontAction<VolunteerBusiness> {
    private static final long serialVersionUID = 2565111276150636692L;
    private static SourceCodeBusiness SOURBUS = (SourceCodeBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_SOURCECODE);
    private VolunteerBean volunteer;
  private String oldPassword;
  private String[][] volunteerCodes = null;
  private List<SourceCodeBean> listSource = null;

    public List<SourceCodeBean> getListSource() {
        return listSource;
    }

    public void setListSource(List<SourceCodeBean> listSource) {
        this.listSource = listSource;
    }

    public String[][] getVolunteerCodes() {
    return volunteerCodes;
  }

  public void setVolunteerCodes(String[][] volunteerCodes) {
    this.volunteerCodes = volunteerCodes;
  }

  public VolunteerBean getVolunteer() {
    return volunteer;
  }

  public void setVolunteer(VolunteerBean volunteer) {
    this.volunteer = volunteer;
  }

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  /**
   * login
   * 
   * @return
   */
  public String login() {
    StringBuffer url = getRequest().getRequestURL();
    int start = url.indexOf("http://");
    if( start != -1) {
      start = start + 7;
    }
    int end = url.length() - getRequest().getRequestURI().length();
    int couldBeEnd = url.lastIndexOf(":");
    if(couldBeEnd > start && end > couldBeEnd) {
      end = couldBeEnd;
    }
    String contextUrl = url.substring(start, end);
    String dbFlag = MultiTenancyManager.getDBFlagByDomainName(contextUrl);
    getRequest().getSession().setAttribute(WebappsConstants.USER_DB_FLAG, dbFlag);
    DBUtils.setDBFlag(dbFlag);
    try {
      if (volunteer != null) {

        VolunteerBean userTmp = getBusiness().getVolunteerBeanByCode(volunteer.getCode());
        if (userTmp != null) {
          if (userTmp.getStatus().intValue() == VolunteerBean.REGISTERED) {
            addActionError("你的账号还没通过审核，请耐心等待");
            return FAILURE;
          } else {
            if (StringUtil.toMD5(volunteer.getPassword()).equals(userTmp.getPassword())) {
              getSession().setAttribute(WebappsConstants.LOGIN_USER_SESSION_ID, userTmp);
              return SUCCESS;
            } else if (volunteer.getPassword().equals(userTmp.getPassword())) {
              // 这是通过指纹的方式拿到MD5密码然后登陆，类似于token
              getSession().setAttribute(WebappsConstants.LOGIN_USER_SESSION_ID, userTmp);
              return SUCCESS;
            }
          }
        }
        addActionError("密码错误");
        return FAILURE;
      } else {
        List<VolunteerBean> volunteers = (List<VolunteerBean>) getBusiness().getAllLeaves().getResponseData();
        int k = 0;
        for (VolunteerBean vt : volunteers) {
            if(vt.getFingerpath()!=null){
                k++;
            }
        }
        String[][] vols = new String[k][2];
        int i = 0;
        for (VolunteerBean vt : volunteers) {
          if(vt.getFingerpath()!=null){
              vols[i][0] = vt.getCode();
              vols[i][1] = vt.getPassword();
              i++;
          }
        }
        this.volunteerCodes = vols;
        return FAILURE;
      }
    } catch (Exception e) {
      LOG.error(e.getMessage());
      return FAILURE;
    } finally {
      DBUtils.removeDBFlag();
    }

  }

  /**
   * login
   * 
   * @return
   */
  public String logout() {
    getSession().removeAttribute(WebappsConstants.LOGIN_USER_SESSION_ID);
    HttpServletRequest req = (HttpServletRequest) ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
    HttpServletResponse resp = (HttpServletResponse) ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);
    eraseCookie(req, resp);
    return SUCCESS;
  }

  /**
   * Register
   * 
   * @return
   * @throws Exception
   */
  public String register() throws Exception {
      this.listSource = (List<SourceCodeBean>) SOURBUS.getAllLeaves().getResponseData();
      if (volunteer != null) {
      BusinessResult result = getBusiness().save(getRequest(), volunteer);
      if (result.getErrors().size() > 0) {
        for (Object error : result.getErrors()) {
          addActionError(error.toString());
        }
        return FAILURE;
      }
      return SUCCESS;
    } else {
      volunteer = new VolunteerBean();
      // volunteer.setCode(ServerContext.getValue(WebappsConstants.ID_PREFIX_KEY) + SequenceUidGenerator.getNewUid());
      return FAILURE;
    }
  }

  /**
   * 
   * @return
   * @throws Exception
   */
  public String view() throws Exception {
    volunteer = (VolunteerBean) getBusiness().getLeaf(getId()).getResponseData();
    return SUCCESS;
  }

  /**
   * 
   * @return
   * @throws Exception
   */
  public String save() throws Exception {
    BusinessResult result = getBusiness().save(getRequest(), volunteer);
    if (result.getErrors().size() > 0) {
      for (Object error : result.getErrors()) {
        addActionError(error.toString());
      }
      return FAILURE;
    }
    if (result.getMessages().size() > 0) {
      for (Object message : result.getMessages()) {
        addActionMessage(message.toString());
      }
      getSession().setAttribute(WebappsConstants.LOGIN_USER_SESSION_ID, volunteer);
      return SUCCESS;
    }
    return SUCCESS;
  }

  /**
   * 
   * @return
   * @throws Exception
   */
  public String edit() throws Exception {
      this.listSource = (List<SourceCodeBean>) SOURBUS.getAllLeaves().getResponseData();
      volunteer = (VolunteerBean) getBusiness().getLeaf(getId()).getResponseData();
    return SUCCESS;
  }

  /**
   * changePassword
   * 
   * @return
   * @throws Exception
   */
  public String changePassword() throws Exception {
    VolunteerBean loginedUser = getLoginedVolunteer();
    if (volunteer != null) {
      if (loginedUser != null && loginedUser.getPassword().equals(StringUtil.toMD5(oldPassword))) {
        loginedUser.setPassword(StringUtil.toMD5(volunteer.getPassword()));
        getBusiness().updateLeaf(loginedUser, loginedUser);
        setLoginVolunteer(loginedUser);
        addActionMessage("密码修改成功");
        return SUCCESS;
      } else {
        addActionError("原始密码错误");
      }
    } else {
      if (null != loginedUser) {
        volunteer = new VolunteerBean();
        volunteer.setName(loginedUser.getName());
      }
    }
    return SUCCESS;
  }

  private void eraseCookie(HttpServletRequest req, HttpServletResponse resp) {
    Cookie[] cookies = req.getCookies();
    if (cookies != null)
      for (int i = 0; i < cookies.length; i++) {
        cookies[i].setValue("");
        cookies[i].setMaxAge(0);
        resp.addCookie(cookies[i]);
      }
  }
}
