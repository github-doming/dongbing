package org.doming.core.common.servlet;

import org.doming.develop.http.HttpConfig;

import java.lang.annotation.*;

/**
 * 异步组件标识注解
 *
 * @Author: Dongming
 * @Date: 2020-05-19 14:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AsynAction {

	/**
	 * http请求编码
	 */
	HttpConfig.Code code() default HttpConfig.Code.CORE;
}
