package wechat.utils;

import bl.beans.SystemSettingBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.SystemSettingBusiness;
import util.DBUtils;
import util.MultiTenancyManager;
import wechat.message.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangronghua on 14-3-19.
 */
public class WechatContext {

  private static SystemSettingBusiness ssb = (SystemSettingBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_SYSTEMSETTING);

  public static void init() {
    MessageBus.get().addHandler(new SubscribeEventHandler());
    MessageBus.get().addHandler(new UnSubscriberEventHandler());
    MessageBus.get().addHandler(new LocationEventHandler());
  }

  public static String getWelcomeMsg() {
    String result = "欢迎使用员工服务平台!";
    SystemSettingBean bean = ssb.getLeaf();
    if(null != bean) {
      result = bean.getWelcomeMsg();
    }
    return result;
  }

}
