package com.ibm.old.v1.client.ibm_client_hm_info.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_client_hm_info 
 * IBM_客户端盘口会员信息的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_client_hm_info")
public class IbmClientHmInfo implements Serializable {

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
	 * IBM_客户端盘口会员信息主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_CLIENT_HM_INFO_ID_")
	private String ibmClientHmInfoId;
	public void setIbmClientHmInfoId(String ibmClientHmInfoId) {
		this.ibmClientHmInfoId=ibmClientHmInfoId;
	}
	public void setIbmClientHmInfoId(Object ibmClientHmInfoId) {
		if (ibmClientHmInfoId != null) {
			this.ibmClientHmInfoId = ibmClientHmInfoId.toString();
		}else{
			this.ibmClientHmInfoId = null;
		}
	}
	public String getIbmClientHmInfoId() {
		return this.ibmClientHmInfoId ;
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
	 * 会员账户
	 */
	@Column(name="MEMBER_ACCOUNT_")
	private String memberAccount;
	public void setMemberAccount(String memberAccount) {
		this.memberAccount=memberAccount;
	}
	public void setMemberAccount(Object memberAccount) {
		if (memberAccount != null) {
			this.memberAccount = memberAccount.toString();
		}else{
			this.memberAccount = null;
		}
	}
	public String getMemberAccount() {
		return this.memberAccount ;
	}

	/**
	 * 所属类别
	 */
	@Column(name="MEMBER_TYPE_")
	private String memberType;
	public void setMemberType(String memberType) {
		this.memberType=memberType;
	}
	public void setMemberType(Object memberType) {
		if (memberType != null) {
			this.memberType = memberType.toString();
		}else{
			this.memberType = null;
		}
	}
	public String getMemberType() {
		return this.memberType ;
	}

	/**
	 * 信用额度
	 */
	@Column(name="CREDIT_QUOTA_T_")
	private Long creditQuotaT;
	public void setCreditQuotaT(Long creditQuotaT) {
		this.creditQuotaT=creditQuotaT;
	}
	public void setCreditQuotaT(Object creditQuotaT) {
		if (creditQuotaT != null) {
			if (creditQuotaT instanceof Long) {
				this.creditQuotaT= (Long) creditQuotaT;
			}else if (StringTool.isInteger(creditQuotaT.toString())) {
				this.creditQuotaT = Long.parseLong(creditQuotaT.toString());
			}
		}else{
			this.creditQuotaT = null;
		}
	}
	public Long getCreditQuotaT() {
		return this.creditQuotaT ;
	}

	/**
	 * 使用金额
	 */
	@Column(name="USED_AMOUNT_T_")
	private Long usedAmountT;
	public void setUsedAmountT(Long usedAmountT) {
		this.usedAmountT=usedAmountT;
	}
	public void setUsedAmountT(Object usedAmountT) {
		if (usedAmountT != null) {
			if (usedAmountT instanceof Long) {
				this.usedAmountT= (Long) usedAmountT;
			}else if (StringTool.isInteger(usedAmountT.toString())) {
				this.usedAmountT = Long.parseLong(usedAmountT.toString());
			}
		}else{
			this.usedAmountT = null;
		}
	}
	public Long getUsedAmountT() {
		return this.usedAmountT ;
	}

	/**
	 * 盈亏金额
	 */
	@Column(name="PROFIT_AMOUNT_T_")
	private Long profitAmountT;
	public void setProfitAmountT(Long profitAmountT) {
		this.profitAmountT=profitAmountT;
	}
	public void setProfitAmountT(Object profitAmountT) {
		if (profitAmountT != null) {
			if (profitAmountT instanceof Long) {
				this.profitAmountT= (Long) profitAmountT;
			}else if (StringTool.isInteger(profitAmountT.toString())) {
				this.profitAmountT = Long.parseLong(profitAmountT.toString());
			}
		}else{
			this.profitAmountT = null;
		}
	}
	public Long getProfitAmountT() {
		return this.profitAmountT ;
	}

	/**
	 * 会员信息
	 */
	@Column(name="MEMBER_INFO_")
	private String memberInfo;
	public void setMemberInfo(String memberInfo) {
		this.memberInfo=memberInfo;
	}
	public void setMemberInfo(Object memberInfo) {
		if (memberInfo != null) {
			this.memberInfo = memberInfo.toString();
		}else{
			this.memberInfo = null;
		}
	}
	public String getMemberInfo() {
		return this.memberInfo ;
	}

	/**
	 * 会员信息码
	 */
	@Column(name="MEMBER_INFO_CODE_")
	private String memberInfoCode;
	public void setMemberInfoCode(String memberInfoCode) {
		this.memberInfoCode=memberInfoCode;
	}
	public void setMemberInfoCode(Object memberInfoCode) {
		if (memberInfoCode != null) {
			this.memberInfoCode = memberInfoCode.toString();
		}else{
			this.memberInfoCode = null;
		}
	}
	public String getMemberInfoCode() {
		return this.memberInfoCode ;
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