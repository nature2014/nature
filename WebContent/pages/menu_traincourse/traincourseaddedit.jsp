<!DOCTYPE html>
<%@ include file="../commonHeader.jsp"%>
<html lang="en">
<html>
<head>
    <title>服务地点</title>
    <script type="text/javascript" src="jslib/flatlab/assets/ckeditor/ckeditor.js"></script>
    <script>
        jQuery(document).ready(function()
        {
            CKEDITOR.replace( 'trainCourse.description',{
                language:'zh-cn',//简体中文
                width : "100%", //宽度
                height:200,  //高度
                toolbar ://工具栏设置
                        [
                            ['Maximize','-','Save','NewPage','Preview','-','Templates'],
                            ['Cut','Copy','Paste','PasteText','PasteFromWord'],
                            ['Undo','Redo','-','Find','Replace','-',,'Table','HorizontalRule','-','SelectAll','RemoveFormat'],
                            ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
                            ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
                            ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
                            ['Link','Unlink','Anchor'],
                            ['Image','Flash','Smiley','SpecialChar','PageBreak'],
                            ['Styles','Format','Font','FontSize'],
                            ['TextColor','BGColor']
                        ]
            });
        });
    </script>
</head>
<body>
<!--main content start-->
<section class="panel">
    <header class="panel-heading">
        培训课程管理
    </header>

    <div class="panel-body">
        <s:actionerror/><s:actionmessage/>
        <form role="form" method="post" class="form-horizontal tasi-form" action="backend/traincourse/save.action">
            <div class="form-group has-success">
                <label class="col-lg-2 col-sm-3 control-label">课程名称</label>
                <div class="col-lg-10 col-sm-9">
                    <input name="trainCourse.id" type="hidden" value="${trainCourse.id}"/>
                    <input name="trainCourse.name" type="text" class="form-control" value="${trainCourse.name}"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="control-label col-lg-2 col-sm-3">课程状态</label>
                <div class="col-lg-10 col-sm-9">
                    <s:select name="trainCourse.status" list="#{0:'创建',1:'开始',2:'结束'}">
                    </s:select>
                </div>
            </div>

            <div class="form-group has-success">
                <section class="panel">
                    <header class="panel-heading" onclick="$('#panelbody').toggle();$('#panelbodybullet').toggleClass('fa fa-chevron-up');$('#panelbodybullet').toggleClass('fa fa-chevron-down');" style="cursor: pointer">
                        <span class="control-label">课程描述</span>
                          <span class="tools pull-right">
                            <span id="panelbodybullet" class="fa fa-chevron-up" style="cursor: pointer"></span>
                          </span>
                    </header>
                    <div class="panel-body" id="panelbody" style="display: none">
                        <div class="form-group">
                            <label class="col-sm-2 control-label col-sm-2"></label>
                            <div class="col-sm-10">
                                <textarea class="form-control" name="trainCourse.description" rows="10">${trainCourse.description}</textarea>
                            </div>
                        </div>
                    </div>
                </section>
            </div>

            <div class="form-group has-success">
                <label class="control-label col-lg-2 col-sm-3">服务地点选择</label>
                <div class="col-lg-10 col-sm-9">
                    <table cellspacing="0" cellpadding="0" border="0" class="mt15 table table-striped table-advance table-hover table-bordered" id="userList">
                        <thead>
                        <tr>
                            <th class="column-name">
                                <label><input type="checkbox" id="selectAll" onclick="selectedAll(this)" value="全选">全选</label>
                                </span>
                                <script>
                                    function selectedAll(obj){
                                        if(obj.checked){
                                            jQuery(".selectedAll input[type='checkbox']").each(function(){
                                                this.checked=true;
                                            });
                                        }else{
                                            jQuery(".selectedAll input[type='checkbox']").each(function(){
                                                this.checked=false;
                                            });
                                        }
                                    }
                                </script>
                            </th>
                            <th class="column-name">地点编码</th>
                            <th class="column-name">服务地点</th>
                        </tr>
                        </thead>
                        <tbody class="selectedAll">
                        <s:iterator value="servicePlaceBeans" var="sp" status="id">
                            <s:set value="false" name="found"/>
                            <s:iterator value="trainCourseServicePlaces" var="sub">
                                <s:if test="#sub.servicePlaceId==#sp.id">
                                    <s:set value="true" name="found" id="found"/>
                                </s:if>
                            </s:iterator>
                            <tr>
                                <td><input type="checkbox" <s:if test="#found">checked</s:if> name="trainCourseServicePlaces[<s:property value="%{#id.index}"/>].servicePlaceId" value="<s:property value="%{#sp.id}"/>"> </td>
                                <td><s:property value="%{#sp.code}"/> </td>
                                <td><s:property value="%{#sp.name}"/> </td>
                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-10">
                    <button class="btn btn-info" type="submit">保存</button>
                    <button class="btn btn-info" type="button" onclick="window.location.href='backend/traincourse/index.action'">取消</button>
                </div>
            </div>
        </form>
    </div>
</section>
</body>
</html>
