/**
 * 
 */
package actions;

import bl.common.TableBusinessInterface;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vo.report.ActiveTimeReportVo;
import vo.table.TableDataVo;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;
import vo.table.TableQueryVo;

import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Base Table Action
 * 
 * @author gudong
 * @since $Date:2014-02-20$
 */
public abstract class BaseTableAction<B extends TableBusinessInterface> extends BaseAction implements ModelDriven<TableQueryVo> {
  private static Logger log = LoggerFactory.getLogger(BaseTableAction.class);
  protected TableQueryVo model;
  protected TableBusinessInterface business;
  public static final String INDEX_SUCCESS = "tableIndex";

  /**
   * The Action Prefix that will be append action. like : getRequest().getContextPath() + "/datatable".
   * 
   * @return
   */
  public abstract String getActionPrex();

    /**
     *  In the TableIndex.jsp, there is a operation named "添加", sometime, need to be brought some parameters.
     * @return
     */
  public String getAddButtonParameter(){
      return "";
  }
  /**
   * 
   * @return
   */
  public String getCustomJs() {
    return null;
  };

    public String getCustomJsp() {
        return null;
    };

  public String getTableTitle() {
    return null;
  }

  public String getTableId() {
    return this.getClass().getSimpleName() + "_table";
  }

  /**
   * 
   * @return
   */
  public abstract TableInitVo getTableInit();

  /**
   * 
   * @return
   */
  public B getBusiness() {
    if (business == null) {
      ParameterizedType t = (ParameterizedType) (this.getClass().getGenericSuperclass());
      Type[] ts = t.getActualTypeArguments();
      try {
        business = (B) ((Class<B>) ts[0]).newInstance();
      } catch (InstantiationException e) {
        log.error("get business error!", e);
        business = null;
      } catch (IllegalAccessException e) {
        log.error("get business error!", e);
        business = null;
      }
    }
    return (B) business;
  }

  @Override
  public TableQueryVo getModel() {
    if (model == null) {
      model = new TableQueryVo();
    }
    model.getFilter().put("isDeleted_!=", true);
    return model;
  }

  /**
   * 
   * @return
   * @throws Exception
   */
  public String index() throws Exception {
    return INDEX_SUCCESS;
  }

  /**
   * initTable
   * 
   * @return
   * @throws Exception
   */
  public String initTable() throws Exception {
    // json
    JsonConfig config = new JsonConfig();
    config.setExcludes(new String[] { "searchOptions" });
    TableInitVo ti = getTableInit();
    JSONObject jsonObject = JSONObject.fromObject(ti, config);
    writeJson(jsonObject.toString());
    return null;
  }

  /**
   * queryTable
   * 
   * @return
   * @throws Exception
   */
  public String queryTable() throws Exception {
    long count = getBusiness().getCount(getModel());
    TableDataVo table = getBusiness().query(getModel());
    table.setsEcho(getModel().getSEcho());
    table.setiTotalDisplayRecords(count);
    table.setiTotalRecords(count);

    // json
    JSONObject jsonObject = JSONObject.fromObject(table);
    writeJson(jsonObject.toString());
    return null;
  }

   public String exportTable() {
        getModel().setIDisplayStart(0);
        getModel().setIDisplayLength(10000);
        long count = getBusiness().getCount(getModel());
        TableDataVo tableData = getBusiness().query(getModel());

       HttpServletResponse response = ServletActionContext.getResponse();
       response.setContentType("application/msexcel;charset=UTF-8");  //两种方法都可以
       String fileName = this.getTableId()+".xls";
       response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

       //客户端不缓存
       response.addHeader("Pargam", "no-cache");
       response.addHeader("Cache-Control", "no-cache");
       TableInitVo tiv = getTableInit();
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       try {
           HSSFWorkbook workbook = new HSSFWorkbook();
           Sheet sheet = workbook.createSheet("导出" + sdf.format(new Date()));
           List<TableHeaderVo> tableHeader = tiv.getAoColumns();
           Row row = sheet.createRow(0);
           int columnLength = 0;
           for (int i = 0; i < tableHeader.size(); i++) {
               if(!tableHeader.get(i).isHiddenColumn()){
                   row.createCell(columnLength).setCellValue(tableHeader.get(i).getsTitle());
                   columnLength++;
               }
           }

           int index = 1;
           for (Object vo : tableData.getAaData()) {
               row = sheet.createRow(index);
               int columnLengthData = 0;
               for (int i = 0; i < tableHeader.size(); i++) {
                   TableHeaderVo thv = tableHeader.get(i);
                   //只显示在grid里的数据
                   if(!thv.isHiddenColumn()){
                       //可读性的属性才可以反射
                       if(PropertyUtils.isReadable(vo, thv.getmData()) && PropertyUtils.getProperty(vo, thv.getmData())!=null){
                           row.createCell(columnLengthData).setCellValue(thv.convertValue(PropertyUtils.getProperty(vo, thv.getmData())));
                       }else{
                           row.createCell(columnLengthData).setCellValue("");
                       }
                       columnLengthData++;
                   }
               }
               index++;
           }
           workbook.write(response.getOutputStream());
           response.getOutputStream().flush();
           response.getOutputStream().close();

       } catch (Exception e) {
           LOG.error("this exception [{}]", e.getMessage());
       }
        return null;
   }


  /**
   * 
   * @return
   * @throws Exception
   */
  public String add() throws Exception {
    return ActionSupport.SUCCESS;
  }

  /**
   * 
   * @return
   * @throws Exception
   */
  public String edit() throws Exception {
    return ActionSupport.SUCCESS;
  }

  /**
   * 
   * @return
   * @throws Exception
   */
  public String delete() throws Exception {
    return ActionSupport.SUCCESS;
  }

  /**
   * 
   * @return
   * @throws Exception
   */
  public String save() throws Exception {
    return ActionSupport.SUCCESS;
  }
}
