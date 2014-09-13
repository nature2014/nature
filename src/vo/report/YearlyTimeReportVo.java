package vo.report;

/**
 * Created by wangronghua on 14-3-23.
 */
public class YearlyTimeReportVo {

  public long getLastYearValue() {
    return lastYearValue;
  }

  public void setLastYearValue(long lastYearValue) {
    this.lastYearValue = lastYearValue;
  }

  public long getThisYearValue() {
    return thisYearValue;
  }

  public void setThisYearValue(long thisYearValue) {
    this.thisYearValue = thisYearValue;
  }
  public String getLastYear() {
    return lastYear;
  }

  public void setLastYear(String lastYear) {
    this.lastYear = lastYear;
  }

  public String getThisYear() {
    return thisYear;
  }

  public void setThisYear(String thisYear) {
    this.thisYear = thisYear;
  }

  private String thisYear;
  private String lastYear;
  private long thisYearValue;
  private long lastYearValue;


}
