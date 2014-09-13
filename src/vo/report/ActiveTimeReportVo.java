package vo.report;

/**
 * Created by wangronghua on 14-3-16.
 */
public class ActiveTimeReportVo {
  private String name;
  private String code;
  private int dayHours;
  private int monthHours;
  private int yearHours;
  private int totalHours;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public int getDayHours() {
    return dayHours;
  }

  public void setDayHours(int dayHours) {
    this.dayHours = dayHours;
  }

  public int getMonthHours() {
    return monthHours;
  }

  public void setMonthHours(int monthHours) {
    this.monthHours = monthHours;
  }

  public int getYearHours() {
    return yearHours;
  }

  public void setYearHours(int yearHours) {
    this.yearHours = yearHours;
  }

  public int getTotalHours() {
    return totalHours;
  }

  public void setTotalHours(int totalHours) {
    this.totalHours = totalHours;
  }

}
