package com.ibm.old.v1.cloud.ibm_cloud_receipt_bet.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_cloud_receipt_bet 
 * IBM_中心端客户端消息回执投注的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_cloud_receipt_bet")
public class IbmCloudReceiptBetT implements Serializable {

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
		}
	}
	public Long getIdx() {
		return this.idx ;
	}

	/**
	 * IBM_中心端客户端投注消息回执主键IBM_CLOUD_CLIENT_MESSAGE_RECEIPT_BET_ID_
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_CLOUD_RECEIPT_BET_ID_")
	private String ibmCloudReceiptBetId;
	public void setIbmCloudReceiptBetId(String ibmCloudReceiptBetId) {
		this.ibmCloudReceiptBetId=ibmCloudReceiptBetId;
	}
	public void setIbmCloudReceiptBetId(Object ibmCloudReceiptBetId) {
		if (ibmCloudReceiptBetId != null) {
			this.ibmCloudReceiptBetId = ibmCloudReceiptBetId.toString();
		}
	}
	public String getIbmCloudReceiptBetId() {
		return this.ibmCloudReceiptBetId ;
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
		}
	}
	public String getClientExistHmId() {
		return this.clientExistHmId ;
	}

	/**
	 * IBM_执行投注项主键
	 */
	@Column(name="EXEC_BET_ITEM_IDS_")
	private String execBetItemIds;
	public void setExecBetItemIds(String execBetItemIds) {
		this.execBetItemIds=execBetItemIds;
	}
	public void setExecBetItemIds(Object execBetItemIds) {
		if (execBetItemIds != null) {
			this.execBetItemIds = execBetItemIds.toString();
		}
	}
	public String getExecBetItemIds() {
		return this.execBetItemIds ;
	}

	/**
	 * 回执状态RECEIPT_STATE_
	 */
	@Column(name="RECEIPT_STATE_")
	private String receiptState;
	public void setReceiptState(String receiptState) {
		this.receiptState=receiptState;
	}
	public void setReceiptState(Object receiptState) {
		if (receiptState != null) {
			this.receiptState = receiptState.toString();
		}
	}
	public String getReceiptState() {
		return this.receiptState ;
	}

	/**
	 * 处理结果PROCESS_RESULT_
	 */
	@Column(name="PROCESS_RESULT_")
	private String processResult;
	public void setProcessResult(String processResult) {
		this.processResult=processResult;
	}
	public void setProcessResult(Object processResult) {
		if (processResult != null) {
			this.processResult = processResult.toString();
		}
	}
	public String getProcessResult() {
		return this.processResult ;
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