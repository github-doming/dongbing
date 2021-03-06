package com.ibs.plan.module.client.ibsc_bet_error.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsc_bet_error 
 * IBSC客户端_投注异常 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsc_bet_error")
public class IbscBetError implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSC客户端_投注异常主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSC_BET_ERROR_ID_")
	private String ibscBetErrorId;
	public void setIbscBetErrorId(String ibscBetErrorId) {
		this.ibscBetErrorId=ibscBetErrorId;
	}
	public void setIbscBetErrorId(Object ibscBetErrorId) {
		if (ibscBetErrorId != null) {
			this.ibscBetErrorId = ibscBetErrorId.toString();
		}
	}
	public String getIbscBetErrorId() {
		return this.ibscBetErrorId ;
	}

	/**
	 * 客户端已存在盘口会员主键CLIENT_HM_EXIST_ID_
	 */
	@Column(name="EXIST_HM_ID_")
	private String existHmId;
	public void setExistHmId(String existHmId) {
		this.existHmId=existHmId;
	}
	public void setExistHmId(Object existHmId) {
		if (existHmId != null) {
			this.existHmId = existHmId.toString();
		}
	}
	public String getExistHmId() {
		return this.existHmId ;
	}

	/**
	 * 投注信息主键
	 */
	@Column(name="BET_INFO_ID_")
	private String betInfoId;
	public void setBetInfoId(String betInfoId) {
		this.betInfoId=betInfoId;
	}
	public void setBetInfoId(Object betInfoId) {
		if (betInfoId != null) {
			this.betInfoId = betInfoId.toString();
		}
	}
	public String getBetInfoId() {
		return this.betInfoId ;
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
		}
	}
	public String getGameCode() {
		return this.gameCode ;
	}

	/**
	 * 期数
	 */
	@Column(name="PERIOD_")
	private String period;
	public void setPeriod(String period) {
		this.period=period;
	}
	public void setPeriod(Object period) {
		if (period != null) {
			this.period = period.toString();
		}
	}
	public String getPeriod() {
		return this.period ;
	}

	/**
	 * 投注内容
	 */
	@Column(name="FAIL_BET_CONTENT_")
	private String failBetContent;
	public void setFailBetContent(String failBetContent) {
		this.failBetContent=failBetContent;
	}
	public void setFailBetContent(Object failBetContent) {
		if (failBetContent != null) {
			this.failBetContent = failBetContent.toString();
		}
	}
	public String getFailBetContent() {
		return this.failBetContent ;
	}

	/**
	 * 错误投注信息
	 */
	@Column(name="ERROR_BET_INFO_")
	private String errorBetInfo;
	public void setErrorBetInfo(String errorBetInfo) {
		this.errorBetInfo=errorBetInfo;
	}
	public void setErrorBetInfo(Object errorBetInfo) {
		if (errorBetInfo != null) {
			this.errorBetInfo = errorBetInfo.toString();
		}
	}
	public String getErrorBetInfo() {
		return this.errorBetInfo ;
	}

	/**
	 * 执行状态
	 */
	@Column(name="EXEC_STATE_")
	private String execState;
	public void setExecState(String execState) {
		this.execState=execState;
	}
	public void setExecState(Object execState) {
		if (execState != null) {
			this.execState = execState.toString();
		}
	}
	public String getExecState() {
		return this.execState ;
	}

	/**
	 * 创建时间
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="CREATE_TIME_")
	private Date createTime;
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
	}
	public void setCreateTime(Object createTime) throws ParseException {
		if (createTime != null) {
			if (createTime instanceof Date) {
				this.createTime= (Date) createTime;
			}else if (StringTool.isInteger(createTime.toString())) {
				this.createTime = new Date(Long.parseLong(createTime.toString()));
			}else {
				this.createTime = DateTool.getTime(createTime.toString());
			}
		}
	}
	public Date getCreateTime() {
		return this.createTime ;
	}

	/**
	 * 创建时间
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
		}
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong ;
	}

	/**
	 * 更新时间
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="UPDATE_TIME_")
	private Date updateTime;
	public void setUpdateTime(Date updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTime(Object updateTime) throws ParseException {
		if (updateTime != null) {
			if (updateTime instanceof Date) {
				this.updateTime= (Date) updateTime;
			}else if (StringTool.isInteger(updateTime.toString())) {
				this.updateTime = new Date(Long.parseLong(updateTime.toString()));
			}else {
				this.updateTime = DateTool.getTime(updateTime.toString());
			}
		}
	}
	public Date getUpdateTime() {
		return this.updateTime ;
	}

	/**
	 * 更新时间
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