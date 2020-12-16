package all.workflow.actwf.actwf_business_def.t.test;
import java.util.Date;

import org.junit.Test;

import all.workflow.actwf.actwf_business_def.t.entity.ActwfBusinessDefT;
import all.workflow.actwf.actwf_business_def.t.service.ActwfBusinessDefTService;
import c.a.util.core.test.CommTest;
import c.a.util.redis.RedisUtil;
import redis.clients.jedis.Jedis;
public class TestWfBusiness_create extends CommTest {
	/**
	 * 工作流业务定义测试
	 * 
	 * @Title: execute
	 * @Description:
	 *
	 * 				参数说明 返回类型 void
	 */
	@Test
	public void execute() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			this.biz();
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			this.transactionRoll(jdbcTool);
			e.printStackTrace();
		}
		System.out.println("end");
	}
	public void biz() throws Exception {
		Date date = new Date();
		ActwfBusinessDefTService service = new ActwfBusinessDefTService();
		ActwfBusinessDefT entity = new ActwfBusinessDefT();
		entity.setName("a1");
		entity.setActDefId("processId_a:2:62508");
		entity.setActDefKey("processId_a");
		service.save(entity);
		// int i = 1 / 0;
		entity.setName("a2");
		entity.setActDefId("processId_a:2:62508");
		entity.setActDefKey("processId_a");
		entity.setCreateTime(date);
		entity.setCreateTimeLong(date.getTime());
		String primaryValue = service.save(entity);
		RedisUtil redisUtil = RedisUtil.findInstance();
		Jedis jedis = redisUtil.findJedis();
		redisUtil.save(jedis, entity, primaryValue);
		entity.setName("a3");
		redisUtil.update(jedis, entity, primaryValue);
		entity.setUpdateTime(date);
		entity.setUpdateTimeLong(date.getTime());
		redisUtil.update(jedis, entity, "f43e6a2462b54d07bf04ba1ef3fe5fa6");
	}
}
