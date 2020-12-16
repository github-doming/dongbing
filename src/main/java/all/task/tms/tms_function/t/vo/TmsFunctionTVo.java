package all.task.tms.tms_function.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table tms_function 
 * vo类
 */

public class TmsFunctionTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//TMS功能接口表主键TMS_FUNCTION_ID_
	private String tmsFunctionId;
	public void setTmsFunctionId(String tmsFunctionId) {
		this.tmsFunctionId=tmsFunctionId;
	}
	public String getTmsFunctionId() {
		return this.tmsFunctionId ;
	}
	
//TMS项目表主键TMS_PROJECT_ID_
	private String tmsProjectId;
	public void setTmsProjectId(String tmsProjectId) {
		this.tmsProjectId=tmsProjectId;
	}
	public String getTmsProjectId() {
		return this.tmsProjectId ;
	}
	
//项目名称PROJECT_NAME_
	private String projectName;
	public void setProjectName(String projectName) {
		this.projectName=projectName;
	}
	public String getProjectName() {
		return this.projectName ;
	}
	
//前端开发人员主键(APP用户主键)
	private String frontDeveloperId;
	public void setFrontDeveloperId(String frontDeveloperId) {
		this.frontDeveloperId=frontDeveloperId;
	}
	public String getFrontDeveloperId() {
		return this.frontDeveloperId ;
	}
	
//前端开发人员名称FRONT_DEVELOPER_NAME_
	private String frontDeveloperName;
	public void setFrontDeveloperName(String frontDeveloperName) {
		this.frontDeveloperName=frontDeveloperName;
	}
	public String getFrontDeveloperName() {
		return this.frontDeveloperName ;
	}
	
//后台开发人员主键(APP用户主键)
	private String backendDeveloperId;
	public void setBackendDeveloperId(String backendDeveloperId) {
		this.backendDeveloperId=backendDeveloperId;
	}
	public String getBackendDeveloperId() {
		return this.backendDeveloperId ;
	}
	
//后台开发人员名称BACKEND_DEVELOPER_NAME_
	private String backendDeveloperName;
	public void setBackendDeveloperName(String backendDeveloperName) {
		this.backendDeveloperName=backendDeveloperName;
	}
	public String getBackendDeveloperName() {
		return this.backendDeveloperName ;
	}
	
//功能接口名称NAME_
	private String name;
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
	
//接口定义API_DEF_
	private String apiDef;
	public void setApiDef(String apiDef) {
		this.apiDef=apiDef;
	}
	public String getApiDef() {
		return this.apiDef ;
	}
	
//接口示例API_EXAMPLE
	private String apiExample;
	public void setApiExample(String apiExample) {
		this.apiExample=apiExample;
	}
	public String getApiExample() {
		return this.apiExample ;
	}
	
//接口参数API_PARAMETER_
	private String apiParameter;
	public void setApiParameter(String apiParameter) {
		this.apiParameter=apiParameter;
	}
	public String getApiParameter() {
		return this.apiParameter ;
	}
	
//接口返回说明API_RETURN_
	private String apiReturn;
	public void setApiReturn(String apiReturn) {
		this.apiReturn=apiReturn;
	}
	public String getApiReturn() {
		return this.apiReturn ;
	}
	
//进度STEP_
	private String step;
	public void setStep(String step) {
		this.step=step;
	}
	public String getStep() {
		return this.step ;
	}
	
//次序
	private String sn;
	public void setSn(String sn) {
		this.sn=sn;
	}
	public String getSn() {
		return this.sn ;
	}
	
//树上一级
	private String parent;
	public void setParent(String parent) {
		this.parent=parent;
	}
	public String getParent() {
		return this.parent ;
	}
	
//树PATH_
	private String path;
	public void setPath(String path) {
		this.path=path;
	}
	public String getPath() {
		return this.path ;
	}
	
//树编码
	private String treeCode;
	public void setTreeCode(String treeCode) {
		this.treeCode=treeCode;
	}
	public String getTreeCode() {
		return this.treeCode ;
	}
	
//类型TYPE_
	private String type;
	public void setType(String type) {
		this.type=type;
	}
	public String getType() {
		return this.type ;
	}
	
//查看地址URL_VIEW_
	private String urlView;
	public void setUrlView(String urlView) {
		this.urlView=urlView;
	}
	public String getUrlView() {
		return this.urlView ;
	}
	
//创建者CREATE_USER_
	private String createUser;
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public String getCreateUser() {
		return this.createUser ;
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
	
//更新者UPDATE_USER_
	private String updateUser;
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public String getUpdateUser() {
		return this.updateUser ;
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
	
//租户编码TENANT_CODE_
	private String tenantCode;
	public void setTenantCode(String tenantCode) {
		this.tenantCode=tenantCode;
	}
	public String getTenantCode() {
		return this.tenantCode ;
	}
	
//租户编号TENANT_NUMBER_
	private String tenantNumber;
	public void setTenantNumber(String tenantNumber) {
		this.tenantNumber=tenantNumber;
	}
	public String getTenantNumber() {
		return this.tenantNumber ;
	}


}