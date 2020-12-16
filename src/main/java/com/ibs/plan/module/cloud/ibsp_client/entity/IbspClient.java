package com.ibs.plan.module.cloud.ibsp_client.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsp_client 
 * IBSP_客户端 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsp_client")
public class IbspClient implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSP_客户端主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSP_CLIENT_ID_")
	private String ibspClientId;
	public void setIbspClientId(String ibspClientId) {
		this.ibspClientId=ibspClientId;
	}
	public void setIbspClientId(Object ibspClientId) {
		if (ibspClientId != null) {
			this.ibspClientId = ibspClientId.toString();
		}
	}
	public String getIbspClientId() {
		return this.ibspClientId ;
	}

	/**
	 * 注册IP主键
	 */
	@Column(name="REGISTER_IP_ID_")
	private String registerIpId;
	public void setRegisterIpId(String registerIpId) {
		this.registerIpId=registerIpId;
	}
	public void setRegisterIpId(Object registerIpId) {
		if (registerIpId != null) {
			this.registerIpId = registerIpId.toString();
		}
	}
	public String getRegisterIpId() {
		return this.registerIpId ;
	}

	/**
	 * REGISTER_IP_
	 */
	@Column(name="REGISTER_IP_")
	private String registerIp;
	public void setRegisterIp(String registerIp) {
		this.registerIp=registerIp;
	}
	public void setRegisterIp(Object registerIp) {
		if (registerIp != null) {
			this.registerIp = registerIp.toString();
		}
	}
	public String getRegisterIp() {
		return this.registerIp ;
	}

	/**
	 * 客户端编码
	 */
	@Column(name="CLIENT_CODE_")
	private String clientCode;
	public void setClientCode(String clientCode) {
		this.clientCode=clientCode;
	}
	public void setClientCode(Object clientCode) {
		if (clientCode != null) {
			this.clientCode = clientCode.toString();
		}
	}
	public String getClientCode() {
		return this.clientCode ;
	}

	/**
	 * 客户端类型
	 */
	@Column(name="CLIENT_TYPE_")
	private String clientType;
	public void setClientType(String clientType) {
		this.clientType=clientType;
	}
	public void setClientType(Object clientType) {
		if (clientType != null) {
			this.clientType = clientType.toString();
		}
	}
	public String getClientType() {
		return this.clientType ;
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