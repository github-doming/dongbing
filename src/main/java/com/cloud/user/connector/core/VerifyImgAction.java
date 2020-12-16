package com.cloud.user.connector.core;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.core.BaseMvcData;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.user.app_session.service.AppSessionService;
import com.cloud.user.app_verify_code.service.AppVerifyCodeService;
import org.doming.core.common.servlet.MvcResult;
import org.doming.core.tools.StringTool;

import java.awt.image.BufferedImage;
/**
 * 验证码图片
 *
 * @Author: Dongming
 * @Date: 2020-06-15 15:15
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class VerifyImgAction extends BaseMvcData {
	private ChannelTypeEnum channelType;
	private int width;
	private int height;
	public VerifyImgAction(ChannelTypeEnum channelType) {
		this(channelType, 60, 18);
	}
	public VerifyImgAction(ChannelTypeEnum channelType, int width, int height) {
		this.channelType = channelType;
		this.width = width;
		this.height = height;
	}
	@Override public Object run() throws Exception {
		super.findJson();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JSONObject jsonData = JSON.parseObject(json);
		String sessionId = StringTool.trimAll(StringTool.getString(jsonData, "session", ""));

		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (StringTool.isEmpty(sessionId)) {
			return bean.put401Data();
		}
		try {
			if (!new AppSessionService().findExist(sessionId, channelType)) {
				bean.putEnum(ReturnCodeEnum.app400Session);
				bean.putSysEnum(ReturnCodeEnum.app400Session);
				return bean;
			}
			AppVerifyCodeService verifyAccountService = new AppVerifyCodeService();
			String verifyCode = verifyAccountService.newVerifyCode(sessionId, channelType);
			if (StringTool.isEmpty(verifyCode)) {
				bean.putEnum(ReturnCodeEnum.app400Session);
				bean.putSysEnum(ReturnCodeEnum.app400Session);
				return bean;
			}
			// 获取 验证码输出流
			BufferedImage image = verifyAccountService.getImageBuffer(verifyCode, width, height);
			return MvcResult.imageResult(image);
		} catch (Exception e) {
			log.error("获取验证码图片失败，{}", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
			return bean;
		}
	}
}
