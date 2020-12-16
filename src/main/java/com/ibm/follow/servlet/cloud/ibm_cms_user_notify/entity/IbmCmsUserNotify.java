package com.ibm.follow.servlet.cloud.ibm_cms_user_notify.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_cms_user_notify 
 * IBM用户消息列表 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_cms_user_notify")
public class IbmCmsUserNotify implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM用户消息主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_CMS_USER_NOTIFY_ID_")
	private String ibmCmsUserNotifyId;
	public void setIbmCmsUserNotifyId(String ibmCmsUserNotifyId) {
		this.ibmCmsUserNotifyId=ibmCmsUserNotifyId;
	}
	public void setIbmCmsUserNotifyId(Object ibmCmsUserNotifyId) {
		if (ibmCmsUserNotifyId != null) {
			this.ibmCmsUserNotifyId = ibmCmsUserNotifyId.toString();
		}
	}
	public String getIbmCmsUserNotifyId() {
		return this.ibmCmsUserNotifyId ;
	}

	/**
	 * 消息主键
	 */
	@Column(name="CMS_NOTIFY_ID_")
	private String cmsNotifyId;
	public void setCmsNotifyId(String cmsNotifyId) {
		this.cmsNotifyId=cmsNotifyId;
	}
	public void setCmsNotifyId(Object cmsNotifyId) {
		if (cmsNotifyId != null) {
			this.cmsNotifyId = cmsNotifyId.toString();
		}
	}
	public String getCmsNotifyId() {
		return this.cmsNotifyId ;
	}

	/**
	 * 公告编码
	 */
	@Column(name="NOTIFY_CODE_")
	private String notifyCode;
	public void setNotifyCode(String notifyCode) {
		this.notifyCode=notifyCode;
	}
	public void setNotifyCode(Object notifyCode) {
		if (notifyCode != null) {
			this.notifyCode = notifyCode.toString();
		}
	}
	public String getNotifyCode() {
		return this.notifyCode ;
	}


	/**
	 * 用户ID
	 */
	@Column(name="USER_ID_")
	private String userId;
	public void setUserId(String userId) {
		this.userId=userId;
	}
	public void setUserId(Object userId) {
		if (userId != null) {
			this.userId = userId.toString();
		}
	}
	public String getUserId() {
		return this.userId ;
	}

	/**
	 * 接受者用户名
	 */
	@Column(name="USER_NAME_")
	private String userName;
	public void setUserName(String userName) {
		this.userName=userName;
	}
	public void setUserName(Object userName) {
		if (userName != null) {
			this.userName = userName.toString();
		}
	}
	public String getUserName() {
		return this.userName ;
	}


	/**
	 * 是否阅读
	 */
	@Column(name="IS_READ_")
	private Boolean isRead;
	public void setIsRead(Boolean isRead) {
		this.isRead=isRead;
	}
	public Boolean getIsRead() {
		return this.isRead ;
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