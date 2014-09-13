package util;

import org.apache.commons.lang.StringUtils;
import vo.table.TableDataVo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangronghua on 14-5-19.
 */
public class VolunteerCountCache {

  private static Map<String, WrappedObject> vcMap = new ConcurrentHashMap<String, WrappedObject>();

  private static long[] DEFAULT = {0, 0}; //第一位代表未审核，第二位代表未面试

  public static TableDataVo getUnVerified() {
    WrappedObject result = null;
    String dbFlag = DBUtils.getDBFlag();
    if(StringUtils.isNotEmpty(dbFlag)) {
      result = vcMap.get(dbFlag);
    }
    if(null == result) {
      return new TableDataVo();
    }
    return result.getUnVerified();
  }

  public static TableDataVo getUnInterviewed() {
    WrappedObject result = null;
    String dbFlag = DBUtils.getDBFlag();
    if(StringUtils.isNotEmpty(dbFlag)) {
      result = vcMap.get(dbFlag);
    }
    if(null == result) {
      return new TableDataVo();
    }
    return result.getUnInterviewed();
  }

  public static synchronized void set(TableDataVo unVerified, TableDataVo unInterviewed) {
    String dbFlag = DBUtils.getDBFlag();
    if(StringUtils.isNotEmpty(dbFlag)) {
      WrappedObject wrappedObject = vcMap.get(dbFlag);
      if(null == wrappedObject) {
        wrappedObject = new WrappedObject(unVerified, unInterviewed);
        vcMap.put(dbFlag, wrappedObject);
      }
      wrappedObject.setUnVerified(unVerified);
      wrappedObject.setUnInterviewed(unInterviewed);
    }
  }

}

class WrappedObject {

  public WrappedObject(TableDataVo unVerified, TableDataVo unInterviewed) {
    this.unVerified = unVerified;
    this.unInterviewed = unInterviewed;
  }
  public TableDataVo getUnInterviewed() {
    return unInterviewed;
  }

  public void setUnInterviewed(TableDataVo unInterviewed) {
    this.unInterviewed = unInterviewed;
  }

  public TableDataVo getUnVerified() {
    return unVerified;
  }

  public void setUnVerified(TableDataVo unVerified) {
    this.unVerified = unVerified;
  }

  private TableDataVo unVerified;
  private TableDataVo unInterviewed;

}