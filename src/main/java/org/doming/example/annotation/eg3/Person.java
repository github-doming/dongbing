package org.doming.example.annotation.eg3;
import java.lang.annotation.*;
/**
 * @Description: 人类注解
 * @Author: Dongming
 * @Date: 2018-09-27 15:01
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Persons.class)
public @interface Person {
	String role() default "";
}
