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
        padding: 30px 0 24px;
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
</style>
<script>
    cellFormatter["state"] = function (data, type, full) {
        var process = [];
        var proArray = ['测量报价', '设计', '看稿', '修改定稿', '金额', '预付款下单', '制作', '安装', '付清余款'];
        process.push('<div class="md_process_wrap">');
        var length = data * 60 + 12;
        process.push('<div class="md_process_sd" style=" width: ' + length + 'px; "></div>');
        for (var i = 0; i < proArray.length; i++) {
            process.push('<i class="md_process_i" style="left:' + (i * 60 - 7) + 'px">' + (i + 1) + '<span class="md_process_tip">' + proArray[i] + '</span></i>');
        }
        process.push('</div>');
        return process.join('');
    };
</script>