var spinner = $('.spinner');
var spinnerup = $('.spinnerup');
var spinnerdown = $('.spinnerdown');

spinner.change(function() {
	var max = parseInt($(this).attr('data-max')) || 9999999;
	var min = parseInt($(this).attr('data-min')) || 0;
	
	if ( $(this).val() != "" ) {
		var reg = /^[0-9]+(.[0-9]{1})?$/;
		if (reg.test($(this).val()) == false) {
			//alert('最多只能一位小数(非负数)');
			$(this).val(min);
		}
		
		if ( $(this).val() > max ) $(this).val(max);
		if ( $(this).val() < min ) $(this).val(min);
	}
});

spinnerup.click(function() {
	if ( $(this).attr("disabled") != undefined ) return;
	
	var max = parseInt($(this).parent().find('.spinner').attr('data-max')) || 9999999;
	var min = parseInt($(this).parent().find('.spinner').attr('data-min')) || 0;
	
	var shu = parseInt($(this).parent().find('.spinner').val()) || min;
	shu = shu + 1;
	if( shu <= max){
		$(this).parent().find('.spinner').val(shu);	
	}else{
		$(this).parent().find('.spinner').val(shu - 1);	
		alert('最大数只能是 ' + max);
	}
});

spinnerdown.click(function() {
	if ( $(this).attr("disabled") != undefined ) return;
	
	var max = parseInt($(this).parent().find('.spinner').attr('data-max')) || 9999999;
	var min = parseInt($(this).parent().find('.spinner').attr('data-min')) || 0;
	
	var shu = parseInt($(this).parent().find('.spinner').val()) || min;
	shu = shu - 1;
	
	if( shu < min){
		$(this).parent().find('.spinner').val(shu + 1);	
		alert('最小数只能是 ' + min);
	}else{
		$(this).parent().find('.spinner').val(shu);	
	}
});
