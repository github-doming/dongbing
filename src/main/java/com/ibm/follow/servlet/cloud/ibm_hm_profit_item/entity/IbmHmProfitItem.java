package com.ibm.follow.servlet.cloud.ibm_hm_profit_item.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_hm_profit_item 
 * IBM_盘口会员盈亏详情的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_hm_profit_item")
public class IbmHmProfitItem implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_盘口会员盈亏详情主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_HM_PROFIT_ITEM_ID_")
	private String ibmHmProfitItemId;
	public void setIbmHmProfitItemId(String ibmHmProfitItemId) {
		this.ibmHmProfitItemId=ibmHmProfitItemId;
	}
	public void setIbmHmProfitItemId(Object ibmHmProfitItemId) {
		if (ibmHmProfitItemId != null) {
			this.ibmHmProfitItemId = ibmHmProfitItemId.toString();
		}else{
			this.ibmHmProfitItemId = null;
		}
	}
	public String getIbmHmProfitItemId() {
		return this.ibmHmProfitItemId ;
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
	 * IBM_盘口会员投注主键
	 */
	@Column(name="HM_BET_ITEM_ID_")
	private String hmBetItemId;
	public void setHmBetItemId(String hmBetItemId) {
		this.hmBetItemId=hmBetItemId;
	}
	public void setHmBetItemId(Object hmBetItemId) {
		if (hmBetItemId != null) {
			this.hmBetItemId = hmBetItemId.toString();
		}else{
			this.hmBetItemId = null;
		}
	}
	public String getHmBetItemId() {
		return this.hmBetItemId ;
	}

	/**
	 * 跟随会员账号
	 */
	@Column(name="FOLLOW_MEMBER_ACCOUNT_")
	private String followMemberAccount;
	public void setFollowMemberAccount(String followMemberAccount) {
		this.followMemberAccount=followMemberAccount;
	}
	public void setFollowMemberAccount(Object followMemberAccount) {
		if (followMemberAccount != null) {
			this.followMemberAccount = followMemberAccount.toString();
		}else{
			this.followMemberAccount = null;
		}
	}
	public String getFollowMemberAccount() {
		return this.followMemberAccount ;
	}

	/**
	 * 金额
	 */
	@Column(name="FUND_T_")
	private Long fundT;
	public void setFundT(Long fundT) {
		this.fundT=fundT;
	}
	public void setFundT(Object fundT) {
		if (fundT != null) {
			if (fundT instanceof Long) {
				this.fundT= (Long) fundT;
			}else if (StringTool.isInteger(fundT.toString())) {
				this.fundT = Long.parseLong(fundT.toString());
			}
		}else{
			this.fundT = null;
		}
	}
	public Long getFundT() {
		return this.fundT ;
	}

	/**
	 * 盈亏额PROFIT_T_
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
		}else{
			this.profitT = null;
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