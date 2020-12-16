package com.ibm.follow.servlet.client.ibmc_hm_game_set.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibmc_hm_game_set 
 * IBMC客户端_盘口会员游戏设置的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibmc_hm_game_set")
public class IbmcHmGameSet implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBMC客户端_盘口会员游戏设置主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBMC_HM_GAME_SET_ID_")
	private String ibmcHmGameSetId;
	public void setIbmcHmGameSetId(String ibmcHmGameSetId) {
		this.ibmcHmGameSetId=ibmcHmGameSetId;
	}
	public void setIbmcHmGameSetId(Object ibmcHmGameSetId) {
		if (ibmcHmGameSetId != null) {
			this.ibmcHmGameSetId = ibmcHmGameSetId.toString();
		}else{
			this.ibmcHmGameSetId = null;
		}
	}
	public String getIbmcHmGameSetId() {
		return this.ibmcHmGameSetId ;
	}

	/**
	 * 客户端已存在盘口会员主键
	 */
	@Column(name="EXIST_HM_ID_")
	private String existHmId;
	public void setExistHmId(String existHmId) {
		this.existHmId=existHmId;
	}
	public void setExistHmId(Object existHmId) {
		if (existHmId != null) {
			this.existHmId = existHmId.toString();
		}else{
			this.existHmId = null;
		}
	}
	public String getExistHmId() {
		return this.existHmId ;
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
	 * 每期投注时刻
	 */
	@Column(name="BET_SECOND_")
	private Integer betSecond;
	public void setBetSecond(Integer betSecond) {
		this.betSecond=betSecond;
	}
	public Integer getBetSecond() {
		return this.betSecond ;
	}

	/**
	 * 双面分投额度
	 */
	@Column(name="SPLIT_TWO_SIDE_AMOUNT_")
	private Integer splitTwoSideAmount;
	public void setSplitTwoSideAmount(Integer splitTwoSideAmount) {
		this.splitTwoSideAmount=splitTwoSideAmount;
	}
	public void setSplitTwoSideAmount(Object splitTwoSideAmount) {
		if (splitTwoSideAmount != null) {
			if (splitTwoSideAmount instanceof Integer) {
				this.splitTwoSideAmount= (Integer) splitTwoSideAmount;
			}else if (StringTool.isInteger(splitTwoSideAmount.toString())) {
				this.splitTwoSideAmount = Integer.parseInt(splitTwoSideAmount.toString());
			}
		}else{
			this.splitTwoSideAmount = null;
		}
	}
	public Integer getSplitTwoSideAmount() {
		return this.splitTwoSideAmount ;
	}

	/**
	 * 号码分投额度
	 */
	@Column(name="SPLIT_NUMBER_AMOUNT_")
	private Integer splitNumberAmount;
	public void setSplitNumberAmount(Integer splitNumberAmount) {
		this.splitNumberAmount=splitNumberAmount;
	}
	public void setSplitNumberAmount(Object splitNumberAmount) {
		if (splitNumberAmount != null) {
			if (splitNumberAmount instanceof Integer) {
				this.splitNumberAmount= (Integer) splitNumberAmount;
			}else if (StringTool.isInteger(splitNumberAmount.toString())) {
				this.splitNumberAmount = Integer.parseInt(splitNumberAmount.toString());
			}
		}else{
			this.splitNumberAmount = null;
		}
	}
	public Integer getSplitNumberAmount() {
		return this.splitNumberAmount ;
	}

	/**
	 * 投注限额
	 */
	@Column(name="BET_LIMIT_")
	private String betLimit;
	public void setBetLimit(String betLimit) {
		this.betLimit=betLimit;
	}
	public void setBetLimit(Object betLimit) {
		if (betLimit != null) {
			this.betLimit = betLimit.toString();
		}else{
			this.betLimit = null;
		}
	}
	public String getBetLimit() {
		return this.betLimit ;
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
		}else{
			this.createTimeLong = null;
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