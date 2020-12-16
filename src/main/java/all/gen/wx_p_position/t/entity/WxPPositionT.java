package all.gen.wx_p_position.t.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import c.a.util.core.annotation.AnnotationEntity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import c.a.util.core.annotation.AnnotationTable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the database table wx_p_position 
 * 微信公众号地理位置WX_P_POSITION的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "wx_p_position")
public class WxPPositionT implements Serializable {

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
		
			
//微信公众号地理位置主键WX_P_POSITION_ID_
@Column(name="WX_P_POSITION_ID_")
	private String wxPPositionId;
	
	public void setWxPPositionId(String wxPPositionId) {
		this.wxPPositionId=wxPPositionId;
	}
	public String getWxPPositionId() {
		return this.wxPPositionId ;
	}
			
			
//APP用户主键APP_USER_ID_
@Column(name="APP_USER_ID_")
	private String appUserId;
	
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public String getAppUserId() {
		return this.appUserId ;
	}
			
			
//姓名
@Column(name="FULLNAME_")
	private String fullname;
	
	public void setFullname(String fullname) {
		this.fullname=fullname;
	}
	public String getFullname() {
		return this.fullname ;
	}
			
			
//微信号ID
@Column(name="WEIXIN_ID_")
	private String weixinId;
	
	public void setWeixinId(String weixinId) {
		this.weixinId=weixinId;
	}
	public String getWeixinId() {
		return this.weixinId ;
	}
			
			
//OPEN_ID_
@Column(name="OPEN_ID_")
	private String openId;
	
	public void setOpenId(String openId) {
		this.openId=openId;
	}
	public String getOpenId() {
		return this.openId ;
	}
			
			
//LATITUDE_
@Column(name="LATITUDE_")
	private Double latitude;
	
	public void setLatitude(Double latitude) {
		this.latitude=latitude;
	}
	public Double getLatitude() {
		return this.latitude ;
	}
			
			
//LONGITUDE_
@Column(name="LONGITUDE_")
	private Double longitude;
	
	public void setLongitude(Double longitude) {
		this.longitude=longitude;
	}
	public Double getLongitude() {
		return this.longitude ;
	}
			
			
//SYS_PRECISION_
@Column(name="SYS_PRECISION_")
	private Double sysPrecision;
	
	public void setSysPrecision(Double sysPrecision) {
		this.sysPrecision=sysPrecision;
	}
	public Double getSysPrecision() {
		return this.sysPrecision ;
	}
			
			
//微信用户昵称
@Column(name="NICK_NAME_")
	private String nickName;
	
	public void setNickName(String nickName) {
		this.nickName=nickName;
	}
	public String getNickName() {
		return this.nickName ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//创建时间 CREATE_TIME_
@Column(name="CREATE_TIME_")
	private Date createTime;
	
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
	}
	public Date getCreateTime() {
		return this.createTime ;
	}
			
			
//创建时间数字型 CREATE_TIME_LONG_
@Column(name="CREATE_TIME_LONG_")
	private Long createTimeLong;
	
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong ;
	}
			
			
//更新时间
@Column(name="UPDATE_TIME_LONG_")
	private Long updateTimeLong;
	
	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong ;
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
			
			
//DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
@Column(name="STATE_")
	private String state;
	
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
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