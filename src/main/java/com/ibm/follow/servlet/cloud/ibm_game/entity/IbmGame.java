package com.ibm.follow.servlet.cloud.ibm_game.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_game
 * IBM_游戏 实体类
 *
 * @author Robot
 */
@AnnotationEntity @AnnotationTable(name = "ibm_game") public class IbmGame implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_游戏主键
	 * 根据底层数据库
	 */
	@Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "IBM_GAME_ID_") private String ibmGameId;
	public void setIbmGameId(String ibmGameId) {
		this.ibmGameId = ibmGameId;
	}
	public void setIbmGameId(Object ibmGameId) {
		if (ibmGameId != null) {
			this.ibmGameId = ibmGameId.toString();
		}
	}
	public String getIbmGameId() {
		return this.ibmGameId;
	}

	/**
	 * 游戏名称
	 */
	@Column(name = "GAME_NAME_") private String gameName;
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public void setGameName(Object gameName) {
		if (gameName != null) {
			this.gameName = gameName.toString();
		}
	}
	public String getGameName() {
		return this.gameName;
	}

	/**
	 * 游戏编码
	 */
	@Column(name = "GAME_CODE_") private String gameCode;
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
	public void setGameCode(Object gameCode) {
		if (gameCode != null) {
			this.gameCode = gameCode.toString();
		}
	}
	public String getGameCode() {
		return this.gameCode;
	}

	/**
	 * 游戏图标
	 */
	@Column(name = "ICON_") private String icon;
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public void setIcon(Object icon) {
		if (icon != null) {
			this.icon = icon.toString();
		}
	}
	public String getIcon() {
		return this.icon;
	}

	/**
	 * 开奖数据表名
	 */
	@Column(name = "REP_GRAB_TABLE_NAME_") private String repGrabTableName;
	public void setRepGrabTableName(String repGrabTableName) {
		this.repGrabTableName = repGrabTableName;
	}
	public void setRepGrabTableName(Object repGrabTableName) {
		if (repGrabTableName != null) {
			this.repGrabTableName = repGrabTableName.toString();
		}
	}
	public String getRepGrabTableName() {
		return this.repGrabTableName;
	}

	/**
	 * 开奖结果表名
	 */
	@Column(name = "REP_DRAW_TABLE_NAME_") private String repDrawTableName;
	public void setRepDrawTableName(String repDrawTableName) {
		this.repDrawTableName = repDrawTableName;
	}
	public void setRepDrawTableName(Object repDrawTableName) {
		if (repDrawTableName != null) {
			this.repDrawTableName = repDrawTableName.toString();
		}
	}
	public String getRepDrawTableName() {
		return this.repDrawTableName;
	}

	/**
	 * 开奖时间
	 */
	@Column(name = "DRAW_TIME_") private Integer drawTime;
	public void setDrawTime(Integer drawTime) {
		this.drawTime = drawTime;
	}
	public Integer getDrawTime() {
		return this.drawTime;
	}

	/**
	 * 游戏价值
	 */
	@Column(name="GAME_WORTH_T_")
	private Integer gameWorthT;
	public void setGameWorthT(Integer gameWorthT) {
		this.gameWorthT=gameWorthT;
	}
	public void setGameWorthT(Object gameWorthT) {
		if (gameWorthT != null) {
			if (gameWorthT instanceof Integer) {
				this.gameWorthT= (Integer) gameWorthT;
			}else if (StringTool.isInteger(gameWorthT.toString())) {
				this.gameWorthT = Integer.parseInt(gameWorthT.toString());
			}
		}else{
			this.gameWorthT = null;
		}
	}
	public Integer getGameWorthT() {
		return this.gameWorthT ;
	}

	public double getGameWorth() {
		return NumberTool.doubleT(this.gameWorthT);
	}

	/**
	 * 次序
	 */
	@Column(name = "SN_") private Integer sn;
	public void setSn(Integer sn) {
		this.sn = sn;
	}
	public Integer getSn() {
		return this.sn;
	}

	/**
	 * 创建者CREATE_USER_
	 */
	@Column(name = "CREATE_USER_") private String createUser;
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public void setCreateUser(Object createUser) {
		if (createUser != null) {
			this.createUser = createUser.toString();
		}
	}
	public String getCreateUser() {
		return this.createUser;
	}

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP) @Column(name = "CREATE_TIME_") private Date createTime;
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setCreateTime(Object createTime) throws ParseException {
		if (createTime != null) {
			if (createTime instanceof Date) {
				this.createTime = (Date) createTime;
			} else if (StringTool.isInteger(createTime.toString())) {
				this.createTime = new Date(Long.parseLong(createTime.toString()));
			} else {
				this.createTime = DateTool.getTime(createTime.toString());
			}
		}
	}
	public Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME_LONG_") private Long createTimeLong;
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong = createTimeLong;
	}
	public void setCreateTimeLong(Object createTimeLong) {
		if (createTimeLong != null) {
			if (createTimeLong instanceof Long) {
				this.createTimeLong = (Long) createTimeLong;
			} else if (StringTool.isInteger(createTimeLong.toString())) {
				this.createTimeLong = Long.parseLong(createTimeLong.toString());
			}
		}
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong;
	}

	/**
	 * 更新者UPDATE_USER_
	 */
	@Column(name = "UPDATE_USER_") private String updateUser;
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public void setUpdateUser(Object updateUser) {
		if (updateUser != null) {
			this.updateUser = updateUser.toString();
		}
	}
	public String getUpdateUser() {
		return this.updateUser;
	}

	/**
	 * 更新时间
	 */
	@Temporal(TemporalType.TIMESTAMP) @Column(name = "UPDATE_TIME_") private Date updateTime;
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setUpdateTime(Object updateTime) throws ParseException {
		if (updateTime != null) {
			if (updateTime instanceof Date) {
				this.updateTime = (Date) updateTime;
			} else if (StringTool.isInteger(updateTime.toString())) {
				this.updateTime = new Date(Long.parseLong(updateTime.toString()));
			} else {
				this.updateTime = DateTool.getTime(updateTime.toString());
			}
		}
	}
	public Date getUpdateTime() {
		return this.updateTime;
	}

	/**
	 * 更新时间
	 */
	@Column(name = "UPDATE_TIME_LONG_") private Long updateTimeLong;
	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong = updateTimeLong;
	}
	public void setUpdateTimeLong(Object updateTimeLong) {
		if (updateTimeLong != null) {
			if (updateTimeLong instanceof Long) {
				this.updateTimeLong = (Long) updateTimeLong;
			} else if (StringTool.isInteger(updateTimeLong.toString())) {
				this.updateTimeLong = Long.parseLong(updateTimeLong.toString());
			}
		}
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong;
	}

	/**
	 * DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	 */
	@Column(name = "STATE_") private String state;
	public void setState(String state) {
		this.state = state;
	}
	public void setState(Object state) {
		if (state != null) {
			this.state = state.toString();
		}
	}
	public String getState() {
		return this.state;
	}

	/**
	 * 描述
	 */
	@Column(name = "DESC_") private String desc;
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public void setDesc(Object desc) {
		if (desc != null) {
			this.desc = desc.toString();
		}
	}
	public String getDesc() {
		return this.desc;
	}

	private String tableNameMy;
	/**
	 * 不映射
	 *
	 * @return 表名
	 */
	@Transient public String getTableNameMy() {
		return tableNameMy;
	}

	public void setTableNameMy(String tableNameMy) {
		this.tableNameMy = tableNameMy;
	}

}