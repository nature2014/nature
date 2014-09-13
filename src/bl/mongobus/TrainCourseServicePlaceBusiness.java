package bl.mongobus;

import bl.beans.ServicePlaceBean;
import bl.beans.TrainCourseServicePlaceBean;
import bl.common.BeanContext;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainCourseServicePlaceBusiness extends MongoCommonBusiness<BeanContext, TrainCourseServicePlaceBean> {
    private static Logger LOG = LoggerFactory.getLogger(TrainCourseServicePlaceBusiness.class);
    private ServicePlaceBusiness spb = (ServicePlaceBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_SERVICEPLACE);

    public TrainCourseServicePlaceBusiness() {
        this.clazz = TrainCourseServicePlaceBean.class;
    }

    public List<ServicePlaceBean> getServicePlacesByTrainCourseId(String trainCourseId) {
      List<ServicePlaceBean> resultList = new ArrayList<ServicePlaceBean>();
      Map filter = new HashMap();
      filter.put("isDeleted_!=", true);
      filter.put("trainCourseId", new ObjectId(trainCourseId));
      List<TrainCourseServicePlaceBean> list = super.queryDataByCondition(filter, null);
      for(TrainCourseServicePlaceBean bean : list) {
        ServicePlaceBean spBean = (ServicePlaceBean)spb.getLeaf(bean.getServicePlaceId()).getResponseData();
        if(!spBean.getIsDeleted()) {
          resultList.add(spBean);
        }
      }
      return resultList;
    }
}
