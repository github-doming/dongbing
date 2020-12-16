package c.a.util.job;
public enum TriggerStateEnum {
	/** 运行 */
	NORMAL("NORMAL") {
		public String getMsg() {
			return "NORMAL";
		}
		public String getMsgCn() {
			return "运行";
		}
	},
	/** 停止 */
	PAUSED("PAUSED") {
		public String getMsg() {
			return "PAUSED";
		}
		public String getMsgCn() {
			return "停止";
		}
	},
	/** 完成 */
	COMPLETE("COMPLETE") {
		public String getMsg() {
			return "COMPLETE";
		}
		public String getMsgCn() {
			return "完成";
		}
	},
	/** 出错 */
	ERROR("ERROR") {
		public String getMsg() {
			return "ERROR";
		}
		public String getMsgCn() {
			return "出错";
		}
	},
	/** 阻塞 */
	BLOCKED("BLOCKED") {
		public String getMsg() {
			return "BLOCKED";
		}
		public String getMsgCn() {
			return "阻塞";
		}
	},
	/** 没有启动 */
	NONE("NONE") {
		public String getMsg() {
			return "NONE";
		}
		public String getMsgCn() {
			return "没有启动";
		}
	}	,
	/** 结束 */
	END("END") {
		public String getMsg() {
			return "END";
		}
		public String getMsgCn() {
			return "结束";
		}
	}
	;
	private String code;
	// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	TriggerStateEnum(String codeInput) {
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
	 * @Title: getMsg @Description: 英文信息 @return 参数说明 @return String
	 *         返回类型 @throws
	 */
	public abstract String getMsg();
	/**
	 * 中文信息
	 * 
	 * @Title: getMsgCn @Description: 中文信息 @return 参数说明 @return String
	 *         返回类型 @throws
	 */
	public abstract String getMsgCn();
}
