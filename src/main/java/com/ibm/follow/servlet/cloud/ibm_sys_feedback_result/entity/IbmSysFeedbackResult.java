package com.ibm.follow.servlet.cloud.ibm_sys_feedback_result.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_sys_feedback_result 
 *  实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_sys_feedback_result")
public class IbmSysFeedbackResult implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户反馈处理结果表主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_SYS_FEEDBACK_RESULT_ID_")
	private String ibmSysFeedbackResultId;
	public void setIbmSysFeedbackResultId(String ibmSysFeedbackResultId) {
		this.ibmSysFeedbackResultId=ibmSysFeedbackResultId;
	}
	public void setIbmSysFeedbackResultId(Object ibmSysFeedbackResultId) {
		if (ibmSysFeedbackResultId != null) {
			this.ibmSysFeedbackResultId = ibmSysFeedbackResultId.toString();
		}
	}
	public String getIbmSysFeedbackResultId() {
		return this.ibmSysFeedbackResultId ;
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
	 * 处理结果
	 */
	@Column(name="FEEDBACK_RESULTS_")
	private String feedbackResults;
	public void setFeedbackResults(String feedbackResults) {
		this.feedbackResults=feedbackResults;
	}
	public void setFeedbackResults(Object feedbackResults) {
		if (feedbackResults != null) {
			this.feedbackResults = feedbackResults.toString();
		}
	}
	public String getFeedbackResults() {
		return this.feedbackResults ;
	}

	/**
	 * 处理人
	 */
	@Column(name="UPDATE_USER_")
	private String updateUser;
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public void setUpdateUser(Object updateUser) {
		if (updateUser != null) {
			this.updateUser = updateUser.toString();
		}
	}
	public String getUpdateUser() {
		return this.updateUser ;
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