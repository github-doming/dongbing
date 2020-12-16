package com.ibs.plan.module.client.ibsc_hm_info.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsc_hm_info 
 * IBSC_客户端盘口会员信息 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsc_hm_info")
public class IbscHmInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSC_客户端盘口会员信息主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSC_HM_INFO_ID_")
	private String ibscHmInfoId;
	public void setIbscHmInfoId(String ibscHmInfoId) {
		this.ibscHmInfoId=ibscHmInfoId;
	}
	public void setIbscHmInfoId(Object ibscHmInfoId) {
		if (ibscHmInfoId != null) {
			this.ibscHmInfoId = ibscHmInfoId.toString();
		}
	}
	public String getIbscHmInfoId() {
		return this.ibscHmInfoId ;
	}

	/**
	 * 已存在盘口会员主键
	 */
	@Column(name="EXIST_HM_ID_")
	private String existHmId;
	public void setExistHmId(String existHmId) {
		this.existHmId=existHmId;
	}
	public void setExistHmId(Object existHmId) {
		if (existHmId != null) {
			this.existHmId = existHmId.toString();
		}
	}
	public String getExistHmId() {
		return this.existHmId ;
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
		}
	}
	public String getMemberAccount() {
		return this.memberAccount ;
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
		}
	}
	public String getMemberInfoCode() {
		return this.memberInfoCode ;
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