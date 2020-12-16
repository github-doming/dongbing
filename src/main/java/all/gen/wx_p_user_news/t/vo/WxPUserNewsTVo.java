package all.gen.wx_p_user_news.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table wx_p_user_news 
 * vo类
 */

public class WxPUserNewsTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//微信公众号图文与文章主键
	private String wxPUserNewsId;
	public void setWxPUserNewsId(String wxPUserNewsId) {
		this.wxPUserNewsId=wxPUserNewsId;
	}
	public String getWxPUserNewsId() {
		return this.wxPUserNewsId ;
	}
	
//图文名称
	private String name;
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
	
//填写图文消息的接收者
	private String touser;
	public void setTouser(String touser) {
		this.touser=touser;
	}
	public String getTouser() {
		return this.touser ;
	}
	
//图文ID
	private String newsId;
	public void setNewsId(String newsId) {
		this.newsId=newsId;
	}
	public String getNewsId() {
		return this.newsId ;
	}
	
//消息类型MSG_TYPE_
	private String msgType;
	public void setMsgType(String msgType) {
		this.msgType=msgType;
	}
	public String getMsgType() {
		return this.msgType ;
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