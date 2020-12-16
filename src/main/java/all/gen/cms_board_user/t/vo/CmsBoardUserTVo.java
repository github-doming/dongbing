package all.gen.cms_board_user.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table cms_board_user 
 * vo类
 */

public class CmsBoardUserTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//CMS版块与APP用户(权限)主键CMS_BOARD_USER_ID_
	private String cmsBoardUserId;
	public void setCmsBoardUserId(String cmsBoardUserId) {
		this.cmsBoardUserId=cmsBoardUserId;
	}
	public String getCmsBoardUserId() {
		return this.cmsBoardUserId ;
	}
	
//用户主键APP_USER_ID_
	private String appUserId;
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public String getAppUserId() {
		return this.appUserId ;
	}
	
//CMS_版块主健CMS_BOARD_ID_
	private String cmsBoardId;
	public void setCmsBoardId(String cmsBoardId) {
		this.cmsBoardId=cmsBoardId;
	}
	public String getCmsBoardId() {
		return this.cmsBoardId ;
	}
	
//相当于表名
	private String tableName;
	public void setTableName(String tableName) {
		this.tableName=tableName;
	}
	public String getTableName() {
		return this.tableName ;
	}
	
//相当于表的ID
	private String tableId;
	public void setTableId(String tableId) {
		this.tableId=tableId;
	}
	public String getTableId() {
		return this.tableId ;
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