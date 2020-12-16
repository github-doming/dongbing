package c.x.platform.admin.feng.fas.cx.fas_subject_info.vo;
import java.io.Serializable;
/**
 * The vo class for the database table fas_subject_info vo类
 */
public class FasSubjectInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;
	// id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	// name
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	// help_code
	private String help_code;
	public void setHelp_code(String help_code) {
		this.help_code = help_code;
	}
	public String getHelp_code() {
		return this.help_code;
	}
	// 备注
	private String sys_description;
	public void setSys_description(String sys_description) {
		this.sys_description = sys_description;
	}
	public String getSys_description() {
		return this.sys_description;
	}
	// sys_enable
	private String sys_enable;
	public void setSys_enable(String sys_enable) {
		this.sys_enable = sys_enable;
	}
	public String getSys_enable() {
		return this.sys_enable;
	}
	// sys_del
	private String sys_del;
	public void setSys_del(String sys_del) {
		this.sys_del = sys_del;
	}
	public String getSys_del() {
		return this.sys_del;
	}
}