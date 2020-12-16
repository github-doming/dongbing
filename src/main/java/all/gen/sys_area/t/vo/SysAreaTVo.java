package all.gen.sys_area.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table sys_area 
 * vo类
 */

public class SysAreaTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//主键
	private String id;
	public void setId(String id) {
		this.id=id;
	}
	public String getId() {
		return this.id ;
	}
	
//SYS_AREA_ID_
	private String sysAreaId;
	public void setSysAreaId(String sysAreaId) {
		this.sysAreaId=sysAreaId;
	}
	public String getSysAreaId() {
		return this.sysAreaId ;
	}
	
//上一级
	private String parent;
	public void setParent(String parent) {
		this.parent=parent;
	}
	public String getParent() {
		return this.parent ;
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
	
//树编码
	private String treeCode;
	public void setTreeCode(String treeCode) {
		this.treeCode=treeCode;
	}
	public String getTreeCode() {
		return this.treeCode ;
	}
	
//次序
	private String sn;
	public void setSn(String sn) {
		this.sn=sn;
	}
	public String getSn() {
		return this.sn ;
	}
	
//是否显示
	private String state;
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
	}
	
//行政编码
	private String code;
	public void setCode(String code) {
		this.code=code;
	}
	public String getCode() {
		return this.code ;
	}
	
//描述
	private String desc;
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}
	
//CURRENT_TIMESTAMP(3)
	private String createTime;
	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}
	public String getCreateTime() {
		return this.createTime ;
	}
	
//CREATE_TIME_LONG_
	private String createTimeLong;
	public void setCreateTimeLong(String createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public String getCreateTimeLong() {
		return this.createTimeLong ;
	}
	
//UPDATE_TIME_
	private String updateTime;
	public void setUpdateTime(String updateTime) {
		this.updateTime=updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime ;
	}
	
//UPDATE_TIME_LONG_
	private String updateTimeLong;
	public void setUpdateTimeLong(String updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public String getUpdateTimeLong() {
		return this.updateTimeLong ;
	}


}