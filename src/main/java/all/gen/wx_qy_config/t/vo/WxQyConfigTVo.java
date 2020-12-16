package all.gen.wx_qy_config.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table wx_qy_config 
 * vo类
 */

public class WxQyConfigTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//租户名称
	private String tenantNameCn;
	public void setTenantNameCn(String tenantNameCn) {
		this.tenantNameCn=tenantNameCn;
	}
	public String getTenantNameCn() {
		return this.tenantNameCn ;
	}
	
//应用凭证
	private String appToken;
	public void setAppToken(String appToken) {
		this.appToken=appToken;
	}
	public String getAppToken() {
		return this.appToken ;
	}
	
//应用ID
	private String corpId;
	public void setCorpId(String corpId) {
		this.corpId=corpId;
	}
	public String getCorpId() {
		return this.corpId ;
	}
	
//应用密钥
	private String corpSecret;
	public void setCorpSecret(String corpSecret) {
		this.corpSecret=corpSecret;
	}
	public String getCorpSecret() {
		return this.corpSecret ;
	}
	
//获取到的凭证(访问凭证)
	private String accessToken;
	public void setAccessToken(String accessToken) {
		this.accessToken=accessToken;
	}
	public String getAccessToken() {
		return this.accessToken ;
	}
	
//凭证有效时间，单位：秒
	private String expiresIn;
	public void setExpiresIn(String expiresIn) {
		this.expiresIn=expiresIn;
	}
	public String getExpiresIn() {
		return this.expiresIn ;
	}
	
//凭证过期时间
	private String expiresTime;
	public void setExpiresTime(String expiresTime) {
		this.expiresTime=expiresTime;
	}
	public String getExpiresTime() {
		return this.expiresTime ;
	}
	
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//微信企业号配置主键
	private String wxQyConfigId;
	public void setWxQyConfigId(String wxQyConfigId) {
		this.wxQyConfigId=wxQyConfigId;
	}
	public String getWxQyConfigId() {
		return this.wxQyConfigId ;
	}
	
//原始ID
	private String initId;
	public void setInitId(String initId) {
		this.initId=initId;
	}
	public String getInitId() {
		return this.initId ;
	}
	
//应用凭证
	private String appToken2;
	public void setAppToken2(String appToken2) {
		this.appToken2=appToken2;
	}
	public String getAppToken2() {
		return this.appToken2 ;
	}
	
//应用ID
	private String appId;
	public void setAppId(String appId) {
		this.appId=appId;
	}
	public String getAppId() {
		return this.appId ;
	}
	
//应用密钥
	private String appSecret;
	public void setAppSecret(String appSecret) {
		this.appSecret=appSecret;
	}
	public String getAppSecret() {
		return this.appSecret ;
	}
	
//获取到的凭证(访问凭证)
	private String accessToken2;
	public void setAccessToken2(String accessToken2) {
		this.accessToken2=accessToken2;
	}
	public String getAccessToken2() {
		return this.accessToken2 ;
	}
	
//凭证有效时间，单位：秒
	private String expiresIn2;
	public void setExpiresIn2(String expiresIn2) {
		this.expiresIn2=expiresIn2;
	}
	public String getExpiresIn2() {
		return this.expiresIn2 ;
	}
	
//凭证过期时间
	private String expiresTime2;
	public void setExpiresTime2(String expiresTime2) {
		this.expiresTime2=expiresTime2;
	}
	public String getExpiresTime2() {
		return this.expiresTime2 ;
	}
	
//过期时间数字型
	private String expiresTimeLong;
	public void setExpiresTimeLong(String expiresTimeLong) {
		this.expiresTimeLong=expiresTimeLong;
	}
	public String getExpiresTimeLong() {
		return this.expiresTimeLong ;
	}
	
//创建时间 CREATE_TIME_
	private String createTime;
	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}
	public String getCreateTime() {
		return this.createTime ;
	}
	
//创建时间数字型 CREATE_TIME_LONG_
	private String createTimeLong;
	public void setCreateTimeLong(String createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public String getCreateTimeLong() {
		return this.createTimeLong ;
	}
	
//更新时间
	private String updateTimeLong;
	public void setUpdateTimeLong(String updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public String getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
	
//更新时间
	private String updateTime;
	public void setUpdateTime(String updateTime) {
		this.updateTime=updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime ;
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