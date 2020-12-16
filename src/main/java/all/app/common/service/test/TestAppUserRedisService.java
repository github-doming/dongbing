package all.app.common.service.test;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import all.app.common.service.AppUserRedisService;
import all.gen.app_user.t.entity.AppUserT;
import c.a.tools.jdbc.IJdbcTool;
import c.a.util.core.test.CommTest;
public class TestAppUserRedisService extends CommTest {
	@Test
	public void print() {
		AppUserRedisService appUserRedisService = new AppUserRedisService();
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			appUserRedisService.print();
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		}
		this.transactionClose(jdbcTool);
		System.out.println("end");
	}
	// @Test
	public void init() {
		AppUserRedisService appUserRedisService = new AppUserRedisService();
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			appUserRedisService.init();
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		}
		this.transactionClose(jdbcTool);
		System.out.println("end");
	}
	// @Test
	public void save() {
		AppUserRedisService appUserRedisService = new AppUserRedisService();
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
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
	// @Test
	public void del() {
		AppUserRedisService appUserRedisService = new AppUserRedisService();
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			appUserRedisService.del("5aa86abd78694b62b9ca542ca903146c");
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		}
		this.transactionClose(jdbcTool);
		System.out.println("end");
	}
	@Test
	public void update(String currentUserTenantCode) {
		AppUserRedisService appUserRedisService = new AppUserRedisService();
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			AppUserT entity = new AppUserT();
			entity.setAppUserId("86f13fc6033e4d1ba089a1bc5a951d50");
			entity.setAppUserName("c1");
			appUserRedisService.update(entity);
			// this.doTransactionPost();
			appUserRedisService.updateAppUser(currentUserTenantCode,entity);
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		}
		this.transactionClose(jdbcTool);
		System.out.println("end");
	}
	// @Test
	public void find() {
		AppUserRedisService appUserRedisService = new AppUserRedisService();
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			AppUserT entity = appUserRedisService.find("c90a156c56874f119bda8ed9ee0b1bc3");
			if (entity != null) {
				System.out.println("name=" + entity.getAppUserName());
			}
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		}
		this.transactionClose(jdbcTool);
		System.out.println("end");
	}
	// @Test
	public void findAll() {
		AppUserRedisService appUserRedisService = new AppUserRedisService();
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			List<AppUserT> listObject = appUserRedisService.findAllObjectByRedis();
			System.out.println("size=" + listObject.size());
			for (AppUserT entity : listObject) {
				System.out.println("name=" + entity.getAppUserName());
			}
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		}
		this.transactionClose(jdbcTool);
		System.out.println("end");
	}
	// @Test
	public void findPageMap() {
		AppUserRedisService appUserRedisService = new AppUserRedisService();
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			List<Map<String, String>> list = appUserRedisService.findPageMapByRedis(2, 3);
			if (list != null) {
				System.out.println("size=" + list.size());
				for (Map<String, String> map : list) {
					System.out.println("m  id=" + map.get("appUserId"));
					System.out.println("name=" + map.get("userName"));
				}
			}
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		}
		this.transactionClose(jdbcTool);
		System.out.println("end");
	}
	// @Test
	public void findPageObject() {
		AppUserRedisService appUserRedisService = new AppUserRedisService();
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			List<AppUserT> listObject = appUserRedisService.findPageObjectByRedis(2, 3);
			if (listObject != null) {
				System.out.println("size=" + listObject.size());
				for (AppUserT entity : listObject) {
					System.out.println("o id=" + entity.getAppUserId());
					System.out.println("name=" + entity.getAppUserName());
				}
			}
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		}
		this.transactionClose(jdbcTool);
		System.out.println("end");
	}
}
