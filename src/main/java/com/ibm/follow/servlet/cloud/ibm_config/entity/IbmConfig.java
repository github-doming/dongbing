package com.ibm.follow.servlet.cloud.ibm_config.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_config 
 * IBM_中心端配置的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_config")
public class IbmConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_中心端配置主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_CONFIG_ID_")
	private String ibmConfigId;
	public void setIbmConfigId(String ibmConfigId) {
		this.ibmConfigId=ibmConfigId;
	}
	public void setIbmConfigId(Object ibmConfigId) {
		if (ibmConfigId != null) {
			this.ibmConfigId = ibmConfigId.toString();
		}else{
			this.ibmConfigId = null;
		}
	}
	public String getIbmConfigId() {
		return this.ibmConfigId ;
	}

	/**
	 * 中心端配置KEY
	 */
	@Column(name="CONFIG_KEY_")
	private String configKey;
	public void setConfigKey(String configKey) {
		this.configKey=configKey;
	}
	public void setConfigKey(Object configKey) {
		if (configKey != null) {
			this.configKey = configKey.toString();
		}else{
			this.configKey = null;
		}
	}
	public String getConfigKey() {
		return this.configKey ;
	}

	/**
	 * 中心端配置VALUE
	 */
	@Column(name="CONFIG_VALUE_")
	private String configValue;
	public void setConfigValue(String configValue) {
		this.configValue=configValue;
	}
	public void setConfigValue(Object configValue) {
		if (configValue != null) {
			this.configValue = configValue.toString();
		}else{
			this.configValue = null;
		}
	}
	public String getConfigValue() {
		return this.configValue ;
	}

	/**
	 * 创建者CREATE_USER_
	 */
	@Column(name="CREATE_USER_")
	private String createUser;
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public void setCreateUser(Object createUser) {
		if (createUser != null) {
			this.createUser = createUser.toString();
		}else{
			this.createUser = null;
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
	 * 更新者UPDATE_USER_
	 */
	@Column(name="UPDATE_USER_")
	private String updateUser;
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public void setUpdateUser(Object updateUser) {
		if (updateUser != null) {
			this.updateUser = updateUser.toString();
		}else{
			this.updateUser = null;
		}
	}
	public String getUpdateUser() {
		return this.updateUser ;
	}

	/**
	 * 更新内容
	 */
	@Column(name="UPDATE_TEXT_")
	private String updateText;
	public void setUpdateText(String updateText) {
		this.updateText=updateText;
	}
	public void setUpdateText(Object updateText) {
		if (updateText != null) {
			this.updateText = updateText.toString();
		}else{
			this.updateText = null;
		}
	}
	public String getUpdateText() {
		return this.updateText ;
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