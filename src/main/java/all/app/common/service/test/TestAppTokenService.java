package all.app.common.service.test;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Test;

import all.app.common.service.AppTokenService;
import all.gen.app_token.t.entity.AppTokenT;
import c.a.tools.jdbc.IJdbcTool;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.test.CommTest;
public class TestAppTokenService extends CommTest {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 
	 * 查询登录用户的token(AppTokenT)
	 * 
	 * @return
	 * @throws Exception
	 */
	@Test
	public void findToken() throws Exception {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			AppTokenService appTokenService = new AppTokenService();
			AppTokenT appTokenT = appTokenService.findAppToken("", "cjx", "cjx", ChannelTypeEnum.APP.getCode());
			System.out.println("userId=" + appTokenT.getAppUserId());
			System.out.println("token=" + appTokenT.getValue());
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
		System.out.println("end	");
	}
}
