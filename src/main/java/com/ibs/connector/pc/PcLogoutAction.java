package com.ibs.connector.pc;

import com.ibs.connector.core.LogoutAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;

/**
 * @Description: 电脑端登出
 * @Author: null
 * @Date: 2020-09-27 15:22
 */
@ActionMapping(value = "/ibs/pc/user/logout", method = HttpConfig.Method.POST)
public class PcLogoutAction extends LogoutAction {
}
