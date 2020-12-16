package all.gen.wx_p_mo_text.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table wx_p_mo_text 
 * vo类
 */

public class WxPMoTextTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//微信上行文本主键WX_P_MO_TEXT_ID_
	private String wxPMoTextId;
	public void setWxPMoTextId(String wxPMoTextId) {
		this.wxPMoTextId=wxPMoTextId;
	}
	public String getWxPMoTextId() {
		return this.wxPMoTextId ;
	}
	
//接收?????户openID
	private String toUserNaem;
	public void setToUserNaem(String toUserNaem) {
		this.toUserNaem=toUserNaem;
	}
	public String getToUserNaem() {
		return this.toUserNaem ;
	}
	
//发送的用???openID
	private String fromUserName;
	public void setFromUserName(String fromUserName) {
		this.fromUserName=fromUserName;
	}
	public String getFromUserName() {
		return this.fromUserName ;
	}
	
//消息类型
	private String msgType;
	public void setMsgType(String msgType) {
		this.msgType=msgType;
	}
	public String getMsgType() {
		return this.msgType ;
	}
	
//内容
	private String content;
	public void setContent(String content) {
		this.content=content;
	}
	public String getContent() {
		return this.content ;
	}
	
//消息id
	private String msgid;
	public void setMsgid(String msgid) {
		this.msgid=msgid;
	}
	public String getMsgid() {
		return this.msgid ;
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