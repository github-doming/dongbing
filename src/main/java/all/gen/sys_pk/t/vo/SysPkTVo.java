package all.gen.sys_pk.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table sys_pk 
 * vo类
 */

public class SysPkTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//SYS_PK_ID_
	private String sysPkId;
	public void setSysPkId(String sysPkId) {
		this.sysPkId=sysPkId;
	}
	public String getSysPkId() {
		return this.sysPkId ;
	}
	
//下一个ID
	private String nextId;
	public void setNextId(String nextId) {
		this.nextId=nextId;
	}
	public String getNextId() {
		return this.nextId ;
	}
	
//下一个ID数字型
	private String nextLong;
	public void setNextLong(String nextLong) {
		this.nextLong=nextLong;
	}
	public String getNextLong() {
		return this.nextLong ;
	}
	
//MACHINE_KEY_
	private String machineKey;
	public void setMachineKey(String machineKey) {
		this.machineKey=machineKey;
	}
	public String getMachineKey() {
		return this.machineKey ;
	}
	
//IP_
	private String ip;
	public void setIp(String ip) {
		this.ip=ip;
	}
	public String getIp() {
		return this.ip ;
	}
	
//MAC_
	private String mac;
	public void setMac(String mac) {
		this.mac=mac;
	}
	public String getMac() {
		return this.mac ;
	}
	
//表名;SQLSERVER  128个字符，临时表116个字符。Oracle          30个字符。（为什么要这么短？）MySQL          64个字符。Access          64个字符。DB2                  128个字符
	private String tableName;
	public void setTableName(String tableName) {
		this.tableName=tableName;
	}
	public String getTableName() {
		return this.tableName ;
	}
	
//版本号
	private String version;
	public void setVersion(String version) {
		this.version=version;
	}
	public String getVersion() {
		return this.version ;
	}
	
//主键初始化
	private String startLong;
	public void setStartLong(String startLong) {
		this.startLong=startLong;
	}
	public String getStartLong() {
		return this.startLong ;
	}
	
//步长
	private String step;
	public void setStep(String step) {
		this.step=step;
	}
	public String getStep() {
		return this.step ;
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