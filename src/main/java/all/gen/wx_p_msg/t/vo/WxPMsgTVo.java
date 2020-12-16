package all.gen.wx_p_msg.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table wx_p_msg 
 * vo类
 */

public class WxPMsgTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//微信公众号信息主键WX_P_MSG_ID_
	private String wxPMsgId;
	public void setWxPMsgId(String wxPMsgId) {
		this.wxPMsgId=wxPMsgId;
	}
	public String getWxPMsgId() {
		return this.wxPMsgId ;
	}
	
//代理应用ID
	private String agentid;
	public void setAgentid(String agentid) {
		this.agentid=agentid;
	}
	public String getAgentid() {
		return this.agentid ;
	}
	
//用户ID
	private String userId;
	public void setUserId(String userId) {
		this.userId=userId;
	}
	public String getUserId() {
		return this.userId ;
	}
	
//内容
	private String content;
	public void setContent(String content) {
		this.content=content;
	}
	public String getContent() {
		return this.content ;
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