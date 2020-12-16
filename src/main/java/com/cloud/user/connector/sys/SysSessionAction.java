package com.cloud.user.connector.sys;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.cloud.user.connector.core.SessionAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * 系统端 获取会话ID
 * @Author: Dongming
 * @Date: 2020-05-23 11:09
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/user/sys/session", method = HttpConfig.Method.GET)
public class SysSessionAction extends SessionAction {
	public SysSessionAction() {
		super(ChannelTypeEnum.ADMIN);
	}
}
