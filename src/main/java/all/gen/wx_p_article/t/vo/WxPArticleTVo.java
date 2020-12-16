package all.gen.wx_p_article.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table wx_p_article 
 * vo类
 */

public class WxPArticleTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//微信公众号文章主键WX_P_ARTICLE_ID_
	private String wxPArticleId;
	public void setWxPArticleId(String wxPArticleId) {
		this.wxPArticleId=wxPArticleId;
	}
	public String getWxPArticleId() {
		return this.wxPArticleId ;
	}
	
//作者
	private String author;
	public void setAuthor(String author) {
		this.author=author;
	}
	public String getAuthor() {
		return this.author ;
	}
	
//标题
	private String title;
	public void setTitle(String title) {
		this.title=title;
	}
	public String getTitle() {
		return this.title ;
	}
	
//原文章地址
	private String contentSourceUrl;
	public void setContentSourceUrl(String contentSourceUrl) {
		this.contentSourceUrl=contentSourceUrl;
	}
	public String getContentSourceUrl() {
		return this.contentSourceUrl ;
	}
	
//图文消息页面的内容
	private String content;
	public void setContent(String content) {
		this.content=content;
	}
	public String getContent() {
		return this.content ;
	}
	
//是否显示封面
	private String showCoverPic;
	public void setShowCoverPic(String showCoverPic) {
		this.showCoverPic=showCoverPic;
	}
	public String getShowCoverPic() {
		return this.showCoverPic ;
	}
	
//MATERIAL_ID_
	private String materialId;
	public void setMaterialId(String materialId) {
		this.materialId=materialId;
	}
	public String getMaterialId() {
		return this.materialId ;
	}
	
//后台文件表主键SYS_FILE_ID_
	private String sysFileId;
	public void setSysFileId(String sysFileId) {
		this.sysFileId=sysFileId;
	}
	public String getSysFileId() {
		return this.sysFileId ;
	}
	
//创建时间 CREATE_TIME_
	private String createTime;
	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}
	public String getCreateTime() {
		return this.createTime ;
	}
	
//创建时间数字型 CREATE_TIME_LONG_
	private String createTimeLong;
	public void setCreateTimeLong(String createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public String getCreateTimeLong() {
		return this.createTimeLong ;
	}
	
//更新时间
	private String updateTimeLong;
	public void setUpdateTimeLong(String updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public String getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
	
//更新时间
	private String updateTime;
	public void setUpdateTime(String updateTime) {
		this.updateTime=updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime ;
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