package com.ibm.follow.servlet.cloud.ibm_hm_info.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_hm_info 
 * IBM_盘口会员信息的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_hm_info")
public class IbmHmInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBMC_客户端盘口会员信息主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_HM_INFO_ID_")
	private String ibmHmInfoId;
	public void setIbmHmInfoId(String ibmHmInfoId) {
		this.ibmHmInfoId=ibmHmInfoId;
	}
	public void setIbmHmInfoId(Object ibmHmInfoId) {
		if (ibmHmInfoId != null) {
			this.ibmHmInfoId = ibmHmInfoId.toString();
		}else{
			this.ibmHmInfoId = null;
		}
	}
	public String getIbmHmInfoId() {
		return this.ibmHmInfoId ;
	}

	/**
	 * 用户主键
	 */
	@Column(name="APP_USER_ID_")
	private String appUserId;
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public void setAppUserId(Object appUserId) {
		if (appUserId != null) {
			this.appUserId = appUserId.toString();
		}else{
			this.appUserId = null;
		}
	}
	public String getAppUserId() {
		return this.appUserId ;
	}

	/**
	 * 盘口会员主键
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
	 * 盘口盈亏HANDICAP_PROFIT_T_
	 */
	@Column(name="HANDICAP_PROFIT_T_")
	private Long handicapProfitT;
	public void setHandicapProfitT(Long handicapProfitT) {
		this.handicapProfitT=handicapProfitT;
	}
	public void setHandicapProfitT(Object handicapProfitT) {
		if (handicapProfitT != null) {
			if (handicapProfitT instanceof Long) {
				this.handicapProfitT= (Long) handicapProfitT;
			}else if (StringTool.isInteger(handicapProfitT.toString())) {
				this.handicapProfitT = Long.parseLong(handicapProfitT.toString());
			}
		}else{
			this.handicapProfitT = null;
		}
	}
	public Long getHandicapProfitT() {
		return this.handicapProfitT ;
	}

	/**
	 * 使用金额
	 */
	@Column(name="AMOUNT_T_")
	private Long amountT;
	public void setAmountT(Long amountT) {
		this.amountT=amountT;
	}
	public void setAmountT(Object amountT) {
		if (amountT != null) {
			if (amountT instanceof Long) {
				this.amountT= (Long) amountT;
			}else if (StringTool.isInteger(amountT.toString())) {
				this.amountT = Long.parseLong(amountT.toString());
			}
		}else{
			this.amountT = null;
		}
	}
	public Long getAmountT() {
		return this.amountT ;
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
	 * 检验信息
	 */
	@Column(name="CHECK_INFO_")
	private String checkInfo;
	public void setCheckInfo(String checkInfo) {
		this.checkInfo=checkInfo;
	}
	public void setCheckInfo(Object checkInfo) {
		if (checkInfo != null) {
			this.checkInfo = checkInfo.toString();
		}else{
			this.checkInfo = null;
		}
	}
	public String getCheckInfo() {
		return this.checkInfo ;
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