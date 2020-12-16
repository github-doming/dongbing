package com.ibm.connector.app.user;
import com.ibm.connector.core.user.LogoutAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * @Description: App端 注销登录
 * @Author: Dongming
 * @Date: 2019-08-28 19:39
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/user/logout", method = HttpConfig.Method.GET)
public class AppLogoutAction extends LogoutAction {

}
