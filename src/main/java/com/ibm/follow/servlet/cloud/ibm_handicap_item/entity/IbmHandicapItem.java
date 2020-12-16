package com.ibm.follow.servlet.cloud.ibm_handicap_item.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_handicap_item 
 * IBM_盘口详情的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_handicap_item")
public class IbmHandicapItem implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_盘口详情主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_HANDICAP_ITEM_ID_")
	private String ibmHandicapItemId;
	public void setIbmHandicapItemId(String ibmHandicapItemId) {
		this.ibmHandicapItemId=ibmHandicapItemId;
	}
	public void setIbmHandicapItemId(Object ibmHandicapItemId) {
		if (ibmHandicapItemId != null) {
			this.ibmHandicapItemId = ibmHandicapItemId.toString();
		}else{
			this.ibmHandicapItemId = null;
		}
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
	 * 盘口名称
	 */
	@Column(name="HANDICAP_NAME_")
	private String handicapName;
	public void setHandicapName(String handicapName) {
		this.handicapName=handicapName;
	}
	public void setHandicapName(Object handicapName) {
		if (handicapName != null) {
			this.handicapName = handicapName.toString();
		}else{
			this.handicapName = null;
		}
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
	public void setHandicapUrl(Object handicapUrl) {
		if (handicapUrl != null) {
			this.handicapUrl = handicapUrl.toString();
		}else{
			this.handicapUrl = null;
		}
	}
	public String getHandicapUrl() {
		return this.handicapUrl ;
	}

	/**
	 * 盘口校验码
	 */
	@Column(name="HANDICAP_CAPTCHA_")
	private String handicapCaptcha;
	public void setHandicapCaptcha(String handicapCaptcha) {
		this.handicapCaptcha=handicapCaptcha;
	}
	public void setHandicapCaptcha(Object handicapCaptcha) {
		if (handicapCaptcha != null) {
			this.handicapCaptcha = handicapCaptcha.toString();
		}else{
			this.handicapCaptcha = null;
		}
	}
	public String getHandicapCaptcha() {
		return this.handicapCaptcha ;
	}

	/**
	 * 盘口类别
	 */
	@Column(name="HANDICAP_CATEGORY_")
	private String handicapCategory;
	public void setHandicapCategory(String handicapCategory) {
		this.handicapCategory=handicapCategory;
	}
	public void setHandicapCategory(Object handicapCategory) {
		if (handicapCategory != null) {
			this.handicapCategory = handicapCategory.toString();
		}else{
			this.handicapCategory = null;
		}
	}
	public String getHandicapCategory() {
		return this.handicapCategory ;
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