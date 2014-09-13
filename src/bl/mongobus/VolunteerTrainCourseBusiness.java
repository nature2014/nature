/**
 * 
 */
package bl.mongobus;

import bl.beans.TrainCourseBean;
import bl.beans.VolunteerTrainCourseBean;
import bl.common.BeanContext;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import dao.MongoDBConnectionFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gudong
 * @since $Date:2014-03-16$
 */
public class VolunteerTrainCourseBusiness extends MongoCommonBusiness<BeanContext, VolunteerTrainCourseBean> {
  private static Logger log = LoggerFactory.getLogger(VolunteerTrainCourseBusiness.class);

  public VolunteerTrainCourseBusiness() {
    //this.dbName = "form";
    this.clazz = VolunteerTrainCourseBean.class;
  }

  /**
   * 
   * @param volunteerId
   * @param traincourseId
   * @return
   */
  public VolunteerTrainCourseBean getVolunteerTrainCourseBean(String volunteerId, String traincourseId) {
    Map filter = new HashMap();
    filter.put("isDeleted_!=", true);
    filter.put("volunteerId_=", volunteerId);
    filter.put("traincourseId_=", traincourseId);
    List list = super.queryDataByCondition(filter, null);
    if (list == null || list.size() == 0) {
      return null;
    } else {
      return (VolunteerTrainCourseBean) list.get(0);
    }
  }

  public List<TrainCourseBean> getPassedTrainCourseByVolunteerId(String volunteerId) {
    List<TrainCourseBean> resultList = new ArrayList<TrainCourseBean>();

    Map filter = new HashMap();
    filter.put("isDeleted_!=", true);
    filter.put("volunteerId_=", volunteerId);
    List<VolunteerTrainCourseBean> list = super.queryDataByCondition(filter, null);
    for(VolunteerTrainCourseBean bean: list) {
      if(bean.getStatus() == 1) {
        TrainCourseBusiness tcb = (TrainCourseBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_TRAINCOURSE);
        TrainCourseBean courseBean = (TrainCourseBean)tcb.getLeaf(bean.getTraincourseId()).getResponseData();
        if(courseBean != null && !courseBean.getIsDeleted()) {
          resultList.add(courseBean);
        }
      }
    }
    return resultList;
  }

  public void updateVolunteerNameAndCode(String volunteerId, String volunteerName, String volunteerCode){
    Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
    UpdateOperations<VolunteerTrainCourseBean> ops
        = dc.createUpdateOperations(VolunteerTrainCourseBean.class)
                .set("volunteerName", volunteerName)
                .set("volunteerCode", volunteerCode);
    org.mongodb.morphia.query.Query query = dc.createQuery(this.clazz);
    query.filter("volunteerId", volunteerId);
    dc.update(query, ops);
  }

  public void updateCourseName(String courseId, String courseName) {
    Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
    UpdateOperations<VolunteerTrainCourseBean> ops
        = dc.createUpdateOperations(VolunteerTrainCourseBean.class).set("traincourseName", courseName);
    org.mongodb.morphia.query.Query query = dc.createQuery(this.clazz);
    query.filter("traincourseId", courseId);
    dc.update(query, ops);
  }

  public void deleteRecordsByVolunteerId(String volunteerId){
    super.updateRecordsByCondition("isDeleted", true, "volunteerId", volunteerId);
  }
}
