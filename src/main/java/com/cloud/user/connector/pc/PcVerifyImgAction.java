package com.cloud.user.connector.pc;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.cloud.user.connector.core.VerifyImgAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * 电脑端 验证码图片
 *
 * @Author: Dongming
 * @Date: 2020-05-23 11:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/user/pc/verifyImage", method = HttpConfig.Method.GET)
public class PcVerifyImgAction extends VerifyImgAction {
	public PcVerifyImgAction() {
		super(ChannelTypeEnum.PC);
	}
}
