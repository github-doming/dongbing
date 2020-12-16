package com.ibm.connector.core.user;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.connector.service.user.AppVerifyAccountService;
import net.sf.json.JSONObject;
import org.doming.core.common.servlet.MvcResult;
import org.doming.core.tools.StringTool;

import java.awt.image.BufferedImage;
/**
 * @Description: 验证码图片
 * @Author: Dongming
 * @Date: 2019-08-28 16:47
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class VerifyImgAction extends CommCoreAction {
	private ChannelTypeEnum channelType;
	private int width;
	private int height;
	public VerifyImgAction(ChannelTypeEnum channelType) {
		super.isTime = false;
		this.channelType = channelType;
		width = 60;
		height = 18;
	}
	public VerifyImgAction(ChannelTypeEnum channelType,int width,int height) {
		super.isTime = false;
		this.channelType = channelType;
		this.width = width;
		this.height = height;
	}
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findJson();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}

		if (StringTool.isEmpty(json)) {
			return400Session(bean);
			return bean;
		}
		JSONObject jObj = JSONObject.fromObject(json);
		Object session = jObj.get("session");
		if (StringTool.isEmpty(session)) {
			return400Session(bean);
			return bean;
		}
		try {
			//获取 用户验证码
			String verifyCode = new AppVerifyAccountService().newVerifyCode(session.toString(), channelType);
			if (StringTool.isEmpty(verifyCode)) {
				return400Session(bean);
				return bean;
			}
			// 获取 验证码输出流
			BufferedImage image = AppUserDefine.getImageBuffer(verifyCode, width, height);
			return MvcResult.imageResult(image);
		} catch (Exception e) {
			log.error("获取验证码图片失败，", e);
			throw e;
		}
	}

	private void return400Session(JsonResultBeanPlus bean) {
		bean.putEnum(ReturnCodeEnum.app400Session);
		bean.putSysEnum(ReturnCodeEnum.app400Session);
	}
}
