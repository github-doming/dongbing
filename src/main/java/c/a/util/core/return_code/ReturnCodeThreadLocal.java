package c.a.util.core.return_code;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.helper.StringUtil;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.uuid.Uuid;
public class ReturnCodeThreadLocal {
	/**
	 * 返回码
	 */
	private static ThreadLocal<List<JsonResultBean>> returnCodeThreadLocalList = null;
	/**
	 * 返回码(主要的)
	 */
	private static ThreadLocal<JsonResultBean> returnCodeThreadLocalLast = null;
	// -- set与get --//
	public static ThreadLocal<List<JsonResultBean>> getReturnCodeThreadLocalList() {
		return returnCodeThreadLocalList;
	}
	public static void setReturnCodeThreadLocalList(ThreadLocal<List<JsonResultBean>> returnCodeThreadLocalList) {
		ReturnCodeThreadLocal.returnCodeThreadLocalList = returnCodeThreadLocalList;
	}
	public static ThreadLocal<JsonResultBean> getReturnCodeThreadLocalLast() {
		return returnCodeThreadLocalLast;
	}
	public static void setReturnCodeThreadLocalLast(ThreadLocal<JsonResultBean> returnCodeThreadLocalLast) {
		ReturnCodeThreadLocal.returnCodeThreadLocalLast = returnCodeThreadLocalLast;
	}
	// -- set与get --//
	public static ThreadLocal<List<JsonResultBean>> findReturnCodeThreadLocalList() {
		if (returnCodeThreadLocalList == null) {
			returnCodeThreadLocalList = new ThreadLocal<List<JsonResultBean>>();
		}
		return returnCodeThreadLocalList;
	}
	public static ThreadLocal<JsonResultBean> findReturnCodeThreadLocalLast() {
		if (returnCodeThreadLocalLast == null) {
			returnCodeThreadLocalLast = new ThreadLocal<JsonResultBean>();
		}
		return returnCodeThreadLocalLast;
	}
	public static List<JsonResultBean> findReturnCodeList() {
		List<JsonResultBean> returnCodeList = findReturnCodeThreadLocalList().get();
		if (returnCodeList == null || returnCodeList.size() == 0) {
			returnCodeList = new ArrayList<JsonResultBean>();
		}
		return returnCodeList;
	}
	/**
	 * 线程保存ReturnCode
	 * 
	 * @Description
	 * @Title setReturnCode
	 * @param returnCode
	 *            参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public static void setReturnCode(JsonResultBean returnCode) {
		List<JsonResultBean> returnCodeList = findReturnCodeList();
		returnCodeList.add(returnCode);
		findReturnCodeThreadLocalList().set(returnCodeList);
		//最后一条ReturnCode
		findReturnCodeThreadLocalLast().set(returnCode);
	}
	/**
	 * 线程保存ReturnCode
	 * 
	 * @Description
	 * @Title setReturnCode
	 * @param success
	 * @param msg
	 *            参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public static void setReturnCode(boolean success, String msg) {
		JsonResultBean returnCode = new JsonResultBean();
		returnCode.setSuccess(success);
		returnCode.setMsg(msg);
		setReturnCode(returnCode);
	}
	/**
	 * 最后一条ReturnCode
	 * @Title: findReturnCode 
	 * @Description: 
	 *
	 * 参数说明 
	 * @return 
	 * 返回类型 JsonResultBean
	 */
	public static JsonResultBean findReturnCode() {
		JsonResultBean _JsonResultBean=findReturnCodeThreadLocalLast().get();
		if(_JsonResultBean==null){
			_JsonResultBean=new JsonResultBean();
		}
		String traceId=_JsonResultBean.getTraceId();
		if(StringUtil.isBlank(traceId)){
			_JsonResultBean.setTraceId(Uuid.findInstance().toString());
		}
		String spanParentId=_JsonResultBean.getSpanParentId();
		if(StringUtil.isBlank(spanParentId)){
			_JsonResultBean.setSpanParentId(traceId);
		}
		String spanId=_JsonResultBean.getSpanId();
		if(StringUtil.isBlank(spanId)){
			_JsonResultBean.setSpanId(Uuid.findInstance().toString());
		}
		findReturnCodeThreadLocalLast().set(_JsonResultBean);
		return _JsonResultBean;
	}
	/**
	 * 最后一条ReturnCode是否成功
	 * @Title: isSuccess 
	 * @Description: 
	 *
	 * 参数说明 
	 * @return 
	 * 返回类型 Boolean
	 */
	public static Boolean isSuccess() {
		return findReturnCode().isSuccess();
	}
	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/
}
