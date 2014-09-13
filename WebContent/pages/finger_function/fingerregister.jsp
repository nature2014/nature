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
   window.remoteServerPath = 'pages/finger_function/pseudo.jpg';
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
<script for="ZKFPEngX1" language="JavaScript" type="text/javascript" event="OnImageReceived(result)">
    callImgReceived(result);
</script>
<script for="ZKFPEngX1" language="JavaScript" type="text/javascript" event="OnEnroll(result,ATemplate)">
    callMyEnroll(result, ATemplate);
</script>

<script type="text/javascript">

    function callImgReceived(result)
    {
        //注册操作
        if(fingerEng.IsRegister){
            if(result){
                var counter = fingerEng.EnrollIndex;
                if(counter-1>0)
                    printMessage("还有"+(counter-1)+"次，请继续刷指纹");
            }else{
                printMessage("指纹质量不好");
            }
        }else{   //验证阶段
            if(result){

            }else{
                printMessage("指纹质量不好");
            }
        }
    }


    function callMyEnroll(result,ATemplate)
    {
        //注册操作
        if(true){
            //产生指纹的特征ID
            var localpath = window.localFingerPath + (window.figureNumber)+".jpg";
            var remotepath = "window.remoteServerPath"+"?time="+new Date().getTime();
            var localFingerPath = window.localFingerPath + (window.figureNumber)+".tpl";
            fingerEng.SaveJPG(localpath);
            //var template = fingerEng.GetTemplateAsString();
            //添加指纹特征模板到高速缓存里
            //fingerEng.AddRegTemplateStrToFPCacheDB(fpcHandle,figureNumber,template);
            //保存特征模板的base64.
            fingerEng.SaveTemplate(localFingerPath,ATemplate);
            document.getElementById("fingerjpg").src=remotepath;
            document.getElementById("fingerpath").value=remotepath;
            printMessage("指纹有效");
        }else{
            printMessage("指纹采集失败，请重新点击指纹录入");
        }
    }

    function beginRegister()
    {
        if(window.fingerStatus==0){
            fingerEng.BeginEnroll();
            document.getElementById("fingerjpg").src="";
            printMessage("如果想要录入指纹，请将手指头放在指纹采集器上，当红灯闪过后，请将手指头离开!");
        }
    }
</script>
</body>
</html>
