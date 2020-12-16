package c.a.util.core.enums.bean;
/**
 * 
 * oauth第三方授权类型(微信或QQ或支付宝)
 * 
 * @ClassName:
 * @Description:
 * @author
 * @date 2016年9月16日 下午6:10:50
 * 
 */
public enum OauthTypeEnum {
	/** 新浪微博注册 */
	WEIBO("WEIBO") {
		public String getMsg() {
			return "WEIBO";
		}
		public String getMsgCn() {
			return "新浪微博注册";
		}
	},
	/** 支付宝注册 */
	ALIPAY("ALIPAY") {
		public String getMsg() {
			return "ALIPAY";
		}
		public String getMsgCn() {
			return "支付宝注册";
		}
	},
	/** QQ注册 */
	QQ("QQ") {
		public String getMsg() {
			return "QQ";
		}
		public String getMsgCn() {
			return "QQ注册";
		}
	},
	/** 微信注册 */
	WEIXIN("WEIXIN") {
		public String getMsg() {
			return "WEIXIN";
		}
		public String getMsgCn() {
			return "微信注册";
		}
	},
	/** 邮箱注册 */
	MAIL("MAIL") {
		public String getMsg() {
			return "MAIL";
		}
		public String getMsgCn() {
			return "邮箱注册";
		}
	},
	/** 手机注册 */
	MOBILE("MOBILE") {
		public String getMsg() {
			return "MOBILE";
		}
		public String getMsgCn() {
			return "手机注册";
		}
	},
	/** 用户在APP上通过账号名注册 */
	APP("APP") {
		public String getMsg() {
			return "APP";
		}
		public String getMsgCn() {
			return "用户在APP上通过账号名注册";
		}
	},
	/** 用户在平台上通过账号名注册 */
	SYS("SYS") {
		public String getMsg() {
			return "SYS";
		}
		public String getMsgCn() {
			return "用户在平台上通过账号名注册";
		}
	};
	private String code;
	// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	OauthTypeEnum(String codeInput) {
		this.code = codeInput;
	}
	public String getCode() {
		return code;
	}
	@Override
	public String toString() {
		return code;
	}
	/**
	 * 英文信息
	 * 
	 * @Title: getMsg
	 * @Description:
	 *
	 * 				参数说明
	 * @return 返回类型 String
	 */
	public abstract String getMsg();
	/**
	 * 中文信息
	 * 
	 * @Title: getMsgCn
	 * @Description:
	 *
	 * 				参数说明
	 * @return 返回类型 String
	 */
	public abstract String getMsgCn();
}
