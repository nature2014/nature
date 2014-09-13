package bl.mongobus;

import bl.beans.ActiveUserBean;
import bl.beans.UserServiceBean;
import bl.common.BeanContext;
import bl.common.BusinessResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dao.MongoDBConnectionFactory;
import org.mongodb.morphia.Datastore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangronghua on 14-3-15.
 */
public class ActiveUserBusiness extends MongoCommonBusiness<BeanContext, ActiveUserBean>{

  private static Logger LOG = LoggerFactory.getLogger(ActiveUserBusiness.class);
  public ActiveUserBusiness() {
    this.clazz = ActiveUserBean.class;
  }

  public BusinessResult getActiveUserByUserId(String userId) {
    Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
    BusinessResult br = new BusinessResult();
    br.setResponseData(dc.find(this.clazz, "userId", userId).get());
    return br;
  }

  public BusinessResult getActiveUsersByServicePlace(String servicePlaceId) {
    Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
    BusinessResult br = new BusinessResult();
    br.setResponseData(dc.find(this.clazz, "servicePlaceId", servicePlaceId)
        .filter("isDeleted", false).order("distance").asList());
    return br;
  }

  /**
   * 用来获取各服务地点的在线用户数，以map形式返回，servicePlaceId作为Key， count作为value
   * @return
   */
  public Map getActiveVolunteerCounts(){
    Map<String, Integer> resultMap = new HashMap<String, Integer>();
    List<ActiveUserBean> activeUserBeanList = (List<ActiveUserBean>)this.getAllLeaves().getResponseData();
    for(ActiveUserBean bean: activeUserBeanList) {
      if(bean.getIsDeleted() != true) {
        Integer count = resultMap.get(bean.getServicePlaceId());
        if(null == count) count = 0;
        resultMap.put(bean.getServicePlaceId(), ++count);
      }
    }
    return resultMap;
  }

  public void deleteRecordsByVolunteerId(String volunteerId){
    super.updateRecordsByCondition("isDeleted", true, "userId", volunteerId);
  }
}
