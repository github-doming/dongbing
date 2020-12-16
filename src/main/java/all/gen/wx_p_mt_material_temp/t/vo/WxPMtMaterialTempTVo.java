package all.gen.wx_p_mt_material_temp.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table wx_p_mt_material_temp 
 * vo类
 */

public class WxPMtMaterialTempTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//微信公众号下行临时素材主键
	private String wxPMtMaterialTempId;
	public void setWxPMtMaterialTempId(String wxPMtMaterialTempId) {
		this.wxPMtMaterialTempId=wxPMtMaterialTempId;
	}
	public String getWxPMtMaterialTempId() {
		return this.wxPMtMaterialTempId ;
	}
	
//名称
	private String name;
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
	
//???体文件类型
	private String type;
	public void setType(String type) {
		this.type=type;
	}
	public String getType() {
		return this.type ;
	}
	
//媒体id
	private String mediaId;
	public void setMediaId(String mediaId) {
		this.mediaId=mediaId;
	}
	public String getMediaId() {
		return this.mediaId ;
	}
	
//微信创建时间
	private String createdAt;
	public void setCreatedAt(String createdAt) {
		this.createdAt=createdAt;
	}
	public String getCreatedAt() {
		return this.createdAt ;
	}
	
//文件id
	private String materialId;
	public void setMaterialId(String materialId) {
		this.materialId=materialId;
	}
	public String getMaterialId() {
		return this.materialId ;
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