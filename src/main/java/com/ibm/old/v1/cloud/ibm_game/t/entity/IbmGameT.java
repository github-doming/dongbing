package com.ibm.old.v1.cloud.ibm_game.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_game 
 * IBM_游戏IBM_GAME的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_game")
public class IbmGameT implements Serializable {

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
	 * IBM_游戏主键IBM_GAME_ID_
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_GAME_ID_")
	private String ibmGameId;
	public void setIbmGameId(String ibmGameId) {
		this.ibmGameId=ibmGameId;
	}
	public String getIbmGameId() {
		return this.ibmGameId ;
	}

	/**
	 * 游戏名称
	 */
	@Column(name="GAME_NAME_")
	private String gameName;
	public void setGameName(String gameName) {
		this.gameName=gameName;
	}
	public String getGameName() {
		return this.gameName ;
	}

	/**
	 * 游戏编码
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
	 * 游戏图标
	 */
	@Column(name="ICON_")
	private String icon;
	public void setIcon(String icon) {
		this.icon=icon;
	}
	public String getIcon() {
		return this.icon ;
	}

	/**
	 * 开奖数据表名
	 */
	@Column(name="REP_GRAB_TABLE_NAME_")
	private String repGrabTableName;
	public void setRepGrabTableName(String repGrabTableName) {
		this.repGrabTableName=repGrabTableName;
	}
	public String getRepGrabTableName() {
		return this.repGrabTableName ;
	}

	/**
	 * 开奖结果表名
	 */
	@Column(name="REP_DRAW_TABLE_NAME_")
	private String repDrawTableName;
	public void setRepDrawTableName(String repDrawTableName) {
		this.repDrawTableName=repDrawTableName;
	}
	public String getRepDrawTableName() {
		return this.repDrawTableName ;
	}

	/**
	 * 次序
	 */
	@Column(name="SN_")
	private Integer sn;
	public void setSn(Integer sn) {
		this.sn=sn;
	}
	public Integer getSn() {
		return this.sn ;
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