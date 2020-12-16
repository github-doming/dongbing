package all.gen.sys_bytes.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table sys_bytes 
 * vo类
 */

public class SysBytesTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//后台字节表主键SYS_BYTES_ID_
	private String sysBytesId;
	public void setSysBytesId(String sysBytesId) {
		this.sysBytesId=sysBytesId;
	}
	public String getSysBytesId() {
		return this.sysBytesId ;
	}
	
//字节内容BYTES_CONTENT_
	private String bytesContent;
	public void setBytesContent(String bytesContent) {
		this.bytesContent=bytesContent;
	}
	public String getBytesContent() {
		return this.bytesContent ;
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