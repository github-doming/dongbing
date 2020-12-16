package c.a.util.core.log;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.helper.StringUtil;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.uuid.Uuid;
public class LogThreadLocal {
	/**
	 * 日志
	 */
	private static ThreadLocal<List<JsonTcpBean>> logThreadLocalList = null;
	/**
	 * 日志(主要的)
	 */
	private static ThreadLocal<JsonTcpBean> logThreadLocalLast = null;
	// -- set与get --//
	public static ThreadLocal<List<JsonTcpBean>> getLogThreadLocalList() {
		return logThreadLocalList;
	}
	public static void setLogThreadLocalList(ThreadLocal<List<JsonTcpBean>> logThreadLocalList) {
		LogThreadLocal.logThreadLocalList = logThreadLocalList;
	}
	public static ThreadLocal<JsonTcpBean> getLogThreadLocalLast() {
		return logThreadLocalLast;
	}
	public static void setLogThreadLocalLast(ThreadLocal<JsonTcpBean> logThreadLocalLast) {
		LogThreadLocal.logThreadLocalLast = logThreadLocalLast;
	}
	// -- set与get --//
	public static ThreadLocal<List<JsonTcpBean>> findLogThreadLocalList() {
		if (logThreadLocalList == null) {
			logThreadLocalList = new ThreadLocal<List<JsonTcpBean>>();
		}
		return logThreadLocalList;
	}
	public static ThreadLocal<JsonTcpBean> findLogThreadLocalLast() {
		if (logThreadLocalLast == null) {
			logThreadLocalLast = new ThreadLocal<JsonTcpBean>();
		}
		return logThreadLocalLast;
	}
	public static List<JsonTcpBean> findLogList() {
		List<JsonTcpBean> logList = findLogThreadLocalList().get();
		if (logList == null || logList.size() == 0) {
			logList = new ArrayList<JsonTcpBean>();
		}
		return logList;
	}
	/**
	 * 线程保存Log
	 * 
	 * @Description
	 * @Title setLog
	 * @param Log
	 *            参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public static void setLog(JsonTcpBean _JsonTcpBean) {
		List<JsonTcpBean> LogList = findLogList();
		LogList.add(_JsonTcpBean);
		findLogThreadLocalList().set(LogList);
		//最后一条Log
		findLogThreadLocalLast().set(_JsonTcpBean);
	}
	/**
	 * 线程保存Log
	 * 
	 * @Description
	 * @Title setLog
	 * @param success
	 * @param msg
	 *            参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public static void setLog(boolean success, String msg) {
		JsonTcpBean log = new JsonTcpBean(success, msg);
		setLog(log);
	}
	/**
	 * 最后一条Log
	 * @Title: findLog 
	 * @Description: 
	 *
	 * 参数说明 
	 * @return 
	 * 返回类型 JsonTcpBean
	 */
	public static JsonTcpBean findLog() {
		JsonTcpBean _JsonTcpBean=findLogThreadLocalLast().get();
		if(_JsonTcpBean==null){
			_JsonTcpBean=new JsonTcpBean();
		}
		String traceId=_JsonTcpBean.getTraceId();
		if(StringUtil.isBlank(traceId)){
			_JsonTcpBean.setTraceId(Uuid.findInstance().toString());
		}
		String spanParentId=_JsonTcpBean.getSpanParentId();
		if(StringUtil.isBlank(spanParentId)){
			_JsonTcpBean.setSpanParentId(traceId);
		}
		String spanId=_JsonTcpBean.getSpanId();
		if(StringUtil.isBlank(spanId)){
			_JsonTcpBean.setSpanId(Uuid.findInstance().toString());
		}
		findLogThreadLocalLast().set(_JsonTcpBean);
		return _JsonTcpBean;
	}
	/**
	 * 最后一条Log是否成功
	 * @Title: isSuccess 
	 * @Description: 
	 *
	 * 参数说明 
	 * @return 
	 * 返回类型 Boolean
	 */
	public static Boolean isSuccess() {
		return findLog().isSuccess();
	}
	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/
}
