/**
 *
 */
package actions;

import bl.common.TableBusinessInterface;
import bl.instancepool.SingleBusinessPoolManager;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.struts2.ServletActionContext;
import org.bson.types.ObjectId;
import vo.table.TableDataVo;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;
import vo.table.TableQueryVo;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base Table Action
 *
 * @author gudong
 * @since $Date:2014-02-20$
 */
public abstract class QueryTableAction<B extends TableBusinessInterface> extends BaseAction implements ModelDriven<TableQueryVo> {

    public TableQueryVo model;
    protected TableBusinessInterface business;
    private String id;
    private String[] ids;
    public static final String INDEX_SUCCESS = "tableQueryIndex";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public static JsonConfig config = new JsonConfig();

    //静态初始化全局的JSON序列化配置
    static {
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        //忽略的标注类型
        config.addIgnoreFieldAnnotation(IgnoreJsonField.class);

        config.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
            @Override
            public Object processObjectValue(String key, Object value, JsonConfig arg2) {
                //System.out.println("key:" + key);
                //System.out.println("value:" + value);
                if(value == null){
                    return value;
                }
                return new SimpleDateFormat("yyyy-MM-dd").format(value);
            }
            @Override
            public Object processArrayValue(Object value, JsonConfig arg1) {
                return value;
            }
        });
    }
    /**
     * The Action Prefix that will be append action. like : getRequest().getContextPath() + "/datatable".
     *
     * @return
     */
    public abstract String getActionPrex();

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
    public abstract String getCustomPath();


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
            ParameterizedType t = (ParameterizedType) this.getClass().getGenericSuperclass();
            Type[] ts = t.getActualTypeArguments();
            business = (B) SingleBusinessPoolManager.getBusObj(((Class<B>)ts[0]).getCanonicalName());
        }
        return (B) business;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public String index() throws Exception {
        return INDEX_SUCCESS;
    }

    @Override
    public TableQueryVo getModel() {
        if (model == null) {
            model = new TableQueryVo();
        }
        return model;
    }

    /**
     * initTable
     *
     * @return
     * @throws Exception
     */
    public String initTable() throws Exception {
        //解决对象之间循环关联
        JSONObject jsonObject = JSONObject.fromObject(getTableInit(), config);
        writeJson(jsonObject);
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
        //解决对象之间循环关联
        JSONObject jsonObject = JSONObject.fromObject(table, config);
        writeJson(jsonObject);
        return null;
    }

    protected TableQueryVo filterEmptyValue(TableQueryVo model){
        Map map = new HashMap<>();
        try {
            for (Object key : model.getFilter().keySet()) {
                Object value = model.getFilter().get(key);
                if (value instanceof String[]) {
                    if (value != null && StringUtils.isNotEmpty(((String[]) value)[0])) {
                        map.put(key, value);
                    }
                } else {
                    if (value != null) {
                        map.put(key, value);
                    }
                }
            }
            model.setFilter(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    public String exportTable() {
        getModel().setIDisplayStart(0);
        getModel().setIDisplayLength(10000);
        filterEmptyValue(getModel());
        long count = getBusiness().getCount(getModel());
        TableDataVo tableData = getBusiness().query(getModel());

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/msexcel;charset=UTF-8");  //两种方法都可以
        String fileName = this.getTableId() + ".xls";
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
                if (!tableHeader.get(i).isHiddenColumn()) {
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
                    if (!thv.isHiddenColumn()) {
                        //可读性的属性才可以反射
                        if (PropertyUtils.isReadable(vo, thv.getmData()) && PropertyUtils.getProperty(vo, thv.getmData()) != null) {
                            row.createCell(columnLengthData).setCellValue(thv.convertValue(PropertyUtils.getProperty(vo, thv.getmData())));
                        } else {
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

    public void setModel(TableQueryVo model) {
        this.model = model;
    }
}
