package wechat.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wechat.BaseEvent;
import wechat.BaseMessage;
import wechat.request.LocationEvent;
import wechat.utils.LocationCache;

/**
 * Created by wangronghua on 14-4-19.
 */
public class LocationEventHandler implements EventHandler{
  protected final static Logger LOG = LoggerFactory.getLogger(UnSubscriberEventHandler.class);
  @Override
  public boolean acceptEvent(BaseMessage message) {
    if("event".equals(message.getMsgType())) {
      if("LOCATION".equals(((BaseEvent) message).getEvent())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void handle(BaseMessage message) {
    if(message instanceof LocationEvent) {
      LocationCache.putLocation((LocationEvent)message);
    } else {
      LOG.warn("Error handling message in LocationEventHandler, message class is:" + message.getClass());
    }
  }
}
