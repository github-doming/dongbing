package all.gen.wx_p_mo_text.t.entity;

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
 * The persistent class for the database table wx_p_mo_text 
 * 微信公众号上行文本WX_P_MO_TEXT的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "wx_p_mo_text")
public class WxPMoTextT implements Serializable {

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
		
			
//微信上行文本主键WX_P_MO_TEXT_ID_
@Column(name="WX_P_MO_TEXT_ID_")
	private String wxPMoTextId;
	
	public void setWxPMoTextId(String wxPMoTextId) {
		this.wxPMoTextId=wxPMoTextId;
	}
	public String getWxPMoTextId() {
		return this.wxPMoTextId ;
	}
			
			
//接收?????户openID
@Column(name="TO_USER_NAEM__")
	private String toUserNaem;
	
	public void setToUserNaem(String toUserNaem) {
		this.toUserNaem=toUserNaem;
	}
	public String getToUserNaem() {
		return this.toUserNaem ;
	}
			
			
//发送的用???openID
@Column(name="FROM_USER_NAME_")
	private String fromUserName;
	
	public void setFromUserName(String fromUserName) {
		this.fromUserName=fromUserName;
	}
	public String getFromUserName() {
		return this.fromUserName ;
	}
			
			
//消息类型
@Column(name="MSG_TYPE_")
	private String msgType;
	
	public void setMsgType(String msgType) {
		this.msgType=msgType;
	}
	public String getMsgType() {
		return this.msgType ;
	}
			
			
//内容
@Column(name="CONTENT_")
	private String content;
	
	public void setContent(String content) {
		this.content=content;
	}
	public String getContent() {
		return this.content ;
	}
			
			
//消息id
@Column(name="MSGID_")
	private String msgid;
	
	public void setMsgid(String msgid) {
		this.msgid=msgid;
	}
	public String getMsgid() {
		return this.msgid ;
	}
			
			
//APP用户主键APP_USER_ID_
@Column(name="APP_USER_ID_")
	private String appUserId;
	
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public String getAppUserId() {
		return this.appUserId ;
	}
			
			
//姓名
@Column(name="FULLNAME_")
	private String fullname;
	
	public void setFullname(String fullname) {
		this.fullname=fullname;
	}
	public String getFullname() {
		return this.fullname ;
	}
			
			
//微信号ID
@Column(name="WEIXIN_ID_")
	private String weixinId;
	
	public void setWeixinId(String weixinId) {
		this.weixinId=weixinId;
	}
	public String getWeixinId() {
		return this.weixinId ;
	}
			
			
//微信用户昵称
@Column(name="NICK_NAME_")
	private String nickName;
	
	public void setNickName(String nickName) {
		this.nickName=nickName;
	}
	public String getNickName() {
		return this.nickName ;
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