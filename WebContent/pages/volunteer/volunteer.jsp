<%--
  User: peter
  Date: 14-4-10
  Time: 下午8:32
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>
    <script>
        cellFormatter["name"] = function ( data, type, full ) {
            return '<a class="fancybox" rel="group" href="' + full.iconpath + '" title="' + data + '"><img style="margin-right:10px" class="volunteerimg imagedesigner" src="' + full.iconpath + '"/><div style="text-align:center">' + data + '</div></a>';
        }

        cellFormatter["occupation"] = function ( data, type, full ) {
           <s:iterator value="listSource">
               if(data =='<s:property value="code"/>'){
                    return '<s:property value="name"/>';
               }
           </s:iterator>
               return "未知职称("+data+")";
        };
        
        options['addTrainCourse'] = {
           'title':'添加培训课程',
           'html': '<button title="添加培训课程" style="margin-left:5px" class="btn btn-info btn-xs" onclick="options[\'addTrainCourse\'].onClick(this)"><i class="fa fa-edit"></i></button>',
           'onClick' : function(button){
               var tableObj = $('#'+tableId).dataTable();
               var nTr = $(button).parents('tr')[0];
               var selectRowData =  tableObj.fnGetData( nTr );
               window.location = "${rootPath}/backend/volunterTrainCourse/add.action?volunteerId=" + selectRowData[idName];
           }
        }
    </script>