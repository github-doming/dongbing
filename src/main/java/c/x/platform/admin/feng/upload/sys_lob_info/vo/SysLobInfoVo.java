package c.x.platform.admin.feng.upload.sys_lob_info.vo;
import java.io.Serializable;
/**
 * The vo class for the database table sys_lob_info vo类
 */
public class SysLobInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;
	// 主键
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	// 文件名
	private String file_name;
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_name() {
		return this.file_name;
	}
	// 文件
	private String file;
	public void setFile(String file) {
		this.file = file;
	}
	public String getFile() {
		return this.file;
	}
	// 用户ID
	private String user_id;
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_id() {
		return this.user_id;
	}
	// create_time
	private String create_time;
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getCreate_time() {
		return this.create_time;
	}
}