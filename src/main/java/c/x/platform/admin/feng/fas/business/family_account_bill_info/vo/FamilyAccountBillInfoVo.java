package c.x.platform.admin.feng.fas.business.family_account_bill_info.vo;
import java.io.Serializable;
/**
 * The vo class for the database table family_account_bill_info vo类
 */
public class FamilyAccountBillInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;
	// id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	// business_name
	private String business_name;
	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}
	public String getBusiness_name() {
		return this.business_name;
	}
	// 业务分类ID
	private String business_id;
	public void setBusiness_id(String business_id) {
		this.business_id = business_id;
	}
	public String getBusiness_id() {
		return this.business_id;
	}
	// account_begin
	private String account_begin;
	public void setAccount_begin(String account_begin) {
		this.account_begin = account_begin;
	}
	public String getAccount_begin() {
		return this.account_begin;
	}
	// account_begin_id
	private String account_begin_id;
	public void setAccount_begin_id(String account_begin_id) {
		this.account_begin_id = account_begin_id;
	}
	public String getAccount_begin_id() {
		return this.account_begin_id;
	}
	// 增减金额
	private String account_amount;
	public void setAccount_amount(String account_amount) {
		this.account_amount = account_amount;
	}
	public String getAccount_amount() {
		return this.account_amount;
	}
	// 结余
	private String account_balance;
	public void setAccount_balance(String account_balance) {
		this.account_balance = account_balance;
	}
	public String getAccount_balance() {
		return this.account_balance;
	}
	// create_time_dt
	private String create_time_dt;
	public void setCreate_time_dt(String create_time_dt) {
		this.create_time_dt = create_time_dt;
	}
	public String getCreate_time_dt() {
		return this.create_time_dt;
	}
	// create_time
	private String create_time;
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getCreate_time() {
		return this.create_time;
	}
	// 收入或支出
	private String value_1;
	public void setValue_1(String value_1) {
		this.value_1 = value_1;
	}
	public String getValue_1() {
		return this.value_1;
	}
	// 状态(中文)
	private String value_2;
	public void setValue_2(String value_2) {
		this.value_2 = value_2;
	}
	public String getValue_2() {
		return this.value_2;
	}
	// value_3
	private String value_3;
	public void setValue_3(String value_3) {
		this.value_3 = value_3;
	}
	public String getValue_3() {
		return this.value_3;
	}
	// value_5
	private String value_5;
	public void setValue_5(String value_5) {
		this.value_5 = value_5;
	}
	public String getValue_5() {
		return this.value_5;
	}
	// business_from
	private String business_from;
	public void setBusiness_from(String business_from) {
		this.business_from = business_from;
	}
	public String getBusiness_from() {
		return this.business_from;
	}
	// business_to
	private String business_to;
	public void setBusiness_to(String business_to) {
		this.business_to = business_to;
	}
	public String getBusiness_to() {
		return this.business_to;
	}
	// row
	private String row;
	public void setRow(String row) {
		this.row = row;
	}
	public String getRow() {
		return this.row;
	}
}