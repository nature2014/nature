package bl.beans;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by Administrator on 14-3-14.
 */
@Entity(value = "serviceplace")
public class ServicePlaceBean extends Bean {
  public static final int TYPE_OUT = 1;
  public static final int TYPE_IN = 0;
  public static final int AREA_OUT = 1;
  public static final int AREA_IN = 0;

  private String code = "";
  private int type = 0; // 0 公司内 含有颜色显示信息 1 公司外 含有坐标信息
  private String color = "white"; // RGB颜色值 default 白色

  private float longitude; // 经度坐标
  private float latitude; // 纬度坐标

  private String serviceicon = "";

  private int sequence;

  private int area = 0; // 0 公司内区域 1 公司外区域

  private String parentid = null;

  private transient List<ServicePlaceBean> children = new ArrayList<ServicePlaceBean>();
  private transient List<ActiveUserBean> activeUserBeanList = new ArrayList<ActiveUserBean>();

  public String getParentid() {
    return parentid;
  }

  public void setParentid(String parentid) {
    this.parentid = parentid;
  }

  public int getArea() {
    return area;
  }

  public void setArea(int area) {
    this.area = area;
  }

  public String getServiceicon() {
    return serviceicon;
  }

  public void setServiceicon(String serviceicon) {
    this.serviceicon = serviceicon;
  }

  public int getSequence() {
    return sequence;
  }

  public void setSequence(int sequence) {
    this.sequence = sequence;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public float getLongitude() {
    return longitude;
  }

  public void setLongitude(float longitude) {
    this.longitude = longitude;
  }

  public float getLatitude() {
    return latitude;
  }

  public void setLatitude(float latitude) {
    this.latitude = latitude;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  private String description;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<ServicePlaceBean> getChildren() {
    return children;
  }

  public void setChildren(List<ServicePlaceBean> children) {
    this.children = children;
  }

  public List<ActiveUserBean> getActiveUserBeanList() {
    return activeUserBeanList;
  }

  public void setActiveUserBeanList(List<ActiveUserBean> activeUserBeanList) {
    this.activeUserBeanList = activeUserBeanList;
  }

}
