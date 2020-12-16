package com.ibm.follow.servlet.cloud.vr_user_follow_member.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table vr_user_follow_member 
 * VR_用户跟投会员 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "vr_user_follow_member")
public class VrUserFollowMember implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * VR_用户跟投会员主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="VR_USER_FOLLOW_MEMBER_ID_")
	private String vrUserFollowMemberId;
	public void setVrUserFollowMemberId(String vrUserFollowMemberId) {
		this.vrUserFollowMemberId=vrUserFollowMemberId;
	}
	public void setVrUserFollowMemberId(Object vrUserFollowMemberId) {
		if (vrUserFollowMemberId != null) {
			this.vrUserFollowMemberId = vrUserFollowMemberId.toString();
		}
	}
	public String getVrUserFollowMemberId() {
		return this.vrUserFollowMemberId ;
	}

	/**
	 * 用户主键
	 */
	@Column(name="USER_ID_")
	private String userId;
	public void setUserId(String userId) {
		this.userId=userId;
	}
	public void setUserId(Object userId) {
		if (userId != null) {
			this.userId = userId.toString();
		}
	}
	public String getUserId() {
		return this.userId ;
	}

	/**
	 * 虚拟会员主键
	 */
	@Column(name="VR_MEMBER_ID_")
	private String vrMemberId;
	public void setVrMemberId(String vrMemberId) {
		this.vrMemberId=vrMemberId;
	}
	public void setVrMemberId(Object vrMemberId) {
		if (vrMemberId != null) {
			this.vrMemberId = vrMemberId.toString();
		}
	}
	public String getVrMemberId() {
		return this.vrMemberId ;
	}

	/**
	 * 虚拟会员账户
	 */
	@Column(name="VR_MEMBER_ACCOUNT_")
	private String vrMemberAccount;
	public void setVrMemberAccount(String vrMemberAccount) {
		this.vrMemberAccount=vrMemberAccount;
	}
	public void setVrMemberAccount(Object vrMemberAccount) {
		if (vrMemberAccount != null) {
			this.vrMemberAccount = vrMemberAccount.toString();
		}
	}
	public String getVrMemberAccount() {
		return this.vrMemberAccount ;
	}

	/**
	 * 虚拟盘口编码
	 */
	@Column(name="VR_HANDICAP_CODE_")
	private String vrHandicapCode;
	public void setVrHandicapCode(String vrHandicapCode) {
		this.vrHandicapCode=vrHandicapCode;
	}
	public void setVrHandicapCode(Object vrHandicapCode) {
		if (vrHandicapCode != null) {
			this.vrHandicapCode = vrHandicapCode.toString();
		}
	}
	public String getVrHandicapCode() {
		return this.vrHandicapCode ;
	}

	/**
	 * 跟投状态
	 */
	@Column(name="FOLLOW_STATE_")
	private String followState;
	public void setFollowState(String followState) {
		this.followState=followState;
	}
	public void setFollowState(Object followState) {
		if (followState != null) {
			this.followState = followState.toString();
		}
	}
	public String getFollowState() {
		return this.followState ;
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