package org.doming.core.common.servlet;
import net.sf.json.JSONObject;
import org.doming.core.enums.CodeEnum;

import javax.servlet.AsyncContext;
import java.util.concurrent.CompletableFuture;
/**
 * 任务运行类
 *
 * @Author: Dongming
 * @Date: 2020-05-19 11:28
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class WorkedRunnable extends WorkedCallable {
	private Class<?> clazz;
	public WorkedRunnable(AsyncContext asyncContext, Class<?> clazz) {
		super(asyncContext);
		this.clazz = clazz;
	}
	@Override public void run() {
		long starTime = System.currentTimeMillis();
		//业务处理调用
		try {
			log.info("MVC 处理业务开始，请求参数为".concat(JSONObject.fromObject(request.getParameterMap()).toString()));
			Object object = clazz.newInstance();
			Object result;
			if (object instanceof MvcExecutor) {
				//找到mvc执行器
				MvcExecutor executor = (MvcExecutor) object;
				otherOrigin = executor.otherOrigin();
				//开始执行
				result = executor.execute(request,response);
			} else {
				throw new Exception("没有继承MVC执行器");
			}
			//业务完成后，响应处理
			if (result == null) {
				callBack(null);
			} else if (result instanceof CompletableFuture) {
				CompletableFuture<Object> future = (CompletableFuture<Object>) result;
				future.thenAccept(this::callBack).exceptionally(e -> {
					callBack(e.getMessage());
					return null;
				});
			} else {
				callBack(result);
			}

		} catch (Exception e) {
			log.error("MVC 处理业务错误", e);
			JSONObject jrb = new JSONObject();
			jrb.put("codeSys",CodeEnum.CODE_500.getCode());
			jrb.put("msgSys",CodeEnum.CODE_500.getMsgCn());
			jrb.put("code",CodeEnum.DM_500.getCode());
			jrb.put("msg",CodeEnum.DM_500.getMsgCn());
			jrb.put("success",false);
			serverError();
			callBack(jrb);
		} finally {
			long endTime = System.currentTimeMillis();
			log.info("MVC 处理业务结束， 消耗时间=" + (endTime - starTime) + "ms");
		}

	}
}
