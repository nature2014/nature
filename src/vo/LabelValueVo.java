package vo;

/**
 * Created by wangronghua on 14-4-9.
 */
public class LabelValueVo<T> {
  private String label;
  private T value;

  public LabelValueVo (String label, T value) {
    this.label = label;
    this.value = value;
  }
  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

}
