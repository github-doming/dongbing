package com.ibm.follow.servlet.cloud.ibm_sys_bet_odds.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_sys_bet_odds 
 *  实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_sys_bet_odds")
public class IbmSysBetOdds implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 模拟投注赔率明细表主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_SYS_BET_ODDS_ID_")
	private String ibmSysBetOddsId;
	public void setIbmSysBetOddsId(String ibmSysBetOddsId) {
		this.ibmSysBetOddsId=ibmSysBetOddsId;
	}
	public void setIbmSysBetOddsId(Object ibmSysBetOddsId) {
		if (ibmSysBetOddsId != null) {
			this.ibmSysBetOddsId = ibmSysBetOddsId.toString();
		}
	}
	public String getIbmSysBetOddsId() {
		return this.ibmSysBetOddsId ;
	}

	/**
	 * 模拟投注赔率表主键
	 */
	@Column(name="SYS_BET_ODDS_TREE_ID_")
	private String sysBetOddsTreeId;
	public void setSysBetOddsTreeId(String sysBetOddsTreeId) {
		this.sysBetOddsTreeId=sysBetOddsTreeId;
	}
	public void setSysBetOddsTreeId(Object sysBetOddsTreeId) {
		if (sysBetOddsTreeId != null) {
			this.sysBetOddsTreeId = sysBetOddsTreeId.toString();
		}
	}
	public String getSysBetOddsTreeId() {
		return this.sysBetOddsTreeId ;
	}

	/**
	 * 游戏ID
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
	 * 游戏玩法名称
	 */
	@Column(name="GAME_PLAY_NAME_")
	private String gamePlayName;
	public void setGamePlayName(String gamePlayName) {
		this.gamePlayName=gamePlayName;
	}
	public void setGamePlayName(Object gamePlayName) {
		if (gamePlayName != null) {
			this.gamePlayName = gamePlayName.toString();
		}
	}
	public String getGamePlayName() {
		return this.gamePlayName ;
	}

	/**
	 * 赔率
	 */
	@Column(name="ODDS_T_")
	private Integer oddsT;
	public void setOddsT(Integer oddsT) {
		this.oddsT=oddsT;
	}
	public void setOddsT(Object oddsT) {
		if (oddsT != null) {
			if (oddsT instanceof Integer) {
				this.oddsT= (Integer) oddsT;
			}else if (StringTool.isInteger(oddsT.toString())) {
				this.oddsT = Integer.parseInt(oddsT.toString());
			}
		}
	}
	public Integer getOddsT() {
		return this.oddsT ;
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
	public void setCreateTime(Object createTime) {
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
	 * 创建时间数字型
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
	public void setUpdateTime(Object updateTime) {
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
	 * 更新时间数字型
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
	 * 状态
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