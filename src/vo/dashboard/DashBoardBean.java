package vo.dashboard;

import vo.LabelValueVo;
import vo.NameValueVo;

import java.util.List;

/**
 * Created by wangronghua on 14-4-9.
 */
public class DashBoardBean {
  private long volunteerCount;
  private long courseCount;
  private long trainCount;
  private long bindingCount;
  private long registerByClient;

  private long registerByWechat;
  private long newUnbindingCount;
  private long newBindingCount;

  private String monthlyWorkingHours;

  public long getNewBindingCount() {
    return newBindingCount;
  }

  public void setNewBindingCount(long newBindingCount) {
    this.newBindingCount = newBindingCount;
  }

  public long getRegisterByClient() {
    return registerByClient;
  }

  public void setRegisterByClient(long registerByClient) {
    this.registerByClient = registerByClient;
  }

  public long getRegisterByWechat() {
    return registerByWechat;
  }

  public void setRegisterByWechat(long registerByWechat) {
    this.registerByWechat = registerByWechat;
  }

  public long getNewUnbindingCount() {
    return newUnbindingCount;
  }

  public void setNewUnbindingCount(long newUnbindingCount) {
    this.newUnbindingCount = newUnbindingCount;
  }


  public long getVolunteerCount() {
    return volunteerCount;
  }

  public void setVolunteerCount(long volunteerCount) {
    this.volunteerCount = volunteerCount;
  }

  public long getCourseCount() {
    return courseCount;
  }

  public void setCourseCount(long courseCount) {
    this.courseCount = courseCount;
  }

  public long getTrainCount() {
    return trainCount;
  }

  public void setTrainCount(long trainCount) {
    this.trainCount = trainCount;
  }

  public long getBindingCount() {
    return bindingCount;
  }

  public void setBindingCount(long bindingCount) {
    this.bindingCount = bindingCount;
  }

  public String getMonthlyWorkingHours() {
    return monthlyWorkingHours;
  }

  public void setMonthlyWorkingHours(String monthlyWorkingHours) {
    this.monthlyWorkingHours = monthlyWorkingHours;
  }


}
