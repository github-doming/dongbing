package all.app.common.expire;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.config.core.CommContextUtil;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.transaction.TransactionBase;
import redis.clients.jedis.JedisPubSub;
/**
 * 
 * 不能长久等待JDBC连接;
 * 
 * 
 * token过期监听
 * 
 * 
 * @Description:
 * @ClassName: RedisTokenExpiredListener
 * @date 2016年6月20日 下午9:44:48
 * @author cxy
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public abstract class RedisJedisPubSub extends JedisPubSub {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 
	 * 下一个action
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract String execute(String token) throws Exception;
	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
		log.trace("redis onPSubscribe pattern=" + pattern);
		log.trace("redis onPSubscribe subscribedChannels=" + subscribedChannels);
	}
	@Override
	public void onPMessage(String pattern, String channel, String message) {
		CommContextUtil commContextUtil=new CommContextUtil();
		long startCalendarLong = 0;
		log.trace("redis onPMessage pattern=" + pattern);
		log.trace("redis onPMessage channel=" + channel);
		log.trace("redis onPMessage message=" + message);
		TransactionBase transactionBase = new TransactionBase();
		IJdbcTool jdbcTool = null;
		try {
			startCalendarLong =commContextUtil.start();
			jdbcTool = transactionBase.findJdbcToolLocal();
			transactionBase.transactionStart(jdbcTool);
			String[] tokenArray = message.split("#");
			this.execute(tokenArray[1]);
			transactionBase.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			transactionBase.transactionRoll(jdbcTool);
		} finally {
			transactionBase.transactionClose(jdbcTool);
			commContextUtil.end(startCalendarLong);
		}
	}
}
