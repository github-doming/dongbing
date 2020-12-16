package com.ibm.follow.servlet.cloud.ibm_cms_notify.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_cms_notify 
 * IBM消息列表 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_cms_notify")
public class IbmCmsNotify implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM消息主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_CMS_NOTIFY_ID_")
	private String ibmCmsNotifyId;
	public void setIbmCmsNotifyId(String ibmCmsNotifyId) {
		this.ibmCmsNotifyId=ibmCmsNotifyId;
	}
	public void setIbmCmsNotifyId(Object ibmCmsNotifyId) {
		if (ibmCmsNotifyId != null) {
			this.ibmCmsNotifyId = ibmCmsNotifyId.toString();
		}
	}
	public String getIbmCmsNotifyId() {
		return this.ibmCmsNotifyId ;
	}

	/**
	 * 消息类型- 提醒-公告-用户消息
	 */
	@Column(name="NOTIFY_TYPE_")
	private String notifyType;
	public void setNotifyType(String notifyType) {
		this.notifyType=notifyType;
	}
	public void setNotifyType(Object notifyType) {
		if (notifyType != null) {
			this.notifyType = notifyType.toString();
		}
	}
	public String getNotifyType() {
		return this.notifyType ;
	}

	/**
	 * 消息标题
	 */
	@Column(name="NOTIFY_TITLE_")
	private String notifyTitle;
	public void setNotifyTitle(String notifyTitle) {
		this.notifyTitle=notifyTitle;
	}
	public void setNotifyTitle(Object notifyTitle) {
		if (notifyTitle != null) {
			this.notifyTitle = notifyTitle.toString();
		}
	}
	public String getNotifyTitle() {
		return this.notifyTitle ;
	}

	/**
	 * 消息内容
	 */
	@Column(name="NOTIFY_CONTENT_")
	private String notifyContent;
	public void setNotifyContent(String notifyContent) {
		this.notifyContent=notifyContent;
	}
	public void setNotifyContent(Object notifyContent) {
		if (notifyContent != null) {
			this.notifyContent = notifyContent.toString();
		}
	}
	public String getNotifyContent() {
		return this.notifyContent ;
	}

	/**
	 * 消息级别
	 */
	@Column(name="NOTIFY_LEVEL_")
	private Integer notifyLevel;
	public void setNotifyLevel(Integer notifyLevel) {
		this.notifyLevel=notifyLevel;
	}
	public Integer getNotifyLevel() {
		return this.notifyLevel ;
	}

	/**
	 * 消息位置-消息显示的位置（弹窗还是轮播）
	 */
	@Column(name="NOTIFY_SITE_")
	private String notifySite;
	public void setNotifySite(String notifySite) {
		this.notifySite=notifySite;
	}
	public void setNotifySite(Object notifySite) {
		if (notifySite != null) {
			this.notifySite = notifySite.toString();
		}
	}
	public String getNotifySite() {
		return this.notifySite ;
	}

	/**
	 * 发布人
	 */
	@Column(name="CREATE_USER_")
	private String createUser;
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public void setCreateUser(Object createUser) {
		if (createUser != null) {
			this.createUser = createUser.toString();
		}
	}
	public String getCreateUser() {
		return this.createUser ;
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