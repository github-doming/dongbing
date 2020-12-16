package c.a.util.core.enums;
/**
 *
 * http返回码
 *
 * @Description:
 * @ClassName:
 * @date 2012年3月21日 下午4:29:57
 * @author cxy
 * @Email:
 * @Copyright
 *
 */
public enum ReturnCodeEnum {
	/** 注册成功 */
	app200Register("app200Register") {
		public String getMsg() {
			return "注册成功";
		}
		public String getMsgCn() {
			return "注册成功";
		}
	},
	/** 找到登录用户 */
	app200Login("app200Login") {
		public String getMsg() {
			return "登录成功";
		}
		public String getMsgCn() {
			return "登录成功";
		}
	},
	/** 退出成功 */
	app200Logout("app200Logout") {
		public String getMsg() {
			return "退出成功";
		}
		public String getMsgCn() {
			return "退出成功";
		}
	},
	/** 通过appUserId查找个人信息成功 */
	app200UserQuery("app200UserQuery") {
		public String getMsg() {
			return "通过appUserId查找个人信息成功";
		}
		public String getMsgCn() {
			return "通过appUserId查找个人信息成功";
		}
	},
	/** 通过token查找个人信息成功 */
	app200TokenQuery("app200TokenQuery") {
		public String getMsg() {
			return "通过token查找个人信息成功";
		}
		public String getMsgCn() {
			return "通过token查找个人信息成功";
		}
	},
	/** 刷新验证码成功 */
	app200Token("app200Token") {
		public String getMsg() {
			return " 刷新验证码成功";
		}
		public String getMsgCn() {
			return " 刷新验证码成功";
		}
	},
	/** 得到SessionId */
	app200Session("app200Session") {
		public String getMsg() {
			return " 找到Session成功";
		}
		public String getMsgCn() {
			return " 找到Session成功";
		}
	},
	/**JSON参数有错,或者JSON数据为空*/
	app400JSON("app400JSON") {
		public String getMsg() {
			return "JSON参数有错,或者JSON数据为空";
		}
		public String getMsgCn() {
			return "JSON参数有错,或者JSON数据为空";
		}
	},
	/**Cmd参数有错 */
	app400Cmd("app400Cmd") {
		public String getMsg() {
			return "Cmd参数有错";
		}
		public String getMsgCn() {
			return "Cmd参数有错";
		}
	},

	/** 查询的值不能为空 */
	app400Blank("app400Blank") {
		public String getMsg() {
			return "查询的值不能为空";
		}
		public String getMsgCn() {
			return "查询的值不能为空";
		}
	},

