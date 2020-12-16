package com.ibm.old.v1.client.ibm_client_bet_error.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_client_bet_error 
 * IBM_客户端投注错误信息的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_client_bet_error")
public class IbmClientBetErrorT implements Serializable {

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
	 * IBM_客户端错误投注信息主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_CLIENT_BET_ERROR_ID_")
	private String ibmClientBetErrorId;
	public void setIbmClientBetErrorId(String ibmClientBetErrorId) {
		this.ibmClientBetErrorId=ibmClientBetErrorId;
	}
	public void setIbmClientBetErrorId(Object ibmClientBetErrorId) {
		if (ibmClientBetErrorId != null) {
			this.ibmClientBetErrorId = ibmClientBetErrorId.toString();
		}else{
			this.ibmClientBetErrorId = null;
		}
	}
	public String getIbmClientBetErrorId() {
		return this.ibmClientBetErrorId ;
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
	 * 执行投注项
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
	 * 消息回执投注
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
	 * 盘口会员
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
	 * 错误投注信息
	 */
	@Column(name="ERROR_BET_INFO_")
	private String errorBetInfo;
	public void setErrorBetInfo(String errorBetInfo) {
		this.errorBetInfo=errorBetInfo;
	}
	public void setErrorBetInfo(Object errorBetInfo) {
		if (errorBetInfo != null) {
			this.errorBetInfo = errorBetInfo.toString();
		}else{
			this.errorBetInfo = null;
		}
	}
	public String getErrorBetInfo() {
		return this.errorBetInfo ;
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