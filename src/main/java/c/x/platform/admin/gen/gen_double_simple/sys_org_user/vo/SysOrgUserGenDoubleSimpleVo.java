package c.x.platform.admin.gen.gen_double_simple.sys_org_user.vo;

import java.io.Serializable;
/**
 * The vo class for the database table sys_org_user 
 * vo类
 */

public class SysOrgUserGenDoubleSimpleVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//ID_
	private String id;
	public void setId(String id) {
		this.id=id;
	}
	public String getId() {
		return this.id ;
	}
	
//SYS_ORG_USER_ID_
	private String sysOrgUserId;
	public void setSysOrgUserId(String sysOrgUserId) {
		this.sysOrgUserId=sysOrgUserId;
	}
	public String getSysOrgUserId() {
		return this.sysOrgUserId ;
	}
	
//SYS_ORG_ID_
	private String sysOrgId;
	public void setSysOrgId(String sysOrgId) {
		this.sysOrgId=sysOrgId;
	}
	public String getSysOrgId() {
		return this.sysOrgId ;
	}
	
//SYS_USER_ID_
	private String sysUserId;
	public void setSysUserId(String sysUserId) {
		this.sysUserId=sysUserId;
	}
	public String getSysUserId() {
		return this.sysUserId ;
	}


}