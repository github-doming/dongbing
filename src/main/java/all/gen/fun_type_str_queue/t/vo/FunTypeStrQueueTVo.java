package all.gen.fun_type_str_queue.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table fun_type_str_queue 
 * vo类
 */

public class FunTypeStrQueueTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//FUN_TYPE_STR_QUEUE_ID_
	private String funTypeStrQueueId;
	public void setFunTypeStrQueueId(String funTypeStrQueueId) {
		this.funTypeStrQueueId=funTypeStrQueueId;
	}
	public String getFunTypeStrQueueId() {
		return this.funTypeStrQueueId ;
	}
	
//NAME_
	private String name;
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
	
//D_
	private String d;
	public void setD(String d) {
		this.d=d;
	}
	public String getD() {
		return this.d ;
	}
	
//DT_
	private String dt;
	public void setDt(String dt) {
		this.dt=dt;
	}
	public String getDt() {
		return this.dt ;
	}
	
//T_
	private String t;
	public void setT(String t) {
		this.t=t;
	}
	public String getT() {
		return this.t ;
	}
	
//TS_
	private String ts;
	public void setTs(String ts) {
		this.ts=ts;
	}
	public String getTs() {
		return this.ts ;
	}
	
//DECIMAL_
	private String decimal;
	public void setDecimal(String decimal) {
		this.decimal=decimal;
	}
	public String getDecimal() {
		return this.decimal ;
	}
	
//NUMERIC_
	private String numeric;
	public void setNumeric(String numeric) {
		this.numeric=numeric;
	}
	public String getNumeric() {
		return this.numeric ;
	}
	
//连接ID CONNECTION_ID_
	private String connectionId;
	public void setConnectionId(String connectionId) {
		this.connectionId=connectionId;
	}
	public String getConnectionId() {
		return this.connectionId ;
	}
	
//线程 THREAD_
	private String thread;
	public void setThread(String thread) {
		this.thread=thread;
	}
	public String getThread() {
		return this.thread ;
	}
	
//积分总余额TOTAL_POINT_
	private String totalPoint;
	public void setTotalPoint(String totalPoint) {
		this.totalPoint=totalPoint;
	}
	public String getTotalPoint() {
		return this.totalPoint ;
	}
	
//可用积分USEABLE_POINT_
	private String useablePoint;
	public void setUseablePoint(String useablePoint) {
		this.useablePoint=useablePoint;
	}
	public String getUseablePoint() {
		return this.useablePoint ;
	}
	
//冻结积分FROZEN_POINT_
	private String frozenPoint;
	public void setFrozenPoint(String frozenPoint) {
		this.frozenPoint=frozenPoint;
	}
	public String getFrozenPoint() {
		return this.frozenPoint ;
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