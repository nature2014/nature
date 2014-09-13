/**
 * 
 */
package actions.front;

import vo.table.TableInitVo;
import webapps.WebappsConstants;
import actions.BaseTableAction;
import bl.beans.VolunteerBean;
import bl.common.TableBusinessInterface;

/**
 * @author gudong
 * @since $Date:2014-03-22$
 * 
 */
public class BaseFrontAction<B extends TableBusinessInterface> extends BaseTableAction<B> {

  /**
   * 
   * @return
   */
  public VolunteerBean getLoginedVolunteer() {
    return (VolunteerBean) getSession().getAttribute(WebappsConstants.LOGIN_USER_SESSION_ID);
  }

  /**
   * 
   * @param loginedUser
   */
  public void setLoginVolunteer(VolunteerBean loginedUser){
    getSession().setAttribute(WebappsConstants.LOGIN_USER_SESSION_ID, loginedUser);
  } 
 
  @Override
  public String getActionPrex() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public TableInitVo getTableInit() {
    // TODO Auto-generated method stub
    return null;
  }
}
