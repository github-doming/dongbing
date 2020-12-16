package com.ibm.connector.pc.user;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.core.user.LoginAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * @Description: 电脑端用户登录
 * @Author: Dongming
 * @Date: 2019-08-28 18:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */

@ActionMapping(value = "/ibm/pc/user/login") public class PcLoginAction
		extends LoginAction {
	public PcLoginAction() {
		super(ChannelTypeEnum.PC, IbmTypeEnum.USER);
	}
}
