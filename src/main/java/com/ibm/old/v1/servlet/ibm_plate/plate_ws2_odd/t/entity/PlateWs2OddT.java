package com.ibm.old.v1.servlet.ibm_plate.plate_ws2_odd.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table plate_ws2_odd 
 * WS2挡板赔率的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "plate_ws2_odd")
public class PlateWs2OddT implements Serializable {

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
	@Column(name="PLATE_WS2_ODD_ID_")
	private String plateWs2OddId;
	public void setPlateWs2OddId(String plateWs2OddId) {
		this.plateWs2OddId=plateWs2OddId;
	}
	public String getPlateWs2OddId() {
		return this.plateWs2OddId ;
	}

	/**
	 * 玩法名称
	 */
	@Column(name="GAMEPLAY_NAME_")
	private String gameplayName;
	public void setGameplayName(String gameplayName) {
		this.gameplayName=gameplayName;
	}
	public String getGameplayName() {
		return this.gameplayName ;
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
	 * 创建者CREATE_USER_
	 */
	@Column(name="CREATE_USER_")
	private String createUser;
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public String getCreateUser() {
		return this.createUser ;
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
	 * 更新者UPDATE_USER_
	 */
	@Column(name="UPDATE_USER_")
	private String updateUser;
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public String getUpdateUser() {
		return this.updateUser ;
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