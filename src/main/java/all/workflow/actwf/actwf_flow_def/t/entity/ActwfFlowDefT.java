package all.workflow.actwf.actwf_flow_def.t.entity;

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
 * The persistent class for the database table actwf_flow_def 
 * 流程定义ACTWF_FLOW_DEF的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "actwf_flow_def")
public class ActwfFlowDefT implements Serializable {

	private static final long serialVersionUID = 1L;
			@Id
	// 根据底层数据库
	@GeneratedValue(strategy = GenerationType.AUTO)
		
			
//工作流流程定义主键ACTWF_FLOW_DEF_ID_
@Column(name="ACTWF_FLOW_DEF_ID_")
	private String actwfFlowDefId;
	
	public void setActwfFlowDefId(String actwfFlowDefId) {
		this.actwfFlowDefId=actwfFlowDefId;
	}
	public String getActwfFlowDefId() {
		return this.actwfFlowDefId ;
	}
			
			
//名称
@Column(name="NAME_")
	private String name;
	
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
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
			
			
//ACT流程定义ID
@Column(name="ACT_DEF_ID_")
	private String actDefId;
	
	public void setActDefId(String actDefId) {
		this.actDefId=actDefId;
	}
	public String getActDefId() {
		return this.actDefId ;
	}
			
			
//ACT流程发布ID
@Column(name="ACT_DEP_ID_")
	private String actDepId;
	
	public void setActDepId(String actDepId) {
		this.actDepId=actDepId;
	}
	public String getActDepId() {
		return this.actDepId ;
	}
			
			
//设计模型ID,关联activity中的ACT_RE_MODEL表主键
@Column(name="ACT_MODEL_ID_")
	private String actModelId;
	
	public void setActModelId(String actModelId) {
		this.actModelId=actModelId;
	}
	public String getActModelId() {
		return this.actModelId ;
	}
			
			
//显示版本EDITION_
@Column(name="EDITION_")
	private String edition;
	
	public void setEdition(String edition) {
		this.edition=edition;
	}
	public String getEdition() {
		return this.edition ;
	}
			
			
//是否主版本
@Column(name="MAIN_EDITION_")
	private String mainEdition;
	
	public void setMainEdition(String mainEdition) {
		this.mainEdition=mainEdition;
	}
	public String getMainEdition() {
		return this.mainEdition ;
	}
			
			
//定义属性设置
@Column(name="SETTING_")
	private String setting;
	
	public void setSetting(String setting) {
		this.setting=setting;
	}
	public String getSetting() {
		return this.setting ;
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
			
			
//次序
@Column(name="SN_")
	private Integer sn;
	
	public void setSn(Integer sn) {
		this.sn=sn;
	}
	public Integer getSn() {
		return this.sn ;
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