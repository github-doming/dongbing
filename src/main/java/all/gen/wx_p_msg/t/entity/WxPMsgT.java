package all.gen.wx_p_msg.t.entity;

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
 * The persistent class for the database table wx_p_msg 
 * 微信公众号信息WX_P_MSG的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "wx_p_msg")
public class WxPMsgT implements Serializable {

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
		
			
//微信公众号信息主键WX_P_MSG_ID_
@Column(name="WX_P_MSG_ID_")
	private String wxPMsgId;
	
	public void setWxPMsgId(String wxPMsgId) {
		this.wxPMsgId=wxPMsgId;
	}
	public String getWxPMsgId() {
		return this.wxPMsgId ;
	}
			
			
//代理应用ID
@Column(name="AGENTID_")
	private String agentid;
	
	public void setAgentid(String agentid) {
		this.agentid=agentid;
	}
	public String getAgentid() {
		return this.agentid ;
	}
			
			
//用户ID
@Column(name="USER_ID_")
	private String userId;
	
	public void setUserId(String userId) {
		this.userId=userId;
	}
	public String getUserId() {
		return this.userId ;
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