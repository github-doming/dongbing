package all.gen.sys_log_response.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table sys_log_response 
 * vo类
 */

public class SysLogResponseTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
// 后台日志_返回主键SYS_LOG_RESPONSE_ID_
	private String sysLogResponseId;
	public void setSysLogResponseId(String sysLogResponseId) {
		this.sysLogResponseId=sysLogResponseId;
	}
	public String getSysLogResponseId() {
		return this.sysLogResponseId ;
	}
	
//名称
	private String name;
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
	
//追踪贯穿父ID SPAN_PARENT_ID_
	private String spanParentId;
	public void setSpanParentId(String spanParentId) {
		this.spanParentId=spanParentId;
	}
	public String getSpanParentId() {
		return this.spanParentId ;
	}
	
//追踪贯穿ID SPAN_ID_
	private String spanId;
	public void setSpanId(String spanId) {
		this.spanId=spanId;
	}
	public String getSpanId() {
		return this.spanId ;
	}
	
//追踪请求或回执TRACE_ID_
	private String traceId;
	public void setTraceId(String traceId) {
		this.traceId=traceId;
	}
	public String getTraceId() {
		return this.traceId ;
	}
	
//JSON_
	private String json;
	public void setJson(String json) {
		this.json=json;
	}
	public String getJson() {
		return this.json ;
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
	
//网络协议NETWORK_PROTOCOL_
	private String networkProtocol;
	public void setNetworkProtocol(String networkProtocol) {
		this.networkProtocol=networkProtocol;
	}
	public String getNetworkProtocol() {
		return this.networkProtocol ;
	}
	
//SERVLET_PATH_
	private String servletPath;
	public void setServletPath(String servletPath) {
		this.servletPath=servletPath;
	}
	public String getServletPath() {
		return this.servletPath ;
	}
	
//请求类型REQUEST_TYPE_
	private String requestType;
	public void setRequestType(String requestType) {
		this.requestType=requestType;
	}
	public String getRequestType() {
		return this.requestType ;
	}


}