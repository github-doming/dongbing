package all.workflow.actwf.actwf_business_def.t.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;

/**
 * The persistent class for the database table actwf_business_def 
 * 工作流业务定义ACTWF_BUSINESS_DEF的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "actwf_business_def")
public class ActwfBusinessDefT implements Serializable {

	private static final long serialVersionUID = 1L;
			@Id
	// 根据底层数据库
	@GeneratedValue(strategy = GenerationType.AUTO)
		
			
//工作流业务定义主键WF_BUSINESS_DEF_ID_
@Column(name="ACTWF_BUSINESS_DEF_ID_")
	private String actwfBusinessDefId;
	
	public void setActwfBusinessDefId(String actwfBusinessDefId) {
		this.actwfBusinessDefId=actwfBusinessDefId;
	}
	public String getActwfBusinessDefId() {
		return this.actwfBusinessDefId ;
	}
			
			
//英文标识KEY_
@Column(name="KEY_")
	private String key;
	
	public void setKey(String key) {
		this.key=key;
	}
	public String getKey() {
		return this.key ;
	}
			
			
//完成的步骤
@Column(name="STEP_")
	private Integer step;
	
	public void setStep(Integer step) {
		this.step=step;
	}
	public Integer getStep() {
		return this.step ;
	}
			
			
//名称NAME_
@Column(name="NAME_")
	private String name;
	
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
			
			
//工作流流程定义主健WF_FLOW_DEF_ID_
@Column(name="WF_FLOW_DEF_ID_")
	private String wfFlowDefId;
	
	public void setWfFlowDefId(String wfFlowDefId) {
		this.wfFlowDefId=wfFlowDefId;
	}
	public String getWfFlowDefId() {
		return this.wfFlowDefId ;
	}
			
			
//工作流表单定义主键WF_FORM_DEF_ID_
@Column(name="WF_FORM_DEF_ID_")
	private String wfFormDefId;
	
	public void setWfFormDefId(String wfFormDefId) {
		this.wfFormDefId=wfFormDefId;
	}
	public String getWfFormDefId() {
		return this.wfFormDefId ;
	}
			
			
//ACT流程定义ID
@Column(name="ACT_DEF_ID_")
	private String actDefId;
	
	public void setActDefId(String actDefId) {
		this.actDefId=actDefId;
	}
	public String getActDefId() {
		return this.actDefId ;
	}
			
			
//绑定流程KEY
@Column(name="ACT_DEF_KEY_")
	private String actDefKey;
	
	public void setActDefKey(String actDefKey) {
		this.actDefKey=actDefKey;
	}
	public String getActDefKey() {
		return this.actDefKey ;
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
			
			
//次序
@Column(name="SN_")
	private Integer sn;
	
	public void setSn(Integer sn) {
		this.sn=sn;
	}
	public Integer getSn() {
		return this.sn ;
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