package actions.backend;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;
import vo.report.ActiveTimeReportVo;
import vo.table.TableDataVo;
import vo.table.TableQueryVo;
import bl.beans.ServicePlaceBean;
import bl.beans.UserServiceBean;
import bl.beans.VolunteerBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.ServicePlaceBusiness;
import bl.mongobus.UserServiceBusiness;
import bl.mongobus.VolunteerBusiness;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangronghua on 14-3-16.
 */
public class BackendTimeReportAction extends BaseBackendAction{
  ServicePlaceBusiness servicePlaceBus = (ServicePlaceBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_SERVICEPLACE);
  UserServiceBusiness userServiceBus = (UserServiceBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_USERSERVICE);
  VolunteerBusiness volunteerBus = (VolunteerBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_VOLUNTEER);

  List<ServicePlaceBean> servicePlaces = null;
  List<ActiveTimeReportVo> activeTimeReportVos;

  private String name;
  private String code;
  private String servicePlaceId;
  private boolean day = true;
  private boolean month = true;
  private boolean year = true;

  private int selectYear;

  private String selectYearDate;
  private List<Integer> yearList;
  private String jsonData;
  private String jsonLabels;
  private String jsonYKeys;


  public String getActiveReport(){
    servicePlaces = (List<ServicePlaceBean>)servicePlaceBus.getAllLeaves().getResponseData();
    return SUCCESS;
  }

  public String getActiveReportData(){
    List<ActiveTimeReportVo> result = new ArrayList<ActiveTimeReportVo>();
    // get filters
    Map<String, String[]> filterMap = getModel().getFilter();
    String[] names = filterMap.get("name");
    if(null != names && names.length > 0) {
      name = names[0];
    }

    String[] codes = filterMap.get("code");
    if(null != codes && codes.length > 0) {
      code = codes[0];
    }

    String[] servicePlaces = filterMap.get("servicePlaceId");

    List<String> serviceIdList = new ArrayList<String>();
    if(null != servicePlaces && servicePlaces.length > 0) {
      servicePlaceId = servicePlaces[0];
      serviceIdList.add(servicePlaceId);
    }

//    Pattern pattern = Pattern.compile("^.*" + personName+ ".*$", // as SQL:  like " '%" + personName + "%' "
//    Pattern.CASE_INSENSITIVE);
//    query.filter("name", pattern);

    long count = volunteerBus.getCount(getModel());

    List<VolunteerBean> beanList = (List<VolunteerBean>)volunteerBus.query(getModel()).getAaData();
    List<String> idList = new ArrayList<String>();
    for(VolunteerBean bean: beanList) {
      idList.add(bean.getId());
    }
    Map<String, Map> beanMap = userServiceBus.statisticTime(userServiceBus.getLeavesByUserIds(idList, serviceIdList));

    for(VolunteerBean bean: beanList) {
      ActiveTimeReportVo vo = new ActiveTimeReportVo();
      vo.setName(bean.getName());
      vo.setCode(bean.getCode());
      Map hourMap = beanMap.get(bean.getId());
      if(null != hourMap) {
        Long dayHours = (Long)hourMap.get(Calendar.DAY_OF_MONTH);
        Long monthHours = (Long)hourMap.get(Calendar.MONTH);
        Long yearHours = (Long)hourMap.get(Calendar.YEAR);
        Long totalHours = (Long)hourMap.get(Calendar.ALL_STYLES);
        vo.setDayHours((int)(dayHours!=null?dayHours:0l)/3600000);
        vo.setMonthHours((int) (monthHours != null ? monthHours : 0l) / 3600000);
        vo.setYearHours((int) (yearHours != null ? yearHours : 0l) /3600000);
        vo.setTotalHours((int) (totalHours != null ? totalHours : 0l) / 3600000);
      }
      result.add(vo);
    }
    
    TableDataVo table = new TableDataVo();
    table.setsEcho(getModel().getSEcho());
    table.setiTotalDisplayRecords(count);
    table.setiTotalRecords(count);
    table.setAaData(result);
    // json
    JSONObject jsonObject = JSONObject.fromObject(table);
    writeJson(jsonObject);
    return null;
  }

  public String getUserReport(){
    return SUCCESS;
  }

  public String getServiceReport(){
    return SUCCESS;
  }

