package wechat.request;

import wechat.BaseMessage;
import wechat.message.MessageHandler;

import java.util.Map;

/**
 * Created by wangronghua on 14-3-11.
 */
public class VoiceRequest extends BaseMessage {


  private String MsgId;
  private String MediaId;
  private String Format;

  public BaseMessage accept(MessageHandler handler) {
    return handler.handle(this);
  }

  public void fill(Map paraMap) {
    super.fill(paraMap);
    this.setMediaId((String)paraMap.get("MediaId"));
    this.setFormat((String) paraMap.get("Format"));
    this.setMsgId((String)paraMap.get("MsgId"));
  }

  public String getFormat() {
    return Format;
  }

  public void setFormat(String format) {
    Format = format;
  }

  public String getMsgId() {
    return MsgId;
  }

  public void setMsgId(String msgId) {
    MsgId = msgId;
  }

  public String getMediaId() {
    return MediaId;
  }

  public void setMediaId(String mediaId) {
    MediaId = mediaId;
  }
}
