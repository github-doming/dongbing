package all.gen.wx_p_mt_user_news.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table wx_p_mt_user_news 
 * vo类
 */

public class WxPMtUserNewsTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//微信公众号下行用户消息主键
	private String wxPMtUserNewsId;
	public void setWxPMtUserNewsId(String wxPMtUserNewsId) {
		this.wxPMtUserNewsId=wxPMtUserNewsId;
	}
	public String getWxPMtUserNewsId() {
		return this.wxPMtUserNewsId ;
	}
	
//用于群发的??文消息的media_id
	private String mediaId;
	public void setMediaId(String mediaId) {
		this.mediaId=mediaId;
	}
	public String getMediaId() {
		return this.mediaId ;
	}
	
//群发的消息类型
	private String userNewsId;
	public void setUserNewsId(String userNewsId) {
		this.userNewsId=userNewsId;
	}
	public String getUserNewsId() {
		return this.userNewsId ;
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