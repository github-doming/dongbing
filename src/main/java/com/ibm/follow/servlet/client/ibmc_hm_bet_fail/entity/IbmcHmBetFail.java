package com.ibm.follow.servlet.client.ibmc_hm_bet_fail.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibmc_hm_bet_fail 
 * IBMC客户端_会员投注失败的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibmc_hm_bet_fail")
public class IbmcHmBetFail implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBMC客户端_失败投注信息主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBMC_HM_BET_FAIL_ID_")
	private String ibmcHmBetFailId;
	public void setIbmcHmBetFailId(String ibmcHmBetFailId) {
		this.ibmcHmBetFailId=ibmcHmBetFailId;
	}
	public void setIbmcHmBetFailId(Object ibmcHmBetFailId) {
		if (ibmcHmBetFailId != null) {
			this.ibmcHmBetFailId = ibmcHmBetFailId.toString();
		}else{
			this.ibmcHmBetFailId = null;
		}
	}
	public String getIbmcHmBetFailId() {
		return this.ibmcHmBetFailId ;
	}

	/**
	 * 客户端已存在盘口会员主键CLIENT_HM_EXIST_ID_
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
	 * IBMC_客户端投注信息主键
	 */
	@Column(name="HM_BET_INFO_ID_")
	private String hmBetInfoId;
	public void setHmBetInfoId(String hmBetInfoId) {
		this.hmBetInfoId=hmBetInfoId;
	}
	public void setHmBetInfoId(Object hmBetInfoId) {
		if (hmBetInfoId != null) {
			this.hmBetInfoId = hmBetInfoId.toString();
		}else{
			this.hmBetInfoId = null;
		}
	}
	public String getHmBetInfoId() {
		return this.hmBetInfoId ;
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
	 * 期数
	 */
	@Column(name="PERIOD_")
	private String period;
	public void setPeriod(String period) {
		this.period=period;
	}
	public void setPeriod(Object period) {
		if (period != null) {
			this.period = period.toString();
		}else{
			this.period = null;
		}
	}
	public String getPeriod() {
		return this.period ;
	}

	/**
	 * 投注内容
	 */
	@Column(name="FAIL_BET_INFO_")
	private String failBetInfo;
	public void setFailBetInfo(String failBetInfo) {
		this.failBetInfo=failBetInfo;
	}
	public void setFailBetInfo(Object failBetInfo) {
		if (failBetInfo != null) {
			this.failBetInfo = failBetInfo.toString();
		}else{
			this.failBetInfo = null;
		}
	}
	public String getFailBetInfo() {
		return this.failBetInfo ;
	}

	/**
	 * 投注类型
	 */
	@Column(name="BET_TYPE_")
	private String betType;
	public void setBetType(String betType) {
		this.betType=betType;
	}
	public void setBetType(Object betType) {
		if (betType != null) {
			this.betType = betType.toString();
		}else{
			this.betType = null;
		}
	}
	public String getBetType() {
		return this.betType ;
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