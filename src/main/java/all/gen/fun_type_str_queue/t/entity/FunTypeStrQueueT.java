package all.gen.fun_type_str_queue.t.entity;

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
 * The persistent class for the database table fun_type_str_queue 
 * 例子FUN_TYPE_STR_QUEUE的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "fun_type_str_queue")
public class FunTypeStrQueueT implements Serializable {

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
		
			
//FUN_TYPE_STR_QUEUE_ID_
@Column(name="FUN_TYPE_STR_QUEUE_ID_")
	private String funTypeStrQueueId;
	
	public void setFunTypeStrQueueId(String funTypeStrQueueId) {
		this.funTypeStrQueueId=funTypeStrQueueId;
	}
	public String getFunTypeStrQueueId() {
		return this.funTypeStrQueueId ;
	}
			
			
//NAME_
@Column(name="NAME_")
	private String name;
	
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//D_
@Column(name="D_")
	private Date d;
	
	public void setD(Date d) {
		this.d=d;
	}
	public Date getD() {
		return this.d ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//DT_
@Column(name="DT_")
	private Date dt;
	
	public void setDt(Date dt) {
		this.dt=dt;
	}
	public Date getDt() {
		return this.dt ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//T_
@Column(name="T_")
	private Date t;
	
	public void setT(Date t) {
		this.t=t;
	}
	public Date getT() {
		return this.t ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//TS_
@Column(name="TS_")
	private Date ts;
	
	public void setTs(Date ts) {
		this.ts=ts;
	}
	public Date getTs() {
		return this.ts ;
	}
			
			
//DECIMAL_
@Column(name="DECIMAL_")
	private BigDecimal decimal;
	
	public void setDecimal(BigDecimal decimal) {
		this.decimal=decimal;
	}
	public BigDecimal getDecimal() {
		return this.decimal ;
	}
			
			
//NUMERIC_
@Column(name="NUMERIC_")
	private BigDecimal numeric;
	
	public void setNumeric(BigDecimal numeric) {
		this.numeric=numeric;
	}
	public BigDecimal getNumeric() {
		return this.numeric ;
	}
			
			
//连接ID CONNECTION_ID_
@Column(name="CONNECTION_ID_")
	private String connectionId;
	
	public void setConnectionId(String connectionId) {
		this.connectionId=connectionId;
	}
	public String getConnectionId() {
		return this.connectionId ;
	}
			
			
//线程 THREAD_
@Column(name="THREAD_")
	private String thread;
	
	public void setThread(String thread) {
		this.thread=thread;
	}
	public String getThread() {
		return this.thread ;
	}
			
			
//积分总余额TOTAL_POINT_
@Column(name="TOTAL_POINT_")
	private Integer totalPoint;
	
	public void setTotalPoint(Integer totalPoint) {
		this.totalPoint=totalPoint;
	}
	public Integer getTotalPoint() {
		return this.totalPoint ;
	}
			
			
//可用积分USEABLE_POINT_
@Column(name="USEABLE_POINT_")
	private Integer useablePoint;
	
	public void setUseablePoint(Integer useablePoint) {
		this.useablePoint=useablePoint;
	}
	public Integer getUseablePoint() {
		return this.useablePoint ;
	}
			
			
//冻结积分FROZEN_POINT_
@Column(name="FROZEN_POINT_")
	private Integer frozenPoint;
	
	public void setFrozenPoint(Integer frozenPoint) {
		this.frozenPoint=frozenPoint;
	}
	public Integer getFrozenPoint() {
		return this.frozenPoint ;
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