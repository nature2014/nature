package vo;

/**
 * Created by wangronghua on 14-3-23.
 */
public class NameValueVo {
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getValue() {
    return value;
  }

  public void setValue(Long value) {
    this.value = value;
  }

  public NameValueVo(String name, Long value) {
    this.name = name;
    this.value = value;
  }

  private String name;
  private Long value;
}
