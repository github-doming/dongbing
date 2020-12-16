
 var options = $.extend({ 
            olay: 'div.modal-overlay',
            modals: 'div.modal-window',
            animationEffect: 'slideDown',
            animationSpeed: 400,
            moveModalSpeed: 'slow', 
        },options);
		
 var fx={     
      "InitModal":function(){
	      if( $('.modal-window').length==0){
		    return $('<div>').addClass('modal-window').appendTo('body');		  
		  }else{
		    return $('.modal-window');
		  }
	  },
	  'boxin':function(data,modal,width,overlayCancel,closeBtn){
	       if(overlayCancel){
			   $("<div>").hide().addClass("modal-overlay").click(
				 function(event){fx.boxout(event);
				}).appendTo("body");
           }else{
		       $("<div>").hide().addClass("modal-overlay").appendTo("body"); 
		   }
		   if(closeBtn){
		       $('<a>').attr('href','#').addClass('modal-close-btn').html("&times;").click(function(event){fx.boxout(event);}).appendTo(modal); 
		   }
	        modal.hide().append(data).appendTo("body");  
		    
		    var olay = $(options.olay);  
		    var modals = $(options.modals);
			var currentModal=$('.modal-window');
			currentModal.css({
				top:$(window).height() /2 - currentModal.outerHeight() /2 + $(window).scrollTop(),
				left:$(window).width() /2 - currentModal.outerWidth() /2 + $(window).scrollLeft()
            });  
		 	
			if(width){
			    currentModal.css({width:width});
			}
			/* 模态窗口下滑显示 */
			olay[options.animationEffect](options.animationSpeed); 
			currentModal.delay(options.animationSpeed)[options.animationEffect](options.animationSpeed); 
		  
		    /* 模态窗口随着窗口变化而调整 */
		    $(window).bind('resize',function (){
				modals.stop(true).animate({
				top:$(window).height() /2 - modals.outerHeight() /2 + $(window).scrollTop(),
				left:$(window).width() /2 - modals.outerWidth() /2 + $(window).scrollLeft()
				},options.moveModalSpeed);
			});
		   
	  },
	  'boxout':function(event){
	       if(event!=undefined){
		      event.preventDefault();
		   }
		   $('a').removeClass("active");
	  
		   var modals = $(options.modals);
		   var olay = $(options.olay); 
		    modals.fadeOut(100, function(){
                if (options.animationEffect === 'slideDown') {
                    olay.slideUp('slow',function(){
					       modals.remove();
				           olay.remove();
						  }); 
                }  
            });  
	  },
	  'boxalert':function(data){  
	  	var currentModal=$('.modal-window');  
	       $('<div>').html(data).addClass('modal-window').css({
				top:$(window).height() /2 - currentModal.outerHeight() /2 + $(window).scrollTop(),
				left:$(window).width() /2 - currentModal.outerWidth() /2 + $(window).scrollLeft()
            }).appendTo('body').slideDown('slow').fadeOut(5000,function(){$('.modal-window').remove();});
		 
	  },
	  'scrollmodal':function(){
		  scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
		  if( $('.modal-window').length==0){
			return $('<div>').addClass('modal-window').css({top:scrollTop}).appendTo('body');	 
		  }else{
			$('.modal-window').css({top:scrollTop});
			return $('.modal-window');
		  } 
	  }
    };   
 