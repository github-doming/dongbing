package com.ibs.plan.module.cloud.ibsp_plan.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsp_plan 
 * IBSP_方案 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsp_plan")
public class IbspPlan implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSP_方案主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSP_PLAN_ID_")
	private String ibspPlanId;
	public void setIbspPlanId(String ibspPlanId) {
		this.ibspPlanId=ibspPlanId;
	}
	public void setIbspPlanId(Object ibspPlanId) {
		if (ibspPlanId != null) {
			this.ibspPlanId = ibspPlanId.toString();
		}
	}
	public String getIbspPlanId() {
		return this.ibspPlanId ;
	}

	/**
	 * 方案名称
	 */
	@Column(name="PLAN_NAME_")
	private String planName;
	public void setPlanName(String planName) {
		this.planName=planName;
	}
	public void setPlanName(Object planName) {
		if (planName != null) {
			this.planName = planName.toString();
		}
	}
	public String getPlanName() {
		return this.planName ;
	}

	/**
	 * 方案编码
	 */
	@Column(name="PLAN_CODE_")
	private String planCode;
	public void setPlanCode(String planCode) {
		this.planCode=planCode;
	}
	public void setPlanCode(Object planCode) {
		if (planCode != null) {
			this.planCode = planCode.toString();
		}
	}
	public String getPlanCode() {
		return this.planCode ;
	}

	/**
	 * 方案说明
	 */
	@Column(name="PLAN_EXPLAIN_")
	private String planExplain;
	public void setPlanExplain(String planExplain) {
		this.planExplain=planExplain;
	}
	public void setPlanExplain(Object planExplain) {
		if (planExplain != null) {
			this.planExplain = planExplain.toString();
		}
	}
	public String getPlanExplain() {
		return this.planExplain ;
	}

	/**
	 * 方案详情表名
	 */
	@Column(name="PLAN_ITEM_TABLE_NAME_")
	private String planItemTableName;
	public void setPlanItemTableName(String planItemTableName) {
		this.planItemTableName=planItemTableName;
	}
	public void setPlanItemTableName(Object planItemTableName) {
		if (planItemTableName != null) {
			this.planItemTableName = planItemTableName.toString();
		}
	}
	public String getPlanItemTableName() {
		return this.planItemTableName ;
	}

	/**
	 * 方案类型
	 */
	@Column(name="PLAN_TYPE_")
	private Integer planType;
	public void setPlanType(Integer planType) {
		this.planType=planType;
	}
	public void setPlanType(Object planType) {
		if (planType != null) {
			if (planType instanceof Integer) {
				this.planType= (Integer) planType;
			}else if (StringTool.isInteger(planType.toString())) {
				this.planType = Integer.parseInt(planType.toString());
			}
		}
	}
	public Integer getPlanType() {
		return this.planType ;
	}

	/**
	 * 适用游戏类型
	 */
	@Column(name="AVAILABLE_GAME_TYPE_")
	private String availableGameType;
	public void setAvailableGameType(String availableGameType) {
		this.availableGameType=availableGameType;
	}
	public void setAvailableGameType(Object availableGameType) {
		if (availableGameType != null) {
			this.availableGameType = availableGameType.toString();
		}
	}
	public String getAvailableGameType() {
		return this.availableGameType ;
	}

	/**
	 * 方案价值
	 */
	@Column(name="PLAN_WORTH_T_")
	private Integer planWorthT;
	public void setPlanWorthT(Integer planWorthT) {
		this.planWorthT=planWorthT;
	}
	public void setPlanWorthT(Object planWorthT) {
		if (planWorthT != null) {
			if (planWorthT instanceof Integer) {
				this.planWorthT= (Integer) planWorthT;
			}else if (StringTool.isInteger(planWorthT.toString())) {
				this.planWorthT = Integer.parseInt(planWorthT.toString());
			}
		}
	}
	public Integer getPlanWorthT() {
		return this.planWorthT ;
	}

	/**
	 * 方案图标
	 */
	@Column(name="PLAN_ICON_")
	private String planIcon;
	public void setPlanIcon(String planIcon) {
		this.planIcon=planIcon;
	}
	public void setPlanIcon(Object planIcon) {
		if (planIcon != null) {
			this.planIcon = planIcon.toString();
		}
	}
	public String getPlanIcon() {
		return this.planIcon ;
	}

	/**
	 * 次序
	 */
	@Column(name="SN_")
	private Integer sn;
	public void setSn(Integer sn) {
		this.sn=sn;
	}
	public void setSn(Object sn) {
		if (sn != null) {
			if (sn instanceof Integer) {
				this.sn= (Integer) sn;
			}else if (StringTool.isInteger(sn.toString())) {
				this.sn = Integer.parseInt(sn.toString());
			}
		}
	}
	public Integer getSn() {
		return this.sn ;
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