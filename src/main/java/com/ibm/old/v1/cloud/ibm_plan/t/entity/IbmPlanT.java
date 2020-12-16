package com.ibm.old.v1.cloud.ibm_plan.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import com.ibm.old.v1.cloud.core.tool.PlanTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_plan
 * IBM_方案IBM_PLAN的实体类
 *
 * @author Robot
 */
@AnnotationEntity @AnnotationTable(name = "ibm_plan") public class IbmPlanT implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 索引
	 */
	@Column(name = "IDX_") private Long idx;
	public void setIdx(Long idx) {
		this.idx = idx;
	}
	public Long getIdx() {
		return this.idx;
	}

	/**
	 * IBM_方案主键IBM_PLAN_ID_
	 * 根据底层数据库
	 */
	@Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "IBM_PLAN_ID_") private String ibmPlanId;
	public void setIbmPlanId(String ibmPlanId) {
		this.ibmPlanId = ibmPlanId;
	}
	public String getIbmPlanId() {
		return this.ibmPlanId;
	}

	/**
	 * 游戏主键
	 */
	@Column(name = "GAME_ID_") private String gameId;
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getGameId() {
		return this.gameId;
	}

	/**
	 * 方案名称
	 */
	@Column(name = "PLAN_NAME_") private String planName;
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getPlanName() {
		return this.planName;
	}

	/**
	 * 方案编码
	 */
	@Column(name = "PLAN_CODE_") private String planCode;
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public String getPlanCode() {
		return this.planCode;
	}
	public PlanTool.Code getCode() {
		return PlanTool.Code.valueOf(getPlanCode());
	}
	/**
	 * 方案说明
	 */
	@Column(name = "PLAN_EXPLAIN_") private String planExplain;
	public void setPlanExplain(String planExplain) {
		this.planExplain = planExplain;
	}
	public String getPlanExplain() {
		return this.planExplain;
	}

	/**
	 * 方案详情表名
	 */
	@Column(name = "PLAN_ITEM_TABLE_NAME_") private String planItemTableName;
	public void setPlanItemTableName(String planItemTableName) {
		this.planItemTableName = planItemTableName;
	}
	public String getPlanItemTableName() {
		return this.planItemTableName;
	}

	/**
	 * 方案类型
	 */
	@Column(name = "PLAN_TYPE_") private String planType;
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	public String getPlanType() {
		return this.planType;
	}

	/**
	 * 方案价值
	 */
	@Column(name = "PLAN_WORTH_T_") private Integer planWorthT;
	public void setPlanWorthT(Integer planWorthT) {
		this.planWorthT = planWorthT;
	}
	public Integer getPlanWorthT() {
		return this.planWorthT;
	}

	/**
	 * 方案图标
	 */
	@Column(name = "PLAN_ICON_") private String planIcon;
	public void setPlanIcon(String planIcon) {
		this.planIcon = planIcon;
	}
	public String getPlanIcon() {
		return this.planIcon;
	}

	/**
	 * 次序
	 */
	@Column(name = "SN_") private Integer sn;
	public void setSn(Integer sn) {
		this.sn = sn;
	}
	public Integer getSn() {
		return this.sn;
	}

	/**
	 * 创建者CREATE_USER_
	 */
	@Column(name = "CREATE_USER_") private String createUser;
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateUser() {
		return this.createUser;
	}

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP) @Column(name = "CREATE_TIME_") private Date createTime;
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME_LONG_") private Long createTimeLong;
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong = createTimeLong;
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong;
	}

	/**
	 * 更新者UPDATE_USER_
	 */
	@Column(name = "UPDATE_USER_") private String updateUser;
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getUpdateUser() {
		return this.updateUser;
	}

	/**
	 * 更新时间
	 */
	@Temporal(TemporalType.TIMESTAMP) @Column(name = "UPDATE_TIME_") private Date updateTime;
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getUpdateTime() {
		return this.updateTime;
	}

	/**
	 * 更新时间
	 */
	@Column(name = "UPDATE_TIME_LONG_") private Long updateTimeLong;
	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong = updateTimeLong;
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong;
	}

	/**
	 * DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	 */
	@Column(name = "STATE_") private String state;
	public void setState(String state) {
		this.state = state;
	}
	public String getState() {
		return this.state;
	}

	/**
	 * 描述
	 */
	@Column(name = "DESC_") private String desc;
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDesc() {
		return this.desc;
	}

	private String tableNameMy;
	/**
	 * 不映射
	 *
	 * @return 表名
	 */
	@Transient public String getTableNameMy() {
		return tableNameMy;
	}

	public void setTableNameMy(String tableNameMy) {
		this.tableNameMy = tableNameMy;
	}

}