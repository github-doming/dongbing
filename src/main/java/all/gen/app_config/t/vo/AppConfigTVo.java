package all.gen.app_config.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table app_config 
 * vo类
 */

public class AppConfigTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//主键APP_CONFIG_ID_
	private String appConfigId;
	public void setAppConfigId(String appConfigId) {
		this.appConfigId=appConfigId;
	}
	public String getAppConfigId() {
		return this.appConfigId ;
	}
	
//APP应用名称NAME_
	private String name;
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
	
//APP版本VERSION_
	private String version;
	public void setVersion(String version) {
		this.version=version;
	}
	public String getVersion() {
		return this.version ;
	}
	
//APP版本(中文)VERSION_CN_
	private String versionCn;
	public void setVersionCn(String versionCn) {
		this.versionCn=versionCn;
	}
	public String getVersionCn() {
		return this.versionCn ;
	}
	
//APP下载地址URL_DOWNLOAD_APK_
	private String urlDownloadApk;
	public void setUrlDownloadApk(String urlDownloadApk) {
		this.urlDownloadApk=urlDownloadApk;
	}
	public String getUrlDownloadApk() {
		return this.urlDownloadApk ;
	}
	
//APP下载地址(二维码图片)URL_DOWNLOAD_IMG_
	private String urlDownloadImg;
	public void setUrlDownloadImg(String urlDownloadImg) {
		this.urlDownloadImg=urlDownloadImg;
	}
	public String getUrlDownloadImg() {
		return this.urlDownloadImg ;
	}
	
//APP下载地址(IOS)URL_DOWNLOAD_IOS_APK_
	private String urlDownloadIosApk;
	public void setUrlDownloadIosApk(String urlDownloadIosApk) {
		this.urlDownloadIosApk=urlDownloadIosApk;
	}
	public String getUrlDownloadIosApk() {
		return this.urlDownloadIosApk ;
	}
	
//APP下载地址(二维码图片)(IOS)URL_DOWNLOAD_IOS_IMG_
	private String urlDownloadIosImg;
	public void setUrlDownloadIosImg(String urlDownloadIosImg) {
		this.urlDownloadIosImg=urlDownloadIosImg;
	}
	public String getUrlDownloadIosImg() {
		return this.urlDownloadIosImg ;
	}
	
//客服微信CUSTOMER_SERVICE_WEIXIN_
	private String customerServiceWeixin;
	public void setCustomerServiceWeixin(String customerServiceWeixin) {
		this.customerServiceWeixin=customerServiceWeixin;
	}
	public String getCustomerServiceWeixin() {
		return this.customerServiceWeixin ;
	}
	
//创建时间
	private String createTime;
	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}
	public String getCreateTime() {
		return this.createTime ;
	}
	
//创建时间
	private String createTimeLong;
	public void setCreateTimeLong(String createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public String getCreateTimeLong() {
		return this.createTimeLong ;
	}
	
//更新时间
	private String updateTime;
	public void setUpdateTime(String updateTime) {
		this.updateTime=updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime ;
	}
	
//更新时间
	private String updateTimeLong;
	public void setUpdateTimeLong(String updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public String getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
	
//DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	private String state;
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
	}
	
//描述
	private String desc;
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}


}