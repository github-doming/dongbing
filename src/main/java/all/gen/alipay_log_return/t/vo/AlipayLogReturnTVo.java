package all.gen.alipay_log_return.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table alipay_log_return 
 * vo类
 */

public class AlipayLogReturnTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//支付宝支付日志_异步_接口返回主键ALIPAY_LOGRETURN_ID_
	private String alipayLogReturnId;
	public void setAlipayLogReturnId(String alipayLogReturnId) {
		this.alipayLogReturnId=alipayLogReturnId;
	}
	public String getAlipayLogReturnId() {
		return this.alipayLogReturnId ;
	}
	
//跟踪请求或回执TRACE_ID_
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
	
//没有解密的数据DATA_
	private String data;
	public void setData(String data) {
		this.data=data;
	}
	public String getData() {
		return this.data ;
	}
	
//REQUEST_URL_
	private String requestUrl;
	public void setRequestUrl(String requestUrl) {
		this.requestUrl=requestUrl;
	}
	public String getRequestUrl() {
		return this.requestUrl ;
	}
	
//REQUEST_URI_
	private String requestUri;
	public void setRequestUri(String requestUri) {
		this.requestUri=requestUri;
	}
	public String getRequestUri() {
		return this.requestUri ;
	}
	
//REQUEST_PARAMETER_
	private String requestParameter;
	public void setRequestParameter(String requestParameter) {
		this.requestParameter=requestParameter;
	}
	public String getRequestParameter() {
		return this.requestParameter ;
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
	
//显示版本VERSION_
	private String version;
	public void setVersion(String version) {
		this.version=version;
	}
	public String getVersion() {
		return this.version ;
	}
	
//CMD_
	private String cmd;
	public void setCmd(String cmd) {
		this.cmd=cmd;
	}
	public String getCmd() {
		return this.cmd ;
	}
	
//返回码
	private String respCode;
	public void setRespCode(String respCode) {
		this.respCode=respCode;
	}
	public String getRespCode() {
		return this.respCode ;
	}
	
//返回信息
	private String respMsg;
	public void setRespMsg(String respMsg) {
		this.respMsg=respMsg;
	}
	public String getRespMsg() {
		return this.respMsg ;
	}


}