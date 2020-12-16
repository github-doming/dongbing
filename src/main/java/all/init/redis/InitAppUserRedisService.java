package all.init.redis;
import all.app.common.service.AppUserRedisService;
import all.gen.app_user.t.entity.AppUserT;
import c.a.util.core.test.CommTest;
public class InitAppUserRedisService extends CommTest {
	// @Test
	public void init() {
		AppUserRedisService appUserRedisService = new AppUserRedisService();
		try {
			jdbcTool = this.findJdbcToolLocal();this.transactionStart(jdbcTool);
			AppUserT entity = new AppUserT();
			entity.setAppUserName("abc1");
			appUserRedisService.save(entity);
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		}
		this.transactionClose(jdbcTool);
		System.out.println("end");
	}
	
}
