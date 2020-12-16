package c.x.platform.admin.feng.bbs.cx.bbs_notegroup_note.vo;
import java.io.Serializable;
/**
 * The vo class for the database table bbs_notegroup_note vo类
 */
public class BbsNotegroupNoteVo implements Serializable {
	private static final long serialVersionUID = 1L;
	// id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	// notegroup_id
	private String notegroup_id;
	public void setNotegroup_id(String notegroup_id) {
		this.notegroup_id = notegroup_id;
	}
	public String getNotegroup_id() {
		return this.notegroup_id;
	}
	// note_id
	private String note_id;
	public void setNote_id(String note_id) {
		this.note_id = note_id;
	}
	public String getNote_id() {
		return this.note_id;
	}
}