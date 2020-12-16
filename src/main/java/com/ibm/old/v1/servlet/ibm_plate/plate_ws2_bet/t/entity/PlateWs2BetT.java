package com.ibm.old.v1.servlet.ibm_plate.plate_ws2_bet.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table plate_ws2_bet 
 * WS2挡板投注的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "plate_ws2_bet")
public class PlateWs2BetT implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 索引
	 */
	@Column(name="IDX_")
	private Long idx;
	public void setIdx(Long idx) {
		this.idx=idx;
	}
	public Long getIdx() {
		return this.idx ;
	}

	/**
	 * WS2挡板赔率主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PLATE_WS2_BET_ID_")
	private String plateWs2BetId;
	public void setPlateWs2BetId(String plateWs2BetId) {
		this.plateWs2BetId=plateWs2BetId;
	}
	public String getPlateWs2BetId() {
		return this.plateWs2BetId ;
	}

	/**
	 * 玩法CODE
	 */
	@Column(name="GAMEPLAY_CODE_")
	private String gameplayCode;
	public void setGameplayCode(String gameplayCode) {
		this.gameplayCode=gameplayCode;
	}
	public String getGameplayCode() {
		return this.gameplayCode ;
	}

	/**
	 * 游戏CODE
	 */
	@Column(name="GAME_CODE_")
	private String gameCode;
	public void setGameCode(String gameCode) {
		this.gameCode=gameCode;
	}
	public String getGameCode() {
		return this.gameCode ;
	}

	/**
	 * 赔率
	 */
	@Column(name="ODDS_T_")
	private Integer oddsT;
	public void setOddsT(Integer oddsT) {
		this.oddsT=oddsT;
	}
	public Integer getOddsT() {
		return this.oddsT ;
	}

	/**
	 * 金额
	 */
	@Column(name="AMOUT_T_")
	private Long amoutT;
	public void setAmoutT(Long amoutT) {
		this.amoutT=amoutT;
	}
	public Long getAmoutT() {
		return this.amoutT ;
	}

	/**
	 * 玩法分类
	 */
	@Column(name="GAMEPLAY_TYPE_")
	private String gameplayType;
	public void setGameplayType(String gameplayType) {
		this.gameplayType=gameplayType;
	}
	public String getGameplayType() {
		return this.gameplayType ;
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