package com.cloud.user.connector.pc;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.cloud.user.connector.core.RegisterAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * 电脑端 注册用户
 *
 * @Author: Dongming
 * @Date: 2020-05-23 11:13
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/user/pc/register", method = HttpConfig.Method.POST) public class PcRegisterAction
		extends RegisterAction {
	public PcRegisterAction() {
		super(ChannelTypeEnum.PC);
	}
}
