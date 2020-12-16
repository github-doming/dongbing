package com.ibm.follow.servlet.cloud.ibm_manage_role.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_manage_role
 * IBM_角色主键
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_manage_role")
public class IbmManageRole implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_角色主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_MANAGE_ROLE_ID_")
	private String ibmManageRoleId;
	public void setIbmManageRoleId(String ibmManageRoleId) {
		this.ibmManageRoleId=ibmManageRoleId;
	}
	public void setIbmManageRoleId(Object ibmManageRoleId) {
		if (ibmManageRoleId != null) {
			this.ibmManageRoleId = ibmManageRoleId.toString();
		}
	}
	public String getIbmManageRoleId() {
		return this.ibmManageRoleId ;
	}

	/**
	 * 角色名
	 */
	@Column(name="ROLE_NAME_")
	private String roleName;
	public void setRoleName(String roleName) {
		this.roleName=roleName;
	}
	public void setRoleName(Object roleName) {
		if (roleName != null) {
			this.roleName = roleName.toString();
		}
	}
	public String getRoleName() {
		return this.roleName ;
	}

	/**
	 * 角色CODE
	 */
	@Column(name="ROLE_CODE_")
	private String roleCode;
	public void setRoleCode(String roleCode) {
		this.roleCode=roleCode;
	}
	public void setRoleCode(Object roleCode) {
		if (roleCode != null) {
			this.roleCode = roleCode.toString();
		}
	}
	public String getRoleCode() {
		return this.roleCode ;
	}

	/**
	 * 角色等级
	 */
	@Column(name="ROLE_LEVEL_")
	private Integer roleLevel;
	public void setRoleLevel(Integer roleLevel) {
		this.roleLevel=roleLevel;
	}
	public void setRoleLevel(Object roleLevel) {
		if (roleLevel != null) {
			if (roleLevel instanceof Integer) {
				this.roleLevel= (Integer) roleLevel;
			}else if (StringTool.isInteger(roleLevel.toString())) {
				this.roleLevel = Integer.parseInt(roleLevel.toString());
			}
		}
	}
	public Integer getRoleLevel() {
		return this.roleLevel ;
	}

    /**
     * 角色类型
     */
    @Column(name="ROLE_TYPE_")
    private String roleType;
    public void setRoleType(String roleType) {
        this.roleType=roleType;
    }
    public void setRoleType(Object roleType) {
        if (roleType != null) {
            this.roleType = roleType.toString();
        }
    }
    public String getRoleType() {
        return this.roleType ;
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