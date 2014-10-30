package actions.backend;

import actions.BaseTableAction;
import bl.beans.ServicePlaceBean;
import bl.beans.TrainCourseBean;
import bl.beans.TrainCourseServicePlaceBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.ServicePlaceBusiness;
import bl.mongobus.TrainCourseBusiness;
import bl.mongobus.TrainCourseServicePlaceBusiness;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.bson.types.ObjectId;

import vo.table.TableDataVo;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;
import vo.table.TableQueryVo;

import java.util.HashMap;
import java.util.List;

/**
 * Created by peter on 14-3-14.
 */
public class BackendTrainCourseAction extends BaseBackendAction<TrainCourseBusiness> {
  List<TrainCourseBean> trainCourses = null;
  TrainCourseBean trainCourse = null;
  List<TrainCourseServicePlaceBean> trainCourseServicePlaces = null;
  List<ServicePlaceBean> servicePlaceBeans = null;

  ServicePlaceBusiness sp = (ServicePlaceBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_SERVICEPLACE);
  TrainCourseBusiness tc = (TrainCourseBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_TRAINCOURSE);
  TrainCourseServicePlaceBusiness tcp = (TrainCourseServicePlaceBusiness) SingleBusinessPoolManager
      .getBusObj(BusTieConstant.BUS_CPATH_TRAINCOURSESERVICEPLACE);

  public List<ServicePlaceBean> getServicePlaceBeans() {
    return servicePlaceBeans;
  }

  public void setServicePlaceBeans(List<ServicePlaceBean> servicePlaceBeans) {
    this.servicePlaceBeans = servicePlaceBeans;
  }

  public List<TrainCourseServicePlaceBean> getTrainCourseServicePlaces() {
    return trainCourseServicePlaces;
  }

  public void setTrainCourseServicePlaces(List<TrainCourseServicePlaceBean> trainCourseServicePlaces) {
    this.trainCourseServicePlaces = trainCourseServicePlaces;
  }

  public String trainCourseList() {
    this.trainCourses = (List<TrainCourseBean>) tc.getAllLeaves().getResponseData();
    return ActionSupport.SUCCESS;
  }

  public String delete() {
    if (getId() != null) {
        getBusiness().deleteLeaf(getId());
    }
    return ActionSupport.SUCCESS;
  }

  public String add() {
    servicePlaceBeans = sp.getAllWithoutParent();
    return ActionSupport.SUCCESS;
  }

  public String edit() {
    servicePlaceBeans = sp.getAllWithoutParent();
    String id = this.getId();
    if (id != null) {
      this.trainCourse = (TrainCourseBean) this.tc.getLeaf(id).getResponseData();
      if (this.trainCourse != null) {
        HashMap<String, Object> filter = new HashMap<String, Object>();
        filter.put("trainCourseId", this.trainCourse.get_id().toString());
        this.trainCourseServicePlaces = tcp.queryDataByCondition(filter, null);
      }
    }
    return ActionSupport.SUCCESS;
  }

  public String save() {
    String id = this.trainCourse.getId();
    try {
      if (id != null && !id.isEmpty()) {
        TrainCourseBean originalBean = (TrainCourseBean) this.tc.getLeaf(id).getResponseData();
        TrainCourseBean newBean = (TrainCourseBean) originalBean.clone();
        BeanUtils.copyProperties(newBean, this.trainCourse);
        tc.updateLeaf(originalBean, newBean);

        // update data relationship table.
        HashMap<String, Object> filter = new HashMap<String, Object>();
        filter.put("trainCourseId", this.trainCourse.get_id().toString());
        tcp.deleteByCondition(filter);

      } else {
        TrainCourseBean bean = (TrainCourseBean)this.tc.getLeafByName(trainCourse.getName()).getResponseData();
        if(null != bean) {
          super.addActionError("课程名已被使用，请更换后重新提交！");
          servicePlaceBeans = (List<ServicePlaceBean>) sp.getAllLeaves().getResponseData();
          return FAILURE;
        } else {
          this.trainCourse.set_id(ObjectId.get());
          this.tc.createLeaf(this.trainCourse);
        }

      }

      // insert data relationship table.
      for (int i = 0; i < this.trainCourseServicePlaces.size(); i++) {
        if (this.trainCourseServicePlaces.get(i) != null) {
          this.trainCourseServicePlaces.get(i).setTrainCourseId(this.trainCourse.getId());
          tcp.createLeaf(this.trainCourseServicePlaces.get(i));
        }
      }

    } catch (Exception e) {
      LOG.error("this exception [{}]", e.getMessage());
    }
    return ActionSupport.SUCCESS;
  }

  public List<TrainCourseBean> getTrainCourses() {
    return trainCourses;
  }

  public void setTrainCourses(List<TrainCourseBean> trainCourses) {
    this.trainCourses = trainCourses;
  }

  public TrainCourseBean getTrainCourse() {
    return trainCourse;
  }

  public void setTrainCourse(TrainCourseBean trainCourse) {
    this.trainCourse = trainCourse;
  }

  @Override
  public String getActionPrex() {
    return getRequest().getContextPath() + "/backend/traincourse";
  }

  @Override
  public TableInitVo getTableInit() {
    TableInitVo init = new TableInitVo();
    init.getAoColumns().add(new TableHeaderVo("name", "课程名称").enableSearch());
    //0 –创建 1-开始 （只有状态为1，员工才可以看见）,2 结束
    init.getAoColumns().add(new TableHeaderVo("status", "状态").addSearchOptions(new String[][] { { "0", "1" ,"2"}, { "创建", "开始", "结束"}}));
    return init;
  }

  @Override
  public String getCustomJs() {
    return getRequest().getContextPath() + "/js/trainCourse.js";
  }
  
  public String search() {
    TableQueryVo param = new TableQueryVo();
    param.getFilter().put("name", trainCourse.getName());
    param.setIDisplayLength(10);
    param.setIDisplayStart(0);
    TableDataVo dataVo = getBusiness().query(param);
    JSONArray jsonArray = JSONArray.fromObject(dataVo.getAaData());
    writeJson(jsonArray);
    return null;
  }
    @Override
    public String getTableTitle() {
        return "<li>培训管理</li><li class=\"active\">培训课程</li>";
    }
}
