<%--
  User: peter
  Date: 14-4-21
  Time: 下午8:32
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<script>
    cellFormatter["checkInMethod"] = function ( data, type, full ) {
        if(data ==0){
            return '<img src="${rootPath}/img/screen.jpg"/>';
        }else{
            return '<img src="${rootPath}/img/wechat.jpg"/>';
        }
    };

    //格式化createTime  modifyTime
    cellFormatter["checkInTime"]=cellFormatter["checkOutTime"] = function ( data, type, full ) {
        if(data!=null){
            return formatDateTime(data.time,false);
        }else{
            return "";
        }
    }
    $(".form-horizontal.tasi-form [name='checkInTime_gteq']").attr("data-date-format","yyyy-mm-dd");
    $(".form-horizontal.tasi-form [name='checkInTime_gteq']").datepicker();
    $(".form-horizontal.tasi-form [name='checkInTime_lteq']").attr("data-date-format","yyyy-mm-dd");
    $(".form-horizontal.tasi-form [name='checkInTime_lteq']").datepicker();
    //没有CRUD操作
    operationButtons = [];

    //没有操作按钮
    options=[];
</script>