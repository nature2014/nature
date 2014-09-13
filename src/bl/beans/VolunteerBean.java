package bl.beans;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

/**
 * @author gudong
 * 
 */
@Entity(value = "volunteer")
public class VolunteerBean extends Bean {
  public static final int REGISTERED = 0;
  public static final int VIERFIED = 1;
  public static final int INTERVIEWED = 2;
  public static final int SERVICED = 3;
  public static final int LOGOUTED = 4;
  
  public static final int REGISTER_HOSPITAL = 1;
  public static final int REGISTER_WECHAT = 2;
  
  
  private String code;
  private Integer registerFrom = REGISTER_HOSPITAL; // 1=hospital,2=wechat.
  private Integer status = REGISTERED; // 0=已注册、1=已审核、2=已面试、3=审核不通过、4=面试不通过
  private Integer sex; // 1=Male,2=Female
  private Date birthDate;
  private String identityCard;
  private Integer identityType = 0 ; // -1 其他， 0 身份证 1 护照 2 港澳台
  private String education;
  private String politics;
  private String occupation;
  private String workAddress;
  private String password;
  private String address;
  private String qq;
  private String email;
  private String cellPhone;

  private String openID;
  private String wechat;
  private String iconpath;
  private String fingerpath;

    public Integer getIdentityType() {
        return identityType;
    }

    public void setIdentityType(Integer identityType) {
        this.identityType = identityType;
    }

    public String getFingerpath() {
        return fingerpath;
    }

    public void setFingerpath(String fingerpath) {
        this.fingerpath = fingerpath;
    }

    public String getIconpath() {
    return iconpath;
  }

  public void setIconpath(String iconpath) {
    this.iconpath = iconpath;
  }

  public Integer getRegisterFrom() {
    return registerFrom;
  }

  public void setRegisterFrom(Integer registerFrom) {
    this.registerFrom = registerFrom;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getSex() {
    return sex;
  }

  public void setSex(Integer sex) {
    this.sex = sex;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getQq() {
    return qq;
  }

  public void setQq(String qq) {
    this.qq = qq;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCellPhone() {
    return cellPhone;
  }

  public void setCellPhone(String cellPhone) {
    this.cellPhone = cellPhone;
  }

  public String getWechat() {
    return wechat;
  }

  public void setWechat(String wechat) {
    this.wechat = wechat;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public String getIdentityCard() {
    return identityCard;
  }

  public void setIdentityCard(String identityCard) {
    this.identityCard = identityCard;
  }

  public String getEducation() {
    return education;
  }

  public void setEducation(String education) {
    this.education = education;
  }

  public String getPolitics() {
    return politics;
  }

  public void setPolitics(String politics) {
    this.politics = politics;
  }

  public String getOccupation() {
    return occupation;
  }

  public void setOccupation(String occupation) {
    this.occupation = occupation;
  }

  public String getWorkAddress() {
    return workAddress;
  }

  public void setWorkAddress(String workAddress) {
    this.workAddress = workAddress;
  }


  public String getOpenID() {
    return openID;
  }

  public void setOpenID(String openID) {
    this.openID = openID;
  }
}
