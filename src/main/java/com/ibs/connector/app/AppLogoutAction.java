package com.ibs.connector.app;

import com.ibs.connector.core.LogoutAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;

/**
 * @Description: 移动端登出接口
 * @Author: null
 * @Date: 2020-09-27 15:42
 */
@ActionMapping(value = "/ibs/app/user/logout", method = HttpConfig.Method.POST)
public class AppLogoutAction extends LogoutAction {
}
