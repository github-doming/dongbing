package com.ibm.common.test.wwj.handicap.logger;
import com.ibm.common.test.wwj.handicap.HttpType;
import com.ibm.common.utils.HandicapUtil;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpResult;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-12-07 10:26
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class OptionLogger extends HttpRequestLogger {
	private String existId;
	private HandicapUtil.Code handicap;
	private HttpType type;

	public OptionLogger(String url, String parameter, String html, String existId, HandicapUtil.Code handicap,
			HttpType type) {
		super(url, parameter, html);
		this.existId = existId;
		this.handicap = handicap;
		this.type = type;
		if (StringTool.isEmpty(html)) {
			setState(404);
		}

	}
	public OptionLogger(String url, String parameter, HttpResult result, String existId, HandicapUtil.Code handicap,
			HttpType type) {
		super(url, parameter, null);
		this.existId = existId;
		this.handicap = handicap;
		this.type = type;
		if (result != null) {
			super.result(result.getHtml(), result.getStatusCode(), result.getLocation());
		} else {
			super.setHtml("");
		}
	}
	public String getExistId() {
		return existId;
	}
	public HandicapUtil.Code getHandicap() {
		return handicap;
	}
	public HttpType getType() {
		return type;
	}
}
