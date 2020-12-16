package c.a.tools.mvc.action;
import java.util.List;
import java.util.Map;

import c.a.config.SysConfig;
import c.a.util.core.asserts.AssertUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.log.LogThreadLocal;
/**
 * 
 * 
 * 
 * 
 * 自定义MVC框架BaseAction
 * 
 */
public abstract class MvcAction extends MvcPathAction {
	public boolean async = false;
	public JsonTcpBean doExecute() throws Exception {
		return this.executeMvc();
	}
	/**
	 * 
	 * 
	 * 不要用反射,因为反射不能与业务异常bizException绑定起来
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract JsonTcpBean executeMvc() throws Exception;
	/**
	 * 返回JsonTcpBean
	 * 
	 * @Title: returnJsonTcpBean
	 * @Description:
	 *
	 * 				参数说明
	 * @param mvcResult
	 * @return
	 * @throws Exception
	 *             返回类型 JsonTcpBean
	 */
	public JsonTcpBean returnJsonTcpBean(String mvcResult) throws Exception {
		JsonTcpBean _JsonTcpBean = LogThreadLocal.findLog();
		_JsonTcpBean.setMvcResult(mvcResult);
		return _JsonTcpBean;
	}
	/**
	 * 返回jsonp;
	 * 
	 * JSONP(JSON with Padding);
	 * 
	 * @Title: returnJsonp
	 * @Description:
	 *
	 * 				参数说明
	 * @param _JsonTcpBean
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String returnJsonp(JsonTcpBean _JsonTcpBean) throws Exception {
		String callback = new String(request.getParameter("callback").getBytes("ISO-8859-1"), "utf-8");
		// log.trace("callback的值=" + callback);
		_JsonTcpBean.setTraceId(LogThreadLocal.findLog().getTraceId());
		String resultJsonStr = JsonThreadLocal.bean2json(_JsonTcpBean);
		// 将json数据封装在callback函数中提供给客户端
		String jsonStr = callback + "(" + resultJsonStr + ")";
		response.getWriter().print(jsonStr);
		return null;
	}
	/**
	 * 返回jsonp;
	 * 
	 * JSONP(JSON with Padding);
	 * 
	 * @Title: returnJsonp
	 * @Description:
	 *
	 * 				参数说明
	 * @param map
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String returnJsonp(Map<String, Object> map) throws Exception {
		String callback = new String(request.getParameter("callback").getBytes("ISO-8859-1"), "utf-8");
		// log.trace("callback的值=" + callback);
		String resultJsonStr = JsonThreadLocal.findThreadLocal().get().map2json(map);
		// 将json数据封装在callback函数中提供给客户端
		String jsonStr = callback + "(" + resultJsonStr + ")";
		response.getWriter().print(jsonStr);
		return null;
	}
	/**
	 * 返回json
	 * 
	 * @Title: returnJson
	 * @Description:
	 *
	 * 				参数说明
	 * @param _JsonResultBean
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String returnJson(JsonResultBean _JsonResultBean) throws Exception {
		_JsonResultBean.setTraceId(LogThreadLocal.findLog().getTraceId());
		String resultJsonStr = JsonThreadLocal.bean2json(_JsonResultBean);
		response.getWriter().print(resultJsonStr);
		return null;
	}
	/**
	 * 返回json
	 * 
	 * @Title: returnJson
	 * @Description:
	 *
	 * 				参数说明
	 * @param _JsonResultBean
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String returnJson(JsonTcpBean _JsonTcpBean) throws Exception {
		_JsonTcpBean.setTraceId(LogThreadLocal.findLog().getTraceId());
		String resultJsonStr = JsonThreadLocal.bean2json(_JsonTcpBean);
		response.getWriter().print(resultJsonStr);
		return null;
	}
	/**
	 * 返回json
	 * 
	 * @Title: returnJson
	 * @Description:
	 *
	 * 				参数说明
	 * @param success
	 * @param message
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String returnJson(boolean success, String message) throws Exception {
		JsonTcpBean _JsonTcpBean = new JsonTcpBean(success, message);
		_JsonTcpBean.setTraceId(LogThreadLocal.findLog().getTraceId());
		String resultJsonStr = JsonThreadLocal.bean2json(_JsonTcpBean);
		response.getWriter().print(resultJsonStr);
		return null;
	}
	/**
	 * 返回json
	 * 
	 * @Title: returnJson
	 * @Description:
	 *
	 * 				参数说明
	 * @param list
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String returnJson(List list) throws Exception {
		String resultJsonStr = JsonThreadLocal.findThreadLocal().get().list2json(list);
		response.getWriter().print(resultJsonStr);
		return null;
	}
	/**
	 * 返回json
	 * 
	 * @Title: returnJson
	 * @Description:
	 *
	 * 				参数说明
	 * @param map
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String returnJson(Map<String, Object> map) throws Exception {
		String resultJsonStr = JsonThreadLocal.findThreadLocal().get().map2json(map);
		response.getWriter().print(resultJsonStr);
		return null;
	}
	/**
	 * 返回json
	 * 
	 * @Title: returnJson
	 * @Description:
	 *
	 * 				参数说明
	 * @param resultJsonStr
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String returnJson(String resultJsonStr) throws Exception {
		response.getWriter().print(resultJsonStr);
		return null;
	}
	/**
	 * 返回
	 * 
	 * @Title: findViewResult
	 * @Description:
	 *
	 * 				参数说明
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String findViewResult() throws Exception {
		String resultReturnStr = BeanThreadLocal.findThreadLocal().get()
				.find(SysConfig.findInstance().findMap().get(SysConfig.commLocalView), CommViewEnum.Default.toString());
		return resultReturnStr;
	}
	/**
	 * 返回空; 视图方案不能为空;
	 * 
	 * @Title: findViewNull
	 * @Description:
	 *
	 * 				参数说明
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String findViewNull() throws Exception {
		AssertUtil.isNull(null, "视图方案不能为空");
		return null;
	}
	public JsonTcpBean doExecuteAsynchronous() throws Exception {
		JsonTcpBean returnStr = this.executeMvcAsynchronous();
		return returnStr;
	}
	/**
	 * 
	 * 
	 * 不要用反射,因为反射不能与业务异常bizException绑定起来
	 * 
	 * @return
	 * @throws Exception
	 */
	// public abstract String executeMvcAsynchronous() throws Exception;
	public JsonTcpBean executeMvcAsynchronous() throws Exception {
		return null;
	}
	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/
}
