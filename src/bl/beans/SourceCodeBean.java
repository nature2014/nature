package bl.beans;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by peter on 06-04-14.
 */
@Entity(value = "sourcecode")
public class SourceCodeBean extends Bean {
    private String code = "";
    private int type = 0; // 0 代表是员工职称

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}