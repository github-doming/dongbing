package com.common.handicap.com;


import com.common.config.handicap.ComBallConfig;
import com.common.config.handicap.ComConfig;
import com.common.config.handicap.ComHappyConfig;
import com.common.config.handicap.ComNumberConfig;
import com.common.enums.HcCodeEnum;
import com.common.handicap.crawler.BaseHandicapGrab;
import com.common.util.BaseGameUtil;
import com.common.util.BaseHandicapUtil;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientUtil;

/**
 * COM盘口 抓取操作抽象类
 *
 * @Author: Dongming
 * @Date: 2020-06-09 18:18
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseComGrab extends BaseHandicapGrab {
	protected static final BaseHandicapUtil.Code HANDICAP_CODE = BaseHandicapUtil.Code.COM;

	/**
	 * 需要设置cookie
	 *
	 * @param handicapUrl url地址
	 */
	protected void setCookie(String handicapUrl) {
		HttpClientContext context = httpConfig().httpContext();
		if (context == null) {
			context = HttpClientContext.create();
			httpConfig().httpContext(context);
		}
		BasicClientCookie cookie = new BasicClientCookie("srcurl", StringTool.string2Hax(handicapUrl));
		cookie.setPath("/");
		cookie.setDomain("");
		context.getCookieStore().addCookie(cookie);

		int height = RandomTool.getInt(1000, 1500);
		int width = height / 9 * 16;
		String url = handicapUrl + "?security_verify_data=" + StringTool.string2Hax(width + "," + height);

		HttpClientUtil.findInstance().get(httpConfig().url(url));
	}

	/**
	 * 登陆错误
	 *
	 * @param msg 登陆结果页面
	 * @return 错误信息
	 */
	public HcCodeEnum loginError(String msg) {
		if (StringTool.isContains(msg, "验证码错误")) {
			return HcCodeEnum.IBS_403_VERIFY_CODE;
		} else if (StringTool.contains(msg, "冻结", "禁用")) {
			return HcCodeEnum.IBS_403_USER_STATE;
		} else if (StringTool.contains(msg, "用户名错误", "密码错误")) {
			return HcCodeEnum.IBS_403_USER_ACCOUNT;
		} else if (StringTool.contains(msg, "新密碼首次登錄,需重置密碼")) {
			return HcCodeEnum.IBS_403_CHANGE_PASSWORD;
		} else if (StringTool.contains(msg, "变更密码")) {
			return HcCodeEnum.IBS_403_PASSWORD_EXPIRED;
		} else {
			log.error("Com盘口会员登陆异常，异常的登陆结果页面为：" + msg);
			return HcCodeEnum.IBS_403_UNKNOWN;
		}
	}


	/**
	 * 获取游戏类型
	 *
	 * @param gameCode 游戏code
	 * @param betType  投注类型
	 * @return 游戏类型
	 */
	protected String getComGameType(BaseGameUtil.Code gameCode, String betType) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return ComConfig.GAME_CODE.get(gameCode.name()).concat("_").concat(ComNumberConfig.TYPE_CODE.get(betType));
			case JSSSC:
			case CQSSC:
				return ComConfig.GAME_CODE.get(gameCode.name()).concat("_").concat(ComBallConfig.TYPE_CODE.get(betType));
			case GDKLC:
				return ComConfig.GAME_CODE.get(gameCode.name()).concat("_").concat(ComHappyConfig.TYPE_CODE.get(betType));
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	public String browserCode() {
		return crawlerImage().crawlerInfo().get("ticket");
	}
}
