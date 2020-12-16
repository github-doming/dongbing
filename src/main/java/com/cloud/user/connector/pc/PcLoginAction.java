package com.cloud.user.connector.pc;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.cloud.user.connector.core.LoginAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * 电脑端 用户登录
 * @Author: Dongming
 * @Date: 2020-05-23 11:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/user/pc/login", method = HttpConfig.Method.POST)
public class PcLoginAction extends LoginAction {
	public PcLoginAction() {
		super(ChannelTypeEnum.PC);
	}
}
