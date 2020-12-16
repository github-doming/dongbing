package c.a.util.core.enums.bean;
/**
 * channel渠道类型
 * 
 * 手机APP端,平板APP端,PC端;
 * 
 * 邮箱端,短信端,微信公众号端;
 * 
 * @ClassName:
 * @Description:
 * @author
 * @date 2016年9月16日 下午6:10:50
 * 
 */
public enum ChannelTypeEnum {
	/** 新浪微博 */
	WEIBO("WEIBO") {
		@Override public String getMsg() {
			return "WEIBO";
		}
		@Override public String getMsgCn() {
			return "新浪微博";
		}
	},
	/** QQ */
	QQ("QQ") {
		@Override public String getMsg() {
			return "QQ";
		}
		@Override public String getMsgCn() {
			return "QQ";
		}
	},
	/** 微信 */
	WEIXIN("WEIXIN") {
		@Override public String getMsg() {
			return "WEIXIN";
		}
		@Override public String getMsgCn() {
			return "微信";
		}
	},
	/** 邮箱 */
	MAIL("MAIL") {
		@Override public String getMsg() {
			return "MAIL";
		}
		@Override public String getMsgCn() {
			return "邮箱";
		}
	},
	/**短信 */
	SMS("SMS") {
		@Override public String getMsg() {
			return "SMS";
		}
		@Override public String getMsgCn() {
			return "短信";
		}
	},
	/** APP端 */
	APP("APP") {
		@Override public String getMsg() {
			return "APP";
		}
		@Override public String getMsgCn() {
			return "APP端";
		}
	},
	/** PC端 */
	PC("PC") {
		@Override public String getMsg() {
			return "PC";
		}
		@Override public String getMsgCn() {
			return "PC端";
		}
	},
	/** NET端 */
	NET("NET") {
		@Override public String getMsg() {
			return "NET";
		}
		@Override public String getMsgCn() {
			return "NET端";
		}
	},
	/** ADMIN端业务管理 */
	ADMIN("ADMIN") {
		@Override public String getMsg() {
			return "ADMIN";
		}
		@Override public String getMsgCn() {
			return "业务管理";
		}
	},
	/** 平台 */
	SYS("SYS") {
		@Override public String getMsg() {
			return "SYS";
		}
		@Override public String getMsgCn() {
			return "平台";
		}
	};
	private String code;
	// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	ChannelTypeEnum(String codeInput) {
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
