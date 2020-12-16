package all.gen.app_config.t.entity;

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
 * The persistent class for the database table app_config 
 * APP配置表APP_CONFIG的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "app_config")
public class AppConfigT implements Serializable {

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
		
			
//主键APP_CONFIG_ID_
@Column(name="APP_CONFIG_ID_")
	private String appConfigId;
	
	public void setAppConfigId(String appConfigId) {
		this.appConfigId=appConfigId;
	}
	public String getAppConfigId() {
		return this.appConfigId ;
	}
			
			
//APP应用名称NAME_
@Column(name="NAME_")
	private String name;
	
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
			
			
//APP版本VERSION_
@Column(name="VERSION_")
	private Long version;
	
	public void setVersion(Long version) {
		this.version=version;
	}
	public Long getVersion() {
		return this.version ;
	}
			
			
//APP版本(中文)VERSION_CN_
@Column(name="VERSION_CN_")
	private String versionCn;
	
	public void setVersionCn(String versionCn) {
		this.versionCn=versionCn;
	}
	public String getVersionCn() {
		return this.versionCn ;
	}
			
			
//APP下载地址URL_DOWNLOAD_APK_
@Column(name="URL_DOWNLOAD_APK_")
	private String urlDownloadApk;
	
	public void setUrlDownloadApk(String urlDownloadApk) {
		this.urlDownloadApk=urlDownloadApk;
	}
	public String getUrlDownloadApk() {
		return this.urlDownloadApk ;
	}
			
			
//APP下载地址(二维码图片)URL_DOWNLOAD_IMG_
@Column(name="URL_DOWNLOAD_IMG_")
	private String urlDownloadImg;
	
	public void setUrlDownloadImg(String urlDownloadImg) {
		this.urlDownloadImg=urlDownloadImg;
	}
	public String getUrlDownloadImg() {
		return this.urlDownloadImg ;
	}
			
			
//APP下载地址(IOS)URL_DOWNLOAD_IOS_APK_
@Column(name="URL_DOWNLOAD_IOS_APK_")
	private String urlDownloadIosApk;
	
	public void setUrlDownloadIosApk(String urlDownloadIosApk) {
		this.urlDownloadIosApk=urlDownloadIosApk;
	}
	public String getUrlDownloadIosApk() {
		return this.urlDownloadIosApk ;
	}
			
			
//APP下载地址(二维码图片)(IOS)URL_DOWNLOAD_IOS_IMG_
@Column(name="URL_DOWNLOAD_IOS_IMG_")
	private String urlDownloadIosImg;
	
	public void setUrlDownloadIosImg(String urlDownloadIosImg) {
		this.urlDownloadIosImg=urlDownloadIosImg;
	}
	public String getUrlDownloadIosImg() {
		return this.urlDownloadIosImg ;
	}
	

			
//客服微信CUSTOMER_SERVICE_WEIXIN_
@Column(name="CUSTOMER_SERVICE_WEIXIN_")
	private String customerServiceWeixin;
	
	public void setCustomerServiceWeixin(String customerServiceWeixin) {
		this.customerServiceWeixin=customerServiceWeixin;
	}
	public String getCustomerServiceWeixin() {
		return this.customerServiceWeixin ;
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
			
			
//创建时间
@Column(name="CREATE_TIME_LONG_")
	private Long createTimeLong;
	
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong ;
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
			
			
//更新时间
@Column(name="UPDATE_TIME_LONG_")
	private Long updateTimeLong;
	
	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong ;
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