package actions.wechat;

import bl.beans.VolunteerBean;
import bl.common.BusinessResult;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.SequenceUidGenerator;
import bl.mongobus.VolunteerBusiness;
import util.ServerContext;
import util.StringUtil;
import webapps.WebappsConstants;

/**
 * Created by wangronghua on 14-3-22.
 */
public class WechatRecruitAction extends WechatBaseAuthAction {
    VolunteerBusiness vb = (VolunteerBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_VOLUNTEER);
    private VolunteerBean register = null;

    public VolunteerBean getRegister() {
        return register;
    }

    public void setRegister(VolunteerBean register) {
        this.register = register;
    }

    public String volunteerRecruitView() {
        register = new VolunteerBean();
        return SUCCESS;
    }

    public String volunteerRecruitSave() {
        //职称微信
        register.setRegisterFrom(2);
        register.setPassword(StringUtil.toMD5("123456"));
        BusinessResult result = vb.save(getRequest(),register);
        if (result.getErrors().size() > 0) {
            for (Object error : result.getErrors()) {
                addActionError(error.toString());
            }
            return ERROR;
        }
        return SUCCESS;
    }
}
