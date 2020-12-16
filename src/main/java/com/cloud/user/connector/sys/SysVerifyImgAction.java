package com.cloud.user.connector.sys;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.cloud.user.connector.core.VerifyImgAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * 系统端 验证码图片
 *
 * @Author: Dongming
 * @Date: 2020-05-23 11:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/user/sys/verifyImage", method = HttpConfig.Method.GET)
public class SysVerifyImgAction extends VerifyImgAction {
	public SysVerifyImgAction() {
		super(ChannelTypeEnum.ADMIN);
	}
}
