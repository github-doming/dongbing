package c.x.platform.admin.gen.gen_double_simple.sys_org.vo;

import java.io.Serializable;
/**
 * The vo class for the database table sys_org 
 * vo类
 */

public class SysOrgGenDoubleSimpleVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//ID_
	private String id;
	public void setId(String id) {
		this.id=id;
	}
	public String getId() {
		return this.id ;
	}
	
//主键
	private String sysOrgId;
	public void setSysOrgId(String sysOrgId) {
		this.sysOrgId=sysOrgId;
	}
	public String getSysOrgId() {
		return this.sysOrgId ;
	}
	
//名称
	private String name;
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
	
//PATH_
	private String path;
	public void setPath(String path) {
		this.path=path;
	}
	public String getPath() {
		return this.path ;
	}
	
//TREE_CODE_
	private String treeCode;
	public void setTreeCode(String treeCode) {
		this.treeCode=treeCode;
	}
	public String getTreeCode() {
		return this.treeCode ;
	}
	
//上一级
	private String parent;
	public void setParent(String parent) {
		this.parent=parent;
	}
	public String getParent() {
		return this.parent ;
	}
	
//排序
	private String sn;
	public void setSn(String sn) {
		this.sn=sn;
	}
	public String getSn() {
		return this.sn ;
	}


}