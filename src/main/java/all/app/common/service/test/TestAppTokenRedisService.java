package all.app.common.service.test;
import org.junit.Test;

import all.app.common.service.AppTokenRedisService;
import c.a.util.core.test.CommTest;
public class TestAppTokenRedisService extends CommTest {
	@Test
	public void execute() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			AppTokenRedisService appTokenRedisService = new AppTokenRedisService();
			String appUserId = appTokenRedisService.findAppUserIdByToken("5e59902c3a274b16a2703f9f8a1b4a5c");
			System.out.println("appUserId=" + appUserId);
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