	/** 用户名或密码输入有错 */
	app400Login("app400Login") {
		public String getMsg() {
			return "用户名或密码输入有错";
		}
		public String getMsgCn() {
			return "用户名或密码输入有错";
		}
	},
	/** 用户被禁用 */
	app404LoginDisable("app404LoginDisable") {
				public String getMsg() {
					return "用户被禁用或用户不属于该平台";
				}
				public String getMsgCn() {
					return "用户被禁用或用户不属于该平台";
				}
	},
	/** 密码输入有错或者为空 */
	app400Password("app400Password") {
		public String getMsg() {
			return "密码输入有错或者为空";
		}
		public String getMsgCn() {
			return "密码输入有错或者为空";
		}
	},
	/**当前密码输入有错 */
	app400PasswordOld("app400PasswordOld") {
		public String getMsg() {
			return "当前密码输入有错";
		}
		public String getMsgCn() {
			return "当前密码输入有错";
		}
	},
	/**Session参数有错 */
	app400Session("app400Session") {
		public String getMsg() {
			return "Session参数有错";
		}
		public String getMsgCn() {
			return "Session参数有错";
		}
	},
	/** 验证码输入有错 */
	app400VerifyCode("app400VerifyCode") {
		public String getMsg() {
			return "验证码输入有错";
		}
		public String getMsgCn() {
			return "验证码输入有错";
		}
	},
	/** token有错,找不到登录用户(当前请求需要用户验证) */
	app401Token("app401Token") {
		public String getMsg() {
			return "token有错,找不到登录用户";
		}
		public String getMsgCn() {
			return "token有错,找不到登录用户";
		}
	},
	/** 没有加密,没有授权,Forbidden*/
	app403TokenRSA("app403TokenRSA") {
		public String getMsg() {
			return "token没有加密";
		}
		public String getMsgCn() {
			return "token没有加密";
		}
	},
	/** 找不到Session */
	app404Session("app404Session") {
		public String getMsg() {
			return "找不到Session";
		}
		public String getMsgCn() {
			return "找不到Session";
		}
	},
	/** 找不到验证码*/
	app404VerifyCode("app404VerifyCode") {
		public String getMsg() {
			return "找不到验证码";
		}
		public String getMsgCn() {
			return "找不到验证码";
		}
	},
	/** 用户名不存在 */
	app404Login("app404Login") {
		public String getMsg() {
			return "用户名不存在";
		}
		public String getMsgCn() {
			return "用户名不存在";
		}
	},
	/** 账户已存在 */
	app409Register("app409Register") {
		public String getMsg() {
			return "账户已存在";
		}
		public String getMsgCn() {
			return "账户已存在";
		}
	},
	/** 用户名或密码验证失败 */
	app409RegisterMatcher("app409RegisterMatcher") {
		public String getMsg() {
			return "账号或密码由6-20位字母、数字组合";
		}
		public String getMsgCn() {
			return "账号或密码由6-20位字母、数字组合";
		}
	},
	/** 发送者跟接收者不能是同一个人 */
	app409CmsSend("409") {
		public String getMsg() {
			return "发送者跟接收者不能是同一个人";
		}
		public String getMsgCn() {
			return "发送者跟接收者不能是同一个人";
		}
	},
	/** 服务器验证码有错 */
	app500VerifyCode("app500VerifyCode") {
		public String getMsg() {
			return "服务器验证码有错";
		}
		public String getMsgCn() {
			return "服务器验证码有错";
		}
	},
	/** 请求成功 */
	code200("200") {
		public String getMsg() {
			return "请求成功";
		}
		public String getMsgCn() {
			return "请求成功";
		}
	},
	/** 没有修改 */
	code304("304") {
		public String getMsg() {
			return "Not Modified";
		}
		public String getMsgCn() {
			return "没有修改";
		}
	},
	/** 请求参数有误 */
	code400("400") {
		public String getMsg() {
			return "请求参数有误";
		}
		public String getMsgCn() {
			return "请求参数有误";
		}
	},
	/** 当前请求需要用户验证 */
	code401("401") {
		public String getMsg() {
			return "当前请求需要用户验证";
		}
		public String getMsgCn() {
			return "当前请求需要用户验证";
		}
	},
	/** 没有授权 */
	code403("403") {
		public String getMsg() {
			return "没有授权";
		}
		public String getMsgCn() {
			return "没有授权";
		}
	},
	/** 没有授权 */
	code403Disable("code403Disable") {
		public String getMsg() {
			return "账号已被禁用，请联系管理员";
		}
		public String getMsgCn() {
			return "账号已被禁用，请联系管理员";
		}
	},
	/** 找不到资源或找不到数据 */
	code404("404") {
		public String getMsg() {
			return "找不到资源";
		}
		public String getMsgCn() {
			return "找不到资源";
		}
	},
	/** 由于和被请求的资源的当前状态之间存在冲突，请求无法完成。 */
	code409("409") {
		public String getMsg() {
			return "和被请求的资源之间存在冲突";
		}
		public String getMsgCn() {
			return "和被请求的资源之间存在冲突";
		}
	},
	/** 服务器有错，请稍候再试 */
	code500("500") {
		public String getMsg() {
			return "服务器有错，请稍候再试";
		}
		public String getMsgCn() {
			return "服务器有错，请稍候再试";
		}
	},
	/** 系统繁忙，请稍候再试 */
	code503("503") {
		public String getMsg() {
			return "系统繁忙，请稍候再试";
		}
		public String getMsgCn() {
			return "系统繁忙，请稍候再试";
		}
	},
	/** 其它 */
	code900("900") {
		public String getMsg() {
			return "en其它";
		}
		public String getMsgCn() {
			return "其它";
		}
	};
	private String code;
	// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	ReturnCodeEnum(String codeInput) {
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
