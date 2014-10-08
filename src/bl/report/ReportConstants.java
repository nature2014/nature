package bl.report;

/**
 * Created by fanxin.wfx on 14-10-7.
 */
public class ReportConstants {
    public static String ECHARTS_PIE_TEMPLATE = "{title:{text:'',x:'center'},tooltip:{trigger:'item',formatter:\"{a} <br/>{b} : {c}元 ({d}%)\"},legend:{orient:'vertical',x:'left',data:[]},calculable:true,series:[{name:'',type:'pie',radius:'55%',center:['50%','60%'],data:[]}]}";

    public static void main(String[] args) {
        System.out.println(ECHARTS_PIE_TEMPLATE);
    }
}
