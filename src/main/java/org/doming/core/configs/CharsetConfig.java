package org.doming.core.configs;

import java.nio.charset.Charset;
/**
 * @Description: 字符集配置文件
 * @Date 2018年9月26日18:46:46
 * @Author Dongming
 * @Email: job.dongming@foxmail.com
 * @Copyright © 2018-2019 本源代码受软件著作法保护，请在授权允许范围内使用
 **/
public interface CharsetConfig {
    String UTF8 = "UTF-8";
    Charset UTF_8 = Charset.forName(UTF8);
}
