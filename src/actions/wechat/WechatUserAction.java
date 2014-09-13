package actions.wechat;

import actions.BaseAction;
import bl.beans.ActiveUserBean;
import bl.beans.ServicePlaceBean;
import bl.beans.VolunteerBean;
import bl.common.BusinessResult;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.ActiveUserBusiness;
import bl.mongobus.SequenceUidGenerator;
import bl.mongobus.ServicePlaceBusiness;
import bl.mongobus.VolunteerBusiness;
import org.apache.commons.lang.StringUtils;
import util.ServerContext;
import util.StringUtil;
import vo.ActiveVolunteerVo;
import vo.serviceplace.ServicePlaceVo;
import webapps.WebappsConstants;
import wechat.access.AccessTokenManager;
import wechat.access.AccessToken;
import wechat.user.UerManager;
import wechat.user.UserInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wangronghua on 14-3-19.
 */
public class WechatUserAction extends WechatBaseAuthAction {

    private String userName;
    private String identityCardNumber;
    private String password;

    private String servicePlaceId;
    private String servicePlaceName;
    private ServicePlaceBean servicePlaceBean;
    private List<ServicePlaceVo> places;
    private List<ActiveVolunteerVo> activeVolunteers;

    private UserInfo user;
    private VolunteerBean vol;

    public VolunteerBean getVol() {
        return vol;
    }

    public void setVol(VolunteerBean vol) {
        this.vol = vol;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    VolunteerBusiness vb = (VolunteerBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_VOLUNTEER);
    ServicePlaceBusiness sp = (ServicePlaceBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_SERVICEPLACE);
    ActiveUserBusiness activeUserBus = (ActiveUserBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_ACTIVEUSER);

    public String binding() {
        if(volunteer != null) {
            //means we already have the volunteer binding to the wechat user, go to the release process
            return "releaseBinding";
        }
        return SUCCESS;
    }

    public String releaseBinding() {
        if(volunteer != null) {
            volunteer.setOpenID(null);
            volunteer.setWechat(null);
            vb.updateLeaf(volunteer, volunteer);
        }
        return SUCCESS;
    }

    /**
     *
     * @return
     */
    public String myInfo() {
        if(null == volunteer) {
            return "redirectBinding";
        }
        return SUCCESS;
    }

    /**
     *
     * @return
     */
    public String myHonor() {
        addActionMessage("该功能暂未开放！");
        return SUCCESS;
    }

    /**
     *
     * @return
     */
    public String save() {
        if (vol == null || StringUtils.isBlank(vol.getId())) {
            addActionMessage("用户不存在, 保存失败!");
        } else {
            VolunteerBean volTmp = (VolunteerBean) vb.getLeaf(vol.getId()).getResponseData();
            if (volTmp == null) {
                addActionMessage("获取用户失败, 保存失败!");
            } else {
                volTmp.setCellPhone(vol.getCellPhone());
                vb.updateLeaf(volTmp, volTmp);
                addActionMessage("保存成功!");
            }
        }
        return SUCCESS;
    }

    public String register() {
        if (vol != null) {
            vol.setWechat(getWechatUser());
            BusinessResult result = vb.save(getRequest(), vol);
            if (result.getErrors().size() > 0) {
                for (Object error : result.getErrors()) {
                    addActionError(error.toString());
                }
                return FAILURE;
            }
            return SUCCESS;
        } else {
            vol = new VolunteerBean();
            return FAILURE;
        }
    }

    public String bindingSubmit() {
        if(StringUtils.isEmpty(userName)) {
            super.addActionError("用户名不能为空！");
        }
        else if(StringUtils.isEmpty(password)) {
            super.addActionError("密码不能为空！");
        }
        else if(StringUtils.isEmpty(identityCardNumber)) {
            super.addActionError("身份证号不能为空！");
        }
        else {
            VolunteerBean userTmp = vb.getVolunteerBeanByCode(userName);
            if (userTmp != null && userTmp.getStatus() == 2 && StringUtil.toMD5(password).equals(userTmp.getPassword())
                && identityCardNumber.equals(userTmp.getIdentityCard())) {
                userTmp.setOpenID(openID);
                userTmp.setWechat(wechatUser);
                vb.updateLeaf(userTmp, userTmp);
                return SUCCESS;
            } else {
                super.addActionError("对不起，您输入的用户信息有误！");
            }
        }
        return ERROR;
    }

    public String searchActiveUser() {
        if (null == volunteer) {
            return "redirectBinding";
        }
        List<ServicePlaceBean> placeBeans = (List<ServicePlaceBean>) sp.getAllLeaves().getResponseData();

        places = sp.getFormattedPlaces(placeBeans, placeBeans);

        if (null == servicePlaceId) {
            ActiveUserBean userBean = (ActiveUserBean) activeUserBus.getActiveUserByUserId(volunteer.getId()).getResponseData();
            if (null != userBean) {
                servicePlaceId = userBean.getServicePlaceId();
            }
        }
        if (null != servicePlaceId) {
            servicePlaceBean = (ServicePlaceBean) sp.getLeaf(servicePlaceId).getResponseData();
            List<ActiveUserBean> activeUserBeanList = (List<ActiveUserBean>) activeUserBus.getActiveUsersByServicePlace(servicePlaceId).getResponseData();
            activeVolunteers = new ArrayList<ActiveVolunteerVo>(activeUserBeanList.size());
            for (ActiveUserBean userBean : activeUserBeanList) {
                VolunteerBean vbean = (VolunteerBean) vb.getLeaf(userBean.getUserId()).getResponseData();
                ActiveVolunteerVo vo = new ActiveVolunteerVo();
                vo.setId(vbean.getId());
                vo.setName(vbean.getName());
                vo.setCellPhone(vbean.getCellPhone());
                vo.setIconPath(vbean.getIconpath());
                vo.setStatus(userBean.getStatus());
                vo.setCheckInTime(userBean.getCheckInTime());
                vo.setDistance(userBean.getDistance());
                vo.setDescription(userBean.getDescription());
                activeVolunteers.add(vo);
            }
        }
        return SUCCESS;
    }

    public String getWechatUser() {
        return wechatUser;
    }

    public void setWechatUser(String wechatUser) {
        this.wechatUser = wechatUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdentityCardNumber() {
        return identityCardNumber;
    }

    public void setIdentityCardNumber(String identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }

    public String getOpenID() {
        return openID;
    }

    public void setOpenID(String openID) {
        this.openID = openID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ServicePlaceVo> getPlaces() {
        return places;
    }

    public void setPlaces(List<ServicePlaceVo> places) {
        this.places = places;
    }

    public String getServicePlaceId() {
        return servicePlaceId;
    }

    public void setServicePlaceId(String servicePlaceId) {
        this.servicePlaceId = servicePlaceId;
    }

    public List<ActiveVolunteerVo> getActiveVolunteers() {
        return activeVolunteers;
    }

    public void setActiveVolunteers(List<ActiveVolunteerVo> activeVolunteers) {
        this.activeVolunteers = activeVolunteers;
    }

    public ServicePlaceBean getServicePlaceBean() {
        return servicePlaceBean;
    }

    public void setServicePlaceBean(ServicePlaceBean servicePlaceBean) {
        this.servicePlaceBean = servicePlaceBean;
    }

    public String getServicePlaceName() {
        return servicePlaceName;
    }

    public void setServicePlaceName(String servicePlaceName) {
        this.servicePlaceName = servicePlaceName;
    }

}
