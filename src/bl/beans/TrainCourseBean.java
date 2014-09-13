package bl.beans;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by Administrator on 14-3-14.
 */
@Entity(value = "traincourse")
public class TrainCourseBean extends Bean {
  public static final int STATUS_CREATE = 0;
  public static final int STATUS_START = 1;
  public static final int STATUS_END = 2;

  private String description;
  private Integer status; // 0 –创建 1-开始 （只有状态为1，员工才可以看见）,2 结束

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
