/**
 * @author gudong
 * @since Date: Mar 12, 2014
 */
package bl.beans;

import org.mongodb.morphia.annotations.Entity;

/**
 * @author gudong
 * 
 */
@Entity(value = "backend_user")
public class BackendUserBean extends Bean {

  private String password;
  private Integer type = 0; // 1=admin

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

}
