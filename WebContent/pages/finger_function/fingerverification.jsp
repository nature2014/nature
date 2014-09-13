<%--
  User: peter
  Date: 14-3-22
  Time: 上午11:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
    </title>
</head>
<body>

<object classid="clsid:CA69969C-2F27-41D3-954D-A48B941C3BA7" type="application/x-oleobject" name="zkf" id="ZKFPEngX1" codebase="pages/finger_function/fingerInstaller.exe">
    <param name="EnrollCount" value="2"/>
    <param name="SensorIndex" value="0"/>
    <param name="Threshold" value="10"/>
    <param name="VerTplFileName" value=""/>
    <param name="RegTplFileName" value=""/>
    <param name="OneToOneThreshold" value="10"/>
    <param name="Active" value="false"/>
    <param name="IsRegister" value="false"/>
    <param name="EnrollIndex" value="0"/>
    <param name="SensorSN" value=""/>
    <param name="FPEngineVersion" value="9"/>
    <param name="ImageWidth" value="40"/>
    <param name="ImageHeight" value="40"/>
    <param name="SensorCount" value="0"/>
    <param name="TemplateLen" value="800"/>
    <param name="EngineValid" value="true"/>
    <param name="ForceSecondMatch" value="true"/>
</object>

<script language="javascript" type="text/jscript">
 function initialize()
 {
   window.localFingerPath = 'C:\\data\\img\\';
   //window.remoteServerPath = '<s:property value="@util.ServerContext@getValue('vitualstorepngdirectory')"/>';
   window.fingerEng = document.getElementById("ZKFPEngX1");
   try{
     fingerEng.InitEngine();
     window.fingerStatus = 0;
     printMessage('指纹采集器初始化成功');
   }catch(error){
     window.fingerStatus = -1;
     printMessage('指纹采集器初始化失败,请确保安装了正确的指纹驱动，以及正在使用IE8以上版本的浏览器');
   }
 }

 initialize();


 jQuery(window).unload(function() {
   if(window.fingerStatus==0 && window.fingerEng!=null){
      //释放资源
      window.fingerEng.EndEngine();
   }
  });

</script>
<script for="ZKFPEngX1" language="JavaScript" type="text/javascript" event="OnCapture(result,ATemplate)">
    callMyCapture(result, ATemplate);
</script>

<script type="text/javascript">

    function callMyCapture(result,ATemplate)
    {
        //验证阶段
        if(result){
            var ref = false;
            //三次尝试
           for(var j=0;j<3;j++){
                for(var i=0;i<window.figureNumber.length;i++){
                  var localpath = window.localFingerPath + (window.figureNumber[i].code)+".tpl";
                  try{
                  var ref = fingerEng.VerRegFingerFile(localpath,ATemplate,false, false);
                  if(ref){
                      callBackSubmit(window.figureNumber[i].code,window.figureNumber[i].md5);
                      printMessage("找到匹配员工("+window.figureNumber[i].code+")对应的指纹");
                      break;
                  }
                  }catch(error){ printMessage("找不到匹配的指纹,错误消息："+ error);}
                }
                if(!ref){
                    printMessage("系统中没有找到合适匹配的指纹，请联系系统管理员");
                }else{
                    //找到指纹匹配
                    break;
                }
           }
        }else{
            printMessage("指纹质量不好");
            beginVerify();
        }
    }

    function beginVerify()
    {
        if(window.fingerStatus==0){
            fingerEng.BeginCapture();
            printMessage("如果想要通过指纹登入，请将手指头放在指纹采集器上，当红灯闪过后，请将手指头离开!");
        }
    }

    beginVerify();
</script>
</body>
</html>
