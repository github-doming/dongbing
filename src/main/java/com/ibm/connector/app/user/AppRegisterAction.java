package com.ibm.connector.app.user;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.core.user.RegisterAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * @Description: 电脑端注册用户
 * @Author: Dongming
 * @Date: 2019-08-28 18:02
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/user/register", method = HttpConfig.Method.GET) public class AppRegisterAction
		extends RegisterAction {
	public AppRegisterAction() {
		super(ChannelTypeEnum.APP, IbmTypeEnum.USER);
	}
}
