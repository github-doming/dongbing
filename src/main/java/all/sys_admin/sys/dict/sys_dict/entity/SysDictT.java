package all.sys_admin.sys.dict.sys_dict.entity;

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
 * The persistent class for the database table sys_dict 
 * 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "sys_dict")
public class SysDictT implements Serializable {

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
		//自己制定ID
	// @Id
	// 根据底层数据库
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
		
			
//后台数据字典主键SYS_DICT_ID_
@Column(name="SYS_DICT_ID_")
	private String sysDictId;
	
	public void setSysDictId(String sysDictId) {
		this.sysDictId=sysDictId;
	}
	public String getSysDictId() {
		return this.sysDictId ;
	}
			
			
//后台数据字典表名称SYS_DICT_NAME_
@Column(name="SYS_DICT_NAME_")
	private String sysDictName;
	
	public void setSysDictName(String sysDictName) {
		this.sysDictName=sysDictName;
	}
	public String getSysDictName() {
		return this.sysDictName ;
	}
			
			
//后台数据字典表编码SYS_DICT_CODE_
@Column(name="SYS_DICT_CODE_")
	private String sysDictCode;
	
	public void setSysDictCode(String sysDictCode) {
		this.sysDictCode=sysDictCode;
	}
	public String getSysDictCode() {
		return this.sysDictCode ;
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
			
			
//相当于表的ID
@Column(name="TABLE_ID_")
	private String tableId;
	
	public void setTableId(String tableId) {
		this.tableId=tableId;
	}
	public String getTableId() {
		return this.tableId ;
	}
			
			
//相当于表的列名
@Column(name="TABLE_COLUMN_")
	private String tableColumn;
	
	public void setTableColumn(String tableColumn) {
		this.tableColumn=tableColumn;
	}
	public String getTableColumn() {
		return this.tableColumn ;
	}
			
			
//表名+表的ID+表的列，用点号来分割如jdbc.local.dburl=jdbc:mysql://39.108.124.247:3306/wsd
@Column(name="TABLE_KEY_")
	private String tableKey;
	
	public void setTableKey(String tableKey) {
		this.tableKey=tableKey;
	}
	public String getTableKey() {
		return this.tableKey ;
	}
			
			
//表的VALUE_
@Column(name="TABLE_VALUE_")
	private String tableValue;
	
	public void setTableValue(String tableValue) {
		this.tableValue=tableValue;
	}
	public String getTableValue() {
		return this.tableValue ;
	}
			
			
//虚拟表的数据类型DATA_TYPE_
@Column(name="DATA_TYPE_")
	private String dataType;
	
	public void setDataType(String dataType) {
		this.dataType=dataType;
	}
	public String getDataType() {
		return this.dataType ;
	}
			
			
//虚拟表的列类型COLUMN_TYPE_
@Column(name="COLUMN_TYPE_")
	private String columnType;
	
	public void setColumnType(String columnType) {
		this.columnType=columnType;
	}
	public String getColumnType() {
		return this.columnType ;
	}
			
			
//虚拟表的列的分类COLUMN_ASSORT_
@Column(name="COLUMN_ASSORT_")
	private String columnAssort;
	
	public void setColumnAssort(String columnAssort) {
		this.columnAssort=columnAssort;
	}
	public String getColumnAssort() {
		return this.columnAssort ;
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
			
			
//中文标识KEY_CN_
@Column(name="KEY_CN_")
	private String keyCn;
	
	public void setKeyCn(String keyCn) {
		this.keyCn=keyCn;
	}
	public String getKeyCn() {
		return this.keyCn ;
	}
			
			
//中文值VALUE_CN_
@Column(name="VALUE_CN_")
	private String valueCn;
	
	public void setValueCn(String valueCn) {
		this.valueCn=valueCn;
	}
	public String getValueCn() {
		return this.valueCn ;
	}
			
			
//是否默认值
@Column(name="DEF_")
	private String def;
	
	public void setDef(String def) {
		this.def=def;
	}
	public String getDef() {
		return this.def ;
	}
			
			
//是否显示SHOW_
@Column(name="SHOW_")
	private String show;
	
	public void setShow(String show) {
		this.show=show;
	}
	public String getShow() {
		return this.show ;
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