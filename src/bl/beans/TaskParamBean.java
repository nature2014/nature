package bl.beans;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by wangronghua on 14-10-19.
 */
@Entity(value = "task_parameter")
public class TaskParamBean extends Bean {

    private String taskId;      //关联Task
    private String displayName;
    private int dataType;       //数据类型, 0:整型，1:浮点，2:字符串，3:布尔，4:日期
    private int type;           //参数类型, 0:开放给用户填写，1:不开放给用户填写
    private int htmlType;       //展示类型，0:文本框，1:下拉筐，2:checkbox，3:radio，4:date
    private String defaultValue;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHtmlType() {
        return htmlType;
    }

    public void setHtmlType(int htmlType) {
        this.htmlType = htmlType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

}
