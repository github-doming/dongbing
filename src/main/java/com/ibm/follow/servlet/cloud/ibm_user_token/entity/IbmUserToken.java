package com.ibm.follow.servlet.cloud.ibm_user_token.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_user_token 
 * 投注用户令牌 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_user_token")
public class IbmUserToken implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 投注用户令牌主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_USER_TOKEN_ID_")
	private String ibmUserTokenId;
	public void setIbmUserTokenId(String ibmUserTokenId) {
		this.ibmUserTokenId=ibmUserTokenId;
	}
	public void setIbmUserTokenId(Object ibmUserTokenId) {
		if (ibmUserTokenId != null) {
			this.ibmUserTokenId = ibmUserTokenId.toString();
		}
	}
	public String getIbmUserTokenId() {
		return this.ibmUserTokenId ;
	}

	/**
	 * APP用户主键
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
	 * 描述
	 */
	@Column(name="VALUE_")
	private String value;
	public void setValue(String value) {
		this.value=value;
	}
	public void setValue(Object value) {
		if (value != null) {
			this.value = value.toString();
		}
	}
	public String getValue() {
		return this.value ;
	}

	/**
	 * 用户类型
	 */
	@Column(name="USER_TYPE_")
	private String userType;
	public void setUserType(String userType) {
		this.userType=userType;
	}
	public void setUserType(Object userType) {
		if (userType != null) {
			this.userType = userType.toString();
		}
	}
	public String getUserType() {
		return this.userType ;
	}

	/**
	 * 渠道类型(APP,PC,邮件，短信，微信)
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
	 * 令牌地址
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
	 * 有效时间
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="PERIOD_TIME_")
	private Date periodTime;
	public void setPeriodTime(Date periodTime) {
		this.periodTime=periodTime;
	}
	public void setPeriodTime(Object periodTime) throws ParseException {
		if (periodTime != null) {
			if (periodTime instanceof Date) {
				this.periodTime= (Date) periodTime;
			}else if (StringTool.isInteger(periodTime.toString())) {
				this.periodTime = new Date(Long.parseLong(periodTime.toString()));
			}else {
				this.periodTime = DateTool.getTime(periodTime.toString());
			}
		}
	}
	public Date getPeriodTime() {
		return this.periodTime ;
	}

	/**
	 * 有效时间数字型
	 */
	@Column(name="PERIOD_TIME_LONG_")
	private Long periodTimeLong;
	public void setPeriodTimeLong(Long periodTimeLong) {
		this.periodTimeLong=periodTimeLong;
	}
	public void setPeriodTimeLong(Object periodTimeLong) {
		if (periodTimeLong != null) {
			if (periodTimeLong instanceof Long) {
				this.periodTimeLong= (Long) periodTimeLong;
			}else if (StringTool.isInteger(periodTimeLong.toString())) {
				this.periodTimeLong = Long.parseLong(periodTimeLong.toString());
			}
		}
	}
	public Long getPeriodTimeLong() {
		return this.periodTimeLong ;
	}

	/**
	 * 租户编码TENANT_CODE_
	 */
	@Column(name="TENANT_CODE_")
	private String tenantCode;
	public void setTenantCode(String tenantCode) {
		this.tenantCode=tenantCode;
	}
	public void setTenantCode(Object tenantCode) {
		if (tenantCode != null) {
			this.tenantCode = tenantCode.toString();
		}
	}
	public String getTenantCode() {
		return this.tenantCode ;
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