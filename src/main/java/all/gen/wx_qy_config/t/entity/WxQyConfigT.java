package all.gen.wx_qy_config.t.entity;

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
 * The persistent class for the database table wx_qy_config 
 * 微信企业号配置WX_QY_CONFIG的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "wx_qy_config")
public class WxQyConfigT implements Serializable {

	private static final long serialVersionUID = 1L;
				
			
//租户名称
@Column(name="TENANT_NAME_CN_")
	private String tenantNameCn;
	
	public void setTenantNameCn(String tenantNameCn) {
		this.tenantNameCn=tenantNameCn;
	}
	public String getTenantNameCn() {
		return this.tenantNameCn ;
	}
			
			
//应用凭证
@Column(name="APP_TOKEN_")
	private String appToken;
	
	public void setAppToken(String appToken) {
		this.appToken=appToken;
	}
	public String getAppToken() {
		return this.appToken ;
	}
			
			
//应用ID
@Column(name="CORP_ID_")
	private String corpId;
	
	public void setCorpId(String corpId) {
		this.corpId=corpId;
	}
	public String getCorpId() {
		return this.corpId ;
	}
			
			
//应用密钥
@Column(name="CORP_SECRET_")
	private String corpSecret;
	
	public void setCorpSecret(String corpSecret) {
		this.corpSecret=corpSecret;
	}
	public String getCorpSecret() {
		return this.corpSecret ;
	}
			
			
//获取到的凭证(访问凭证)
@Column(name="ACCESS_TOKEN_")
	private String accessToken;
	
	public void setAccessToken(String accessToken) {
		this.accessToken=accessToken;
	}
	public String getAccessToken() {
		return this.accessToken ;
	}
			
			
//凭证有效时间，单位：秒
@Column(name="EXPIRES_IN_")
	private String expiresIn;
	
	public void setExpiresIn(String expiresIn) {
		this.expiresIn=expiresIn;
	}
	public String getExpiresIn() {
		return this.expiresIn ;
	}
			
			
//凭证过期时间
@Column(name="EXPIRES_TIME_")
	private Long expiresTime;
	
	public void setExpiresTime(Long expiresTime) {
		this.expiresTime=expiresTime;
	}
	public Long getExpiresTime() {
		return this.expiresTime ;
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
		
			
//微信企业号配置主键
@Column(name="WX_QY_CONFIG_ID_")
	private String wxQyConfigId;
	
	public void setWxQyConfigId(String wxQyConfigId) {
		this.wxQyConfigId=wxQyConfigId;
	}
	public String getWxQyConfigId() {
		return this.wxQyConfigId ;
	}
			
			
//原始ID
@Column(name="INIT_ID_")
	private String initId;
	
	public void setInitId(String initId) {
		this.initId=initId;
	}
	public String getInitId() {
		return this.initId ;
	}
			
			
//应用凭证
@Column(name="APP_TOKEN_2")
	private String appToken2;
	
	public void setAppToken2(String appToken2) {
		this.appToken2=appToken2;
	}
	public String getAppToken2() {
		return this.appToken2 ;
	}
			
			
//应用ID
@Column(name="APP_ID_")
	private String appId;
	
	public void setAppId(String appId) {
		this.appId=appId;
	}
	public String getAppId() {
		return this.appId ;
	}
			
			
//应用密钥
@Column(name="APP_SECRET_")
	private String appSecret;
	
	public void setAppSecret(String appSecret) {
		this.appSecret=appSecret;
	}
	public String getAppSecret() {
		return this.appSecret ;
	}
			
			
//获取到的凭证(访问凭证)
@Column(name="ACCESS_TOKEN_2")
	private String accessToken2;
	
	public void setAccessToken2(String accessToken2) {
		this.accessToken2=accessToken2;
	}
	public String getAccessToken2() {
		return this.accessToken2 ;
	}
			
			
//凭证有效时间，单位：秒
@Column(name="EXPIRES_IN_2")
	private Integer expiresIn2;
	
	public void setExpiresIn2(Integer expiresIn2) {
		this.expiresIn2=expiresIn2;
	}
	public Integer getExpiresIn2() {
		return this.expiresIn2 ;
	}
			
			
//凭证过期时间
@Column(name="EXPIRES_TIME_2")
	private Long expiresTime2;
	
	public void setExpiresTime2(Long expiresTime2) {
		this.expiresTime2=expiresTime2;
	}
	public Long getExpiresTime2() {
		return this.expiresTime2 ;
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
			
		@Temporal( TemporalType.TIMESTAMP)
		
//创建时间 CREATE_TIME_
@Column(name="CREATE_TIME_")
	private Date createTime;
	
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
	}
	public Date getCreateTime() {
		return this.createTime ;
	}
			
			
//创建时间数字型 CREATE_TIME_LONG_
@Column(name="CREATE_TIME_LONG_")
	private Long createTimeLong;
	
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong ;
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