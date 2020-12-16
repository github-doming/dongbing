package all.gen.wx_p_config.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table wx_p_config 
 * vo类
 */

public class WxPConfigTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//微信公众号配置WX_P_CONFIG_ID_
	private String wxPConfigId;
	public void setWxPConfigId(String wxPConfigId) {
		this.wxPConfigId=wxPConfigId;
	}
	public String getWxPConfigId() {
		return this.wxPConfigId ;
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
	private String appToken;
	public void setAppToken(String appToken) {
		this.appToken=appToken;
	}
	public String getAppToken() {
		return this.appToken ;
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