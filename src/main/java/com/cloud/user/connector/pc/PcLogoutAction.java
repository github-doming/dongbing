package com.cloud.user.connector.pc;
import com.cloud.user.connector.core.LogoutAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * 电脑端 注销登录
 *
 * @Author: Dongming
 * @Date: 2020-05-23 11:13
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/user/pc/logout", method = HttpConfig.Method.GET) public class PcLogoutAction
		extends LogoutAction {

}
