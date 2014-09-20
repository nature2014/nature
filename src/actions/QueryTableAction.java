/**
 *
 */
package actions;

import bl.common.TableBusinessInterface;
import bl.instancepool.SingleBusinessPoolManager;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.apache.commons.beanutils.PropertyUtils;
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
import java.util.List;

/**
 * Base Table Action
 *
 * @author gudong
 * @since $Date:2014-02-20$
 */
public abstract class QueryTableAction<B extends TableBusinessInterface> extends BaseAction implements ModelDriven<TableQueryVo> {

    protected TableQueryVo model;
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

    private static JsonConfig config = new JsonConfig();

    //静态初始化全局的JSON序列化配置
    static {
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        //忽略的标注类型
        config.addIgnoreFieldAnnotation(IgnoreJsonField.class);
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
}
