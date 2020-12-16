package com.cloud.recharge.card_user_relation.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table card_user_relation 
 * 充值卡用户关联 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "card_user_relation")
public class CardUserRelation implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 充值卡用户关联主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="CARD_USER_RELATION_ID_")
	private String cardUserRelationId;
	public void setCardUserRelationId(String cardUserRelationId) {
		this.cardUserRelationId=cardUserRelationId;
	}
	public void setCardUserRelationId(Object cardUserRelationId) {
		if (cardUserRelationId != null) {
			this.cardUserRelationId = cardUserRelationId.toString();
		}
	}
	public String getCardUserRelationId() {
		return this.cardUserRelationId ;
	}

	/**
	 * 使用用户主键
	 */
	@Column(name="USED_USER_ID_")
	private String usedUserId;
	public void setUsedUserId(String usedUserId) {
		this.usedUserId=usedUserId;
	}
	public void setUsedUserId(Object usedUserId) {
		if (usedUserId != null) {
			this.usedUserId = usedUserId.toString();
		}
	}
	public String getUsedUserId() {
		return this.usedUserId ;
	}

	/**
	 * 使用用户名称
	 */
	@Column(name="USED_USER_NAME_")
	private String usedUserName;
	public void setUsedUserName(String usedUserName) {
		this.usedUserName=usedUserName;
	}
	public void setUsedUserName(Object usedUserName) {
		if (usedUserName != null) {
			this.usedUserName = usedUserName.toString();
		}
	}
	public String getUsedUserName() {
		return this.usedUserName ;
	}

	/**
	 * 管理用户主键
	 */
	@Column(name="ADMIN_USER_ID_")
	private String adminUserId;
	public void setAdminUserId(String adminUserId) {
		this.adminUserId=adminUserId;
	}
	public void setAdminUserId(Object adminUserId) {
		if (adminUserId != null) {
			this.adminUserId = adminUserId.toString();
		}
	}
	public String getAdminUserId() {
		return this.adminUserId ;
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