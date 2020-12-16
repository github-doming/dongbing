package all.gen.alipay_config.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table alipay_config 
 * vo类
 */

public class AlipayConfigTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//支付宝支付配置主键ALIPAY_CONFIG_ID_
	private String alipayConfigId;
	public void setAlipayConfigId(String alipayConfigId) {
		this.alipayConfigId=alipayConfigId;
	}
	public String getAlipayConfigId() {
		return this.alipayConfigId ;
	}
	
//系统编码CODE_
	private String code;
	public void setCode(String code) {
		this.code=code;
	}
	public String getCode() {
		return this.code ;
	}
	
//应用ID APP_ID_
	private String appId;
	public void setAppId(String appId) {
		this.appId=appId;
	}
	public String getAppId() {
		return this.appId ;
	}
	
//应用私钥APP_PRIVATE_KEY_
	private String appPrivateKey;
	public void setAppPrivateKey(String appPrivateKey) {
		this.appPrivateKey=appPrivateKey;
	}
	public String getAppPrivateKey() {
		return this.appPrivateKey ;
	}
	
//应用公钥APP_PUBLIC_KEY_
	private String appPublicKey;
	public void setAppPublicKey(String appPublicKey) {
		this.appPublicKey=appPublicKey;
	}
	public String getAppPublicKey() {
		return this.appPublicKey ;
	}
	
//第三方支付宝私钥T_PRIVATE_KEY_
	private String tPrivateKey;
	public void setTPrivateKey(String tPrivateKey) {
		this.tPrivateKey=tPrivateKey;
	}
	public String getTPrivateKey() {
		return this.tPrivateKey ;
	}
	
//第三方支付宝公钥T_PUBLIC_KEY_ 
	private String tPublicKey;
	public void setTPublicKey(String tPublicKey) {
		this.tPublicKey=tPublicKey;
	}
	public String getTPublicKey() {
		return this.tPublicKey ;
	}
	
//创建时间
	private String createTime;
	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}
	public String getCreateTime() {
		return this.createTime ;
	}
	
//创建时间
	private String createTimeLong;
	public void setCreateTimeLong(String createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public String getCreateTimeLong() {
		return this.createTimeLong ;
	}
	
//更新时间
	private String updateTime;
	public void setUpdateTime(String updateTime) {
		this.updateTime=updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime ;
	}
	
//更新时间
	private String updateTimeLong;
	public void setUpdateTimeLong(String updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public String getUpdateTimeLong() {
		return this.updateTimeLong ;
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
	
//获取到的凭证(访问凭证)APP_AUTH_TOKEN_
	private String appAuthToken;
	public void setAppAuthToken(String appAuthToken) {
		this.appAuthToken=appAuthToken;
	}
	public String getAppAuthToken() {
		return this.appAuthToken ;
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


}