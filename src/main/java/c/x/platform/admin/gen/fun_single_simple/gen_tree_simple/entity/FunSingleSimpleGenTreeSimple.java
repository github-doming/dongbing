package c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.entity;

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
 * The persistent class for the database table fun_single_simple 
 * 简单功能的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "fun_single_simple")
public class FunSingleSimpleGenTreeSimple implements Serializable {

	private static final long serialVersionUID = 1L;
			@Id
	// 根据底层数据库
	@GeneratedValue(strategy = GenerationType.AUTO)
		
			
//FUN_SINGLE_SIMPLE_ID_
@Column(name="FUN_SINGLE_SIMPLE_ID_")
	private String funSingleSimpleId;
	
	public void setFunSingleSimpleId(String funSingleSimpleId) {
		this.funSingleSimpleId=funSingleSimpleId;
	}
	public String getFunSingleSimpleId() {
		return this.funSingleSimpleId ;
	}
			
			
//主键
@Column(name="ID_")
	private Long id;
	
	public void setId(Long id) {
		this.id=id;
	}
	public Long getId() {
		return this.id ;
	}
			
			
//名称
@Column(name="NAME_")
	private String name;
	
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
			
			
//PATH_
@Column(name="PATH_")
	private String path;
	
	public void setPath(String path) {
		this.path=path;
	}
	public String getPath() {
		return this.path ;
	}
			
			
//树编码
@Column(name="TREE_CODE_")
	private String treeCode;
	
	public void setTreeCode(String treeCode) {
		this.treeCode=treeCode;
	}
	public String getTreeCode() {
		return this.treeCode ;
	}
			
			
//URL_
@Column(name="URL_")
	private String url;
	
	public void setUrl(String url) {
		this.url=url;
	}
	public String getUrl() {
		return this.url ;
	}
			
			
//上一级
@Column(name="PARENT_")
	private String parent;
	
	public void setParent(String parent) {
		this.parent=parent;
	}
	public String getParent() {
		return this.parent ;
	}
			
			
//PIC_OPEN_
@Column(name="PIC_OPEN_")
	private String picOpen;
	
	public void setPicOpen(String picOpen) {
		this.picOpen=picOpen;
	}
	public String getPicOpen() {
		return this.picOpen ;
	}
			
			
//PIC_CLOSE_
@Column(name="PIC_CLOSE_")
	private String picClose;
	
	public void setPicClose(String picClose) {
		this.picClose=picClose;
	}
	public String getPicClose() {
		return this.picClose ;
	}
			
			
//PIC_
@Column(name="PIC_")
	private String pic;
	
	public void setPic(String pic) {
		this.pic=pic;
	}
	public String getPic() {
		return this.pic ;
	}
			
			
//排序
@Column(name="SN_")
	private Integer sn;
	
	public void setSn(Integer sn) {
		this.sn=sn;
	}
	public Integer getSn() {
		return this.sn ;
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