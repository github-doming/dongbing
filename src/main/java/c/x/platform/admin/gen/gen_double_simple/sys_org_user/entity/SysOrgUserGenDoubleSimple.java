package c.x.platform.admin.gen.gen_double_simple.sys_org_user.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import c.a.util.core.annotation.AnnotationEntity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import c.a.util.core.annotation.AnnotationTable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the database table sys_org_user 
 * 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "sys_org_user")
public class SysOrgUserGenDoubleSimple implements Serializable {

	private static final long serialVersionUID = 1L;
				
			
//ID_
@Column(name="ID_")
	private Long id;
	
	public void setId(Long id) {
		this.id=id;
	}
	public Long getId() {
		return this.id ;
	}
		//自己制定ID
	// @Id
	// 根据底层数据库
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
		
			
//SYS_ORG_USER_ID_
@Column(name="SYS_ORG_USER_ID_")
	private String sysOrgUserId;
	
	public void setSysOrgUserId(String sysOrgUserId) {
		this.sysOrgUserId=sysOrgUserId;
	}
	public String getSysOrgUserId() {
		return this.sysOrgUserId ;
	}
			
			
//SYS_ORG_ID_
@Column(name="SYS_ORG_ID_")
	private String sysOrgId;
	
	public void setSysOrgId(String sysOrgId) {
		this.sysOrgId=sysOrgId;
	}
	public String getSysOrgId() {
		return this.sysOrgId ;
	}
			
			
//SYS_USER_ID_
@Column(name="SYS_USER_ID_")
	private String sysUserId;
	
	public void setSysUserId(String sysUserId) {
		this.sysUserId=sysUserId;
	}
	public String getSysUserId() {
		return this.sysUserId ;
	}

	private String tableName;
	/*
	 * 不映射
	 * 
	 * @return
	 */
	@Transient
	public String getTableName() {
		return tableName;
	}

	public void setTable_name(String tableName) {
		this.tableName = tableName;
	}

}