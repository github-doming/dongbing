package com.ibm.follow.servlet.client.ibmc_agent_member_info.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibmc_agent_member_info
 * IBMC客户端_代理会员信息 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibmc_agent_member_info")
public class IbmcAgentMemberInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBMC_客户端代理会员主键IBMC_AGENT_MEMBER_ID_
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBMC_AGENT_MEMBER_INFO_ID_")
	private String ibmcAgentMemberInfoId;
	public void setIbmcAgentMemberInfoId(String ibmcAgentMemberInfoId) {
		this.ibmcAgentMemberInfoId=ibmcAgentMemberInfoId;
	}
	public void setIbmcAgentMemberInfoId(Object ibmcAgentMemberInfoId) {
		if (ibmcAgentMemberInfoId != null) {
			this.ibmcAgentMemberInfoId = ibmcAgentMemberInfoId.toString();
		}
	}
	public String getIbmcAgentMemberInfoId() {
		return this.ibmcAgentMemberInfoId ;
	}

	/**
	 * 已存在盘口代理主键
	 */
	@Column(name="EXIST_HA_ID_")
	private String existHaId;
	public void setExistHaId(String existHaId) {
		this.existHaId=existHaId;
	}
	public void setExistHaId(Object existHaId) {
		if (existHaId != null) {
			this.existHaId = existHaId.toString();
		}
	}
	public String getExistHaId() {
		return this.existHaId ;
	}

	/**
	 * 已存在盘口会员主键
	 */
	@Column(name="EXIST_HM_ID_")
	private String existHmId;
	public void setExistHmId(String existHmId) {
		this.existHmId=existHmId;
	}
	public void setExistHmId(Object existHmId) {
		if (existHmId != null) {
			this.existHmId = existHmId.toString();
		}
	}
	public String getExistHmId() {
		return this.existHmId ;
	}

	/**
	 * 会员客户端编码
	 */
	@Column(name="MEMBER_CLIENT_CODE_")
	private String memberClientCode;
	public void setMemberClientCode(String memberClientCode) {
		this.memberClientCode=memberClientCode;
	}
	public void setMemberClientCode(Object memberClientCode) {
		if (memberClientCode != null) {
			this.memberClientCode = memberClientCode.toString();
		}
	}
	public String getMemberClientCode() {
		return this.memberClientCode ;
	}

	/**
	 * 投注模式
	 */
	@Column(name="BET_MODE_INFO_")
	private String betModeInfo;
	public void setBetModeInfo(String betModeInfo) {
		this.betModeInfo=betModeInfo;
	}
	public void setBetModeInfo(Object betModeInfo) {
		if (betModeInfo != null) {
			this.betModeInfo = betModeInfo.toString();
		}
	}
	public String getBetModeInfo() {
		return this.betModeInfo ;
	}

    /**
     * 会员盘口编码
     */
    @Column(name="MEMBER_HANDICAP_CODE_")
    private String memberHandicapCode;
    public void setMemberHandicapCode(String memeberHandicapCode) {
        this.memberHandicapCode=memeberHandicapCode;
    }
    public void setMemberHandicapCode(Object memeberHandicapCode) {
        if (memeberHandicapCode != null) {
            this.memberHandicapCode = memeberHandicapCode.toString();
        }else{
            this.memberHandicapCode = null;
        }
    }
    public String getMemberHandicapCode() {
        return this.memberHandicapCode ;
    }

    /**
     * 会员账号
     */
    @Column(name="MEMBER_ACCOUNT_")
    private String memberAccount;
    public void setMemberAccount(String memberAccount) {
        this.memberAccount=memberAccount;
    }
    public void setMemberAccount(Object memberAccount) {
        if (memberAccount != null) {
            this.memberAccount = memberAccount.toString();
        }else{
            this.memberAccount = null;
        }
    }
    public String getMemberAccount() {
        return this.memberAccount ;
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