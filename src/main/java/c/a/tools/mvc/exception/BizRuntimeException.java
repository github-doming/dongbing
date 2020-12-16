package c.a.tools.mvc.exception;
public class BizRuntimeException extends RuntimeException {
	public BizRuntimeException() {
		super();
	}
	public BizRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
	public BizRuntimeException(String msg) {
		super(msg);
	}
	public BizRuntimeException(Throwable cause) {
		super(cause);
	}
}
