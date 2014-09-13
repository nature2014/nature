/**
 * 
 */
package bl.mongobus;

import bl.beans.BackendUserBean;
import bl.common.BeanContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gudong
 * @since $Date:2014-02-20$
 */
public class BackendUserBusiness extends MongoCommonBusiness<BeanContext, BackendUserBean> {
  private static Logger log = LoggerFactory.getLogger(BackendUserBusiness.class);

  public BackendUserBusiness() {
    //this.dbName = "form";
    this.clazz = BackendUserBean.class;
  }
   
}
