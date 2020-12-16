package com.ibm.common.test.wwj.handicap.logger;
import com.ibm.common.utils.HandicapUtil;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpResult;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-12-07 10:13
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HandicapLogger extends HttpRequestLogger {
	private HandicapUtil.Code handicap;
	public HandicapLogger(String url, String parameter, String html, HandicapUtil.Code handicap) {
		super(url, parameter, html);
		this.handicap = handicap;
		if (StringTool.isEmpty(html)) {
			setState(404);
		}
	}
	public HandicapLogger(String url, String parameter, HttpResult result, HandicapUtil.Code handicap) {
		super(url, parameter, null);
		this.handicap = handicap;
		if (result != null) {
			super.result(result.getHtml(), result.getStatusCode(), result.getLocation());
		} else {
			super.setHtml("");
		}
	}
	public HandicapUtil.Code getHandicap() {
		return handicap;
	}
	public void setHandicap(HandicapUtil.Code handicap) {
		this.handicap = handicap;
	}
}
