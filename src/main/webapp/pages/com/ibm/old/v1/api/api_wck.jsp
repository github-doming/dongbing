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
					<td>
						<p>1.查看消息</p>
					</td>
					<td>
						<p>
							<a href="javascript:void(0);"
								onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/cms_topic/w/list.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'pageIndex\':\'1\',\'pageSize\':\'10\'}}');">
								http://IP:PORT/PROJECT/ibm/cloud/cms_topic/w/list.do </a>
						</p>
					</td>
					<td>
						<p>{token : "用户token",data:{pageIndex:"当前页",pageSize:"每页显示记录数"}}</p>
					</td>
					<td>
						<p>{code : "请求结果代码",</p>
						<p>data: {
						hasNextPage: "是否有下一页",
						hasPreviousPage:"是否有上一页",
						list: [
							{
							TITLE_: "消息标题",
							TOPIC_ID_: "消息ID",
							READ_STATE_: 阅读状态（1未读，2已读）
							},
						],</p>
						<p>
							pageIndex: 当前页,
							pageSize: 每页显示记录数,
							totalCount: 总记录数,
							totalPages: 总页数
						}</p>
						<p>message : "请求结果消息"}</p>
					</td>
				</tr>

				<tr>
					<td>
						<p>2.查看消息内容</p>
					</td>
					<td>
						<p>
							<a href="javascript:void(0);"
								onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/cms_topic/w/find_topic.do?json={\'token\':\'49b8e0763c94484da64a40329d2f6e3a\',data:{\'TOPIC_ID_\':\'8c1a864f9ef14ec0b6d280903ff818f6\'}}');">
								http://IP:PORT/PROJECT/ibm/cloud/cms_topic/w/find_topic.do </a>
						</p>
					</td>
					<td>
						<p>{token : "用户token",data {TOPIC_ID_:"消息ID"}}</p>
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
						<p>3.删除消息</p>
					</td>
					<td>
						<p>
							<a href="javascript:void(0);"
								onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/cms_topic/w/del.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'IBM_CMS_TOPIC_ID_\':\'38edc5b655344f9c98ad5c8404db7307\'}}');">
								http://IP:PORT/PROJECT/ibm/cloud/cms_topic/w/del.do </a>
						</p>
					</td>
					<td>
						<p>{token : "用户token",data {IBM_CMS_TOPIC_ID_:"用户消息ID"}}</p>
					</td>
					<td>
						<p>{code : "请求结果代码",</p>
						<p>message : "请求结果消息"}</p>
					</td>
				</tr>

				<tr>
					<td>
						<p>4.清空消息</p>
					</td>
					<td>
						<p>
							<a href="javascript:void(0);"
								onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/cms_topic/w/del_all.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\'}');">
								http://IP:PORT/PROJECT/ibm/cloud/cms_topic/w/del_all.do </a>
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
						<p>5.一键已阅</p>
					</td>
					<td>
						<p>
							<a href="javascript:void(0);"
								onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/cms_topic/w/read_all.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\'}');">
								http://IP:PORT/PROJECT/ibm/cloud/cms_topic/w/read_all.do </a>
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
						<p>6.修改密码</p>
					</td>
					<td>
						<p>
							<a href="javascript:void(0);"
								onClick="findUrl('${pageContext.request.contextPath}/ibm/all/ibm/app_user/update_password.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'oldPassword\':\'cjx12345678\',\'newPassword\':\'12345678cjx\'}}');">
								http://IP:PORT/PROJECT/ibm/all/ibm/app_user/update_password.do </a>
						</p>
					</td>
					<td>
						<p>{token : "用户token",data {oldPassword:"旧密码",newPassword:"新密码"}}</p>
					</td>
					<td>
						<p>{code : "请求结果代码",</p>
						<p>message : "请求结果消息"}</p>
					</td>
				</tr>

				<tr>
					<td>
						<p>7.用户反馈</p>
					</td>
					<td>
						<p>
							<a href="javascript:void(0);"
								onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/ibm_sys_suggest/w/save.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'SUGGEST_CONTENT_\':\'test\'}}');">
								http://IP:PORT/PROJECT/ibm/cloud/ibm_sys_suggest/w/save.do </a>
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

				<tr>
					<td>
						<p>8.重置方案</p>
					</td>
					<td>
						<p>
							<a href="javascript:void(0);"
								onClick="findUrl('${pageContext.request.contextPath}/ibm/ibm_hm_plan_exec_item/w/replay.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'GAME_CODE_\':\'PK10\',\'PLAN_CODE_\':\'LOCATION_BET_NUMBER,FOLLOW_TWO_SIDE\'}}');">
								http://IP:PORT/PROJECT/ibm/ibm_hm_plan_exec_item/w/replay.do </a>
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
						<p>9.重置所有方案</p>
					</td>
					<td>
						<p>
							<a href="javascript:void(0);"
								onClick="findUrl('${pageContext.request.contextPath}/ibm/ibm_hm_plan_exec_item/w/replay_all.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'PLAN_ID_\':\'abe15fe213444d14a6318ee8e720fddf,b066108af2cd47bd81fb13077484cb26\'}}');">
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
					<td>
						<p>10.手动投注</p>
					</td>
					<td>
						<p>
							<a href="javascript:void(0);"
								onClick="findUrl('${pageContext.request.contextPath}/ibm/ibm_hm_plan_group_bet_item/t/handleBet.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'betItem\':[{\'betPosition\':\'2\',\'betOption\':\'1,2,3,4,5,6,7,8,9,10,大,小,单,双,龙,虎\'},{\'betPosition\':\'7\',\'betOption\':\'1,2,3,4,5,6,7,8,9,10,大,小,单,双\'},{\'betPosition\':\'9\',\'betOption\':\'1,2,3,4,5,6,7,8,9,10,大,小,单,双\'}],\'GAME_CODE_\':\'PK10\',\'PERIOD_\':\'720720\',\'FUND_T_\':\'3\',\'EXEC_STATE_\':\'VR\',\'HANDICAP_MEMBER_ID_\':\'f22a3e4c56cc4612b68d991ab2391a73\'}}');">
								http://IP:PORT/PROJECT/ibm/ibm_hm_plan_group_bet_item/t/handleBet.do </a>
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
					<td>
						<p>11.修改选中方案详情状态</p>
					</td>
					<td>
						<p>
							<a href="javascript:void(0);"
								onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/ibm_plan_user/t/editAllState.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'GAME_CODE_\':\'PK10\',\'PLAN_CODE_\':\'PLAN_LOCATION_BET_NUMBER,FOLLOW_TWO_SIDE\',\'STATE_\':\'STOP\'}}');">
								http://IP:PORT/PROJECT/ibm/cloud/ibm_plan_user/t/editAllState.do </a>
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
						<p>12.打开盘口</p>
					</td>
					<td>
						<p>
							<a href="javascript:void(0);"
								onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/handicap_user/t/openHandicap.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'HANDICAP_MEMBER_ID_\':\'108a8cb156db4d898ce97d7d79501dd3\',\'HANDICAP_CODE_\':\'WS2\'}}');">
								http://IP:PORT/PROJECT/ibm/cloud/handicap_user/t/openHandicap.do </a>
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
				        <p>"handicapMemberInfo":[                                      </p>
				        <p>    {handicapMemberId:"盘口会员ID",								</p>
				        <p>		"set":盘口设置信息:{                                         </p>
				        <p>            "PROFIT_LIMIT_MAX_T_":"止盈上限",                   </p>
				        <p>            "RESET_TYPE_":"每天重置：1，自定义重置：2",                 </p>
				        <p>            "RESET_LOSS_MIN_T_":"重置亏损下限",                   </p>
				        <p>            "BET_RATE_T_":投注比例,                             </p>
				        <p>            "LANDED_TIME_":定时登陆,                            </p>
				        <p>            "BLAST_STOP_":"炸停止",                            </p>
				        <p>            "LOSS_LIMIT_MIN_T_":止损下限,                       </p>
				        <p>            "MEMBER_PASSWORD_":"密码",                        </p>
				        <p>            "BET_RECORD_ROWS_":投注记录保存行数,                    </p>
				        <p>            "MEMBER_ACCOUNT_":"账号",                         </p>
				        <p>            "PROFIT_LIMIT_MIN_T_":止盈下限,                     </p>
				        <p>            "handicapMemberId":"盘口会员ID",                    </p>
				        <p>            "RESET_PROFIT_MAX_T_":重置盈利上限,                   </p>
				        <p>            "BET_MERGER_":"投注合并"                            </p>
				        <p>            "isStopProfit":"是否显示盈利下限"（TRUE/FALSE）                            </p>
				        <p>            "SAVE_ACCOUNT_":"是否保存账户信息"（TRUE/FALSE）                            </p>
				        <p>            "NICKNAME_":"账户"                            </p>
				        <p>            "BALANCE_":"余额"                            </p>
				        <p>       },                                                  </p>
				        <p>      "handicapInfo":{                                      </p>
						<p>        		"vrBet":模拟投注信息,							</p>
		                <p>    			"realBet":真实投注信息，                        </p>
		                <p>    			"NICKNAME_":"账户",                             </p>
		                <p>    			"BALANCE_":"余额"                              </p>
						<p>       },                                                    </p>
				        <p>    }],                                                       </p>
				        <p>	"allAccount":[                                                 </p>
					    <p>        {                                                      </p>
					    <p>            "MEMBER_ACCOUNT_":"会员账号",                       </p>
					    <p>            "IBM_HANDICAP_MEMBER_ID_":"盘口会员id"             </p>
					    <p>        }                                                     </p>
					    <p>    ],                                                         </p>
					    <p>    "handicapItemId":"盘口详情id"
				        <p>},</p>
		                <p>message : "请求结果消息"}</p>
		            </td>
				</tr>
				
				<tr>
					<td>
						<p>13.退出登录</p>
					</td>
					<td>
						<p>
							<a href="javascript:void(0);"
								onClick="findUrl('${pageContext.request.contextPath}/ibm/ibm_handicap_member/t/IbmHandicapMemberLogout.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'HANDICAP_MEMBER_ID_\':\'f22a3e4c56cc4612b68d991ab2391a71\'}}');">
								http://IP:PORT/PROJECT/ibm/ibm_handicap_member/t/IbmHandicapMemberLogout.do</a>
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
					<td>14.会员盘口定时登录</td>
					<td>
						<p>
							<a href="javascript:void(0);"
							   onClick="findUrl('http://192.168.2.140:8080/a/ibm/ibm_handicap_member/t/IbmHandicapMemberLogin.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'HANDICAP_MEMBER_ID_\':\'f22a3e4c56cc4612b68d991ab2391a71\',\'HANDICAP_CODE_\':\'WS2\',\'SAVE_ACCOUNT_\':\'TRUE\',\'LANDED_TIME_\':\'10:10:10\',\'HANDICAP_ITEM_ID_\':\'acc72a41b8634d778b73678b822be142\'}}');"
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
					<td>15.会员盘口登录——账号密码登录</td>
					<td>
						<p>
							<a href="javascript:void(0);"
							   onClick="findUrl('http://192.168.2.140:8080/a/ibm/ibm_handicap_member/t/IbmHandicapMemberLogin.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'MEMBER_ACCOUNT_\':\'cs7654322\',\'MEMBER_PASSWORD_\':\'Cs987we12..\',\'HANDICAP_CODE_\':\'WS2\',\'SAVE_ACCOUNT_\':\'FALSE\',\'HANDICAP_ITEM_ID_\':\'acc72a41b8634d778b73678b822be142\'}}');"
							> http://IP:PORT/PROJECT/ibm/ibm_handicap_member/t/IbmHandicapMemberLogin.do</a>
						</p>
					</td>
					<td>
						<p>{"token": "用户token"}</p>
						<p>MEMBER_ACCOUNT_:"会员账号"</p>
						<p>MEMBER_PASSWORD_:"会员密码"</p>
						<p>HANDICAP_CODE_:"盘口编码"}</p>
						<p>HANDICAP_ITEM_ID_:"盘口详情id"}</p>
						<p>SAVE_ACCOUNT_:"是否保存账户信息"}</p>
					</td>
					<td>
						<p>{sysEnum : "状态码",</p>
						<p>success : "请求状态",</p>
						<p>enum : "请求结果消息"}</p>

					</td>
				</tr>
				
				<tr>
					<td>16.会员盘口登录——盘口会员ID登录</td>
					<td>
						<p>
							<a href="javascript:void(0);"
							   onClick="findUrl('http://192.168.2.140:8080/a/ibm/ibm_handicap_member/t/IbmHandicapMemberLogin.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'HANDICAP_MEMBER_ID_\':\'f22a3e4c56cc4612b68d991ab2391a71\',\'HANDICAP_CODE_\':\'WS2\',\'SAVE_ACCOUNT_\':\'TRUE\',\'HANDICAP_ITEM_ID_\':\'acc72a41b8634d778b73678b822be142\'}}');"
							> http://IP:PORT/PROJECT/ibm/ibm_handicap_member/t/IbmHandicapMemberLogin.do</a>
						</p>
					</td>
					<td>
						<p>{"token": "用户token"}</p>
						<p>data{HANDICAP_MEMBER_ID_ : 盘口会员id</p>
						<p>HANDICAP_CODE_:"盘口编码"}</p>
						<p>HANDICAP_ITEM_ID_:"盘口详情id"}</p>
						<p>SAVE_ACCOUNT_:"是否保存账户信息"}</p>
					</td>
					<td>
						<p>{sysEnum : "状态码",</p>
						<p>success : "请求状态",</p>
						<p>enum : "请求结果消息"}</p>

					</td>
				</tr>
				
				<tr>
					<td>17.注销登录</td>
					<td>
						<p>
							<a href="javascript:void(0);"
							   onClick="findUrl('${pageContext.request.contextPath}/ibm/app_user/logout.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\'}}');"
							> http://IP:PORT/PROJECT/ibm/app_user/logout.do</a>
						</p>
					</td>
					<td>
						<p>{"token": "用户token"}</p>
					</td>
					<td>
						<p>{sysEnum : "状态码",</p>
						<p>success : "请求状态",</p>
						<p>enum : "请求结果消息"}</p>

					</td>
				</tr>
				
								
				<tr>
					<td>18.获取盘口所有方案</td>
					<td>
						<p>
							<a href="javascript:void(0);"
							   onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/ibm_plan_user/t/listByHandicap.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',\'data\':{\'HANDICAP_CODE_\':\'WS2\'}}}');"
							> http://IP:PORT/PROJECT/ibm/cloud/ibm_plan_user/t/listByHandicap.do</a>
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
					<td>19.盘口游戏设置-投注状态</td>
					<td>
						<p>
							<a href="javascript:void(0);"
							   onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/ibm_hm_game_set/t/betStateSet.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'HANDICAP_MEMBER_ID_\':\'f22a3e4c56cc4612b68d991ab2391a71\',\'GAME_CODE_\':\'PK10\',\'BET_STATE_\':\'FALSE\'}}');"
							> http://IP:PORT/PROJECT/ibm/cloud/ibm_hm_game_set/t/betStateSet.do</a>
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
					<td>20.盘口游戏设置-自动停止投注</td>
					<td>
						<p>
							<a href="javascript:void(0);"
							   onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/ibm_hm_game_set/t/autoStopSet.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'HANDICAP_MEMBER_ID_\':\'f22a3e4c56cc4612b68d991ab2391a71\',\'GAME_CODE_\':\'PK10\',\'BET_AUTO_STOP_\':\'TRUE\',\'BET_AUTO_STOP_TIME_\':\'12:30:00\'}}');"
							> http://IP:PORT/PROJECT/ibm/cloud/ibm_hm_game_set/t/autoStopSet.do</a>
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
					<td>21.盘口游戏设置-自动开始投注</td>
					<td>
						<p>
							<a href="javascript:void(0);"
							   onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/ibm_hm_game_set/t/autoStartSet.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'HANDICAP_MEMBER_ID_\':\'f22a3e4c56cc4612b68d991ab2391a71\',\'GAME_CODE_\':\'PK10\',\'BET_AUTO_START_\':\'TRUE\',\'BET_AUTO_START_TIME_\':\'12:30:00\'}}');"
							> http://IP:PORT/PROJECT/ibm/cloud/ibm_hm_game_set/t/autoStartSet.do</a>
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
					<td>22.盘口游戏设置</td>
					<td>
						<p>
							<a href="javascript:void(0);"
							   onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/ibm_hm_game_set/t/handicapGameSet.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'HANDICAP_MEMBER_ID_\':\'f22a3e4c56cc4612b68d991ab2391a71\',\'GAME_CODE_\':\'PK10\',\'BET_MODE_\':\'REAL\',\'INCREASE_STATE_\':\'AUTO\',\'INCREASE_STOP_TIME_\':\'20:30:00\',\'BET_SECOND_\':\'120\',\'SPLIT_TWO_SIDE_AMOUNT_\':\'0\',\'SPLIT_NUMBER_AMOUNT_\':\'0\'}}');"
							> http://IP:PORT/PROJECT/ibm/cloud/ibm_hm_game_set/t/handicapGameSet.do</a>
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
		            <td>
		                <p>23.识别验证码</p>
		            </td>
		            <td>
		                <p>
		                    <a href="javascript:void(0);"
		                       onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/handicap_user/t/discernHandicap.do?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'HANDICAP_URL\':\'http://sf33.qr68.us/\',\'HANDICAP_CAPTCHA\':\'az311\'}}');"
		                    > http://IP:PORT/PROJECT/ibm/cloud/handicap_user/t/discernHandicap.do</a>
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
		                <p>25.识别验证码——盘口会员登录后</p>
		            </td>
		            <td>
		                <p>
		                    <a href="javascript:void(0);"
		                       onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/handicap_user/t/	?json={\'token\':\'ae847f82533549a4880b89ee3584a3bb\',data:{\'HANDICAP_MEMBER_ID_\':\'f22a3e4c56cc4612b68d991ab2391a71\',\'HANDICAP_CODE_\':\'IDC\',\'HANDICAP_URL\':\'http://sf33.qr68.us/\',\'HANDICAP_CAPTCHA\':\'az311\'}}');"
		                    > http://IP:PORT/PROJECT/ibm/cloud/handicap_user/t/checkHandicap.do</a>
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
		                <p>26.盘口会员登录消息回执</p>
		            </td>
		            <td>
		                <p>
		                    <a href="javascript:void(0);"
		                       onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/ibm_cloud_receipt/t/hmLoginReceipt.do?json={\'token\':\'efa3537367344174b7f14597091fc770\',data:{\'HANDICAP_MEMBER_ID_\':\'8610fd1a21ef4909b053eb81b5cf1556\',\'RECEIPT_ID_\':\'fd62e17c684542b1803e572936634110\',\'MESSAGE_METHOD_\':\'LOGIN\'}}');"
		                    > http://IP:PORT/PROJECT/ibm/cloud/ibm_cloud_receipt/t/hmLoginReceipt.do</a>
		                </p>
		            </td>
		            <td>
		                <p>{token : "用户token",data:</p>
		                <p>{HANDICAP_MEMBER_ID_:"盘口会员ID",</p>
		                <p>RECEIPT_ID_:"消息回执id",</p>
		                <p>MESSAGE_METHOD_ : 消息回执方式}}</p>
		            </td>
		            <td>
		                <p>{code : "请求结果代码",</p>
		                <p>"data":{
						        "RECEIPT_STATE_":"消息回执状态",
						        "PROCESS_RESULT_":消息回执结果（json）,
						    },</p>
		                <p>message : "请求结果消息"}</p>
		            </td>
		        </tr>
		        <tr>
					<td>27.刷新投注信息</td>
					<td>
						<p>
							<a href="javascript:void(0);"
							   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_handicap_game/refreshBetInfo.do?json={\'token\':\'b86b18c2fbe64215b337a2deafe8ef06\',data:{\'HANDICAP_MEMBER_ID_\':\'b521adb9a85f46669e7d2e79dacb56a8\',\'GAME_CODE_\':\'PK10\',\'checkTime\':\'1554692720482\'}}');"
							> http://IP:PORT/PROJECT/ibm/pc/ibm_handicap_game/refreshBetInfo.do</a>
						</p>
					</td>									
					<td>
						<p>{"token": "用户token"}</p>
						<p>data{HANDICAP_MEMBER_ID_:"盘口会员id"</p>
						<p>GAME_CODE_:"游戏id"}</p>
						<p>checkTime:"请求时间"}</p>
					</td>
					<td>
						<p>{sysEnum : "状态码",</p>
						<p>success : "请求状态",</p>
						<p>{"code":"200","codeSys":"200","command":"","commandCn":"","data":{"drawInfoList":[{"IBM_EXEC_BET_ITEM_ID_":"872D4977DE514D8398874FD7D870B332","info":{"DRAW_NUMBER_":"6,7,10,3,5,4,8,9,1,2","EXEC_STATE_":"READY","PROFIT_T_":20000,"ODDS_":"9.910","IS_MERGER_":"TRUE","DESC_":"MANUAL","BET_MERGER_":"OPEN"}},{"IBM_EXEC_BET_ITEM_ID_":"D6EA7F808A2C407E837C8AA2180079ED","info":{"DRAW_NUMBER_":"6,7,10,3,5,4,8,9,1,2","EXEC_STATE_":"READY","PROFIT_T_":20000,"ODDS_":"9.910","IS_MERGER_":"TRUE","DESC_":"MANUAL","BET_MERGER_":"OPEN"}}],"memberInfo":{"NICKNAME":"wck123","BALANCE":"9998"},"currentBetInfoList":[{"IBM_EXEC_BET_ITEM_ID_":"AAE0244D8C784E6CA0CBE7283BFC98EF","info":{"DRAW_NUMBER_":"6,7,10,3,5,4,8,9,1,2","EXEC_STATE_":"SUCCESS","PROFIT_T_":20000,"ODDS_":"9.910","IS_MERGER_":"TRUE","DESC_":"MANUAL","BET_MERGER_":"OPEN"}},{"IBM_EXEC_BET_ITEM_ID_":"D21A9E5D3EDB404BB6ED447FA402D0F4","info":{"DRAW_NUMBER_":"6,7,10,3,5,4,8,9,1,2","EXEC_STATE_":"SUCCESS","PROFIT_T_":20000,"ODDS_":"9.910","IS_MERGER_":"TRUE","DESC_":"MANUAL","BET_MERGER_":"OPEN"}},{"IBM_EXEC_BET_ITEM_ID_":"F2F64E981C5B4467BD760603877185D6","info":{"DRAW_NUMBER_":"6,7,10,3,5,4,8,9,1,2","EXEC_STATE_":"SUCCESS","PROFIT_T_":20000,"ODDS_":"9.910","IS_MERGER_":"TRUE","DESC_":"MANUAL","BET_MERGER_":"OPEN"}},{"IBM_EXEC_BET_ITEM_ID_":"08318E24F3974242818A2FDFC48BA022","info":{"EXEC_STATE_":"READY","IS_MERGER_":"TRUE","DESC_":"MANUAL","BET_MERGER_":"OPEN"}},{"IBM_EXEC_BET_ITEM_ID_":"3951F43317044C1FA01E3B4DF37CAAE6","info":{"EXEC_STATE_":"READY","IS_MERGER_":"TRUE","DESC_":"MANUAL","BET_MERGER_":"OPEN"}}],"newTime":1554781489274,"betInfoList":[{"FUNDS_INDEX_":1,"ODDS_":null,"IBM_EXEC_BET_ITEM_ID_":"08318E24F3974242818A2FDFC48BA022","ROW_NUM":"1","EXEC_PLAN_GROUP_ID_":null,"PERIOD_":"731907","EXEC_STATE_":"READY","PROFIT_T_":0,"DRAW_NUMBER_":null,"FUND_T_":20,"BET_CONTENT_":"第三名-4,8","DESC_":"MANUAL","BET_MODE_":"REAL","PLAN_GROUP_DESC_":"手动投注","BET_MERGER_":"OPEN"},{"FUNDS_INDEX_":1,"ODDS_":null,"IBM_EXEC_BET_ITEM_ID_":"3951F43317044C1FA01E3B4DF37CAAE6","ROW_NUM":"2","EXEC_PLAN_GROUP_ID_":null,"PERIOD_":"731907","EXEC_STATE_":"READY","PROFIT_T_":0,"DRAW_NUMBER_":null,"FUND_T_":20,"BET_CONTENT_":"第八名-4,8","DESC_":"MANUAL","BET_MODE_":"REAL","PLAN_GROUP_DESC_":"手动投注","BET_MERGER_":"OPEN"}]},"desc":"","id":0,"message":"请求成功","messageSys":"请求成功","msg":"请求成功","msgEn":"Request success","state":"","success":true,"total":0}</p>
						<p>enum : "请求结果消息"}</p>

					</td>
				</tr>
				<tr>
					<td>28.刷新盈亏信息</td>
					<td>
						<p>
							<a href="javascript:void(0);"
							   onClick="findUrl('${pageContext.request.contextPath}/ibm/cloud/ibm_hm_profit/t/refreshProfit.do?json={\'token\':\'bfcae07895924b08ab96e060e9d5b3b6\',data:{\'HANDICAP_MEMBER_ID_\':\'df357da1edc447d3bf12969f72282eeb\',\'GAME_CODE_\':\'XYFT\'\'checkTime\':\'1558164010062\'}}');"
							> http://IP:PORT/PROJECT/ibm/cloud/ibm_hm_profit/t/refreshProfit.do</a>
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
					<td>29.刷新盘口用户信息</td>
					<td>
						<p>
							<a href="javascript:void(0);"
							   onClick="findUrl('${pageContext.request.contextPath}/ibm/pc/ibm_handicap_member/refreshMemberInfo.do?json={\'token\':\'21eaecaaab424fe283563a5a06a464aa\',data:{\'HANDICAP_MEMBER_ID_\':\'508ecc2a537944608b6a904bc22bb985\'}}');"
							> http://IP:PORT/PROJECT/ibm/pc/ibm_handicap_member/refreshMemberInfo.do</a>
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
					<td>30.测试</td>
					<td>
						<p>
							<a href="javascript:void(0);"
							   onClick="findUrl('${pageContext.request.contextPath}/com/cms/pc/cms_topic_board/wck/list.do?json={\'token\':\'21eaecaaab424fe283563a5a06a464aa\',data:{\'cms_board_cmsBoardId\':\'237f9888f54b45e288ddc3738f586195\'}}');"
							> http://IP:PORT/PROJECT/ibm/pc/ibm_handicap_member/refreshMemberInfo.do</a>
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
			</tbody>
		</table>
	</div>
</body>
<script type="text/javascript"> 
    function findUrl(url) {
        url = encodeURI(url);
        console.log(url);
        window.open(url,"_blank");
        return url;
    }
    	
</script>
</html>
