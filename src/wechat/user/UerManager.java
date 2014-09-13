package wechat.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.json.JSONObject;
import wechat.utils.Constants;
import wechat.HttpClientHelper;
import wechat.utils.URLManager;

/**
 * Created by wangronghua on 14-3-20.
 */
public class UerManager {
  protected final static Logger LOG = LoggerFactory.getLogger(UerManager.class);

  public static UserInfo getUserInfo(String accessToken, String openID) {
    UserInfo info = null;
    String url = URLManager.getUrl_GetUserInfo(accessToken, openID);
    String jsonResult = HttpClientHelper.getResponseAsJSONString(url);
    JSONObject object = JSONObject.fromObject(jsonResult);
    if(null != object.get(Constants.ERR_CODE)) {
      LOG.error("Error while getting token from server, errcode:{};{}", String.valueOf(object.get(Constants.ERR_CODE)), String.valueOf(object.get(Constants.ERR_CODE)));
    } else {
      info = (UserInfo) JSONObject.toBean(object, UserInfo.class);
    }
    return info;
  }

}
