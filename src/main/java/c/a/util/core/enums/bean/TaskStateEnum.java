package c.a.util.core.enums.bean;
/**
 * 任务状态或数据状态
 * 
 * @ClassName:
 * @Description:
 * @author
 * @date 2016年9月16日 下午6:10:50
 * 
 */
public enum TaskStateEnum {
	/** 是 */
	TRUE("TRUE") {
		public String getMsg() {
			return "TRUE";
		}
		public String getMsgCn() {
			return "是";
		}
	},
	/** 否 */
	FALSE("FALSE") {
		public String getMsg() {
			return "FALSE";
		}
		public String getMsgCn() {
			return "否";
		}
	},
	/** 成功 */
	SUCCESS("SUCCESS") {
		public String getMsg() {
			return "SUCCESS";
		}
		public String getMsgCn() {
			return "成功";
		}
	},
	/** 失败 */
	ERROR("ERROR") {
		public String getMsg() {
			return "ERROR";
		}
		public String getMsgCn() {
			return "失败";
		}
	},
	/** 新建 */
	NEW("NEW") {
		public String getMsg() {
			return "NEW";
		}
		public String getMsgCn() {
			return "新建";
		}
	},
	/** 草稿或起草 */
	DRAFT("DRAFT") {
		public String getMsg() {
			return "DRAFT";
		}
		public String getMsgCn() {
			return "草稿或起草";
		}
	},
	/** 开始 */
	START("START") {
		public String getMsg() {
			return "START";
		}
		public String getMsgCn() {
			return "开始";
		}
	},
	/** 初始化 */
	INIT("INIT") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "初始化";
		}
	},
	/** 等待 */
	WAIT("WAIT") {
		public String getMsg() {
			return "WAIT";
		}
		public String getMsgCn() {
			return "等待 ";
		}
	},
	/** 运行 */
	RUN("RUN") {
		public String getMsg() {
			return "RUN";
		}
		public String getMsgCn() {
			return "运行";
		}
	},
	/** 暂停 */
	PAUSE("PAUSE") {
		public String getMsg() {
			return "PAUSE";
		}
		public String getMsgCn() {
			return "暂停";
		}
	},
	/** 完成 */
	END("END") {
		public String getMsg() {
			return "END";
		}
		public String getMsgCn() {
			return "完成";
		}
	},
	/** 设计 */
	DESIGN("DESIGN") {
		public String getMsg() {
			return "DESIGN";
		}
		public String getMsgCn() {
			return "设计";
		}
	},
	/** 开发 */
	DEVELOP("DEVELOP") {
		public String getMsg() {
			return "DEVELOP";
		}
		public String getMsgCn() {
			return "开发";
		}
	},
	/** 测试 */
	TEST("TEST") {
		public String getMsg() {
			return "TEST";
		}
		public String getMsgCn() {
			return "测试";
		}
	},
	/** 已归档 */
	FILE("FILE") {
		public String getMsg() {
			return "FILE";
		}
		public String getMsgCn() {
			return "归档";
		}
	},
	/** 已打开或启用 */
	OPEN("OPEN") {
		public String getMsg() {
			return "OPEN";
		}
		public String getMsgCn() {
			return "打开或启用";
		}
	},
	/** 已关闭或禁用 */
	CLOSE("CLOSE") {
		public String getMsg() {
			return "CLOSE";
		}
		public String getMsgCn() {
			return "关闭或禁用";
		}
	},
	/** 缺省 */
	DEFAULT("DEFAULT") {
		public String getMsg() {
			return "DEFAULT";
		}
		public String getMsgCn() {
			return "缺省";
		}
	},
	/** 版本维护 */
	VERSION("VERSION") {
		public String getMsg() {
			return "VERSION";
		}
		public String getMsgCn() {
			return "版本维护";
		}
	},
	/** 历史记录 */
	HISTORY("HISTORY") {
		public String getMsg() {
			return "HISTORY";
		}
		public String getMsgCn() {
			return "历史记录";
		}
	},
	/** 取消 */
	CANCEL("CANCEL") {
		public String getMsg() {
			return "CANCEL";
		}
		public String getMsgCn() {
			return "取消";
		}
	},
	/** 已删除 */
	DEL("DEL") {
		public String getMsg() {
			return "DEL";
		}
		public String getMsgCn() {
			return "已删除";
		}
	};
	private String code;
	// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	TaskStateEnum(String codeInput) {
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
