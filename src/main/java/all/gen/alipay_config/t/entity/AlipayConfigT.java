package all.gen.alipay_config.t.entity;

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
 * The persistent class for the database table alipay_config 
 * 支付宝支付配置ALIPAY_CONFIG的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "alipay_config")
public class AlipayConfigT implements Serializable {

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
		
			
//支付宝支付配置主键ALIPAY_CONFIG_ID_
@Column(name="ALIPAY_CONFIG_ID_")
	private String alipayConfigId;
	
	public void setAlipayConfigId(String alipayConfigId) {
		this.alipayConfigId=alipayConfigId;
	}
	public String getAlipayConfigId() {
		return this.alipayConfigId ;
	}
			
			
//系统编码CODE_
@Column(name="CODE_")
	private String code;
	
	public void setCode(String code) {
		this.code=code;
	}
	public String getCode() {
		return this.code ;
	}
			
			
//应用ID APP_ID_
@Column(name="APP_ID_")
	private String appId;
	
	public void setAppId(String appId) {
		this.appId=appId;
	}
	public String getAppId() {
		return this.appId ;
	}
			
			
//应用私钥APP_PRIVATE_KEY_
@Column(name="APP_PRIVATE_KEY_")
	private String appPrivateKey;
	
	public void setAppPrivateKey(String appPrivateKey) {
		this.appPrivateKey=appPrivateKey;
	}
	public String getAppPrivateKey() {
		return this.appPrivateKey ;
	}
			
			
//应用公钥APP_PUBLIC_KEY_
@Column(name="APP_PUBLIC_KEY_")
	private String appPublicKey;
	
	public void setAppPublicKey(String appPublicKey) {
		this.appPublicKey=appPublicKey;
	}
	public String getAppPublicKey() {
		return this.appPublicKey ;
	}
			
			
//第三方支付宝私钥T_PRIVATE_KEY_
@Column(name="T_PRIVATE_KEY_")
	private String tPrivateKey;
	
	public void setTPrivateKey(String tPrivateKey) {
		this.tPrivateKey=tPrivateKey;
	}
	public String getTPrivateKey() {
		return this.tPrivateKey ;
	}
			
			
//第三方支付宝公钥T_PUBLIC_KEY_ 
@Column(name="T_PUBLIC_KEY_")
	private String tPublicKey;
	
	public void setTPublicKey(String tPublicKey) {
		this.tPublicKey=tPublicKey;
	}
	public String getTPublicKey() {
		return this.tPublicKey ;
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
			
			
//获取到的凭证(访问凭证)APP_AUTH_TOKEN_
@Column(name="APP_AUTH_TOKEN_")
	private String appAuthToken;
	
	public void setAppAuthToken(String appAuthToken) {
		this.appAuthToken=appAuthToken;
	}
	public String getAppAuthToken() {
		return this.appAuthToken ;
	}
			
			
//凭证有效时间，单位：秒
@Column(name="EXPIRES_IN_")
	private Integer expiresIn;
	
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn=expiresIn;
	}
	public Integer getExpiresIn() {
		return this.expiresIn ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//凭证过期时间
@Column(name="EXPIRES_TIME_")
	private Date expiresTime;
	
	public void setExpiresTime(Date expiresTime) {
		this.expiresTime=expiresTime;
	}
	public Date getExpiresTime() {
		return this.expiresTime ;
	}
			
			
//过期时间数字型
@Column(name="EXPIRES_TIME_LONG_")
	private Long expiresTimeLong;
	
	public void setExpiresTimeLong(Long expiresTimeLong) {
		this.expiresTimeLong=expiresTimeLong;
	}
	public Long getExpiresTimeLong() {
		return this.expiresTimeLong ;
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