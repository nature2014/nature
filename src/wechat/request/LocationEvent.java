package wechat.request;

import wechat.BaseEvent;
import wechat.BaseMessage;
import wechat.message.MessageHandler;

import java.util.Map;

/**
 * Created by wangronghua on 14-3-15.
 */
public class LocationEvent extends BaseEvent{

  private String Latitude;
  private String Longitude;
  private String Precision;


  public BaseMessage accept(MessageHandler handler) {
    return handler.handle(this);
  }

  public void fill(Map paraMap) {
    super.fill(paraMap);
    this.Latitude = (String)paraMap.get("Latitude");
    this.Longitude = (String)paraMap.get("Longitude");
    this.Precision = (String)paraMap.get("Precision");
  }

  public String getPrecision() {
    return Precision;
  }

  public void setPrecision(String precision) {
    Precision = precision;
  }

  public String getLatitude() {
    return Latitude;
  }

  public void setLatitude(String latitude) {
    Latitude = latitude;
  }

  public String getLongitude() {
    return Longitude;
  }

  public void setLongitude(String longitude) {
    Longitude = longitude;
  }
}
