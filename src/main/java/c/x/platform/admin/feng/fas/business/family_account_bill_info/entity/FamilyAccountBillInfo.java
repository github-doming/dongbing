package c.x.platform.admin.feng.fas.business.family_account_bill_info.entity;
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
 * The persistent class for the database table fas_bill_info 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "fas_bill_info")
public class FamilyAccountBillInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	// 自己制定ID
	// @Id
	// 根据底层数据库
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// id
	@Column(name = "id")
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	// business_name
	@Column(name = "business_name")
	private String business_name;
	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}
	public String getBusiness_name() {
		return this.business_name;
	}
	// 业务分类ID
	@Column(name = "business_id")
	private Long business_id;
	public void setBusiness_id(Long business_id) {
		this.business_id = business_id;
	}
	public Long getBusiness_id() {
		return this.business_id;
	}
	// account_begin
	@Column(name = "account_begin")
	private BigDecimal account_begin;
	public void setAccount_begin(BigDecimal account_begin) {
		this.account_begin = account_begin;
	}
	public BigDecimal getAccount_begin() {
		return this.account_begin;
	}
	// account_begin_id
	@Column(name = "account_begin_id")
	private Long account_begin_id;
	public void setAccount_begin_id(Long account_begin_id) {
		this.account_begin_id = account_begin_id;
	}
	public Long getAccount_begin_id() {
		return this.account_begin_id;
	}
	// 增减金额
	@Column(name = "account_amount")
	private BigDecimal account_amount;
	public void setAccount_amount(BigDecimal account_amount) {
		this.account_amount = account_amount;
	}
	public BigDecimal getAccount_amount() {
		return this.account_amount;
	}
	// 结余
	@Column(name = "account_balance")
	private BigDecimal account_balance;
	public void setAccount_balance(BigDecimal account_balance) {
		this.account_balance = account_balance;
	}
	public BigDecimal getAccount_balance() {
		return this.account_balance;
	}
	@Temporal(TemporalType.TIMESTAMP)
	// create_time_dt
	@Column(name = "create_time_dt")
	private Date create_time_dt;
	public void setCreate_time_dt(Date create_time_dt) {
		this.create_time_dt = create_time_dt;
	}
	public Date getCreate_time_dt() {
		return this.create_time_dt;
	}
	// create_time
	@Column(name = "create_time")
	private Long create_time;
	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}
	public Long getCreate_time() {
		return this.create_time;
	}
	// 收入或支出
	@Column(name = "value_1")
	private String value_1;
	public void setValue_1(String value_1) {
		this.value_1 = value_1;
	}
	public String getValue_1() {
		return this.value_1;
	}
	// 状态(中文)
	@Column(name = "value_2")
	private String value_2;
	public void setValue_2(String value_2) {
		this.value_2 = value_2;
	}
	public String getValue_2() {
		return this.value_2;
	}
	// value_3
	@Column(name = "value_3")
	private String value_3;
	public void setValue_3(String value_3) {
		this.value_3 = value_3;
	}
	public String getValue_3() {
		return this.value_3;
	}
	// value_5
	@Column(name = "value_5")
	private String value_5;
	public void setValue_5(String value_5) {
		this.value_5 = value_5;
	}
	public String getValue_5() {
		return this.value_5;
	}
	// business_from
	@Column(name = "business_from")
	private String business_from;
	public void setBusiness_from(String business_from) {
		this.business_from = business_from;
	}
	public String getBusiness_from() {
		return this.business_from;
	}
	// business_to
	@Column(name = "business_to")
	private String business_to;
	public void setBusiness_to(String business_to) {
		this.business_to = business_to;
	}
	public String getBusiness_to() {
		return this.business_to;
	}
	// row
	@Column(name = "row")
	private Long row;
	public void setRow(Long row) {
		this.row = row;
	}
	public Long getRow() {
		return this.row;
	}
	private String table_name;
	/*
	 * 不映射
	 * 
	 * @return
	 */
	@Transient
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String tableName) {
		table_name = tableName;
	}
}