package interceptor;

import actions.BaseAction;
import actions.wechat.WechatBaseAuthAction;
import bl.beans.VolunteerBean;
import bl.constants.BusTieConstant;
import bl.exceptions.MiServerException;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.VolunteerBusiness;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.StrutsStatics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DBUtils;
import util.MultiTenancyManager;
import util.ServerContext;
import wechat.access.AccessToken;
import wechat.access.AccessTokenManager;
import wechat.user.UerManager;
import wechat.user.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by wangronghua on 14-3-27.
 */
public class WechatInterceptor extends AbstractInterceptor {

  protected static Logger log = LoggerFactory.getLogger(WechatInterceptor.class);

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {

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
    String dbFlag = MultiTenancyManager.getDBFlagByDomainName(contextUrl);

    DBUtils.setDBFlag(dbFlag);

    String result = "message";

    try {
      Map<String, Object> parameters = invocation.getInvocationContext().getParameters();

      if(invocation.getAction() instanceof WechatBaseAuthAction) {
        WechatBaseAuthAction action = (WechatBaseAuthAction) invocation.getAction();
        String openID = null;
        String wechatUser = null;
        if(ServerContext.isWechatLocalDebug()) {
          openID = "test";
          wechatUser = "test";
        } else {
          String code = null;
          String[] codesArray = (String[])parameters.get("code");
          if(null != codesArray && codesArray.length > 0) {
            code = codesArray[0];
          }
          if(null != code) {
            AccessToken token = AccessTokenManager.getAccessToken(code);
            if(null != token) {
                openID = token.getOpenid();
                UserInfo info = UerManager.getUserInfo(token.getAccess_token(), token.getOpenid());
                if (null != info) {
                    wechatUser = info.getNickname();
                }
            }
          }
        }
        if(null != openID) {
          action.setOpenID(openID);
          action.setWechatUser(wechatUser);
        }

        if(null != action.getOpenID()) {
          VolunteerBean volunteer;
          VolunteerBusiness vb = (VolunteerBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_VOLUNTEER);
          volunteer = vb.getVolunteerBeanByOpenID(action.getOpenID());
          if(null != volunteer && volunteer.getStatus() == 2) {
            action.setVolunteer(volunteer);
            if(null == wechatUser) {
              wechatUser = volunteer.getWechat();
              action.setWechatUser(wechatUser);
            }
          }
        }
      }

      result = invocation.invoke();
    } catch (MiServerException e) {
      log.error("Exception happened:", e);
      if(invocation.getAction() instanceof BaseAction) {
        ((BaseAction)invocation.getAction()).addActionError(e.getMessage());
      }
    } catch (Exception e){
      log.error("Fetal Exception happened:", e);
    } finally {
      DBUtils.removeDBFlag();
    }
    return result;
  }
}
