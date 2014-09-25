<%--
  Created by IntelliJ IDEA.
  User: wangronghua
  Date: 14-9-21
  Time: 下午8:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/pages/miniwechatHeader.jsp" %>
    <link href="${rootPath}/jslib/flatlab/css/style.css" rel="stylesheet">
    <style type="text/css">
        .node-active:before {
            background: none repeat scroll 0 0 #41cac0;
            border: 2px solid #fafafa;
            border-radius: 100px;
            height: 14px;
            margin: 23px 0 0 -6px;
            width: 14px;
        }
    </style>
    <script type="text/javascript">

        function custom_close(){
            WeixinJSBridge.call('closeWindow');
        }
    </script>
</head>
<body>
    <section class="panel">
        <header class="panel-heading">
            订单详情
            <span class="tools pull-right">

            </span>
        </header>
        <div class="panel-body">
            <div class="timeline-messages">
                <!-- Comment -->
                <s:if test="orderBean.state > -1">
                    <div class="msg-time-chat node-active">
                </s:if>
                <s:else>
                    <div class="msg-time-chat">
                </s:else>
                    <%--<a href="#" class="message-img"><img class="avatar" src="img/chat-avatar.jpg" alt=""></a>--%>
                    <div class="message-body msg-in">
                        <span class="arrow"></span>
                        <div class="text">
                            <p class="attribution"><a href="#">测量报价(¥<s:property value="orderBean.offerPrice" />元)</a></p>
                        </div>
                    </div>
                </div>
                <s:if test="orderBean.state > 0">
                    <div class="msg-time-chat node-active">
                </s:if>
                <s:else>
                    <div class="msg-time-chat">
                </s:else>
                    <%--<a href="#" class="message-img"><img class="avatar" src="img/chat-avatar.jpg" alt=""></a>--%>
                    <div class="message-body msg-in">
                        <span class="arrow"></span>
                        <div class="text">
                            <p class="attribution"><a href="#">设计</a></p>
                        </div>
                    </div>
                </div>
                <s:if test="orderBean.state > 1">
                    <div class="msg-time-chat node-active">
                </s:if>
                <s:else>
                    <div class="msg-time-chat">
                </s:else>
                    <%--<a href="#" class="message-img"><img class="avatar" src="img/chat-avatar.jpg" alt=""></a>--%>
                    <div class="message-body msg-in">
                        <span class="arrow"></span>
                        <div class="text">
                            <p class="attribution"><a href="#">看稿</a></p>
                        </div>
                    </div>
                </div>
                <s:if test="orderBean.state > 2">
                    <div class="msg-time-chat node-active">
                </s:if>
                <s:else>
                    <div class="msg-time-chat">
                </s:else>
                    <%--<a href="#" class="message-img"><img class="avatar" src="img/chat-avatar.jpg" alt=""></a>--%>
                    <div class="message-body msg-in">
                        <span class="arrow"></span>
                        <div class="text">
                            <p class="attribution"><a href="#">修改定稿</a></p>
                        </div>
                    </div>
                </div>
                <s:if test="orderBean.state > 3">
                    <div class="msg-time-chat node-active">
                </s:if>
                <s:else>
                    <div class="msg-time-chat">
                </s:else>
                    <%--<a href="#" class="message-img"><img class="avatar" src="img/chat-avatar.jpg" alt=""></a>--%>
                    <div class="message-body msg-in">
                        <span class="arrow"></span>
                        <div class="text">
                            <p class="attribution"><a href="#">订单价格(¥<s:property value="orderBean.price" />元)</a></p>
                        </div>
                    </div>
                </div>
                <s:if test="orderBean.state > 4">
                    <div class="msg-time-chat node-active">
                </s:if>
                <s:else>
                    <div class="msg-time-chat">
                </s:else>
                    <%--<a href="#" class="message-img"><img class="avatar" src="img/chat-avatar.jpg" alt=""></a>--%>
                    <div class="message-body msg-in">
                        <span class="arrow"></span>
                        <div class="text">
                            <s:if test="orderBean.prePaymentState == 0">
                                <p class="attribution"><a href="#">预付款下单(¥<s:property value="orderBean.prePayment" />元)</a></p>
                            </s:if>
                            <s:elseif test="orderBean.prePaymentState == 1">
                                <p class="attribution"><a href="#">未付定金(¥0元)</a></p>
                            </s:elseif>
                            <s:elseif test="orderBean.prePaymentState == 2">
                                <p class="attribution"><a href="#">报价未做</a></p>
                            </s:elseif>
                        </div>
                    </div>
                </div>
                <s:if test="orderBean.state > 5">
                    <div class="msg-time-chat node-active">
                </s:if>
                <s:else>
                    <div class="msg-time-chat">
                </s:else>
                    <%--<a href="#" class="message-img"><img class="avatar" src="img/chat-avatar.jpg" alt=""></a>--%>
                    <div class="message-body msg-in">
                        <span class="arrow"></span>
                        <div class="text">
                            <p class="attribution"><a href="#">制作</a></p>
                        </div>
                    </div>
                </div>
                <s:if test="orderBean.state > 6">
                    <div class="msg-time-chat node-active">
                </s:if>
                <s:else>
                    <div class="msg-time-chat">
                </s:else>
                    <%--<a href="#" class="message-img"><img class="avatar" src="img/chat-avatar.jpg" alt=""></a>--%>
                    <div class="message-body msg-in">
                        <span class="arrow"></span>
                        <div class="text">
                            <p class="attribution"><a href="#">安装</a></p>
                        </div>
                    </div>
                </div>
                <s:if test="orderBean.state > 7">
                    <div class="msg-time-chat node-active">
                </s:if>
                <s:else>
                    <div class="msg-time-chat">
                </s:else>
                    <%--<a href="#" class="message-img"><img class="avatar" src="img/chat-avatar.jpg" alt=""></a>--%>
                    <div class="message-body msg-in">
                        <span class="arrow"></span>
                        <div class="text">
                            <p class="attribution"><a href="#">已付余款(¥<s:property value="orderBean.closePayment" />元)</a></p>
                        </div>
                    </div>
                </div>
                <!-- /comment -->
            </div>
        </div>
    </section>
</body>
</html>
