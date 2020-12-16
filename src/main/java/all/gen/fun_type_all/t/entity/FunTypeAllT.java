package all.gen.fun_type_all.t.entity;

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
 * The persistent class for the database table fun_type_all 
 * 的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "fun_type_all")
public class FunTypeAllT implements Serializable {

	private static final long serialVersionUID = 1L;
			@Id
	// 根据底层数据库
	@GeneratedValue(strategy = GenerationType.AUTO)
		
			
//ID
@Column(name="ID")
	private Integer id;
	
	public void setId(Integer id) {
		this.id=id;
	}
	public Integer getId() {
		return this.id ;
	}
			
			
//名称
@Column(name="VARCHAR_C")
	private String varcharC;
	
	public void setVarcharC(String varcharC) {
		this.varcharC=varcharC;
	}
	public String getVarcharC() {
		return this.varcharC ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//DATE
@Column(name="DATE_C")
	private Date dateC;
	
	public void setDateC(Date dateC) {
		this.dateC=dateC;
	}
	public Date getDateC() {
		return this.dateC ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//DATETIME
@Column(name="DATETIME")
	private Date datetime;
	
	public void setDatetime(Date datetime) {
		this.datetime=datetime;
	}
	public Date getDatetime() {
		return this.datetime ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//TIME
@Column(name="TIME")
	private Date time;
	
	public void setTime(Date time) {
		this.time=time;
	}
	public Date getTime() {
		return this.time ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//TIMESTAMP
@Column(name="TIMESTAMP")
	private Date timestamp;
	
	public void setTimestamp(Date timestamp) {
		this.timestamp=timestamp;
	}
	public Date getTimestamp() {
		return this.timestamp ;
	}
			
			
//DECIMAL_C
@Column(name="DECIMAL_C")
	private BigDecimal decimalC;
	
	public void setDecimalC(BigDecimal decimalC) {
		this.decimalC=decimalC;
	}
	public BigDecimal getDecimalC() {
		return this.decimalC ;
	}
			
			
//NUMERIC_C
@Column(name="NUMERIC_C")
	private BigDecimal numericC;
	
	public void setNumericC(BigDecimal numericC) {
		this.numericC=numericC;
	}
	public BigDecimal getNumericC() {
		return this.numericC ;
	}
			
			
//删除标志
@Column(name="CHART_C")
	private String chartC;
	
	public void setChartC(String chartC) {
		this.chartC=chartC;
	}
	public String getChartC() {
		return this.chartC ;
	}
			
			
//是否删除
@Column(name="LONG_C")
	private Long longC;
	
	public void setLongC(Long longC) {
		this.longC=longC;
	}
	public Long getLongC() {
		return this.longC ;
	}
			
			
//SMALLINT_C
@Column(name="SMALLINT_C")
	private Short smallintC;
	
	public void setSmallintC(Short smallintC) {
		this.smallintC=smallintC;
	}
	public Short getSmallintC() {
		return this.smallintC ;
	}
			
			
//TINYINT_C
@Column(name="TINYINT_C")
	private Byte tinyintC;
	
	public void setTinyintC(Byte tinyintC) {
		this.tinyintC=tinyintC;
	}
	public Byte getTinyintC() {
		return this.tinyintC ;
	}
			
			
//INT_C
@Column(name="INT_C")
	private Integer intC;
	
	public void setIntC(Integer intC) {
		this.intC=intC;
	}
	public Integer getIntC() {
		return this.intC ;
	}
			
			
//INT_UNSIGNED
@Column(name="INT_UNSIGNED")
	private Integer intUnsigned;
	
	public void setIntUnsigned(Integer intUnsigned) {
		this.intUnsigned=intUnsigned;
	}
	public Integer getIntUnsigned() {
		return this.intUnsigned ;
	}
			
			
//BIT_C
@Column(name="BIT_C")
	private Boolean bitC;
	
	public void setBitC(Boolean bitC) {
		this.bitC=bitC;
	}
	public Boolean getBitC() {
		return this.bitC ;
	}
			
			
//DOUBLE_C
@Column(name="DOUBLE_C")
	private Double doubleC;
	
	public void setDoubleC(Double doubleC) {
		this.doubleC=doubleC;
	}
	public Double getDoubleC() {
		return this.doubleC ;
	}
			
			
//FLOAT_C
@Column(name="FLOAT_C")
	private Float floatC;
	
	public void setFloatC(Float floatC) {
		this.floatC=floatC;
	}
	public Float getFloatC() {
		return this.floatC ;
	}
			
			
//BLOB_C
@Column(name="BLOB_C")
	private byte[] blobC;
	
	public void setBlobC(byte[] blobC) {
		this.blobC=blobC;
	}
	public byte[] getBlobC() {
		return this.blobC ;
	}
			
			
//TEXT_C
@Column(name="TEXT_C")
	private String textC;
	
	public void setTextC(String textC) {
		this.textC=textC;
	}
	public String getTextC() {
		return this.textC ;
	}
			
			
//LONGTEXT_C
@Column(name="LONGTEXT_C")
	private String longtextC;
	
	public void setLongtextC(String longtextC) {
		this.longtextC=longtextC;
	}
	public String getLongtextC() {
		return this.longtextC ;
	}
			
			
//LONGBLOB_C
@Column(name="LONGBLOB_C")
	private byte[] longblobC;
	
	public void setLongblobC(byte[] longblobC) {
		this.longblobC=longblobC;
	}
	public byte[] getLongblobC() {
		return this.longblobC ;
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