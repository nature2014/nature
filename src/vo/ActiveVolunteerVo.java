package vo;

import java.util.Date;

/**
 * Created by wangronghua on 14-4-12.
 */
public class ActiveVolunteerVo {
  private String id;
  private String name;
  private int status;
  private Date checkInTime;
  private String cellPhone;
  private String iconPath;
  private double distance;
  private String description;

  public String getIconPath() {
    return iconPath;
  }

  public void setIconPath(String iconPath) {
    this.iconPath = iconPath;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Date getCheckInTime() {
    return checkInTime;
  }

  public void setCheckInTime(Date checkInTime) {
    this.checkInTime = checkInTime;
  }

  public String getCellPhone() {
    return cellPhone;
  }

  public void setCellPhone(String cellPhone) {
    this.cellPhone = cellPhone;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getDistance() {
    return distance;
  }

  public void setDistance(double distance) {
    this.distance = distance;
  }


}
