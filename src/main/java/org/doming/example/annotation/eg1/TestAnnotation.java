package org.doming.example.annotation.eg1;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * @Description: 注解的定义
 * @Author: Dongming
 * @Date: 2018-09-27 10:28
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
//注解可以保留到程序运行的时候，它会被加载进入到 JVM 中，所以在程序运行时可以获取到它们。
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {
}
