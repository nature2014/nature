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
    subbutton11.setUrl(MenuUtils.getOAuthUrl("/wechat/productList.action"));

    WechatButton button2 = new WechatButton();
    button2.setName("最新优惠");

    WechatButton button3 = new WechatButton();
    button3.setName("我的订单");

    button1.addSubButton(subbutton11);

    List<WechatButton> buttonList = new ArrayList<WechatButton>();
    buttonList.add(button1);
    buttonList.add(button2);
    buttonList.add(button3);

    menu.setButton(buttonList);
    return menu;
  }
}
