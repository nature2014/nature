package bl.mongobus;

import bl.beans.Bean;
import bl.beans.ServicePlaceBean;
import bl.common.BeanContext;
import bl.common.BusinessResult;
import bl.constants.BusTieConstant;
import bl.exceptions.MiServerException;
import bl.instancepool.SingleBusinessPoolManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dao.MongoDBConnectionFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import vo.serviceplace.ServicePlaceVo;

import java.util.*;

public class ServicePlaceBusiness extends MongoCommonBusiness<BeanContext, ServicePlaceBean> {
    private static Logger LOG = LoggerFactory.getLogger(ServicePlaceBusiness.class);
    ActiveUserBusiness activeUserBus = (ActiveUserBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_ACTIVEUSER);
    private static final UserServiceBusiness usb
            = (UserServiceBusiness)SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_USERSERVICE);

    public ServicePlaceBusiness() {
        this.clazz = ServicePlaceBean.class;
    }

    @Override
    public BusinessResult createLeaf(BeanContext genLeafBean) {
        ServicePlaceBean sp = (ServicePlaceBean) genLeafBean;
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
        Query<ServicePlaceBean> query = dc.createQuery(this.clazz);
        if(sp.getParentid()!=null && !sp.getParentid().isEmpty()){
            query.filter("isDeleted", false).filter("parentid",sp.getParentid()).or(
                    query.criteria("name").equal(sp.getName()),
                    query.criteria("code").equal(sp.getCode())
            );
        }else{
            query.filter("isDeleted", false).filter("type",1).or(
                    query.criteria("name").equal(sp.getName()),
                    query.criteria("code").equal(sp.getCode())
            );
        }
        List<ServicePlaceBean> exists = query.asList();
        if (exists.size() > 0) {
            ((ServicePlaceBean) genLeafBean).set_id(null);
            throw new MiServerException.Conflicted("已经存在的服务地点名称或者编码");
        }
        return super.createLeaf(genLeafBean);
    }

    @Override
    public BusinessResult updateLeaf(BeanContext origBean, BeanContext newBean) {
        ServicePlaceBean sp = (ServicePlaceBean) newBean;
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
        Query<ServicePlaceBean> query = dc.createQuery(this.clazz);
        if(sp.getParentid()!=null && !sp.getParentid().isEmpty()){
            query.filter("isDeleted", false).filter("parentid",sp.getParentid()).or(
                    query.criteria("name").equal(sp.getName()),
                    query.criteria("code").equal(sp.getCode())
            );
        }else{
            query.filter("isDeleted", false).filter("type",1).or(
                    query.criteria("name").equal(sp.getName()),
                    query.criteria("code").equal(sp.getCode())
            );
        }
        List<ServicePlaceBean> exists = query.asList();
        if (exists.size() > 1 || (exists.size() == 1 && !exists.get(0).getId().equals(sp.getId()))) {
            throw new MiServerException.Conflicted("已经存在的服务地点名称或者编码");
        }
        BusinessResult result = super.updateLeaf(origBean, newBean);

        usb.updateServicePlaceNameByServicePlaceId(((ServicePlaceBean) newBean).getId(), ((ServicePlaceBean) newBean).getName());
        return result;
    }

  /**
   * Used to get formatted Vo object list for displaying service places in wechat 签入 and 谁在这里
   * @param allPlaceBeans
   * @param placeBeans
   * @return
   */
    public List<ServicePlaceVo>  getFormattedPlaces(List<ServicePlaceBean> allPlaceBeans, List<ServicePlaceBean> placeBeans){

      String outterPlaceParentId = "out";
      String outterPlaceParentName = "公司外";

      Map<String, ServicePlaceVo> resultMap = new HashMap<String, ServicePlaceVo>();

      Map<String, Integer> countMap = activeUserBus.getActiveVolunteerCounts();

      for(ServicePlaceBean bean: placeBeans) {
        if(bean.getIsDeleted() == true) continue;

        if(StringUtils.isEmpty(bean.getParentid())) {
          if(bean.getArea() == ServicePlaceBean.AREA_IN) {
            ServicePlaceVo parentVo = resultMap.get(bean.getId());
            if(null == parentVo) {
              parentVo = new ServicePlaceVo();
              parentVo.setId(bean.getId());
              parentVo.setName(bean.getName());
              parentVo.setSelections(new ArrayList<ServicePlaceVo>());
              resultMap.put(bean.getId(), parentVo);
            }
          } else if(bean.getArea() == ServicePlaceBean.AREA_OUT) {
            ServicePlaceVo parentVo = resultMap.get(outterPlaceParentId);
            if(null == parentVo) {
              parentVo = new ServicePlaceVo();
              parentVo.setId(outterPlaceParentId);
              parentVo.setName(outterPlaceParentName);
              parentVo.setSelections(new ArrayList<ServicePlaceVo>());
              resultMap.put(outterPlaceParentId, parentVo);
            }
            parentVo.getSelections().add(new ServicePlaceVo(bean.getId(), bean.getName(), countMap.get(bean.getId())));
          }
        } else {
          String parentId = bean.getParentid();
          ServicePlaceVo parentVo = resultMap.get(parentId);
          if(null == parentVo) {
            for(ServicePlaceBean placeBean: allPlaceBeans) {
              if(placeBean.getId().equals(parentId)) {
                parentVo = new ServicePlaceVo(placeBean.getId(), placeBean.getName(), countMap.get(bean.getId()));
                parentVo.setSelections(new ArrayList<ServicePlaceVo>());
                resultMap.put(parentId, parentVo);
                break;
              }
            }
          }
          if(null != parentVo) {
            parentVo.getSelections().add(new ServicePlaceVo(bean.getId(), bean.getName(), countMap.get(bean.getId()), bean.getServiceicon()));
          }
        }
      }
      List<ServicePlaceVo> resultList = new ArrayList<ServicePlaceVo>();
      Iterator<ServicePlaceVo> it = resultMap.values().iterator();
      while(it.hasNext()) {
        ServicePlaceVo parentVo = it.next();
        if(!outterPlaceParentId.equals(parentVo.getId())) {
          resultList.add(parentVo);
        }
      }
      if(null != resultMap.get(outterPlaceParentId)) {
        resultList.add(resultMap.get(outterPlaceParentId));
      }
      return resultList;
    }

    public List<ServicePlaceBean> getAllWithoutParent() {
      List<ServicePlaceBean> resultList = new ArrayList<ServicePlaceBean>();
      List<ServicePlaceBean> servicePlaceBeans = (List<ServicePlaceBean>)this.getAllLeaves().getResponseData();
      if(null != servicePlaceBeans) {
        for(ServicePlaceBean bean: servicePlaceBeans) {
          if(bean.getArea() == ServicePlaceBean.AREA_IN && bean.getParentid() == null) {
            continue;
          }
          resultList.add(bean);
        }
      }
      return resultList;
    }

}
