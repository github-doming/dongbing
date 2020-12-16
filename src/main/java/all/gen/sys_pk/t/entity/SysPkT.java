package all.gen.sys_pk.t.entity;

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
 * The persistent class for the database table sys_pk 
 * 后台主键表SYS_PK的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "sys_pk")
public class SysPkT implements Serializable {

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
		
			
//SYS_PK_ID_
@Column(name="SYS_PK_ID_")
	private String sysPkId;
	
	public void setSysPkId(String sysPkId) {
		this.sysPkId=sysPkId;
	}
	public String getSysPkId() {
		return this.sysPkId ;
	}
			
			
//下一个ID
@Column(name="NEXT_ID_")
	private String nextId;
	
	public void setNextId(String nextId) {
		this.nextId=nextId;
	}
	public String getNextId() {
		return this.nextId ;
	}
			
			
//下一个ID数字型
@Column(name="NEXT_LONG_")
	private Long nextLong;
	
	public void setNextLong(Long nextLong) {
		this.nextLong=nextLong;
	}
	public Long getNextLong() {
		return this.nextLong ;
	}
			
			
//MACHINE_KEY_
@Column(name="MACHINE_KEY_")
	private String machineKey;
	
	public void setMachineKey(String machineKey) {
		this.machineKey=machineKey;
	}
	public String getMachineKey() {
		return this.machineKey ;
	}
			
			
//IP_
@Column(name="IP_")
	private String ip;
	
	public void setIp(String ip) {
		this.ip=ip;
	}
	public String getIp() {
		return this.ip ;
	}
			
			
//MAC_
@Column(name="MAC_")
	private String mac;
	
	public void setMac(String mac) {
		this.mac=mac;
	}
	public String getMac() {
		return this.mac ;
	}
			
			
//表名;SQLSERVER  128个字符，临时表116个字符。Oracle          30个字符。（为什么要这么短？）MySQL          64个字符。Access          64个字符。DB2                  128个字符
@Column(name="TABLE_NAME_")
	private String tableName;
	
	public void setTableName(String tableName) {
		this.tableName=tableName;
	}
	public String getTableName() {
		return this.tableName ;
	}
			
			
//版本号
@Column(name="VERSION_")
	private Integer version;
	
	public void setVersion(Integer version) {
		this.version=version;
	}
	public Integer getVersion() {
		return this.version ;
	}
			
			
//主键初始化
@Column(name="START_LONG_")
	private Long startLong;
	
	public void setStartLong(Long startLong) {
		this.startLong=startLong;
	}
	public Long getStartLong() {
		return this.startLong ;
	}
			
			
//步长
@Column(name="STEP_")
	private Integer step;
	
	public void setStep(Integer step) {
		this.step=step;
	}
	public Integer getStep() {
		return this.step ;
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