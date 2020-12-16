package com.ibm.old.v1.client.ibm_client_bet_fail.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_client_bet_fail 
 * IBM_客户端失败投注信息的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_client_bet_fail")
public class IbmClientBetFailT implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 索引
	 */
	@Column(name="IDX_")
	private Long idx;
	public void setIdx(Long idx) {
		this.idx=idx;
	}
	public void setIdx(Object idx) {
		if (idx != null) {
			if (idx instanceof Long) {
				this.idx= (Long) idx;
			}else if (StringTool.isInteger(idx.toString())) {
				this.idx = Long.parseLong(idx.toString());
			}
		}else{
			this.idx = null;
		}
	}
	public Long getIdx() {
		return this.idx ;
	}

	/**
	 * IBM_客户端已存投注信息主键IBM_CLIENT_BET_INFO_EXIST_ID_
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_CLIENT_BET_FAIL_ID_")
	private String ibmClientBetFailId;
	public void setIbmClientBetFailId(String ibmClientBetFailId) {
		this.ibmClientBetFailId=ibmClientBetFailId;
	}
	public void setIbmClientBetFailId(Object ibmClientBetFailId) {
		if (ibmClientBetFailId != null) {
			this.ibmClientBetFailId = ibmClientBetFailId.toString();
		}else{
			this.ibmClientBetFailId = null;
		}
	}
	public String getIbmClientBetFailId() {
		return this.ibmClientBetFailId ;
	}

	/**
	 * 客户端已存在盘口会员主键
	 */
	@Column(name="CLIENT_EXIST_HM_ID_")
	private String clientExistHmId;
	public void setClientExistHmId(String clientExistHmId) {
		this.clientExistHmId=clientExistHmId;
	}
	public void setClientExistHmId(Object clientExistHmId) {
		if (clientExistHmId != null) {
			this.clientExistHmId = clientExistHmId.toString();
		}else{
			this.clientExistHmId = null;
		}
	}
	public String getClientExistHmId() {
		return this.clientExistHmId ;
	}

	/**
	 * IBM_客户端已存投注信息主键
	 */
	@Column(name="CLIENT_EXIST_BET_ID_")
	private String clientExistBetId;
	public void setClientExistBetId(String clientExistBetId) {
		this.clientExistBetId=clientExistBetId;
	}
	public void setClientExistBetId(Object clientExistBetId) {
		if (clientExistBetId != null) {
			this.clientExistBetId = clientExistBetId.toString();
		}else{
			this.clientExistBetId = null;
		}
	}
	public String getClientExistBetId() {
		return this.clientExistBetId ;
	}

	/**
	 * IBM_执行投注项主键
	 */
	@Column(name="EXEC_BET_ITEM_ID_")
	private String execBetItemId;
	public void setExecBetItemId(String execBetItemId) {
		this.execBetItemId=execBetItemId;
	}
	public void setExecBetItemId(Object execBetItemId) {
		if (execBetItemId != null) {
			this.execBetItemId = execBetItemId.toString();
		}else{
			this.execBetItemId = null;
		}
	}
	public String getExecBetItemId() {
		return this.execBetItemId ;
	}

	/**
	 * 消息回执投注主键
	 */
	@Column(name="CLOUD_RECEIPT_BET_ID_")
	private String cloudReceiptBetId;
	public void setCloudReceiptBetId(String cloudReceiptBetId) {
		this.cloudReceiptBetId=cloudReceiptBetId;
	}
	public void setCloudReceiptBetId(Object cloudReceiptBetId) {
		if (cloudReceiptBetId != null) {
			this.cloudReceiptBetId = cloudReceiptBetId.toString();
		}else{
			this.cloudReceiptBetId = null;
		}
	}
	public String getCloudReceiptBetId() {
		return this.cloudReceiptBetId ;
	}

	/**
	 * 盘口会员主键HANDICAP_MEMBER_ID_
	 */
	@Column(name="HANDICAP_MEMBER_ID_")
	private String handicapMemberId;
	public void setHandicapMemberId(String handicapMemberId) {
		this.handicapMemberId=handicapMemberId;
	}
	public void setHandicapMemberId(Object handicapMemberId) {
		if (handicapMemberId != null) {
			this.handicapMemberId = handicapMemberId.toString();
		}else{
			this.handicapMemberId = null;
		}
	}
	public String getHandicapMemberId() {
		return this.handicapMemberId ;
	}

	/**
	 * 游戏编码GAME_CODE_
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
	 * 期数PERIOD_
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
	 * 投注分类BET_TYPE_
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
	 * 投注内容BET_INFO_
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
	public void setUpdateTime(Object updateTime) throws ParseException {
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