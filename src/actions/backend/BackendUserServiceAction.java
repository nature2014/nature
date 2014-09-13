package actions.backend;

import bl.mongobus.UserServiceBusiness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;
import vo.table.TableQueryVo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by peter on 06-04-21.
 */
public class BackendUserServiceAction extends BaseBackendAction<UserServiceBusiness> {
    protected static Logger LOG = LoggerFactory.getLogger(BackendUserServiceAction.class);
    @Override
    public String getActionPrex() {
        return getRequest().getContextPath() + "/backend/userservice";
    }
    @Override
    public TableQueryVo getModel() {
        if (model == null) {
            model = new TableQueryVo();
        }
        //默认按着签入的降序显示在TableIndex.jsp
        model.getSort().remove("userName");
        model.getSort().put("checkInTime","desc");
        model.getFilter().put("isDeleted_!=", true);
        return model;
    }
    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("userName", "员工").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("userCode", "工号").enableSearch());
        //0:普通签入, 1:微信签入
        init.getAoColumns().add(new TableHeaderVo("checkInMethod", "签到方式").addSearchOptions(new String[][]{{"0","1"},{"普通签入","微信签入"}}));
        init.getAoColumns().add(new TableHeaderVo("servicePlaceName", "服务地点").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("checkInTime_gteq", "起始时间").setHiddenColumn(true).enableSearch());
        init.getAoColumns().add(new TableHeaderVo("checkInTime_lteq", "结束时间").setHiddenColumn(true).enableSearch());
        init.getAoColumns().add(new TableHeaderVo("description", "签入位置信息",false));
        init.getAoColumns().add(new TableHeaderVo("checkOutDescription", "签出位置信息",false));
        init.getAoColumns().add(new TableHeaderVo("latitude", "签入纬度",false));
        init.getAoColumns().add(new TableHeaderVo("longitude", "签入经度",false));
        init.getAoColumns().add(new TableHeaderVo("checkOutLatitude", "签出纬度",false));
        init.getAoColumns().add(new TableHeaderVo("checkOutLongitude", "签出经度",false));
        init.getAoColumns().add(new TableHeaderVo("checkInTime", "签入时间"));
        init.getAoColumns().add(new TableHeaderVo("checkOutTime", "签出时间"));
        return init;
    }

    @Override
    public String getCustomJsp() {
        return "/pages/userservice/userservicegrid.jsp";
    }

    @Override
    public String getTableTitle() {
        return "<ul class=\"breadcrumb\"><li>工时管理</li><li class=\"active\">签到记录</li></ul>";
    }
}
