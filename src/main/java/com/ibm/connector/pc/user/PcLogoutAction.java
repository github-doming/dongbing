package com.ibm.connector.pc.user;
import com.ibm.connector.core.user.LogoutAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * @Description: 电脑端 注销登录
 * @Author: Dongming
 * @Date: 2019-08-28 19:39
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/user/logout", method = HttpConfig.Method.GET)
public class PcLogoutAction extends LogoutAction {

}
