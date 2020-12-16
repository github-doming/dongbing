package com.ibs.plan.module.client.ibsc_hm_set.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsc_hm_set 
 * IBSC_客户端会员设置 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsc_hm_set")
public class IbscHmSet implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSC_客户端会员设置主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSC_HM_SET_ID_")
	private String ibscHmSetId;
	public void setIbscHmSetId(String ibscHmSetId) {
		this.ibscHmSetId=ibscHmSetId;
	}
	public void setIbscHmSetId(Object ibscHmSetId) {
		if (ibscHmSetId != null) {
			this.ibscHmSetId = ibscHmSetId.toString();
		}
	}
	public String getIbscHmSetId() {
		return this.ibscHmSetId ;
	}

	/**
	 * 客户端已存在盘口会员主键
	 */
	@Column(name="EXIST_HM_ID_")
	private String existHmId;
	public void setExistHmId(String existHmId) {
		this.existHmId=existHmId;
	}
	public void setExistHmId(Object existHmId) {
		if (existHmId != null) {
			this.existHmId = existHmId.toString();
		}
	}
	public String getExistHmId() {
		return this.existHmId ;
	}

	/**
	 * 投注比例
	 */
	@Column(name="BET_RATE_T_")
	private Integer betRateT;
	public void setBetRateT(Integer betRateT) {
		this.betRateT=betRateT;
	}
	public void setBetRateT(Object betRateT) {
		if (betRateT != null) {
			if (betRateT instanceof Integer) {
				this.betRateT= (Integer) betRateT;
			}else if (StringTool.isInteger(betRateT.toString())) {
				this.betRateT = Integer.parseInt(betRateT.toString());
			}
		}
	}
	public Integer getBetRateT() {
		return this.betRateT ;
	}

	/**
	 * 投注合并
	 */
	@Column(name="BET_MERGER_")
	private String betMerger;
	public void setBetMerger(String betMerger) {
		this.betMerger=betMerger;
	}
	public void setBetMerger(Object betMerger) {
		if (betMerger != null) {
			this.betMerger = betMerger.toString();
		}
	}
	public String getBetMerger() {
		return this.betMerger ;
	}

	/**
	 * 炸停止
	 */
	@Column(name="BLAST_STOP_")
	private String blastStop;
	public void setBlastStop(String blastStop) {
		this.blastStop=blastStop;
	}
	public void setBlastStop(Object blastStop) {
		if (blastStop != null) {
			this.blastStop = blastStop.toString();
		}
	}
	public String getBlastStop() {
		return this.blastStop ;
	}

	/**
	 * 创建时间CREATE_TIME_
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
	 * 创建时间数字型CREATE_TIME_LONG_
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
	 * 更新时间UPDATE_TIME_
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
	 * 更新时间数字型UPDATE_TIME_LONG_
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