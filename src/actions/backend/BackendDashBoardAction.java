package actions.backend;

import actions.BaseAction;
import bl.beans.UserServiceBean;
import bl.beans.VolunteerBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.*;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import vo.dashboard.DashBoardBean;
import vo.table.TableQueryVo;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangronghua on 14-4-9.
 */
public class BackendDashBoardAction extends BaseAction{

  UserServiceBusiness userServiceBus = (UserServiceBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_USERSERVICE);
  VolunteerBusiness volunteerBus = (VolunteerBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_VOLUNTEER);
  TrainCourseBusiness courseBusiness = (TrainCourseBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_TRAINCOURSE);
  VolunteerTrainCourseBusiness vtcBusiness = (VolunteerTrainCourseBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_VOLUNTEERTRAINCOURSE);

  private DashBoardBean dashBoardBean = new DashBoardBean();
  private List<VolunteerBean> volunteerBeanList;

  public String getAllData() {
    dashBoardBean = new DashBoardBean();
    volunteerBeanList = (List<VolunteerBean>)volunteerBus.getAllLeaves().getResponseData();

    this.getVolunteerCount(dashBoardBean);
    this.getWorkingHours(dashBoardBean);

    dashBoardBean.setTrainCount(getTrainCount());
    dashBoardBean.setCourseCount(getCourseCount());

    return SUCCESS;
  }

  private void getVolunteerCount(DashBoardBean dashBoardBean) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.DAY_OF_MONTH, 1);
    cal.set(Calendar.HOUR, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    Date monthStart = cal.getTime();

    long volunteerCount = 0;
    long bindingCount = 0;

    long newBindingCount = 0;
    long newUnbindingCount = 0;

    long registerClient = 0;
    long registerWechat = 0;

    if(null != volunteerBeanList) {
      for(VolunteerBean bean : volunteerBeanList) {
        if(bean.getStatus() == VolunteerBean.INTERVIEWED && bean.getIsDeleted() == false) {
          volunteerCount ++ ;

          if(StringUtils.isNotEmpty(bean.getWechat())) {
            bindingCount ++;
          }

          if(null != bean.getCreateTime() && !bean.getCreateTime().before(monthStart)) {
            if(StringUtils.isNotEmpty(bean.getWechat())) {
              newBindingCount ++;
            } else {
              newUnbindingCount ++;
            }
          }

          if(bean.getRegisterFrom() == VolunteerBean.REGISTER_HOSPITAL) {
            registerClient ++ ;
          } else if(bean.getRegisterFrom() == VolunteerBean.REGISTER_WECHAT) {
            registerWechat ++ ;
          }
        }
      }
    }

    dashBoardBean.setVolunteerCount(volunteerCount);
    dashBoardBean.setBindingCount(bindingCount);
    dashBoardBean.setNewBindingCount(newBindingCount);
    dashBoardBean.setNewUnbindingCount(newUnbindingCount);
    dashBoardBean.setRegisterByClient(registerClient);
    dashBoardBean.setRegisterByWechat(registerWechat);
  }

  private long getCourseCount() {
    TableQueryVo query = new TableQueryVo();
    query.getFilter().put("isDeleted_!=", true);
    return courseBusiness.getCount(query);
  }

  private long getTrainCount() {
    TableQueryVo query = new TableQueryVo();
    query.getFilter().put("isDeleted_!=", true);
    return vtcBusiness.getCount(query);
  }

  private void getWorkingHours(DashBoardBean dashBoardBean) {
    Calendar cal = Calendar.getInstance();

    List dataList = new ArrayList();

    cal = initDate(cal);
    Date start = cal.getTime();
    cal.add(Calendar.YEAR, 1);
    Date end = cal.getTime();

    List<UserServiceBean> beanList = userServiceBus.queryUserServices(null, null, start, end);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    Map<String, Long> valueMap = this.formatData(beanList, sdf);
    cal.setTime(start);
    for(int index = 0; index < 12; index ++) {
      String time = sdf.format(cal.getTime());
      Long value = valueMap.get(time);
      Map monthMap = new HashMap();
      monthMap.put("time", time);
      monthMap.put("hours", (value!=null?value:0l)/ 3600000);
      dataList.add(monthMap);
      cal.add(Calendar.MONTH, 1);
    }
    dashBoardBean.setMonthlyWorkingHours(JSONArray.fromObject(dataList).toString());
  }

  private Calendar initDate(Calendar cal) {
    cal.set(Calendar.MILLISECOND, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.HOUR, 0);
    cal.set(Calendar.DAY_OF_MONTH, 1);
    cal.set(Calendar.MONTH, 0);
    return cal;
  }

  private Map<String, Long> formatData(List<UserServiceBean> beanList, SimpleDateFormat sdf){
    Map<String, Long> result = new HashMap<String, Long>();
    for(UserServiceBean bean: beanList) {
      String time = sdf.format(bean.getCheckOutTime());
      Long value = (Long)result.get(time);
      if(null ==  value) {
        value = 0l;
      }
      result.put(time, (bean.getCheckOutTime().getTime() - bean.getCheckInTime().getTime() + value ));
    }
    return result;
  }

  public DashBoardBean getDashBoardBean() {
    return dashBoardBean;
  }

  public void setDashBoardBean(DashBoardBean dashBoardBean) {
    this.dashBoardBean = dashBoardBean;
  }

}
