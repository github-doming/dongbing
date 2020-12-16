package all.sys_admin.sys.dict.sys_dict.vo;

import java.io.Serializable;
/**
 * The vo class for the database table sys_dict 
 * vo类
 */

public class SysDictTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//后台数据字典主键SYS_DICT_ID_
	private String sysDictId;
	public void setSysDictId(String sysDictId) {
		this.sysDictId=sysDictId;
	}
	public String getSysDictId() {
		return this.sysDictId ;
	}
	
//后台数据字典表名称SYS_DICT_NAME_
	private String sysDictName;
	public void setSysDictName(String sysDictName) {
		this.sysDictName=sysDictName;
	}
	public String getSysDictName() {
		return this.sysDictName ;
	}
	
//后台数据字典表编码SYS_DICT_CODE_
	private String sysDictCode;
	public void setSysDictCode(String sysDictCode) {
		this.sysDictCode=sysDictCode;
	}
	public String getSysDictCode() {
		return this.sysDictCode ;
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
	
//相当于表的列名
	private String tableColumn;
	public void setTableColumn(String tableColumn) {
		this.tableColumn=tableColumn;
	}
	public String getTableColumn() {
		return this.tableColumn ;
	}
	
//表名+表的ID+表的列，用点号来分割如jdbc.local.dburl=jdbc:mysql://39.108.124.247:3306/wsd
	private String tableKey;
	public void setTableKey(String tableKey) {
		this.tableKey=tableKey;
	}
	public String getTableKey() {
		return this.tableKey ;
	}
	
//表的VALUE_
	private String tableValue;
	public void setTableValue(String tableValue) {
		this.tableValue=tableValue;
	}
	public String getTableValue() {
		return this.tableValue ;
	}
	
//虚拟表的数据类型DATA_TYPE_
	private String dataType;
	public void setDataType(String dataType) {
		this.dataType=dataType;
	}
	public String getDataType() {
		return this.dataType ;
	}
	
//虚拟表的列类型COLUMN_TYPE_
	private String columnType;
	public void setColumnType(String columnType) {
		this.columnType=columnType;
	}
	public String getColumnType() {
		return this.columnType ;
	}
	
//虚拟表的列的分类COLUMN_ASSORT_
	private String columnAssort;
	public void setColumnAssort(String columnAssort) {
		this.columnAssort=columnAssort;
	}
	public String getColumnAssort() {
		return this.columnAssort ;
	}
	
//次序
	private String sn;
	public void setSn(String sn) {
		this.sn=sn;
	}
	public String getSn() {
		return this.sn ;
	}
	
//中文标识KEY_CN_
	private String keyCn;
	public void setKeyCn(String keyCn) {
		this.keyCn=keyCn;
	}
	public String getKeyCn() {
		return this.keyCn ;
	}
	
//中文值VALUE_CN_
	private String valueCn;
	public void setValueCn(String valueCn) {
		this.valueCn=valueCn;
	}
	public String getValueCn() {
		return this.valueCn ;
	}
	
//是否默认值
	private String def;
	public void setDef(String def) {
		this.def=def;
	}
	public String getDef() {
		return this.def ;
	}
	
//是否显示SHOW_
	private String show;
	public void setShow(String show) {
		this.show=show;
	}
	public String getShow() {
		return this.show ;
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