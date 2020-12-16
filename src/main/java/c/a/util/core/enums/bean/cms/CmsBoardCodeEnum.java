package c.a.util.core.enums.bean.cms;
/**
 * 
 * 消息版块编码
 * 
 * @ClassName:
 * @Description:
 * @author
 * @date 2016年9月16日 下午6:10:50
 * 
 */
public enum CmsBoardCodeEnum {
	/** 反馈 */
	FEEDBACK("FEEDBACK") {
		public String getMsg() {
			return "FEEDBACK";
		}
		public String getMsgCn() {
			return "反馈";
		}
	},
	/** 首页公告 */
	NOTICE_INDEX("NOTICE_INDEX") {
		public String getMsg() {
			return "NOTICE_INDEX";
		}
		public String getMsgCn() {
			return "首页公告";
		}
	},
	/** 轮播公告*/
	NOTICE_ROLL("NOTICE_ROLL") {
		public String getMsg() {
			return "NOTICE_ROLL";
		}
		public String getMsgCn() {
			return "轮播公告";
		}
	},
	/** 会话,聊天 */
	CHAT("CHAT") {
		public String getMsg() {
			return "CHAT";
		}
		public String getMsgCn() {
			return "会话";
		}
	},
	/** 消息*/
	MSG("MSG") {
		public String getMsg() {
			return "MSG";
		}
		public String getMsgCn() {
			return "消息";
		}
	};
	private String code;
	// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	CmsBoardCodeEnum(String codeInput) {
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
