package com.cloud.user.app_user_point.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table app_user_point 
 * APP用户点数 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "app_user_point")
public class AppUserPoint implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * APP用户点数主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="APP_USER_POINT_ID_")
	private String appUserPointId;
	public void setAppUserPointId(String appUserPointId) {
		this.appUserPointId=appUserPointId;
	}
	public void setAppUserPointId(Object appUserPointId) {
		if (appUserPointId != null) {
			this.appUserPointId = appUserPointId.toString();
		}
	}
	public String getAppUserPointId() {
		return this.appUserPointId ;
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
	 * 末次报表主键
	 */
	@Column(name="REP_POINT_ID_")
	private String repPointId;
	public void setRepPointId(String repPointId) {
		this.repPointId=repPointId;
	}
	public void setRepPointId(Object repPointId) {
		if (repPointId != null) {
			this.repPointId = repPointId.toString();
		}
	}
	public String getRepPointId() {
		return this.repPointId ;
	}

	/**
	 * 点数总余额
	 */
	@Column(name="TOTAL_POINT_T_")
	private Long totalPointT;
	public void setTotalPointT(Long totalPointT) {
		this.totalPointT=totalPointT;
	}
	public void setTotalPointT(Object totalPointT) {
		if (totalPointT != null) {
			if (totalPointT instanceof Long) {
				this.totalPointT= (Long) totalPointT;
			}else if (StringTool.isInteger(totalPointT.toString())) {
				this.totalPointT = Long.parseLong(totalPointT.toString());
			}
		}
	}
	public Long getTotalPointT() {
		return this.totalPointT ;
	}

	/**
	 * 可用点数
	 */
	@Column(name="USEABLE_POINT_T_")
	private Long useablePointT;
	public void setUseablePointT(Long useablePointT) {
		this.useablePointT=useablePointT;
	}
	public void setUseablePointT(Object useablePointT) {
		if (useablePointT != null) {
			if (useablePointT instanceof Long) {
				this.useablePointT= (Long) useablePointT;
			}else if (StringTool.isInteger(useablePointT.toString())) {
				this.useablePointT = Long.parseLong(useablePointT.toString());
			}
		}
	}
	public Long getUseablePointT() {
		return this.useablePointT ;
	}

	/**
	 * 冻结点数
	 */
	@Column(name="FROZEN_POINT_T_")
	private Long frozenPointT;
	public void setFrozenPointT(Long frozenPointT) {
		this.frozenPointT=frozenPointT;
	}
	public void setFrozenPointT(Object frozenPointT) {
		if (frozenPointT != null) {
			if (frozenPointT instanceof Long) {
				this.frozenPointT= (Long) frozenPointT;
			}else if (StringTool.isInteger(frozenPointT.toString())) {
				this.frozenPointT = Long.parseLong(frozenPointT.toString());
			}
		}
	}
	public Long getFrozenPointT() {
		return this.frozenPointT ;
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