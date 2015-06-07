package wechat.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DBUtils;
import util.ServerContext;
import wechat.BaseEvent;
import wechat.BaseMessage;
import wechat.servicemessage.ImgNewsServiceMessage;
import wechat.utils.URLManager;
import wechat.servicemessage.ServiceMessage;
import wechat.servicemessage.ServiceMessageUtils;
import wechat.servicemessage.TextServiceMessage;
import wechat.utils.WechatContext;

import java.io.UnsupportedEncodingException;

/**
 * Created by wangronghua on 14-3-19.
 */
public class SubscribeEventHandler implements EventHandler {

  String content = "你可以在此处绑定员工服务平台: <a href='%s'>点击这里</a>";
  protected final static Logger LOG = LoggerFactory.getLogger(SubscribeEventHandler.class);

  @Override
  public boolean acceptEvent(BaseMessage message) {
    if("event".equals(message.getMsgType())) {
      if("subscribe".equals(((BaseEvent) message).getEvent())) {
        return true;
      }
    } 
    return false;
  }

  @Override
  public void handle(BaseMessage message) {
    try {
      DBUtils.setDBFlag(message.getDbFlag());
      ServiceMessage response = new ImgNewsServiceMessage(message.getFromUserName(), WechatContext.getWelcomeMsg());
        ServiceMessageUtils.sendMessage(message.getDbFlag(), response);
    } catch (Exception e) {
      LOG.error(e.getMessage());
    } finally {
      DBUtils.removeDBFlag();
    }
  }
}
