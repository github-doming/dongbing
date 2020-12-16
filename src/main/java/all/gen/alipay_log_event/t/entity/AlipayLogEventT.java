package all.gen.alipay_log_event.t.entity;

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
 * The persistent class for the database table alipay_log_event 
 * 支付宝支付日志_异步_事件通知ALIPAY_LOG_EVENT的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "alipay_log_event")
public class AlipayLogEventT implements Serializable {

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
		
			
//支支付宝支付日志_异步_事件通知主键ALIPAY_LOG_EVENT_ID_
@Column(name="ALIPAY_LOG_EVENT_ID_")
	private String alipayLogEventId;
	
	public void setAlipayLogEventId(String alipayLogEventId) {
		this.alipayLogEventId=alipayLogEventId;
	}
	public String getAlipayLogEventId() {
		return this.alipayLogEventId ;
	}
			
			
//跟踪请求或回执TRACE_ID_
@Column(name="TRACE_ID_")
	private String traceId;
	
	public void setTraceId(String traceId) {
		this.traceId=traceId;
	}
	public String getTraceId() {
		return this.traceId ;
	}
			
			
//JSON_
@Column(name="JSON_")
	private String json;
	
	public void setJson(String json) {
		this.json=json;
	}
	public String getJson() {
		return this.json ;
	}
			
			
//没有解密的数据DATA_
@Column(name="DATA_")
	private String data;
	
	public void setData(String data) {
		this.data=data;
	}
	public String getData() {
		return this.data ;
	}
			
			
//REQUEST_URL_
@Column(name="REQUEST_URL_")
	private String requestUrl;
	
	public void setRequestUrl(String requestUrl) {
		this.requestUrl=requestUrl;
	}
	public String getRequestUrl() {
		return this.requestUrl ;
	}
			
			
//REQUEST_URI_
@Column(name="REQUEST_URI_")
	private String requestUri;
	
	public void setRequestUri(String requestUri) {
		this.requestUri=requestUri;
	}
	public String getRequestUri() {
		return this.requestUri ;
	}
			
			
//REQUEST_PARAMETER_
@Column(name="REQUEST_PARAMETER_")
	private String requestParameter;
	
	public void setRequestParameter(String requestParameter) {
		this.requestParameter=requestParameter;
	}
	public String getRequestParameter() {
		return this.requestParameter ;
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
			
			
//显示版本VERSION_
@Column(name="VERSION_")
	private Long version;
	
	public void setVersion(Long version) {
		this.version=version;
	}
	public Long getVersion() {
		return this.version ;
	}
			
			
//CMD_
@Column(name="CMD_")
	private String cmd;
	
	public void setCmd(String cmd) {
		this.cmd=cmd;
	}
	public String getCmd() {
		return this.cmd ;
	}
			
			
//返回码
@Column(name="RESP_CODE_")
	private String respCode;
	
	public void setRespCode(String respCode) {
		this.respCode=respCode;
	}
	public String getRespCode() {
		return this.respCode ;
	}
			
			
//返回信息
@Column(name="RESP_MSG_")
	private String respMsg;
	
	public void setRespMsg(String respMsg) {
		this.respMsg=respMsg;
	}
	public String getRespMsg() {
		return this.respMsg ;
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