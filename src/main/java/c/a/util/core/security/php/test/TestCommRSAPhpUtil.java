package c.a.util.core.security.php.test;
import org.junit.Test;

import c.a.util.core.security.php.CommRSAPhpUtil;
import c.a.util.core.test.CommTest;
public class TestCommRSAPhpUtil extends CommTest {
	@Test
	public void decodce() {
		try {
			log.trace("1 plain=" + CommRSAPhpUtil.decode(
					"LPlZfA060yUpxhj6KYyJj/nIqxdPnSAlJtK63yYXf44kyW+KKzBPqKkoLsVIMR4ORW74k6TBO8BDg9Au5f7akzB/YLBsC4m18R2YXUbLqEROsz9yjZTq06yZOaKHmIyVjPQCmODOLD8tnlhBnT6OxA/QpQLq/IkiqKdYroD+2qI="));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void encodce() {
		try {
			log.trace("2 data=" + CommRSAPhpUtil.encode("我们爱中国"));
			log.trace("2 plain=" + CommRSAPhpUtil.decode(CommRSAPhpUtil.encode("我们爱中国")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
