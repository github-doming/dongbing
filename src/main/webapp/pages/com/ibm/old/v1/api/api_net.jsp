<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<title>接口测试页</title>
</head>
<body>
	<div class="container-fluid">
		<h1>api接口说明</h1>
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th>接口名称</th>
					<th>接口调用示例</th>
                    <th>返回数据示例</th>
					<th>入参说明</th>
					<th>返参说明</th>
				</tr>
			</thead>
			<tbody>

			<tr>
				<td>1.session</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/net/user/token/session.do');"
						> http://IP:PORT/PROJECT/ibm/net/user/token/session.do</a>
					</p>
				</td>
                <td>
                    <p>{"code":"200","codeSys":"200","command":"","commandCn":"","data":"1FFD672B4AEA4395AED16D630955EFA4",</p>
                    <p>"desc":"","id":0,"message":"请求成功","messageSys":"请求成功","msg":"请求成功","msgEn":"Request success",</p>
                    <p>"state":"","success":true,"total":0}</p>
                </td>
				<td>

				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>data : "session",</p>
					<p>message : "请求结果消息"} </p>
				</td>
			</tr>

			<tr>
				<td>2.账号验证码图片</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/net/user/token/verify/img.do?json={session:\'FEA1A8BD4A424AB89D8F7715B0EA7527\'}');"
						> http://IP:PORT/PROJECT/ibm/net/user/token/verify/img.do</a>
					</p>
				</td>
                <td>

                </td>
				<td>
					<p>{"json": "session"}</p>
				</td>
				<td>
				</td>
			</tr>


			<tr>
				<td>3.登录</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/net/user/token/login.do?json={\'name\':\'zz123456\',\'password\':\'zz123456\',\'code\':\'1039\',\'session\':\'FEA1A8BD4A424AB89D8F7715B0EA7527\'}');"
						> http://IP:PORT/PROJECT/ibm/net/user/token/login.do</a>
					</p>
				</td>
                <td>
                    <p>{"code":"200","codeSys":"200","command":"","commandCn":"","data":</p>
					<p>{"planCodes":[{"PLAN_NAME_":"跟上期双面","PLAN_CODE_":"FOLLOW_TWO_SIDE"},</p>
					<p>{"PLAN_NAME_":"位置投注","PLAN_CODE_":"LOCATION_BET_NUMBER"}],</p>
                    <p>"token":"13ccd9881a704323974ccc5296c9dab5",</p>
                    <p>"gameCodes":[{"GAME_NAME_":"幸运飞艇","GAME_CODE_":"XYFT"},{"GAME_NAME_":"北京赛车","GAME_CODE_":"PK10"}]},</p>
                    <p>"desc":"","id":0,"message":"请求成功","messageSys":"请求成功","msg":"请求成功","msgEn":"Request success","state":"","success":true,"total":0}</p>
                </td>
				<td>
					<p>{"name": "用户名",</p>
					<p>password:"密码",</p>
					<p>code:"验证码",</p>
					<p>session:"session"}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>data : "{token:token,planCodes:{PLAN_NAME_:方案名称，PLAN_CODE_：方案code},gameCodes:{GAME_NAME_:游戏名称，GAME_CODE_：游戏code}}",</p>
					<p>message : "请求结果消息"} </p>
				</td>
			</tr>



			<tr>
				<td>4.用户登出</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/net/user/logout.do?json={\'token\':\'fef89690c1d340d6b92efc02964e74e6\'}');"
						> http://IP:PORT/PROJECT/ibm/net/user/logout.do</a>
					</p>
				</td>
                <td>

                </td>
				<td>
					<p>{"token": "token"}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>message : "请求结果消息"} </p>
				</td>
			</tr>


			<tr>
				<td>5.方案统计</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/net/planStatistics.dm?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'startDay\':\'2019-05-21\',\'endDay\':\'2019-05-21\',\'startTime\':\'14:30\',\'endTime\':\'17:30\',\'statisticalState\':\'CLOSE\',\'gameCode\':\'XYFT\',\'planCode\':\'FOLLOW_TWO_SIDE\',\'resetState\':\'OPEN\'}}');">
							http://IP:PORT/PROJECT/ibm/net/planStatistics.do </a>
					</p>
				</td>
				<td>
					<p>{"code":"200","codeSys":"200","command":"","commandCn":"","data":[[["2019-04-25",88,47,41,2520,257,257,-391,370,318,-242,7,</p>
					<p>"732622(15:30:00.0 3),732629(17:50:00.0 5),732637(20:30:00.0 7)",4,"732625(16:30:00.0 2),732634(19:30:00.0 3),</p>
					<p>732635(19:50:00.0 4)",0,""]],[["2019-04-25 14:50:00.0","732620","10,6,2,1,5,4,8,7,3,9","16",{"第7组":["第七名|小","10",</p>
					<p>"亏",-10,"1.983,"],"第14组":["第三名|单","10","亏",-10,"1.983,"]},20,-20,-20,-20]],["日期","投注次数","赢","亏",</p>
					<p>"总投注金额","输赢金额","最高盈利","最低亏损","单期最大下注","单期最高盈利","单期最低亏损","最大连错","前五连错",</p>
					<p>"最大连对","前五连对","炸人次数","炸人凶手"],["日期","期数","开奖号码","冠亚","投注","金额","中奖","资金变化",</p>
					<p>"资金赔率","投注","金额","中奖","资金变化","资金赔率","投注","金额","中奖","资金变化","资金赔率","投注","金额","中奖",</p>
					<p>"资金变化","资金赔率","投注","金额","中奖","资金变化","资金赔率","投注","金额","中奖","资金变化","资金赔率","当期下注额",</p>
					<p>"当期输赢","当日输赢","累计金额"]],"desc":"","id":0,"message":"请求成功","messageSys":"请求成功",</p>
					<p>"msg":"请求成功","msgEn":"Request success","state":"","success":true,"total":0}</p>
				</td>
				<td>
					<p>{"token": "token",</p>
					<p>data:{startDay:开始日期，endDay：结束日期，startTime：开始时间，endTime：结束时间，statisticalState：</p>
					<p>统计状态，gameCode：游戏code,planCode:方案code,resetState:重置资金状态}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>data : "{array[0]:统计结果，array[1]：统计明细，array[2]:统计结果标题，array[3]:统计明细标题}",</p>
					<p>message : "请求结果消息"} </p>
				</td>
			</tr>











			</tbody>
		</table>
	</div>
</body>
<script type="text/javascript">
    function findUrl(url) {
        url = encodeURI(url);
        window.open(url,"_blank");
        return url;
    }
</script>
</html>
