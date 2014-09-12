<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<label>工号</label>
<div class="input-control text" data-role="input-control">
    <input type="text" name="volunteer.code" value="${volunteer.code}" readonly="readonly"/>
</div>

<s:if test="%{!#request['struts.request_uri'].contains('view.action')}">
<label>指纹信息</label>
<label id="console_message" style="color:red;"></label>
<div class="input-control text" data-role="input-control">
    <img id="fingerjpg" src="${volunteer.fingerpath}" style="width:100px;height:80px;margin-bottom:120px" onerror="this.src='img/notfound.png';">
    <script>
        window.figureNumber = "${volunteer.code}";
        function  printMessage(message){
            jQuery("#console_message").html(message);
        }
    </script>
    <%@include file="../finger_function/fingerregister.jsp"%>
</div>
<input id="fingerpath" name="volunteer.fingerpath" type="hidden" value="${volunteer.fingerpath}">
<input name="volunteer.id" class="btn-reveal" type="button" value="指纹录入" onclick="beginRegister()">
</s:if>
<s:else>
    <label>指纹信息</label>
    <div class="input-control text" data-role="input-control">
        <img id="fingerjpg" src="${volunteer.fingerpath}" style="width:100px;height:80px;margin-bottom:120px" onerror="this.src='img/notfound.png';">
    </div>
</s:else>

<label>姓名</label>
<div class="input-control text" data-role="input-control">
    <input name="volunteer.id" type="hidden" value="${volunteer.id}"/>
    <input name="volunteer.registerFrom" type="hidden" value="${volunteer.registerFrom}"/>
    <input name="volunteer.status" type="hidden" value="${volunteer.status}"/>
    <input type="text" placeholder="请输入姓名" name="volunteer.name" value="${volunteer.name}" autofocus required="required"/>
    <button class="btn-clear" tabindex="-1"></button>
</div>

<s:if test="volunteer.id.length() > 0">
 </s:if>
<s:else>
<label>密码</label>
<div class="input-control password" data-role="input-control">
    <input type="password" placeholder="请输入密码" id="password" name="volunteer.password" autofocus required="required"/>
    <button class="btn-reveal" tabindex="-1"></button>
</div>
<div class="input-control password" data-role="input-control">
    <input type="password" placeholder="请再次输入密码" name="confirm_password" autofocus required="required"/>
    <button class="btn-reveal" tabindex="-1"></button>
</div>
</s:else>

<label>性别</label>
<div class="input-control radio default-style inline-block" data-role="input-control">
    <label class="inline-block">
        <input type="radio" name="volunteer.sex" value="1" checked
          <s:if test="volunteer.sex==1">
            checked = "checked"
          </s:if>
         />
        <span class="check"></span>
        男
    </label>
    <label class="inline-block">
        <input type="radio" name="volunteer.sex" value="2"
          <s:if test="volunteer.sex==2">
            checked = "checked"
          </s:if>
        />
        <span class="check"></span>
        女
    </label>
</div>

<label>身份证号</label>
<div class="input-control text" data-role="input-control">
    <input type="text" placeholder="请输入身份证号" name="volunteer.identityCard" value="${volunteer.identityCard}" required="required"/>
    <button class="btn-clear" tabindex="-1"></button>
</div>

<label>手机</label>
<div class="input-control text" data-role="input-control">
    <input type="text" placeholder="请输入手机" name="volunteer.cellPhone" value="${volunteer.cellPhone}" required="required"/>
    <button class="btn-clear" tabindex="-1"></button>
</div>

<label>微信</label>
<div class="input-control text" data-role="input-control">
    <input type="text" placeholder="请输入微信" name="volunteer.wechat" value="${volunteer.wechat}" />
    <button class="btn-clear" tabindex="-1"></button>
</div>

<label>邮箱</label>
<div class="input-control text" data-role="input-control">
    <input type="text" placeholder="请输入邮箱" name="volunteer.email" value="${volunteer.email}" />
    <button class="btn-clear" tabindex="-1"></button>
</div>

