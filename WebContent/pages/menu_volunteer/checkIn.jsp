<!DOCTYPE html>
<html lang="en">
<html>
<head>
    <title>签到管理</title>
    <%@ include file="../bootstrapHeader.jsp"%>
    <style type="text/css">
        .mt15 {
            margin-top: 15px;
        }

    </style>

    <script type="text/javascript">
        function cancel() {
            window.location.href = "${rootPath}/userFront/getCheckInRecords.action";
        }
    </script>
</head>
<body>
<section class="panel">
    <header class="panel-heading">
        签到管理
    </header>
    <%-- 消息引用 --%>
    <s:include value="../strutsMessage.jsp"/>

    <div class="panel-body">
        <form id="dataForm" action="${rootPath}/userFront/checkInSubmit.action" class="form-horizontal">
            <div class="adv-table dataTables_wrapper form-inline">

                <div class="row mt15">
                    <div class="form-group col-sm-10">
                        <div class="col-md-10">
                            <div class="btn-group" data-toggle="buttons">
                                <s:iterator value="servicePlaces" var="sp">
                                    <label class="btn btn-default btn-lg">
                                        <input id="<s:property value='%{#sp.id}'/>" name="servicePlaceId" type="radio" value="<s:property value='%{#sp.id}'/>">
                                        <s:property value="%{#sp.name}"/>
                                    </label>
                                </s:iterator>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row mt15">
                    <div class="form-group col-sm-10">
                        <div class="col-sm-2">
                            <button type="submit" class="btn btn-info btn-block">确定</button>
                        </div>
                        <div class="col-sm-2">
                            <button type="button" class="btn btn-info btn-block" onclick="cancel()">取消</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</section>
</body>
</html>