package org.doming.core.common.servlet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
/**
 * @Description: 任务回调
 * @Author: Dongming
 * @Date: 2019-05-17 09:39
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class WorkedCallable implements Runnable {
	protected static final Logger log = LogManager.getLogger(WorkedCallable.class);

	protected AsyncContext asyncContext;
	protected HttpServletResponse response;
	protected HttpServletRequest request;

	protected Boolean otherOrigin;

	public WorkedCallable(AsyncContext asyncContext) {
		this.asyncContext = asyncContext;
		response = (HttpServletResponse) asyncContext.getResponse();
		request = (HttpServletRequest) asyncContext.getRequest();
	}
	public AsyncContext getAsyncContext() {
		return asyncContext;
	}

	/**
	 * 执行回执
	 *
	 * @param result 执行结果
	 */
	protected void callBack(Object result) {
		try {
			if (result == null) {
				write404();
			} else if (result instanceof String) {
				//输出字符串
				write(result.toString());
			} else if (result instanceof ModelAndView) {
				//输出视图
				forward((ModelAndView) result);
			} else if (result instanceof MvcResult) {
				mvcResult((MvcResult) result);
			} else if (result instanceof Throwable) {
				//输出视图
				log.error("执行错误", (Throwable) result);
				serverError();
			} else {
				//默认输出json字符串
				write(JSON.toJSONString(result, SerializerFeature.WriteMapNullValue));
			}
		} catch (Throwable t) {
			//程序内部错误
			log.error("回传执行结果错误", t);
			serverError();
		} finally {
			asyncContext.complete();
		}
	}

	/**
	 * 回显文本信息
	 *
	 * @param result 执行结果
	 */
	private void write(String result) throws IOException {
		HttpServletTool.write(response, result, otherOrigin);
	}
	/**
	 * 回显文本信息
	 */
	private void write404() throws IOException {
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		HttpServletTool.write(response, "(;´༎ຶД༎ຶ`) 数据出错，请求结果为空", otherOrigin);
	}

	/**
	 * 根据模型视图对象获取导向
	 *
	 * @param result 响应模型视图
	 */
	private void forward(ModelAndView result) throws IOException, ServletException {
		//模型
		if (result.getType().equals(ModelAndView.Type.MODEL)) {
			write(JSONObject.toJSON(result.getModel()).toString());
		} else if (ModelAndView.Type.VIEW.equals(result.getType())) {
			//视图
			try {
				HttpServletTool.forward(response, request, result.getView(), result.getModel());
			} catch (ServletException | IOException e) {
				log.error("重定向页面错误，url=".concat(result.getView()), e);
				throw e;
			}
		} else {
			HttpServletTool.write(response, JSONObject.toJSON(result.getModel()).toString(), otherOrigin);
		}
	}

	/**
	 * 返回状态码 500
	 */
	public void serverError() {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

	/**
	 * 执行MVC 结果
	 *
	 * @param result MVC 结果
	 */
	private void mvcResult(MvcResult result) throws IOException {
		if (result.getType() == MvcResult.Type.FILE) {
			outFile(result.getContent());
		} else if (result.getType() == MvcResult.Type.IMAGE) {
			HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
			response.setContentType("image/jpeg");
			ImageIO.write(result.getBufferedImage(), "JPEG", response.getOutputStream());
		} else {
			write("");
		}

	}

	/**
	 * 输出文件
	 *
	 * @param path 文件路径
	 */
	private void outFile(String path) {
		HttpServletRequest request = (HttpServletRequest) asyncContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
		response.setCharacterEncoding(request.getCharacterEncoding());
		response.setContentType("application/octet-stream");
		File file = new File(path);
		try (FileInputStream fis = new FileInputStream(file)) {
			String fileName = URLEncoder.encode(file.getName(), "UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			IOUtils.copy(fis, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			log.error("输出文件错误", e);
		}
	}
}
