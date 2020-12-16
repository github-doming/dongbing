package c.a.util.core.verify_code;

public class VerifyCodeUtil {
	/**
	 * 4位数字验证码
	 * @Title: findVerifyCode 
	 * @Description: 
	 *
	 * 参数说明 
	 * @return 
	 * 返回类型 String
	 */
	public static String findVerifyCode() {

		String rand = String.valueOf(Math.random() * 1000000D);
		switch (rand.length()) {
			case 1 : // '\001'
				rand = (new StringBuilder("000")).append(rand).toString();
				break;
			case 2 : // '\002'
				rand = (new StringBuilder("00")).append(rand).toString();
				break;
			case 3 : // '\003'
				rand = (new StringBuilder("0")).append(rand).toString();
				break;
			default :
				rand = rand.substring(0, 4);
				break;
		}
		return rand;	

	}

}
