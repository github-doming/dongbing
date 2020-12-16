
function uploadImages(inputid, submitid, uploadurl, deleteurl, context, min, max) {
	var inputs = [], id = 0,
	    inputEle = document.getElementById(inputid), 
		submitEle = document.getElementById(submitid),
		fetchurl = $('#'+inputid + 'Id').val(),
		imageCount = 0;
	//服务器预览
	if (fetchurl) {
		//如果有
		var imgs = fetchurl.split(';');
		for (var idx = 0; idx < imgs.length; idx++) {
			if (imgs[idx].length > 0) {
				(function(idx){
					var html = '<div class="preview fetch' + idx + '">' + 
					'<span class="delete glyphicon glyphicon-trash fr" data-index="' + id + '" ></span>' +
				 		'<div class="w"><img alt="" src="' +context + imgs[idx] + '" data-index="' + id + '" data-src="'+ imgs[idx]+'" ></div>' + 
				 		'<div class="status"></div></div>';
				 	$('.' + inputid + '-container').append(html);
				 	imageCount++;
				 	if (imageCount >= (max || 10)) {
				 		inputEle.disabled = true;
				 	}
				})(idx)
			}
		}
		$('.' + inputid + '-container span').on('click', function() {
				deleteImage($(this).siblings('img').attr('data-src'), $(this));
			});
	}
	//选择文件
	inputEle.onchange = function(e) {
		id += 1;
		inputs.push(createInput(e, id, inputEle));
		(function(id){
			for (var i = 0; i < e.target.files.length; i++) {
				var reader = new FileReader();
				reader.onload = function(e) {
					//生成本地预览
	   			 var html = $('<div class="preview ' + inputid + id + '">');
	   			 var del = $('<span class="delete glyphicon glyphicon-trash fr" data-index="' + id + '" ></span>');
	   			 var img = $('<div class="w"><img alt="" src="' + e.target.result + '" data-index="' + id + '" ><div>');
	   			 var status = $('<div class="status"></div></div>');
	   			 del.click(function() {
	   				deleteImage($(this).siblings('img').attr('data-src'), $(this));
	   			 });
	   			 html.append(del, img, status);
	   			 $('.' + inputid + '-container').append(html);
				};
				reader.readAsDataURL(e.target.files[i]);
		}
		})(id);
		imageCount++;
	 	if (imageCount >= (max || 10)) {
	 		inputEle.disabled = true;
	 	}
	};
	//点击上传
	submitEle.onclick = function(e) {
		if (imageCount < min) {
			alert('至少上传' + min + '图片');
			return;
		}
		for (var i = 0; i < inputs.length; i++) {
			fileUpload(inputs[i].id);
		}
		inputs = [];
	};

	function removeinputs(id) {
		for (var index = 0; index < inputs.length; index++) {
				if (inputs[index].id == inputid + id) {
					inputs.splice(index, 1);
					break;
				}
			}
	}
	
	//上传文件
	    function fileUpload(fileElementId){
	    	$.ajaxFileUpload({
	    		url:uploadurl,//处理图片脚本
	    	    secureuri:false,
	    	    fileElementId:fileElementId,
	    	    dataType:"json",
	    	    success:function(data,status){
	    	    	if(data['state']=='SUCCESS'){
	    	    		$('.' + inputid + '-container .'+ fileElementId +' img').attr('data-src', data.url);
	    	    		$('.' + inputid + '-container .'+ fileElementId +' .status').text('上传成功');
	    	    		if (!$('#'+inputid + 'Id').val()) {
	    	    			$('#'+inputid + 'Id').val(data.url);
	    	    		} else {
	    	    			$('#'+ inputid + 'Id').val($('#'+ inputid + 'Id').val() + ';' + data.url);
	    	    		}
	    	    	}
	    	    },
	    	    error:function(data,state){
	    	    	alert(state);
	    	    }
	    	})
	    	return false;
	    }
	    
	  //删除图片
	    function deleteImage(url, ele) {
	    	if (!url) {
	    		//未上传过图片
	    		removeinputs(parseInt($(ele).attr("data-index")));
   				$(ele).parent().fadeOut(function() {
   					$(this).remove();
   					imageCount--;
   			    	if (imageCount < (max || 10)) {inputEle.disabled = false;}
   				});
	    	} else {
	    		$.ajax({
		 			url:deleteurl,
		 			data:{url:url},
		 			type:"GET",
		 			dataType:"json",
		 			success:function(data){
		 				console.log('删除成功');
		 				imageCount--;
	   			    	if (imageCount < (max || 10)) {inputEle.disabled = false;}
		 				var urls = $('#'+inputid + 'Id').val();
		 				if (urls.indexOf(url) != -1) {
		 					if (urls.indexOf(url) == 0) {
		 						urls = urls.replace(new RegExp(url + '(;?)', 'g'), "");
		 					} else {
		 						urls = urls.replace(new RegExp('(;?)' + url, 'g'), "");
		 					}
		 					$('#'+inputid + 'Id').val(urls);
		 				}
		 				$(ele).parent().fadeOut(function() {
		   					$(this).remove();
		   				});
		 			},
		 			error:function(data){
		 				alert('删除失败');
		 			}
		 		});
	    	}	
		}
	 //生成表单,用于上传图片
	function createInput(e, id, ele) {
		var input = ele.cloneNode(true);
		input.id = inputid + id;
		input.style.display = 'none';
		input.files = e.target.files;
		console.log(input.value);
		document.body.appendChild(input);
		return input;
	}
}