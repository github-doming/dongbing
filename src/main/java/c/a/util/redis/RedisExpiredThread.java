package c.a.util.redis;
import all.app.common.expire.RedisExpiredTokenListener;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.thread.common.CommThread;
import redis.clients.jedis.Jedis;
/**
 * 
 * 不能长久等待JDBC连接
 * 
 * @Description:
 * @date 2018年8月30日 下午7:21:05
 * @author cxy
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class RedisExpiredThread extends CommThread {
	private JsonTcpBean _JsonTcpBean = null;
	public JsonTcpBean getJsonTcpBean() {
		return _JsonTcpBean;
	}
	public void setJsonTcpBean(JsonTcpBean _JsonTcpBean) {
		this._JsonTcpBean = _JsonTcpBean;
	}
	public RedisExpiredThread() {
		// 不能长久等待JDBC连接
		// 不需要数据库操作
		this.database = false;
		// 不需要事务操作
		transaction = false;
	}
	@Override
	public String execute() throws Exception {
		RedisUtil redisUtil = RedisUtil.findInstance();
		Jedis jedis = redisUtil.findJedis();
		RedisExpiredTokenListener redisKeyExpiredListener = new RedisExpiredTokenListener();
		redisUtil.psubscribe(jedis, redisKeyExpiredListener, "__keyevent@*__:expired");
		// 下面的代码不会执行
		System.out.println("end class=" + this.getClass());
		return null;
	}
}
