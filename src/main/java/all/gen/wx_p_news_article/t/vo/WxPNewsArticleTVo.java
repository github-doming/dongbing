package all.gen.wx_p_news_article.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table wx_p_news_article 
 * vo类
 */

public class WxPNewsArticleTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//微信公众号图文与文章主键
	private String wxPNewsArticleId;
	public void setWxPNewsArticleId(String wxPNewsArticleId) {
		this.wxPNewsArticleId=wxPNewsArticleId;
	}
	public String getWxPNewsArticleId() {
		return this.wxPNewsArticleId ;
	}
	
//图文ID
	private String newsId;
	public void setNewsId(String newsId) {
		this.newsId=newsId;
	}
	public String getNewsId() {
		return this.newsId ;
	}
	
//文章ID
	private String articleId;
	public void setArticleId(String articleId) {
		this.articleId=articleId;
	}
	public String getArticleId() {
		return this.articleId ;
	}
	
//次序
	private String sn;
	public void setSn(String sn) {
		this.sn=sn;
	}
	public String getSn() {
		return this.sn ;
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