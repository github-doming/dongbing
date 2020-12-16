package org.doming.core.common.servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @Description: mvc执行器
 * @Author: Dongming
 * @Date: 2019-05-18 10:20
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface MvcExecutor {
	/**
	 * mvc执行器
	 *
	 * @param request  请求对象
	 * @param response 返回对象
	 * @return 执行结果
	 * @throws Exception 执行错误
	 */
	Object execute(HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * 是否允许跨域
	 *
	 * @return 是否允许跨域
	 */
	Boolean otherOrigin();

}
