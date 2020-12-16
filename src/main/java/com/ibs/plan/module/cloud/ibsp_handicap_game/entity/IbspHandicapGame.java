package com.ibs.plan.module.cloud.ibsp_handicap_game.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsp_handicap_game 
 * IBSP_盘口游戏 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsp_handicap_game")
public class IbspHandicapGame implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSP_盘口游戏主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSP_HANDICAP_GAME_ID_")
	private String ibspHandicapGameId;
	public void setIbspHandicapGameId(String ibspHandicapGameId) {
		this.ibspHandicapGameId=ibspHandicapGameId;
	}
	public void setIbspHandicapGameId(Object ibspHandicapGameId) {
		if (ibspHandicapGameId != null) {
			this.ibspHandicapGameId = ibspHandicapGameId.toString();
		}
	}
	public String getIbspHandicapGameId() {
		return this.ibspHandicapGameId ;
	}

	/**
	 * 盘口主键
	 */
	@Column(name="HANDICAP_ID_")
	private String handicapId;
	public void setHandicapId(String handicapId) {
		this.handicapId=handicapId;
	}
	public void setHandicapId(Object handicapId) {
		if (handicapId != null) {
			this.handicapId = handicapId.toString();
		}
	}
	public String getHandicapId() {
		return this.handicapId ;
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
	 * 盘口游戏名称
	 */
	@Column(name="HANDICAP_GAME_NAME_")
	private String handicapGameName;
	public void setHandicapGameName(String handicapGameName) {
		this.handicapGameName=handicapGameName;
	}
	public void setHandicapGameName(Object handicapGameName) {
		if (handicapGameName != null) {
			this.handicapGameName = handicapGameName.toString();
		}
	}
	public String getHandicapGameName() {
		return this.handicapGameName ;
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
	 * 封盘时间
	 */
	@Column(name="CLOSE_TIME")
	private Integer closeTime;
	public void setCloseTime(Integer closeTime) {
		this.closeTime=closeTime;
	}
	public void setCloseTime(Object closeTime) {
		if (closeTime != null) {
			if (closeTime instanceof Integer) {
				this.closeTime= (Integer) closeTime;
			}else if (StringTool.isInteger(closeTime.toString())) {
				this.closeTime = Integer.parseInt(closeTime.toString());
			}
		}
	}
	public Integer getCloseTime() {
		return this.closeTime ;
	}

	/**
	 * 开奖时间
	 */
	@Column(name="DRAW_TIME_")
	private Integer drawTime;
	public void setDrawTime(Integer drawTime) {
		this.drawTime=drawTime;
	}
	public void setDrawTime(Object drawTime) {
		if (drawTime != null) {
			if (drawTime instanceof Integer) {
				this.drawTime= (Integer) drawTime;
			}else if (StringTool.isInteger(drawTime.toString())) {
				this.drawTime = Integer.parseInt(drawTime.toString());
			}
		}
	}
	public Integer getDrawTime() {
		return this.drawTime ;
	}

	/**
	 * 盘口游戏图标
	 */
	@Column(name="ICON_")
	private String icon;
	public void setIcon(String icon) {
		this.icon=icon;
	}
	public void setIcon(Object icon) {
		if (icon != null) {
			this.icon = icon.toString();
		}
	}
	public String getIcon() {
		return this.icon ;
	}

	/**
	 * 次序
	 */
	@Column(name="SN_")
	private Integer sn;
	public void setSn(Integer sn) {
		this.sn=sn;
	}
	public void setSn(Object sn) {
		if (sn != null) {
			if (sn instanceof Integer) {
				this.sn= (Integer) sn;
			}else if (StringTool.isInteger(sn.toString())) {
				this.sn = Integer.parseInt(sn.toString());
			}
		}
	}
	public Integer getSn() {
		return this.sn ;
	}

	/**
	 * 创建者
	 */
	@Column(name="CREATE_USER_")
	private String createUser;
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public void setCreateUser(Object createUser) {
		if (createUser != null) {
			this.createUser = createUser.toString();
		}
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
	 * 更新者
	 */
	@Column(name="UPDATE_USER_")
	private String updateUser;
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public void setUpdateUser(Object updateUser) {
		if (updateUser != null) {
			this.updateUser = updateUser.toString();
		}
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