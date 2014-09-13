package bl.beans;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by Administrator on 14-3-18.
 */
@Entity(value = "systemsetting")
public class SystemSettingBean extends Bean {
    private String maptoken="YLVPteLNu0BakMGSb3GIkTBr";
    private String city="南京";
    private String introduction;

    private String appID;
    private String appsecret;
    private String apptoken;
    private String domainname;
    private boolean debugMode = false;
    private String defaultPassword = "123456";

    private String welcomeMsg = "欢迎使用员工服务平台!";

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    public boolean isDebugMode() {
      return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
      this.debugMode = debugMode;
    }

    public String getAppID() {
      return appID;
    }

    public void setAppID(String appID) {
      this.appID = appID;
    }

    public String getAppsecret() {
      return appsecret;
    }

    public void setAppsecret(String appsecret) {
      this.appsecret = appsecret;
    }

    public String getApptoken() {
      return apptoken;
    }

    public void setApptoken(String apptoken) {
      this.apptoken = apptoken;
    }

    public String getDomainname() {
      return domainname;
    }

    public void setDomainname(String domainname) {
      this.domainname = domainname;
    }


    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMaptoken() {
        return maptoken;
    }

    public void setMaptoken(String maptoken) {
        this.maptoken = maptoken;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWelcomeMsg() {
      return welcomeMsg;
    }

    public void setWelcomeMsg(String welcomeMsg) {
      this.welcomeMsg = welcomeMsg;
    }

}
