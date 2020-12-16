package com.ibm.follow.servlet.cloud.vr_plan.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table vr_plan 
 * VR_方案 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "vr_plan")
public class VrPlan implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * VR_方案主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="VR_PLAN_ID_")
	private String vrPlanId;
	public void setVrPlanId(String vrPlanId) {
		this.vrPlanId=vrPlanId;
	}
	public void setVrPlanId(Object vrPlanId) {
		if (vrPlanId != null) {
			this.vrPlanId = vrPlanId.toString();
		}
	}
	public String getVrPlanId() {
		return this.vrPlanId ;
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
	 * 创建时间数字型
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
	 * 更新时间数字型
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