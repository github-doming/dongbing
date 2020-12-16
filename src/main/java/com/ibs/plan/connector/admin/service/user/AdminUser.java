package com.ibs.plan.connector.admin.service.user;
import javax.persistence.Column;
import java.io.Serializable;

/**
 * @Description: 管理者用户
 * @Author: Dongming
 * @Date: 2019-09-07 16:15
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AdminUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "USER_ID_") private String userId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(Object userId) {
		if (userId != null) {
			if (userId instanceof String) {
				this.userId = (String) userId;
			} else {
				this.userId = userId.toString();
			}
		} else {
			this.userId = null;
		}
	}

	@Column(name = "USER_NAME_") private String userName;
	public String getUserName() {
		return userName;
	}
	public void setUserName(Object userName) {
		if (userName != null) {
			if (userName instanceof String) {
				this.userName = (String) userName;
			} else {
				this.userName = userName.toString();
			}
		} else {
			this.userName = null;
		}
	}
	/**
	 * APP用户类型
	 */
	@Column(name="USER_TYPE_")
	private String userType;
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
}
