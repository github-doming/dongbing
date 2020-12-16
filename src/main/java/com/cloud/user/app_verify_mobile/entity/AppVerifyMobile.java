package com.cloud.user.app_verify_mobile.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table app_verify_mobile 
 * APP认证_手机短信验证码 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "app_verify_mobile")
public class AppVerifyMobile implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * APP认证_手机短信验证码主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="APP_VERIFY_MOBILE_ID_")
	private String appVerifyMobileId;
	public void setAppVerifyMobileId(String appVerifyMobileId) {
		this.appVerifyMobileId=appVerifyMobileId;
	}
	public void setAppVerifyMobileId(Object appVerifyMobileId) {
		if (appVerifyMobileId != null) {
			this.appVerifyMobileId = appVerifyMobileId.toString();
		}
	}
	public String getAppVerifyMobileId() {
		return this.appVerifyMobileId ;
	}

	/**
	 * APP会话主键
	 */
	@Column(name="APP_SESSION_ID_")
	private String appSessionId;
	public void setAppSessionId(String appSessionId) {
		this.appSessionId=appSessionId;
	}
	public void setAppSessionId(Object appSessionId) {
		if (appSessionId != null) {
			this.appSessionId = appSessionId.toString();
		}
	}
	public String getAppSessionId() {
		return this.appSessionId ;
	}

	/**
	 * 手机号
	 */
	@Column(name="MOBILE_")
	private String mobile;
	public void setMobile(String mobile) {
		this.mobile=mobile;
	}
	public void setMobile(Object mobile) {
		if (mobile != null) {
			this.mobile = mobile.toString();
		}
	}
	public String getMobile() {
		return this.mobile ;
	}

	/**
	 * 验证码
	 */
	@Column(name="CODE_")
	private String code;
	public void setCode(String code) {
		this.code=code;
	}
	public void setCode(Object code) {
		if (code != null) {
			this.code = code.toString();
		}
	}
	public String getCode() {
		return this.code ;
	}

	/**
	 * 验证码序号
	 */
	@Column(name="CODE_SN_")
	private Long codeSn;
	public void setCodeSn(Long codeSn) {
		this.codeSn=codeSn;
	}
	public void setCodeSn(Object codeSn) {
		if (codeSn != null) {
			if (codeSn instanceof Long) {
				this.codeSn= (Long) codeSn;
			}else if (StringTool.isInteger(codeSn.toString())) {
				this.codeSn = Long.parseLong(codeSn.toString());
			}
		}
	}
	public Long getCodeSn() {
		return this.codeSn ;
	}

	/**
	 * 类型
	 */
	@Column(name="TYPE_")
	private String type;
	public void setType(String type) {
		this.type=type;
	}
	public void setType(Object type) {
		if (type != null) {
			this.type = type.toString();
		}
	}
	public String getType() {
		return this.type ;
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