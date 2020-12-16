package com.ibm.connector.app.user;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.ibm.connector.core.user.VerifyImgAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * @Description: 手机端验证码图片
 * @Author: Dongming
 * @Date: 2019-08-28 17:34
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/user/verifyImage", method = HttpConfig.Method.GET) public class AppVerifyImgAction
		extends VerifyImgAction {
	public AppVerifyImgAction() {
		super(ChannelTypeEnum.APP);
	}
}
