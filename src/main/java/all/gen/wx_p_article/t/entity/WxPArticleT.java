package all.gen.wx_p_article.t.entity;

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
 * The persistent class for the database table wx_p_article 
 * 微信公众号文章WX_P_ARTICLE的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "wx_p_article")
public class WxPArticleT implements Serializable {

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
		
			
//微信公众号文章主键WX_P_ARTICLE_ID_
@Column(name="WX_P_ARTICLE_ID_")
	private String wxPArticleId;
	
	public void setWxPArticleId(String wxPArticleId) {
		this.wxPArticleId=wxPArticleId;
	}
	public String getWxPArticleId() {
		return this.wxPArticleId ;
	}
			
			
//作者
@Column(name="AUTHOR_")
	private String author;
	
	public void setAuthor(String author) {
		this.author=author;
	}
	public String getAuthor() {
		return this.author ;
	}
			
			
//标题
@Column(name="TITLE_")
	private String title;
	
	public void setTitle(String title) {
		this.title=title;
	}
	public String getTitle() {
		return this.title ;
	}
			
			
//原文章地址
@Column(name="CONTENT_SOURCE_URL_")
	private String contentSourceUrl;
	
	public void setContentSourceUrl(String contentSourceUrl) {
		this.contentSourceUrl=contentSourceUrl;
	}
	public String getContentSourceUrl() {
		return this.contentSourceUrl ;
	}
			
			
//图文消息页面的内容
@Column(name="CONTENT_")
	private String content;
	
	public void setContent(String content) {
		this.content=content;
	}
	public String getContent() {
		return this.content ;
	}
			
			
//是否显示封面
@Column(name="SHOW_COVER_PIC_")
	private String showCoverPic;
	
	public void setShowCoverPic(String showCoverPic) {
		this.showCoverPic=showCoverPic;
	}
	public String getShowCoverPic() {
		return this.showCoverPic ;
	}
			
			
//MATERIAL_ID_
@Column(name="MATERIAL_ID_")
	private String materialId;
	
	public void setMaterialId(String materialId) {
		this.materialId=materialId;
	}
	public String getMaterialId() {
		return this.materialId ;
	}
			
			
//后台文件表主键SYS_FILE_ID_
@Column(name="SYS_FILE_ID_")
	private String sysFileId;
	
	public void setSysFileId(String sysFileId) {
		this.sysFileId=sysFileId;
	}
	public String getSysFileId() {
		return this.sysFileId ;
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