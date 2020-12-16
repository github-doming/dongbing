package all.workflow.actwf.actwf_form_def.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table actwf_form_def 
 * vo类
 */

public class ActwfFormDefTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//工作流表单定义主键ACTWF_FORM_DEF_ID_
	private String actwfFormDefId;
	public void setActwfFormDefId(String actwfFormDefId) {
		this.actwfFormDefId=actwfFormDefId;
	}
	public String getActwfFormDefId() {
		return this.actwfFormDefId ;
	}
	
//英文标识KEY_
	private String key;
	public void setKey(String key) {
		this.key=key;
	}
	public String getKey() {
		return this.key ;
	}
	
//名称
	private String name;
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
	
//相当于表名
	private String tableName;
	public void setTableName(String tableName) {
		this.tableName=tableName;
	}
	public String getTableName() {
		return this.tableName ;
	}
	
//标题TITLE_
	private String title;
	public void setTitle(String title) {
		this.title=title;
	}
	public String getTitle() {
		return this.title ;
	}
	
//内容CONTENT_
	private String content;
	public void setContent(String content) {
		this.content=content;
	}
	public String getContent() {
		return this.content ;
	}
	
//显示版本EDITION_
	private String edition;
	public void setEdition(String edition) {
		this.edition=edition;
	}
	public String getEdition() {
		return this.edition ;
	}
	
//类型
	private String type;
	public void setType(String type) {
		this.type=type;
	}
	public String getType() {
		return this.type ;
	}
	
//查看地址URL_VIEW_
	private String urlView;
	public void setUrlView(String urlView) {
		this.urlView=urlView;
	}
	public String getUrlView() {
		return this.urlView ;
	}
	
//上传地址URL_UPLOAD_
	private String urlUpload;
	public void setUrlUpload(String urlUpload) {
		this.urlUpload=urlUpload;
	}
	public String getUrlUpload() {
		return this.urlUpload ;
	}
	
//下载地址URL_DOWNLOAD_
	private String urlDownload;
	public void setUrlDownload(String urlDownload) {
		this.urlDownload=urlDownload;
	}
	public String getUrlDownload() {
		return this.urlDownload ;
	}
	
//创建者CREATE_USER_
	private String createUser;
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public String getCreateUser() {
		return this.createUser ;
	}
	
//创建时间
	private String createTime;
	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}
	public String getCreateTime() {
		return this.createTime ;
	}
	
//创建时间
	private String createTimeLong;
	public void setCreateTimeLong(String createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public String getCreateTimeLong() {
		return this.createTimeLong ;
	}
	
//更新者UPDATE_USER_
	private String updateUser;
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public String getUpdateUser() {
		return this.updateUser ;
	}
	
//更新时间
	private String updateTime;
	public void setUpdateTime(String updateTime) {
		this.updateTime=updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime ;
	}
	
//更新时间
	private String updateTimeLong;
	public void setUpdateTimeLong(String updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public String getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
	
//次序
	private String sn;
	public void setSn(String sn) {
		this.sn=sn;
	}
	public String getSn() {
		return this.sn ;
	}
	
//DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	private String state;
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
	}
	
//描述
	private String desc;
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}


}