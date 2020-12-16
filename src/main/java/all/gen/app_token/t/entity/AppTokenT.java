package all.gen.app_token.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table app_token 
 * APP令牌APP_TOKEN的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "app_token")
public class AppTokenT implements Serializable {

	private static final long serialVersionUID = 1L;
				
			
//索引
@Column(name="IDX_")
	private Long idx;
	
	public void setIdx(Long idx) {
		this.idx=idx;
	}
	public Long getIdx() {
		return this.idx ;
	}
		@Id
	// 根据底层数据库
	@GeneratedValue(strategy = GenerationType.AUTO)
		
			
//APP_TOKEN_ID_
@Column(name="APP_TOKEN_ID_")
	private String appTokenId;
	
	public void setAppTokenId(String appTokenId) {
		this.appTokenId=appTokenId;
	}
	public String getAppTokenId() {
		return this.appTokenId ;
	}
			
			
//APP_USER_ID_
@Column(name="APP_USER_ID_")
	private String appUserId;
	
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public String getAppUserId() {
		return this.appUserId ;
	}
			
			
//KEY_
@Column(name="KEY_")
	private String key;
	
	public void setKey(String key) {
		this.key=key;
	}
	public String getKey() {
		return this.key ;
	}
			
			
//描述
@Column(name="VALUE_")
	private String value;
	
	public void setValue(String value) {
		this.value=value;
	}
	public String getValue() {
		return this.value ;
	}


    //IP_
    @Column(name="IP_")
    private String ip;

    public void setIp(String ip) {
        this.ip=ip;
    }
    public String getIp() {
        return this.ip ;
    }
			
			
//SESSION_ID_
@Column(name="SESSION_ID_")
	private String sessionId;
	
	public void setSessionId(String sessionId) {
		this.sessionId=sessionId;
	}
	public String getSessionId() {
		return this.sessionId ;
	}
			
			
//用户类型
@Column(name="USER_TYPE_")
	private String userType;
	
	public void setUserType(String userType) {
		this.userType=userType;
	}
	public String getUserType() {
		return this.userType ;
	}
			
			
//注册类型或来源(APP或PC或管理员或程序)REGISTER_TYPE_
@Column(name="REGISTER_TYPE_")
	private String registerType;
	
	public void setRegisterType(String registerType) {
		this.registerType=registerType;
	}
	public String getRegisterType() {
		return this.registerType ;
	}
			
			
//渠道类型(APP,PC,邮件，短信，微信)CHANNEL_TYPE_
@Column(name="CHANNEL_TYPE_")
	private String channelType;
	
	public void setChannelType(String channelType) {
		this.channelType=channelType;
	}
	public String getChannelType() {
		return this.channelType ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//有效时间
@Column(name="PERIOD_TIME_")
	private Date periodTime;
	
	public void setPeriodTime(Date periodTime) {
		this.periodTime=periodTime;
	}
	public Date getPeriodTime() {
		return this.periodTime ;
	}
			
			
//有效时间数字型
@Column(name="PERIOD_TIME_LONG_")
	private Long periodTimeLong;
	
	public void setPeriodTimeLong(Long periodTimeLong) {
		this.periodTimeLong=periodTimeLong;
	}
	public Long getPeriodTimeLong() {
		return this.periodTimeLong ;
	}
			
			
//DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
@Column(name="STATE_")
	private String state;
	
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
	}
			
			
//创建者CREATE_USER_
@Column(name="CREATE_USER_")
	private String createUser;
	
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public String getCreateUser() {
		return this.createUser ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//创建时间
@Column(name="CREATE_TIME_")
	private Date createTime;
	
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
	}
	public Date getCreateTime() {
		return this.createTime ;
	}
			
			
//创建时间数字型CREATE_TIME_LONG_
@Column(name="CREATE_TIME_LONG_")
	private Long createTimeLong;
	
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong ;
	}
			
			
//更新者UPDATE_USER_
@Column(name="UPDATE_USER_")
	private String updateUser;
	
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public String getUpdateUser() {
		return this.updateUser ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//更新时间
@Column(name="UPDATE_TIME_")
	private Date updateTime;
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime=updateTime;
	}
	public Date getUpdateTime() {
		return this.updateTime ;
	}
			
			
//更新时间数字型UPDATE_TIME_LONG_
@Column(name="UPDATE_TIME_LONG_")
	private Long updateTimeLong;
	
	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
			
			
//租户编码TENANT_CODE_
@Column(name="TENANT_CODE_")
	private String tenantCode;
	
	public void setTenantCode(String tenantCode) {
		this.tenantCode=tenantCode;
	}
	public String getTenantCode() {
		return this.tenantCode ;
	}
			
			
//租户编号TENANT_NUMBER_
@Column(name="TENANT_NUMBER_")
	private Integer tenantNumber;
	
	public void setTenantNumber(Integer tenantNumber) {
		this.tenantNumber=tenantNumber;
	}
	public Integer getTenantNumber() {
		return this.tenantNumber ;
	}
			
			
//描述
@Column(name="DESC_")
	private String desc;
	
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}

	private String tableNameMy;
	/*
	 * 不映射
	 * 
	 * @return
	 */
	@Transient
	public String getTableNameMy() {
		return tableNameMy;
	}

	public void setTableNameMy(String tableNameMy) {
		this.tableNameMy = tableNameMy;
	}

}