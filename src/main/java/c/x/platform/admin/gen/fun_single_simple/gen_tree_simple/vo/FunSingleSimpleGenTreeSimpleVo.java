package c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.vo;

import java.io.Serializable;
/**
 * The vo class for the database table fun_single_simple 
 * vo类
 */

public class FunSingleSimpleGenTreeSimpleVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//FUN_SINGLE_SIMPLE_ID_
	private String funSingleSimpleId;
	public void setFunSingleSimpleId(String funSingleSimpleId) {
		this.funSingleSimpleId=funSingleSimpleId;
	}
	public String getFunSingleSimpleId() {
		return this.funSingleSimpleId ;
	}
	
//主键
	private String id;
	public void setId(String id) {
		this.id=id;
	}
	public String getId() {
		return this.id ;
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
	
//URL_
	private String url;
	public void setUrl(String url) {
		this.url=url;
	}
	public String getUrl() {
		return this.url ;
	}
	
//上一级
	private String parent;
	public void setParent(String parent) {
		this.parent=parent;
	}
	public String getParent() {
		return this.parent ;
	}
	
//PIC_OPEN_
	private String picOpen;
	public void setPicOpen(String picOpen) {
		this.picOpen=picOpen;
	}
	public String getPicOpen() {
		return this.picOpen ;
	}
	
//PIC_CLOSE_
	private String picClose;
	public void setPicClose(String picClose) {
		this.picClose=picClose;
	}
	public String getPicClose() {
		return this.picClose ;
	}
	
//PIC_
	private String pic;
	public void setPic(String pic) {
		this.pic=pic;
	}
	public String getPic() {
		return this.pic ;
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