/*
	$.dialog({
		content:"测试",  //不填默认
		title:"测试"   //不填默认
		onCancel:false,  //是否开启取消按钮 默认关闭
		callBack:function(){
			alert("测试");
		}
	});
*/

(function($){

    // 对话框
	$.dialog = function(options) {
		var defaults = {  
			title:'测试标题',
			content:'测试内容',
			ok: '确 定',
			cancel: '取 消',
			onClose: false,
			onOk: true,
			onCancel: false,
			callBack:function(){}
		};
	
		$.extend(defaults, options);
		
		$('#customModal').remove();

		var html = "";
		html+='<div class="modal fade" id="customModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">';
		html+='<div class="modal-dialog">';
		html+='<div class="modal-content">';
		html+='<div class="modal-header">';
		html+='<button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="close">&times;</button>';
		html+='<h4 class="modal-title" id="myModalLabel"><p>'+defaults.title+'</p></h4></div>';
		html+='<div class="modal-body"><p class="body_p">'+defaults.content+'</p></div>';
		html+='<div class="modal-footer">';
		html+='<button type="button" class="btn btn-default" data-dismiss="modal" id="sure"><span>确 定</span></button>';
		html+='<button type="button" class="btn btn-primary" data-dismiss="modal" id="cancel"><span>取 消</span></button></div></div></div></div>';
		
		$('body').append(html);
		
		if(defaults.onClose == true){
			$('#close').show();
		}else{
			$('#close').hide();
		}
		
		if(defaults.onCancel == true){
			$('#cancel').show();
		}else{
			$('#cancel').hide();
		}
		
		dialogShow();

        $('#sure').click(function(){
			$('#customModal').modal('hide');
			defaults.callBack();
		})		
		
		function dialogShow(){
			$('#customModal').modal('show');
		}
		
		return $dialog;
	};	
	
})(jQuery);

function centerModals() {
	$('#customModal').each(function(i) {
		var $clone = $(this).clone().css('display', 'block').appendTo('body'); var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);
	    top = top > 0 ? top : 0;
	    $clone.remove();
	    $(this).find('.modal-content').css("margin-top", top);
	});
}
	
$('#customModal').on('show.bs.modal', centerModals);