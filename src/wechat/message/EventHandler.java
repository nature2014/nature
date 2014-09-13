package wechat.message;

import wechat.BaseMessage;

/**
 * Created by wangronghua on 14-3-19.
 */
public interface EventHandler extends Cloneable{

  public boolean acceptEvent(BaseMessage message);

  public void handle(BaseMessage message);
}
