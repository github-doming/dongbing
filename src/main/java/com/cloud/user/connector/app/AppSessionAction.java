package com.cloud.user.connector.app;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.cloud.user.connector.core.SessionAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * 手机端 获取会话ID
 * @Author: Dongming
 * @Date: 2020-05-23 11:09
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/user/app/session", method = HttpConfig.Method.GET)
public class AppSessionAction extends SessionAction {
	public AppSessionAction() {
		super(ChannelTypeEnum.APP);
	}
}
