package com.ibs.plan.module.cloud.ibsp_config.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsp_config 
 * IBSP_中心端配置 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsp_config")
public class IbspConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSP_配置主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSP_CONFIG_ID_")
	private String ibspConfigId;
	public void setIbspConfigId(String ibspConfigId) {
		this.ibspConfigId=ibspConfigId;
	}
	public void setIbspConfigId(Object ibspConfigId) {
		if (ibspConfigId != null) {
			this.ibspConfigId = ibspConfigId.toString();
		}
	}
	public String getIbspConfigId() {
		return this.ibspConfigId ;
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
		}
	}
	public String getConfigValue() {
		return this.configValue ;
	}

	/**
	 * 配置类型
	 */
	@Column(name="CONFIG_TYPE_")
	private String configType;
	public void setConfigType(String configType) {
		this.configType=configType;
	}
	public void setConfigType(Object configType) {
		if (configType != null) {
			this.configType = configType.toString();
		}
	}
	public String getConfigType() {
		return this.configType ;
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