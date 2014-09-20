package actions;

import bl.beans.ServicePlaceBean;
import bl.beans.SystemSettingBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.ServicePlaceBusiness;
import bl.mongobus.SystemSettingBusiness;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import common.Constants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import util.ServerContext;
import wechat.menu.MenuUtils;
import wechat.menu.WechatButton;
import wechat.menu.WechatMenu;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 14-3-18.
 */
public class SystemSettingAction extends BaseAction {
    private SystemSettingBean systemSetting;
    private static SystemSettingBusiness ssb = (SystemSettingBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_SYSTEMSETTING);

    public SystemSettingBean getSystemSetting() {
        return systemSetting;
    }

    public void setSystemSetting(SystemSettingBean systemSetting) {
        this.systemSetting = systemSetting;
    }

    public String modify() {
        this.systemSetting = ssb.getLeaf();
        return SUCCESS;
    }

    public String save() {
        try {
            SystemSettingBean originalBean = ssb.getLeaf();
            SystemSettingBean newBean = (SystemSettingBean) originalBean.clone();
            BeanUtils.copyProperties(newBean, this.systemSetting);
            ssb.updateLeaf(originalBean, newBean);
            ssb.loadServerContext();
            //ActionContext.getContext().getApplication().put(Constants.GLOBALSETTING, newBean);
            super.addActionMessage("系统参数设定保存成功");
        } catch (Exception e) {
            LOG.error("this exception [{}]", e.getMessage());
        }
        return SUCCESS;
    }

    public String pushMenu() throws UnsupportedEncodingException {
        WechatMenu menu = this.getMenu();
        String result;
        if(MenuUtils.create(menu)){
            result = "菜单更新成功";
        } else {
            result = "菜单更新失败";
        }
        //JSONObject jsonObject = JSONObject.fromObject(result);
        writeJson(result);
        return null;
    }

    private WechatMenu getMenu() throws UnsupportedEncodingException {
        WechatMenu menu = new WechatMenu();
        WechatButton button1 = new WechatButton();
        button1.setName("大自然广告");

        WechatButton subbutton11 = new WechatButton();
        subbutton11.setName("产品展示");
        subbutton11.setType("view");
        subbutton11.setUrl(MenuUtils.getRedirectUrl("/wechat/productList.action"));

        WechatButton subbutton12 = new WechatButton();
        subbutton12.setName("订单查询");
        subbutton12.setType("view");
        subbutton12.setUrl(MenuUtils.getRedirectUrl("/wechat/productList.action"));

        WechatButton button2 = new WechatButton();
        button2.setName("员工管理");

        WechatButton subbutton21 = new WechatButton();
        subbutton21.setName("服务签入");
        subbutton21.setType("view");
        subbutton21.setUrl(MenuUtils.getOAuthUrl("/wechat/checkIn.action"));

        WechatButton subbutton22 = new WechatButton();
        subbutton22.setName("服务签出");
        subbutton22.setType("view");
        subbutton22.setUrl(MenuUtils.getOAuthUrl("/wechat/checkOut.action"));

        WechatButton subbutton23 = new WechatButton();
        subbutton23.setName("我的工时");
        subbutton23.setType("click");
        subbutton23.setKey("ME_TIMECARD");

        WechatButton subbutton24 = new WechatButton();
        subbutton24.setName("我的荣誉");
        subbutton24.setType("view");
        subbutton24.setUrl(MenuUtils.getOAuthUrl("/wechat/user/myHonor.action"));

        WechatButton subbutton25 = new WechatButton();
        subbutton25.setName("我的培训");
        subbutton25.setType("view");
        subbutton25.setUrl(MenuUtils.getOAuthUrl("/wechat/trainCourse/myTrainCourse.action"));

        WechatButton subbutton26 = new WechatButton();
        subbutton26.setName("我的资料");
        subbutton26.setType("view");
        subbutton26.setUrl(MenuUtils.getOAuthUrl("/wechat/user/myInfo.action"));

        WechatButton button3 = new WechatButton();
        button3.setName("公司动态");

        WechatButton subbutton31 = new WechatButton();
        subbutton31.setName("志愿者招募");
        subbutton31.setType("view");
        subbutton31.setUrl(MenuUtils.getOAuthUrl("/wechat/volunteerRecruit.action"));

        WechatButton subbutton32 = new WechatButton();
        subbutton32.setName("我的建议");
        subbutton32.setType("view");
        subbutton32.setUrl(MenuUtils.getOAuthUrl("/wechat/myAdvise.action"));

        WechatButton subbutton33 = new WechatButton();
        subbutton33.setName("当前活动");
        subbutton33.setType("view");
        subbutton33.setUrl(MenuUtils.getOAuthUrl("/wechat/currentActivity.action"));

        WechatButton subbutton34 = new WechatButton();
        subbutton34.setName("服务介绍");
        subbutton34.setType("view");
        subbutton34.setUrl(MenuUtils.getOAuthUrl("/wechat/serviceDescription.action"));

        button1.addSubButton(subbutton11);
        button1.addSubButton(subbutton12);

        button2.addSubButton(subbutton21);
        button2.addSubButton(subbutton22);
        button2.addSubButton(subbutton23);
        button2.addSubButton(subbutton24);
        button2.addSubButton(subbutton25);
        button2.addSubButton(subbutton26);

        button3.addSubButton(subbutton31);
        button3.addSubButton(subbutton32);
        button3.addSubButton(subbutton33);
        button3.addSubButton(subbutton34);

        List<WechatButton> buttonList = new ArrayList<WechatButton>();
        buttonList.add(button1);
        buttonList.add(button2);
        buttonList.add(button3);

        menu.setButton(buttonList);
        return menu;
    }
}
