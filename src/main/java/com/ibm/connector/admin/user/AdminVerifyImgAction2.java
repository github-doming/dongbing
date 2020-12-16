package com.ibm.connector.admin.user;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.ibm.connector.core.user.VerifyImgAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * 系统管理端 验证码图片
 *
 * @Author: Dongming
 * @Date: 2020-03-26 17:50
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/user/verifyImage2", method = HttpConfig.Method.GET) public class AdminVerifyImgAction2
		extends VerifyImgAction {
	public AdminVerifyImgAction2() {
		super(ChannelTypeEnum.ADMIN,80,36);
	}
}
