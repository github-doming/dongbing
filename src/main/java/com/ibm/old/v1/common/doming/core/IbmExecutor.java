package com.ibm.old.v1.common.doming.core;
import org.doming.core.Executor;
/**
 * @Description: 智能投注执行器
 * @Author: Dongming
 * @Date: 2019-06-10 17:42
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface IbmExecutor extends Executor {

	/**
	 * 执行方法
	 *
	 * @param jrb 返回处理结果
	 * @throws Exception 执行异常
	 */
	default void execute(JsonResultBeanPlus jrb) throws Exception {
	}
}
