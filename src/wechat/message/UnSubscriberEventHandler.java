package wechat.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wechat.BaseEvent;
import wechat.BaseMessage;

/**
 * Created by wangronghua on 14-4-19.
 */
public class UnSubscriberEventHandler implements EventHandler {
  protected final static Logger LOG = LoggerFactory.getLogger(UnSubscriberEventHandler.class);

  @Override
  public boolean acceptEvent(BaseMessage message) {
    if("event".equals(message.getMsgType())) {
      if("unsubscribe".equals(((BaseEvent) message).getEvent())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void handle(BaseMessage message) {
    //todo unsubscribe, remove those openID existing in DB
  }
}
