package com.cloud.user.connector.app;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.cloud.user.connector.core.RegisterAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * 手机端 注册用户
 *
 * @Author: Dongming
 * @Date: 2020-05-23 11:13
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/user/app/register", method = HttpConfig.Method.POST) public class AppRegisterAction
		extends RegisterAction {
	public AppRegisterAction() {
		super(ChannelTypeEnum.APP);
	}
}
