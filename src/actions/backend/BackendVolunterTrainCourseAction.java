package actions.backend;

import bl.beans.TrainCourseBean;
import bl.beans.VolunteerBean;
import bl.beans.VolunteerTrainCourseBean;
import bl.common.BusinessResult;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.TrainCourseBusiness;
import bl.mongobus.VolunteerBusiness;
import bl.mongobus.VolunteerTrainCourseBusiness;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author gudong
 * @since $Date:2013-03-20$
 */
public class BackendVolunterTrainCourseAction extends BaseBackendAction<VolunteerTrainCourseBusiness> {

  private static final VolunteerBusiness volunteerBus = (VolunteerBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_VOLUNTEER);
  private static final TrainCourseBusiness tcBus = (TrainCourseBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_TRAINCOURSE);

  private VolunteerTrainCourseBean volunteerTrainCourse;
  private List<TrainCourseBean> trainCourseList;
  private String volunteerId;
  private String traincourseId;

  public String getVolunteerId() {
    return volunteerId;
  }

  public void setVolunteerId(String volunteerId) {
    this.volunteerId = volunteerId;
  }

  public String getTraincourseId() {
    return traincourseId;
  }

  public void setTraincourseId(String traincourseId) {
    this.traincourseId = traincourseId;
  }

  public VolunteerTrainCourseBean getVolunteerTrainCourse() {
    return volunteerTrainCourse;
  }

  public void setVolunteerTrainCourse(VolunteerTrainCourseBean volunteerTrainCourse) {
    this.volunteerTrainCourse = volunteerTrainCourse;
  }

  public List<TrainCourseBean> getTrainCourseList() {
    return trainCourseList;
  }

  public void setTrainCourseList(List<TrainCourseBean> trainCourseList) {
    this.trainCourseList = trainCourseList;
  }

  @Override
  public String getActionPrex() {
    return getRequest().getContextPath() + "/backend/volunterTrainCourse";
  }

  @Override
  public String getCustomJs() {
    return getRequest().getContextPath() + "/js/volunterTrainCourse.js";
  }

  @Override
  public TableInitVo getTableInit() {
    TableInitVo init = new TableInitVo();
    init.getAoColumns().add(new TableHeaderVo("volunteerName", "员工").enableSearch());
    init.getAoColumns().add(new TableHeaderVo("volunteerCode", "工号").enableSearch());
    init.getAoColumns().add(new TableHeaderVo("traincourseName", "课程名称").enableSearch());
    //0=未通过,1=通过
    init.getAoColumns().add(new TableHeaderVo("status", "状态").addSearchOptions(new String[][] { { "0", "1"}, { "未通过", "通过"}}).enableSearch());
    init.getAoColumns().add(new TableHeaderVo("createTime", "培训时间"));

    return init;
  }

  @Override
  public String add() {
    Map filter = new HashMap();
    filter.put("isDeleted_!=", true);
    trainCourseList = tcBus.queryDataByCondition(filter, null);
    return SUCCESS;
  }

  @Override
  public String edit() throws Exception {
    volunteerTrainCourse = (VolunteerTrainCourseBean) getBusiness().getLeaf(getId()).getResponseData();
    BusinessResult result;
    if (volunteerTrainCourse != null && volunteerTrainCourse.getTraincourseId() != null) {
      result = tcBus.getLeaf(volunteerTrainCourse.getTraincourseId());
      if (result != null && result.getResponseData() != null) {
        volunteerTrainCourse.setTrainCourse((TrainCourseBean) result.getResponseData());
      }
    }
    if (volunteerTrainCourse.getVolunteerId() != null) {
      result = volunteerBus.getLeaf(volunteerTrainCourse.getVolunteerId());
      if (result != null && result.getResponseData() != null) {
        volunteerTrainCourse.setVolunteer((VolunteerBean) result.getResponseData());
      }
    }
    Map filter = new HashMap();
    filter.put("isDeleted_!=", true);
    trainCourseList = new TrainCourseBusiness().queryDataByCondition(filter, null);
    traincourseId = volunteerTrainCourse.getTraincourseId().toString();
    volunteerId = volunteerTrainCourse.getVolunteerId().toString();
    return SUCCESS;
  }

  @Override
  public String delete() throws Exception {
    getBusiness().deleteLeaf(getId());
    return SUCCESS;
  }

  @Override
  public String save() throws Exception {
    if (StringUtils.isNotBlank(traincourseId) && StringUtils.isNotBlank(volunteerId)) {
      String[] volunteerIdSplit = volunteerId.split(",");
        for (String vid : volunteerIdSplit) {
            vid = vid.trim();
            VolunteerTrainCourseBean vtc = null;
            boolean update = false;
            vtc = getBusiness().getVolunteerTrainCourseBean(vid, traincourseId);
            if (vtc != null) {
                update = true;
                vtc.setStatus(volunteerTrainCourse.getStatus());
            } else {
                vtc = new VolunteerTrainCourseBean();
                vtc.setStatus(volunteerTrainCourse.getStatus());
            }

            vtc.setTraincourseId(traincourseId);
            vtc.setVolunteerId(vid);
            VolunteerBean volunteer = (VolunteerBean) volunteerBus.getLeaf(vid).getResponseData();
            TrainCourseBean tcBean = (TrainCourseBean) tcBus.getLeaf(traincourseId).getResponseData();
            if (null != volunteer) {
                vtc.setVolunteerName(volunteer.getName());
                vtc.setVolunteerCode(volunteer.getCode());
            }
            if (null != tcBean) {
                vtc.setTraincourseName(tcBean.getName());
            }
            if (update) {
                getBusiness().updateLeaf(vtc, vtc);
            } else {
                vtc.set_id(ObjectId.get());
                getBusiness().createLeaf(vtc);
            }
        }
      return SUCCESS;
    } else {
      Map filter = new HashMap();
      filter.put("isDeleted_!=", true);
      trainCourseList = tcBus.queryDataByCondition(filter, null);
      addActionError("员工或者培训课程不能为空!");
      return FAILURE;
    }
  }

  @Override
  public String getTableTitle() {
    return "<li>培训管理</li><li class=\"active\">培训记录</li>";
  }
}
