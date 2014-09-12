/**
 *
 */
package actions;

import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import org.bson.types.ObjectId;
import util.StringUtil;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;
import bl.UserBusiness;
import bl.beans.UserBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gudong
 * @since $Date:2014-02-10$
 */
public class UserAction extends BaseTableAction<UserBusiness> {
    private static Logger log = LoggerFactory.getLogger(UserAction.class);
    public final static String LOGIN_USER_SESSION_ID = "sessionUser";
    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    /**
     *
     */
    private static final long serialVersionUID = -5222876000116738224L;

    @Override
    public String getActionPrex() {
        return getRequest().getContextPath() + "/user";
    }

    @Override
    public String getCustomJs() {
        return getRequest().getContextPath() + "/js/user.js";
    }

    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("name", "用户名").enableSearch());
        return init;
    }

    @Override
    public String save() throws Exception {
        if (StringUtils.isBlank(user.getId())) {
            user.set_id(new ObjectId(getSession().getAttribute("dataId").toString()));
            getBusiness().createLeaf(user);
        } else {
            UserBean origUser = (UserBean) getBusiness().getLeaf(user.getId().toString()).getResponseData();
            BeanUtils.copyProperties(origUser, user);
            getBusiness().updateLeaf(origUser, origUser);
        }
        return SUCCESS;
    }

    @Override
    public String edit() throws Exception {
        user = (UserBean) getBusiness().getLeaf(getId()).getResponseData();
        getSession().setAttribute("dataId", user.getId());
        return SUCCESS;
    }

    @Override
    public String delete() throws Exception {
        if (getIds() != null) {
            for (String id : getIds()) {
                getBusiness().deleteLeaf(id);
            }
        }
        return SUCCESS;
    }

    /**
     * lock or unlock
     *
     * @return
     * @throws Exception
     */
    public String lock() throws Exception {
        user = (UserBean) getBusiness().getLeaf(getId()).getResponseData();
        if (user != null) {
            if (user.getLock() == UserBean.LOCK) {
                user.setLock(UserBean.UN_LOCK);
            } else {
                user.setLock(UserBean.LOCK);
            }
            getBusiness().updateLeaf(user, user);
        }
        return SUCCESS;
    }


    /**
     * changePassword
     *
     * @return
     * @throws Exception
     */
    public String changePassword() throws Exception {
        if (user != null) {
            UserBean sessionUser = (UserBean) getSession().getAttribute(LOGIN_USER_SESSION_ID);
            if (sessionUser != null && sessionUser.getPassword().equals(StringUtil.toMD5(user.getPassword()))) {
                sessionUser.setPassword(StringUtil.toMD5(getRequest().getParameter("newPassword")));
                getBusiness().updateLeaf(sessionUser, sessionUser);
                getSession().setAttribute(LOGIN_USER_SESSION_ID, sessionUser);
                return SUCCESS;
            } else {
                addActionError("原始密码错误");
            }
        }
        return FAILURE;
    }

    /**
     * @return
     */
    public String resetPassword() {
        user = (UserBean) getBusiness().getLeaf(getId()).getResponseData();
        if (user != null) {
            user.setPassword(StringUtil.toMD5("123456"));
            getBusiness().updateLeaf(user, user);
            addActionMessage("密码重置成功！");
        } else {
            addActionMessage("获取用户失败！重置密码失败！");
        }
        return SUCCESS;
    }

    /**
     * login
     *
     * @return
     */
    public String login() {
        if (user != null) {
            UserBean userTmp = (UserBean) getBusiness().getLeafByName(user.getName()).getResponseData();
            if (userTmp != null && userTmp.getPassword().equals(user.getPassword())) {
                getSession().setAttribute(LOGIN_USER_SESSION_ID, userTmp);
                return SUCCESS;
            }
        }
        return "login_failure";
    }

    /**
     * login
     *
     * @return
     */
    public String logout() {
        getSession().removeAttribute(LOGIN_USER_SESSION_ID);
        HttpServletRequest req = (HttpServletRequest) ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
        HttpServletResponse resp = (HttpServletResponse) ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);
        eraseCookie(req, resp);
        return SUCCESS;
    }

    private void eraseCookie(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null)
            for (int i = 0; i < cookies.length; i++) {
                cookies[i].setValue("");
                cookies[i].setMaxAge(0);
                resp.addCookie(cookies[i]);
            }
    }
}
