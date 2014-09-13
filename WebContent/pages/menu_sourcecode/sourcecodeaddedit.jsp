<!DOCTYPE html>
<%@ include file="../commonHeader.jsp"%>
<html lang="en">
<html>
<head>
    <title>职称编码</title>
    <script>
    </script>
</head>
<body>
<!--main content start-->
<section class="panel">
    <header class="panel-heading">
        职称编码
    </header>
    <div class="panel-body">
        <%@include file="../strutsMessage.jsp"%>
        <form role="form" method="post" class="form-horizontal tasi-form" action="${rootPath}/backend/sourcecode/save.action">
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">名称</label>
                <div class="col-lg-10">
                    <input name="sourceCode.id" type="hidden" value="${sourceCode.id}"/>
                    <input name="sourceCode.name" type="text" class="form-control" value="${sourceCode.name}"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="control-label col-lg-2 col-sm-3">编码</label>
                <div class="col-lg-10">
                    <input name="sourceCode.code" type="text" class="form-control" <s:if test="sourceCode.id!=''">readonly="true"</s:if> value="${sourceCode.code}"/>
                </div>
                <script>
                    $(document).ready(function() {
                        $("form").validate({
                            rules: {
                                "sourceCode.name":{
                                    required:true,
                                    maxlength: 8
                                },
                                "sourceCode.code":{
                                    required:true,
                                    number: true
                                }
                            },
                            messages: {
                                'sourceCode.name': {
                                    required: "请输入名称",
                                    maxlength: "名称最多20个文字"
                                },
                                "sourceCode.code":{
                                    required:"请输入编码",
                                    number:"编码必须是数值型"
                                }
                            }
                        });
                    });
                </script>
            </div>

            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-10">
                    <button class="btn btn-info" type="submit">保存</button>
                    <button class="btn btn-info" type="button" onclick="window.location.href='${rootPath}/backend/sourcecode/index.action'">取消</button>
                </div>
            </div>
        </form>
    </div>
</section>
</body>
</html>
