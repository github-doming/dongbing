package all.archit.complex.spring_kaida.admin.fun.user_info.vo;
import java.io.Serializable;
/**
 * The vo class for the database table sys_user_info vo类
 */
public class FunUserInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;
	// 主键
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	// 用户名
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	// 密码
	private String password;
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return this.password;
	}
	// 数据权限等级
	private String permission_grade;
	public void setPermission_grade(String permission_grade) {
		this.permission_grade = permission_grade;
	}
	public String getPermission_grade() {
		return this.permission_grade;
	}
	// 描述
	private String description;
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return this.description;
	}
}