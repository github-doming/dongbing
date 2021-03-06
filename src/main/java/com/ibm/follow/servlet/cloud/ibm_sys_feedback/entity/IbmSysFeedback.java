package com.ibm.follow.servlet.cloud.ibm_sys_feedback.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_sys_feedback 
 *  实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_sys_feedback")
public class IbmSysFeedback implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户反馈表主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_SYS_FEEDBACK_ID_")
	private String ibmSysFeedbackId;
	public void setIbmSysFeedbackId(String ibmSysFeedbackId) {
		this.ibmSysFeedbackId=ibmSysFeedbackId;
	}
	public void setIbmSysFeedbackId(Object ibmSysFeedbackId) {
		if (ibmSysFeedbackId != null) {
			this.ibmSysFeedbackId = ibmSysFeedbackId.toString();
		}
	}
	public String getIbmSysFeedbackId() {
		return this.ibmSysFeedbackId ;
	}

	/**
	 * 反馈编码
	 */
	@Column(name="FEEDBACK_CODE_")
	private String feedbackCode;
	public void setFeedbackCode(String feedbackCode) {
		this.feedbackCode=feedbackCode;
	}
	public void setFeedbackCode(Object feedbackCode) {
		if (feedbackCode != null) {
			this.feedbackCode = feedbackCode.toString();
		}
	}
	public String getFeedbackCode() {
		return this.feedbackCode ;
	}

	/**
	 * 用户名
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
	 * 反馈类型
	 */
	@Column(name="FEEDBACK_TYPE_")
	private String feedbackType;
	public void setFeedbackType(String feedbackType) {
		this.feedbackType=feedbackType;
	}
	public void setFeedbackType(Object feedbackType) {
		if (feedbackType != null) {
			this.feedbackType = feedbackType.toString();
		}
	}
	public String getFeedbackType() {
		return this.feedbackType ;
	}

	/**
	 * 反馈标题
	 */
	@Column(name="FEEDBACK_TITLE_")
	private String feedbackTitle;
	public void setFeedbackTitle(String feedbackTitle) {
		this.feedbackTitle=feedbackTitle;
	}
	public void setFeedbackTitle(Object feedbackTitle) {
		if (feedbackTitle != null) {
			this.feedbackTitle = feedbackTitle.toString();
		}
	}
	public String getFeedbackTitle() {
		return this.feedbackTitle ;
	}

	/**
	 * 反馈内容
	 */
	@Column(name="FEEDBACK_INFO_")
	private String feedbackInfo;
	public void setFeedbackInfo(String feedbackInfo) {
		this.feedbackInfo=feedbackInfo;
	}
	public void setFeedbackInfo(Object feedbackInfo) {
		if (feedbackInfo != null) {
			this.feedbackInfo = feedbackInfo.toString();
		}
	}
	public String getFeedbackInfo() {
		return this.feedbackInfo ;
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
	 * 状态
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