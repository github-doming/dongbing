package com.ibm.connector.admin.user;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.ibm.connector.core.user.SessionAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * 系统管理端 获取会话ID
 *
 * @Author: Dongming
 * @Date: 2020-03-26 17:49
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/user/session", method = HttpConfig.Method.GET) public class AdminSessionAction
		extends SessionAction {
	public AdminSessionAction() {
		super(ChannelTypeEnum.ADMIN);
	}

}
