package com.ibs.plan.module.cloud.ibsp_handicap_member.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsp_handicap_member 
 * IBSP_盘口会员 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsp_handicap_member")
public class IbspHandicapMember implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSP_盘口会员主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSP_HANDICAP_MEMBER_ID_")
	private String ibspHandicapMemberId;
	public void setIbspHandicapMemberId(String ibspHandicapMemberId) {
		this.ibspHandicapMemberId=ibspHandicapMemberId;
	}
	public void setIbspHandicapMemberId(Object ibspHandicapMemberId) {
		if (ibspHandicapMemberId != null) {
			this.ibspHandicapMemberId = ibspHandicapMemberId.toString();
		}
	}
	public String getIbspHandicapMemberId() {
		return this.ibspHandicapMemberId ;
	}

	/**
	 * 盘口主键
	 */
	@Column(name="HANDICAP_ID_")
	private String handicapId;
	public void setHandicapId(String handicapId) {
		this.handicapId=handicapId;
	}
	public void setHandicapId(Object handicapId) {
		if (handicapId != null) {
			this.handicapId = handicapId.toString();
		}
	}
	public String getHandicapId() {
		return this.handicapId ;
	}

	/**
	 * 盘口详情主键
	 */
	@Column(name="HANDICAP_ITEM_ID_")
	private String handicapItemId;
	public void setHandicapItemId(String handicapItemId) {
		this.handicapItemId=handicapItemId;
	}
	public void setHandicapItemId(Object handicapItemId) {
		if (handicapItemId != null) {
			this.handicapItemId = handicapItemId.toString();
		}
	}
	public String getHandicapItemId() {
		return this.handicapItemId ;
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
		}
	}
	public String getAppUserId() {
		return this.appUserId ;
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
	 * 会员密码
	 */
	@Column(name="MEMBER_PASSWORD_")
	private String memberPassword;
	public void setMemberPassword(String memberPassword) {
		this.memberPassword=memberPassword;
	}
	public void setMemberPassword(Object memberPassword) {
		if (memberPassword != null) {
			this.memberPassword = memberPassword.toString();
		}
	}
	public String getMemberPassword() {
		return this.memberPassword ;
	}

	/**
	 * 是否定时登录
	 */
	@Column(name="IS_LANDED_")
	private String isLanded;
	public void setIsLanded(String isLanded) {
		this.isLanded=isLanded;
	}
	public void setIsLanded(Object isLanded) {
		if (isLanded != null) {
			this.isLanded = isLanded.toString();
		}
	}
	public String getIsLanded() {
		return this.isLanded ;
	}

	/**
	 * 定时登录
	 */
	@Column(name="LANDED_TIME_LONG_")
	private Long landedTimeLong;
	public void setLandedTimeLong(Long landedTimeLong) {
		this.landedTimeLong=landedTimeLong;
	}
	public void setLandedTimeLong(Object landedTimeLong) {
		if (landedTimeLong != null) {
			if (landedTimeLong instanceof Long) {
				this.landedTimeLong= (Long) landedTimeLong;
			}else if (StringTool.isInteger(landedTimeLong.toString())) {
				this.landedTimeLong = Long.parseLong(landedTimeLong.toString());
			}
		}
	}
	public Long getLandedTimeLong() {
		return this.landedTimeLong ;
	}

	/**
	 * 使用频次
	 */
	@Column(name="FREQUENCY_")
	private Integer frequency;
	public void setFrequency(Integer frequency) {
		this.frequency=frequency;
	}
	public void setFrequency(Object frequency) {
		if (frequency != null) {
			if (frequency instanceof Integer) {
				this.frequency= (Integer) frequency;
			}else if (StringTool.isInteger(frequency.toString())) {
				this.frequency = Integer.parseInt(frequency.toString());
			}
		}
	}
	public Integer getFrequency() {
		return this.frequency ;
	}

	/**
	 * 操作
	 */
	@Column(name="OPERATING_")
	private String operating;
	public void setOperating(String operating) {
		this.operating=operating;
	}
	public void setOperating(Object operating) {
		if (operating != null) {
			this.operating = operating.toString();
		}
	}
	public String getOperating() {
		return this.operating ;
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