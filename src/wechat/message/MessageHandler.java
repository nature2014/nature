package wechat.message;

import wechat.BaseEvent;
import wechat.BaseMessage;
import wechat.request.*;

/**
 * Created by wangronghua on 14-3-11.
 */
public interface MessageHandler {

  public BaseMessage handle(BaseMessage message);
  public BaseMessage handle(TextRequest text);
  public BaseMessage handle(VoiceRequest voice);
  public BaseMessage handle(VideoRequest video);
  public BaseMessage handle(ImageRequest image);
  public BaseMessage handle(LinkRequest link);
  public BaseMessage handle(LocationRequest location);

  public BaseMessage handle(BaseEvent event);
  public BaseMessage handle(SubscribeEvent event);
  public BaseMessage handle(UnSubscribeEvent event);
  public BaseMessage handle(LocationEvent event);
  public BaseMessage handle(ClickEvent event);
  public BaseMessage handle(ViewEvent event);

}
