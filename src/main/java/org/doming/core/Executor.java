package org.doming.core;
/**
 * @Description: 执行者监听器
 * @Author: Dongming
 * @Date: 2018-12-03 11:44
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface Executor {

	/**
	 * 运行方法
	 *
	 * @param inVar 输入参数
	 * @return 执行结果
	 * @throws Exception 执行异常
	 */
	default String run(String inVar) throws Exception{
		return null;
	}
	/**
	 * 执行方法
	 *
	 * @param inVar 输入参数
	 * @return 执行结果
	 * @throws Exception 执行异常
	 */
	default String execute(String inVar) throws Exception{
		return null;
	}

}
