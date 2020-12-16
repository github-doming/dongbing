package com.cloud.user.connector.app;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.cloud.user.connector.core.LoginAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * 手机端 用户登录
 * @Author: Dongming
 * @Date: 2020-05-23 11:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/user/app/login", method = HttpConfig.Method.POST)
public class AppLoginAction extends LoginAction {
	public AppLoginAction() {
		super(ChannelTypeEnum.APP);
	}
}
