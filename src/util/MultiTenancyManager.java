package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangronghua on 14-5-14.
 */
public class MultiTenancyManager {

  private static Map<String, MTBean> mtMap = new HashMap<String, MTBean>();

  private static final String PREFIX="mt";
  private static final String DBFLAG_DEFAULT = "form";

  static {
    int index;

    try {
      String indexStr = ServerContext.getValue("mt.index");
      index = Integer.valueOf(indexStr);
    } catch (Exception e) {
      index = 0;
    }

    int start = 0;
    while(start < index) {
      String domainnameKey = PREFIX + start + ".domainname";
      String dbKey = PREFIX + start + ".dbflag";
      String hostname = ServerContext.getValue(domainnameKey);
      String dbFlag =  ServerContext.getValue(dbKey);
      MTBean mtBean = new MTBean(hostname, dbFlag);
      mtMap.put(hostname, mtBean);
      start ++;
    }
  }

  public static String getDBFlagByDomainName(String domainName) {
    MTBean mtBean = mtMap.get(domainName);
    if(null != mtBean) {
      if(null != mtBean.getDbFlag()) {
        return mtBean.getDbFlag();
      }
    }
    return DBFLAG_DEFAULT;
  }

  public static String[] getDBFlags() {
    MTBean[] beans = mtMap.values().toArray(new MTBean[0]);
    String[] results = new String[beans.length];
    for(int index = 0; index < beans.length; index ++) {
      MTBean bean = beans[index];
      results[index] = bean.getDbFlag();
    }
    return results;
  }
}

class MTBean {
  private String domainName;
  private String dbFlag;

  public MTBean(String domainName, String dbFlag) {
    this.domainName = domainName;
    this.dbFlag = dbFlag;
  }

  public String getDbFlag() {
    return dbFlag;
  }

  public void setDbFlag(String dbFlag) {
    this.dbFlag = dbFlag;
  }

  public String getDomainName() {
    return domainName;
  }

  public void setDomainName(String domainName) {
    this.domainName = domainName;
  }
}
