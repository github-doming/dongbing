package org.doming.example.annotation.eg4;
import java.lang.annotation.*;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2018-09-27 15:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {
	int id() default -1;

	String msg() default "hello,world ";
}
