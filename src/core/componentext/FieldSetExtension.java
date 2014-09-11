package core.componentext;

import dynamicschema.Section;

/**
 * Created by wangronghua on 14-2-15.
 */
public class FieldSetExtension extends Section {

  public String getInnerHTML() {
    return innerHTML;
  }

  public void setInnerHTML(String innerHTML) {
    this.innerHTML = innerHTML;
  }

  private String innerHTML;

}
