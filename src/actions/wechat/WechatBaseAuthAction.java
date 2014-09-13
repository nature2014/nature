package actions.wechat;

import actions.BaseAction;
import bl.beans.VolunteerBean;

/**
 * Created by wangronghua on 14-3-27.
 */
public class WechatBaseAuthAction extends BaseAction{

  public VolunteerBean getVolunteer() {
    return volunteer;
  }

  public void setVolunteer(VolunteerBean volunteer) {
    this.volunteer = volunteer;
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

  protected String openID;
  protected String wechatUser;
  protected VolunteerBean volunteer;

}
