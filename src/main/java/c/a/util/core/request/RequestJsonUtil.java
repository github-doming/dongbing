package c.a.util.core.request;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import c.a.util.core.reflect.ReflectJsonUtil;
import c.a.util.core.reflect.ReflectThreadLocal;
/**
 * 
 * @author cxy
 * @Email:  使用范围：
 * 
 */
public class RequestJsonUtil extends RequestByUtil {
	/**
	 * 
	 * request.getParameterNames转成map;
	 * 
	 * 
	 * 
	 * 然后再转成实体类;
	 * 
	 * @param tableName
	 * @param classOrigin
	 * @param classDestination
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Object doRequest2EntityByJson(Class classOrigin, Class classDestination, HttpServletRequest request)
			throws Exception {
		ReflectJsonUtil reflectJsonUtil = ReflectThreadLocal.findThreadLocal().get();
		Map<String, Object> map = findParameterList(request);
		Object obj = reflectJsonUtil.doMap2ObjectByJson(classOrigin, classDestination, map);
		return obj;
	}
}