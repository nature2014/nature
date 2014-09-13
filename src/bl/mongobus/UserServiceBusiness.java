package bl.mongobus;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import bl.beans.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import bl.common.BeanContext;
import bl.common.BusinessResult;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.MongoDBConnectionFactory;
import util.EarthGpsDistanceUtil;
import wechat.request.LocationEvent;
import wechat.utils.LocationCache;

/**
 * Created by wangronghua on 14-3-15.
 */
public class UserServiceBusiness extends MongoCommonBusiness<BeanContext, UserServiceBean> {

    private static final ServicePlaceBusiness servicePlaceBus = (ServicePlaceBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_SERVICEPLACE);
    private static final  ActiveUserBusiness activeUserBus = (ActiveUserBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_ACTIVEUSER);

    private static final VolunteerBusiness userBus = (VolunteerBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_VOLUNTEER);
    private static final TrainCourseServicePlaceBusiness tcspBus = (TrainCourseServicePlaceBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_TRAINCOURSESERVICEPLACE);
    private static final TrainCourseBusiness tcBus = (TrainCourseBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_TRAINCOURSE);
    private static final  VolunteerTrainCourseBusiness vtcBus = (VolunteerTrainCourseBusiness)SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_VOLUNTEERTRAINCOURSE);

  private static Logger LOG = LoggerFactory.getLogger(UserServiceBusiness.class);

  public UserServiceBusiness() {
    this.clazz = UserServiceBean.class;
  }

  public BusinessResult getOrderedLeavesByUserId(String userId, int size) {

    Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
    BusinessResult br = new BusinessResult();

    List<UserServiceBean> beanList = dc.find(this.clazz, "userId", userId).order("-checkOutTime").limit(size).asList();

    VolunteerBean user = null;
    if (StringUtils.isNotEmpty(userId)) {
      user = (VolunteerBean) userBus.getLeaf(userId).getResponseData();
    }

    for (UserServiceBean bean : beanList) {
      bean.setUserBean(user);
      String servicePlaceId = bean.getServicePlaceId();
      if (StringUtils.isNotEmpty(servicePlaceId)) {
        bean.setServicePlaceBean((ServicePlaceBean) servicePlaceBus.getLeaf(servicePlaceId).getResponseData());
      }
    }
    br.setResponseData(beanList);
    return br;
  }

  public List<UserServiceBean> getLeavesByUserIds(List<String> userIdList, List<String> serviceIdList) {
    return queryUserServices(userIdList, serviceIdList, null, null);
  }

  public List<UserServiceBean> queryUserServices(List<String> userIdList, List<String> serviceIdList, Date start, Date end) {
    Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
    Query query = dc.createQuery(this.clazz);
    if (null != userIdList && userIdList.size() > 0) {
      query = query.filter("userId in", userIdList);
    }
    if (null != serviceIdList && serviceIdList.size() > 0) {
      query = query.filter("servicePlaceId in", serviceIdList);
    }
    if (null != start) {
      query = query.filter("checkInTime >=", start);
    }
    if (null != end) {
      query = query.filter("checkOutTime <", end);
    }
    List<UserServiceBean> beanList = query.asList();
    return beanList;
  }

  /**
   * get serviced hours of current month
   * 
   * @param volunnteerId
   * @return
   */
  public double getServicedHoursForCurrentMonth(String volunnteerId) {
    Calendar startCalendar = Calendar.getInstance();
    startCalendar.set(Calendar.MILLISECOND, 0);
    startCalendar.set(Calendar.SECOND, 0);
    startCalendar.set(Calendar.MINUTE, 0);
    startCalendar.set(Calendar.HOUR, 0);
    startCalendar.set(Calendar.DAY_OF_MONTH, 1);

    Calendar endCalendar = Calendar.getInstance();
    startCalendar.set(Calendar.MILLISECOND, 0);
    startCalendar.set(Calendar.SECOND, 0);
    startCalendar.set(Calendar.MINUTE, 0);
    startCalendar.set(Calendar.HOUR, 0);
    startCalendar.set(Calendar.DAY_OF_MONTH, 1);
    endCalendar.set(Calendar.MONTH, startCalendar.get(Calendar.MONTH) + 1);

    return getVolunteerServiceHours(volunnteerId, startCalendar, endCalendar);
  }

  /**
   * get time of current month
   * 
   * @param volunnteerId
   * @return
   */
  public double getServicedHoursForCurrentYear(String volunnteerId) {
    Calendar startCalendar = Calendar.getInstance();
    startCalendar.set(Calendar.MILLISECOND, 0);
    startCalendar.set(Calendar.SECOND, 0);
    startCalendar.set(Calendar.MINUTE, 0);
    startCalendar.set(Calendar.HOUR, 0);
    startCalendar.set(Calendar.MONTH, 0);
    startCalendar.set(Calendar.DAY_OF_MONTH, 1);

    Calendar endCalendar = Calendar.getInstance();
    startCalendar.set(Calendar.MILLISECOND, 0);
    startCalendar.set(Calendar.SECOND, 0);
    startCalendar.set(Calendar.MINUTE, 0);
    startCalendar.set(Calendar.HOUR, 0);
    startCalendar.set(Calendar.MONTH, 0);
    startCalendar.set(Calendar.DAY_OF_MONTH, 1);
    startCalendar.set(Calendar.YEAR, startCalendar.get(Calendar.YEAR) + 1);

    return getVolunteerServiceHours(volunnteerId, startCalendar, endCalendar);
  }

  public double getVolunteerServiceHours(String volunnteerId, Calendar startCalendar, Calendar endCalendar) {
    Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
    Query query = dc.createQuery(this.clazz);

    query = query.filter("userId =", volunnteerId);

    query = query.filter("checkInTime >=", startCalendar.getTime());
    query = query.filter("checkOutTime <", endCalendar.getTime());
    List<UserServiceBean> userServiceList = query.asList();

    double time = 0;
    if (userServiceList != null) {
      for (UserServiceBean userServiceBean : userServiceList) {
        time += (userServiceBean.getCheckOutTime().getTime() - userServiceBean.getCheckInTime().getTime());
      }
    }
    time = time / 3600000;

    BigDecimal b = new BigDecimal(time);
    time = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

    return time;
  }

  public BusinessResult getLeavesByUserId(String userId) {
    BusinessResult br = new BusinessResult();
    Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());

    List<UserServiceBean> beanList = dc.find(this.clazz, "userId", userId).asList();

    VolunteerBean user = null;
    if (StringUtils.isNotEmpty(userId)) {
      user = (VolunteerBean) userBus.getLeaf(userId).getResponseData();
    }

    for (UserServiceBean bean : beanList) {
      bean.setUserBean(user);
      String servicePlaceId = bean.getServicePlaceId();
      if (StringUtils.isNotEmpty(servicePlaceId)) {
        bean.setServicePlaceBean((ServicePlaceBean) servicePlaceBus.getLeaf(servicePlaceId).getResponseData());
      }
    }
    br.setResponseData(beanList);
    return br;
  }

  public BusinessResult checkIn(String userId, String servicePlaceId) {
    return this.checkIn(userId, servicePlaceId, false);
  }
  public BusinessResult checkIn(String userId, String servicePlaceId, boolean isWechat) {
    VolunteerBean volunteer = (VolunteerBean)userBus.getLeaf(userId).getResponseData();
    if(null != volunteer) {
      ActiveUserBean bean = new ActiveUserBean();
      bean.set_id(ObjectId.get());
      bean.setUserId(userId);
      bean.setServicePlaceId(servicePlaceId);
      bean.setCheckInTime(new Date());
      if(isWechat){
        bean.setStatus(ActiveUserBean.STATUS_WECHAT);
        if(null != volunteer.getOpenID()) {
          LocationEvent event = LocationCache.getLocation(volunteer.getOpenID());
          if(null != event) {
            bean.setLatitude(event.getLatitude());
            bean.setLongitude(event.getLongitude());
            bean.setPrecision(event.getPrecision());
            ServicePlaceBean sp = (ServicePlaceBean) servicePlaceBus.getLeaf(servicePlaceId).getResponseData();
            if(sp.getParentid()!=null && !sp.getParentid().isEmpty()){
             sp = (ServicePlaceBean) servicePlaceBus.getLeaf(sp.getParentid()).getResponseData();
            }
            if(sp!=null){
                //计算距离和显示描述信息
                double lat = Double.valueOf(bean.getLatitude());
                double loi = Double.valueOf(bean.getLongitude());
                double distance = EarthGpsDistanceUtil.getDistance(lat, loi, sp.getLatitude(), sp.getLongitude());
                bean.setDistance(distance);
                bean.setDescription("附近大约"+ distance +"公里");
            }
          }
        }
      }
      this.createLeaf(bean);
    }
    return new BusinessResult();
  }

  public BusinessResult checkOut(String userId) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    ActiveUserBean activeUserBean = (ActiveUserBean) activeUserBus.getActiveUserByUserId(userId).getResponseData();
    if (null != activeUserBean) {
      VolunteerBean volunteer = (VolunteerBean)userBus.getLeaf(userId).getResponseData();
      ServicePlaceBean sp =
          (ServicePlaceBean) servicePlaceBus.getLeaf(activeUserBean.getServicePlaceId()).getResponseData();

      UserServiceBean usBean = new UserServiceBean();
      PropertyUtils.copyProperties(usBean, activeUserBean);
      usBean.setCheckInMethod(activeUserBean.getStatus());
      usBean.setUserCode(volunteer.getCode());
      usBean.setUserName(volunteer.getName());
      if(null != sp) {
        usBean.setServicePlaceName(sp.getName());
      }
      if(null != volunteer.getOpenID()) {
        LocationEvent event = LocationCache.getLocation(volunteer.getOpenID());
        if(null != event) {
          usBean.setCheckOutLatitude(event.getLatitude());
          usBean.setCheckOutLongitude(event.getLongitude());
          usBean.setCheckOutPrecision(event.getPrecision());
          if(sp.getParentid()!=null && !sp.getParentid().isEmpty()){
            sp = (ServicePlaceBean) servicePlaceBus.getLeaf(sp.getParentid()).getResponseData();
          }
          if(sp!=null){
            //计算距离和显示描述信息
            double lat = Double.valueOf(event.getLatitude());
            double loi = Double.valueOf(event.getLongitude());
            double distance = EarthGpsDistanceUtil.getDistance(lat, loi, sp.getLatitude(), sp.getLongitude());
            usBean.setCheckOutDistance(distance);
            usBean.setCheckOutDescription("附近大约" + distance + "公里");
          }
        }
      }
      usBean.set_id(ObjectId.get());
      Calendar cal = Calendar.getInstance();
      Date checkOutTime = cal.getTime();
      usBean.setCheckOutTime(checkOutTime);

      cal.set(Calendar.HOUR_OF_DAY, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MILLISECOND, 0);
      // 如果签出，签入不在同一天，则设置记录为异常记录，置delete flag为true
      if(cal.after(usBean.getCheckInTime())) {
        usBean.setIsDeleted(true);
      }
      this.createLeaf(usBean);

      activeUserBus.deleteLeaf(activeUserBean.getId(), true);
    }
    return new BusinessResult();
  }

  public List<ServicePlaceBean> getAvailableServicePlaces(String volunteerId) {
    Map<String, ServicePlaceBean> filterMap = new HashMap<String, ServicePlaceBean>();
    List<TrainCourseBean> courses = vtcBus.getPassedTrainCourseByVolunteerId(volunteerId);
    for(TrainCourseBean course: courses) {
      List<ServicePlaceBean> places = tcspBus.getServicePlacesByTrainCourseId(course.getId());
      for(ServicePlaceBean bean: places) {
        filterMap.put(bean.getId(), bean);
      }
    }
    return Arrays.asList(filterMap.values().toArray(new ServicePlaceBean[0]));

  }

  /**
   * 
   * @param beanList
   * @param sdfString
   *          could be: yyyy-MM-dd, yyyy-MM, yyyy
   * @param baseTime
   *          the base compare time
   * @return
   */
  public Map<String, Map> statisticTime(List<UserServiceBean> beanList, String sdfString, String baseTime) {
    Map<String, Map> resultMap = new HashMap<String, Map>();
    SimpleDateFormat sdf = new SimpleDateFormat(sdfString);

    for (UserServiceBean bean : beanList) {
      Map userMap = resultMap.get(bean.getUserId());
      if (null == userMap) {
        userMap = new HashMap<String, Map>();
        userMap.put("user", bean.getUserBean());
        resultMap.put(bean.getUserId(), userMap);
      }
      String formatedTime = sdf.format(bean.getCheckOutTime());
      if (formatedTime.startsWith(baseTime)) {
        long time = bean.getCheckOutTime().getTime() - bean.getCheckInTime().getTime();
        if (null == userMap.get(formatedTime)) {
          userMap.put(formatedTime, 0l);
        }
        userMap.put(formatedTime, (long) userMap.get(formatedTime) + time);
      }
    }
    return resultMap;
  }

  public Map<String, Map> statisticTime(List<UserServiceBean> beanList) {
    Map<String, Map> resultMap = new HashMap<String, Map>();
    SimpleDateFormat day_sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat month_sdf = new SimpleDateFormat("yyyy-MM");
    SimpleDateFormat year_sdf = new SimpleDateFormat("yyyy");

    Date now = new Date();
    String baseDay = day_sdf.format(now);
    String baseMonth = month_sdf.format(now);
    String baseYear = year_sdf.format(now);

    for (UserServiceBean bean : beanList) {
      Map userMap = resultMap.get(bean.getUserId());
      if (null == userMap) {
        userMap = new HashMap();
        userMap.put("user", bean.getUserBean());
        userMap.put(Calendar.DAY_OF_MONTH, 0l);
        userMap.put(Calendar.MONTH, 0l);
        userMap.put(Calendar.YEAR, 0l);
        userMap.put(Calendar.ALL_STYLES, 0l);
        resultMap.put(bean.getUserId(), userMap);
      }

      if (baseDay.equals(day_sdf.format(bean.getCheckOutTime()))) {
        long time = bean.getCheckOutTime().getTime() - bean.getCheckInTime().getTime();

        userMap.put(Calendar.DAY_OF_MONTH, (long) userMap.get(Calendar.DAY_OF_MONTH) + time);
        userMap.put(Calendar.MONTH, (long) userMap.get(Calendar.MONTH) + time);
        userMap.put(Calendar.YEAR, (long) userMap.get(Calendar.YEAR) + time);
        userMap.put(Calendar.ALL_STYLES, (long) userMap.get(Calendar.ALL_STYLES) + time);

      } else if (baseMonth.equals(month_sdf.format(bean.getCheckOutTime()))) {
        long time = bean.getCheckOutTime().getTime() - bean.getCheckInTime().getTime();

        userMap.put(Calendar.MONTH, (long) userMap.get(Calendar.MONTH) + time);
        userMap.put(Calendar.YEAR, (long) userMap.get(Calendar.YEAR) + time);
        userMap.put(Calendar.ALL_STYLES, (long) userMap.get(Calendar.ALL_STYLES) + time);

      } else if (baseYear.equals(year_sdf.format(bean.getCheckOutTime()))) {
        long time = bean.getCheckOutTime().getTime() - bean.getCheckInTime().getTime();

        userMap.put(Calendar.YEAR, (long) userMap.get(Calendar.YEAR) + time);
        userMap.put(Calendar.ALL_STYLES, (long) userMap.get(Calendar.ALL_STYLES) + time);

      } else {
        long time = bean.getCheckOutTime().getTime() - bean.getCheckInTime().getTime();

        userMap.put(Calendar.ALL_STYLES, (long) userMap.get(Calendar.ALL_STYLES) + time);

      }
    }

    return resultMap;
  }

  public void deleteRecordsByVolunteerId(String volunteerId){
    super.updateRecordsByCondition("isDeleted", true, "userId", volunteerId);
  }

  public void updateVolunteerByVolunteerId(String volunteerId, String code, String name){
    super.updateRecordsByCondition("userCode", code, "userId", volunteerId);
    super.updateRecordsByCondition("userName", name, "userId", volunteerId);
  }

  public void updateServicePlaceNameByServicePlaceId(String servicePlaceId, String name){
    super.updateRecordsByCondition("servicePlaceName", name, "servicePlaceId", servicePlaceId);
  }
}
