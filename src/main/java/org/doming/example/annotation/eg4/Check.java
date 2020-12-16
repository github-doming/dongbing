package org.doming.example.annotation.eg4;
import java.lang.annotation.*;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2018-09-27 15:26
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Check {
	String value();
}
