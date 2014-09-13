package actions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by pli on 14-8-23.
 * 用了自适应Json-lib的忽略字段序列化机制采用标注的办法，
 * @see BaseTableAction
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreJsonField {

}
