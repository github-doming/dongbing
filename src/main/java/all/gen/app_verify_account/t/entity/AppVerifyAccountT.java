package all.gen.app_verify_account.t.entity;

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
 * The persistent class for the database table app_verify_account 
 * APP_VERIFY_ACCOUNT APP认证_账号验证码的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "app_verify_account")
public class AppVerifyAccountT implements Serializable {

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
		
			
//APP_VERIFY_ACCOUNT_ID_
@Column(name="APP_VERIFY_ACCOUNT_ID_")
	private String appVerifyAccountId;
	
	public void setAppVerifyAccountId(String appVerifyAccountId) {
		this.appVerifyAccountId=appVerifyAccountId;
	}
	public String getAppVerifyAccountId() {
		return this.appVerifyAccountId ;
	}
			
			
//账号名称ACCOUNT_NAME_
@Column(name="ACCOUNT_NAME_")
	private String accountName;
	
	public void setAccountName(String accountName) {
		this.accountName=accountName;
	}
	public String getAccountName() {
		return this.accountName ;
	}
			
			
//验证码
@Column(name="CODE_")
	private String code;
	
	public void setCode(String code) {
		this.code=code;
	}
	public String getCode() {
		return this.code ;
	}
			
			
//验证码序号
@Column(name="CODE_SN_")
	private Long codeSn;
	
	public void setCodeSn(Long codeSn) {
		this.codeSn=codeSn;
	}
	public Long getCodeSn() {
		return this.codeSn ;
	}
			
			
//类型
@Column(name="TYPE_")
	private String type;
	
	public void setType(String type) {
		this.type=type;
	}
	public String getType() {
		return this.type ;
	}
			
			
//SESSION_ID_
@Column(name="SESSION_ID_")
	private String sessionId;
	
	public void setSessionId(String sessionId) {
		this.sessionId=sessionId;
	}
	public String getSessionId() {
		return this.sessionId ;
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