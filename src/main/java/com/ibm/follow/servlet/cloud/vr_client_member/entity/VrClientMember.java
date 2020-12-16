package com.ibm.follow.servlet.cloud.vr_client_member.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table vr_client_member 
 * VR_客户端虚拟会员 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "vr_client_member")
public class VrClientMember implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * VR_客户端虚拟会员主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="VR_CLIENT_MEMBER_ID_")
	private String vrClientMemberId;
	public void setVrClientMemberId(String vrClientMemberId) {
		this.vrClientMemberId=vrClientMemberId;
	}
	public void setVrClientMemberId(Object vrClientMemberId) {
		if (vrClientMemberId != null) {
			this.vrClientMemberId = vrClientMemberId.toString();
		}
	}
	public String getVrClientMemberId() {
		return this.vrClientMemberId ;
	}

	/**
	 * 虚拟会员主键
	 */
	@Column(name="VR_MEMBER_ID_")
	private String vrMemberId;
	public void setVrMemberId(String vrMemberId) {
		this.vrMemberId=vrMemberId;
	}
	public void setVrMemberId(Object vrMemberId) {
		if (vrMemberId != null) {
			this.vrMemberId = vrMemberId.toString();
		}
	}
	public String getVrMemberId() {
		return this.vrMemberId ;
	}

	/**
	 * 客户端已存在盘口会员主键
	 */
	@Column(name="EXIST_MEMBER_VR_ID_")
	private String existMemberVrId;
	public void setExistMemberVrId(String existMemberVrId) {
		this.existMemberVrId=existMemberVrId;
	}
	public void setExistMemberVrId(Object existMemberVrId) {
		if (existMemberVrId != null) {
			this.existMemberVrId = existMemberVrId.toString();
		}
	}
	public String getExistMemberVrId() {
		return this.existMemberVrId ;
	}

	/**
	 * 中心端客户端主键
	 */
	@Column(name="CLIENT_ID_")
	private String clientId;
	public void setClientId(String clientId) {
		this.clientId=clientId;
	}
	public void setClientId(Object clientId) {
		if (clientId != null) {
			this.clientId = clientId.toString();
		}
	}
	public String getClientId() {
		return this.clientId ;
	}

	/**
	 * 客户端编码
	 */
	@Column(name="CLIENT_CODE_")
	private String clientCode;
	public void setClientCode(String clientCode) {
		this.clientCode=clientCode;
	}
	public void setClientCode(Object clientCode) {
		if (clientCode != null) {
			this.clientCode = clientCode.toString();
		}
	}
	public String getClientCode() {
		return this.clientCode ;
	}

	/**
	 * 盘口编码HANDICAP_CODE_
	 */
	@Column(name="HANDICAP_CODE_")
	private String handicapCode;
	public void setHandicapCode(String handicapCode) {
		this.handicapCode=handicapCode;
	}
	public void setHandicapCode(Object handicapCode) {
		if (handicapCode != null) {
			this.handicapCode = handicapCode.toString();
		}
	}
	public String getHandicapCode() {
		return this.handicapCode ;
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
	 * 创建时间
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
	 * 更新时间
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