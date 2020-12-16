package com.cloud.user.connector.pc;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.cloud.user.connector.core.SessionAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * 电脑端 获取会话ID
 * @Author: Dongming
 * @Date: 2020-05-23 11:09
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/user/pc/session", method = HttpConfig.Method.GET)
public class PcSessionAction extends SessionAction {
	public PcSessionAction() {
		super(ChannelTypeEnum.PC);
	}
}
