package bl.mongobus;

import bl.beans.TrainCourseBean;
import bl.common.BeanContext;
import bl.common.BusinessResult;
import bl.constants.BusTieConstant;
import bl.exceptions.MiServerException;
import bl.instancepool.SingleBusinessPoolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainCourseBusiness extends MongoCommonBusiness<BeanContext, TrainCourseBean> {
	private static final Logger LOG = LoggerFactory.getLogger(TrainCourseBusiness.class);

  public TrainCourseBusiness() {
		this.clazz = TrainCourseBean.class;
	}

  @Override
  public BusinessResult updateLeaf(BeanContext origBean, BeanContext newBean) {
    VolunteerTrainCourseBusiness vtcb  = (VolunteerTrainCourseBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_VOLUNTEERTRAINCOURSE);
    BusinessResult result = super.updateLeaf(origBean, newBean);
    TrainCourseBean bean = (TrainCourseBean)newBean;
    vtcb.updateCourseName(bean.getId(), bean.getName());
    return result;
  }

    @Override
    public BusinessResult deleteLeaf(String objectId) {
        VolunteerTrainCourseBusiness vtcb  = (VolunteerTrainCourseBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_VOLUNTEERTRAINCOURSE);
        //检查培训记录表
        Map<String, String> filter = new HashMap<String, String>();
        filter.put("traincourseId_=", objectId);

        List list = vtcb.queryDataByCondition(filter, null);
        if (list.size() > 0) {
            throw new MiServerException.Conflicted("该课程已经被培训记录关联，请先解除关联关系");
        }
        return super.deleteLeaf(objectId);
    }
}
