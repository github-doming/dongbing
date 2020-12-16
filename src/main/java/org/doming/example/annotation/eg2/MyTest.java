package org.doming.example.annotation.eg2;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * @Description: 测试注解
 * @Author: Dongming
 * @Date: 2018-09-27 14:56
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface MyTest {
}
