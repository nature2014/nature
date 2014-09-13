/**
 * 
 */
package actions.wechat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bl.beans.TrainCourseBean;
import bl.beans.VolunteerBean;
import bl.beans.VolunteerTrainCourseBean;
import bl.common.BusinessResult;
import bl.mongobus.TrainCourseBusiness;
import bl.mongobus.VolunteerBusiness;
import bl.mongobus.VolunteerTrainCourseBusiness;

/**
 * @author gudong
 * @since $Date:2014-03-29$
 */
public class WechatTrainCourseAction extends WechatBaseAuthAction {
  private List<VolunteerTrainCourseBean> myTrainCourseList = new ArrayList<VolunteerTrainCourseBean>();

  public List<VolunteerTrainCourseBean> getMyTrainCourseList() {
    return myTrainCourseList;
  }

  public void setMyTrainCourseList(List<VolunteerTrainCourseBean> myTrainCourseList) {
    this.myTrainCourseList = myTrainCourseList;
  }

  /**
   * 
   * @return
   */
  public String myTrainCourse() {
    if(null == volunteer) {
      return "redirectBinding";
    }
    TrainCourseBusiness trainCourseBusiness = new TrainCourseBusiness();
    VolunteerTrainCourseBusiness volunteerCourseBusiness = new VolunteerTrainCourseBusiness();

    Map filter = new HashMap();
    filter.put("volunteerId_=", volunteer.getId());
    filter.put("isDeleted_!=", true);

    myTrainCourseList = volunteerCourseBusiness.queryDataByCondition(filter, null);

    BusinessResult result;
    if (myTrainCourseList != null) {
      for (VolunteerTrainCourseBean volunteerTrainCourseBean : myTrainCourseList) {
        if (volunteerTrainCourseBean.getTraincourseId() != null) {
          result = trainCourseBusiness.getLeaf(volunteerTrainCourseBean.getTraincourseId());
          if (result != null && result.getResponseData() != null) {
            volunteerTrainCourseBean.setTrainCourse((TrainCourseBean) result.getResponseData());
          }
        }
      }
    }
    return SUCCESS;
  }
}
