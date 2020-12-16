package all.workflow.actwf.actwf_flow_def.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table actwf_flow_def 
 * vo类
 */

public class ActwfFlowDefTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//工作流流程定义主键ACTWF_FLOW_DEF_ID_
	private String actwfFlowDefId;
	public void setActwfFlowDefId(String actwfFlowDefId) {
		this.actwfFlowDefId=actwfFlowDefId;
	}
	public String getActwfFlowDefId() {
		return this.actwfFlowDefId ;
	}
	
//名称
	private String name;
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
	
//英文标识KEY_
	private String key;
	public void setKey(String key) {
		this.key=key;
	}
	public String getKey() {
		return this.key ;
	}
	
//ACT流程定义ID
	private String actDefId;
	public void setActDefId(String actDefId) {
		this.actDefId=actDefId;
	}
	public String getActDefId() {
		return this.actDefId ;
	}
	
//ACT流程发布ID
	private String actDepId;
	public void setActDepId(String actDepId) {
		this.actDepId=actDepId;
	}
	public String getActDepId() {
		return this.actDepId ;
	}
	
//设计模型ID,关联activity中的ACT_RE_MODEL表主键
	private String actModelId;
	public void setActModelId(String actModelId) {
		this.actModelId=actModelId;
	}
	public String getActModelId() {
		return this.actModelId ;
	}
	
//显示版本EDITION_
	private String edition;
	public void setEdition(String edition) {
		this.edition=edition;
	}
	public String getEdition() {
		return this.edition ;
	}
	
//是否主版本
	private String mainEdition;
	public void setMainEdition(String mainEdition) {
		this.mainEdition=mainEdition;
	}
	public String getMainEdition() {
		return this.mainEdition ;
	}
	
//定义属性设置
	private String setting;
	public void setSetting(String setting) {
		this.setting=setting;
	}
	public String getSetting() {
		return this.setting ;
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
	
//次序
	private String sn;
	public void setSn(String sn) {
		this.sn=sn;
	}
	public String getSn() {
		return this.sn ;
	}


}