package com.ibm.old.v1.cloud.ibm_exec_bet_result.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_exec_bet_result 
 * IBM_执行投注结果的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_exec_bet_result")
public class IbmExecBetResult implements Serializable {

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
	 * IBM_执行投注结果主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_EXEC_BET_RESULT_ID_")
	private String ibmExecBetResultId;
	public void setIbmExecBetResultId(String ibmExecBetResultId) {
		this.ibmExecBetResultId=ibmExecBetResultId;
	}
	public void setIbmExecBetResultId(Object ibmExecBetResultId) {
		if (ibmExecBetResultId != null) {
			this.ibmExecBetResultId = ibmExecBetResultId.toString();
		}else{
			this.ibmExecBetResultId = null;
		}
	}
	public String getIbmExecBetResultId() {
		return this.ibmExecBetResultId ;
	}

	/**
	 * 游戏主键
	 */
	@Column(name="GAME_ID_")
	private String gameId;
	public void setGameId(String gameId) {
		this.gameId=gameId;
	}
	public void setGameId(Object gameId) {
		if (gameId != null) {
			this.gameId = gameId.toString();
		}else{
			this.gameId = null;
		}
	}
	public String getGameId() {
		return this.gameId ;
	}

	/**
	 * 方案主键
	 */
	@Column(name="PLAN_ID_")
	private String planId;
	public void setPlanId(String planId) {
		this.planId=planId;
	}
	public void setPlanId(Object planId) {
		if (planId != null) {
			this.planId = planId.toString();
		}else{
			this.planId = null;
		}
	}
	public String getPlanId() {
		return this.planId ;
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
		}else{
			this.handicapId = null;
		}
	}
	public String getHandicapId() {
		return this.handicapId ;
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
	 * 执行盘口会员方案组主键
	 */
	@Column(name="EXEC_PLAN_GROUP_ID_")
	private String execPlanGroupId;
	public void setExecPlanGroupId(String execPlanGroupId) {
		this.execPlanGroupId=execPlanGroupId;
	}
	public void setExecPlanGroupId(Object execPlanGroupId) {
		if (execPlanGroupId != null) {
			this.execPlanGroupId = execPlanGroupId.toString();
		}else{
			this.execPlanGroupId = null;
		}
	}
	public String getExecPlanGroupId() {
		return this.execPlanGroupId ;
	}

	/**
	 * 方案详情表主键
	 */
	@Column(name="PLAN_ITEM_TABLE_ID_")
	private String planItemTableId;
	public void setPlanItemTableId(String planItemTableId) {
		this.planItemTableId=planItemTableId;
	}
	public void setPlanItemTableId(Object planItemTableId) {
		if (planItemTableId != null) {
			this.planItemTableId = planItemTableId.toString();
		}else{
			this.planItemTableId = null;
		}
	}
	public String getPlanItemTableId() {
		return this.planItemTableId ;
	}

	/**
	 * 方案组key
	 */
	@Column(name="PLAN_GROUP_KEY_")
	private String planGroupKey;
	public void setPlanGroupKey(String planGroupKey) {
		this.planGroupKey=planGroupKey;
	}
	public void setPlanGroupKey(Object planGroupKey) {
		if (planGroupKey != null) {
			this.planGroupKey = planGroupKey.toString();
		}else{
			this.planGroupKey = null;
		}
	}
	public String getPlanGroupKey() {
		return this.planGroupKey ;
	}

	/**
	 * 执行结果
	 */
	@Column(name="EXEC_RESULT_")
	private String execResult;
	public void setExecResult(String execResult) {
		this.execResult=execResult;
	}
	public void setExecResult(Object execResult) {
		if (execResult != null) {
			this.execResult = execResult.toString();
		}else{
			this.execResult = null;
		}
	}
	public String getExecResult() {
		return this.execResult ;
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