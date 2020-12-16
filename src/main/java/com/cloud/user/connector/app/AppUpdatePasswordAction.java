package com.cloud.user.connector.app;
import com.cloud.user.connector.core.UpdatePasswordAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * 手机端 修改密码
 * @Author: Dongming
 * @Date: 2020-05-23 11:15
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/user/app/updatePassword", method = HttpConfig.Method.POST)
public class AppUpdatePasswordAction extends UpdatePasswordAction {
}
