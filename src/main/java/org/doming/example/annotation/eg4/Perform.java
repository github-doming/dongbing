package org.doming.example.annotation.eg4;
import java.lang.annotation.*;
/**
 * @Description: 注解的属性
 * @Author: Dongming
 * @Date: 2018-09-27 15:28
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Perform {

	String name() default "";

}
