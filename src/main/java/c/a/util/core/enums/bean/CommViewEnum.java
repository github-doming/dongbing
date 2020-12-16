package c.a.util.core.enums.bean;
/**
 * 视图
 * 
 * @ClassName:
 * @Description:
 * @author
 * @date 2016年9月16日 下午6:10:50
 * 
 */
public enum CommViewEnum {
	/** index */
	index("index") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "index";
		}
	},
	/** cds统计局大数据风格 */
	cds("cds") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "cds";
		}
	},
	/** ace风格 */
	ace("ace") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "ace";
		}
	},
	/** Miniui */
	Miniui("Miniui") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "Miniui";
		}
	},
	/** Easyui */
	Easyui("Easyui") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "Easyui";
		}
	},
	/** Kaida风格 */
	Kaida("Kaida") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "Kaida";
		}
	},
	/** 培训系统风格 */
	train("train") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "train";
		}
	},
	/** 幼儿园风格 */
	nursery("nursery") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "nursery";
		}
	},
	/** Bootstrap */
	Bootstrap("Bootstrap") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "Bootstrap";
		}
	},
	/** BootstrapObject */
	BootstrapObject("BootstrapObject") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "BootstrapObject";
		}
	},
	/** Default */
	Default("Default") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "Default";
		}
	},
	/** DefaultObject */
	DefaultObject("DefaultObject") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "DefaultObject";
		}
	},
	/** SYS */
	SYS("SYS") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "SYS";
		}
	},
	/** APP */
	APP("APP") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "APP";
		}
	};
	private String code;
	// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	CommViewEnum(String codeInput) {
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
