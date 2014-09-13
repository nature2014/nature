package util;

/**
 * Created by wangronghua on 14-5-13.
 */
public class DBUtils {

  private static ThreadLocal<String> threadLocal = new ThreadLocal<String>();

  public static void setDBFlag(String dbFlag){
    threadLocal.set(dbFlag);
  }

  public static String getDBFlag() {
    return threadLocal.get();
  }

  public static void removeDBFlag(){
    threadLocal.remove();
  }
}
