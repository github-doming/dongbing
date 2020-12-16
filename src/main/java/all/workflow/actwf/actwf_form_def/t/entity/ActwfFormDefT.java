package all.workflow.actwf.actwf_form_def.t.entity;

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
 * The persistent class for the database table actwf_form_def 
 * 工作流表单定义WF_FORM_DEF的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "actwf_form_def")
public class ActwfFormDefT implements Serializable {

	private static final long serialVersionUID = 1L;
			@Id
	// 根据底层数据库
	@GeneratedValue(strategy = GenerationType.AUTO)
		
			
//工作流表单定义主键ACTWF_FORM_DEF_ID_
@Column(name="ACTWF_FORM_DEF_ID_")
	private String actwfFormDefId;
	
	public void setActwfFormDefId(String actwfFormDefId) {
		this.actwfFormDefId=actwfFormDefId;
	}
	public String getActwfFormDefId() {
		return this.actwfFormDefId ;
	}
			
			
//英文标识KEY_
@Column(name="KEY_")
	private String key;
	
	public void setKey(String key) {
		this.key=key;
	}
	public String getKey() {
		return this.key ;
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
			
			
//相当于表名
@Column(name="TABLE_NAME_")
	private String tableName;
	
	public void setTableName(String tableName) {
		this.tableName=tableName;
	}
	public String getTableName() {
		return this.tableName ;
	}
			
			
//标题TITLE_
@Column(name="TITLE_")
	private String title;
	
	public void setTitle(String title) {
		this.title=title;
	}
	public String getTitle() {
		return this.title ;
	}
			
			
//内容CONTENT_
@Column(name="CONTENT_")
	private String content;
	
	public void setContent(String content) {
		this.content=content;
	}
	public String getContent() {
		return this.content ;
	}
			
			
//显示版本EDITION_
@Column(name="EDITION_")
	private String edition;
	
	public void setEdition(String edition) {
		this.edition=edition;
	}
	public String getEdition() {
		return this.edition ;
	}
			
			
//类型
@Column(name="TYPE_")
	private String type;
	
	public void setType(String type) {
		this.type=type;
	}
	public String getType() {
		return this.type ;
	}
			
			
//查看地址URL_VIEW_
@Column(name="URL_VIEW_")
	private String urlView;
	
	public void setUrlView(String urlView) {
		this.urlView=urlView;
	}
	public String getUrlView() {
		return this.urlView ;
	}
			
			
//上传地址URL_UPLOAD_
@Column(name="URL_UPLOAD_")
	private String urlUpload;
	
	public void setUrlUpload(String urlUpload) {
		this.urlUpload=urlUpload;
	}
	public String getUrlUpload() {
		return this.urlUpload ;
	}
			
			
//下载地址URL_DOWNLOAD_
@Column(name="URL_DOWNLOAD_")
	private String urlDownload;
	
	public void setUrlDownload(String urlDownload) {
		this.urlDownload=urlDownload;
	}
	public String getUrlDownload() {
		return this.urlDownload ;
	}
			
			
//创建者CREATE_USER_
@Column(name="CREATE_USER_")
	private String createUser;
	
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public String getCreateUser() {
		return this.createUser ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//创建时间
@Column(name="CREATE_TIME_")
	private Date createTime;
	
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
	}
	public Date getCreateTime() {
		return this.createTime ;
	}
			
			
//创建时间
@Column(name="CREATE_TIME_LONG_")
	private Long createTimeLong;
	
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong ;
	}
			
			
//更新者UPDATE_USER_
@Column(name="UPDATE_USER_")
	private String updateUser;
	
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public String getUpdateUser() {
		return this.updateUser ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//更新时间
@Column(name="UPDATE_TIME_")
	private Date updateTime;
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime=updateTime;
	}
	public Date getUpdateTime() {
		return this.updateTime ;
	}
			
			
//更新时间
@Column(name="UPDATE_TIME_LONG_")
	private Long updateTimeLong;
	
	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
			
			
//次序
@Column(name="SN_")
	private Integer sn;
	
	public void setSn(Integer sn) {
		this.sn=sn;
	}
	public Integer getSn() {
		return this.sn ;
	}
			
			
//DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
@Column(name="STATE_")
	private String state;
	
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
	}
			
			
//描述
@Column(name="DESC_")
	private String desc;
	
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}

	private String tableNameMy;
	/*
	 * 不映射
	 * 
	 * @return
	 */
	@Transient
	public String getTableNameMy() {
		return tableNameMy;
	}

	public void setTableNameMy(String tableNameMy) {
		this.tableNameMy = tableNameMy;
	}

}