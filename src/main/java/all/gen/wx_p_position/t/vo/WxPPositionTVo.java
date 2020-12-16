package all.gen.wx_p_position.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table wx_p_position 
 * vo类
 */

public class WxPPositionTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//微信公众号地理位置主键WX_P_POSITION_ID_
	private String wxPPositionId;
	public void setWxPPositionId(String wxPPositionId) {
		this.wxPPositionId=wxPPositionId;
	}
	public String getWxPPositionId() {
		return this.wxPPositionId ;
	}
	
//APP用户主键APP_USER_ID_
	private String appUserId;
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public String getAppUserId() {
		return this.appUserId ;
	}
	
//姓名
	private String fullname;
	public void setFullname(String fullname) {
		this.fullname=fullname;
	}
	public String getFullname() {
		return this.fullname ;
	}
	
//微信号ID
	private String weixinId;
	public void setWeixinId(String weixinId) {
		this.weixinId=weixinId;
	}
	public String getWeixinId() {
		return this.weixinId ;
	}
	
//OPEN_ID_
	private String openId;
	public void setOpenId(String openId) {
		this.openId=openId;
	}
	public String getOpenId() {
		return this.openId ;
	}
	
//LATITUDE_
	private String latitude;
	public void setLatitude(String latitude) {
		this.latitude=latitude;
	}
	public String getLatitude() {
		return this.latitude ;
	}
	
//LONGITUDE_
	private String longitude;
	public void setLongitude(String longitude) {
		this.longitude=longitude;
	}
	public String getLongitude() {
		return this.longitude ;
	}
	
//SYS_PRECISION_
	private String sysPrecision;
	public void setSysPrecision(String sysPrecision) {
		this.sysPrecision=sysPrecision;
	}
	public String getSysPrecision() {
		return this.sysPrecision ;
	}
	
//微信用户昵称
	private String nickName;
	public void setNickName(String nickName) {
		this.nickName=nickName;
	}
	public String getNickName() {
		return this.nickName ;
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