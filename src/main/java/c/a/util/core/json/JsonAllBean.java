package c.a.util.core.json;
import java.util.List;
import java.util.Map;
import io.netty.channel.Channel;
/**
 * 返回的Json格式字符串,包括是否成功的状态及返回的格式字符串
 * 
 * @author cxy
 * @Email:
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 * 
 */
public class JsonAllBean {
	private String channelId;
	private Channel channel;
	// 追踪ID
	private String traceId = null;
	// 追踪贯穿ID SPAN_ID_
	private String spanId = null;
	// 追踪贯穿父ID SPAN_PARENT_ID_
	private String spanParentId = null;
	// 请求类型REQUEST_TYPE_
	private String requestType;
	// 自己的数据
	private Object dataSelf = null;
	// 所有用户的数据
	private Object data = null;
	private List<String> userSend = null;
	private Map<String, Object> userSendMap = null;
	private String url;
	private String urlSend;
	private String mvcResult;
	private String token;
	private String appUserId;
	protected boolean success = false;
	protected String code = null;
	protected String msg = null;
	private String codeSys = null;
	protected String msgSys = null;
	private String message = null;
	// 返回数据的记录数;
	private Integer total = 0;
	private String state = null;
	//private String status = null;
	private String desc = null;
	// 返回数据的序号;
	private Integer id = 0;
	private String command = null;
	private String commandCn = null;
	// 日志项目名称PROJECT_NAME_
	private String projectName;
	// 日志模块名称MODULAR_NAME_
	private String modularName;
	// 日志请求功能编码REQUEST_FUNCTION_CODE_
	private String requestFunctionCode;
	// 日志请求功能名称REQUEST_FUNCTION_NAME_
	private String requestFunctionName;
	// 日志返回功能编码RESPONSE_FUNCTION_CODE_
	private String responseFunctionCode;
	// 日志返回功能名称RESPONSE_FUNCTION_NAME_
	private String responseFunctionName;
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public String getTraceId() {
		return traceId;
	}
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	public String getSpanId() {
		return spanId;
	}
	public void setSpanId(String spanId) {
		this.spanId = spanId;
	}
	public String getSpanParentId() {
		return spanParentId;
	}
	public void setSpanParentId(String spanParentId) {
		this.spanParentId = spanParentId;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public Object getDataSelf() {
		return dataSelf;
	}
	public void setDataSelf(Object dataSelf) {
		this.dataSelf = dataSelf;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public List<String> getUserSend() {
		return userSend;
	}
	public void setUserSend(List<String> userSend) {
		this.userSend = userSend;
	}
	public Map<String, Object> getUserSendMap() {
		return userSendMap;
	}
	public void setUserSendMap(Map<String, Object> userSendMap) {
		this.userSendMap = userSendMap;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrlSend() {
		return urlSend;
	}
	public void setUrlSend(String urlSend) {
		this.urlSend = urlSend;
	}
	public String getMvcResult() {
		return mvcResult;
	}
	public void setMvcResult(String mvcResult) {
		this.mvcResult = mvcResult;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCodeSys() {
		return codeSys;
	}
	public void setCodeSys(String codeSys) {
		this.codeSys = codeSys;
	}
	public String getMsgSys() {
		return msgSys;
	}
	public void setMsgSys(String msgSys) {
		this.msgSys = msgSys;
	}
	public String getMessage() {
		// return message;
		return msg;
	}
	public void setMessage(String message) {
		// this.message = message;
		this.msg = message;
	}
	public String getMessageSys() {
		return msgSys;
	}
	public void setMessageSys(String messageSys) {
		this.msgSys = messageSys;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	// public String getStatus() {
	// return status;
	// }
	// public void setStatus(String status) {
	// this.status = status;
	// }
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getCommandCn() {
		return commandCn;
	}
	public void setCommandCn(String commandCn) {
		this.commandCn = commandCn;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getModularName() {
		return modularName;
	}
	public void setModularName(String modularName) {
		this.modularName = modularName;
	}
	public String getRequestFunctionCode() {
		return requestFunctionCode;
	}
	public void setRequestFunctionCode(String requestFunctionCode) {
		this.requestFunctionCode = requestFunctionCode;
	}
	public String getRequestFunctionName() {
		return requestFunctionName;
	}
	public void setRequestFunctionName(String requestFunctionName) {
		this.requestFunctionName = requestFunctionName;
	}
	public String getResponseFunctionCode() {
		return responseFunctionCode;
	}
	public void setResponseFunctionCode(String responseFunctionCode) {
		this.responseFunctionCode = responseFunctionCode;
	}
	public String getResponseFunctionName() {
		return responseFunctionName;
	}
	public void setResponseFunctionName(String responseFunctionName) {
		this.responseFunctionName = responseFunctionName;
	}
	public JsonAllBean() {
		super();
	}
	public JsonAllBean(boolean success, String code, String msg) {
		super();
		this.success = success;
		this.code = code;
		this.msg = msg;
	}
	public JsonAllBean(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}
}