  public String getUserDailyReport() throws ParseException {
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat parsesdf = new SimpleDateFormat("yyyy-MM");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    List dataList = new ArrayList();
    List<String> labelList = new ArrayList<String>();
    List<String> yKeysList = new ArrayList<String>();

    if(StringUtils.isEmpty(selectYearDate)) {
      selectYearDate = parsesdf.format(new Date());
    }

    if((StringUtils.isNotEmpty(name) || StringUtils.isNotEmpty(code)) && StringUtils.isNotEmpty(selectYearDate)) {
      VolunteerBean volunteer = null;
      if(StringUtils.isNotEmpty(code)) {
        volunteer = volunteerBus.getVolunteerBeanByCode(code);
        if(null != volunteer) {
          name = volunteer.getName();
        }
      }
      if(null == volunteer && StringUtils.isNotEmpty(name)) {
        volunteer = (VolunteerBean)volunteerBus.getLeafByName(name).getResponseData();
        if(null != volunteer) {
          code = "";
        }
      }

      if(null != volunteer) {
        //set label
        labelList.add(volunteer.getName());
        yKeysList.add(volunteer.getName());
        //set ykeys
        Date yearDate = parsesdf.parse(selectYearDate);
        cal.setTime(yearDate);
        Date start = cal.getTime();
        cal.add(Calendar.MONTH, 1);
        Date end = cal.getTime();
        List<String> userIdList = new ArrayList<String>();
        userIdList.add(volunteer.getId());
        List<UserServiceBean> beanList = userServiceBus.queryUserServices(userIdList, null, start, end);
        Map<String, Long> valueMap = this.formatData(beanList, sdf);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        int daySize = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(start);
        for(int index = 0; index < daySize; index ++) {
          String time = sdf.format(cal.getTime());
          Long value = valueMap.get(time);
          Map monthMap = new HashMap();
          monthMap.put("time", time);
          monthMap.put(volunteer.getName(), (value!=null?value:0l)/ 3600000);
          dataList.add(monthMap);
          cal.add(Calendar.DAY_OF_MONTH, 1);
        }
      }
    }

    jsonData = JSONArray.fromObject(dataList).toString();
    jsonLabels = JSONArray.fromObject(labelList).toString();
    jsonYKeys = JSONArray.fromObject(yKeysList).toString();

    if(null != labelList && labelList.size() == 0) {
      super.addActionMessage("没有查询到相关记录！");
    }
    return SUCCESS;
  }

  public String getUserMonthlyReport(){
    Calendar cal = Calendar.getInstance();
    yearList = new ArrayList<Integer>();
    int y = cal.get(Calendar.YEAR);
    for(int index = 0; index < 10; index ++) {
      yearList.add(y-index);
    }

    List dataList = new ArrayList();
    List<String> labelList = new ArrayList<String>();
    List<String> yKeysList = new ArrayList<String>();

    if((StringUtils.isNotEmpty(name) || StringUtils.isNotEmpty(code)) && selectYear > 0) {
      VolunteerBean volunteer = null;
      if(StringUtils.isNotEmpty(code)) {
        volunteer = volunteerBus.getVolunteerBeanByCode(code);
        if(null != volunteer) {
          name = volunteer.getName();
        }
      }
      if(null == volunteer && StringUtils.isNotEmpty(name)) {
        volunteer = (VolunteerBean)volunteerBus.getLeafByName(name).getResponseData();
        if(null != volunteer) {
          code = "";
        }
      }
      if(null != volunteer) {
        name = volunteer.getName();
        //set label
        labelList.add(volunteer.getName());
        yKeysList.add(volunteer.getName());
        //set ykeys
        cal = initDate(cal);
        cal.set(Calendar.YEAR, selectYear);
        Date start = cal.getTime();
        cal.add(Calendar.YEAR, 1);
        Date end = cal.getTime();

        List<String> userIdList = new ArrayList<String>();
        userIdList.add(volunteer.getId());
        List<UserServiceBean> beanList = userServiceBus.queryUserServices(userIdList, null, start, end);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Map<String, Long> valueMap = this.formatData(beanList, sdf);
        cal.setTime(start);
        for(int index = 0; index < 12; index ++) {
          String time = sdf.format(cal.getTime());
          Long value = valueMap.get(time);
          Map monthMap = new HashMap();
          monthMap.put("time", time);
          monthMap.put(volunteer.getName(), (value!=null?value:0l)/ 3600000);
          dataList.add(monthMap);
          cal.add(Calendar.MONTH, 1);
        }
      }
    }

    jsonData = JSONArray.fromObject(dataList).toString();
    jsonLabels = JSONArray.fromObject(labelList).toString();
    jsonYKeys = JSONArray.fromObject(yKeysList).toString();

    if(null != labelList && labelList.size() == 0) {
      super.addActionMessage("没有查询到相关记录！");
    }

    return SUCCESS;
  }

