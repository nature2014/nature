package wechat.message;

import wechat.BaseMessage;

/**
 * Created by wangronghua on 14-3-19.
 */
public class ActiveUserHandler implements EventHandler {
  @Override
  public boolean acceptEvent(BaseMessage message) {
    return true;
  }

  @Override
  public void handle(BaseMessage message) {

  }
}
