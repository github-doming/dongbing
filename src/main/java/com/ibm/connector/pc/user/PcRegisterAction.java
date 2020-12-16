package com.ibm.connector.pc.user;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.core.user.RegisterAction;
import org.doming.core.common.servlet.ActionMapping;
/**
 * @Description: 电脑端注册用户
 * @Author: Dongming
 * @Date: 2019-08-28 18:02
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/user/register") public class PcRegisterAction
		extends RegisterAction {
	public PcRegisterAction() {
		super(ChannelTypeEnum.PC, IbmTypeEnum.USER);
	}
}
