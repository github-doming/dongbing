package all.gen.sys_file.t.entity;

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
 * The persistent class for the database table sys_file 
 * 后台文件表SYS_FILE的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "sys_file")
public class SysFileT implements Serializable {

	private static final long serialVersionUID = 1L;
				
			
//索引
@Column(name="IDX_")
	private Long idx;
	
	public void setIdx(Long idx) {
		this.idx=idx;
	}
	public Long getIdx() {
		return this.idx ;
	}
		@Id
	// 根据底层数据库
	@GeneratedValue(strategy = GenerationType.AUTO)
		
			
//后台文件表主键SYS_FILE_ID_
@Column(name="SYS_FILE_ID_")
	private String sysFileId;
	
	public void setSysFileId(String sysFileId) {
		this.sysFileId=sysFileId;
	}
	public String getSysFileId() {
		return this.sysFileId ;
	}
			
			
//后台文件表名称FILE_NAME_
@Column(name="FILE_NAME_")
	private String fileName;
	
	public void setFileName(String fileName) {
		this.fileName=fileName;
	}
	public String getFileName() {
		return this.fileName ;
	}
			
			
//文件编码或OBJECT_ID,FILE_CODE_
@Column(name="FILE_CODE_")
	private String fileCode;
	
	public void setFileCode(String fileCode) {
		this.fileCode=fileCode;
	}
	public String getFileCode() {
		return this.fileCode ;
	}
			
			
//文件路径FILE_PATH_
@Column(name="FILE_PATH_")
	private String filePath;
	
	public void setFilePath(String filePath) {
		this.filePath=filePath;
	}
	public String getFilePath() {
		return this.filePath ;
	}
			
			
//文件查看地址FILE_URL_
@Column(name="FILE_URL_")
	private String fileUrl;
	
	public void setFileUrl(String fileUrl) {
		this.fileUrl=fileUrl;
	}
	public String getFileUrl() {
		return this.fileUrl ;
	}
			
			
//文件类型FILE_TYPE_
@Column(name="FILE_TYPE_")
	private String fileType;
	
	public void setFileType(String fileType) {
		this.fileType=fileType;
	}
	public String getFileType() {
		return this.fileType ;
	}
			
			
//是否分类ASSORT_
@Column(name="ASSORT_")
	private String assort;
	
	public void setAssort(String assort) {
		this.assort=assort;
	}
	public String getAssort() {
		return this.assort ;
	}
			
			
//功能接口名称FUNCTION_NAME_
@Column(name="FUNCTION_NAME_")
	private String functionName;
	
	public void setFunctionName(String functionName) {
		this.functionName=functionName;
	}
	public String getFunctionName() {
		return this.functionName ;
	}
			
			
//功能编码FUNCTION_CODE_
@Column(name="FUNCTION_CODE_")
	private String functionCode;
	
	public void setFunctionCode(String functionCode) {
		this.functionCode=functionCode;
	}
	public String getFunctionCode() {
		return this.functionCode ;
	}
			
			
//项目名称PROJECT_NAME_
@Column(name="PROJECT_NAME_")
	private String projectName;
	
	public void setProjectName(String projectName) {
		this.projectName=projectName;
	}
	public String getProjectName() {
		return this.projectName ;
	}
			
			
//项目编码PROJECT_CODE_
@Column(name="PROJECT_CODE_")
	private String projectCode;
	
	public void setProjectCode(String projectCode) {
		this.projectCode=projectCode;
	}
	public String getProjectCode() {
		return this.projectCode ;
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