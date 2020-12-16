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
		<h1>api接口测试页</h1>
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th>接口名称</th>
					<th>接口定义</th>
					<th>接口调用示例</th>
					<th>参数说明</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>用户登陆</td>
					<td>login(username,password)</td>
					<td><a
						href="${pageContext.request.contextPath}/mobile/login?username=admin&password=admin"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/mobile/login?username=admin&password=admin</a>
					</td>
					<td>
						<p>username：登陆用户名</p>
						<p>password:登陆密码</p>
					</td>
				</tr>
				<tr>
					<td>注销登陆</td>
					<td>mlogout(username)</td>
					<td><a href="${pageContext.request.contextPath}/mobile/logout?username=admin"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/mobile/logout?username=admin</a>
					</td>
					<td>username：登陆用户名</td>
				</tr>
				<tr>
					<td>企业信息</td>
					<td>getEnt(entNo)</td>
					<td><a href="${pageContext.request.contextPath}/ent/enterprise/mobile/ent/list/admin/2006"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/ent/enterprise/mobile/ent/list/{loginName}/{entNo}</a>
					</td>
					<td>
						<p>loginName:登陆用户名</p>
						<p>entNo:餐饮许可证号</p>
					</td>
				</tr>
				<tr>
					<td>企业信息明细</td>
					<td>getEntById(id)</td>
					<td><a href="${pageContext.request.contextPath}/ent/enterprise/mobile/ent/detail/1"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/ent/enterprise/mobile/ent/detail/{id}</a>
					</td>
					<td>id:企业id</td>
				</tr>
				<tr>
					<td>获取企业许可证</td>
					<td>getPermitByEntId(entId)</td>
					<td><a href="${pageContext.request.contextPath}/ent/permit/mobile/ent/permit/1"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/ent/permit/mobile/ent/permit/{entId}</a>
					</td>
					<td>entId:企业id</td>
				</tr>
				<tr>
					<td>知识库类型</td>
					<td>getRepType()</td>
					<td><a href="${pageContext.request.contextPath}/check/repository/mobile/reptype/list/"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/check/repository/mobile/reptype/list/</a>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>根据类型获取知识库</td>
					<td>getRepByType(String value)</td>
					<td><a href="${pageContext.request.contextPath}/check/repository/mobile/rep/list/2"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/check/repository/mobile/rep/list/<span
							style="color: #ff0000">2</span></a></td>
					<td>红色"2"为 知识库类型的“value”属性值</td>
				</tr>
				<tr>
					<td>保存抽检记录</td>
					<td>saveRecord(Map params)</td>
					<td><a href="javascript:void(0)" target="_blank">http://IP:PORT${pageContext.request.contextPath}/check/record/mobile/save/
					</td>
					<td>
						<p>参数类型map</p>
						<p>code：32位UUID标签码</p>
						<p>inpDate：抽检时间</p>
						<p>startHour：抽检开始小时</p>
						<p>startMin：抽检开始分钟</p>
						<p>endHour：抽检结束小时</p>
						<p>endMin：抽检结束分钟</p>
						<p>content：抽检项目ID和结果，例如：id1:result1,id2:result2……</p>
						<p>inpUser：当前登录账号</p>
						<p>entName：被抽企业名称</p>
						<p>entPhone：被抽企业电话</p>
						<p>entAddr：被抽企业地址</p>
						<p>entPermit：被抽企业卫生许可证</p>
					</td>
				</tr>
				<tr>
					<td>生成PDF</td>
					<td>getPrintPdf(String tagCode)</td>
					<td><a
						href="${pageContext.request.contextPath}/check/record/mobile/create/report/8a488efd3e3ad713013e3aef19010036"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/check/record/mobile/create/report/<span
							style="color: #ff0000">8a488efd3e3ad713013e3aef19010036</span></a></td>
					<td>红色部分为32位UUID标识码</td>
				</tr>
				<tr>
					<td>附件上传</td>
					<td></td>
					<td>
						<form method="post" action="${pageContext.request.contextPath}/check/attach/mobile/upload"
							enctype="multipart/form-data">
							<input type="file" name="file">
							<button type="submit">提交</button>
						</form>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>获取标签项</td>
					<td>getRepTagList()</td>
					<td><a href="${pageContext.request.contextPath}/check/repository/mobile/rep/tag/list/">http://IP:PORT${pageContext.request.contextPath}/check/repository/mobile/rep/tag/list/</a>
					</td>
					<td>返回数据： id:检查项目id tag:抽检标签项</td>
				</tr>
				<tr>
					<td>获取标签子项目</td>
					<td>getTagItemList()</td>
					<td><a
						href="${pageContext.request.contextPath}/check/repository/mobile/rep/tag/item/list/5/">http://IP:PORT${pageContext.request.contextPath}/check/repository/mobile/rep/tag/item/list/{标签id}</a>
					</td>
					<td>返回数据： id:检查项目id tag:抽检标签项，content:抽检项目</td>
				</tr>
				<tr>
					<td>获取所有检查项目</td>
					<td>listByTag()</td>
					<td><a
						href="${pageContext.request.contextPath}/check/repository/mobile/list?pageNo=1&pageSize=10">http://IP:PORT${pageContext.request.contextPath}/check/repository/mobile/list?pageNo=1&pageSize=10</a>
					</td>
					<td>pageNo:页数，pageSize:每页记录条数， 返回数据：同知识库，count:记录总条数</td>
				</tr>
				<tr>
					<td>查询任务明细</td>
					<td>listTaskInfoById(id)</td>
					<td><a href="${pageContext.request.contextPath}/task/info/mobile/task_info/list/1"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/task/info/mobile/task_info/list/{任务id}</a>
					</td>
					<td>
						<p>id：任务id</p>
					</td>
				</tr>
				<tr>
					<td>任务已读(状态改为已读)</td>
					<td>update_state2read(task_pubish_id)</td>
					<td><a
						href="${pageContext.request.contextPath}/task/pubish/mobile/task_pubish/update_state2read?task_pubish_id=1"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/task/pubish/mobile/task_pubish/update_state2read?task_pubish_id=1</a>
					</td>
					<td>
						<p>task_pubish_id：任务下发id</p>
					</td>
				</tr>
				<tr>
					<td>反馈(完成状态改为已完成)</td>
					<td>update_feedback(task_pubish_id,feedback)</td>
					<td><a
						href="${pageContext.request.contextPath}/task/pubish/mobile/task_pubish/update_feedback?task_pubish_id=1&feedback=test"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/task/pubish/mobile/task_pubish/update_feedback?task_pubish_id=1&feedback=test</a>
					</td>
					<td>
						<p>task_pubish_id：任务下发id</p>
						<p>feedback:反馈内容</p>
					</td>
				</tr>
				<tr>
					<td>个人的任务下发</td>
					<td>listTaskPubish</td>
					<td><a
						href="${pageContext.request.contextPath}/task/pubish/mobile/task_pubish/list?login_name=admin&state=0&complete_state=0&number=5"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/task/pubish/mobile/task_pubish/list?login_name=admin&&state=0&&complete_state=0&number=5</a>
					</td>
					<td>
						<p>login_name：登录用户名</p>
						<p>state:状态(0:未读;1:已读)</p>
						<p>complete_state:完成状态(0:未读;1:已读)</p>
						<p>number:最新任务数(查询最新5条记录)</p>
					</td>
				</tr>
				<tr>
					<td>根据许可证查检查记录</td>
					<td>listCheckRecord_byEntPermit</td>
					<td><a
						href="${pageContext.request.contextPath}/check/record/mobile/list_by_entpermit?ent_permit=gvv"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/check/record/mobile/list_by_entpermit?ent_permit=gvv</a>
					</td>
					<td>
						<p>ent_permit：许可证</p>
					</td>
				</tr>
				<tr>
					<td>查询所有的信息通告</td>
					<td>listAllNoticeInfo</td>
					<td><a href="${pageContext.request.contextPath}/msg/noticeInfo/mobile/list"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/msg/noticeInfo/mobile/list</a>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>查询通告明细</td>
					<td>listNoticeInfoById(mid)</td>
					<td><a href="${pageContext.request.contextPath}/msg/noticeInfo/mobile/msg_info/list?mid=1"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/msg/noticeInfo/mobile/msg_info/list?mid=1</a>
					</td>
					<td>
						<p>mid：通告id</p>
					</td>
				</tr>
				<tr>
					<td>个人的信息通告</td>
					<td>listMsgPubish</td>
					<td><a
						href="${pageContext.request.contextPath}/msg/noticePubish/mobile/msg_pubish/list?login_name=admin&state=0&number=5"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/msg/noticePubish/mobile/msg_pubish/list?login_name=admin&&state=0&&number=5</a>
					</td>
					<td>
						<p>login_name：登录用户名</p>
						<p>state:状态(0:未读;1:已读)</p>
						<p>number:最新[信息通告 ]数(查询最新5条记录)</p>
					</td>
				</tr>
				<tr>
					<td>通告已读(状态改为已读)</td>
					<td>update_state2read(pubish_id)</td>
					<td><a
						href="${pageContext.request.contextPath}/msg/noticePubish/mobile/msg_pubish/update_state2read?pubish_id=1"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/msg/noticePubish/mobile/msg_pubish/update_state2read?pubish_id=1</a>
					</td>
					<td>
						<p>pubish_id：通告下发id</p>
					</td>
				</tr>
				<tr>
					<td>企业处罚记录</td>
					<td>getEntPunish(entNo)</td>
					<td><a href="${pageContext.request.contextPath}/ent/punish/mobile/punish/list/125"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/ent/punish/mobile/punish/list/{entNo}</a>
					</td>
					<td>entNo:餐饮许可证号</td>
				</tr>
				<tr>
					<td>企业处罚记录明细</td>
					<td>getPunishById(id)</td>
					<td><a href="${pageContext.request.contextPath}/ent/punish/mobile/punish/1"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/ent/punish/mobile/punish/{id}</a>
					</td>
					<td>id:处罚记录id</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>
