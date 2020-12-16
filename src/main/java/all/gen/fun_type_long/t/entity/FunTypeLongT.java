package all.gen.fun_type_long.t.entity;

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
 * The persistent class for the database table fun_type_long 
 * 的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "fun_type_long")
public class FunTypeLongT implements Serializable {

	private static final long serialVersionUID = 1L;
			@Id
	// 根据底层数据库
	@GeneratedValue(strategy = GenerationType.AUTO)
		
			
//ID
@Column(name="ID")
	private Long id;
	
	public void setId(Long id) {
		this.id=id;
	}
	public Long getId() {
		return this.id ;
	}
			
			
//名称
@Column(name="NAME")
	private String name;
	
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//DATE
@Column(name="D")
	private Date d;
	
	public void setD(Date d) {
		this.d=d;
	}
	public Date getD() {
		return this.d ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//DATETIME
@Column(name="DT")
	private Date dt;
	
	public void setDt(Date dt) {
		this.dt=dt;
	}
	public Date getDt() {
		return this.dt ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//TIME
@Column(name="T")
	private Date t;
	
	public void setT(Date t) {
		this.t=t;
	}
	public Date getT() {
		return this.t ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//TIMESTAMP
@Column(name="TS")
	private Date ts;
	
	public void setTs(Date ts) {
		this.ts=ts;
	}
	public Date getTs() {
		return this.ts ;
	}
			
			
//C_DECIMAL
@Column(name="C_DECIMAL")
	private BigDecimal cDecimal;
	
	public void setCDecimal(BigDecimal cDecimal) {
		this.cDecimal=cDecimal;
	}
	public BigDecimal getCDecimal() {
		return this.cDecimal ;
	}
			
			
//C_NUMERIC
@Column(name="C_NUMERIC")
	private BigDecimal cNumeric;
	
	public void setCNumeric(BigDecimal cNumeric) {
		this.cNumeric=cNumeric;
	}
	public BigDecimal getCNumeric() {
		return this.cNumeric ;
	}
			
			
//删除标志
@Column(name="DEL_FLAG")
	private String delFlag;
	
	public void setDelFlag(String delFlag) {
		this.delFlag=delFlag;
	}
	public String getDelFlag() {
		return this.delFlag ;
	}
			
			
//是否删除
@Column(name="IS_DEL")
	private Integer isDel;
	
	public void setIsDel(Integer isDel) {
		this.isDel=isDel;
	}
	public Integer getIsDel() {
		return this.isDel ;
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