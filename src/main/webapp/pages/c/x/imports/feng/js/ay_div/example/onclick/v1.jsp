
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	


http://localhost:9080/a/pages/c/x/imports/feng/js/ay_div/example/onclick/v1.jsp

<script type="text/javascript">
window.onload = function() {
	document.onclick = function(e) {
		var ele = e ? e.target : window.event.srcElement;
		if(ele.id !== 'the_div') {
			document.getElementById('the_div').style.display = 'none';

			// 以下为测试代码，可删除
			alert('一秒后 div 恢复');
			setTimeout(function() {
				document.getElementById('the_div').style.display = 'block';
			}, 1000);
			// 结束测试代码
		}
	};
};
</script>

<div id="the_div" style="width: 100px; height: 100px; background: #f00;"></div>

<!-- 以下为测试代码，可删除 -->
<p>p</p>
<p>p</p>
<p><a href="#">link</a></p>
<p>p</p>
<input type="text" />
<!-- 结束测试代码 -->