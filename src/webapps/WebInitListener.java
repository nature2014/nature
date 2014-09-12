package webapps;

import java.io.FileInputStream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import actions.SystemSettingAction;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.SystemSettingBusiness;
import bl.mongobus.VolunteerBusiness;
import common.Constants;
import util.DBUtils;
import util.MultiTenancyManager;
import util.ServerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.MongoDBConnectionFactory;
import util.VolunteerCountCache;

public class WebInitListener implements ServletContextListener {
  protected static Logger LOG = LoggerFactory.getLogger(WebInitListener.class);

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    LOG.info("init dynamic form war");

    LOG.error("Before seting SNI, {}",System.getProperty("jsse.enableSNIExtension"));
    System.setProperty("jsse.enableSNIExtension", "false");
    LOG.error("After seting SNI, {}",System.getProperty("jsse.enableSNIExtension"));

    try {
      LOG.info("init server.properties file");
      ServerContext.init(WebInitListener.class.getResourceAsStream("/server.properties"));
    } catch (Exception e) {
      LOG.error("Reading file has some exception {}", e.getMessage());
    }
    try {
      LOG.info("init /etc/db.properties file");
      ServerContext.init(new FileInputStream("/etc/db.properties"));
    } catch (Exception e) {
      LOG.error("Reading file has some exception {}", e.getMessage());
    }

    try {
      LOG.info("init C:\\vzhiyuan\\server.properties file");
      ServerContext.init(new FileInputStream("C:\\vzhiyuan\\server.properties"));
    } catch (Exception e) {
      LOG.error("Reading file has some exception {}", e.getMessage());
    }

    LOG.info("init MongoDB");
    MongoDBConnectionFactory.initDb();

    // init Global Setting
    //Object global = SystemSettingAction.init();
    //sce.getServletContext().setAttribute(Constants.GLOBALSETTING, global);
//    VolunteerBusiness volunteerBusiness = new VolunteerBusiness();
//    VolunteerCountCache.set(volunteerBusiness.getUnVerifiedVolunteers(), volunteerBusiness.getUnInterviewedVolunteers());
//    sce.getServletContext().setAttribute(WebappsConstants.UNVERIFIED_VOLUNTEER_KEY, volunteerBusiness.getUnVerifiedVolunteers());
//    sce.getServletContext().setAttribute(WebappsConstants.UNINTERVIEWED_VOLUNTEER_KEY, volunteerBusiness.getUnInterviewedVolunteers());
    sce.getServletContext().setAttribute("rootPath", sce.getServletContext().getContextPath());

    // init db flag data by server.properties
      String[] dbFlags = MultiTenancyManager.getDBFlags();
      for(String dbFlag : dbFlags) {
          loadServerContext(dbFlag);
      }
  }
    private static void loadServerContext(String dbFlag) {
        SystemSettingBusiness ssb = (SystemSettingBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_SYSTEMSETTING);
        String tempFlag = DBUtils.getDBFlag();
        DBUtils.setDBFlag(dbFlag);
        /**load system setting from mongo db**/
        ssb.loadServerContext();
        if(null != tempFlag) {
            DBUtils.setDBFlag(tempFlag);
        } else {
            DBUtils.removeDBFlag();
        }
    }
  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    LOG.info("destroy dynamic form war");
    LOG.info("disconnect conection of MongoDB");
    MongoDBConnectionFactory.destroy();
  }

}
