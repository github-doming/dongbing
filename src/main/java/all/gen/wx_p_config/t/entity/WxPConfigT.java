package all.gen.wx_p_config.t.entity;

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
 * The persistent class for the database table wx_p_config 
 * 微信公众号配置WX_P_CONFIG的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "wx_p_config")
public class WxPConfigT implements Serializable {

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
		
			
//微信公众号配置WX_P_CONFIG_ID_
@Column(name="WX_P_CONFIG_ID_")
	private String wxPConfigId;
	
	public void setWxPConfigId(String wxPConfigId) {
		this.wxPConfigId=wxPConfigId;
	}
	public String getWxPConfigId() {
		return this.wxPConfigId ;
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
@Column(name="APP_TOKEN_")
	private String appToken;
	
	public void setAppToken(String appToken) {
		this.appToken=appToken;
	}
	public String getAppToken() {
		return this.appToken ;
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
	private Integer expiresIn;
	
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn=expiresIn;
	}
	public Integer getExpiresIn() {
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