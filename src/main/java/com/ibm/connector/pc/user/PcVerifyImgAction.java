package com.ibm.connector.pc.user;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.ibm.connector.core.user.VerifyImgAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * @Description: 电脑端 验证码图片
 * @Author: Dongming
 * @Date: 2019-08-28 17:34
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/user/verifyImage", method = HttpConfig.Method.GET) public class PcVerifyImgAction
		extends VerifyImgAction {
	public PcVerifyImgAction() {
		super(ChannelTypeEnum.PC);
	}
}
