package com.ibm.connector.admin.user;
import com.ibm.connector.core.user.LogoutAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * 系统管理端 登出
 *
 * @Author: Dongming
 * @Date: 2020-03-26 17:47
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/user/logout", method = HttpConfig.Method.POST) public class AdminLogoutAction
		extends LogoutAction {
}
