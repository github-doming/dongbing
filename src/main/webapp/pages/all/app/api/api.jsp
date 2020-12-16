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
		<h1>api接口说明(APP端,陈俊先)</h1>
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th>接口名称</th>
					<th>进度</th>
					<th>接口定义</th>
					<th>接口调用示例</th>
					<th>参数说明</th>
					<th>返回说明</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>房主查询玩家战绩</td>
					<td>安妮</td>
					<td>{token:'e62e45297c984e7cb522882919cfbbe7',data:{startDay:'',endDay:'',playerId:'0a96e4b28afa447fb7be0d8e4216fe82,8',pageIndex:'1',pageSize:'20'}}</td>
					<td><a
						href="${pageContext.request.contextPath}/game/sys/ga_bet_result/list.do?json={token:'e62e45297c984e7cb522882919cfbbe7',data:{startDay:'2010-01-01',endDay:'2019-01-01',playerId:'0a96e4b28afa447fb7be0d8e4216fe82,8',pageIndex:'1',pageSize:'20'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/game/sys/ga_bet_result/list.do</a>
					</td>
					<td>
						<p>token：令牌</p>
						<p>gameId：游戏id,为空则查询全部,也可以逗号分割查询某几个</p>
						<p>roomAgentId：代理id,为空则查询全部,也可以逗号分割查询某几个</p>
						<p>roomAgentId：代理id,为"no"表示没有代理,比如没有代理的玩家</p>
						<p>playerId：玩家id,为空则查询全部,也可以逗号分割查询某几个</p>
						<p>startDay：开始日期,格式如"2010-01-01"</p>
						<p>endDay：结束日期,格式如"2010-01-01"</p>
						<p>pageIndex：第几页，可为空</p>
						<p>pageSize：页大小，可为空</p>
					</td>
					<td>
						<p>command 命令说明</p>
						<p>command 命令中文说明</p>
						<p>page 分页信息</p>
						<p>list (列表信息，没有加工数据)</p>
						<p>array (数组信息,有加工数据)</p>
						<p>gameList 游戏积分合计列表</p>
						<p>playerList 玩家积分列表</p>
						<p>BET_POINT_：投注额</p>
						<p>PlayerResultPoint:投注结果得到的积分</p>
						<p>PLAYER_BONUS_POINT_：退水</p>
						<p>PLAYER_RESULT_GAIN_：最终战绩(得到积分+退水)</p>
						<p>GAMEPLAY_NAME_ 投注项</p>
					</td>
				</tr>
				<tr>
					<td>私聊消息总条数(新的未读消息)</td>
					<td></td>
					<td>{"token":"","data":{}}</td>
					<td><a
						href="${pageContext.request.contextPath}/all/cms/cms_msg/state_new/cnt_total.do?json={token:'adee0b83400546a5afa6e257b054c02f',data:{}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/cms/cms_msg/state_new/cnt_total.do</a>
					</td>
					<td>
						<p>token：令牌</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>websocket发送消息(在会话下发送)</td>
					<td></td>
					<td>{ "token": "", "data": { "content": "", "receiveUser": "",
						"pageIndex": "1", "pageSize": "10", "topicId": "" } }</td>
					<td><a href="#" target="_blank">ws://IP:PORT${pageContext.request.contextPath}/websocket/cms/msg/post/{topicId}</a>
					</td>
					<td>
						<p>token：令牌</p>
						<p>content：内容</p>
						<p>receiveUser ：接收人</p>
						<p>topicId：消息主题id</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>websocket发送主题</td>
					<td></td>
					<td>{ "token": "", "data": { "content": "", "receiveUser": "",
						"pageIndex": "1", "pageSize": "10" } }</td>
					<td><a href="#" target="_blank">ws://IP:PORT${pageContext.request.contextPath}/websocket/cms/msg/topic/{appUserId}</a>
					</td>
					<td>
						<p>token：令牌</p>
						<p>content：内容</p>
						<p>receiveUser ：接收人列表(用逗号来分割)</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>私聊消息主题删除(会话删除))</td>
					<td></td>
					<td>{"token":"","data":{"topicId":""}}</td>
					<td><a
						href="${pageContext.request.contextPath}/all/cms/cms_msg_topic/del.do?json={token:'adee0b83400546a5afa6e257b054c02f',data:{topicId:'02836953817f4f428fe083e7a6463581'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/cms/cms_msg_topic/del.do</a>
					</td>
					<td>
						<p>token：令牌</p>
						<p>topicId：消息主题id,逗号分割删除多条</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>私聊消息删除</td>
					<td>安妮</td>
					<td>{"token":"","data":{"msgId":""}}</td>
					<td><a
						href="${pageContext.request.contextPath}/all/cms/cms_msg/del.do?json={token:'adee0b83400546a5afa6e257b054c02f',data:{msgId:'8729b782e69348a5b0a0d83fae3d9f05'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/cms/cms_msg/del.do</a>
					</td>
					<td>
						<p>token：令牌</p>
						<p>msgId：消息id,逗号分割删除多条</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>私聊消息列表</td>
					<td>安妮</td>
					<td>{token:'t20180614111420953095',data:{topicId:'4c91e26975324ea99addd205d2c77d80',pageIndex:'2',pageSize:'3'}}</td>
					<td><a
						href="${pageContext.request.contextPath}/all/cms/cms_msg/list.do?json={token:'adee0b83400546a5afa6e257b054c02f',data:{topicId:'b9ef84633c874400a6ad305707fbb529',pageIndex:'1',pageSize:'10'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/cms/cms_msg/list.do</a>
					</td>
					<td>
						<p>token：令牌</p>
						<p>topicId：消息主题id</p>
						<p>pageIndex：第几页，可为空</p>
						<p>pageSize：页大小，可为空</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>私聊消息列表(弃用)</td>
					<td></td>
					<td>{"token":"","data":{"topicId":"","createTimeLong":"","pageType":"first"}}</td>
					<td><a
						href="${pageContext.request.contextPath}/all/cms/cms_msg/list2.do?json={token:'t20180614111420953095',data:{topicId:'02836953817f4f428fe083e7a6463581',createTimeLong:'',pageType:'first'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/cms/cms_msg/list2.do</a>
					</td>
					<td>
						<p>token：令牌</p>
						<p>topicId：消息主题id</p>
						<p>createTimeLong：时间</p>
						<p>pageIndex：第几页，可为空</p>
						<p>pageSize：页大小，可为空</p>
						<p>pageType：页类型,first首页,up,上一页，down下一页</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>私聊消息主题(返回主题id)</td>
					<td>安妮</td>
					<td>{"token":"","data":{"receiveUser":""}}</td>
					<td><a
						href="${pageContext.request.contextPath}/all/cms/cms_msg_topic/form.do?json={token:'adee0b83400546a5afa6e257b054c02f',data:{receiveUser:'1'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/cms/cms_msg_topic/form.do</a>
					</td>
					<td>
						<p>token：令牌</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>私聊消息主题列表</td>
					<td>安妮</td>
					<td>{"token":"","data":{pageIndex:'1',pageSize:'3'}}</td>
					<td><a
					href="javascript:void(0);" onClick="findUrl('${pageContext.request.contextPath}/all/cms/cms_msg_topic/list.do?json={token:\'adee0b83400546a5afa6e257b054c02f\',data:{pageIndex:\'1\',pageSize:\'100\'}}');" 
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/cms/cms_msg_topic/list.do</a>
					</td>
					<td>
						<p>token：令牌</p>
						<p>pageIndex：第几页，可为空</p>
						<p>pageSize：页大小，可为空</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>私聊消息主题列表(弃用)</td>
					<td></td>
					<td>{"token":"","data":{"createTimeLong":"","pageType":"first"}}</td>
					<td><a
						href="${pageContext.request.contextPath}/all/cms/cms_msg_topic/list2.do?json={token:'adee0b83400546a5afa6e257b054c02f',data:{createTimeLong:'',pageType:'first'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/cms/cms_msg_topic/list2.do</a>
					</td>
					<td>
						<p>token：令牌</p>
						<p>createTimeLong：时间</p>
						<p>pageIndex：第几页，可为空</p>
						<p>pageSize：页大小，可为空</p>
						<p>pageType：页类型,first首页,up,上一页，down下一页</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>私聊群发消息(主题)(弃用)</td>
					<td>安妮</td>
					<td>{"token":"","data":{"content":"","receiveUser":"1"}}</td>
					<td><a
						href="${pageContext.request.contextPath}/all/cms/cms_msg_topic/saveBatch.do?json={token:'adee0b83400546a5afa6e257b054c02f',data:{cmsMsgTopicId:'58f54282-3dd2-4db7-b788-8f156f8e2182',content:'我2',receiveUser:'1'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/cms/cms_msg_topic/saveBatch.do</a>
					</td>
					<td>
						<p>token：令牌(发送人)</p>
						<p>content：内容</p>
						<p>receiveUser ：接收人列表(用逗号来分割)</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>私聊发消息(在会话下发送)(弃用)</td>
					<td>安妮</td>
					<td>{"token":"","data":{"cmsMsgTopicId":"","content":"","receiveUser":""}}</td>
					<td><a
						href="${pageContext.request.contextPath}/all/cms/cms_msg/save.do?json={token:'adee0b83400546a5afa6e257b054c02f',data:{cmsMsgTopicId:'58f54282-3dd2-4db7-b788-8f156f8e2182',content:'我a1',receiveUser:'2'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/cms/cms_msg/save.do</a>
					</td>
					<td>
						<p>token：令牌(发送人)</p>
						<p>content：内容</p>
						<p>receiveUser ：接收人</p>
						<p>topicId：消息主题id</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>发反馈</td>
					<td>敏婷</td>
					<td>{"token":"","data":{"content":""}}</td>
					<td><a
						href="${pageContext.request.contextPath}/all/cms/cms_topic_feeback/save.do?json={token:'32cdccaa152a4643a43cda86d0b90259',data:{content:'我1'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/cms/cms_topic_feeback/save.do</a>
					</td>
					<td>
						<p>token：令牌(发送人)</p>
						<p>content：内容</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>(代理消息列表)房间联系人(搜索)</td>
					<td>安妮</td>
					<td>{"token":"", "cmd":
						"queryName","data":{"roomId":"","userName":""}}</td>
					<td><a
						href="${pageContext.request.contextPath}/game/sys/ga_user_room/contact/roomagent/list.do?json={token:'d23dc6a8de77439fb29fac29c55c6a1e',cmd:'queryName',data:{userName:'代',roomId:'d592671ab38a4fa6b6c430c985307504'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/game/sys/ga_user_room/contact/roomagent/list.do</a>
					</td>
					<td>
						<p>token：令牌</p>
						<p>roomId：房间id</p>
						<p>cmd：queryName</p>
						<p>userName：用户账户名称或昵称</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>(代理消息列表)房间联系人(所有)</td>
					<td>安妮</td>
					<td>{"token":"", "cmd": "all","data":{"roomId":""}}</td>
					<td><a
						href="${pageContext.request.contextPath}/game/sys/ga_user_room/contact/roomagent/list.do?json={token:'d23dc6a8de77439fb29fac29c55c6a1e',cmd:'all',data:{roomId:'d592671ab38a4fa6b6c430c985307504'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/game/sys/ga_user_room/contact/roomagent/list.do</a>
					</td>
					<td>
						<p>token：令牌</p>
						<p>roomId：房间id</p>
						<p>cmd：all</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>(玩家消息列表)房间联系人(房主与代理)</td>
					<td>安妮</td>
					<td>{"token":"", "cmd": "all","data":{"roomId":""}}</td>
					<td><a
						href="${pageContext.request.contextPath}/game/sys/ga_user_room/contact/player/list.do?json={token:'5ea145d32db741d9b60619c76d730a6f',cmd:'all',data:{roomId:'d592671ab38a4fa6b6c430c985307504'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/game/sys/ga_user_room/contact/player/list.do</a>
					</td>
					<td>
						<p>token：令牌</p>
						<p>roomId：房间id</p>
						<p>cmd：all</p>
					</td>
					<td>
						<p>owner：房主信息</p>
						<p>ownerCms：与房主之间的通信消息</p>
						<p>roomagent：房间代理信息</p>
						<p>roomagentCms：与房间代理的通信消息</p>
					</td>
				</tr>
				<tr>
					<td>(房主消息列表)房间联系人(搜索名称加上用户类型)</td>
					<td>安妮</td>
					<td>{"token":"", "cmd":
						"queryNameAndUserType","data":{"roomId":"","userName":"","userType":""}}</td>
					<td><a
						href="${pageContext.request.contextPath}/game/sys/ga_user_room/contact/list.do?json={token:'adee0b83400546a5afa6e257b054c02f',cmd:'queryNameAndUserType',data:{userType:'PLAYER',userName:'38',roomId:'d592671ab38a4fa6b6c430c985307504'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/game/sys/ga_user_room/contact/list.do</a>
					</td>
					<td>
						<p>token：令牌</p>
						<p>roomId：房间id</p>
						<p>cmd：queryName</p>
						<p>userName：用户账户名称或昵称</p>
						<p>userType：用户类型</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>(房主消息列表)房间联系人(搜索)</td>
					<td>安妮</td>
					<td>{"token":"", "cmd":
						"queryName","data":{"roomId":"","userName":""}}</td>
					<td><a
						href="${pageContext.request.contextPath}/game/sys/ga_user_room/contact/list.do?json={token:'adee0b83400546a5afa6e257b054c02f',cmd:'queryName',data:{userName:'38',roomId:'d592671ab38a4fa6b6c430c985307504'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/game/sys/ga_user_room/contact/list.do</a>
					</td>
					<td>
						<p>token：令牌</p>
						<p>roomId：房间id</p>
						<p>cmd：queryName</p>
						<p>userName：用户账户名称或昵称</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>(房主消息列表)房间联系人(用户类型)</td>
					<td>安妮</td>
					<td>{"token":"", "cmd": "userType","data":{"roomId":""}}</td>
					<td><a
						href="${pageContext.request.contextPath}/game/sys/ga_user_room/contact/list.do?json={token:'adee0b83400546a5afa6e257b054c02f',cmd:'userType',data:{userType:'PLAYER',roomId:'d592671ab38a4fa6b6c430c985307504'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/game/sys/ga_user_room/contact/list.do</a>
					</td>
					<td>
						<p>token：令牌</p>
						<p>roomId：房间id</p>
						<p>cmd：userType</p>
						<p>userType：用户类型,玩家PLAYER代理ROOMAGENCY</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>(房主消息列表)房间联系人(所有)</td>
					<td>安妮</td>
					<td>{"token":"", "cmd": "all","data":{"roomId":""}}</td>
					<td><a
						href="${pageContext.request.contextPath}/game/sys/ga_user_room/contact/list.do?json={token:'5f04532eac684a6b9a5ce253234b1f86',cmd:'all',data:{roomId:'d592671ab38a4fa6b6c430c985307504'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/game/sys/ga_user_room/contact/list.do</a>
					</td>
					<td>
						<p>token：令牌</p>
						<p>roomId：房间id</p>
						<p>cmd：all</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>(房主消息列表)房间联系人(弃用)</td>
					<td>安妮</td>
					<td>{"token":"","data":{"roomId":"","createTimeLong":"","pageType":"first"}}</td>
					<td><a
						href="${pageContext.request.contextPath}/game/sys/ga_user_room/contact/list2.do?json={token:'adee0b83400546a5afa6e257b054c02f',data:{roomId:'d592671ab38a4fa6b6c430c985307504',createTimeLong:'',pageType:'first'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/game/sys/ga_user_room/contact/list2.do</a>
					</td>
					<td>
						<p>token：令牌</p>
						<p>roomId：房间id</p>
						<p>createTimeLong：时间</p>
						<p>pageIndex：第几页，可为空</p>
						<p>pageSize：页大小，可为空</p>
						<p>pageType：页类型,first首页,up,上一页，down下一页</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>首页公告发布(启用或停用)</td>
					<td>敏婷</td>
					<td>{"token":"","data":{"state","CLOSE,"roomId":"1"}}</td>
					<td><a
						href="${pageContext.request.contextPath}/game/sys/ga_room_board/index/saveState.do?json={token:'adee0b83400546a5afa6e257b054c02f',data:{state:'CLOSE',roomId:'1'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/game/sys/ga_room_board/index/saveState.do</a>
					</td>
					<td><p>roomId：房间id</p>
						<p>state：状态,OPEN启用,CLOSE停用</p></td>
					<td></td>
				</tr>
				<tr>
					<td>首页公告发布</td>
					<td>敏婷</td>
					<td>{"token":"","data":{"state","OPEN","roomId":"1","title":"标题1","content":"内容1"}}</td>
					<td><a
						href="${pageContext.request.contextPath}/game/sys/ga_room_board/index/save.do?json={token:'adee0b83400546a5afa6e257b054c02f',data:{state:'OPEN',roomId:'1',title:'标题1',content:'内容1'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/game/sys/ga_room_board/index/save.do</a>
					</td>
					<td><p>roomId：房间id</p>
						<p>state：状态,OPEN启用,CLOSE停用</p></td>
					<td></td>
				</tr>
				<tr>
					<td>得到首页公告</td>
					<td>敏婷</td>
					<td>{"token":"","data":{"roomId":"1"}}</td>
					<td><a
						href="${pageContext.request.contextPath}/game/sys/ga_room_board/index/form.do?json={token:'adee0b83400546a5afa6e257b054c02f',data:{roomId:'1'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/game/sys/ga_room_board/index/form.do</a>
					</td>
					<td><p>roomId：房间id</p></td>
					<td></td>
				</tr>
				<tr>
					<td>轮播公告发布(启用或停用)</td>
					<td>敏婷</td>
					<td>{"token":"","data":{"state","CLOSE","roomId":"1"}}</td>
					<td><a
						href="${pageContext.request.contextPath}/game/sys/ga_room_board/roll/saveState.do?json={token:'adee0b83400546a5afa6e257b054c02f',data:{state:'CLOSE',roomId:'1'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/game/sys/ga_room_board/roll/saveState.do</a>
					</td>
					<td><p>roomId：房间id</p>
						<p>state：状态,OPEN启用,CLOSE停用</p></td>
					<td></td>
				</tr>
				<tr>
					<td>轮播公告发布</td>
					<td>敏婷</td>
					<td>{"token":"","data":{"state","OPEN","roomId":"1","title":"标题1","content":"内容1"}}</td>
					<td><a
						href="${pageContext.request.contextPath}/game/sys/ga_room_board/roll/save.do?json={token:'adee0b83400546a5afa6e257b054c02f',data:{state:'OPEN',roomId:'1',title:'标题1',content:'内容1'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/game/sys/ga_room_board/roll/save.do</a>
					</td>
					<td><p>roomId：房间id</p>
						<p>state：状态,OPEN启用,CLOSE停用</p></td>
					<td></td>
				</tr>
				<tr>
					<td>得到轮播公告</td>
					<td>敏婷</td>
					<td>{"token":"","data":{"roomId":"1"}}</td>
					<td><a
						href="${pageContext.request.contextPath}/game/sys/ga_room_board/roll/form.do?json={token:'adee0b83400546a5afa6e257b054c02f',data:{roomId:'1'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/game/sys/ga_room_board/roll/form.do</a>
					</td>
					<td><p>roomId：房间id</p></td>
					<td></td>
				</tr>
				<tr>
					<td>修改头像和昵称</td>
					<td>敏婷</td>
					<td>{
    "token": "cjx",
    "cmd": "update",
    "data": {
        "headPortrait": "陈经理头像",
        "nickName": "陈经理昵称"
    }
}</td>
					<td><a
						href="${pageContext.request.contextPath}/all/app/user/save.do?json={token: 'adee0b83400546a5afa6e257b054c02f',cmd:'updateHeadPortrait',data: {headPortrait: '陈经理'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/app/user/save.do</a>
					</td>
					<td>
						<p>token：app登陆令牌</p>
						<p>cmd：udpate</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>修改头像</td>
					<td>安妮</td>
					<td>{ "token": "cjx", "cmd": "updateHeadPortrait","data":
						{"headPortrait": "陈经理" } }</td>
					<td><a
						href="${pageContext.request.contextPath}/all/app/user/save.do?json={token: 'adee0b83400546a5afa6e257b054c02f',cmd:'updateHeadPortrait',data: {headPortrait: '陈经理'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/app/user/save.do</a>
					</td>
					<td>
						<p>token：app登陆令牌</p>
						<p>cmd：udpateHeadPortrait</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>修改性别</td>
					<td>安妮</td>
					<td>{ "token": "cjx", "cmd": "updateSex","data": {"sex":
						"FEMALE" } }</td>
					<td><a
						href="${pageContext.request.contextPath}/all/app/user/save.do?json={token: 'adee0b83400546a5afa6e257b054c02f',cmd:'updateSex',data: {sex: 'FEMALE'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/app/user/save.do</a>
					</td>
					<td>
						<p>token：app登陆令牌</p>
						<p>cmd：upateSex</p>
						<p>sex：FEMALE女 MALE男</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>修改昵称</td>
					<td>安妮</td>
					<td>{ "token": "cjx", "cmd": "updateNickName","data":
						{"nickName": "陈经理" } }</td>
					<td><a
						href="${pageContext.request.contextPath}/all/app/user/save.do?json={token: 'adee0b83400546a5afa6e257b054c02f',cmd:'updateNickName',data: {nickName: '陈经理'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/app/user/save.do</a>
					</td>
					<td>
						<p>token：app登陆令牌</p>
						<p>cmd：upateNickName</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>修改密码</td>
					<td>安妮</td>
					<td>{ "token": "cjx", "cmd": "updatePassword","data":
						{"passwordOld": "当前密码","passwordNew": "新密码" } }</td>
					<td><a
						href="${pageContext.request.contextPath}/all/app/user/save.do?json={token: 'adee0b83400546a5afa6e257b054c02f',cmd:'updatePassword',data: {passwordOld: '当前密码',passwordNew: '新密码'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/app/user/save.do</a>
					</td>
					<td>
						<p>token：app登陆令牌</p>
						<p>cmd：updatePassword</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>验证密码</td>
					<td>安妮</td>
					<td>{ "token": "cjx","data":
						{"passwordOld": "当前密码" } }</td>
					<td><a
						href="${pageContext.request.contextPath}/all/app/account/password/verify.do?json={token: '9ef7e7b06d6045bca8e09ee38299932a',data: {passwordOld: '1'}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/app/account/password/verify.do</a>
					</td>
					<td>
						<p>token：app登陆令牌</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>通过appUserId查找用户</td>
					<td>敏婷</td>
					<td>{ "token": "cjx", "data": {appUserId:1 } }</td>
					<td><a
						href="javascript:void(0);" onClick="findUrl('${pageContext.request.contextPath}/all/app/user/form.do?json={token: \'adee0b83400546a5afa6e257b054c02f\',data: {appUserId:\'1\'}}');" 
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/app/user/form.do</a>
					</td>
					<td>
						<p>token：app登陆令牌</p>
					</td>
					<td>
						<p>app401：token出错,找不到登录用户</p>
					</td>
				</tr>
				<tr>
					<td>通过token查找用户</td>
					<td>敏婷</td>
					<td>{ "token": "cjx", "data": { } }</td>
					<td><a
						href="javascript:void(0);" onClick="findUrl('${pageContext.request.contextPath}/all/app/token/form.do?json={token: \'b27fe7c21c594c8982e7dd1ff633afd3\',data: {}}');" 
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/app/token/form.do</a>
					</td>
					<td>
						<p>token：app登陆令牌</p>
					</td>
					<td>
						<p>app401：token出错,找不到登录用户</p>
					</td>
				</tr>
				<tr>
					<td>通过token查找用户(tomcat8)</td>
					<td>敏婷</td>
					<td>{ "token": "cjx", "data": { } }</td>
					<td><a
						href="${pageContext.request.contextPath}/all/app/token/form.do?json={traceId:'123',token: 'b27fe7c21c594c8982e7dd1ff633afd3',data: {}}" 
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/app/token/form.do</a>
					</td>
					<td>
						<p>token：app登陆令牌</p>
					</td>
					<td>
						<p>app401：token出错,找不到登录用户</p>
					</td>
				</tr>
				<tr>
					<td>得到session</td>
					<td>敏婷</td>
					<td></td>
					<td><a
					href="javascript:void(0);" onClick="findUrl('${pageContext.request.contextPath}/all/app/token/session.do');" 
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/app/token/session.do</a>
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>刷新验证码(图片)</td>
					<td>敏婷</td>
					<td>{"session":"8947"}</td>
					<td><a
					href="javascript:void(0);" onClick="findUrl('${pageContext.request.contextPath}/all/app/token/verify/img.do?json={session:\'3556A8AA5CE551E986341E1589E5421A\'}');" 
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/app/token/verify/img.do</a>
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>刷新验证码</td>
					<td>不需要做</td>
					<td>{"session":"8947"}</td>
					<td><a
						href="javascript:void(0);" onClick="findUrl('${pageContext.request.contextPath}/all/app/token/verify.do?json={session:\'078C49AE48218D474C1C5E2FCEFC5191\'}');" 
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/app/token/verify.do</a>
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>登录</td>
					<td>敏婷</td>
					<td>{"session":" ","code":"8947","name": "cjx", "password":
						"cjx" }</td>
					<td><a
						href="javascript:void(0);" onClick="findUrl('${pageContext.request.contextPath}/all/app/token/login.do?json={session:\'3556A8AA5CE551E986341E1589E5421A\',code:\'9980\',name: \'o1\',password:\'1\'}');" 
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/app/token/login.do</a>
					</td>
					<td>
						<p>username：登陆用户名</p>
						<p>password:登陆密码</p>
						<p>code:验证码</p>
					</td>
					<td>
						<p>app400：用户名或密码出错</p>
					</td>
				</tr>
				<tr>
					<td>登录(不需要验证码)</td>
					<td>不需要做</td>
					<td>{"name": "cjx", "password": "cjx", }</td>
					<td><a
						href="${pageContext.request.contextPath}/all/app/token/login2.do?json={name: 'cjx',password:'cjx'}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/app/token/login2.do</a>
					</td>
					<td>
						<p>username：登陆用户名</p>
						<p>password:登陆密码</p>
					</td>
					<td>
						<p>app400：用户名或密码出错</p>
					</td>
				</tr>
				<tr>
					<td>游客登录</td>
					<td>敏婷</td>
					<td></td>
					<td><a
						href="${pageContext.request.contextPath}/all/app/token/loginGuest.do"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/app/token/loginGuest.do</a>
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>注销登陆</td>
					<td>敏婷</td>
					<td>{ "token": "cjx", "data": { } }</td>
					<td><a
						href="${pageContext.request.contextPath}/all/app/token/logout.do?json={token: 'abc',data: {}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/app/token/logout.do</a>
					</td>
					<td>
						<p>token：app登陆令牌</p>
					</td>
					<td>
						<p>app401：token出错,找不到登录用户</p>
					</td>
				</tr>
				<tr>
					<td>注册</td>
					<td>敏婷</td>
					<td>{"session":"" ,"code":"3776","name": "cjx1", "password":
						"cjx1"}</td>
					<td><a
						href="${pageContext.request.contextPath}/all/app/token/register.do?json={session:'E0FDAD23FDA7ECBFE555036544C539B6' ,code:'8870',name: 'cjx3',password:'cjx3'}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/app/token/register.do</a>
					</td>
					<td>
						<p>username：登陆用户名</p>
						<p>password:登陆密码</p>
						<p>code:验证码</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>注册(不需要验证码)</td>
					<td>不需要做</td>
					<td>{"name": "cjx", "password": "cjx", }</td>
					<td><a
						href="${pageContext.request.contextPath}/all/app/token/register2.do?json={name: 'cjx',password:'cjx'}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/app/token/register2.do</a>
					</td>
					<td>
						<p>username：登陆用户名</p>
						<p>password:登陆密码</p>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>关于我们(版本更新)</td>
					<td>敏婷</td>
					<td></td>
					<td><a
						href="${pageContext.request.contextPath}/all/app/version/form.do"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/app/version/form.do</a>
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>查询用户(所有)</td>
					<td></td>
					<td>{"token":"", "cmd": "queryAllUser","data":{}</td>
					<td><a
						href="${pageContext.request.contextPath}/all/app/openapi.do?json={token:'adee0b83400546a5afa6e257b054c02f',cmd:'queryAllUser',data:{}}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/app/openapi.do</a>
					</td>
					<td>
						<p>token：令牌</p>
						<p>cmd：queryAllUser</p>
					</td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>
<script type="text/javascript">
function findUrl(url){
	//alert('url='+url);
	url=encodeURI(url);
	//alert('url2='+url);
	window.location.href=url;
	return url;
}
</script>
<script type="text/javascript">
</script>
