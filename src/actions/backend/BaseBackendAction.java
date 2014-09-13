/**
 * 
 */
package actions.backend;

import vo.table.TableInitVo;
import webapps.WebappsConstants;
import actions.BaseTableAction;
import bl.beans.BackendUserBean;
import bl.common.TableBusinessInterface;

/**
 * @author gudong
 * @since $Date:2014-03-22$
 * 
 */
public class BaseBackendAction<B extends TableBusinessInterface> extends BaseTableAction<B> {

  /**
   * 
   * @return
   */
  public BackendUserBean getLoginedVolunteer() {
    return (BackendUserBean) getSession().getAttribute(WebappsConstants.LOGIN_BACKEND_USER_SESSION_ID);
  }

  /**
   * 
   * @param loginedUser
   */
  public void setLoginBackendUser(BackendUserBean loginedUser) {
    getSession().setAttribute(WebappsConstants.LOGIN_BACKEND_USER_SESSION_ID, loginedUser);
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
