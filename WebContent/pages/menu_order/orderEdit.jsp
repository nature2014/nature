<%@ include file="../commonHeader.jsp" %>
<!--main content start-->
<section class="panel">
    <header class="panel-heading">
        <s:if test="product.id.length() > 0">
            修改订单
        </s:if>
        <s:else>
            添加订单
        </s:else>
    </header>
    <div class="panel-body">
        <s:actionerror/><s:actionmessage/>
        <form id="order" class="form-horizontal tasi-form" action="${rootPath}/backend/order/save.action"
              method="post">
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">业务名称</label>

                <div class="col-lg-10">
                    <input name="order.id" type="hidden" value="${order.id}"/>
                    <input type="text" placeholder="业务名称" name="order.name" class="form-control"
                           autofocus="autofocus"
                           required="required" value="${order.name}"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">客户</label>

                <div class="col-lg-10">
                    <s:if test="listCustomerBean!=null">
                        <s:select name="order.customerId" list="listCustomerBean" listKey="id" listValue="name"
                                  value="order.customerId" cssClass="form-control"/>
                    </s:if>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">负责人</label>

                <div class="col-lg-10">
                    <s:if test="listVolunteerBean!=null">
                        <s:select name="order.resOfficer" list="listVolunteerBean" listKey="id" listValue="name"
                                  value="order.resOfficer" cssClass="form-control"/>
                    </s:if>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">订单状态</label>
                <input type="hidden" id="orderStateValue" name="order.state" value="${order.state}">

                <div class="col-lg-10">
                    <div id="orderState"></div>
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
                            cursor: pointer;
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
                        $(document).ready(function () {
                            var process = [];
                            var proArray = ['测量报价', '设计', '看稿', '修改定稿', '金额', '预付款下单', '制作', '安装', '付清余款'];
                            process.push('<div class="md_process_wrap">');
                            var length = parseInt('${order.state}') * 60 + 12;
                            process.push('<div id="progressLength" class="md_process_sd" style=" width: ' + length + 'px; "></div>');
                            for (var i = 0; i < proArray.length; i++) {
                                process.push('<i class="md_process_i" data-index="' + i + '" style="left:' + (i * 60 - 7) + 'px">' + (i + 1) + '<span class="md_process_tip">' + proArray[i] + '</span></i>');
                            }
                            process.push('</div>');
                            $("#orderState").html(process.join(''));

                            $(".md_process_i").on("click", function (evt) {
                                var index = $(evt.target).attr("data-index");
                                if (index < (parseInt('${order.state}') + 1)) {
                                    $(".hiddenGroup").hide();
                                    $(".showGroup" + index).show();
                                }
                                //表明进入下一个状态
                                else if (index == parseInt('${order.state}') + 1) {
                                    if ($("#prePaymentState").val() == 2) {
                                        alert("对不起！由于你当前订单处于报价未做状态！");
                                    } else {
                                        $("#progressLength").width($("#progressLength").width() + 72);
                                        $("#orderStateValue").val(index);
                                    }
                                }
                            });
                            $("#prePaymentState").change(function () {
                                if ($(this).val() != 0) {
                                    $("#prePaymentValue").hide();
                                    //如果不是预付定金状态，强制设置0
                                    $("input[name='order.prePayment']").val(0);
                                } else {
                                    $("#prePaymentValue").show();
                                }
                            });
                            $(".showGroup${order.state}").show();

                        });
                    </script>
                </div>
            </div>
            <div class="form-group has-success showGroup5 hiddenGroup" style="display:none">
                <label class="col-lg-2 control-label">预付款状态</label>

                <div class="col-lg-10">
                    <s:select cssClass="form-control" id="prePaymentState" name="order.prePaymentState" list="#{'0':'预付定金', '1':'未付定金','2':'报价未做'}"
                              value="order.prePaymentState"/>
                </div>
            </div>
            <div class="form-group has-success showGroup5 hiddenGroup" id="prePaymentValue" style="display:none">
                <label class="col-lg-2 control-label">预付款(元)</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="预付款(元)" class="form-control" name="order.prePayment"
                           value="${order.prePayment}"/>
                </div>
            </div>
            <div class="form-group has-success showGroup0 hiddenGroup" style="display:none">
                <label class="col-lg-2 control-label">测量报价(元)</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="测量报价(元)" class="form-control" name="order.offerPrice"
                           value="${order.offerPrice}"/>
                </div>
            </div>
            <div class="form-group has-success showGroup4 hiddenGroup" style="display:none">
                <label class="col-lg-2 control-label">金额(元)</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="金额(元)" class="form-control" name="order.price"
                           value="${order.price}"/>
                </div>
            </div>

            <div class="form-group has-success showGroup8 hiddenGroup" style="display:none">
                <label class="col-lg-2 control-label">付清余款(元)</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="付清余款(元)" class="form-control" name="order.closePayment"
                           value="${order.closePayment}"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">其它</label>

                <div class="col-lg-10">
                    <textarea placeholder="其它" name="order.comments" class="form-control"
                              required="required" rows="5">${order.comments}</textarea>
                </div>
            </div>

            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-10">
                    <button class="btn btn-info" type="submit">保存</button>
                    <button class="btn btn-info" type="button"
                            onclick="window.location.href='${rootPath}/backend/order/index.action'">取消
                    </button>
                </div>
            </div>
        </form>
    </div>
</section>

<script type="text/javascript">
    //please refer to form-validation-script.js
    $(document).ready(function () {
        $("#order").validate({
            rules: {
                'order.price': {
                    required: true,
                    min: 0,
                    max: 999999
                },
                'order.offerPrice': {
                    required: true,
                    min: 0,
                    max: 999999
                },
                'order.prePayment': {
                    required: true,
                    min: 0,
                    max: 999999
                },
                'order.closePayment': {
                    required: true,
                    min: 0,
                    max: 999999
                }
            },
            messages: {
                'order.price': {
                    required: "请输入价格",
                    min: "价格必须大于等于0",
                    max: "价格必须小于等于999999"
                },
                'order.offerPrice': {
                    required: "请输入测量报价",
                    min: "测量报价必须大于等于0",
                    max: "测量报价必须小于等于999999"
                },
                'order.prePayment': {
                    required: "请输入预付款",
                    min: "预付款必须大于等于0",
                    max: "预付款必须小于等于999999"
                },
                'order.closePayment': {
                    required: "请输入结算金额",
                    min: "结算金额必须大于等于0",
                    max: "结算金额必须小于等于999999"
                }
            }
        });
        jQuery("form").on("submit", function () {
            if ($("#orderStateValue").val() == 8) {
                var price = $("input[name='order.price']").val();
                var prePayment = $("input[name='order.prePayment']").val();
                var closePayment = $("input[name='order.closePayment']").val();
                if (Math.abs(price * 1 - prePayment * 1 - closePayment * 1) > 0.001) {
                    alert('您的结算金额+预付款不等于订单价格，请重新核对！');
                    return false;
                } else {
                    return true;
                }
            }
        });
    });
</script>