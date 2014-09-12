<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript" src="js/checkUtil.js"></script>
<script type="text/javascript">
    //please refer to form-validation-script.js
    $(document).ready(function() {
        $("#volunteerForm").validate({
            rules: {
                confirm_password: {
                    equalTo: "#password"
                },
                'volunteer.email':{
                    email:true
                },
                'volunteer.identityCard':{
                    required: true,
                    idCardNo:true
                },
                'volunteer.cellPhone':{
                    required:true,
                    cellPhone:true
                }
            },
            messages: {
                'volunteer.name': {
                    required: "请输入用户名"
                },
                'volunteer.code': {
                    required: "请输入工号"
                },
                'volunteer.password': {
                    required: "请输入密码"
                },
                'volunteer.identityCard': {
                    required: "请输入证件号",
                    idCardNo: "请输入正确的证件号"
                },
                confirm_password: {
                    required: "请再次输入密码",
                    equalTo: "密码两次输入不一致"
                },
                'volunteer.cellPhone': {
                    required: "请输入手机",
                    cellPhone: "请输入正确的手机号, 例如：13912332122"
                },
                'volunteer.wechat': {
                    required: "请输入微信"
                },
                'volunteer.email': {
                    email: "请输入正确的邮箱, 例如：test@qq.com"
                },
                'oldPassword': {
                    required: "请输入旧密码"
                }
            }
        });
    });
</script>