package c.x.platform.admin.feng.bbs.cx.bbs_note_info.vo;
import java.io.Serializable;
/**
 * The vo class for the database table bbs_note_info vo类
 */
public class BbsNoteInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;
	// id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	// 标题
	private String title;
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return this.title;
	}
	// 内容
	private String content;
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return this.content;
	}
}