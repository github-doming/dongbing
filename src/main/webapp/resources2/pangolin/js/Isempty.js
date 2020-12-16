jQuery.fn.extend({
	isempty:function(){
		var isok = true;
		var _this =$('.mustfill');	
		for(var i=0;i<_this.length;i++){
				if(_this.eq(i).val()==''){
					_this.eq(i).addClass('notnull');
					isok = false;
				}
		}
	
		return isok;
		
	}
})
//插件作用：检测input是否为空。如果为空停止表单提交。
//用法if($('.mustfill').isempty() == false){ return false;}     页面的mustfill都启用这方法
//需要检查的input需要添加mustfill这个class。才能检查