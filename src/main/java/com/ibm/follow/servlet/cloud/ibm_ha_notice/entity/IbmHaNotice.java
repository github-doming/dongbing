package com.ibm.follow.servlet.cloud.ibm_ha_notice.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_ha_notice 
 * IBM_盘口代理通知的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_ha_notice")
public class IbmHaNotice implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_盘口代理通知主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_HA_NOTICE_ID_")
	private String ibmHaNoticeId;
	public void setIbmHaNoticeId(String ibmHaNoticeId) {
		this.ibmHaNoticeId=ibmHaNoticeId;
	}
	public void setIbmHaNoticeId(Object ibmHaNoticeId) {
		if (ibmHaNoticeId != null) {
			this.ibmHaNoticeId = ibmHaNoticeId.toString();
		}else{
			this.ibmHaNoticeId = null;
		}
	}
	public String getIbmHaNoticeId() {
		return this.ibmHaNoticeId ;
	}

	/**
	 * 盘口代理主键
	 */
	@Column(name="HANDICAP_AGENT_ID_")
	private String handicapAgentId;
	public void setHandicapAgentId(String handicapAgentId) {
		this.handicapAgentId=handicapAgentId;
	}
	public void setHandicapAgentId(Object handicapAgentId) {
		if (handicapAgentId != null) {
			this.handicapAgentId = handicapAgentId.toString();
		}else{
			this.handicapAgentId = null;
		}
	}
	public String getHandicapAgentId() {
		return this.handicapAgentId ;
	}

	/**
	 * 游戏编码
	 */
	@Column(name="GAME_CODE_")
	private String gameCode;
	public void setGameCode(String gameCode) {
		this.gameCode=gameCode;
	}
	public void setGameCode(Object gameCode) {
		if (gameCode != null) {
			this.gameCode = gameCode.toString();
		}else{
			this.gameCode = null;
		}
	}
	public String getGameCode() {
		return this.gameCode ;
	}

	/**
	 * 通知类型
	 */
	@Column(name="NOTICE_TYPE_")
	private String noticeType;
	public void setNoticeType(String noticeType) {
		this.noticeType=noticeType;
	}
	public void setNoticeType(Object noticeType) {
		if (noticeType != null) {
			this.noticeType = noticeType.toString();
		}else{
			this.noticeType = null;
		}
	}
	public String getNoticeType() {
		return this.noticeType ;
	}

	/**
	 * 通知内容
	 */
	@Column(name="NOTICE_CONTENT_")
	private String noticeContent;
	public void setNoticeContent(String noticeContent) {
		this.noticeContent=noticeContent;
	}
	public void setNoticeContent(Object noticeContent) {
		if (noticeContent != null) {
			this.noticeContent = noticeContent.toString();
		}else{
			this.noticeContent = null;
		}
	}
	public String getNoticeContent() {
		return this.noticeContent ;
	}

	/**
	 * 通知状态
	 */
	@Column(name="NOTICE_STATE_")
	private String noticeState;
	public void setNoticeState(String noticeState) {
		this.noticeState=noticeState;
	}
	public void setNoticeState(Object noticeState) {
		if (noticeState != null) {
			this.noticeState = noticeState.toString();
		}else{
			this.noticeState = null;
		}
	}
	public String getNoticeState() {
		return this.noticeState ;
	}

	/**
	 * 创建时间CREATE_TIME_
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="CREATE_TIME_")
	private Date createTime;
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
	}
	public void setCreateTime(Object createTime) {
		if (createTime != null) {
			if (createTime instanceof Date) {
				this.createTime= (Date) createTime;
			}else if (StringTool.isInteger(createTime.toString())) {
				this.createTime = new Date(Long.parseLong(createTime.toString()));
			}else {
				this.createTime = DateTool.getTime(createTime.toString());
			}
		}else{
			this.createTime = null;
		}
	}
	public Date getCreateTime() {
		return this.createTime ;
	}

	/**
	 * 创建时间数字型CREATE_TIME_LONG_
	 */
	@Column(name="CREATE_TIME_LONG_")
	private Long createTimeLong;
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public void setCreateTimeLong(Object createTimeLong) {
		if (createTimeLong != null) {
			if (createTimeLong instanceof Long) {
				this.createTimeLong= (Long) createTimeLong;
			}else if (StringTool.isInteger(createTimeLong.toString())) {
				this.createTimeLong = Long.parseLong(createTimeLong.toString());
			}
		}else{
			this.createTimeLong = null;
		}
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong ;
	}

	/**
	 * 更新时间UPDATE_TIME_
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="UPDATE_TIME_")
	private Date updateTime;
	public void setUpdateTime(Date updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTime(Object updateTime) {
		if (updateTime != null) {
			if (updateTime instanceof Date) {
				this.updateTime= (Date) updateTime;
			}else if (StringTool.isInteger(updateTime.toString())) {
				this.updateTime = new Date(Long.parseLong(updateTime.toString()));
			}else {
				this.updateTime = DateTool.getTime(updateTime.toString());
			}
		}else{
			this.updateTime = null;
		}
	}
	public Date getUpdateTime() {
		return this.updateTime ;
	}

	/**
	 * 更新时间数字型UPDATE_TIME_LONG_
	 */
	@Column(name="UPDATE_TIME_LONG_")
	private Long updateTimeLong;
	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public void setUpdateTimeLong(Object updateTimeLong) {
		if (updateTimeLong != null) {
			if (updateTimeLong instanceof Long) {
				this.updateTimeLong= (Long) updateTimeLong;
			}else if (StringTool.isInteger(updateTimeLong.toString())) {
				this.updateTimeLong = Long.parseLong(updateTimeLong.toString());
			}
		}else{
			this.updateTimeLong = null;
		}
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong ;
	}

	/**
	 * DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	 */
	@Column(name="STATE_")
	private String state;
	public void setState(String state) {
		this.state=state;
	}
	public void setState(Object state) {
		if (state != null) {
			this.state = state.toString();
		}else{
			this.state = null;
		}
	}
	public String getState() {
		return this.state ;
	}

	/**
	 * 描述
	 */
	@Column(name="DESC_")
	private String desc;
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public void setDesc(Object desc) {
		if (desc != null) {
			this.desc = desc.toString();
		}else{
			this.desc = null;
		}
	}
	public String getDesc() {
		return this.desc ;
	}

	private String tableNameMy;
	/**
	 * 不映射
	 * @return 表名
	 */
	@Transient
	public String getTableNameMy() {
		return tableNameMy;
	}

	public void setTableNameMy(String tableNameMy) {
		this.tableNameMy = tableNameMy;
	}

}