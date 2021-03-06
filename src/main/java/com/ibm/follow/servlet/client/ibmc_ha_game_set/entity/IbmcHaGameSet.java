package com.ibm.follow.servlet.client.ibmc_ha_game_set.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibmc_ha_game_set 
 * IBMC_客户端代理游戏设置IBMC_HA_GAME_SET的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibmc_ha_game_set")
public class IbmcHaGameSet implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBMC_客户端代理游戏设置主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBMC_HA_GAME_SET_ID_")
	private String ibmcHaGameSetId;
	public void setIbmcHaGameSetId(String ibmcHaGameSetId) {
		this.ibmcHaGameSetId=ibmcHaGameSetId;
	}
	public void setIbmcHaGameSetId(Object ibmcHaGameSetId) {
		if (ibmcHaGameSetId != null) {
			this.ibmcHaGameSetId = ibmcHaGameSetId.toString();
		}else{
			this.ibmcHaGameSetId = null;
		}
	}
	public String getIbmcHaGameSetId() {
		return this.ibmcHaGameSetId ;
	}

	/**
	 * 已存在盘口代理主键
	 */
	@Column(name="EXIST_HA_ID_")
	private String existHaId;
	public void setExistHaId(String existHaId) {
		this.existHaId=existHaId;
	}
	public void setExistHaId(Object existHaId) {
		if (existHaId != null) {
			this.existHaId = existHaId.toString();
		}else{
			this.existHaId = null;
		}
	}
	public String getExistHaId() {
		return this.existHaId ;
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
	 * 投注状态
	 */
	@Column(name="BET_STATE_")
	private String betState;
	public void setBetState(String betState) {
		this.betState=betState;
	}
	public void setBetState(Object betState) {
		if (betState != null) {
			this.betState = betState.toString();
		}else{
			this.betState = null;
		}
	}
	public String getBetState() {
		return this.betState ;
	}

	/**
	 * 跟投倍数
	 */
	@Column(name="BET_FOLLOW_MULTIPLE_T_")
	private Integer betFollowMultipleT;
	public void setBetFollowMultipleT(Integer betFollowMultipleT) {
		this.betFollowMultipleT=betFollowMultipleT;
	}
	public void setBetFollowMultipleT(Object betFollowMultipleT) {
		if (betFollowMultipleT != null) {
			if (betFollowMultipleT instanceof Integer) {
				this.betFollowMultipleT= (Integer) betFollowMultipleT;
			}else if (StringTool.isInteger(betFollowMultipleT.toString())) {
				this.betFollowMultipleT = Integer.parseInt(betFollowMultipleT.toString());
			}
		}else{
			this.betFollowMultipleT = null;
		}
	}
	public Integer getBetFollowMultipleT() {
		return this.betFollowMultipleT ;
	}

	/**
	 * 最低金额
	 */
	@Column(name="BET_LEAST_AMOUNT_T_")
	private Integer betLeastAmountT;
	public void setBetLeastAmountT(Integer betLeastAmountT) {
		this.betLeastAmountT=betLeastAmountT;
	}
	public void setBetLeastAmountT(Object betLeastAmountT) {
		if (betLeastAmountT != null) {
			if (betLeastAmountT instanceof Integer) {
				this.betLeastAmountT= (Integer) betLeastAmountT;
			}else if (StringTool.isInteger(betLeastAmountT.toString())) {
				this.betLeastAmountT = Integer.parseInt(betLeastAmountT.toString());
			}
		}else{
			this.betLeastAmountT = null;
		}
	}
	public Integer getBetLeastAmountT() {
		return this.betLeastAmountT ;
	}

	/**
	 * 最高金额
	 */
	@Column(name="BET_MOST_AMOUNT_T_")
	private Integer betMostAmountT;
	public void setBetMostAmountT(Integer betMostAmountT) {
		this.betMostAmountT=betMostAmountT;
	}
	public void setBetMostAmountT(Object betMostAmountT) {
		if (betMostAmountT != null) {
			if (betMostAmountT instanceof Integer) {
				this.betMostAmountT= (Integer) betMostAmountT;
			}else if (StringTool.isInteger(betMostAmountT.toString())) {
				this.betMostAmountT = Integer.parseInt(betMostAmountT.toString());
			}
		}else{
			this.betMostAmountT = null;
		}
	}
	public Integer getBetMostAmountT() {
		return this.betMostAmountT ;
	}

	/**
	 * 过滤数字
	 */
	@Column(name="BET_FILTER_NUMBER_")
	private String betFilterNumber;
	public void setBetFilterNumber(String betFilterNumber) {
		this.betFilterNumber=betFilterNumber;
	}
	public void setBetFilterNumber(Object betFilterNumber) {
		if (betFilterNumber != null) {
			this.betFilterNumber = betFilterNumber.toString();
		}else{
			this.betFilterNumber = null;
		}
	}
	public String getBetFilterNumber() {
		return this.betFilterNumber ;
	}

	/**
	 * 过滤双面
	 */
	@Column(name="BET_FILTER_TWO_SIDE_")
	private String betFilterTwoSide;
	public void setBetFilterTwoSide(String betFilterTwoSide) {
		this.betFilterTwoSide=betFilterTwoSide;
	}
	public void setBetFilterTwoSide(Object betFilterTwoSide) {
		if (betFilterTwoSide != null) {
			this.betFilterTwoSide = betFilterTwoSide.toString();
		}else{
			this.betFilterTwoSide = null;
		}
	}
	public String getBetFilterTwoSide() {
		return this.betFilterTwoSide ;
	}

	/**
	 * 数字反投
	 */
	@Column(name="NUMBER_OPPOSING_")
	private String numberOpposing;
	public void setNumberOpposing(String numberOpposing) {
		this.numberOpposing=numberOpposing;
	}
	public void setNumberOpposing(Object numberOpposing) {
		if (numberOpposing != null) {
			this.numberOpposing = numberOpposing.toString();
		}else{
			this.numberOpposing = null;
		}
	}
	public String getNumberOpposing() {
		return this.numberOpposing ;
	}

	/**
	 * 双面反投
	 */
	@Column(name="TWO_SIDE_OPPOSING_")
	private String twoSideOpposing;
	public void setTwoSideOpposing(String twoSideOpposing) {
		this.twoSideOpposing=twoSideOpposing;
	}
	public void setTwoSideOpposing(Object twoSideOpposing) {
		if (twoSideOpposing != null) {
			this.twoSideOpposing = twoSideOpposing.toString();
		}else{
			this.twoSideOpposing = null;
		}
	}
	public String getTwoSideOpposing() {
		return this.twoSideOpposing ;
	}

	/**
	 * 过滤项目
	 */
	@Column(name="FILTER_PROJECT_")
	private String filterProject;
	public void setFilterProject(String filterProject) {
		this.filterProject=filterProject;
	}
	public void setFilterProject(Object filterProject) {
		if (filterProject != null) {
			this.filterProject = filterProject.toString();
		}else{
			this.filterProject = null;
		}
	}
	public String getFilterProject() {
		return this.filterProject ;
	}

	/**
	 * 扩展设置
	 */
	@Column(name="EXTENSION_SET_")
	private String extensionSet;
	public void setExtensionSet(String extensionSet) {
		this.extensionSet=extensionSet;
	}
	public void setExtensionSet(Object extensionSet) {
		if (extensionSet != null) {
			this.extensionSet = extensionSet.toString();
		}else{
			this.extensionSet = null;
		}
	}
	public String getExtensionSet() {
		return this.extensionSet ;
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