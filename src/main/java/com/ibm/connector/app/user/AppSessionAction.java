package com.ibm.connector.app.user;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.ibm.connector.core.user.SessionAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-08-28 16:40
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/user/session", method = HttpConfig.Method.GET) public class AppSessionAction
		extends SessionAction {
	public AppSessionAction() {
		super(ChannelTypeEnum.APP);
	}
}
