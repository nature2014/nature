/**
 * @author pli
 * @since 2014/09/13
 */
package bl.beans;

import org.mongodb.morphia.annotations.Entity;

/**
 * @author pli
 */
@Entity(value = "backend_productlevel")
public class ProductLevelBean extends Bean {
    //编码
    private String code;
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
