package com.ibm.old.v1.cloud.ibm_plan_hm.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_plan_hm
 * IBM_盘口会员与方案的实体类
 */
@AnnotationEntity @AnnotationTable(name = "ibm_plan_hm") public class IbmPlanHmT implements Serializable {

	private static final long serialVersionUID = 1L;

	//索引
	@Column(name = "IDX_") private Long idx;

	public void setIdx(Long idx) {
		this.idx = idx;
	}
	public Long getIdx() {
		return this.idx;
	}
	@Id
	// 根据底层数据库
	@GeneratedValue(strategy = GenerationType.AUTO)

	//IBM_盘口会员与方案主键
	@Column(name = "IBM_PLAN_HM_ID_") private String ibmPlanHmId;

	public void setIbmPlanHmId(String ibmPlanHmId) {
		this.ibmPlanHmId = ibmPlanHmId;
	}
	public String getIbmPlanHmId() {
		return this.ibmPlanHmId;
	}

	//方案主键
	@Column(name = "PLAN_ID_") private String planId;

	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getPlanId() {
		return this.planId;
	}

	//盘口会员主键
	@Column(name = "HANDICAP_MEMBER_ID_") private String handicapMemberId;

	public void setHandicapMemberId(String handicapMemberId) {
		this.handicapMemberId = handicapMemberId;
	}
	public String getHandicapMemberId() {
		return this.handicapMemberId;
	}

	//玩家与方案主键
	@Column(name = "PLAN_USER_ID_") private String planUserId;

	public void setPlanUserId(String planUserId) {
		this.planUserId = planUserId;
	}
	public String getPlanUserId() {
		return this.planUserId;
	}

	//游戏主键
	@Column(name = "GAME_ID_") private String gameId;

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getGameId() {
		return this.gameId;
	}

	/**
	 * 方案详情表主键
	 */
	@Column(name = "PLAN_ITEM_TABLE_ID_") private String planItemTableId;
	public void setPlanItemTableId(String planItemTableId) {
		this.planItemTableId = planItemTableId;
	}
	public void setPlanItemTableId(Object planItemTableId) {
		if (planItemTableId != null) {
			this.planItemTableId = planItemTableId.toString();
		} else {
			this.planItemTableId = null;
		}
	}
	public String getPlanItemTableId() {
		return this.planItemTableId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	//创建时间
	@Column(name = "CREATE_TIME_") private Date createTime;

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getCreateTime() {
		return this.createTime;
	}

	//创建时间
	@Column(name = "CREATE_TIME_LONG_") private Long createTimeLong;

	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong = createTimeLong;
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong;
	}

	@Temporal(TemporalType.TIMESTAMP)

	//更新时间
	@Column(name = "UPDATE_TIME_") private Date updateTime;

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getUpdateTime() {
		return this.updateTime;
	}

	//更新时间
	@Column(name = "UPDATE_TIME_LONG_") private Long updateTimeLong;

	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong = updateTimeLong;
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong;
	}

	//DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	@Column(name = "STATE_") private String state;

	public void setState(String state) {
		this.state = state;
	}
	public String getState() {
		return this.state;
	}

	//描述
	@Column(name = "DESC_") private String desc;

	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDesc() {
		return this.desc;
	}

	private String tableNameMy;
	/*
	 * 不映射
	 *
	 * @return
	 */
	@Transient public String getTableNameMy() {
		return tableNameMy;
	}

	public void setTableNameMy(String tableNameMy) {
		this.tableNameMy = tableNameMy;
	}

}