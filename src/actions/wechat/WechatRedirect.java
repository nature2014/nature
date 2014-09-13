package actions.wechat;

import actions.BaseAction;
import util.ServerContext;
import wechat.utils.URLManager;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by wangronghua on 14-3-22.
 */
public class WechatRedirect extends BaseAction {

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  private String url;

  public String redirect() {
    try {
      url = URLManager.getUrl_OAuthRedirect(ServerContext.getDomainName() +
                        URLDecoder.decode(getRequest().getParameter("url"), "UTF-8"), ServerContext.getAppID());
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return SUCCESS;
  }

}
