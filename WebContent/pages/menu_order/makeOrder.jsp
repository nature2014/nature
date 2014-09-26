<%--
  User: peter
  Date: 14-09-20
  Time: 下午8:32
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<style type="text/css">
    .md_process_wrap {
        position: relative;
        height: 23px;
        margin: 0 auto;
        padding: 50px 0 24px;
        width: 492px;
        background: url("${rootPath}/img/process_sd.png") 0px 10px repeat-x;
        background-size: 60px 30px;
        margin-left: 10px;
        margin-right: 10px;
    }

    .md_process_sd {
        background: url("${rootPath}/img/process.png") 10px 0 repeat-x;
        position: absolute;
        top: 10px;
        left: -2px;
        height: 50px;
        background-size: 60px 30px;
    }

    .md_process_i {
        position: absolute;
        top: 16px;
        width: 23px;
        height: 23px;
        font: 400 14px/23px helvetica, tahoma, arial, sans-serif;
        color: #fff;
        text-align: center;
    }

    .md_process_tip {
        color: #3252ff;
        position: absolute;
        top: 24px;
        width: 80px;
        height: 20px;
        left: 50%;
        margin-left: -40px;
        font: 400 12px/20px helvetica, tahoma, arial, sans-serif;
    }

    a {
        color: blue;
    }
</style>
<script>
    operationButtons = [
        '<a class="btn btn-info" href="${actionPrex}/add.action?${addButtonParameter}"><i class="fa fa-plus"></i> 添加 </a>'
    ];
    //格式化payDate
    cellFormatter["customerId"] = function (data, type, full) {
        if (data != null) {
            return '<a href="${rootPath}/backend/customer/index.action?name=' + full.customerName + '">' + full.customerName + '</a>';
        } else {
            return "";
        }
    }
    //格式化payDate
    cellFormatter["payDate"] = function (data, type, full) {
        if (data != null) {
            return formatDateTime(data.time, false);
        } else {
            return "";
        }
    }
    cellFormatter["state"] = function (data, type, full) {
        var process = [];
        var wrapPre = '预付款下单<br>(¥' + full.prePayment + '元)';
        if (full.prePaymentState == 1) {
            wrapPre = '未付定金(¥' + 0 + '元)';
        } else if (full.prePaymentState == 2) {
            wrapPre = '报价未做';
        }
        var proArray = ['测量报价<br>(¥' + full.offerPrice + '元)', '设计', '看稿', '修改定稿', '订单价格<br>(¥' + full.price + '元)', wrapPre, '制作', '安装', '已付余款<br>(¥' + full.closePayment + '元)'];
        process.push('<div class="md_process_wrap">');
        var length = data * 60 + 12;
        process.push('<div class="md_process_sd" style=" width: ' + length + 'px; "></div>');
        for (var i = 0; i < proArray.length; i++) {
            process.push('<i class="md_process_i" style="left:' + (i * 60 - 7) + 'px">' + (i + 1) + '<span class="md_process_tip">' + proArray[i] + '</span></i>');
        }
        process.push('</div>');
        return process.join('');
    };

    $(".form-horizontal.tasi-form [name='createTime_gteq']").attr("data-date-format", "yyyy-mm-dd");
    $(".form-horizontal.tasi-form [name='createTime_gteq']").datepicker();
    $(".form-horizontal.tasi-form [name='createTime_lteq']").attr("data-date-format", "yyyy-mm-dd");
    $(".form-horizontal.tasi-form [name='createTime_lteq']").datepicker();

    $(".form-horizontal.tasi-form [name='payDate_gteq']").attr("data-date-format", "yyyy-mm-dd");
    $(".form-horizontal.tasi-form [name='payDate_gteq']").datepicker();
    $(".form-horizontal.tasi-form [name='payDate_lteq']").attr("data-date-format", "yyyy-mm-dd");
    $(".form-horizontal.tasi-form [name='payDate_lteq']").datepicker();
</script>