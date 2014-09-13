/**
 * @author gudong
 * @since Date: Mar 16, 2014
 */
package bl.beans;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

/**
 * @author gudong
 * @since $Date:2013-03-16$
 */
@Entity(value = "volunteer_traincourse")
public class VolunteerTrainCourseBean extends Bean {
  @Indexed
  private String volunteerId;
  @Indexed
  private String volunteerName;
  @Indexed
  private String volunteerCode;
  @Indexed
  private String traincourseId;
  @Indexed
  private String traincourseName;
  private transient VolunteerBean volunteer;
  private transient TrainCourseBean trainCourse;
  private Integer status = 0; // 0=未通过,1=通过

    public String getVolunteerName() {
        return volunteerName;
    }

    public void setVolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }

    public String getTraincourseName() {
        return traincourseName;
    }

    public void setTraincourseName(String traincourseName) {
        this.traincourseName = traincourseName;
    }

    public String getVolunteerId() {
    return volunteerId;
  }

  public void setVolunteerId(String volunteerId) {
    this.volunteerId = volunteerId;
  }

  public String getTraincourseId() {
    return traincourseId;
  }

  public void setTraincourseId(String traincourseId) {
    this.traincourseId = traincourseId;
  }

  public VolunteerBean getVolunteer() {
    return volunteer;
  }

  public void setVolunteer(VolunteerBean volunteer) {
    this.volunteer = volunteer;
  }

  public TrainCourseBean getTrainCourse() {
    return trainCourse;
  }

  public void setTrainCourse(TrainCourseBean trainCourse) {
    this.trainCourse = trainCourse;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }


  public String getVolunteerCode() {
    return volunteerCode;
  }

  public void setVolunteerCode(String volunteerCode) {
    this.volunteerCode = volunteerCode;
  }
}
