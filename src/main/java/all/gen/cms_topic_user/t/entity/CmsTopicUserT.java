package all.gen.cms_topic_user.t.entity;

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
 * The persistent class for the database table cms_topic_user 
 * CMS主题与APP用户(权限)CMS_TOPIC_USER的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "cms_topic_user")
public class CmsTopicUserT implements Serializable {

	private static final long serialVersionUID = 1L;
				
			
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
		
			
//CMS主题与APP用户(权限)主键CMS_TOPIC_USER_ID_
@Column(name="CMS_TOPIC_USER_ID_")
	private String cmsTopicUserId;
	
	public void setCmsTopicUserId(String cmsTopicUserId) {
		this.cmsTopicUserId=cmsTopicUserId;
	}
	public String getCmsTopicUserId() {
		return this.cmsTopicUserId ;
	}
			
			
//用户主键APP_USER_ID_
@Column(name="APP_USER_ID_")
	private String appUserId;
	
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public String getAppUserId() {
		return this.appUserId ;
	}
			
			
//CMS_主题主键CMS_TOPIC_ID_
@Column(name="CMS_TOPIC_ID_")
	private String cmsTopicId;
	
	public void setCmsTopicId(String cmsTopicId) {
		this.cmsTopicId=cmsTopicId;
	}
	public String getCmsTopicId() {
		return this.cmsTopicId ;
	}
			
			
//相当于表名
@Column(name="TABLE_NAME_")
	private String tableName;
	
	public void setTableName(String tableName) {
		this.tableName=tableName;
	}
	public String getTableName() {
		return this.tableName ;
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
			
			
//TOPIC_TYPE_
@Column(name="TOPIC_TYPE_")
	private String topicType;
	
	public void setTopicType(String topicType) {
		this.topicType=topicType;
	}
	public String getTopicType() {
		return this.topicType ;
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