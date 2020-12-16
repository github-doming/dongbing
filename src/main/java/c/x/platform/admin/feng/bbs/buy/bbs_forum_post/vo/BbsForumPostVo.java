package c.x.platform.admin.feng.bbs.buy.bbs_forum_post.vo;

import java.io.Serializable;
/**
 * The vo class for the database table bbs_forum_post vo类
 */

public class BbsForumPostVo implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}

	// forum_id
	private String forum_id;
	public void setForum_id(String forum_id) {
		this.forum_id = forum_id;
	}
	public String getForum_id() {
		return this.forum_id;
	}

	// post_id
	private String post_id;
	public void setPost_id(String post_id) {
		this.post_id = post_id;
	}
	public String getPost_id() {
		return this.post_id;
	}

}