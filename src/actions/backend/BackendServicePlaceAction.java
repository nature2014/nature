package actions.backend;

import bl.beans.ServicePlaceBean;
import bl.beans.TrainCourseServicePlaceBean;
import bl.constants.BusTieConstant;
import bl.exceptions.MiServerException;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.ServicePlaceBusiness;

import bl.mongobus.TrainCourseServicePlaceBusiness;
import com.opensymphony.xwork2.ActionSupport;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;
import org.bson.types.ObjectId;

import util.ServerContext;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;
import vo.table.TableQueryVo;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by peter on 14-3-14.
 */
public class BackendServicePlaceAction extends BaseBackendAction<ServicePlaceBusiness> {
    List<ServicePlaceBean> servicePlaces = null;
    ServicePlaceBean servicePlace = null;
    ServicePlaceBusiness sp = (ServicePlaceBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_SERVICEPLACE);
    private static final TrainCourseServicePlaceBusiness tcspBus = (TrainCourseServicePlaceBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_TRAINCOURSESERVICEPLACE);
    private int type = 0;
    private String[] serviceicons = null;

    private List<ServicePlaceBean> innerHospital = null;

    public List<ServicePlaceBean> getInnerHospital() {
        return innerHospital;
    }

    public void setInnerHospital(List<ServicePlaceBean> innerHospital) {
        this.innerHospital = innerHospital;
    }

    public String[] getServiceicons() {
        return serviceicons;
    }

