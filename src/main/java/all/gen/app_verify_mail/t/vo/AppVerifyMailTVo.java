package all.gen.app_verify_mail.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table app_verify_mail 
 * vo类
 */

public class AppVerifyMailTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//APP_VERIFY_MAIL_ID_
	private String appVerifyMailId;
	public void setAppVerifyMailId(String appVerifyMailId) {
		this.appVerifyMailId=appVerifyMailId;
	}
	public String getAppVerifyMailId() {
		return this.appVerifyMailId ;
	}
	
//邮箱MAIL_
	private String mail;
	public void setMail(String mail) {
		this.mail=mail;
	}
	public String getMail() {
		return this.mail ;
	}
	
//验证码
	private String code;
	public void setCode(String code) {
		this.code=code;
	}
	public String getCode() {
		return this.code ;
	}
	
//验证码序号
	private String codeSn;
	public void setCodeSn(String codeSn) {
		this.codeSn=codeSn;
	}
	public String getCodeSn() {
		return this.codeSn ;
	}
	
//类型
	private String type;
	public void setType(String type) {
		this.type=type;
	}
	public String getType() {
		return this.type ;
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


}