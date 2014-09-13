package wechat.message;

import bl.beans.VolunteerBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.UserServiceBusiness;
import bl.mongobus.VolunteerBusiness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ServerContext;
import wechat.BaseEvent;
import wechat.BaseMessage;
import wechat.request.*;
import wechat.response.TextResponse;
import wechat.response.VoiceResponse;
import wechat.servicemessage.ServiceMessage;
import wechat.servicemessage.ServiceMessageUtils;
import wechat.servicemessage.TextServiceMessage;
import wechat.utils.URLManager;
import wechat.utils.WechatContext;

import java.io.UnsupportedEncodingException;

/**
 * Created by wangronghua on 14-3-11.
 */
public class DefaultMessageHandler implements MessageHandler {
  private static Logger logger = LoggerFactory.getLogger(DefaultMessageHandler.class);

  @Override
  public BaseMessage handle(BaseMessage message) {
    return null;
  }

  @Override
  public BaseMessage handle(TextRequest text) {
    TextResponse response = new TextResponse();
    response.setToUserName(text.getFromUserName());
    response.setFromUserName(text.getToUserName());
    response.setCreateTime(System.currentTimeMillis() / 1000);
    response.setContent("对不起，暂未提供短信助手功能！");
    return response;
  }

  @Override
  public BaseMessage handle(VoiceRequest voice) {
    TextResponse response = new TextResponse();
    response.setToUserName(voice.getFromUserName());
    response.setFromUserName(voice.getToUserName());
    response.setCreateTime(System.currentTimeMillis() / 1000);
    response.setContent("对不起，暂未提供语音助手功能！");
    return response;
  }

  @Override
  public BaseMessage handle(VideoRequest video) {
    return null;
  }

  @Override
  public BaseMessage handle(ImageRequest image) {
    return null;
  }

  @Override
  public BaseMessage handle(LinkRequest link) {
    return null;
  }

  @Override
  public BaseMessage handle(LocationRequest location) {
    return null;
  }

  @Override
  public BaseMessage handle(BaseEvent event) {
    return null;
  }

  @Override
  public BaseMessage handle(SubscribeEvent event) {
    TextResponse response = new TextResponse();
    response.setToUserName(event.getFromUserName());
    response.setFromUserName(event.getToUserName());
    response.setCreateTime(System.currentTimeMillis() / 1000);
    response.setContent(WechatContext.getWelcomeMsg());
    return response;
  }

  @Override
  public BaseMessage handle(UnSubscribeEvent event) {
    return null;
  }

  @Override
  public BaseMessage handle(LocationEvent event) {
    return null;
  }

  @Override
  public BaseMessage handle(ClickEvent event) {
    if (event.getEventKey().equals("ME_TIMECARD")) {
      TextResponse response = new TextResponse();
      response.setToUserName(event.getFromUserName());
      response.setFromUserName(event.getToUserName());
      response.setCreateTime(System.currentTimeMillis() / 1000);
      // 直接把当月工时，当年工时发给用户就好了
      UserServiceBusiness userServiceBus = (UserServiceBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_USERSERVICE);
      VolunteerBusiness volunteerBus = (VolunteerBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_VOLUNTEER);

      VolunteerBean volunteer = volunteerBus.getVolunteerBeanByOpenID(event.getFromUserName());
      String text = "对不起，您还未绑定员工账号!";
      if (volunteer != null) {
        double hoursForMonth = userServiceBus.getServicedHoursForCurrentMonth(volunteer.getId());
        double hoursForYear = userServiceBus.getServicedHoursForCurrentMonth(volunteer.getId());
        text = "您当月服务" + hoursForMonth + "小时，当年服务" + hoursForYear + "小时.";
      } else {
        String content = "对不起，您还未绑定员工账号! 你可以在此处绑定员工服务平台: <a href='%s'>点击这里</a>";
        String url = ServerContext.getDomainName() + "/wechat/userBinding.action";
        try {
          text = String.format(content, URLManager.getUrl_OAuthRedirect(url, ServerContext.getAppID(), "snsapi_userinfo"));
        } catch (UnsupportedEncodingException e) {
          logger.error("Exception happened while getUrl_OAuthRedirect:{}", e);
        }
      }
      response.setContent(text);
      return response;
    }
    return null;
  }

  @Override
  public BaseMessage handle(ViewEvent event) {
    return null;
  }
}
