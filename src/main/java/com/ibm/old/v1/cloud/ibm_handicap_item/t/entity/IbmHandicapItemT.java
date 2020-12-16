package com.ibm.old.v1.cloud.ibm_handicap_item.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_handicap_item 
 * IBM_盘口详情IBM_HANDICAP_ITEM的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_handicap_item")
public class IbmHandicapItemT implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 索引
	 */
	@Column(name="IDX_")
	private Long idx;
	public void setIdx(Long idx) {
		this.idx=idx;
	}
	public Long getIdx() {
		return this.idx ;
	}

	/**
	 * IBM_盘口详情主键IBM_HANDICAP_ITEM_ID_
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_HANDICAP_ITEM_ID_")
	private String ibmHandicapItemId;
	public void setIbmHandicapItemId(String ibmHandicapItemId) {
		this.ibmHandicapItemId=ibmHandicapItemId;
	}
	public String getIbmHandicapItemId() {
		return this.ibmHandicapItemId ;
	}

	/**
	 * 盘口主键
	 */
	@Column(name="HANDICAP_ID_")
	private String handicapId;
	public void setHandicapId(String handicapId) {
		this.handicapId=handicapId;
	}
	public String getHandicapId() {
		return this.handicapId ;
	}

	/**
	 * 盘口名称
	 */
	@Column(name="HANDICAP_NAME_")
	private String handicapName;
	public void setHandicapName(String handicapName) {
		this.handicapName=handicapName;
	}
	public String getHandicapName() {
		return this.handicapName ;
	}

	/**
	 * 盘口地址
	 */
	@Column(name="HANDICAP_URL_")
	private String handicapUrl;
	public void setHandicapUrl(String handicapUrl) {
		this.handicapUrl=handicapUrl;
	}
	public String getHandicapUrl() {
		return this.handicapUrl ;
	}

	/**
	 * 校验码
	 */
	@Column(name="HANDICAP_CAPTCHA_")
	private String handicapCaptcha;
	public void setHandicapCaptcha(String handicapCaptcha) {
		this.handicapCaptcha=handicapCaptcha;
	}
	public String getHandicapCaptcha() {
		return this.handicapCaptcha ;
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