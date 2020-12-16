package all.workflow.actwf.actwf_business_def.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table actwf_business_def 
 * vo类
 */

public class ActwfBusinessDefTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//工作流业务定义主键WF_BUSINESS_DEF_ID_
	private String actwfBusinessDefId;
	public void setActwfBusinessDefId(String actwfBusinessDefId) {
		this.actwfBusinessDefId=actwfBusinessDefId;
	}
	public String getActwfBusinessDefId() {
		return this.actwfBusinessDefId ;
	}
	
//英文标识KEY_
	private String key;
	public void setKey(String key) {
		this.key=key;
	}
	public String getKey() {
		return this.key ;
	}
	
//完成的步骤
	private String step;
	public void setStep(String step) {
		this.step=step;
	}
	public String getStep() {
		return this.step ;
	}
	
//名称NAME_
	private String name;
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
	
//工作流流程定义主健WF_FLOW_DEF_ID_
	private String wfFlowDefId;
	public void setWfFlowDefId(String wfFlowDefId) {
		this.wfFlowDefId=wfFlowDefId;
	}
	public String getWfFlowDefId() {
		return this.wfFlowDefId ;
	}
	
//工作流表单定义主键WF_FORM_DEF_ID_
	private String wfFormDefId;
	public void setWfFormDefId(String wfFormDefId) {
		this.wfFormDefId=wfFormDefId;
	}
	public String getWfFormDefId() {
		return this.wfFormDefId ;
	}
	
//ACT流程定义ID
	private String actDefId;
	public void setActDefId(String actDefId) {
		this.actDefId=actDefId;
	}
	public String getActDefId() {
		return this.actDefId ;
	}
	
//绑定流程KEY
	private String actDefKey;
	public void setActDefKey(String actDefKey) {
		this.actDefKey=actDefKey;
	}
	public String getActDefKey() {
		return this.actDefKey ;
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
	
//次序
	private String sn;
	public void setSn(String sn) {
		this.sn=sn;
	}
	public String getSn() {
		return this.sn ;
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