package all.gen.app_verify_mobile.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table app_verify_mobile 
 * vo类
 */

public class AppVerifyMobileTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//APP_VERIFY_MOBILE_ID_
	private String appVerifyMobileId;
	public void setAppVerifyMobileId(String appVerifyMobileId) {
		this.appVerifyMobileId=appVerifyMobileId;
	}
	public String getAppVerifyMobileId() {
		return this.appVerifyMobileId ;
	}
	
//手机号
	private String mobile;
	public void setMobile(String mobile) {
		this.mobile=mobile;
	}
	public String getMobile() {
		return this.mobile ;
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