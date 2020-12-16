package com.ibm.follow.servlet.cloud.vr_profit_game.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table vr_profit_game 
 * VR_游戏盈亏 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "vr_profit_game")
public class VrProfitGame implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * VR_游戏盈亏主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="VR_PROFIT_GAME_ID_")
	private String vrProfitGameId;
	public void setVrProfitGameId(String vrProfitGameId) {
		this.vrProfitGameId=vrProfitGameId;
	}
	public void setVrProfitGameId(Object vrProfitGameId) {
		if (vrProfitGameId != null) {
			this.vrProfitGameId = vrProfitGameId.toString();
		}
	}
	public String getVrProfitGameId() {
		return this.vrProfitGameId ;
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
	 * 游戏名称
	 */
	@Column(name="GAME_NAME_")
	private String gameName;
	public void setGameName(String gameName) {
		this.gameName=gameName;
	}
	public void setGameName(Object gameName) {
		if (gameName != null) {
			this.gameName = gameName.toString();
		}
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
	public void setGameCode(Object gameCode) {
		if (gameCode != null) {
			this.gameCode = gameCode.toString();
		}
	}
	public String getGameCode() {
		return this.gameCode ;
	}

	/**
	 * 投注数
	 */
	@Column(name="BET_LEN_")
	private Integer betLen;
	public void setBetLen(Integer betLen) {
		this.betLen=betLen;
	}
	public void setBetLen(Object betLen) {
		if (betLen != null) {
			if (betLen instanceof Integer) {
				this.betLen= (Integer) betLen;
			}else if (StringTool.isInteger(betLen.toString())) {
				this.betLen = Integer.parseInt(betLen.toString());
			}
		}
	}
	public Integer getBetLen() {
		return this.betLen ;
	}

	/**
	 * 最大盈利
	 */
	@Column(name="PROFIT_MAX_T_")
	private Long profitMaxT;
	public void setProfitMaxT(Long profitMaxT) {
		this.profitMaxT=profitMaxT;
	}
	public void setProfitMaxT(Object profitMaxT) {
		if (profitMaxT != null) {
			if (profitMaxT instanceof Long) {
				this.profitMaxT= (Long) profitMaxT;
			}else if (StringTool.isInteger(profitMaxT.toString())) {
				this.profitMaxT = Long.parseLong(profitMaxT.toString());
			}
		}
	}
	public Long getProfitMaxT() {
		return this.profitMaxT ;
	}

	/**
	 * 最大亏损
	 */
	@Column(name="LOSS_MAX_T_")
	private Long lossMaxT;
	public void setLossMaxT(Long lossMaxT) {
		this.lossMaxT=lossMaxT;
	}
	public void setLossMaxT(Object lossMaxT) {
		if (lossMaxT != null) {
			if (lossMaxT instanceof Long) {
				this.lossMaxT= (Long) lossMaxT;
			}else if (StringTool.isInteger(lossMaxT.toString())) {
				this.lossMaxT = Long.parseLong(lossMaxT.toString());
			}
		}
	}
	public Long getLossMaxT() {
		return this.lossMaxT ;
	}

	/**
	 * 胜率
	 */
	@Column(name="WIN_RATE_T_")
	private Long winRateT;
	public void setWinRateT(Long winRateT) {
		this.winRateT=winRateT;
	}
	public void setWinRateT(Object winRateT) {
		if (winRateT != null) {
			if (winRateT instanceof Long) {
				this.winRateT= (Long) winRateT;
			}else if (StringTool.isInteger(winRateT.toString())) {
				this.winRateT = Long.parseLong(winRateT.toString());
			}
		}
	}
	public Long getWinRateT() {
		return this.winRateT ;
	}

	/**
	 * 输赢金额
	 */
	@Column(name="PROFIT_T_")
	private Long profitT;
	public void setProfitT(Long profitT) {
		this.profitT=profitT;
	}
	public void setProfitT(Object profitT) {
		if (profitT != null) {
			if (profitT instanceof Long) {
				this.profitT= (Long) profitT;
			}else if (StringTool.isInteger(profitT.toString())) {
				this.profitT = Long.parseLong(profitT.toString());
			}
		}
	}
	public Long getProfitT() {
		return this.profitT ;
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