package bl.beans;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexed;

/**
 * Created by Administrator on 14-3-14.
 */
@Entity(value = "traincourse_serviceplace")

public class TrainCourseServicePlaceBean extends Bean {
    @Indexed
    private String trainCourseId;
    @Indexed
    private String servicePlaceId;

    public String getTrainCourseId() {
        return trainCourseId;
    }

    public void setTrainCourseId(String trainCourseId) {
        this.trainCourseId = trainCourseId;
    }

    public String getServicePlaceId() {
        return servicePlaceId;
    }

    public void setServicePlaceId(String servicePlaceId) {
        this.servicePlaceId = servicePlaceId;
    }
}
