package c.a.util.core.exception;
/**
 * 
 * 
 * 连接异常,unChecked异常，
 * 
 * @Description:
 * @ClassName: NetConntionRuntimeException
 * @date 2016年11月2日 下午2:50:10
 * @author cxy
 * @Email: 
 * @Copyright 
 * 
 */
public class NetConntionRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public NetConntionRuntimeException() {
		super();
	}
	public NetConntionRuntimeException(String msg, Throwable cause) {
		super(msg);
		super.initCause(cause);
	}
	public NetConntionRuntimeException(String msg) {
		super(msg);
	}
	public NetConntionRuntimeException(Throwable cause) {
		super();
		super.initCause(cause);
	}
}
