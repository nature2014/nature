package core.componentext;

import dynamicschema.Form;

/**
 * Created by wangronghua on 14-2-11.
 */
public class FormExtension extends Form{

  public String getInnerHTML() {
    return innerHTML;
  }

  public void setInnerHTML(String innerHTML) {
    this.innerHTML = innerHTML;
  }

  private String innerHTML;


}