    public void setServiceicons(String[] serviceicons) {
        this.serviceicons = serviceicons;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private static File FILEPATH = null;
    private static String contextPath = null;

    static {
        LOG.debug("初始化图标路径");
        String catalinaHome = System.getProperty("catalina.home");
        FILEPATH = new File(catalinaHome + File.separator + "upload" + File.separator + "serviceplace");
        if (!FILEPATH.exists()) {
            //确保目录存在
            FILEPATH.mkdir();
        }
        LOG.debug("初始化应用服务器上下文地址");
        contextPath = ServletActionContext.getServletContext().getContextPath();
    }
    public String servicePlaceList() {
        HashMap<String,Integer> filterByType = new HashMap<String,Integer>();
        filterByType.put("type",this.type);
        this.servicePlaces = (List<ServicePlaceBean>) sp.queryDataByCondition(filterByType, null);
        return ActionSupport.SUCCESS;
    }

    public String servicePlaceDelete() {
        if (this.getId() != null) {
            Map<String,String> filterMap = new HashMap<String,String>();
            filterMap.put("parentid_=",this.getId());
            filterMap.put("isDeleted","false");
            List<ServicePlaceBean> list = sp.queryDataByCondition(filterMap,null);
            if(list.size()>0){
                addActionError("该地理位置被办公位置引用，请先解除关联关系");
            }
            filterMap = new HashMap<String, String>();
            filterMap.put("servicePlaceId",this.getId());
            List<TrainCourseServicePlaceBean> listService = tcspBus.queryDataByCondition(filterMap, null);
            if(listService.size()>0){
                addActionError("该地理位置被培训课程引用，请先解除关联关系");
            }
            if(this.hasErrors()){
                return ActionSupport.ERROR;
            }
            sp.deleteLeaf(this.getId());
        }
        return ActionSupport.SUCCESS;
    }

    public String servicePlaceAddEdit() {
        //read file list
        File iconList = FILEPATH;
        if(iconList.isDirectory() && iconList.exists()){
            String[] list = iconList.list();
            //convert to virtual path within tomcat service.
            serviceicons = new String[list.length];
            String vitual = contextPath + "/upload/getImage.action?getfile=serviceplace/";
            for (int i = 0; i < list.length; i++) {
                serviceicons[i] = vitual+ list[i];
            }
        }
        if (this.getId() != null) {
            String id = this.getId();
            this.servicePlace = (ServicePlaceBean) this.sp.getLeaf(id).getResponseData();
        }
        Map<String,String> filterMap = new HashMap<String,String>();
        filterMap.put("area_=","0");
        filterMap.put("type_=","1");
        filterMap.put("isDeleted","false");
        this.innerHospital = sp.queryDataByCondition(filterMap,null);

        return ActionSupport.SUCCESS;
    }

    public String servicePlaceSubmit() throws Exception{
        String id = this.servicePlace.getId();
        try {
            if (id != null && !id.isEmpty()) {
                ServicePlaceBean originalBean = (ServicePlaceBean) this.sp.getLeaf(id).getResponseData();
                ServicePlaceBean newBean = (ServicePlaceBean) originalBean.clone();
                BeanUtils.copyProperties(newBean, this.servicePlace);
                sp.updateLeaf(originalBean, newBean);
            } else {
                this.servicePlace.set_id(ObjectId.get());
                this.sp.createLeaf(this.servicePlace);
            }
        } catch (MiServerException miEx) {
            //read file list
            File iconList = FILEPATH;
            if (iconList.isDirectory() && iconList.exists()) {
                String[] list = iconList.list();
                //convert to virtual path within tomcat service.
                serviceicons = new String[list.length];
                String vitual = contextPath + "/upload/serviceplace/";
                for (int i = 0; i < list.length; i++) {
                    serviceicons[i] = vitual + list[i];
                }
            }
            Map<String, String> filterMap = new HashMap<String, String>();
            filterMap.put("area_=", "0");
            filterMap.put("type_=", "1");
            filterMap.put("isDeleted", "false");
            this.innerHospital = sp.queryDataByCondition(filterMap, null);
            throw miEx;
        }
        return ActionSupport.SUCCESS;
    }

    @Override
    public String getActionPrex() {
        return getRequest().getContextPath() + "/backend/serviceplace";
    }

    @Override
    public String getCustomJs() {
        return super.getCustomJs();
        //return getRequest().getContextPath() + "/js/serviceplace.js";
    }

    public String getCustomJsp() {
        if (this.type == 0) {
            return "/pages/menu_serviceplace/servicePlaceParent.jsp";
        } else {
            return null;
        }
    };

    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("sequence", "显示序号").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("code", "位置编码").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("name", "位置名称").enableSearch());
        if(this.type==0){
            Map<String, String> filterMap = new HashMap<String, String>();
            filterMap.put("area_=", "0");
            filterMap.put("type_=", "1");
            filterMap.put("isDeleted", "false");
            this.innerHospital = sp.queryDataByCondition(filterMap,null);
            String[][] hospital = new String[2][this.innerHospital.size()];
            if (this.innerHospital.size()>0) {
                for (int i = 0; i < this.innerHospital.size(); i++) {
                    hospital[0][i] = this.innerHospital.get(i).getId();
                    hospital[1][i] = this.innerHospital.get(i).getName();
                }
            } else {
                hospital = null;
            }
            init.getAoColumns().add(new TableHeaderVo("parentid", "地图地点").addSearchOptions(hospital));
        }
        return init;
    }

    @Override
    public TableQueryVo getModel() {
        TableQueryVo model = super.getModel();
        model.getFilter().put("type", this.type + "");
        return model;
    }

    @Override
    public String getAddButtonParameter(){
         return "type="+this.type;
    }

    public List<ServicePlaceBean> getServicePlaces() {
        return servicePlaces;
    }

    public void setServicePlaces(List<ServicePlaceBean> servicePlaces) {
        this.servicePlaces = servicePlaces;
    }

    public ServicePlaceBean getServicePlace() {
        return servicePlace;
    }

    public void setServicePlace(ServicePlaceBean servicePlace) {
        this.servicePlace = servicePlace;
    }

    @Override
    public String getTableTitle() {
        if (this.type == 0)
            return "<li>服务管理</li><li class=\"active\">公司</li>";
        else
            return "<li>服务管理</li><li class=\"active\">地图地点</li>";
    }
}
