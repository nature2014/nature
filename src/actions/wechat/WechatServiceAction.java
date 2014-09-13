package actions.wechat;

import actions.BaseAction;
import bl.beans.ActiveUserBean;
import bl.beans.ServicePlaceBean;
import bl.beans.VolunteerBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.ActiveUserBusiness;
import bl.mongobus.ServicePlaceBusiness;
import bl.mongobus.UserServiceBusiness;
import bl.mongobus.VolunteerBusiness;
import org.apache.commons.lang.StringUtils;
import vo.NameValueVo;
import vo.serviceplace.ServicePlaceVo;
import wechat.access.AccessToken;
import wechat.access.AccessTokenManager;
import wechat.user.UerManager;
import wechat.user.UserInfo;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by wangronghua on 14-3-22.
 */
public class WechatServiceAction extends WechatBaseAuthAction {

  private String userID;
  private String userName;
  private Date currentTime;
  private String servicePlaceId;
  private List<ServicePlaceVo> places;

  UserServiceBusiness usb = (UserServiceBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_USERSERVICE);
  ActiveUserBusiness activeUserBus = (ActiveUserBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_ACTIVEUSER);
  ServicePlaceBusiness sp = (ServicePlaceBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_SERVICEPLACE);

  public String checkIn(){
    if(null == volunteer) {
      return "redirectBinding";
    }
    currentTime = new Date();
    if(null != volunteer) {
      userName = volunteer.getName();
      userID = volunteer.getId();
      ActiveUserBean aub = (ActiveUserBean) activeUserBus.getActiveUserByUserId(userID).getResponseData();
      if (aub != null) {
        ServicePlaceBean spb = (ServicePlaceBean) sp.getLeaf(aub.getServicePlaceId()).getResponseData();
        if (spb != null){
          super.addActionMessage("您当前正在：" + spb.getName() + " 参与服务！");
          return "checkout";
        }
      }
      List<ServicePlaceBean> placeBeans = usb.getAvailableServicePlaces(userID);
      List<ServicePlaceBean> allPlaceBeans = (List<ServicePlaceBean>)sp.getAllLeaves().getResponseData();
      places = sp.getFormattedPlaces(allPlaceBeans, placeBeans);
      return SUCCESS;
    } else {
      return LOGIN;
    }
  }

  public String checkInSubmit(){
    if(null != userID && null != servicePlaceId) {
      usb.checkIn(userID, servicePlaceId, true);
      ServicePlaceBean spb = (ServicePlaceBean) sp.getLeaf(servicePlaceId).getResponseData();
      if (spb != null){
        super.addActionMessage("您当前正在：" + spb.getName() + " 参与服务！");
      }
    }
    return SUCCESS;
  }

  public String checkOut(){
    if(null == volunteer) {
      return "redirectBinding";
    }
    String code = getRequest().getParameter("code");
    if(null != volunteer) {
      userName = volunteer.getName();
      userID = volunteer.getId();
      ActiveUserBean aub = (ActiveUserBean) activeUserBus.getActiveUserByUserId(userID).getResponseData();
      if (aub != null) {
        ServicePlaceBean spb = (ServicePlaceBean) sp.getLeaf(aub.getServicePlaceId()).getResponseData();
        if (spb != null){
          super.addActionMessage("您当前正在：" + spb.getName() + " 参与服务！");
          return SUCCESS;
        }
      }
    }
    super.addActionError("您当前未在任何地点参与服务！");
    return ERROR;
  }

  public String checkOutSubmit() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    if(null != userID) {
      usb.checkOut(userID);
      ActiveUserBean aub = (ActiveUserBean) activeUserBus.getActiveUserByUserId(userID).getResponseData();
      if(null != aub) {
        super.addActionError("未成功签出，发生未知错误！");
      } else {
        super.addActionMessage("成功签出！");
      }
    }
    return SUCCESS;
  }


  public List<ServicePlaceVo> getPlaces() {
    return places;
  }

  public void setPlaces(List<ServicePlaceVo> places) {
    this.places = places;
  }

  public String getOpenID() {
    return openID;
  }

  public void setOpenID(String openID) {
    this.openID = openID;
  }

  public String getWechatUser() {
    return wechatUser;
  }

  public void setWechatUser(String wechatUser) {
    this.wechatUser = wechatUser;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Date getCurrentTime() {
    return currentTime;
  }

  public void setCurrentTime(Date currentTime) {
    this.currentTime = currentTime;
  }


  public String getServicePlaceId() {
    return servicePlaceId;
  }

  public void setServicePlaceId(String servicePlaceId) {
    this.servicePlaceId = servicePlaceId;
  }

  public String getUserID() {
    return userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }
}
