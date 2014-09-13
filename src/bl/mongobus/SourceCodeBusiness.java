package bl.mongobus;

import bl.beans.SourceCodeBean;
import bl.common.BeanContext;
import bl.common.BusinessResult;
import bl.exceptions.MiServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dao.MongoDBConnectionFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.List;

/**
 * Created by peter on 06-04-14.
 */
public class SourceCodeBusiness extends MongoCommonBusiness<BeanContext, SourceCodeBean> {
    private static Logger LOG = LoggerFactory.getLogger(SourceCodeBusiness.class);

    public SourceCodeBusiness() {
        this.clazz = SourceCodeBean.class;
    }

    @Override
    public BusinessResult createLeaf(BeanContext genLeafBean) {
        SourceCodeBean sp = (SourceCodeBean) genLeafBean;
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
        Query<SourceCodeBean> query = dc.createQuery(this.clazz);
        query.filter("isDeleted", false).or(
                query.criteria("name").equal(sp.getName()),
                query.criteria("code").equal(sp.getCode())
        );
        List<SourceCodeBean> exists = query.asList();
        if (exists.size() > 0) {
            sp.set_id(null);
            throw new MiServerException.Conflicted("已经存在的职称名称或者编码");
        }
        return super.createLeaf(genLeafBean);
    }

    @Override
    public BusinessResult updateLeaf(BeanContext origBean, BeanContext newBean) {
        SourceCodeBean sp = (SourceCodeBean) newBean;
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
        Query<SourceCodeBean> query = dc.createQuery(this.clazz);
        query.filter("isDeleted", false).or(
                query.criteria("name").equal(sp.getName()),
                query.criteria("code").equal(sp.getCode())
        );
        List<SourceCodeBean> exists = query.asList();
        if (exists.size() > 1 || (exists.size() == 1 && !exists.get(0).getId().equals(sp.getId()))) {
            throw new MiServerException.Conflicted("已经存在的职称名称或者编码");
        }
        return super.updateLeaf(origBean, newBean);
    }
}
