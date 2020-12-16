package org.doming.core.common.servlet;

import org.doming.develop.http.HttpConfig;

import java.lang.annotation.*;

/**
 * @Description: web请求路径映射
 * @Author: Dongming
 * @Date: 2019-05-17 15:59
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @see org.springframework.web.bind.annotation.RequestMapping
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ActionMapping {
	/**
	 * 为此映射指定名称
	 */
	String name() default "";

	/**
	 * 映射路径的URI
	 */
	String[] value() default {};

	/**
	 * http请求类型
	 */
	HttpConfig.Method[] method() default {};

}
