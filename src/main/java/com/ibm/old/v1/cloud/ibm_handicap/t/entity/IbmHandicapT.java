package com.ibm.old.v1.cloud.ibm_handicap.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_handicap 
 * IBM_盘口IBM_HANDICAP的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_handicap")
public class IbmHandicapT implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 索引
	 */
	@Column(name="IDX_")
	private Long idx;
	public void setIdx(Long idx) {
		this.idx=idx;
	}
	public void setIdx(Object idx) {
		if (idx != null) {
			if (idx instanceof Long) {
				this.idx= (Long) idx;
			}else if (StringTool.isInteger(idx.toString())) {
				this.idx = Long.parseLong(idx.toString());
			}
		}
	}
	public Long getIdx() {
		return this.idx ;
	}

	/**
	 * IBM_盘口主键IBM_HANDICAP_ID_
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_HANDICAP_ID_")
	private String ibmHandicapId;
	public void setIbmHandicapId(String ibmHandicapId) {
		this.ibmHandicapId=ibmHandicapId;
	}
	public void setIbmHandicapId(Object ibmHandicapId) {
		if (ibmHandicapId != null) {
			this.ibmHandicapId = ibmHandicapId.toString();
		}
	}
	public String getIbmHandicapId() {
		return this.ibmHandicapId ;
	}

	/**
	 * 盘口名称
	 */
	@Column(name="HANDICAP_NAME_")
	private String handicapName;
	public void setHandicapName(String handicapName) {
		this.handicapName=handicapName;
	}
	public void setHandicapName(Object handicapName) {
		if (handicapName != null) {
			this.handicapName = handicapName.toString();
		}
	}
	public String getHandicapName() {
		return this.handicapName ;
	}

	/**
	 * 盘口编码
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
	 * 盘口说明
	 */
	@Column(name="HANDICAP_EXPLAIN_")
	private String handicapExplain;
	public void setHandicapExplain(String handicapExplain) {
		this.handicapExplain=handicapExplain;
	}
	public void setHandicapExplain(Object handicapExplain) {
		if (handicapExplain != null) {
			this.handicapExplain = handicapExplain.toString();
		}
	}
	public String getHandicapExplain() {
		return this.handicapExplain ;
	}

	/**
	 * 盘口类型
	 */
	@Column(name="HANDICAP_TYPE_")
	private String handicapType;
	public void setHandicapType(String handicapType) {
		this.handicapType=handicapType;
	}
	public void setHandicapType(Object handicapType) {
		if (handicapType != null) {
			this.handicapType = handicapType.toString();
		}
	}
	public String getHandicapType() {
		return this.handicapType ;
	}

	/**
	 * 盘口价值
	 */
	@Column(name="HANDICAP_WORTH_T_")
	private Integer handicapWorthT;
	public void setHandicapWorthT(Integer handicapWorthT) {
		this.handicapWorthT=handicapWorthT;
	}
	public void setHandicapWorthT(Object handicapWorthT) {
		if (handicapWorthT != null) {
			if (handicapWorthT instanceof Integer) {
				this.handicapWorthT= (Integer) handicapWorthT;
			}else if (StringTool.isInteger(handicapWorthT.toString())) {
				this.handicapWorthT = Integer.parseInt(handicapWorthT.toString());
			}
		}
	}
	public Integer getHandicapWorthT() {
		return this.handicapWorthT ;
	}

	/**
	 * 盘口版本
	 */
	@Column(name="HANDICAP_VERSION_")
	private String handicapVersion;
	public void setHandicapVersion(String handicapVersion) {
		this.handicapVersion=handicapVersion;
	}
	public void setHandicapVersion(Object handicapVersion) {
		if (handicapVersion != null) {
			this.handicapVersion = handicapVersion.toString();
		}
	}
	public String getHandicapVersion() {
		return this.handicapVersion ;
	}

	/**
	 * 方案图标
	 */
	@Column(name="HANDICAP_ICON_")
	private String handicapIcon;
	public void setHandicapIcon(String handicapIcon) {
		this.handicapIcon=handicapIcon;
	}
	public void setHandicapIcon(Object handicapIcon) {
		if (handicapIcon != null) {
			this.handicapIcon = handicapIcon.toString();
		}
	}
	public String getHandicapIcon() {
		return this.handicapIcon ;
	}

	/**
	 * 次序
	 */
	@Column(name="SN_")
	private Integer sn;
	public void setSn(Integer sn) {
		this.sn=sn;
	}
	public void setSn(Object sn) {
		if (sn != null) {
			if (sn instanceof Integer) {
				this.sn= (Integer) sn;
			}else if (StringTool.isInteger(sn.toString())) {
				this.sn = Integer.parseInt(sn.toString());
			}
		}
	}
	public Integer getSn() {
		return this.sn ;
	}

	/**
	 * 创建者CREATE_USER_
	 */
	@Column(name="CREATE_USER_")
	private String createUser;
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public void setCreateUser(Object createUser) {
		if (createUser != null) {
			this.createUser = createUser.toString();
		}
	}
	public String getCreateUser() {
		return this.createUser ;
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
	 * 更新者UPDATE_USER_
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