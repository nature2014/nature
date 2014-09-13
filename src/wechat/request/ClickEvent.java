package wechat.request;

import java.util.Map;

import wechat.BaseEvent;
import wechat.BaseMessage;
import wechat.message.MessageHandler;
import wechat.utils.Constants;

/**
 * Created by wangronghua on 14-3-15.
 */
public class ClickEvent extends BaseEvent {
  private String eventKey;

  public String getEventKey() {
    return eventKey;
  }

  public void setEventKey(String eventKey) {
    this.eventKey = eventKey;
  }

  public BaseMessage accept(MessageHandler handler) {
    return handler.handle(this);
  }

  public void fill(Map paraMap) {
    super.fill(paraMap);
    this.setEventKey(paraMap.get(Constants.EVENT_KEY).toString());
  }

}
