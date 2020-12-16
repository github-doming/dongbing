package com.ibm.follow.servlet.cloud.vr_fm_game_set.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table vr_fm_game_set 
 * VR_跟投会员游戏设置 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "vr_fm_game_set")
public class VrFmGameSet implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * VR_跟投会员游戏设置主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="VR_FM_GAME_SET_ID_")
	private String vrFmGameSetId;
	public void setVrFmGameSetId(String vrFmGameSetId) {
		this.vrFmGameSetId=vrFmGameSetId;
	}
	public void setVrFmGameSetId(Object vrFmGameSetId) {
		if (vrFmGameSetId != null) {
			this.vrFmGameSetId = vrFmGameSetId.toString();
		}
	}
	public String getVrFmGameSetId() {
		return this.vrFmGameSetId ;
	}

	/**
	 * 用户主键
	 */
	@Column(name="USER_ID_")
	private String userId;
	public void setUserId(String userId) {
		this.userId=userId;
	}
	public void setUserId(Object userId) {
		if (userId != null) {
			this.userId = userId.toString();
		}
	}
	public String getUserId() {
		return this.userId ;
	}

	/**
	 * 虚拟会员主键
	 */
	@Column(name="VR_MEMBER_ID_")
	private String vrMemberId;
	public void setVrMemberId(String vrMemberId) {
		this.vrMemberId=vrMemberId;
	}
	public void setVrMemberId(Object vrMemberId) {
		if (vrMemberId != null) {
			this.vrMemberId = vrMemberId.toString();
		}
	}
	public String getVrMemberId() {
		return this.vrMemberId ;
	}

	/**
	 * 盘口编码
	 */
	@Column(name="HANDICAP_CODE_")
	private String handicapCode;
	public void setHandicapCode(String handicapCode) {
		this.handicapCode=handicapCode;
	}
	public void setHandicapCode(Object handicapCode) {
		if (handicapCode != null) {
			this.handicapCode = handicapCode.toString();
		}
	}
	public String getHandicapCode() {
		return this.handicapCode ;
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