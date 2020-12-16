package all.archit.complex.spring_kaida.admin.fun.user_info.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
/**
 * The persistent class for the database table sys_user_info 实体类
 */
@Entity
@Table(name = "sys_user_info")
public class SpringKaidaFunUserInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	// 自己制定ID
	// @Id
	// 根据底层数据库
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// 主键
	@Column(name = "id")
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	// 用户名
	@Column(name = "name")
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	// 密码
	@Column(name = "password")
	private String password;
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return this.password;
	}
	// 数据权限等级
	@Column(name = "permission_grade")
	private Integer permission_grade;
	public void setPermission_grade(Integer permission_grade) {
		this.permission_grade = permission_grade;
	}
	public Integer getPermission_grade() {
		return this.permission_grade;
	}
	// 描述
	@Column(name = "description")
	private String description;
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return this.description;
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