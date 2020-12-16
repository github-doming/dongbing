package com.ibm.connector.app.user;
import com.ibm.connector.core.user.UpdatePasswordAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * @Description: APP端 修改密码
 * @Author: Dongming
 * @Date: 2019-08-28 19:32
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/user/updatePassword", method = HttpConfig.Method.GET) public class AppUpdatePasswordAction
		extends UpdatePasswordAction {
}
