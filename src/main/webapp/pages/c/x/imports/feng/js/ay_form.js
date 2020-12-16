/**
 * 
 * 依赖
 * 
 * 
 * @description 用于form的js
 * 
 * 
 */
/**
 * 日志
 */
// console.log("加载js");
/**
 * @author 陈俊先
 * @description 隐藏列;
 * 
 * 用法;

 */

function ayFormOpenwindow(url,name,iWidth,iHeight) {
	try {
		var url;                                 //转向网页的地址;
		var name;                           //网页名称，可为空;
		var iWidth;                          //弹出窗口的宽度;
		var iHeight;                        //弹出窗口的高度;
		var iTop = (window.screen.availHeight-30-iHeight)/2;       //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth-10-iWidth)/2;           //获得窗口的水平位置;
		window.open(url,name,'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');

	} catch (e) {
		
		alert(e.stack);
	}
}


