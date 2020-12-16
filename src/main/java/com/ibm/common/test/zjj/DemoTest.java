package com.ibm.common.test.zjj;

import c.a.util.core.test.CommTest;
import com.ibm.common.tools.EncryptTool;
import org.junit.Test;

/**
 * @Description: 测试类
 * @Author: zjj
 * @Date: 2019-08-29 16:44
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class DemoTest extends CommTest {

    @Test
    public void test05() throws Exception {
        System.out.println(EncryptTool.decode(EncryptTool.Type.ASE, "anKfguqLBjt24ko0So9jcw=="));
//        System.out.println(EncryptTool.encode(EncryptTool.Type.ASE, "zjjadmin12"));

    }


    @Test
    public void test() {

    }
}
