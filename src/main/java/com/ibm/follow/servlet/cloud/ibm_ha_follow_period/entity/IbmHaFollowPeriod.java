package com.ibm.follow.servlet.cloud.ibm_ha_follow_period.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_ha_follow_period 
 * IBM_盘口代理当期跟投信息
 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_ha_follow_period")
public class IbmHaFollowPeriod implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_盘口代理当期跟投信息主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_HA_FOLLOW_PERIOD_ID_")
	private String ibmHaFollowPeriodId;
	public void setIbmHaFollowPeriodId(String ibmHaFollowPeriodId) {
		this.ibmHaFollowPeriodId=ibmHaFollowPeriodId;
	}
	public void setIbmHaFollowPeriodId(Object ibmHaFollowPeriodId) {
		if (ibmHaFollowPeriodId != null) {
			this.ibmHaFollowPeriodId = ibmHaFollowPeriodId.toString();
		}
	}
	public String getIbmHaFollowPeriodId() {
		return this.ibmHaFollowPeriodId ;
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
		}
	}
	public String getHandicapAgentId() {
		return this.handicapAgentId ;
	}

	/**
	 * 游戏主键
	 */
	@Column(name="GAME_ID_")
	private String gameId;
	public void setGameId(String gameId) {
		this.gameId=gameId;
	}
	public void setGameId(Object gameId) {
		if (gameId != null) {
			this.gameId = gameId.toString();
		}
	}
	public String getGameId() {
		return this.gameId ;
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
	 * 投注数
	 */
	@Column(name="BET_LEN_")
	private Integer betLen;
	public void setBetLen(Integer betLen) {
		this.betLen=betLen;
	}
	public Integer getBetLen() {
		return this.betLen ;
	}

	/**
	 * 投注额
	 */
	@Column(name="BET_FUNDS_T_")
	private Integer betFundsT;
	public void setBetFundsT(Integer betFundsT) {
		this.betFundsT=betFundsT;
	}
	public void setBetFundsT(Object betFundsT) {
		if (betFundsT != null) {
			if (betFundsT instanceof Integer) {
				this.betFundsT= (Integer) betFundsT;
			}else if (StringTool.isInteger(betFundsT.toString())) {
				this.betFundsT = Integer.parseInt(betFundsT.toString());
			}
		}
	}
	public Integer getBetFundsT() {
		return this.betFundsT ;
	}

	/**
	 * 跟投金额
	 */
	@Column(name="FOLLOW_FUND_T_")
	private Integer followFundT;
	public void setFollowFundT(Integer followFundT) {
		this.followFundT=followFundT;
	}
	public void setFollowFundT(Object followFundT) {
		if (followFundT != null) {
			if (followFundT instanceof Integer) {
				this.followFundT= (Integer) followFundT;
			}else if (StringTool.isInteger(followFundT.toString())) {
				this.followFundT = Integer.parseInt(followFundT.toString());
			}
		}
	}
	public Integer getFollowFundT() {
		return this.followFundT ;
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