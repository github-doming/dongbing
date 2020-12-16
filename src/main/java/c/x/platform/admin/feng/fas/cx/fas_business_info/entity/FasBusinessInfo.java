package c.x.platform.admin.feng.fas.cx.fas_business_info.entity;
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
 * The persistent class for the database table fas_business_info 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "fas_business_info")
public class FasBusinessInfo implements Serializable {
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
	// name
	@Column(name = "name")
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	// help_code
	@Column(name = "help_code")
	private String help_code;
	public void setHelp_code(String help_code) {
		this.help_code = help_code;
	}
	public String getHelp_code() {
		return this.help_code;
	}
	// 备注
	@Column(name = "sys_description")
	private String sys_description;
	public void setSys_description(String sys_description) {
		this.sys_description = sys_description;
	}
	public String getSys_description() {
		return this.sys_description;
	}
	// sys_enable
	@Column(name = "sys_enable")
	private Byte sys_enable;
	public void setSys_enable(Byte sys_enable) {
		this.sys_enable = sys_enable;
	}
	public Byte getSys_enable() {
		return this.sys_enable;
	}
	// sys_del
	@Column(name = "sys_del")
	private Byte sys_del;
	public void setSys_del(Byte sys_del) {
		this.sys_del = sys_del;
	}
	public Byte getSys_del() {
		return this.sys_del;
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