/**
 * Created by peter on 14-3-15.
 */

cellFormatter["status"] = function ( data, type, full ) {
    if(data == 0){
        return '创建';
    }else if(data == 1){
        return '开始';
    }else if(data == 2){
        return '结束';
    }else{
        return '未知状态';
    }
}