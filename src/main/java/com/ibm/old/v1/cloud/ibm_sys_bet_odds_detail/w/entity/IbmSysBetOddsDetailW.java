package com.ibm.old.v1.cloud.ibm_sys_bet_odds_detail.w.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_sys_bet_odds_detail 
 * 的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_sys_bet_odds_detail")
public class IbmSysBetOddsDetailW implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IDX_
	 */
	@Column(name="IDX_")
	private Long idx;
	public void setIdx(Long idx) {
		this.idx=idx;
	}
	public void setIdx(Object idx) {
		if (idx != null) {
			if (idx instanceof Long) {
				this.idx= (Long) idx;
			}else if (StringTool.isInteger(idx.toString())) {
				this.idx = Long.parseLong(idx.toString());
			}
		}else{
			this.idx = null;
		}
	}
	public Long getIdx() {
		return this.idx ;
	}

	/**
	 * IBM_SYS_BET_ODDS_DETAIL_ID_
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_SYS_BET_ODDS_DETAIL_ID_")
	private String ibmSysBetOddsDetailId;
	public void setIbmSysBetOddsDetailId(String ibmSysBetOddsDetailId) {
		this.ibmSysBetOddsDetailId=ibmSysBetOddsDetailId;
	}
	public void setIbmSysBetOddsDetailId(Object ibmSysBetOddsDetailId) {
		if (ibmSysBetOddsDetailId != null) {
			this.ibmSysBetOddsDetailId = ibmSysBetOddsDetailId.toString();
		}else{
			this.ibmSysBetOddsDetailId = null;
		}
	}
	public String getIbmSysBetOddsDetailId() {
		return this.ibmSysBetOddsDetailId ;
	}

	/**
	 * GAME_ID_
	 */
	@Column(name="GAME_ID_")
	private String gameId;
	public void setGameId(String gameId) {
		this.gameId=gameId;
	}
	public void setGameId(Object gameId) {
		if (gameId != null) {
			this.gameId = gameId.toString();
		}else{
			this.gameId = null;
		}
	}
	public String getGameId() {
		return this.gameId ;
	}

	/**
	 * SYS_BET_ODDS_ID_
	 */
	@Column(name="SYS_BET_ODDS_ID_")
	private String sysBetOddsId;
	public void setSysBetOddsId(String sysBetOddsId) {
		this.sysBetOddsId=sysBetOddsId;
	}
	public void setSysBetOddsId(Object sysBetOddsId) {
		if (sysBetOddsId != null) {
			this.sysBetOddsId = sysBetOddsId.toString();
		}else{
			this.sysBetOddsId = null;
		}
	}
	public String getSysBetOddsId() {
		return this.sysBetOddsId ;
	}

	/**
	 * GAME_PLAY_NAME_
	 */
	@Column(name="GAME_PLAY_NAME_")
	private String gamePlayName;
	public void setGamePlayName(String gamePlayName) {
		this.gamePlayName=gamePlayName;
	}
	public void setGamePlayName(Object gamePlayName) {
		if (gamePlayName != null) {
			this.gamePlayName = gamePlayName.toString();
		}else{
			this.gamePlayName = null;
		}
	}
	public String getGamePlayName() {
		return this.gamePlayName ;
	}

	/**
	 * ODDS_
	 */
	@Column(name="ODDS_")
	private String odds;
	public void setOdds(String odds) {
		this.odds=odds;
	}
	public void setOdds(Object odds) {
		if (odds != null) {
			this.odds = odds.toString();
		}else{
			this.odds = null;
		}
	}
	public String getOdds() {
		return this.odds ;
	}

	/**
	 * CREATE_TIME_
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
		}else{
			this.createTime = null;
		}
	}
	public Date getCreateTime() {
		return this.createTime ;
	}

	/**
	 * CREATE_TIME_LONG_
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
	 * UPDATE_TIME_
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
		}else{
			this.updateTime = null;
		}
	}
	public Date getUpdateTime() {
		return this.updateTime ;
	}

	/**
	 * UPDATE_TIME_LONG_
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
	 * STATE_
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
	 * DESC_
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