package wechat.response;

import wechat.BaseMessage;
import wechat.utils.Constants;

/**
 * Created by wangronghua on 14-3-11.
 */
public class VoiceResponse extends BaseMessage {


  private Voice Voice;

  public void setMediaId(String mediaId) {
    this.setVoice(new Voice(mediaId));
  }

  public VoiceResponse() {
    this.setMsgType(Constants.MSG_TYPE_VOICE);
  }

  public String toXml() {
    xstream.alias("xml", this.getClass());
    xstream.alias("Voice", Voice.getClass());
    return xstream.toXML(this);
  }


  public Voice getVoice() {
    return Voice;
  }

  public void setVoice(Voice voice) {
    Voice = voice;
  }


}

class Voice{
  private String MediaId;

  public Voice(String MediaId) {
    this.MediaId = MediaId;
  }
  public String getMediaId() {
    return MediaId;
  }

  public void setMediaId(String MediaId) {
    this.MediaId = MediaId;
  }

}
