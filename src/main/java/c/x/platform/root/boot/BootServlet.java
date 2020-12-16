package c.x.platform.root.boot;
import c.a.config.SysConfig;
import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.thread.pool.ThreadPoolExecutorService;
import c.a.util.core.thread.pool.ThreadPoolExecutorServiceListUtil;
import c.a.util.mq.activemq.ActiveMqPoolUtil;
import c.a.util.mq.activemq.ActiveMqUtil;
import c.a.util.mq.rabbitmq.RabbitMqUtil;
import c.a.util.netty.core.TcpNettyThread;
import c.a.util.redis.RedisExpiredThread;
import c.a.util.redis.RedisUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
/**
 *
 *
 * 理论上访问顺序一般先Listener，后Servlet;
 *
 * 测试时,访问顺序Listener和Servlet是不同线程开启,没有访问顺序,不过Listener一般先执行;
 *
 * @Description:
 * @ClassName:
 * @date 2012年3月10日 上午10:28:38
 * @author cxy
 * @Email:
 * @Copyright
 *
 */
public class BootServlet extends HttpServlet {
	protected Logger log = LogManager.getLogger(BootServlet.class);
	private static final long serialVersionUID = 1L;
	@Override
	public String getInitParameter(String name) {
		return super.getInitParameter(name);
	}
	@Override
	public void init(){
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		this.executeTime(request, response);
	}
	@Override
	public void destroy() {
		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil == null) {
			contextUtil = new ContextUtil();
			contextUtil.init();
			ContextThreadLocal.findThreadLocal().set(contextUtil);
		}
		try {
			// 不自动启动定时,需要手动启动
			String quartzLocalStart = BeanThreadLocal
					.find(SysConfig.findInstance().findMap().get("quartz.scheduler.start"), "");
			if ("true".equals(quartzLocalStart)) {
				// 控制台看不了，只能在日志里看
				log.trace("1 销毁,class=" + this.getClass().getName());
				System.out.println("2 销毁,class=" + this.getClass().getName());
//				if (false) {
//					new SysQuartzConfigService().pause(null);
//				}
			}
			//关闭线程池
			if (ThreadPoolExecutorServiceListUtil.getMap() != null){
				for (ThreadPoolExecutorService executorService : ThreadPoolExecutorServiceListUtil.getMap().values()){
					//执行此函数后线程池不再接收新任务，并等待所有任务执行完毕后销毁线程。此函数并不会等待线程销毁完毕，而是立即返回的
					executorService.findExecutorService().shutdown();
					//如想要同步等待线程池完成关闭，可使用下面的函数判断是否都执行完毕了，该函数等待timeout后，返回是否所有任务都执行完毕了
					executorService.findExecutorService().awaitTermination(1, TimeUnit.SECONDS);
					//尝试结束所有活动线程，并返回等待队列里的任务
					executorService.findExecutorService().shutdownNow();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil != null) {
			contextUtil.remove();
			ContextThreadLocal.findThreadLocal().remove();
		}
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.executeTime(request, response);
	}
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.executeTime(request, response);
	}
	public void executeTime(HttpServletRequest request, HttpServletResponse response) {
		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil == null) {
			contextUtil = new ContextUtil();
			contextUtil.init();
			ContextThreadLocal.findThreadLocal().set(contextUtil);
		}
		// 执行业务
		log.trace("BootServlet 初始化开始");
		log.trace("class=" + this.getClass().getName());
		String servletPath = null;
		if (request != null) {
			servletPath = request.getServletPath();
			String logStr = "调用url=" + servletPath;
			log.info(logStr);
		}
		try {
			this.execute(request, response);
		} catch (Exception e) {
			String logStr = "Exception,出错的url=" + servletPath;
			String logFunError = "BootServlet功能出错,";
			logStr = logFunError + logStr;
			log.error(logStr, e);
		}
		contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil != null) {
			contextUtil.remove();
			ContextThreadLocal.findThreadLocal().remove();
		}
	}
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		if (false) {
//			SysConfig sysConfig = SysConfig.findInstance();
//			Map<String, MvcActionDto> mvcConfig = MvcConfig.findInstance().findMap();
//			DataSourceUtil jdbcDataSource = DataSourceListUtil.findInstance().findLocal();
//		}
		String redisLocalStart = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("redis.local.start"), "");
		if ("true".equals(redisLocalStart)) {
			// Redis初始化
			RedisUtil redisUtil = RedisUtil.findInstance();
			Jedis jedis = redisUtil.findJedis();
			jedis.close();
			// RedisExpired
			RedisExpiredThread redisExpiredThread = new RedisExpiredThread();
			// ThreadPoolExecutorService threadExecutorService =
			// ThreadPoolExecutorServiceListUtil.findInstance()
			// .findThreadExecutorService("local2");
			ThreadPoolExecutorService threadPoolExecutorService = ThreadPoolExecutorServiceListUtil.findInstance()
					.findLocal();
			ExecutorService executorService = threadPoolExecutorService.findExecutorService();;
			executorService.execute(redisExpiredThread);
			// executorService.shutdown();
		}
		// 不自动启动定时,需要手动启动
		String quartzLocalStart = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("quartz.scheduler.start"),
				"");
		if ("true".equals(quartzLocalStart)) {
			String quartzStart = SysConfig.findInstance().findMap().get("quartz.scheduler.start").toString();
			log.trace("1 quartzStart=" + quartzStart + ",class=" + this.getClass().getName());
			quartzStart = SysConfig.findInstance().findMap().get("quartz.scheduler.start").toString();
			log.trace("2 quartzStart=" + quartzStart + ",class=" + this.getClass().getName());
		}
		// rabbitmq接收监听
		String rabbitmqStart = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("rabbitmq.local.start"),
				"false");
		String rabbitmqSecurityReceive = BeanThreadLocal
				.find(SysConfig.findInstance().findMap().get("rabbitmq.security.receive"), "false");
		log.trace("rabbitmqStart=" + rabbitmqStart);
		if ("true".equals(rabbitmqStart) && ("true".equals(rabbitmqSecurityReceive))) {
			RabbitMqUtil mqUtil = RabbitMqUtil.findInstance();
			mqUtil.doBindExchange("exchange_cms_topic");
			mqUtil.receiveExchange("exchange_cms_topic", "queue_cms_topic");
			mqUtil.doBindExchange("exchange_cms_post");
			mqUtil.receiveExchange("exchange_cms_post", "queue_cms_post");
			// exchange_save_php
			mqUtil.doBindExchange("exchange_save_php");
			mqUtil.receiveExchange("exchange_save_php", "queue_save_php");
		}
		// activeMq
		String activeMqStart = getInitParameter("ActiveMq");
		activeMqStart = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("activemq.local.start"), "false");
		log.trace("activemq=" + activeMqStart);
		if ("true".equals(activeMqStart)) {
			ActiveMqPoolUtil activeMqPoolUtil = ActiveMqPoolUtil.findInstance();
			activeMqPoolUtil.receiveQueue();
			ActiveMqUtil activeMqUtil = ActiveMqUtil.findInstance();
			activeMqUtil.receiveTopic();
		}
		//netty
		String nettyLocalStart = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("netty.local.start"), "false");
		if ("true".equals(nettyLocalStart)) {
			TcpNettyThread  nettyThread = new TcpNettyThread();
			ThreadPoolExecutorService threadPoolExecutorService = ThreadPoolExecutorServiceListUtil.findInstance()
					.findLocal();
			ExecutorService executorService = threadPoolExecutorService.findExecutorService();
			executorService.execute(nettyThread);
		}

	}
}