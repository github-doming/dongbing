package c.a.util.core.json;
import c.a.tools.jdbc.transaction.DatabaseBase;
import c.a.util.core.string.StringUtil;
/**
 * 
 * @Description:
 * @date 2018年6月27日 下午12:02:49
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public  class JsonTransactionBase extends DatabaseBase{
	public boolean isFindJson(String json) throws Exception {
		if (StringUtil.isNotBlank(json)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 返回json
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param jsonResultBean
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String doJsonResultBean2Json(JsonTcpBean _JsonTcpBean) throws Exception {
		String resultJsonStr = JsonThreadLocal.bean2json(_JsonTcpBean);
		return resultJsonStr;
	}
}