  public String export() throws IOException {
    List<ActiveTimeReportVo> result = new ArrayList<ActiveTimeReportVo>();

    List<String> serviceIdList = new ArrayList<String>();
    if(StringUtils.isNotEmpty(servicePlaceId)) {
      serviceIdList.add(servicePlaceId);
    }

    List<VolunteerBean> beanList = (List<VolunteerBean>)volunteerBus.queryVolunteers(name, code);

    List<String> idList = new ArrayList<String>();
    for(VolunteerBean bean: beanList) {
      idList.add(bean.getId());
    }

    Map<String, Map> beanMap = userServiceBus.statisticTime(userServiceBus.getLeavesByUserIds(idList, serviceIdList));

    for(VolunteerBean bean: beanList) {
      ActiveTimeReportVo vo = new ActiveTimeReportVo();
      vo.setName(bean.getName());
      vo.setCode(bean.getCode());
      Map hourMap = beanMap.get(bean.getId());
      if(null != hourMap) {
        Long dayHours = (Long)hourMap.get(Calendar.DAY_OF_MONTH);
        Long monthHours = (Long)hourMap.get(Calendar.MONTH);
        Long yearHours = (Long)hourMap.get(Calendar.YEAR);
        Long totalHours = (Long)hourMap.get(Calendar.ALL_STYLES);
        vo.setDayHours((int)(dayHours!=null?dayHours:0l)/3600000);
        vo.setMonthHours((int) (monthHours != null ? monthHours : 0l) / 3600000);
        vo.setYearHours((int) (yearHours != null ? yearHours : 0l) /3600000);
        vo.setTotalHours((int) (totalHours != null ? totalHours : 0l) / 3600000);
      }
      result.add(vo);
    }

    HttpServletResponse response = ServletActionContext.getResponse();
    response.setContentType("application/msexcel;charset=UTF-8");  //两种方法都可以
    String fileName = "ActiveTimeReport.xls";
    response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

    //客户端不缓存
    response.addHeader("Pargam", "no-cache");
    response.addHeader("Cache-Control", "no-cache");

    HSSFWorkbook workbook = exportExcel(result);
    workbook.write(response.getOutputStream());

    response.getOutputStream().flush();
    response.getOutputStream().close();
    return null;
  }

  private HSSFWorkbook exportExcel(List<ActiveTimeReportVo> list) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    HSSFWorkbook book = new HSSFWorkbook();

    Sheet sheet = book.createSheet("工时统计" + sdf.format(new Date()));
    Row row = sheet.createRow(0);
    row.createCell(0).setCellValue("用户名");
    row.createCell(1).setCellValue("工号");
    row.createCell(2).setCellValue("当日累计工时");
    row.createCell(3).setCellValue("当月累计工时");
    row.createCell(4).setCellValue("当年累计工时");
    row.createCell(5).setCellValue("总累计工时");
    CellStyle sty = book.createCellStyle();
    int index = 1;
    for (ActiveTimeReportVo vo : list) {
      row = sheet.createRow(index);
      row.createCell(0).setCellValue(vo.getName());
      row.createCell(1).setCellValue(vo.getCode());
      row.createCell(2).setCellValue(vo.getDayHours());
      row.createCell(3).setCellValue(vo.getMonthHours());
      row.createCell(4).setCellValue(vo.getYearHours());
      row.createCell(5).setCellValue(vo.getTotalHours());
      index ++;
    }
    return book;
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

  public String getServiceDailyReport(){
    return SUCCESS;
  }

  public String getServiceMonthlyReport(){
    return SUCCESS;
  }

  public List<ServicePlaceBean> getServicePlaces() {
    return servicePlaces;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name.trim();
  }

  public boolean isDay() {
    return day;
  }

  public void setDay(boolean day) {
    this.day = day;
  }

  public boolean isMonth() {
    return month;
  }

  public void setMonth(boolean month) {
    this.month = month;
  }

  public boolean isYear() {
    return year;
  }

  public void setYear(boolean year) {
    this.year = year;
  }

  public String getServicePlaceId() {
    return servicePlaceId;
  }

  public void setServicePlaceId(String servicePlaceId) {
    this.servicePlaceId = servicePlaceId;
  }


  public void setServicePlaces(List<ServicePlaceBean> servicePlaces) {
    this.servicePlaces = servicePlaces;
  }

  public List<ActiveTimeReportVo> getActiveTimeReportVos() {
    return activeTimeReportVos;
  }

  public void setActiveTimeReportVos(List<ActiveTimeReportVo> activeTimeReportVos) {
    this.activeTimeReportVos = activeTimeReportVos;
  }



  public String getJsonYKeys() {
    return jsonYKeys;
  }

  public void setJsonYKeys(String jsonYKeys) {
    this.jsonYKeys = jsonYKeys;
  }

  public int getSelectYear() {
    return selectYear;
  }

  public void setSelectYear(int selectYear) {
    this.selectYear = selectYear;
  }

  public List<Integer> getYearList() {
    return yearList;
  }

  public void setYearList(List<Integer> yearList) {
    this.yearList = yearList;
  }

  public String getJsonData() {
    return jsonData;
  }

  public void setJsonData(String jsonData) {
    this.jsonData = jsonData;
  }

  public String getJsonLabels() {
    return jsonLabels;
  }

  public void setJsonLabels(String jsonLabels) {
    this.jsonLabels = jsonLabels;
  }

  public String getSelectYearDate() {
    return selectYearDate;
  }

  public void setSelectYearDate(String selectYearDate) {
    this.selectYearDate = selectYearDate;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code.trim();
  }
}
