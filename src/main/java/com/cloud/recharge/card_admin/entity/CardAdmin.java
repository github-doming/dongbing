package com.cloud.recharge.card_admin.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table card_admin 
 * 充值卡管理用户 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "card_admin")
public class CardAdmin implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 充值卡管理用户主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CARD_ADMIN_ID_")
	private Long cardAdminId;
	public void setCardAdminId(Long cardAdminId) {
		this.cardAdminId=cardAdminId;
	}
	public void setCardAdminId(Object cardAdminId) {
		if (cardAdminId != null) {
			if (cardAdminId instanceof Long) {
				this.cardAdminId= (Long) cardAdminId;
			}else if (StringTool.isInteger(cardAdminId.toString())) {
				this.cardAdminId = Long.parseLong(cardAdminId.toString());
			}
		}
	}
	public Long getCardAdminId() {
		return this.cardAdminId ;
	}

	/**
	 * 用户ID
	 */
	@Column(name="APP_USER_ID_")
	private String appUserId;
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public void setAppUserId(Object appUserId) {
		if (appUserId != null) {
			this.appUserId = appUserId.toString();
		}
	}
	public String getAppUserId() {
		return this.appUserId ;
	}

	/**
	 * 用户名称
	 */
	@Column(name="USER_NAME_")
	private String userName;
	public void setUserName(String userName) {
		this.userName=userName;
	}
	public void setUserName(Object userName) {
		if (userName != null) {
			this.userName = userName.toString();
		}
	}
	public String getUserName() {
		return this.userName ;
	}

	/**
	 * 用户名称
	 */
	@Column(name="USER_ALIAS_")
	private String userAlias;
	public void setUserAlias(String userAlias) {
		this.userAlias=userAlias;
	}
	public void setUserAlias(Object userAlias) {
		if (userAlias != null) {
			this.userAlias = userAlias.toString();
		}
	}
	public String getUserAlias() {
		return this.userAlias ;
	}

	/**
	 * 用户类型
	 */
	@Column(name="USER_TYPE_")
	private String userType;
	public void setUserType(String userType) {
		this.userType=userType;
	}
	public void setUserType(Object userType) {
		if (userType != null) {
			this.userType = userType.toString();
		}
	}
	public String getUserType() {
		return this.userType ;
	}

	/**
	 * 用户等级
	 */
	@Column(name="USER_LEVEL_")
	private int userLevel;
	public void setUserLevel(int userLevel) {
		this.userLevel=userLevel;
	}
	public int getUserLevel() {
		return this.userLevel ;
	}

	/**
	 * 用户等级
	 */
	@Column(name="USER_PATH_")
	private String userPath;
	public void setUserPath(String userPath) {
		this.userPath=userPath;
	}
	public void setUserPath(Object userPath) {
		if (userPath != null) {
			this.userPath = userPath.toString();
		}
	}
	public String getUserPath() {
		return this.userPath ;
	}

	/**
	 * 上级用户ID
	 */
	@Column(name="PARENT_USER_ID_")
	private String parentUserId;
	public void setParentUserId(String parentUserId) {
		this.parentUserId=parentUserId;
	}
	public void setParentUserId(Object parentUserId) {
		if (parentUserId != null) {
			this.parentUserId = parentUserId.toString();
		}
	}
	public String getParentUserId() {
		return this.parentUserId ;
	}

	/**
	 * 是否允许添加子代
	 */
	@Column(name="IS_ADD_")
	private Boolean isAdd;
	public void setIsAdd(Boolean isAdd) {
		this.isAdd=isAdd;
	}
	public Boolean getIsAdd() {
		return this.isAdd ;
	}

	/**
	 * 用户状态
	 */
	@Column(name="USER_STATE_")
	private String userState;
	public void setUserState(String userState) {
		this.userState=userState;
	}
	public void setUserState(Object userState) {
		if (userState != null) {
			this.userState = userState.toString();
		}
	}
	public String getUserState() {
		return this.userState ;
	}

	/**
	 * 创建时间
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="CREATE_TIME_")
	private Date createTime;
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
	}
	public void setCreateTime(Object createTime) throws ParseException {
		if (createTime != null) {
			if (createTime instanceof Date) {
				this.createTime= (Date) createTime;
			}else if (StringTool.isInteger(createTime.toString())) {
				this.createTime = new Date(Long.parseLong(createTime.toString()));
			}else {
				this.createTime = DateTool.getTime(createTime.toString());
			}
		}
	}
	public Date getCreateTime() {
		return this.createTime ;
	}

	/**
	 * 创建时间数字型
	 */
	@Column(name="CREATE_TIME_LONG_")
	private Long createTimeLong;
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public void setCreateTimeLong(Object createTimeLong) {
		if (createTimeLong != null) {
			if (createTimeLong instanceof Long) {
				this.createTimeLong= (Long) createTimeLong;
			}else if (StringTool.isInteger(createTimeLong.toString())) {
				this.createTimeLong = Long.parseLong(createTimeLong.toString());
			}
		}
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong ;
	}

	/**
	 * 更新时间
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="UPDATE_TIME_")
	private Date updateTime;
	public void setUpdateTime(Date updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTime(Object updateTime) throws ParseException {
		if (updateTime != null) {
			if (updateTime instanceof Date) {
				this.updateTime= (Date) updateTime;
			}else if (StringTool.isInteger(updateTime.toString())) {
				this.updateTime = new Date(Long.parseLong(updateTime.toString()));
			}else {
				this.updateTime = DateTool.getTime(updateTime.toString());
			}
		}
	}
	public Date getUpdateTime() {
		return this.updateTime ;
	}

	/**
	 * 更新时间数字型
	 */
	@Column(name="UPDATE_TIME_LONG_")
	private Long updateTimeLong;
	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public void setUpdateTimeLong(Object updateTimeLong) {
		if (updateTimeLong != null) {
			if (updateTimeLong instanceof Long) {
				this.updateTimeLong= (Long) updateTimeLong;
			}else if (StringTool.isInteger(updateTimeLong.toString())) {
				this.updateTimeLong = Long.parseLong(updateTimeLong.toString());
			}
		}
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong ;
	}

	/**
	 * DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	 */
	@Column(name="STATE_")
	private String state;
	public void setState(String state) {
		this.state=state;
	}
	public void setState(Object state) {
		if (state != null) {
			this.state = state.toString();
		}
	}
	public String getState() {
		return this.state ;
	}

	/**
	 * 描述
	 */
	@Column(name="DESC_")
	private String desc;
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public void setDesc(Object desc) {
		if (desc != null) {
			this.desc = desc.toString();
		}
	}
	public String getDesc() {
		return this.desc ;
	}

	private String tableNameMy;
	/**
	 * 不映射
	 * @return 表名
	 */
	@Transient
	public String getTableNameMy() {
		return tableNameMy;
	}

	public void setTableNameMy(String tableNameMy) {
		this.tableNameMy = tableNameMy;
	}

}