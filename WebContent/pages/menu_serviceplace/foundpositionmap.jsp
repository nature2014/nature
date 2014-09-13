<%--
  User: peter
  Date: 14-3-18
  Time: 上午7:08
--%>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=<s:property value="@util.ServerContext@getValue(@util.DBUtils@getDBFlag()+@wechat.utils.Constants@MAP_TOKEN)"/>"></script>
<div style="clear:both;"></div>
<div id="allmap"></div>
<script type="text/javascript">
      try{
        window.city = "<s:property value="@util.ServerContext@getValue(@util.DBUtils@getDBFlag()+@wechat.utils.Constants@CITY)"/>";
        // 百度地图API功能
        var map = new BMap.Map("allmap");
        var contextMenu = new BMap.ContextMenu();
        var txtMenuItem = [
            {
                text:'放大',
                callback:function(){map.zoomIn()}
            },
            {
                text:'缩小',
                callback:function(){map.zoomOut()}
            },
            {
                text:'放置到最大级',
                callback:function(){map.setZoom(18)}
            },
            {
                text:'查看全国',
                callback:function(){map.setZoom(4)}
            }
        ];


        for(var i=0; i < txtMenuItem.length; i++){
            contextMenu.addItem(new BMap.MenuItem(txtMenuItem[i].text,txtMenuItem[i].callback,100));
            if(i==1 || i==3) {
                contextMenu.addSeparator();
            }
        }
        map.addContextMenu(contextMenu);


        window.marker = null;
        function mapPosition(destination,clear,callBackFunction){
            // 创建地址解析器实例
            var myGeo = new BMap.Geocoder();
            if(clear){
              map.removeOverlay(window.marker);
              window.marker = null;
              map.reset();
            }
            // 将地址解析结果显示在地图上,并调整地图视野
            myGeo.getPoint(destination, function(point){
                if (point) {
                    map.centerAndZoom(point, 15);
                    if(window.marker==null)
                      window.marker = new BMap.Marker(point);
                    map.addOverlay(window.marker);
                    window.marker.enableDragging();//允许拖动
                    window.marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
                    var label = new BMap.Label(destination,{offset:new BMap.Size(20,-10)});
                    window.marker.setLabel(label);
                    if(typeof callBackFunction =='function'){
                        callBackFunction.call(window,point);//传入位置信息
                    }
                }
            }, window.city);
            window.marker.enableDragging();//允许拖动
            window.marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
        }
        function refreshPosition(callBackFunction){
            if(window.marker!=null){
                callBackFunction.call(window,window.marker.getPosition());//传入位置信息
            }
        }
        map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
        map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_LEFT})); // 左下

       function initializePosition(destination,lng,lat){

          if(lng!="" && lat!=""){
              var point = new BMap.Point(lng, lat);
              map.centerAndZoom(point,15);
              if(window.marker==null)
                 window.marker = new BMap.Marker(point);
              window.marker.enableDragging();//允许拖动
              window.marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
              map.addOverlay(window.marker);
              var label = new BMap.Label(destination,{offset:new BMap.Size(20,-10)});
              window.marker.setLabel(label);
          }else{
              map.centerAndZoom(window.city,15); // 初始化地图,设置中心点坐标和地图级别。
          }

       }

      //whoisheremap.jsp will call this function.
      function mapMutiplePosition(destination,lng,lat){
          if(lng!="" && lat!=""){
              var point = new BMap.Point(lng, lat);
              map.centerAndZoom(point,15);
              var marker = new BMap.Marker(point);
              //marker.enableDragging();//允许拖动
              marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
              map.addOverlay(marker);
              var label = new BMap.Label(destination,{offset:new BMap.Size(20,-10)});
              marker.setLabel(label);
          }
      }

      }catch(error){
          jQuery("#dialog_message").html('<span style="color:red">请检查电脑的网络状况，确保可以访问百度地图</span>');
          jQuery("#dialog_message").dialog();
      }
</script>
