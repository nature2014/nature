package vo.report;

import vo.NameValueVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangronghua on 14-3-23.
 */
public class DailyTimeReportVo {

  public List<NameValueVo> getValueList() {
    return valueList;
  }

  public void setValueList(List<NameValueVo> valueList) {
    this.valueList = valueList;
  }

  public void addNameValueVo(NameValueVo vo) {
    if(null == valueList)valueList = new ArrayList<NameValueVo>();
    valueList.add(vo);
  }

  private List<NameValueVo> valueList;
}
