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
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/user/token/session.do');"
						> http://IP:PORT/PROJECT/ibm/pc/user/token/session.do</a>
					</p>
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
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/user/token/verify/img.do?json={session:\'C301AAF0D1204A859D07DDB770FC50F9\'}');"
						> http://IP:PORT/PROJECT/ibm/pc/user/token/verify/img.do</a>
					</p>
				</td>
				<td>
					<p>{"json": "session"}</p>
				</td>
				<td>
				</td>
			</tr>

			<tr>
				<td>3.注册</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/user/token/register.do?json={\'name\':\'867867\',\'password\':\'123321\',\'code\':\'1669\',\'session\':\'03A12283588D44F1B32D4812C559CFCE\'}');"
						> http://IP:PORT/PROJECT/ibm/pc/user/token/register.do</a>
					</p>
				</td>
				<td>
					<p>{"name": "用户名",</p>
					<p>password:"密码",</p>
					<p>code:"验证码",</p>
					<p>session:"session"}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>data : "token",</p>
					<p>message : "请求结果消息"} </p>
				</td>
			</tr>

			<tr>
				<td>4.登录</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/user/token/login.do?json={\'name\':\'867867\',\'password\':\'123321\',\'code\':\'1669\',\'session\':\'03A12283588D44F1B32D4812C559CFCE\'}');"
						> http://IP:PORT/PROJECT/ibm/pc/user/token/login.do</a>
					</p>
				</td>
				<td>
					<p>{"name": "用户名",</p>
					<p>password:"密码",</p>
					<p>code:"验证码",</p>
					<p>session:"session"}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>data : "token",</p>
					<p>message : "请求结果消息"} </p>
				</td>
			</tr>


			<tr>
				<td>5.修改密码</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/user/updatePassword.do?json={\'oldPassword\':\'867867\',\'newPassword\':\'123321\'}');"
						> http://IP:PORT/PROJECT/ibm/pc/user/updatePassword.do</a>
					</p>
				</td>
				<td>
					<p>{"oldPassword": "旧密码",</p>
					<p>newPassword:"新密码"}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>message : "请求结果消息"} </p>
				</td>
			</tr>


			<tr>
				<td>6.用户登出</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/user/logout.do?json={\'token\':\'09a5a86553a140e7a7254b3a5e389721\'}');"
						> http://IP:PORT/PROJECT/ibm/pc/user/logout.do</a>
					</p>
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
				<td>
					<p>7.查看消息</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/cms_message/list.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'pageIndex\':\'1\',\'pageSize\':\'10\'}}');">
							http://IP:PORT/PROJECT/ibm/pc/cms_message/list.do </a>
					</p>
				</td>
				<td>
					<p>{token : "用户token",data:{pageIndex:"当前页",pageSize:"每页显示记录数"}}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>data: {pageCore:{
						hasNextPage: "是否有下一页",
						hasPreviousPage:"是否有上一页",
						list: [ { TITLE_: "消息标题", IBM_CMS_MESSAGE_CONTENT_ID_: "消息ID", READING_STATE_: 阅读状态（UNREAD 未读，READ 已读）},],</p>
					<p>pageIndex: 当前页, pageSize: 每页显示记录数, totalCount: 总记录数, totalPages: 总页数 },unReadcount:未读条数}</p>
					<p>message : "请求结果消息"}</p>
				</td>
			</tr>


			<tr>
				<td>
					<p>8.查看消息内容</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/cms_message/getContent.do?json={\'token\':\'49b8e0763c94484da64a40329d2f6e3a\',data:{\'IBM_CMS_MESSAGE_CONTENT_ID_\':\'8c1a864f9ef14ec0b6d280903ff818f6\'}}');">
							http://IP:PORT/PROJECT/ibm/pc/cms_message/getContent.do </a>
					</p>
				</td>
				<td>
					<p>{token : "用户token",data {IBM_CMS_MESSAGE_CONTENT_ID_:"消息ID"}}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>data:data: {
						CONTENT_: "消息内容",
						TITLE_: "消息标题",
						CREATE_TIME_: "创建时间"
						}</p>
					<p>message : "请求结果消息"}</p>
				</td>
			</tr>


			<tr>
				<td>
					<p>9.删除消息</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/cms_message/del.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'IBM_CMS_MESSAGE_CONTENT_ID_\':\'38edc5b655344f9c98ad5c8404db7307\'}}');">
							http://IP:PORT/PROJECT/ibm/pc/cms_message/del.do </a>
					</p>
				</td>
				<td>
					<p>{token : "用户token",data {IBM_CMS_MESSAGE_CONTENT_ID_:"用户消息ID"}}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>message : "请求结果消息"}</p>
				</td>
			</tr>

			<tr>
				<td>
					<p>10.清空消息</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/cms_message/delAll.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\'}');">
							http://IP:PORT/PROJECT/ibm/pc/cms_message/delAll.do </a>
					</p>
				</td>
				<td>
					<p>{token : "用户token"}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>message : "请求结果消息"}</p>
				</td>
			</tr>


			<tr>
				<td>
					<p>11.一键已阅</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/cms_message/readAll.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\'}');">
							http://IP:PORT/PROJECT/ibm/pc/cms_message/readAll.do </a>
					</p>
				</td>
				<td>
					<p>{token : "用户token"}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>message : "请求结果消息"}</p>
				</td>
			</tr>



			<tr>
				<td>
					<p>12.获取开奖信息</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_exec_bet_item/getRepDrawResult.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'HANDICAP_CODE_\':\'WS2\',\'GAME_CODE_\':\'PK10\'}}');">
							http://IP:PORT/PROJECT/ibm/pc/ibm_exec_bet_item/getRepDrawResult.do </a>
					</p>
				</td>
				<td>
					<p>{token : "用户token",data:{HANDICAP_CODE_:"盘口code",</p>
					<p>GAME_CODE_:"游戏code"}}</p>
				</td>
				<td>
					<p>{sysEnum : "状态码",</p>
					<p>success : "请求状态",</p>
					<p>data : [map:{page:{PERIOD_:期数，PLAN_GROUP_DESC_：方案组说明，FUND_T_：金额，BET_CONTENT_：投注内容，EXEC_STATE_：投注状态，
						PROFIT_T_：盈亏，ODDS_T_：赔率},pK10List:{PERIOD_：期数，DRAW_TIME_：开奖时间，DRAW_NUMBER_：开奖号码}}]</p>
					<p>enum : "请求结果消息"}</p>

				</td>
			</tr>


			<tr>
				<td>
					<p>13.手动投注</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_exec_bet_item/handleBet.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'betItem\':[{\'betPosition\':\'2\',\'betOption\':\'1,2,3,4,5,6,7,8,9,10,大,小,单,双,龙,虎\'},{\'betPosition\':\'7\',\'betOption\':\'1,2,3,4,5,6,7,8,9,10,大,小,单,双\'},{\'betPosition\':\'9\',\'betOption\':\'1,2,3,4,5,6,7,8,9,10,大,小,单,双\'}],\'GAME_CODE_\':\'PK10\',\'PERIOD_\':\'720720\',\'FUND_T_\':\'3\',\'EXEC_STATE_\':\'VR\',\'HANDICAP_MEMBER_ID_\':\'f22a3e4c56cc4612b68d991ab2391a73\'}}');">
							http://IP:PORT/PROJECT/ibm/pc/ibm_exec_bet_item/handleBet.do </a>
					</p>
				</td>
				<td>
					<p>{token : "用户token",data:{betPosition:"投注位置",betOption:"投注选项",GAME_CODE_:"游戏CODE",</p>
					<p>PERIOD_:"期数",FUND_T_:"金额",HANDICAP_MEMBER_ID_:"盘口会员ID",EXEC_STATE_:"投注状态"}}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>message : "请求结果消息"}</p>
				</td>
			</tr>



			<tr>
				<td>14.盘口游戏</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_handicap_game/handicapGame.do?json={\'token\':\'1\',data:{\'HANDICAP_MEMBER_ID_\':\'f22a3e4c56cc4612b68d991ab2391a71\'}}');"
						> http://IP:PORT/PROJECT/ibm/pc/ibm_handicap_game/handicapGame.do</a>
					</p>
				</td>
				<td>
					<p>{token : "用户token",data:</p>
					<p>{HANDICAP_MEMBER_ID_ : 盘口会员id}}</p>
				</td>
				<td>
					<p>{sysEnum : "状态码",</p>
					<p>success : "请求状态",</p>
					<p>data : [{IBM_HM_GAME_SET_ID_:IBM_盘口会员游戏设置主键,HANDICAP_MEMBER_ID_:盘口会员主键,HANDICAP_GAME_ID_:盘口游戏主键,HANDICAP_ID_:盘口主键,
						USER_ID_:用户主键,GAME_ID_:游戏主键,BET_STATE_:投注状态,BET_MODE_:投注模式,BET_AUTO_STOP_:自动停止投注,BET_AUTO_STOP_TIME_:自动停止投注时间,
						BET_AUTO_START_:自动开始投注,BET_AUTO_START_TIME_:自动开始投注时间,INCREASE_STATE_:新增状态,INCREASE_STOP_TIME_:停止新增时间,
						BET_SECOND_:每期投注时刻,SPLIT_TWO_SIDE_AMOUNT_:双面分投额度,SPLIT_NUMBER_AMOUNT_:号码分投额度}}],</p>
					<p>enum : "请求结果消息"}</p>
				</td>
			</tr>


			<tr>
				<td>15.刷新投注信息</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_handicap_game/refreshBetInfo.dm?json={\'token\':\'7103e85a9866411f81814247bdbc29dd\',data:{\'HANDICAP_MEMBER_ID_\':\'850cd3e4fbc842b8be176632d0ad8259\',\'GAME_CODE_\':\'PK10\',\'checkTime\':\'\'}}');"
						> http://IP:PORT/PROJECT/ibm/pc/ibm_handicap_game/refreshBetInfo.dm</a>
					</p>
				</td>
				<td>
					<p>{token : "用户token",data:</p>
					<p>{HANDICAP_MEMBER_ID_ : 盘口会员id,GAME_CODE_:游戏code,checkTime:校验时间}}</p>
				</td>
				<td>
					<p>{sysEnum : "状态码",</p>
					<p>success : "请求状态",</p>
					<p>data : {newTime:时间，newBetInfoList:新投注信息,amount:投注总额,number:投注总数,period:期数，oldBetInfoList：旧投注信息</p>
					<p>enum : "请求结果消息"}</p>
				</td>
			</tr>


			<tr>
				<td>16.盘口会员登录</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_handicap_member/ibmHandicapMemberLogin.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'HANDICAP_MEMBER_ID_\':\'f22a3e4c56cc4612b68d991ab2391a71\',\'HANDICAP_CODE_\':\'WS2\',\'SAVE_ACCOUNT_\':\'TRUE\',\'LANDED_TIME_\':\'10:10:10\',\'HANDICAP_ITEM_ID_\':\'acc72a41b8634d778b73678b822be142\'}}');"
						> http://IP:PORT/PROJECT/ibm/ibm_handicap_member/t/IbmHandicapMemberLogin.do</a>
					</p>
				</td>
				<td>
					<p>{"token": "用户token"}</p>
					<p>data{HANDICAP_MEMBER_ID_ : 盘口会员id</p>
					<p>HANDICAP_CODE_:"盘口编码"}</p>
					<p>SAVE_ACCOUNT_:"是否保存账户信息"}</p>
					<p>HANDICAP_ITEM_ID_:"盘口详情id"}</p>
					<p>LANDED_TIME_:"定时登录时间"}</p>
				</td>
				<td>
					<p>{sysEnum : "状态码",</p>
					<p>success : "请求状态",</p>
					<p>enum : "请求结果消息"}</p>

				</td>
			</tr>


			<tr>
				<td>
					<p>17.退出登录</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_handicap_member/ibmHandicapMemberLogout.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'HANDICAP_MEMBER_ID_\':\'f22a3e4c56cc4612b68d991ab2391a71\'}}');">
							http://IP:PORT/PROJECT/ibm/pc/ibm_handicap_member/ibmHandicapMemberLogout.do</a>
					</p>
				</td>
				<td>
					<p>{token : "用户token",data:{HANDICAP_MEMBER_ID_:"盘口会员ID"}}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>message : "请求结果消息"}</p>
				</td>
			</tr>


			<tr>
				<td>
					<p>18.盘口会员获取所有账号</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_handicap_member/allAccount.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'HANDICAP_CODE_\':\'WS2\'}}');">
							http://IP:PORT/PROJECT/ibm/pc/ibm_handicap_member/allAccount.do</a>
					</p>
				</td>
				<td>
					<p>{token : "用户token",data:{HANDICAP_CODE_:"盘口code"}}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>message : "请求结果消息"}</p>
				</td>
			</tr>


			<tr>
				<td>19.刷新盘口用户信息</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_handicap_member/refreshMemberInfo.dm?json={\'token\':\'6691c3034bc24180bdadc3ec01b9e0c8\',data:{\'HANDICAP_MEMBER_ID_\':\'ce677376243c42a4bf6844892940eae6\'}}');"
						> http://IP:PORT/PROJECT/ibm/pc/ibm_handicap_member/refreshMemberInfo.dm</a>
					</p>
				</td>
				<td>
					<p>{"token": "用户token"}</p>
					<p>data{HANDICAP_MEMBER_ID_:"盘口会员id"</p>
				</td>
				<td>
					<p>{sysEnum : "状态码",</p>
					<p>success : "请求状态",</p>
					<p>{"data":{"realBetProfit":{"HANDICAP_MEMBER_ID_":"盘口会员id","PROFIT_T_":盈亏信息,"BET_FUNDS_T_":投注总额,"BET_LEN_":投注总数},"vrBetProfit":{"HANDICAP_MEMBER_ID_":"盘口会员id","PROFIT_T_":盈亏信息,"BET_FUNDS_T_":投注总额,"BET_LEN_":投注总数},</p>
					<p>"memberInfoJson":{"MEMBER_INFO_":"盘口会员信息","LOGIN_STATE_":登录状态},"code":处理code,"info":处理内容信息,gameCode:游戏code}}</p>
					<p>enum : "请求结果消息"}</p>

				</td>
			</tr>


			<tr>
				<td>20.选择盘口用户账号</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/handicap_user/selectHandicapAccount.do?json={\'token\':\'2cf52dfeef354207b892a127fefe9edd\',data:{\'HANDICAP_CODE_\':\'WS2\'}}');"
						> http://IP:PORT/PROJECT/ibm/pc/handicap_user/selectHandicapAccount.do</a>
					</p>
				</td>
				<td>
					<p>{"token": "用户token"}</p>
					<p>data{HANDICAP_MEMBER_ID_:"盘口会员id"</p>
				</td>
				<td>
					<p>{sysEnum : "状态码",</p>
					<p>success : "请求状态",</p>
					<p>{"code":"200","codeSys":"200","command":"","commandCn":"","data":{"realBetProfit":{"HANDICAP_MEMBER_ID_":"盘口会员id","PROFIT_T_":盈亏信息,"BET_FUNDS_T_":投注总额,"BET_LEN_":投注总数},"vrBetProfit":{"HANDICAP_MEMBER_ID_":"盘口会员id","PROFIT_T_":盈亏信息,"BET_FUNDS_T_":投注总额,"BET_LEN_":投注总数}},"desc":"","id":0,"message":"请求成功","messageSys":"请求成功","msg":"请求成功","msgEn":"Request success","state":"","success":true,"total":0}</p>
					<p>enum : "请求结果消息"}</p>

				</td>
			</tr>


			<tr>
				<td>
					<p>21.识别验证码</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/handicap_user/discernHandicap.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'HANDICAP_URL\':\'http://sf33.qr68.us/\',\'HANDICAP_CAPTCHA\':\'az311\'}}');"
						> http://IP:PORT/PROJECT/ibm/pc/handicap_user/discernHandicap.do</a>
					</p>
				</td>
				<td>
					<p>{token : "用户token",data:</p>
					<p>{HANDICAP_URL : 盘口路径,</p>
					<p>HANDICAP_CAPTCHA:盘口验证码}}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>data : 见打开盘口接口,</p>
					<p>message : "请求结果消息"}</p>
				</td>
			</tr>


			<tr>
				<td>
					<p>22.识别验证码——盘口会员登录后</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/handicap_user/checkHandicap.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'HANDICAP_MEMBER_ID_\':\'f22a3e4c56cc4612b68d991ab2391a71\',\'HANDICAP_CODE_\':\'IDC\',\'HANDICAP_URL\':\'http://sf33.qr68.us/\',\'HANDICAP_CAPTCHA\':\'az311\'}}');"
						> http://IP:PORT/PROJECT/ibm/pc/handicap_user/checkHandicap.do</a>
					</p>
				</td>
				<td>
					<p>{token : "用户token",data:</p>
					<p>{HANDICAP_MEMBER_ID_:"盘口会员ID",</p>
					<p>HANDICAP_CODE_:"盘口code",</p>
					<p>HANDICAP_URL : 盘口路径,</p>
					<p>HANDICAP_CAPTCHA:盘口验证码}}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>data : 见打开盘口接口,</p>
					<p>message : "请求结果消息"}</p>
				</td>
			</tr>


			<tr>
				<td>
					<p>23.打开盘口</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/handicap_user/openHandicap.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'HANDICAP_MEMBER_ID_\':\'108a8cb156db4d898ce97d7d79501dd3\',\'HANDICAP_CODE_\':\'WS2\'}}');">
							http://IP:PORT/PROJECT/ibm/pc/handicap_user/openHandicap.do </a>
					</p>
				</td>
				<td>
					<p>{token : "用户token",data:</p>
					<p>{HANDICAP_MEMBER_ID_:盘口会员ID,</p>
					<p>HANDICAP_CODE_:盘口code}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>"data":{</p>
					<p>"handicapMemberInfo":[ </p>
					<p> {handicapMemberId:"盘口会员ID", </p>
					<p> "set":盘口设置信息:{ </p>
					<p> "PROFIT_LIMIT_MAX_T_":"止盈上限", </p>
					<p> "RESET_TYPE_":"每天重置：1，自定义重置：2", </p>
					<p> "RESET_LOSS_MIN_T_":"重置亏损下限", </p>
					<p> "BET_RATE_T_":投注比例, </p>
					<p> "LANDED_TIME_":定时登陆, </p>
					<p> "BLAST_STOP_":"炸停止", </p>
					<p> "LOSS_LIMIT_MIN_T_":止损下限, </p>
					<p> "MEMBER_PASSWORD_":"密码", </p>
					<p> "BET_RECORD_ROWS_":投注记录保存行数, </p>
					<p> "MEMBER_ACCOUNT_":"账号", </p>
					<p> "PROFIT_LIMIT_MIN_T_":止盈下限, </p>
					<p> "handicapMemberId":"盘口会员ID", </p>
					<p> "RESET_PROFIT_MAX_T_":重置盈利上限, </p>
					<p> "BET_MERGER_":"投注合并" </p>
					<p> "isStopProfit":"是否显示盈利下限"（TRUE/FALSE） </p>
					<p> "SAVE_ACCOUNT_":"是否保存账户信息"（TRUE/FALSE） </p>
					<p> "NICKNAME_":"账户" </p>
					<p> "BALANCE_":"余额" </p>
					<p> }, </p>
					<p> "handicapInfo":{ </p>
					<p> "vrBet":模拟投注信息, </p>
					<p> "realBet":真实投注信息， </p>
					<p> "NICKNAME_":"账户", </p>
					<p> "BALANCE_":"余额" </p>
					<p> }, </p>
					<p> }], </p>
					<p> "allAccount":[ </p>
					<p> { </p>
					<p> "MEMBER_ACCOUNT_":"会员账号", </p>
					<p> "IBM_HANDICAP_MEMBER_ID_":"盘口会员id" </p>
					<p> } </p>
					<p> ], </p>
					<p> "handicapItemId":"盘口详情id"
					<p>},</p>
					<p>message : "请求结果消息"}</p>
				</td>
			</tr>



			<tr>
				<td>24.盘口游戏设置</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_hm_game_set/handicapGameSet.do?json={\'token\':\'0a575f461e854486a34f729816c3d144\',data:{\'HANDICAP_MEMBER_ID_\':\'850cd3e4fbc842b8be176632d0ad8259\',\'GAME_CODE_\':\'XYFT\',\'BET_MODE_\':\'REAL\',\'INCREASE_STATE_\':\'NONE\',\'INCREASE_STOP_TIME_\':\'NaN\',\'BET_SECOND_\':\'120\',\'SPLIT_TWO_SIDE_AMOUNT_\':\'0\',\'SPLIT_NUMBER_AMOUNT_\':\'0\'}}');"
						> http://IP:PORT/PROJECT/ibm/pc/ibm_hm_game_set/handicapGameSet.do</a>
					</p>
				</td>
				<td>
					<p>{"token": "用户token"}</p>
					<p>data{HANDICAP_MEMBER_ID_:"盘口会员id"</p>
					<p>GAME_CODE_:"游戏code"</p>
					<p>BET_MODE_:"投注模式"</p>
					<p>INCREASE_STATE_:"新增状态"</p>
					<p>INCREASE_STOP_TIME_:"停止新增时间"</p>
					<p>BET_SECOND_:"每期投注时刻"</p>
					<p>SPLIT_TWO_SIDE_AMOUNT_:"双面分投额度"</p>
					<p>SPLIT_NUMBER_AMOUNT_:"号码分投额度"}</p>
				</td>
				<td>
					<p>{sysEnum : "状态码",</p>
					<p>success : "请求状态",</p>
					<p>enum : "请求结果消息"}</p>
				</td>
			</tr>


			<tr>
				<td>25.盘口游戏设置-投注状态</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_hm_game_set/betStateSet.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'HANDICAP_MEMBER_ID_\':\'f22a3e4c56cc4612b68d991ab2391a71\',\'GAME_CODE_\':\'PK10\',\'BET_STATE_\':\'FALSE\'}}');"
						> http://IP:PORT/PROJECT/ibm/pc/ibm_hm_game_set/betStateSet.do</a>
					</p>
				</td>
				<td>
					<p>{"token": "用户token",data:{HANDICAP_MEMBER_ID_:"盘口会员ID",GAME_CODE_:"游戏CODE",BET_STATE_:"投注状态"}}</p>
				</td>
				<td>
					<p>{sysEnum : "状态码",</p>
					<p>success : "请求状态",</p>
					<p>enum : "请求结果消息"}</p>

				</td>
			</tr>


			<tr>
				<td>26.盘口游戏设置-自动开始投注</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_hm_game_set/autoStartSet.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'HANDICAP_MEMBER_ID_\':\'f22a3e4c56cc4612b68d991ab2391a71\',\'GAME_CODE_\':\'PK10\',\'BET_AUTO_START_\':\'TRUE\',\'BET_AUTO_START_TIME_\':\'12:30:00\'}}');"
						> http://IP:PORT/PROJECT/ibm/pc/ibm_hm_game_set/autoStartSet.do</a>
					</p>
				</td>
				<td>
					<p>{"token": "用户token",data:{HANDICAP_MEMBER_ID_:"盘口会员ID",GAME_CODE_:"游戏CODE",BET_AUTO_START_:"自动开始投注",BET_AUTO_START_TIME_:"自动开始投注时间"}}</p>
				</td>
				<td>
					<p>{sysEnum : "状态码",</p>
					<p>success : "请求状态",</p>
					<p>enum : "请求结果消息"}</p>

				</td>
			</tr>


			<tr>
				<td>27.盘口游戏设置-自动停止投注</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_hm_game_set/autoStopSet.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'HANDICAP_MEMBER_ID_\':\'f22a3e4c56cc4612b68d991ab2391a71\',\'GAME_CODE_\':\'PK10\',\'BET_AUTO_STOP_\':\'TRUE\',\'BET_AUTO_STOP_TIME_\':\'12:30:00\'}}');"
						> http://IP:PORT/PROJECT/ibm/pc/ibm_hm_game_set/autoStopSet.do</a>
					</p>
				</td>
				<td>
					<p>{"token": "用户token",data:{HANDICAP_MEMBER_ID_:"盘口会员ID",GAME_CODE_:"游戏CODE",BET_AUTO_STOP_:"自动停止投注",</p>
					<p>BET_AUTO_STOP_TIME_:"自动停止投注时间"}}</p>
				</td>
				<td>
					<p>{sysEnum : "状态码",</p>
					<p>success : "请求状态",</p>
					<p>enum : "请求结果消息"}</p>

				</td>
			</tr>


			<tr>
				<td>
					<p>28.重置方案</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_hm_plan_exec_item/replay.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'GAME_CODE_\':\'PK10\',\'PLAN_CODE_\':\'LOCATION_BET_NUMBER,FOLLOW_TWO_SIDE\'}}');">
							http://IP:PORT/PROJECT/ibm/pc/ibm_hm_plan_exec_item/replay.do </a>
					</p>
				</td>
				<td>
					<p>{token : "用户token",data:{GAME_CODE_:"游戏CODE",PLAN_CODE_:"方案CODE1，方案CODE2，...，方案CODEn"}}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>message : "请求结果消息"}</p>
				</td>
			</tr>

			<tr>
				<td>
					<p>29.重置所有方案</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_hm_plan_exec_item/replayAll.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'PLAN_ID_\':\'abe15fe213444d14a6318ee8e720fddf,b066108af2cd47bd81fb13077484cb26\'}}');">
							http://IP:PORT/PROJECT/ibm/ibm_hm_plan_exec_item/w/replay_all.do </a>
					</p>
				</td>
				<td>
					<p>{token : "用户token",data:{PLAN_ID_:"方案ID1，方案ID2，...，方案IDn"}}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>message : "请求结果消息"}</p>
				</td>
			</tr>

			<tr>
				<td>30.刷新盈亏信息</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_hm_profit/refreshProfit.do?json={\'token\':\'a23eb067aa1e4263812aa6a0c9dbe382\',data:{\'HANDICAP_MEMBER_ID_\':\'b521adb9a85f46669e7d2e79dacb56a8\'}}');"
						> http://IP:PORT/PROJECT/ibm/pc/ibm_hm_profit/refreshProfit.do</a>
					</p>
				</td>
				<td>
					<p>{"token": "用户token"}</p>
					<p>data{HANDICAP_MEMBER_ID_:"盘口会员id"</p>
				</td>
				<td>
					<p>{sysEnum : "状态码",</p>
					<p>success : "请求状态",</p>
					<p>{"code":"200","codeSys":"200","command":"","commandCn":"","data":{"realBetProfit":{"HANDICAP_MEMBER_ID_":"盘口会员id","PROFIT_T_":盈亏信息,"BET_FUNDS_T_":投注总额,"BET_LEN_":投注总数},"vrBetProfit":{"HANDICAP_MEMBER_ID_":"盘口会员id","PROFIT_T_":盈亏信息,"BET_FUNDS_T_":投注总额,"BET_LEN_":投注总数}},"desc":"","id":0,"message":"请求成功","messageSys":"请求成功","msg":"请求成功","msgEn":"Request success","state":"","success":true,"total":0}</p>
					<p>enum : "请求结果消息"}</p>

				</td>
			</tr>


			<tr>
				<td>31.会员盘口设置</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_hm_set/handicapMemberSet.do?json={\'token\':\'1\',data:{\'HANDICAP_MEMBER_ID_\':\'f22a3e4c56cc4612b68d991ab2391a73\',\'LANDED_TIME_\':\'11:11:30\',\'BET_RECORD_ROWS_\':\'100\',\'BET_RATE_T_\':\'100\',\'PROFIT_LIMIT_MAX_T_\':\'10000\',\'PROFIT_LIMIT_MIN_T_\':\'10000\',\'LOSS_LIMIT_MIN_T_\':\'-10000\',\'RESET_TYPE_\':\'1\',\'RESET_PROFIT_MAX_T_\':\'1000\',\'RESET_LOSS_MIN_T_\':\'-1000\',\'BLAST_STOP_\':\'OPEN\',\'BET_MERGER_\':\'CLOSE\'}}');"
						> http://IP:PORT/PROJECT/ibm/pc/ibm_hm_set/handicapMemberSet.do</a>
					</p>
				</td>
				<td>
					<p>{"token": "用户token"}</p>
					<p>data{HANDICAP_MEMBER_ID_:"盘口会员id"</p>
					<p>LANDED_TIME_:"定时登录"</p>
					<p>BET_RECORD_ROWS_:"投注记录保存行数"</p>
					<p>BET_RATE_T_:"投注比例"</p>
					<p>PROFIT_LIMIT_MAX_T_:"止盈上限"</p>
					<p>PROFIT_LIMIT_MIN_T_:"止盈下限"</p>
					<p>LOSS_LIMIT_MIN_T_:"止损下限"</p>
					<p>RESET_TYPE_:"重置类型"</p>
					<p>RESET_PROFIT_MAX_T_:"重置盈利上限"</p>
					<p>RESET_LOSS_MIN_T_:"重置亏损下限"</p>
					<p>BLAST_STOP_:"炸停止"</p>
					<p>BET_MERGER_:"投注合并"}</p>
				</td>
				<td>
					<p>{sysEnum : "状态码",</p>
					<p>success : "请求状态",</p>
					<p>enum : "请求结果消息"}</p>

				</td>
			</tr>

			<tr>
				<td>
					<p>32.首页主体框架</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/home/main.do?json={\'token\':\'1\'}');"
						> http://IP:PORT/PROJECT/ibm/pc/home/main.do</a>
					</p>
				</td>
				<td>
					<p>{token : "用户token"}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>data : {handicapInfo:{HANDICAP_NAME_:盘口名称,HANDICAP_CODE_:盘口编码,HANDICAP_ICON_:盘口图标,SN_:盘口顺序},</p>
					<p>gameInfo:{GAME_NAME_:游戏名称,GAME_CODE_:游戏code,ICON_:游戏图标},endTimeLong:软件到期时间戳,unread:未读消息数 },</p>
					<p>message : "请求结果消息"} </p>
				</td>
			</tr>


			<tr>
				<td>
					<p>33.首页主体信息</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/home/page.do?json={\'token\':\'1\'}');"
						> http://IP:PORT/PROJECT/ibm/pc/home/page.do</a>
					</p>
				</td>
				<td>
					<p>{token : "用户token"}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>data : {userInfo:{NICKNAME_:用户,POINT_:点数,软件到期时间戳},</p>
					<p>cmsInfo:[{IBM_CMS_TOPIC_ID_:消息id,TITLE_:消息标题,CREATE_TIME_LONG_:创建时间戳}],</p>
					<p>recentLogin:[{HANDICAP_CODE_:盘口code,HANDICAP_NAME_:盘口名称}],</p>
					<p>onHosting:[{HANDICAP_CODE_:盘口code,HANDICAP_NAME_:盘口名称}],</p>
					<p>gamePlan:[{GAME_NAME_:游戏名称,GAME_CODE_:游戏code,ICON_游戏图标:,SN_:游戏排行,</p>
					<p>planList:[{PLAN_NAME_:方案名称,STATE_:方案状态}]}]},</p>
					<p>message : "请求结果消息"} </p>
				</td>
			</tr>





			<tr>
				<td>
					<p>34.方案列表</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_plan_user/list.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'GAME_CODE_\':\'PK10\',\'PLAN_CODE_\':\'PLAN_LOCATION_BET_NUMBER,FOLLOW_TWO_SIDE\',\'STATE_\':\'STOP\'}}');">
							http://IP:PORT/PROJECT/ibm/pc/ibm_plan_user/list.do </a>
					</p>
				</td>
				<td>
					<p>{token : "用户token",data:</p>
					<p>{GAME_CODE_:方案详情id,PLAN_CODE_:编码,</p>
					<p>方案数据整体,STATE_:状态(OPEN,STOP,DEL)}}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>message : "请求结果消息"}</p>
				</td>
			</tr>

			<tr>
				<td>
					<p>35.方案详情列表</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_plan_user/show.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'GAME_CODE_\':\'PK10\',\'PLAN_CODE_\':\'PLAN_LOCATION_BET_NUMBER,FOLLOW_TWO_SIDE\',\'STATE_\':\'STOP\'}}');">
							http://IP:PORT/PROJECT/ibm/pc/ibm_plan_user/show.do </a>
					</p>
				</td>
				<td>
					<p>{token : "用户token",data:</p>
					<p>{GAME_CODE_:方案详情id,PLAN_CODE_:编码,</p>
					<p>方案数据整体,STATE_:状态(OPEN,STOP,DEL)}}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>message : "请求结果消息"}</p>
				</td>
			</tr>


			<tr>
				<td>
					<p>36.方案详情修改</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_plan_user/edit.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'GAME_CODE_\':\'PK10\',\'PLAN_CODE_\':\'PLAN_LOCATION_BET_NUMBER,FOLLOW_TWO_SIDE\',\'STATE_\':\'STOP\'}}');">
							http://IP:PORT/PROJECT/ibm/pc/ibm_plan_user/edit.do </a>
					</p>
				</td>
				<td>
					<p>{token : "用户token",data:</p>
					<p>{GAME_CODE_:方案详情id,PLAN_CODE_:编码,</p>
					<p>方案数据整体,STATE_:状态(OPEN,STOP,DEL)}}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>message : "请求结果消息"}</p>
				</td>
			</tr>


			<tr>
				<td>
					<p>37.方案详情状态修改</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_plan_user/editState.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'GAME_CODE_\':\'PK10\',\'PLAN_CODE_\':\'PLAN_LOCATION_BET_NUMBER,FOLLOW_TWO_SIDE\',\'STATE_\':\'STOP\'}}');">
							http://IP:PORT/PROJECT/ibm/pc/ibm_plan_user/editState.do </a>
					</p>
				</td>
				<td>
					<p>{token : "用户token",data:</p>
					<p>{GAME_CODE_:方案详情id,PLAN_CODE_:编码,</p>
					<p>方案数据整体,STATE_:状态(OPEN,STOP,DEL)}}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>message : "请求结果消息"}</p>
				</td>
			</tr>



			<tr>
				<td>
					<p>38.修改选中方案详情状态</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_plan_user/editAllState.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'GAME_CODE_\':\'PK10\',\'PLAN_CODE_\':\'PLAN_LOCATION_BET_NUMBER,FOLLOW_TWO_SIDE\',\'STATE_\':\'STOP\'}}');">
							http://IP:PORT/PROJECT/ibm/pc/ibm_plan_user/editAllState.do </a>
					</p>
				</td>
				<td>
					<p>{token : "用户token",data:</p>
					<p>{GAME_CODE_:方案详情id,PLAN_CODE_:编码,</p>
					<p>方案数据整体,STATE_:状态(OPEN,STOP,DEL)}}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>message : "请求结果消息"}</p>
				</td>
			</tr>


			<tr>
				<td>39.获取盘口所有方案</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_plan_user/listByHandicap.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',\'data\':{\'HANDICAP_CODE_\':\'WS2\'}}}');"
						> http://IP:PORT/PROJECT/ibm/pc/ibm_plan_user/listByHandicap.do</a>
					</p>
				</td>
				<td>
					<p>{"token": "用户token",data{"HANDICAP_CODE_":"盘口code"}}</p>
				</td>
				<td>
					<p>{sysEnum : "状态码",</p>
					<p>success : "请求状态",</p>
					<p>enum : "请求结果消息"}</p>

				</td>
			</tr>


			<tr>
				<td>
					<p>40.用户反馈</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
						   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_sys_suggest/save.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'SUGGEST_CONTENT_\':\'test\'}}');">
							http://IP:PORT/PROJECT/ibm/pc/ibm_sys_suggest/save.do </a>
					</p>
				</td>
				<td>
					<p>{token : "用户token",data:{SUGGEST_CONTENT_:"反馈内容"}}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>message : "请求结果消息"}</p>
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
