package actions.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.UserServiceBusiness;
import org.bson.types.ObjectId;

import vo.table.TableDataVo;
import vo.table.TableQueryVo;
import webapps.WebappsConstants;
import bl.beans.TrainCourseBean;
import bl.beans.VolunteerBean;
import bl.beans.VolunteerTrainCourseBean;
import bl.common.BusinessResult;
import bl.mongobus.TrainCourseBusiness;
import bl.mongobus.VolunteerTrainCourseBusiness;

/**
 * Created by peter on 14-3-14.
 */
public class TrainCourseAction extends BaseFrontAction<TrainCourseBusiness> {
  private static final TrainCourseBusiness trainCourseBusiness = (TrainCourseBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_TRAINCOURSE);
  private static final VolunteerTrainCourseBusiness volunteerCourseBusiness = (VolunteerTrainCourseBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_VOLUNTEERTRAINCOURSE);
  private List<VolunteerTrainCourseBean> myTraincourse;
  private List<TrainCourseBean> allTraincourse;

  private int start = 0;
  private int length = 5;
  private long count = 0;

  public int getStart() {
    return start;
  }

  public void setStart(int start) {
    this.start = start;
  }

  public int getLength() {
    return length;
  }

  public List<VolunteerTrainCourseBean> getMyTraincourse() {
    return myTraincourse;
  }

  public void setMyTraincourse(List<VolunteerTrainCourseBean> myTraincourse) {
    this.myTraincourse = myTraincourse;
  }

  public List<TrainCourseBean> getAllTraincourse() {
    return allTraincourse;
  }

  public void setAllTraincourse(List<TrainCourseBean> allTraincourse) {
    this.allTraincourse = allTraincourse;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }

  /**
   * 
   * @return
   * @throws Exception
   */
  public String index() throws Exception {
    if (start < 0) {
      start = 0;
    }
    // query allTraincourse
    TableQueryVo allTraincourseModel = new TableQueryVo();
    allTraincourseModel.setIDisplayStart(start);
    allTraincourseModel.setIDisplayLength(length);
    allTraincourseModel.getFilter().put("isDeleted_!=", true);
    allTraincourseModel.getFilter().put("status", TrainCourseBean.STATUS_START + "");

    // add not in filter
    Map filter = new HashMap();
    filter.put("volunteerId", getLoginedVolunteer().getId());
    List<VolunteerTrainCourseBean> volunteerTrainCourseList = volunteerCourseBusiness.queryDataByCondition(filter, null);
    if (volunteerTrainCourseList != null && volunteerTrainCourseList.size() > 0) {
      ObjectId[] trainCourseId = new ObjectId[volunteerTrainCourseList.size()];
      for (int i = 0; i < volunteerTrainCourseList.size(); i++) {
        trainCourseId[i] = new org.bson.types.ObjectId(volunteerTrainCourseList.get(i).getTraincourseId());
      }
      allTraincourseModel.getFilter().put("_id_nin", trainCourseId);
    }
    allTraincourse = trainCourseBusiness.query(allTraincourseModel).getAaData();
    count = trainCourseBusiness.getCount(allTraincourseModel);

    // query myTraincourse
    TableQueryVo myTraincourseModel = new TableQueryVo();
    myTraincourseModel.setIDisplayStart(start);
    myTraincourseModel.setIDisplayLength(length);
    myTraincourseModel.getFilter().put("volunteerId", getLoginedVolunteer().getId());

    myTraincourse = volunteerCourseBusiness.query(myTraincourseModel).getAaData();
    long myCount = volunteerCourseBusiness.getCount(myTraincourseModel);
    if (myCount > count) {
      count = myCount;
    }
    if (myTraincourse != null) {
      BusinessResult result;
      for (VolunteerTrainCourseBean volunteerTrainCourseBean : myTraincourse) {
        if (volunteerTrainCourseBean.getTraincourseId() != null) {
          result = trainCourseBusiness.getLeaf(volunteerTrainCourseBean.getTraincourseId().toString());
          if (result != null && result.getResponseData() != null) {
            volunteerTrainCourseBean.setTrainCourse((TrainCourseBean) result.getResponseData());
          }
        }
      }
    }

    return INDEX_SUCCESS;
  }

  @Override
  public TableQueryVo getModel() {
    TableQueryVo model = super.getModel();
    model.getFilter().put("status", TrainCourseBean.STATUS_START + "");
    return model;
  }

  /**
   * 
   * @return
   * @throws Exception
   */
  public String receive() throws Exception {
    String[] trainCourseIds = getIds();
    if (trainCourseIds != null) {
      VolunteerTrainCourseBusiness volunteerCourseBusiness = new VolunteerTrainCourseBusiness();
      VolunteerBean volunteer = (VolunteerBean) getSession().getAttribute(WebappsConstants.LOGIN_USER_SESSION_ID);

      VolunteerTrainCourseBean volunteerTrainCourseBean;
      Map filterMap = new HashMap();
      for (String trainCourseId : trainCourseIds) {
        if (trainCourseId == null)
          continue;
        filterMap.put("volunteerId", volunteer.getId());
        filterMap.put("traincourseId", new ObjectId(trainCourseId));
        List list = volunteerCourseBusiness.queryDataByCondition(filterMap, null);
        if (list.size() == 0) {
         TrainCourseBean tb = (TrainCourseBean)trainCourseBusiness.getLeaf(trainCourseId).getResponseData();
          volunteerTrainCourseBean = new VolunteerTrainCourseBean();
          volunteerTrainCourseBean.setVolunteerId(volunteer.getId());
          volunteerTrainCourseBean.setTraincourseId(trainCourseId);
          volunteerTrainCourseBean.setVolunteerName(volunteer.getName());
          volunteerTrainCourseBean.setTraincourseName(tb.getName());
          volunteerCourseBusiness.createLeaf(volunteerTrainCourseBean);
        }
      }
    }
    return SUCCESS;
  }
}
