package com.cloud.user.user_login_log.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table user_login_log 
 * 用户登录日志 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "user_login_log")
public class UserLoginLog implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 充值卡操作日志主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="LOGIN_LOG_ID_")
	private Long loginLogId;
	public void setLoginLogId(Long loginLogId) {
		this.loginLogId=loginLogId;
	}
	public void setLoginLogId(Object loginLogId) {
		if (loginLogId != null) {
			if (loginLogId instanceof Long) {
				this.loginLogId= (Long) loginLogId;
			}else if (StringTool.isInteger(loginLogId.toString())) {
				this.loginLogId = Long.parseLong(loginLogId.toString());
			}
		}
	}
	public Long getLoginLogId() {
		return this.loginLogId ;
	}

	/**
	 * 用户主键
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
	 * 用户名称
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
	 * 用户路径
	 */
	@Column(name="USER_PATH_")
	private String userPath;
	public void setUserPath(String userPath) {
		this.userPath=userPath;
	}
	public void setUserPath(Object userPath) {
		if (userPath != null) {
			this.userPath = userPath.toString();
		}
	}
	public String getUserPath() {
		return this.userPath ;
	}

	/**
	 * 操作内容
	 */
	@Column(name="OPERT_CONTENT_")
	private String opertContent;
	public void setOpertContent(String opertContent) {
		this.opertContent=opertContent;
	}
	public void setOpertContent(Object opertContent) {
		if (opertContent != null) {
			this.opertContent = opertContent.toString();
		}
	}
	public String getOpertContent() {
		return this.opertContent ;
	}

	/**
	 * 平台类型
	 */
	@Column(name="CHANNEL_TYPE_")
	private String channelType;
	public void setChannelType(String channelType) {
		this.channelType=channelType;
	}
	public void setChannelType(Object channelType) {
		if (channelType != null) {
			this.channelType = channelType.toString();
		}
	}
	public String getChannelType() {
		return this.channelType ;
	}

	/**
	 * IP地址
	 */
	@Column(name="IP_")
	private String ip;
	public void setIp(String ip) {
		this.ip=ip;
	}
	public void setIp(Object ip) {
		if (ip != null) {
			this.ip = ip.toString();
		}
	}
	public String getIp() {
		return this.ip ;
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