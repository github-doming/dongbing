package all.gen.wx_p_mt_material_temp.t.entity;

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
 * The persistent class for the database table wx_p_mt_material_temp 
 * 微信公众号下行临时素材WX_P_MT_MATERIAL_TEMP的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "wx_p_mt_material_temp")
public class WxPMtMaterialTempT implements Serializable {

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
		
			
//微信公众号下行临时素材主键
@Column(name="WX_P_MT_MATERIAL_TEMP_ID_")
	private String wxPMtMaterialTempId;
	
	public void setWxPMtMaterialTempId(String wxPMtMaterialTempId) {
		this.wxPMtMaterialTempId=wxPMtMaterialTempId;
	}
	public String getWxPMtMaterialTempId() {
		return this.wxPMtMaterialTempId ;
	}
			
			
//名称
@Column(name="NAME_")
	private String name;
	
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
			
			
//???体文件类型
@Column(name="TYPE_")
	private String type;
	
	public void setType(String type) {
		this.type=type;
	}
	public String getType() {
		return this.type ;
	}
			
			
//媒体id
@Column(name="MEDIA_ID_")
	private String mediaId;
	
	public void setMediaId(String mediaId) {
		this.mediaId=mediaId;
	}
	public String getMediaId() {
		return this.mediaId ;
	}
			
			
//微信创建时间
@Column(name="CREATED_AT_")
	private String createdAt;
	
	public void setCreatedAt(String createdAt) {
		this.createdAt=createdAt;
	}
	public String getCreatedAt() {
		return this.createdAt ;
	}
			
			
//文件id
@Column(name="MATERIAL_ID_")
	private String materialId;
	
	public void setMaterialId(String materialId) {
		this.materialId=materialId;
	}
	public String getMaterialId() {
		return this.materialId ;
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