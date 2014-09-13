package vo.serviceplace;

import java.util.List;

/**
 * Created by wangronghua on 14-4-12.
 */
public class ServicePlaceVo {

  private String id;
  private String name;

  private String serviceicon;
  private int count;


  public ServicePlaceVo() {

  }

  public ServicePlaceVo(String id, String name, Integer count) {
    this.id = id;
    this.name = name;
    if(null != count) {
      this.count = count;
    }
  }

  public ServicePlaceVo(String id, String name, Integer count, String serviceicon) {
    this.id = id;
    this.name = name;
    this.serviceicon = serviceicon;
    if(null != count) {
      this.count = count;
    }
  }

  private List<ServicePlaceVo> selections;

  public List<ServicePlaceVo> getSelections() {
    return selections;
  }

  public void setSelections(List<ServicePlaceVo> selections) {
    this.selections = selections;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }


  public String getServiceicon() {
    return serviceicon;
  }

  public void setServiceicon(String serviceicon) {
    this.serviceicon = serviceicon;
  }

}
