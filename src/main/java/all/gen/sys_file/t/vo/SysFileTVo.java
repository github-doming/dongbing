package all.gen.sys_file.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table sys_file 
 * vo类
 */

public class SysFileTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//后台文件表主键SYS_FILE_ID_
	private String sysFileId;
	public void setSysFileId(String sysFileId) {
		this.sysFileId=sysFileId;
	}
	public String getSysFileId() {
		return this.sysFileId ;
	}
	
//后台文件表名称FILE_NAME_
	private String fileName;
	public void setFileName(String fileName) {
		this.fileName=fileName;
	}
	public String getFileName() {
		return this.fileName ;
	}
	
//文件编码或OBJECT_ID,FILE_CODE_
	private String fileCode;
	public void setFileCode(String fileCode) {
		this.fileCode=fileCode;
	}
	public String getFileCode() {
		return this.fileCode ;
	}
	
//文件路径FILE_PATH_
	private String filePath;
	public void setFilePath(String filePath) {
		this.filePath=filePath;
	}
	public String getFilePath() {
		return this.filePath ;
	}
	
//文件查看地址FILE_URL_
	private String fileUrl;
	public void setFileUrl(String fileUrl) {
		this.fileUrl=fileUrl;
	}
	public String getFileUrl() {
		return this.fileUrl ;
	}
	
//文件类型FILE_TYPE_
	private String fileType;
	public void setFileType(String fileType) {
		this.fileType=fileType;
	}
	public String getFileType() {
		return this.fileType ;
	}
	
//是否分类ASSORT_
	private String assort;
	public void setAssort(String assort) {
		this.assort=assort;
	}
	public String getAssort() {
		return this.assort ;
	}
	
//功能接口名称FUNCTION_NAME_
	private String functionName;
	public void setFunctionName(String functionName) {
		this.functionName=functionName;
	}
	public String getFunctionName() {
		return this.functionName ;
	}
	
//功能编码FUNCTION_CODE_
	private String functionCode;
	public void setFunctionCode(String functionCode) {
		this.functionCode=functionCode;
	}
	public String getFunctionCode() {
		return this.functionCode ;
	}
	
//项目名称PROJECT_NAME_
	private String projectName;
	public void setProjectName(String projectName) {
		this.projectName=projectName;
	}
	public String getProjectName() {
		return this.projectName ;
	}
	
//项目编码PROJECT_CODE_
	private String projectCode;
	public void setProjectCode(String projectCode) {
		this.projectCode=projectCode;
	}
	public String getProjectCode() {
		return this.projectCode ;
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