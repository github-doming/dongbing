package com.ibm.connector.pc.user;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.ibm.connector.core.user.SessionAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * @Description: 电脑端 获取会话ID
 * @Author: Dongming
 * @Date: 2019-08-28 16:40
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/user/session") public class PcSessionAction
		extends SessionAction {
	public PcSessionAction() {
		super(ChannelTypeEnum.PC);
	}
}
