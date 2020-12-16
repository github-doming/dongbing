package com.ibs.plan.module.cloud.ibsp_handicap.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsp_handicap 
 * IBSP_盘口 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsp_handicap")
public class IbspHandicap implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSP_盘口主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSP_HANDICAP_ID_")
	private String ibspHandicapId;
	public void setIbspHandicapId(String ibspHandicapId) {
		this.ibspHandicapId=ibspHandicapId;
	}
	public void setIbspHandicapId(Object ibspHandicapId) {
		if (ibspHandicapId != null) {
			this.ibspHandicapId = ibspHandicapId.toString();
		}
	}
	public String getIbspHandicapId() {
		return this.ibspHandicapId ;
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
	 * 盘口图标
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
	 * 总容量
	 */
	@Column(name="CAPACITY_MAX_")
	private Integer capacityMax;
	public void setCapacityMax(Integer capacityMax) {
		this.capacityMax=capacityMax;
	}
	public void setCapacityMax(Object capacityMax) {
		if (capacityMax != null) {
			if (capacityMax instanceof Integer) {
				this.capacityMax= (Integer) capacityMax;
			}else if (StringTool.isInteger(capacityMax.toString())) {
				this.capacityMax = Integer.parseInt(capacityMax.toString());
			}
		}
	}
	public Integer getCapacityMax() {
		return this.capacityMax ;
	}

	/**
	 * 已使用容量
	 */
	@Column(name="CAPACITY_")
	private Integer capacity;
	public void setCapacity(Integer capacity) {
		this.capacity=capacity;
	}
	public void setCapacity(Object capacity) {
		if (capacity != null) {
			if (capacity instanceof Integer) {
				this.capacity= (Integer) capacity;
			}else if (StringTool.isInteger(capacity.toString())) {
				this.capacity = Integer.parseInt(capacity.toString());
			}
		}
	}
	public Integer getCapacity() {
		return this.capacity ;
	}

	/**
	 * 创建者
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
	 * 更新者
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