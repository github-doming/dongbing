package all.task.tms.project_user.tms_project_user.entity;

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
 * The persistent class for the database table tms_project_user 
 * 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "tms_project_user")
public class TmsProjectUserProjectUser implements Serializable {

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
		//自己制定ID
	// @Id
	// 根据底层数据库
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
		
			
//TMS_PROJECT_USER_ID_
@Column(name="TMS_PROJECT_USER_ID_")
	private String tmsProjectUserId;
	
	public void setTmsProjectUserId(String tmsProjectUserId) {
		this.tmsProjectUserId=tmsProjectUserId;
	}
	public String getTmsProjectUserId() {
		return this.tmsProjectUserId ;
	}
			
			
//TMS项目表主键TMS_PROJECT_ID_
@Column(name="TMS_PROJECT_ID_")
	private String tmsProjectId;
	
	public void setTmsProjectId(String tmsProjectId) {
		this.tmsProjectId=tmsProjectId;
	}
	public String getTmsProjectId() {
		return this.tmsProjectId ;
	}
			
			
//用户ID
@Column(name="USER_ID_")
	private String userId;
	
	public void setUserId(String userId) {
		this.userId=userId;
	}
	public String getUserId() {
		return this.userId ;
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
			
			
//租户编码TENANT_CODE_
@Column(name="TENANT_CODE_")
	private String tenantCode;
	
	public void setTenantCode(String tenantCode) {
		this.tenantCode=tenantCode;
	}
	public String getTenantCode() {
		return this.tenantCode ;
	}
			
			
//租户编号TENANT_NUMBER_
@Column(name="TENANT_NUMBER_")
	private Integer tenantNumber;
	
	public void setTenantNumber(Integer tenantNumber) {
		this.tenantNumber=tenantNumber;
	}
	public Integer getTenantNumber() {
		return this.tenantNumber ;
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