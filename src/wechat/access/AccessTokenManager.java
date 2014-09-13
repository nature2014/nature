package wechat.access;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.json.JSONObject;
import util.ServerContext;
import wechat.utils.Constants;
import wechat.HttpClientHelper;
import wechat.utils.URLManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangronghua on 14-3-9.
 */
public class AccessTokenManager {
  protected final static Logger LOG = LoggerFactory.getLogger(AccessTokenManager.class);

  private static Map<String, Token> tokenMap = new HashMap<String, Token>();

  public synchronized static String getToken(String dbFlag) {
    Token token = tokenMap.get(dbFlag);
    if(null != token) {
      if(token.getExpireTimestamp() > System.currentTimeMillis()) {
        return token.getToken();
      }
    } else {
      token = new Token(null, 0l);
      tokenMap.put(dbFlag, token);
    }
    String url = URLManager.getUrl_Accesstoken(ServerContext.getAppID(), ServerContext.getAppSecret()) ;

    Map resultMap = HttpClientHelper.get(url);
    if(null != resultMap.get(Constants.ERR_CODE)) {
      LOG.error("Error while getting token from server, errcode:{};{}", String.valueOf(resultMap.get(Constants.ERR_CODE)), String.valueOf(resultMap.get(Constants.ERR_CODE)));
    } else if(null != resultMap.get(Constants.ACCESS_TOKEN)){
      token.setToken((String) resultMap.get(Constants.ACCESS_TOKEN));
      Integer expires = (Integer)resultMap.get(Constants.EXPIRES_IN);
      if(null != expires) {
        token.setExpireTimestamp(System.currentTimeMillis() + (expires - 1000) * 1000);
      } else {
        token.setExpireTimestamp(0);
      }
    }
    return token.getToken();
  }

  public synchronized static AccessToken getAccessToken(String code) {
    AccessToken token = null;
    String url = URLManager.getUrl_OAuthAccesstoken(ServerContext.getAppID(), ServerContext.getAppSecret(), code);
    String resultString = HttpClientHelper.getResponseAsJSONString(url);
    JSONObject object = JSONObject.fromObject(resultString);
    if(null != object.get(Constants.ERR_CODE)) {
      LOG.error("Error while getting token from server, errcode:{};{}", String.valueOf(object.get(Constants.ERR_CODE)), String.valueOf(object.get(Constants.ERR_CODE)));
    } else {
      token = (AccessToken)JSONObject.toBean(object, AccessToken.class);
    }
    return token;
  }
}

class Token{
  private String token;
  private long expireTimestamp;

  public Token(String token, long timestamp) {
    this.token = token;
    this.expireTimestamp = timestamp;
  }

  public long getExpireTimestamp() {
    return expireTimestamp;
  }

  public void setExpireTimestamp(long expireTimestamp) {
    this.expireTimestamp = expireTimestamp;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }


}
