<%@ include file="../commonHeader.jsp" %>

<!--main content start-->
<section class="panel">
    <header class="panel-heading">
        <s:if test="customer.id.length() > 0">
            修改客户
        </s:if>
        <s:else>
            添加客户
        </s:else>
    </header>
    <div class="panel-body">
        <s:actionerror/><s:actionmessage/>
        <form id="customer" class="form-horizontal tasi-form" onsubmit="return checkForm()" action="${rootPath}/backend/customer/save.action"
              method="post">
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">客户名称</label>

                <div class="col-lg-10">
                    <input name="customer.id" type="hidden" value="${customer.id}"/>
                    <input type="text" placeholder="客户名称" name="customer.name" class="form-control"
                           required="required" value="${customer.name}"
                            />
                </div>
            </div>

            <div class="form-group has-success">
                <label class="col-lg-2 control-label">客户图标</label>

                <div class="col-lg-10">
                    <img id="customer_logo" style="width:240px;height:80px;float:left" src="${customer.logo}"/>
                    <input type="text" placeholder="客户图标" name="customer.logo" class="form-control"
                           required="required" value="${customer.logo}" onkeyup="jQuery('#customer_logo').attr('src',this.value);"/>

                </div>
            </div>

            <div class="form-group has-success">
                <label class="col-lg-2 control-label">手机号码</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="手机号码" name="customer.cellPhone" class="form-control"
                           required="required" value="${customer.cellPhone}"/>
                </div>
            </div>


            <div class="form-group has-success">
                <label class="col-lg-2 control-label">固定电话</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="固定电话" name="customer.fixedPhone" class="form-control"
                           required="required" value="${customer.fixedPhone}"/>
                </div>
            </div>

            <div class="form-group has-success">
                <label class="col-lg-2 control-label">QQ帐号</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="QQ帐号" name="customer.qq" class="form-control"
                           required="required" value="${customer.qq}"/>
                </div>
            </div>

            <div class="form-group has-success">
                <label class="col-lg-2 control-label">邮箱地址</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="邮箱地址" name="customer.email" class="form-control"
                           required="required" value="${customer.email}"/>
                </div>
            </div>

            <div class="form-group has-success">
                <label class="col-lg-2 control-label">微信</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="微信" name="customer.wechat" class="form-control"
                           required="required" value="${customer.wechat}"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">客户地址</label>

                <div class="col-lg-10">
                    <input id="servicePlacename" type="text" placeholder="客户地址" name="customer.address" class="form-control"
                           required="required" value="${customer.address}"/>
                </div>
            </div>

            <div class="form-group has-success">
                <label class="control-label col-lg-2 col-sm-3">地理坐标</label>

                <div class="col-lg-10">
                    经度：<s:textfield id="servicePlacelongitude" name="customer.longitude" readonly="true"></s:textfield>
                    纬度：<s:textfield id="servicePlacelatitude" name="customer.latitude" readonly="true"></s:textfield>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="control-label col-lg-2 col-sm-3">选择地图位置</label>

                <div class="col-lg-10">
                    <label style="color:green">请在上面地点名称中输入你想要的地理位置，然后点击"找位置"按钮;同样可以在地图上拖动跳动的位置然后点击“坐标更新”</label>
                    <br>
                    <input type="button" class="btn btn-success" name="" value="找位置"
                           onclick="mapPosition(jQuery('#servicePlacename').val(),true, callBackFunction)">
                    <input type="button" class="btn btn-success" name="" value="坐标更新" onclick="refreshPosition(callBackFunction)">
                    <br>
                    <br>
                    <style type="text/css">
                        #allmap {
                            width: 80%;
                            height: 500px;
                            overflow: hidden;
                            margin: 0;
                        }
                    </style>
                    <%@ include file="/pages/menu_serviceplace/foundpositionmap.jsp" %>
                    <script type="text/javascript">

                        jQuery(document).ready(function () {
                            var longitude = jQuery("#servicePlacelongitude").val();
                            var latitude = jQuery("#servicePlacelatitude").val();
                            initializePosition(jQuery("#servicePlacename").val(), longitude, latitude);
                            setTimeout(function () {
                                map.setZoom(10);
                            }, 100);
                        });

                        function callBackFunction(point) {
                            jQuery("#servicePlacelongitude").val(point.lng);
                            jQuery("#servicePlacelatitude").val(point.lat);
                        }
                        function checkForm() {
                            var longitude = jQuery("#servicePlacelongitude").val();
                            var latitude = jQuery("#servicePlacelatitude").val();
                            if (longitude == "" || latitude == "") {
                                jQuery("#dialog_message").html('<span style="color:red">请确保经度和纬度具备合法的地理坐标。</span>');
                                jQuery("#dialog_message").dialog();
                                return false;
                            }
                            //自动更新位置坐标
                            refreshPosition(callBackFunction);
                            return true;
                        }
                    </script>
                </div>
            </div>

            <div class="form-group has-success">
                <label class="col-lg-2 control-label">客户附件资料</label>

                <div class="col-lg-10">
                    <%@ include file="/pages/imageUploadPlugin.jsp" %>
                </div>
            </div>

            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-10">
                    <button class="btn btn-info" type="submit">保存</button>
                    <button class="btn btn-info" type="button"
                            onclick="window.location.href='${rootPath}/backend/customer/index.action'">取消
                    </button>
                </div>
            </div>
        </form>
    </div>
</section>
<script type="text/javascript">
    //please refer to form-validation-script.js
    $(document).ready(function () {
        $("#customer").validate({
            rules: {
                'customer.cellPhone': {
                    required: true,
                    cellPhone: true
                },
                'customer.email': {
                    email: true
                },
            },
            messages: {
                'customer.cellPhone': {
                    required: "请输入手机",
                    cellPhone: "请输入正确的手机号, 例如：13912332122"
                },
                'customer.email': {
                    email: "请输入正确的邮箱, 例如：test@qq.com"
                },
            }
        });
    });
</script>
