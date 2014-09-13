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
import wechat.access.AccessToken;
import wechat.access.AccessTokenManager;
import wechat.user.UerManager;
import wechat.user.UserInfo;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

/**
 * Created by peter on 14-3-22.
 */
public class WechatServiceDescriptionAction extends BaseAction {

    public String outDescription() {
        return SUCCESS;
    }

}
