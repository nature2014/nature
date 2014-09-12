package util;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wechat.utils.Constants;

public class ServerContext {
  protected static Logger LOG = LoggerFactory.getLogger(ServerContext.class);
  private final static Properties prop = new Properties();

  public static void init(InputStream input) {
    // 从流中加载properties文件信息
    try {
        prop.load(input);
    } catch (Exception e) {
        LOG.error("this exception [{}]", e.getMessage());
    }
  }

  public static String getValue(String key) {
    if (prop != null) {
        return prop.getProperty(key);
    }
    return "";
  }

  public static void putValue(String key, String value) {
    if(prop != null) {
      prop.put(key, value);
    } else {
      LOG.error("ServerContext property is not ready for Wechat!");
    }
  }

  public static boolean isWechatLocalDebug() {
    if (null != prop) {
        return "true".equals(getValue("wechat.debug.local"));
    }
    return false;
  }

  public static String getAppID() {
    return getValue(DBUtils.getDBFlag() + Constants.APP_ID);
  }

  public static String getAppToken() {
    return getValue(DBUtils.getDBFlag() + Constants.APP_TOKEN);
  }

  public static String getAppSecret() {
    return getValue(DBUtils.getDBFlag() + Constants.APP_SECRET);
  }

  public static String getDomainName() {
    return getValue(DBUtils.getDBFlag() + Constants.DOMAIN_NAME);
  }

  public static String getRealPngDir() {
    String result = ServerContext.getValue("realstorepngdirectory");
    if(StringUtils.isNotEmpty(DBUtils.getDBFlag())){
      result = result + File.separator + DBUtils.getDBFlag();
    }
    return result;
  }

  public static String getVirtualPngDir() {
    String result = ServerContext.getValue("vitualstorepngdirectory");
    if(StringUtils.isNotEmpty(DBUtils.getDBFlag())){
      result = result + DBUtils.getDBFlag() + "/";
    }
    return result;
  }

}
