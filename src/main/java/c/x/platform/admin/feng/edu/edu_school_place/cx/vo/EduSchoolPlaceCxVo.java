package c.x.platform.admin.feng.edu.edu_school_place.cx.vo;

import java.io.Serializable;
/**
 * The vo class for the database table edu_school_place vo类
 */

public class EduSchoolPlaceCxVo implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}

	// school_id
	private String schoolId;
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolId() {
		return this.schoolId;
	}

	// place_id
	private String placeId;
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public String getPlaceId() {
		return this.placeId;
	}

}