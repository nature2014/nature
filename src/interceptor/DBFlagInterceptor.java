package interceptor;

import bl.exceptions.MiServerException;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsStatics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DBUtils;
import util.MultiTenancyManager;
import util.WrappedRuntimeException;
import webapps.WebappsConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wangronghua on 14-5-19.
 */
public class DBFlagInterceptor extends AbstractInterceptor {

  protected static Logger log = LoggerFactory.getLogger(DBFlagInterceptor.class);

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    String dbFlag = (String) ActionContext.getContext().getSession().get(WebappsConstants.USER_DB_FLAG);
    if(StringUtils.isEmpty(dbFlag)) {
      ActionContext actionContext = invocation.getInvocationContext();
      HttpServletRequest request= (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
      StringBuffer url = request.getRequestURL();
      int start = url.indexOf("http://");
      if( start != -1) {
        start = start + 7;
      }
      int end = url.length() - request.getRequestURI().length();
      int couldBeEnd = url.lastIndexOf(":");
      if(couldBeEnd > start && end > couldBeEnd) {
        end = couldBeEnd;
      }
      String contextUrl = url.substring(start, end);
      dbFlag = MultiTenancyManager.getDBFlagByDomainName(contextUrl);
    }

    DBUtils.setDBFlag(dbFlag);

    String result;
    try {
      result = invocation.invoke();
    } catch (MiServerException e) {
      if (invocation.getAction() instanceof ActionSupport) {
        ActionSupport as = (ActionSupport) invocation.getAction();
        String errorMessage = as.getText(e.getKeyMessage(), e.getParameterMessage());
        log.error(errorMessage);
        invocation.getStack().setValue(WebappsConstants.CTX_TOKEN_ERROR_MSG_REQUEST, errorMessage);
        return as.INPUT;
      } else {
        log.error("This action exception is: {}", e);
        throw new WrappedRuntimeException(e);
      }
    } finally {
      DBUtils.removeDBFlag();
    }
    return result;
  }
}
