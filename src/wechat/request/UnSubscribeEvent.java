package wechat.request;

import wechat.BaseEvent;
import wechat.BaseMessage;
import wechat.message.MessageHandler;

/**
 * Created by wangronghua on 14-3-15.
 */
public class UnSubscribeEvent extends BaseEvent{
  public BaseMessage accept(MessageHandler handler) {
    return handler.handle(this);
  }
}
