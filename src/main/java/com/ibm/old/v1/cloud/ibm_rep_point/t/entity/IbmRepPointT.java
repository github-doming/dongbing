package com.ibm.old.v1.cloud.ibm_rep_point.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_rep_point 
 * IBM报表_点数使用情况的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_rep_point")
public class IbmRepPointT implements Serializable {

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
		}
	}
	public Long getIdx() {
		return this.idx ;
	}

	/**
	 * IBM报表_点数主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_REP_POINT_ID_")
	private String ibmRepPointId;
	public void setIbmRepPointId(String ibmRepPointId) {
		this.ibmRepPointId=ibmRepPointId;
	}
	public void setIbmRepPointId(Object ibmRepPointId) {
		if (ibmRepPointId != null) {
			this.ibmRepPointId = ibmRepPointId.toString();
		}
	}
	public String getIbmRepPointId() {
		return this.ibmRepPointId ;
	}

	/**
	 * 上一次主键
	 */
	@Column(name="PRE_ID_")
	private String preId;
	public void setPreId(String preId) {
		this.preId=preId;
	}
	public void setPreId(Object preId) {
		if (preId != null) {
			this.preId = preId.toString();
		}
	}
	public String getPreId() {
		return this.preId ;
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
	 * 标题
	 */
	@Column(name="TITLE_")
	private String title;
	public void setTitle(String title) {
		this.title=title;
	}
	public void setTitle(Object title) {
		if (title != null) {
			this.title = title.toString();
		}
	}
	public String getTitle() {
		return this.title ;
	}

	/**
	 * 增减之前余额
	 */
	@Column(name="PRE_T_")
	private Long preT;
	public void setPreT(Long preT) {
		this.preT=preT;
	}
	public void setPreT(Object preT) {
		if (preT != null) {
			if (preT instanceof Long) {
				this.preT= (Long) preT;
			}else if (StringTool.isInteger(preT.toString())) {
				this.preT = Long.parseLong(preT.toString());
			}
		}
	}
	public Long getPreT() {
		return this.preT ;
	}

	/**
	 * 增减数字
	 */
	@Column(name="NUMBER_T_")
	private Long numberT;
	public void setNumberT(Long numberT) {
		this.numberT=numberT;
	}
	public void setNumberT(Object numberT) {
		if (numberT != null) {
			if (numberT instanceof Long) {
				this.numberT= (Long) numberT;
			}else if (StringTool.isInteger(numberT.toString())) {
				this.numberT = Long.parseLong(numberT.toString());
			}
		}
	}
	public Long getNumberT() {
		return this.numberT ;
	}

	/**
	 * 余额
	 */
	@Column(name="BALANCE_T_")
	private Long balanceT;
	public void setBalanceT(Long balanceT) {
		this.balanceT=balanceT;
	}
	public void setBalanceT(Object balanceT) {
		if (balanceT != null) {
			if (balanceT instanceof Long) {
				this.balanceT= (Long) balanceT;
			}else if (StringTool.isInteger(balanceT.toString())) {
				this.balanceT = Long.parseLong(balanceT.toString());
			}
		}
	}
	public Long getBalanceT() {
		return this.balanceT ;
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