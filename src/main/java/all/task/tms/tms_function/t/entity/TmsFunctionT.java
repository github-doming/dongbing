package all.task.tms.tms_function.t.entity;

import java.util.List;
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
 * The persistent class for the database table tms_function 
 * TMS功能接口表
的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "tms_function")
public class TmsFunctionT implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<TmsFunctionT > children;
	public List<TmsFunctionT> getChildren() {
		return children;
	}
	public void setChildren(List<TmsFunctionT> children) {
		this.children = children;
	}
				
			
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
		
			
//TMS功能接口表主键TMS_FUNCTION_ID_
@Column(name="TMS_FUNCTION_ID_")
	private String tmsFunctionId;
	
	public void setTmsFunctionId(String tmsFunctionId) {
		this.tmsFunctionId=tmsFunctionId;
	}
	public String getTmsFunctionId() {
		return this.tmsFunctionId ;
	}
			
			
//TMS项目表主键TMS_PROJECT_ID_
@Column(name="TMS_PROJECT_ID_")
	private String tmsProjectId;
	
	public void setTmsProjectId(String tmsProjectId) {
		this.tmsProjectId=tmsProjectId;
	}
	public String getTmsProjectId() {
		return this.tmsProjectId ;
	}
			
			
//项目名称PROJECT_NAME_
@Column(name="PROJECT_NAME_")
	private String projectName;
	
	public void setProjectName(String projectName) {
		this.projectName=projectName;
	}
	public String getProjectName() {
		return this.projectName ;
	}
			
			
//前端开发人员主键(APP用户主键)
@Column(name="FRONT_DEVELOPER_ID_")
	private String frontDeveloperId;
	
	public void setFrontDeveloperId(String frontDeveloperId) {
		this.frontDeveloperId=frontDeveloperId;
	}
	public String getFrontDeveloperId() {
		return this.frontDeveloperId ;
	}
			
			
//前端开发人员名称FRONT_DEVELOPER_NAME_
@Column(name="FRONT_DEVELOPER_NAME_")
	private String frontDeveloperName;
	
	public void setFrontDeveloperName(String frontDeveloperName) {
		this.frontDeveloperName=frontDeveloperName;
	}
	public String getFrontDeveloperName() {
		return this.frontDeveloperName ;
	}
			
			
//后台开发人员主键(APP用户主键)
@Column(name="BACKEND_DEVELOPER_ID_")
	private String backendDeveloperId;
	
	public void setBackendDeveloperId(String backendDeveloperId) {
		this.backendDeveloperId=backendDeveloperId;
	}
	public String getBackendDeveloperId() {
		return this.backendDeveloperId ;
	}
			
			
//后台开发人员名称BACKEND_DEVELOPER_NAME_
@Column(name="BACKEND_DEVELOPER_NAME_")
	private String backendDeveloperName;
	
	public void setBackendDeveloperName(String backendDeveloperName) {
		this.backendDeveloperName=backendDeveloperName;
	}
	public String getBackendDeveloperName() {
		return this.backendDeveloperName ;
	}
			
			
//功能接口名称NAME_
@Column(name="NAME_")
	private String name;
	
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
			
			
//接口定义API_DEF_
@Column(name="API_DEF_")
	private String apiDef;
	
	public void setApiDef(String apiDef) {
		this.apiDef=apiDef;
	}
	public String getApiDef() {
		return this.apiDef ;
	}
			
			
//接口示例API_EXAMPLE
@Column(name="API_EXAMPLE_")
	private String apiExample;
	
	public void setApiExample(String apiExample) {
		this.apiExample=apiExample;
	}
	public String getApiExample() {
		return this.apiExample ;
	}
			
			
//接口参数API_PARAMETER_
@Column(name="API_PARAMETER_")
	private String apiParameter;
	
	public void setApiParameter(String apiParameter) {
		this.apiParameter=apiParameter;
	}
	public String getApiParameter() {
		return this.apiParameter ;
	}
			
			
//接口返回说明API_RETURN_
@Column(name="API_RETURN_")
	private String apiReturn;
	
	public void setApiReturn(String apiReturn) {
		this.apiReturn=apiReturn;
	}
	public String getApiReturn() {
		return this.apiReturn ;
	}
			
			
//进度STEP_
@Column(name="STEP_")
	private String step;
	
	public void setStep(String step) {
		this.step=step;
	}
	public String getStep() {
		return this.step ;
	}
			
			
//次序
@Column(name="SN_")
	private Integer sn;
	
	public void setSn(Integer sn) {
		this.sn=sn;
	}
	public Integer getSn() {
		return this.sn ;
	}
			
			
//树上一级
@Column(name="PARENT_")
	private String parent;
	
	public void setParent(String parent) {
		this.parent=parent;
	}
	public String getParent() {
		return this.parent ;
	}
			
			
//树PATH_
@Column(name="PATH_")
	private String path;
	
	public void setPath(String path) {
		this.path=path;
	}
	public String getPath() {
		return this.path ;
	}
			
			
//树编码
@Column(name="TREE_CODE_")
	private String treeCode;
	
	public void setTreeCode(String treeCode) {
		this.treeCode=treeCode;
	}
	public String getTreeCode() {
		return this.treeCode ;
	}
			
			
//类型TYPE_
@Column(name="TYPE_")
	private String type;
	
	public void setType(String type) {
		this.type=type;
	}
	public String getType() {
		return this.type ;
	}
			
			
//查看地址URL_VIEW_
@Column(name="URL_VIEW_")
	private String urlView;
	
	public void setUrlView(String urlView) {
		this.urlView=urlView;
	}
	public String getUrlView() {
		return this.urlView ;
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
			
			
//创建时间
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