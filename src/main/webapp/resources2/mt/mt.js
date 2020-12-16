/*调用方法:
	$.mttwo({
		title:'标题',
		content:'内容',
		cancelBtn : false,  //是否开启取消按钮
		way:function(){
			alert(2);
			}
		});
*/
(function($){  
	$.extend({
		
		mttwo:function(options){
			
			var defaults = {  
               title:'测试标题',
			   content:'测试内容',
			   cancelBtn : false,
			   way:function(){}
            };
			var body = $('body');
			var wh = $(window).height();
			var ww = $(window).width();
					
		    var options =  $.extend(defaults, options); 
			var o = options;  
			
			$('.lock').remove();
			
			var html ='';
			
			
			html +='<div class="lock"><div class="scc"><div class="modal-content"><div class="modal-header"><h4 class="modal-title" id="myModalLabel">'+o.title+'</h4></div><div class="modal-body tis">'+o.content+'</div><div class="modal-footer"><button type="button" id="sure" class="btn btn-primary" data-dismiss="modal">确认</button><button type="button" id="cancel" class="btn " data-dismiss="modal">取消</button></div></div></div></div>';
			
			
			
			body.append(html);
			
			
			if(o.cancelBtn == true){
				$('#cancel').show();		
			}else{
				$('#cancel').hide();	
			}
			
			body.find('.lock').height(wh).width(ww).show();

			
			$('#sure').click(function(){
				
				body.find('.lock').hide();
				o.way();//运行用户自定义方法
			})
			
		
			
			$('#cancel').click(function(){
				body.find('.lock').hide();
			})
		}
	})
})(jQuery);  