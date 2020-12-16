package all.gen.tms_task.t.entity;

import java.util.List;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import c.a.util.core.annotation.AnnotationEntity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import c.a.util.core.annotation.AnnotationTable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
/**
 * The persistent class for the database table tms_task 
 * TMS任务表TMS_TASK

的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "tms_task")
public class TmsTaskT implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<TmsTaskT > children;
	public List<TmsTaskT> getChildren() {
		return children;
	}
	public void setChildren(List<TmsTaskT> children) {
		this.children = children;
	}
				
			
//索引
@Column(name="IDX_")
	private Long idx;
	
	public void setIdx(Long idx) {
		this.idx=idx;
	}
	public Long getIdx() {
		return this.idx ;
	}
		@Id
	// 根据底层数据库
	@GeneratedValue(strategy = GenerationType.AUTO)
		
			
//TMS任务表主键TMS_TASK_ID_
@Column(name="TMS_TASK_ID_")
	private String tmsTaskId;
	
	public void setTmsTaskId(String tmsTaskId) {
		this.tmsTaskId=tmsTaskId;
	}
	public String getTmsTaskId() {
		return this.tmsTaskId ;
	}
			
			
//TMS项目表主键TMS_PROJECT_ID_ID_
@Column(name="TMS_PROJECT_ID_ID_")
	private String tmsProjectIdId;
	
	public void setTmsProjectIdId(String tmsProjectIdId) {
		this.tmsProjectIdId=tmsProjectIdId;
	}
	public String getTmsProjectIdId() {
		return this.tmsProjectIdId ;
	}
			
			
//次序
@Column(name="SN_")
	private Integer sn;
	
	public void setSn(Integer sn) {
		this.sn=sn;
	}
	public Integer getSn() {
		return this.sn ;
	}
			
			
//树上一级
@Column(name="PARENT_")
	private String parent;
	
	public void setParent(String parent) {
		this.parent=parent;
	}
	public String getParent() {
		return this.parent ;
	}
			
			
//树PATH_
@Column(name="PATH_")
	private String path;
	
	public void setPath(String path) {
		this.path=path;
	}
	public String getPath() {
		return this.path ;
	}
			
			
//树编码
@Column(name="TREE_CODE_")
	private String treeCode;
	
	public void setTreeCode(String treeCode) {
		this.treeCode=treeCode;
	}
	public String getTreeCode() {
		return this.treeCode ;
	}
			
			
//主题
@Column(name="TITLE_")
	private String title;
	
	public void setTitle(String title) {
		this.title=title;
	}
	public String getTitle() {
		return this.title ;
	}
			
			
//简介
@Column(name="CONTENT_SHORT_")
	private String contentShort;
	
	public void setContentShort(String contentShort) {
		this.contentShort=contentShort;
	}
	public String getContentShort() {
		return this.contentShort ;
	}
			
			
//内容
@Column(name="CONTENT_")
	private String content;
	
	public void setContent(String content) {
		this.content=content;
	}
	public String getContent() {
		return this.content ;
	}
			
			
//优先级high,medium,low
@Column(name="PRIORITY_")
	private String priority;
	
	public void setPriority(String priority) {
		this.priority=priority;
	}
	public String getPriority() {
		return this.priority ;
	}
			
			
//发送用户(发布人)SEND_USER_
@Column(name="SEND_USER_")
	private String sendUser;
	
	public void setSendUser(String sendUser) {
		this.sendUser=sendUser;
	}
	public String getSendUser() {
		return this.sendUser ;
	}
			
			
//接收用户(处理人)RECEIVE_USER_
@Column(name="RECEIVE_USER_")
	private String receiveUser;
	
	public void setReceiveUser(String receiveUser) {
		this.receiveUser=receiveUser;
	}
	public String getReceiveUser() {
		return this.receiveUser ;
	}
			
			
//类型TYPE_
@Column(name="TYPE_")
	private String type;
	
	public void setType(String type) {
		this.type=type;
	}
	public String getType() {
		return this.type ;
	}
			
			
//查看地址URL_VIEW_
@Column(name="URL_VIEW_")
	private String urlView;
	
	public void setUrlView(String urlView) {
		this.urlView=urlView;
	}
	public String getUrlView() {
		return this.urlView ;
	}
			
			
//是否回复REPLY_
@Column(name="REPLY_")
	private String reply;
	
	public void setReply(String reply) {
		this.reply=reply;
	}
	public String getReply() {
		return this.reply ;
	}
			
			
//是否有附件FILE_
@Column(name="FILE_")
	private String file;
	
	public void setFile(String file) {
		this.file=file;
	}
	public String getFile() {
		return this.file ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//阅读时间
@Column(name="READ_TIME_")
	private Date readTime;
	
	public void setReadTime(Date readTime) {
		this.readTime=readTime;
	}
	public Date getReadTime() {
		return this.readTime ;
	}
			
			
//版本迭代VERSION_
@Column(name="VERSION_")
	private Long version;
	
	public void setVersion(Long version) {
		this.version=version;
	}
	public Long getVersion() {
		return this.version ;
	}
			
			
//SVN版本SVN_VERSION_
@Column(name="SVN_VERSION_")
	private Long svnVersion;
	
	public void setSvnVersion(Long svnVersion) {
		this.svnVersion=svnVersion;
	}
	public Long getSvnVersion() {
		return this.svnVersion ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//SVN版本时间SVN_TIME_
@Column(name="SVN_TIME_")
	private Date svnTime;
	
	public void setSvnTime(Date svnTime) {
		this.svnTime=svnTime;
	}
	public Date getSvnTime() {
		return this.svnTime ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//任务开始时间TASK_START_TIME_
@Column(name="TASK_START_TIME_")
	private Date taskStartTime;
	
	public void setTaskStartTime(Date taskStartTime) {
		this.taskStartTime=taskStartTime;
	}
	public Date getTaskStartTime() {
		return this.taskStartTime ;
	}
			
			
//任务开始时间数字型TASK_START_TIME_LONG_
@Column(name="TASK_START_TIME_LONG_")
	private Long taskStartTimeLong;
	
	public void setTaskStartTimeLong(Long taskStartTimeLong) {
		this.taskStartTimeLong=taskStartTimeLong;
	}
	public Long getTaskStartTimeLong() {
		return this.taskStartTimeLong ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//任务结束时间TASK_END_TIME_
@Column(name="TASK_END_TIME_")
	private Date taskEndTime;
	
	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime=taskEndTime;
	}
	public Date getTaskEndTime() {
		return this.taskEndTime ;
	}
			
			
//任务结束时间数字型TASK_END_TIME_LONG_
@Column(name="TASK_END_TIME_LONG_")
	private Long taskEndTimeLong;
	
	public void setTaskEndTimeLong(Long taskEndTimeLong) {
		this.taskEndTimeLong=taskEndTimeLong;
	}
	public Long getTaskEndTimeLong() {
		return this.taskEndTimeLong ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//计划开始时间PLAN_START_TIME_
@Column(name="PLAN_START_TIME_")
	private Date planStartTime;
	
	public void setPlanStartTime(Date planStartTime) {
		this.planStartTime=planStartTime;
	}
	public Date getPlanStartTime() {
		return this.planStartTime ;
	}
			
			
//计划开始时间数字型PLAN_START_TIME_LONG_
@Column(name="PLAN_START_TIME_LONG_")
	private Long planStartTimeLong;
	
	public void setPlanStartTimeLong(Long planStartTimeLong) {
		this.planStartTimeLong=planStartTimeLong;
	}
	public Long getPlanStartTimeLong() {
		return this.planStartTimeLong ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//计划结束时间PLAN_END_TIME_
@Column(name="PLAN_END_TIME_")
	private Date planEndTime;
	
	public void setPlanEndTime(Date planEndTime) {
		this.planEndTime=planEndTime;
	}
	public Date getPlanEndTime() {
		return this.planEndTime ;
	}
			
			
//计划结束时间数字型PLAN_END_TIME_LONG_
@Column(name="PLAN_END_TIME_LONG_")
	private Long planEndTimeLong;
	
	public void setPlanEndTimeLong(Long planEndTimeLong) {
		this.planEndTimeLong=planEndTimeLong;
	}
	public Long getPlanEndTimeLong() {
		return this.planEndTimeLong ;
	}
			
			
//创建者CREATE_USER_
@Column(name="CREATE_USER_")
	private String createUser;
	
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public String getCreateUser() {
		return this.createUser ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//创建时间
@Column(name="CREATE_TIME_")
	private Date createTime;
	
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
	}
	public Date getCreateTime() {
		return this.createTime ;
	}
			
			
//创建时间
@Column(name="CREATE_TIME_LONG_")
	private Long createTimeLong;
	
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong ;
	}
			
			
//更新者UPDATE_USER_
@Column(name="UPDATE_USER_")
	private String updateUser;
	
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public String getUpdateUser() {
		return this.updateUser ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//更新时间
@Column(name="UPDATE_TIME_")
	private Date updateTime;
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime=updateTime;
	}
	public Date getUpdateTime() {
		return this.updateTime ;
	}
			
			
//更新时间
@Column(name="UPDATE_TIME_LONG_")
	private Long updateTimeLong;
	
	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
			
			
//DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
@Column(name="STATE_")
	private String state;
	
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
	}
			
			
//描述
@Column(name="DESC_")
	private String desc;
	
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}

	private String tableNameMy;
	/*
	 * 不映射
	 * 
	 * @return
	 */
	@Transient
	public String getTableNameMy() {
		return tableNameMy;
	}

	public void setTableNameMy(String tableNameMy) {
		this.tableNameMy = tableNameMy;
	}


}