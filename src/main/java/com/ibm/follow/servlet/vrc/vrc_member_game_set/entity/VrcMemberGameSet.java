package com.ibm.follow.servlet.vrc.vrc_member_game_set.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table vrc_member_game_set 
 * VRC客户端_会员游戏设置 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "vrc_member_game_set")
public class VrcMemberGameSet implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * VRC客户端_会员游戏设置主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="VRC_MEMBER_GAME_SET_ID_")
	private String vrcMemberGameSetId;
	public void setVrcMemberGameSetId(String vrcMemberGameSetId) {
		this.vrcMemberGameSetId=vrcMemberGameSetId;
	}
	public void setVrcMemberGameSetId(Object vrcMemberGameSetId) {
		if (vrcMemberGameSetId != null) {
			this.vrcMemberGameSetId = vrcMemberGameSetId.toString();
		}
	}
	public String getVrcMemberGameSetId() {
		return this.vrcMemberGameSetId ;
	}

	/**
	 * 虚拟盘口会员主键
	 */
	@Column(name="EXIST_MEMBER_VR_ID_")
	private String existMemberVrId;
	public void setExistMemberVrId(String existMemberVrId) {
		this.existMemberVrId=existMemberVrId;
	}
	public void setExistMemberVrId(Object existMemberVrId) {
		if (existMemberVrId != null) {
			this.existMemberVrId = existMemberVrId.toString();
		}
	}
	public String getExistMemberVrId() {
		return this.existMemberVrId ;
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
	 * 游戏开奖类型
	 */
	@Column(name="GAME_DRAW_TYPE_")
	private String gameDrawType;
	public void setGameDrawType(String gameDrawType) {
		this.gameDrawType=gameDrawType;
	}
	public void setGameDrawType(Object gameDrawType) {
		if (gameDrawType != null) {
			this.gameDrawType = gameDrawType.toString();
		}
	}
	public String getGameDrawType() {
		return this.gameDrawType ;
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