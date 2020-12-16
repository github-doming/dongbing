package all.gen.tms_task.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table tms_task 
 * vo类
 */

public class TmsTaskTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//TMS任务表主键TMS_TASK_ID_
	private String tmsTaskId;
	public void setTmsTaskId(String tmsTaskId) {
		this.tmsTaskId=tmsTaskId;
	}
	public String getTmsTaskId() {
		return this.tmsTaskId ;
	}
	
//TMS项目表主键TMS_PROJECT_ID_ID_
	private String tmsProjectIdId;
	public void setTmsProjectIdId(String tmsProjectIdId) {
		this.tmsProjectIdId=tmsProjectIdId;
	}
	public String getTmsProjectIdId() {
		return this.tmsProjectIdId ;
	}
	
//次序
	private String sn;
	public void setSn(String sn) {
		this.sn=sn;
	}
	public String getSn() {
		return this.sn ;
	}
	
//树上一级
	private String parent;
	public void setParent(String parent) {
		this.parent=parent;
	}
	public String getParent() {
		return this.parent ;
	}
	
//树PATH_
	private String path;
	public void setPath(String path) {
		this.path=path;
	}
	public String getPath() {
		return this.path ;
	}
	
//树编码
	private String treeCode;
	public void setTreeCode(String treeCode) {
		this.treeCode=treeCode;
	}
	public String getTreeCode() {
		return this.treeCode ;
	}
	
//主题
	private String title;
	public void setTitle(String title) {
		this.title=title;
	}
	public String getTitle() {
		return this.title ;
	}
	
//简介
	private String contentShort;
	public void setContentShort(String contentShort) {
		this.contentShort=contentShort;
	}
	public String getContentShort() {
		return this.contentShort ;
	}
	
//内容
	private String content;
	public void setContent(String content) {
		this.content=content;
	}
	public String getContent() {
		return this.content ;
	}
	
//优先级high,medium,low
	private String priority;
	public void setPriority(String priority) {
		this.priority=priority;
	}
	public String getPriority() {
		return this.priority ;
	}
	
//发送用户(发布人)SEND_USER_
	private String sendUser;
	public void setSendUser(String sendUser) {
		this.sendUser=sendUser;
	}
	public String getSendUser() {
		return this.sendUser ;
	}
	
//接收用户(处理人)RECEIVE_USER_
	private String receiveUser;
	public void setReceiveUser(String receiveUser) {
		this.receiveUser=receiveUser;
	}
	public String getReceiveUser() {
		return this.receiveUser ;
	}
	
//类型TYPE_
	private String type;
	public void setType(String type) {
		this.type=type;
	}
	public String getType() {
		return this.type ;
	}
	
//查看地址URL_VIEW_
	private String urlView;
	public void setUrlView(String urlView) {
		this.urlView=urlView;
	}
	public String getUrlView() {
		return this.urlView ;
	}
	
//是否回复REPLY_
	private String reply;
	public void setReply(String reply) {
		this.reply=reply;
	}
	public String getReply() {
		return this.reply ;
	}
	
//是否有附件FILE_
	private String file;
	public void setFile(String file) {
		this.file=file;
	}
	public String getFile() {
		return this.file ;
	}
	
//阅读时间
	private String readTime;
	public void setReadTime(String readTime) {
		this.readTime=readTime;
	}
	public String getReadTime() {
		return this.readTime ;
	}
	
//版本迭代VERSION_
	private String version;
	public void setVersion(String version) {
		this.version=version;
	}
	public String getVersion() {
		return this.version ;
	}
	
//SVN版本SVN_VERSION_
	private String svnVersion;
	public void setSvnVersion(String svnVersion) {
		this.svnVersion=svnVersion;
	}
	public String getSvnVersion() {
		return this.svnVersion ;
	}
	
//SVN版本时间SVN_TIME_
	private String svnTime;
	public void setSvnTime(String svnTime) {
		this.svnTime=svnTime;
	}
	public String getSvnTime() {
		return this.svnTime ;
	}
	
//任务开始时间TASK_START_TIME_
	private String taskStartTime;
	public void setTaskStartTime(String taskStartTime) {
		this.taskStartTime=taskStartTime;
	}
	public String getTaskStartTime() {
		return this.taskStartTime ;
	}
	
//任务开始时间数字型TASK_START_TIME_LONG_
	private String taskStartTimeLong;
	public void setTaskStartTimeLong(String taskStartTimeLong) {
		this.taskStartTimeLong=taskStartTimeLong;
	}
	public String getTaskStartTimeLong() {
		return this.taskStartTimeLong ;
	}
	
//任务结束时间TASK_END_TIME_
	private String taskEndTime;
	public void setTaskEndTime(String taskEndTime) {
		this.taskEndTime=taskEndTime;
	}
	public String getTaskEndTime() {
		return this.taskEndTime ;
	}
	
//任务结束时间数字型TASK_END_TIME_LONG_
	private String taskEndTimeLong;
	public void setTaskEndTimeLong(String taskEndTimeLong) {
		this.taskEndTimeLong=taskEndTimeLong;
	}
	public String getTaskEndTimeLong() {
		return this.taskEndTimeLong ;
	}
	
//计划开始时间PLAN_START_TIME_
	private String planStartTime;
	public void setPlanStartTime(String planStartTime) {
		this.planStartTime=planStartTime;
	}
	public String getPlanStartTime() {
		return this.planStartTime ;
	}
	
//计划开始时间数字型PLAN_START_TIME_LONG_
	private String planStartTimeLong;
	public void setPlanStartTimeLong(String planStartTimeLong) {
		this.planStartTimeLong=planStartTimeLong;
	}
	public String getPlanStartTimeLong() {
		return this.planStartTimeLong ;
	}
	
//计划结束时间PLAN_END_TIME_
	private String planEndTime;
	public void setPlanEndTime(String planEndTime) {
		this.planEndTime=planEndTime;
	}
	public String getPlanEndTime() {
		return this.planEndTime ;
	}
	
//计划结束时间数字型PLAN_END_TIME_LONG_
	private String planEndTimeLong;
	public void setPlanEndTimeLong(String planEndTimeLong) {
		this.planEndTimeLong=planEndTimeLong;
	}
	public String getPlanEndTimeLong() {
		return this.planEndTimeLong ;
	}
	
//创建者CREATE_USER_
	private String createUser;
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public String getCreateUser() {
		return this.createUser ;
	}
	
//创建时间
	private String createTime;
	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}
	public String getCreateTime() {
		return this.createTime ;
	}
	
//创建时间
	private String createTimeLong;
	public void setCreateTimeLong(String createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public String getCreateTimeLong() {
		return this.createTimeLong ;
	}
	
//更新者UPDATE_USER_
	private String updateUser;
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public String getUpdateUser() {
		return this.updateUser ;
	}
	
//更新时间
	private String updateTime;
	public void setUpdateTime(String updateTime) {
		this.updateTime=updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime ;
	}
	
//更新时间
	private String updateTimeLong;
	public void setUpdateTimeLong(String updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public String getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
	
//DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	private String state;
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
	}
	
//描述
	private String desc;
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}


}