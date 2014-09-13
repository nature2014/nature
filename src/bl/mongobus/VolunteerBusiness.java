/**
 * 
 */
package bl.mongobus;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import bl.beans.ActiveUserBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import util.StringUtil;
import util.VolunteerCountCache;
import vo.table.TableDataVo;
import vo.table.TableQueryVo;
import webapps.WebappsConstants;
import bl.beans.VolunteerBean;
import bl.common.BeanContext;
import bl.common.BusinessResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gudong
 * @since $Date:2014-02-20$
 */
public class VolunteerBusiness extends MongoCommonBusiness<BeanContext, VolunteerBean> {
  private static Logger log = LoggerFactory.getLogger(VolunteerBusiness.class);

  private static final VolunteerTrainCourseBusiness vtcb
      = (VolunteerTrainCourseBusiness)SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_VOLUNTEERTRAINCOURSE);
  private static final UserServiceBusiness usb
      = (UserServiceBusiness)SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_USERSERVICE);
  private static final ActiveUserBusiness aub
      = (ActiveUserBusiness)SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_ACTIVEUSER);

  public VolunteerBusiness() {
    //this.dbName = "form";
    this.clazz = VolunteerBean.class;
  }

  /**
   * 通过工号获取员工
   * 
   * @param code
   *          工号
   * @return
   */
  public VolunteerBean getVolunteerBeanByCode(String code) {
    Map filter = new HashMap();
    filter.put("code_=", code);

    List<VolunteerBean> result = super.queryDataByCondition(filter, null);
    if (result != null && result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }
  }

  /**
   * 通过身份证号码获取员工
   * 
   * @param identityCard
   *          身份证号码
   * @return
   */
  public VolunteerBean getVolunteerBeanByIdentityCard(String identityCard) {
    Map filter = new HashMap();
    filter.put("identityCard_=", identityCard);

    List<VolunteerBean> result = super.queryDataByCondition(filter, null);
    if (result != null && result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }
  }

  /**
   * 
   * @param volunteer
   * @return
   */
  public BusinessResult save(HttpServletRequest request, VolunteerBean volunteer) {
    BusinessResult result = new BusinessResult();
    if (StringUtils.isBlank(volunteer.getId())) {
      VolunteerBean volunteerTmp = getVolunteerBeanByIdentityCard(volunteer.getIdentityCard());
      if (volunteerTmp != null && volunteerTmp.getIsDeleted() != true) {
        result.addError("证件号已经被注册");
        return result;
      }
      if(volunteer.getCode()!=null && !volunteer.getCode().isEmpty()){
      volunteerTmp = getVolunteerBeanByCode(volunteer.getCode());
      if (volunteerTmp != null && volunteerTmp.getIsDeleted() != true) {
        result.addError("工号已经被注册");
        return result;
      }
      }
      volunteer.set_id(ObjectId.get());
      volunteer.setPassword(StringUtil.toMD5(volunteer.getPassword()));
      result = createLeaf(request,volunteer);
      return result;
    } else {
      VolunteerBean volunteerTmp = getVolunteerBeanByIdentityCard(volunteer.getIdentityCard());
      if (volunteerTmp != null && !volunteerTmp.getId().equals(volunteer.getId()) && volunteerTmp.getIsDeleted() != true) {
        result.addError("证件号已经被注册");
        return result;
      }
      
      volunteerTmp = getVolunteerBeanByCode(volunteer.getCode());
      if (volunteerTmp != null && !volunteerTmp.getId().equals(volunteer.getId()) && volunteerTmp.getIsDeleted() != true) {
        result.addError("工号已经被注册");
        return result;
      }
      
      VolunteerBean origUser = (VolunteerBean) getLeaf(volunteer.getId().toString()).getResponseData();
      volunteer.setPassword(origUser.getPassword());
      try {
        BeanUtils.copyProperties(origUser, volunteer);
        result = updateLeaf(request,origUser);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
        result.addError(e);
      } catch (InvocationTargetException e) {
        e.printStackTrace();
        result.addError(e);
      }
      return result;
    }
  }

  /**
   * 通过OpenID获取员工
   * 
   * @param openID
   * 
   * @return
   */
  public VolunteerBean getVolunteerBeanByOpenID(String openID) {
    Map filter = new HashMap();
    filter.put("openID_=", openID);

    List<VolunteerBean> result = super.queryDataByCondition(filter, null);
    if (result != null && result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }
  }

  /**
   * 
   * @return
   */
  public TableDataVo getUnVerifiedVolunteers() {
    TableQueryVo query = new TableQueryVo();
    query.getFilter().put("status", VolunteerBean.REGISTERED);
    query.getFilter().put("isDeleted_!=", true);
    query.setIDisplayStart(0);
    query.setIDisplayLength(7);

    long count = getCount(query);
    TableDataVo dataVo = query(query);
    dataVo.setiTotalDisplayRecords(count);
    dataVo.setiTotalRecords(count);
    return dataVo;
  }

  /**
   * 
   * @return
   */
  public TableDataVo getUnInterviewedVolunteers() {
    TableQueryVo query = new TableQueryVo();
    query.getFilter().put("status", VolunteerBean.VIERFIED);
    query.getFilter().put("isDeleted_!=", true);
    query.setIDisplayStart(0);
    query.setIDisplayLength(7);
    long count = getCount(query);
    TableDataVo dataVo = query(query);
    dataVo.setiTotalDisplayRecords(count);
    dataVo.setiTotalRecords(count);
    return dataVo;
  }

  @Override
  public BusinessResult updateLeaf(BeanContext origBean, BeanContext newBean) {
    BusinessResult result = super.updateLeaf(origBean, newBean);
    VolunteerBean bean = (VolunteerBean)newBean;
    vtcb.updateVolunteerNameAndCode(bean.getId(), bean.getName(), bean.getCode());
    usb.updateVolunteerByVolunteerId(bean.getId(), bean.getCode(), bean.getName());
    return result;
  }

  @Override
  public BusinessResult deleteLeaf(String objectId) {
    BusinessResult result = super.deleteLeaf(objectId);
    vtcb.deleteRecordsByVolunteerId(objectId);
    usb.deleteRecordsByVolunteerId(objectId);
    aub.deleteRecordsByVolunteerId(objectId);
    return result;
  }

  public BusinessResult updateLeaf(HttpServletRequest request, BeanContext newBean) {
    BusinessResult result = updateLeaf(newBean, newBean);
    updateVolunteerStatus(request);
    return result;
  }

  public BusinessResult createLeaf(HttpServletRequest request, BeanContext newBean) {
    BusinessResult result = createLeaf(newBean);
    updateVolunteerStatus(request);
    return result;
  }

  public BusinessResult deleteLeaf(HttpServletRequest request, String objectId) {
    BusinessResult result = deleteLeaf(objectId);
    updateVolunteerStatus(request);
    return result;
  }

  public void updateVolunteerStatus(HttpServletRequest request) {
    VolunteerCountCache.set(getUnVerifiedVolunteers(), getUnInterviewedVolunteers());
//    request.getServletContext().setAttribute(WebappsConstants.UNVERIFIED_VOLUNTEER_KEY, getUnVerifiedVolunteers());
//    request.getServletContext().setAttribute(WebappsConstants.UNINTERVIEWED_VOLUNTEER_KEY, getUnInterviewedVolunteers());
  }

  public List<VolunteerBean> queryVolunteers(String name, String code) {
    Map filter = new HashMap();
    if(org.apache.commons.lang.StringUtils.isNotEmpty(name)) {
      filter.put("name_=", name);
    }
    if(org.apache.commons.lang.StringUtils.isNotEmpty(code)) {
      filter.put("code_=", code);
    }
    filter.put("isDeleted_=", false);
    List<VolunteerBean> result = super.queryDataByCondition(filter, null);
    return result;
  }
}
