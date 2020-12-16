package c.a.util.redis;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Id;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.quartz.SchedulerException;
import c.a.config.SysConfig;
import c.a.util.core.annotation.AnnotationTable;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.jdbc.mysql.PageMysqlBean;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.reflect.ReflectThreadLocal;
import c.a.util.core.reflect.ReflectUtil;
import c.a.util.core.string.StringUtil;
import c.a.util.core.uuid.Uuid;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
/**
 * 
 * Redis最常用的有三种;
 * 
 * 
 * 1 表的所有数据(支持分页);
 * 
 * 2 分页与排序;
 * 
 * 3 模糊查询;
 * 
 * 5 精确查询 ;
 * 
 * @Description:
 * @ClassName: LogUtil
 * @date 2015年10月16日 下午7:05:10
 * @author cxy
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class RedisUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	// 单例
	private volatile static RedisUtil instance = null;
	private final static Object key = new Object();
	/**
	 * 基本参数
	 */
	// Redis服务器IP
	private static String ip = "192.168.0.1";
	// Redis的端口号
	private static int port = 6379;
	// 访问密码
	// private static String auth = "admin";
	private static String password = "";
	// 可用连接实例的最大数目，默认值为8；
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	// private static int MAX_ACTIVE = 1000;
	private static int MAX_ACTIVE = 1000;
	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	// private static int MAX_IDLE = 1000;
	private static int MAX_IDLE = 1000;
	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = 3000;
	// 设置redis将会关闭超时超过30秒的空闲连接。而不是设置读取数据的超时时间。
	private static int TIMEOUT = 30000;
	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;
	private static JedisPool jedisPool = null;
	/**
	 * 扩展参数
	 */
	// 分割符号
	private String separator = "#";
	// 数据库的前缀
	public static int dbIndex = 1;
	// 表的前缀
	private String tablePrefix = "table#";
	// 数据库数据的前缀
	private String dataPrefix = "data#";
	// 精确查询的前缀
	private String queryExactPrefix = "qE#";
	// 模糊查询的前缀
	private String queryFuzzyPrefix = "qF#";
	// 数据库的前缀
	private int expireIndex = 2;
	// 过期时间的前缀
	private String expirePrefix = "expire#";
	// token的前缀
	private String tokenPrefix = "token#";
	// 过期时间(秒)
	// private static int tokenExpire = 1;
	// 过期时间 6小时
	private static int tokenExpire = 21600;
	/**
	 * @deprecated
	 */
	// 数据库的前缀
	private int appVerifyCodeIndex = 3;
	/**
	 * @deprecated
	 */
	// appVerifyCode 的前缀
	private String appVerifyCodePrefix = "app#verifyCode#";
	// 数据库的前缀
	private int sysAdminVerifyCodeIndex = 3;
	/**
	 * @deprecated
	 */
	private String sysAdminVerifyCodePrefix = "sysAdmin#verifyCode#";
	// 构造函数
	private RedisUtil() {
	}
	public static RedisUtil findInstance() throws Exception {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new RedisUtil();
					// 初始化
					init();
				}
			}
		}
		return instance;
	}
	/**
	 * 初始化
	 * 
	 * @Title: init
	 * @Description:
	 *
	 * 				参数说明
	 * @throws SchedulerException
	 * @throws Exception
	 *             返回类型 void
	 */
	private static void init() throws Exception {
		// 初始化Redis连接池
		// log.trace("初始化Redis连接池,class=" + LogUtil.class.getName());
		ip = SysConfig.findInstance().findMap().get("redis.local.ip").toString();
		port = BeanThreadLocal.findThreadLocal().get()
				.find(SysConfig.findInstance().findMap().get("redis.local.port").toString(), port);
		password = SysConfig.findInstance().findMap().get("redis.local.password").toString();
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(MAX_ACTIVE);
		config.setMaxIdle(MAX_IDLE);
		config.setMaxWaitMillis(MAX_WAIT);
		config.setTestOnBorrow(TEST_ON_BORROW);
		jedisPool = new JedisPool(config, ip, port, TIMEOUT, password);
		tokenExpire = BeanThreadLocal.findThreadLocal().get()
				.find(SysConfig.findInstance().findMap().get("redis.local.expire").toString(), 1);
	}
	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	public Jedis findJedis() throws Exception {
		Jedis resource = null;
		// log.trace("jedisPool.isClosed()=" + jedisPool.isClosed());
		if (jedisPool != null) {
			try {
				resource = jedisPool.getResource();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resource;
	}
	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public void close(Jedis jedis) {
		if (jedis != null) {
			// 这里很重要，一旦拿到的jedis实例使用完毕，必须要返还给池中
			// Jedis 3.0以下版本用,看源代码可得知 by cjx
			// jedisPool.returnResource(jedis);
			jedis.close();
		}
	}
	public void doExpireToken(Jedis jedis, String token) throws Exception {
		String tokenKey = this.findKeyExpire(token);
		// 建议在redis配置文件中设置
		doExpireConfig(jedis);
		jedis.select(expireIndex);
		jedis.set(tokenKey, token);
		jedis.expire(tokenKey, tokenExpire);
	}
	private void doExpireConfig(Jedis jedis) {
		String parameter = "notify-keyspace-events";
		List<String> notify = jedis.configGet(parameter);
		if (notify.get(1).equals("")) {
			// 过期事件
			jedis.configSet(parameter, "Ex");
		}
	}
	/**
	 * 构造Expire
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param id
	 * @return 返回类型 String
	 */
	public String findKeyExpire(String id) {
		return this.expirePrefix + id;
	}
	/**
	 * Redis Psubscribe 命令订阅一个或多个符合给定模式的频道。
	 * 
	 * @Title: psubscribe
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 *            返回类型 void
	 */
	public void psubscribe(Jedis jedis, JedisPubSub jedisPubSub, final String... patterns) {
		if (jedis != null) {
			jedis.psubscribe(jedisPubSub, patterns);
		}
	}
	/**
	 * 构造token
	 * 
	 * @Description:
	 * @return 返回类型 String
	 */
	public String findKeyToken(String token) {
		return this.tokenPrefix + token;
	}
	/**
	 * 构造表名
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param tableName
	 * @return 返回类型 String
	 */
	public String findKeyTable(String tableName) {
		return this.tablePrefix + tableName;
	}
	/**
	 * 
	 * @Title: findKey
	 * @Description:
	 *
	 * 				参数说明
	 * @param tableName
	 * @param primaryValue
	 * @return 返回类型 String
	 */
	public String findKey(String tableName, String primaryValue) {
		return this.dataPrefix + tableName + separator + primaryValue;
	}
	public String findKeyQueryExact(String tableName, String columnName) {
		return this.queryExactPrefix + tableName + separator + columnName;
	}
	public String findKeyQueryFuzzy(String tableName, String columnName, String name) {
		return this.queryFuzzyPrefix + tableName + separator + columnName + separator + name;
	}
	/**
	 * redis操作Map
	 */
	public void saveForMap(Jedis jedis, String key, Map<String, String> map) {
		if (jedis != null) {
			if (map.size() != 0) {
				jedis.hmset(key, map);
			}
		}
	}
	/**
	 * redis操作Map
	 */
	public void delForMap(Jedis jedis, String key, final String... fieldList) {
		if (jedis != null) {
			// 删除map中的某个键值
			jedis.hdel(key, fieldList);
		}
	}
	/**
	 * redis操作Map
	 */
	public String findValueForMap(Jedis jedis, String key, String field) {
		if (jedis != null) {
			List<String> strList = jedis.hmget(key, field);
			return strList.get(0);
		}
		return null;
	}
	/**
	 * redis操作Map
	 */
	public List<String> findValueListForMap(Jedis jedis, String key, final String... fieldList) {
		if (jedis != null) {
			List<String> strList = jedis.hmget(key, fieldList);
			return strList;
		}
		return null;
	}
	/**
	 * redis操作Map
	 */
	public Map<String, String> findForMap(Jedis jedis, String key) {
		if (jedis != null) {
			Map<String, String> map = new HashMap<String, String>();
			// 返回map对象中的所有key
			Iterator<String> strIterator = jedis.hkeys(key).iterator();
			while (strIterator.hasNext()) {
				String mapKey = strIterator.next();
				String mapValue = jedis.hmget(key, mapKey).get(0);
				map.put(mapKey, mapValue);
			}
			return map;
		}
		return null;
	}
	/**
	 * redis操作sortedSet
	 */
	public void saveForSortedSet(Jedis jedis, String key, Map<String, Double> map) {
		if (jedis != null) {
			// 加数据
			jedis.zadd(key, map);
		}
	}
	/**
	 * redis操作sortedSet
	 */
	public void delForSortedSet(Jedis jedis, String key, final String... fieldList) {
		if (jedis != null) {
			// 删除某个键值
			jedis.zrem(key, fieldList);
		}
	}
	/**
	 * redis操作sortedSet;
	 * 
	 * 分页;
	 * 
	 * @Title: sortedSet2valueByMin
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return 返回类型 Set<String>
	 */
	public Set<String> findValueListByMinForSortedSet(Jedis jedis, String key, String min, String max, int offset,
			int count) {
		if (jedis != null) {
			Set<String> result = jedis.zrangeByScore(key, min, max, offset, count);
			return result;
		}
		return null;
	}
	/**
	 * redis操作sortedSet;
	 * 
	 * 分页;
	 * 
	 * @Title: sortedSet2valueByMax
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return 返回类型 Set<String>
	 */
	public Set<String> findValueListByMaxForSortedSet(Jedis jedis, String key, String min, String max, int offset,
			int count) {
		if (jedis != null) {
			Set<String> result = jedis.zrevrangeByScore(key, max, min, offset, count);
			return result;
		}
		return null;
	}
	/**
	 * redis操作sortedSet;
	 * 
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param key
	 * @return 返回类型 Set<String>
	 */
	public Set<String> findValueListForSortedSet(Jedis jedis, String key) {
		if (jedis != null) {
			Set<String> result = jedis.zrangeByScore(key, "-inf", "+inf");
			return result;
		}
		return null;
	}
	/**
	 * 分页; 排序; 从小到大;
	 * 
	 * @Title: findPageByMin
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param key
	 * @param min
	 * @param max
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *             返回类型 Set<String>
	 */
	public Set<String> findPage2ValueListByMinForSortedSet(Jedis jedis, String key, String min, String max,
			int pageIndex, int pageSize) throws Exception {
		if (jedis != null) {
			PageCoreBean<Map<String, Object>> pageBean = new PageMysqlBean<Map<String, Object>>(pageIndex, pageSize);
			return findValueListByMinForSortedSet(jedis, key, min, max, pageBean.getStart(), pageBean.getPageSize());
		}
		return null;
	}
	/**
	 * 分页; 排序; 从大到小;
	 * 
	 * @Title: findPageByMax
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param key
	 * @param min
	 * @param max
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *             返回类型 Set<String>
	 */
	public Set<String> findPage2ValueListByMaxForSortedSet(Jedis jedis, String key, String min, String max,
			int pageIndex, int pageSize) throws Exception {
		if (jedis != null) {
			PageCoreBean<Map<String, Object>> pageBean = new PageMysqlBean<Map<String, Object>>(pageIndex, pageSize);
			return findValueListByMaxForSortedSet(jedis, key, min, max, pageBean.getStart(), pageBean.getPageSize());
		}
		return null;
	}
	/**
	 * 分页; 排序; 从小到大;
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param key
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *             返回类型 Set<String>
	 */
	public Set<String> findPage2ValueListByMinForSortedSet(Jedis jedis, String key, int pageIndex, int pageSize)
			throws Exception {
		if (jedis != null) {
			PageCoreBean<Map<String, Object>> pageBean = new PageMysqlBean<Map<String, Object>>(pageIndex, pageSize);
			return findValueListByMinForSortedSet(jedis, key, "-inf", "+inf", pageBean.getStart(),
					pageBean.getPageSize());
		}
		return null;
	}
	/**
	 * 分页; 排序; 从大到小;
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param key
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *             返回类型 Set<String>
	 */
	public Set<String> findPage2ValueListByMaxForSortedSet(Jedis jedis, String key, int pageIndex, int pageSize)
			throws Exception {
		if (jedis != null) {
			PageCoreBean<Map<String, Object>> pageBean = new PageMysqlBean<Map<String, Object>>(pageIndex, pageSize);
			return findValueListByMaxForSortedSet(jedis, key, "-inf", "+inf", pageBean.getStart(),
					pageBean.getPageSize());
		}
		return null;
	}
	/**
	 * 
	 * 比如按时间排序;从大到小;取下一页的数据;
	 * 
	 * 分页; 排序; 从大到小;
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param key
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *             返回类型 Set<String>
	 */
	public Set<String> findPage2ValueListByMaxForSortedSet(Jedis jedis, String key, String max, int pageIndex,
			int pageSize) throws Exception {
		if (jedis != null) {
			PageCoreBean<Map<String, Object>> pageBean = new PageMysqlBean<Map<String, Object>>(pageIndex, pageSize);
			// 加1,去掉第一条重复的数据
			return findValueListByMaxForSortedSet(jedis, key, "-inf", max, pageBean.getStart() + 1,
					pageBean.getPageSize());
		}
		return null;
	}
	/**
	 * 保存到redis(模拟数据库的表操作)
	 * 
	 * @Title: saveJson
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param tableName
	 * @param primaryValue
	 * @param createTimeLong
	 * @param dataMap
	 * @throws Exception
	 *             返回类型 void
	 */
	public void saveJson(Jedis jedis, String tableName, String primaryValue, Long createTimeLong,
			Map<String, Object> dataMap) throws Exception {
		if (jedis != null) {
			Map<String, Double> rowMap = new HashMap<String, Double>();
			rowMap.put(primaryValue, createTimeLong.doubleValue());
			this.saveForSortedSet(jedis, findKeyTable(tableName), rowMap);
			String valueJson = JsonThreadLocal.findThreadLocal().get().map2json(dataMap);
			jedis.set(findKey(tableName, primaryValue), valueJson);
		}
	}
	/**
	 * 保存到redis(模拟数据库的表操作)
	 * 
	 * @Title: save
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param tableName
	 * @param primaryValue
	 * @param createTimeLong
	 * @param dataMap
	 * @throws Exception
	 *             返回类型 void
	 */
	public void save(Jedis jedis, String tableName, String primaryValue, Long createTimeLong,
			Map<String, String> dataMap) throws Exception {
		if (jedis != null) {
			Map<String, Double> rowMap = new HashMap<String, Double>();
			rowMap.put(primaryValue, createTimeLong.doubleValue());
			this.saveForSortedSet(jedis, findKeyTable(tableName), rowMap);
			this.saveForMap(jedis, findKey(tableName, primaryValue), dataMap);
		}
	}
	/**
	 * redis删除数据(模拟数据库的表操作)
	 * 
	 * @Title: del
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param tableName
	 * @param primaryValue
	 * @throws Exception
	 *             返回类型 void
	 */
	public void del(Jedis jedis, String tableName, String primaryValue) throws Exception {
		if (jedis != null) {
			this.delForSortedSet(jedis, findKeyTable(tableName), primaryValue);
			jedis.del(findKey(tableName, primaryValue));
		}
	}
	/**
	 * 找出某行记录(模拟数据库的表操作)
	 * 
	 * @Title: findMap
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param tableName
	 * @param primaryValue
	 * @return
	 * @throws Exception
	 *             返回类型 Map<String,String>
	 */
	public Map<String, String> findMap(Jedis jedis, String tableName, String primaryValue) throws Exception {
		if (jedis != null) {
			return this.findForMap(jedis, findKey(tableName, primaryValue));
		}
		return null;
	}
	/**
	 * 找出所有的记录(模拟数据库的表操作)
	 * 
	 * @Title: findMapList
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param tableName
	 * @return
	 * @throws Exception
	 *             返回类型 List<Map<String,String>>
	 */
	public List<Map<String, Object>> findMapListByJson(Jedis jedis, String tableName) throws Exception {
		if (jedis != null) {
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			// 找出所有的主键
			Set<String> primaryValueList = this.findValueListForSortedSet(jedis, findKeyTable(tableName));
			for (String primaryValue : primaryValueList) {
				String valueJson = jedis.get(findKey(tableName, primaryValue));
				Map<String, Object> map = JsonThreadLocal.findThreadLocal().get().json2map(valueJson);
				if (map.size() != 0) {
					listMap.add(map);
				}
			}
			return listMap;
		}
		return null;
	}
	/**
	 * 找出所有的记录(模拟数据库的表操作)
	 * 
	 * @Title: findMapList
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param tableName
	 * @return
	 * @throws Exception
	 *             返回类型 List<Map<String,String>>
	 */
	public List<Map<String, String>> findMapList(Jedis jedis, String tableName) throws Exception {
		if (jedis != null) {
			List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
			// 找出所有的主键
			Set<String> primaryValueList = this.findValueListForSortedSet(jedis, findKeyTable(tableName));
			for (String primaryValue : primaryValueList) {
				Map<String, String> map = this.findForMap(jedis, findKey(tableName, primaryValue));
				if (map.size() != 0) {
					listMap.add(map);
				}
			}
			return listMap;
		}
		return null;
	}
	/**
	 * 数据库数据保存到redis
	 * 
	 * @Title: save
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param object
	 * @param primaryValue
	 * @throws Exception
	 *             返回类型 void
	 */
	public void save(Jedis jedis, Object object, String primaryValue) throws Exception {
		if (jedis != null) {
			Class classObj = object.getClass();
			Map<String, String> dataMap = new HashMap<String, String>();
			Long createTimeLong = null;
			// 得到表名
			AnnotationTable table = (AnnotationTable) classObj.getAnnotation(AnnotationTable.class);
			String tableName = table.name();
			ReflectUtil reflectUtil = ReflectThreadLocal.findThreadLocal().get();
			Field[] fields = classObj.getDeclaredFields();
			for (Field field : fields) {
				/*
				 * 实体类的域是不是主键
				 */
				boolean flagPK = field.isAnnotationPresent(Id.class);
				/*
				 * 实体类的域是不是对应数据库的列
				 */
				boolean columnFlag = field.isAnnotationPresent(Column.class);
				if (columnFlag) {
					String columnName = field.getAnnotation(Column.class).name();
					Method getter = reflectUtil.findGetterMethodByField(classObj, field.getName());
					// 执行方法
					Object value = getter.invoke(object);
					if (StringUtil.isNotBlank(value)) {
						dataMap.put(columnName, value.toString());
					}
					// 索引
					if (columnName.toUpperCase().equals("IDX_")) {
					}
					// 时间
					if (columnName.toUpperCase().equals("CREATE_TIME_LONG_")) {
						if (StringUtil.isNotBlank(value)) {
							createTimeLong = Long.valueOf(value.toString());
						}
					}
				}
			}
			if (createTimeLong != null) {
				this.save(jedis, tableName, primaryValue, createTimeLong, dataMap);
			}
		}
	}
	/**
	 * 数据库数据更新到redis
	 * 
	 * @Title: update
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param object
	 * @param primaryValue
	 * @throws Exception
	 *             返回类型 void
	 */
	public void update(Jedis jedis, Object object, String primaryValue) throws Exception {
		if (jedis != null) {
			Class classObj = object.getClass();
			Map<String, String> dataMap = new HashMap<String, String>();
			Long createTimeLong = null;
			// 得到表名
			AnnotationTable table = (AnnotationTable) classObj.getAnnotation(AnnotationTable.class);
			String tableName = table.name();
			ReflectUtil reflectUtil = ReflectThreadLocal.findThreadLocal().get();
			Field[] fields = classObj.getDeclaredFields();
			for (Field field : fields) {
				/*
				 * 实体类的域是不是主键
				 */
				boolean flagPK = field.isAnnotationPresent(Id.class);
				/*
				 * 实体类的域是不是对应数据库的列
				 */
				boolean columnFlag = field.isAnnotationPresent(Column.class);
				if (columnFlag) {
					String columnName = field.getAnnotation(Column.class).name();
					Method getter = reflectUtil.findGetterMethodByField(classObj, field.getName());
					// 执行方法
					Object value = getter.invoke(object);
					if (StringUtil.isNotBlank(value)) {
						dataMap.put(columnName, value.toString());
					}
					// 索引
					if (columnName.toUpperCase().equals("IDX_")) {
					}
					// 时间
					if (columnName.toUpperCase().equals("UPDATE_TIME_LONG_")) {
						if (StringUtil.isNotBlank(value)) {
							createTimeLong = Long.valueOf(value.toString());
						}
					}
				}
			}
			if (createTimeLong != null) {
				this.save(jedis, tableName, primaryValue, createTimeLong, dataMap);
			}
		}
	}
	/**
	 * 模糊查询(保存)
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param tableName
	 * @param columnName
	 * @param name
	 * @param map
	 *            返回类型 void
	 */
	public void saveForQueryFuzzy(Jedis jedis, String tableName, String columnName, String queryName,
			Map<String, Double> map) {
		if (jedis != null) {
			String key = this.findKeyQueryFuzzy(tableName, columnName, queryName);
			this.saveForSortedSet(jedis, key, map);
		}
	}
	/**
	 * 模糊查询
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param tableName
	 * @param columnName
	 * @param queryName
	 * @return 返回类型 Set<String>
	 */
	public Set<String> findForQueryFuzzy(Jedis jedis, String tableName, String columnName, String queryName) {
		if (jedis != null) {
			String key = this.findKeyQueryFuzzy(tableName, columnName, queryName);
			return this.findValueListForSortedSet(jedis, key);
		}
		return null;
	}
	/**
	 * 精确查询(保存)
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param tableName
	 * @param columnName
	 * @param name
	 * @param map
	 *            返回类型 void
	 */
	public void saveForQueryExact(Jedis jedis, String tableName, String columnName, Map<String, String> map) {
		if (jedis != null) {
			String key = this.findKeyQueryExact(tableName, columnName);
			saveForMap(jedis, key, map);
		}
	}
	/**
	 * 精确查询(删除)
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param tableName
	 * @param columnName
	 * @param name
	 * @param map
	 *            返回类型 void
	 */
	public void delForQueryExact(Jedis jedis, String tableName, String columnName, String queryName) {
		if (jedis != null) {
			String key = this.findKeyQueryExact(tableName, columnName);
			// value sent to redis cannot be null
			delForMap(jedis, key, queryName);
		}
	}
	/**
	 * 精确查询
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param tableName
	 * @param columnName
	 * @param name
	 * @return 返回类型 Set<String>
	 */
	public String findForQueryExact(Jedis jedis, String tableName, String columnName, String queryName) {
		if (jedis != null) {
			String key = this.findKeyQueryExact(tableName, columnName);
			return findValueForMap(jedis, key, queryName);
		}
		return null;
	}
	/**
	 * 找出某行记录(模拟数据库的表操作)
	 * 
	 * @Title: findMap
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param tableName
	 * @param primaryValue
	 * @return
	 * @throws Exception
	 *             返回类型 Map<String,String>
	 */
	public Object findObject(Jedis jedis, Class classObj, String tableName, String primaryValue) throws Exception {
		if (jedis != null) {
			ReflectUtil reflectUtil = ReflectThreadLocal.findThreadLocal().get();
			Map<String, String> map = this.findForMap(jedis, findKey(tableName, primaryValue));
			Object objectReturn = reflectUtil.doMap2Object(classObj, map);
			return objectReturn;
		}
		return null;
	}
	/**
	 * 找出所有的记录(模拟数据库的表操作)
	 * 
	 * @Title: findObjectList
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param classObj
	 * @return
	 * @throws Exception
	 *             返回类型 List
	 */
	public List findObjectList(Jedis jedis, Class classObj) throws Exception {
		if (jedis != null) {
			// 得到表名
			AnnotationTable table = (AnnotationTable) classObj.getAnnotation(AnnotationTable.class);
			String tableName = table.name();
			String key = this.findKeyTable(tableName);
			List list = new ArrayList<>();
			// 找出所有的主键
			Set<String> primaryValueList = this.findValueListForSortedSet(jedis, key);
			for (String primaryValue : primaryValueList) {
				Object object = this.findObject(jedis, classObj, tableName, primaryValue);
				if (object != null) {
					list.add(object);
				}
			}
			return list;
		}
		return null;
	}
	/**
	 * 找出分页的记录(模拟数据库的表操作)
	 * 
	 * @Title: findPageObject
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param classObj
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *             返回类型 List
	 */
	public List findPageObject(Jedis jedis, Class classObj, int pageIndex, int pageSize) throws Exception {
		if (jedis != null) {
			// 得到表名
			AnnotationTable table = (AnnotationTable) classObj.getAnnotation(AnnotationTable.class);
			String tableName = table.name();
			String key = this.findKeyTable(tableName);
			List list = new ArrayList<>();
			// 找出所有的主键
			Set<String> primaryValueList = this.findPage2ValueListByMinForSortedSet(jedis, key, pageIndex, pageSize);
			for (String primaryValue : primaryValueList) {
				Object object = this.findObject(jedis, classObj, tableName, primaryValue);
				if (object != null) {
					list.add(object);
				}
			}
			return list;
		}
		return null;
	}
	/**
	 * 找出分页的记录(模拟数据库的表操作)
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param classObj
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *             返回类型 List
	 */
	public List<Map<String, String>> findPageMap(Jedis jedis, Class classObj, int pageIndex, int pageSize)
			throws Exception {
		if (jedis != null) {
			// 得到表名
			AnnotationTable table = (AnnotationTable) classObj.getAnnotation(AnnotationTable.class);
			String tableName = table.name();
			String key = this.findKeyTable(tableName);
			List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
			// 找出所有的主键
			Set<String> primaryValueList = this.findPage2ValueListByMinForSortedSet(jedis, key, pageIndex, pageSize);
			for (String primaryValue : primaryValueList) {
				Map<String, String> map = this.findMap(jedis, tableName, primaryValue);
				if (map != null) {
					listMap.add(map);
				}
			}
			return listMap;
		}
		return null;
	}
	/**
	 * 是否存在VerifyCode
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param sessionId
	 * @param valueInput
	 * @return
	 * @throws Exception
	 *             返回类型 boolean
	 */
	public boolean isAppVerifyCode(Jedis jedis, String sessionId, String valueInput) throws Exception {
		String value = this.findAppVerifyCode(jedis, sessionId);
		if (value != null) {
			if (value.equals(valueInput)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * VerifyCode
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param sessionId
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String findAppVerifyCode(Jedis jedis, String sessionId) throws Exception {
		String key = this.findKeyAppVerifyCode(sessionId);
		String value = null;
		jedis.select(appVerifyCodeIndex);
		Object valueObject = jedis.get(key);
		if (valueObject != null) {
			value = valueObject.toString();
		}
		return value;
	}
	/**
	 * 保存VerifyCode
	 * 
	 * @deprecated
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param sessionId
	 * @param value
	 * @throws Exception
	 *             返回类型 void
	 */
	public void saveAppVerifyCode(Jedis jedis, String sessionId, String value) throws Exception {
		String key = this.findKeyAppVerifyCode(sessionId);
		jedis.select(appVerifyCodeIndex);
		jedis.set(key, value);
	}
	/**
	 * 删除VerifyCode
	 * 
	 * @Title: delVerifyCode
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param sessionId
	 * @throws Exception
	 *             返回类型 void
	 */
	public void delAppVerifyCode(Jedis jedis, String sessionId) throws Exception {
		String key = this.findKeyAppVerifyCode(sessionId);
		jedis.select(appVerifyCodeIndex);
		jedis.del(key);
	}
	/**
	 * 新的VerifyCode
	 * 
	 * @deprecated
	 * @Title: findNewVerifyCode
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String findNewAppSession(Jedis jedis) throws Exception {
		String sessionId = Uuid.findInstance().toString();
		String value = "";
		String key = this.findKeyAppVerifyCode(sessionId);
		jedis.select(appVerifyCodeIndex);
		jedis.set(key, value);
		return sessionId;
	}
	/**
	 * 构造VerifyCode
	 * 
	 * @deprecated
	 * @Description:
	 *
	 * 				参数说明
	 * @param sessionId
	 * @return 返回类型 String
	 */
	public String findKeyAppVerifyCode(String sessionId) {
		return this.appVerifyCodePrefix + sessionId;
	}
	/**
	 * 是否存在VerifyCode
	 * 
	 * @deprecated
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param sessionId
	 * @param valueInput
	 * @return
	 * @throws Exception
	 *             返回类型 boolean
	 */
	public boolean isSysAdminVerifyCode(Jedis jedis, String sessionId, String valueInput) throws Exception {
		String value = this.findSysAdminVerifyCode(jedis, sessionId);
		if (value != null) {
			if (value.equals(valueInput)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * VerifyCode
	 * 
	 * @deprecated
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param sessionId
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String findSysAdminVerifyCode(Jedis jedis, String sessionId) throws Exception {
		String key = this.findKeySysAdminVerifyCode(sessionId);
		String value = null;
		jedis.select(sysAdminVerifyCodeIndex);
		Object valueObject = jedis.get(key);
		if (valueObject != null) {
			value = valueObject.toString();
		}
		return value;
	}
	/**
	 * 保存VerifyCode
	 * 
	 * @deprecated
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param sessionId
	 * @param value
	 * @throws Exception
	 *             返回类型 void
	 */
	public void saveSysAdminVerifyCode(Jedis jedis, String sessionId, String value) throws Exception {
		String key = this.findKeySysAdminVerifyCode(sessionId);
		jedis.select(sysAdminVerifyCodeIndex);
		jedis.set(key, value);
	}
	/**
	 * 删除VerifyCode
	 * 
	 * @Title: delVerifyCode
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param sessionId
	 * @throws Exception
	 *             返回类型 void
	 */
	public void delSysAdminVerifyCode(Jedis jedis, String sessionId) throws Exception {
		String key = this.findKeySysAdminVerifyCode(sessionId);
		jedis.select(sysAdminVerifyCodeIndex);
		jedis.del(key);
	}
	/**
	 * 新的VerifyCode
	 * 
	 * @Title: findNewVerifyCode
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String findNewSysAdminSession(Jedis jedis) throws Exception {
		String sessionId = Uuid.findInstance().toString();
		String value = "";
		String key = this.findKeySysAdminVerifyCode(sessionId);
		jedis.select(sysAdminVerifyCodeIndex);
		jedis.set(key, value);
		return sessionId;
	}
	/**
	 * 构造VerifyCode
	 * 
	 * @deprecated
	 * @Description:
	 *
	 * 				参数说明
	 * @param sessionId
	 * @return 返回类型 String
	 */
	public String findKeySysAdminVerifyCode(String sessionId) {
		return this.sysAdminVerifyCodePrefix + sessionId;
	}
	/**
	 * @deprecated
	 * @Title: saveCurrentUser
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param map
	 *            返回类型 void
	 */
	public void saveCurrentUser(Jedis jedis, Map<String, String> map) {
		saveForMap(jedis, SysConfig.CurrentUser, map);
	}
	/**
	 * @deprecated
	 * @Title: saveCurrentUser
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @param commLocalProjectValue
	 * @param commLocalTenantValue
	 *            返回类型 void
	 */
	public void saveCurrentUser(Jedis jedis, String commLocalProjectValue, String commLocalTenantValue) {
		jedis.select(this.dbIndex);
		Map<String, String> map = new HashMap<String, String>();
		map.put(SysConfig.commLocalType, commLocalProjectValue);
		map.put(SysConfig.commLocalTenant, commLocalTenantValue);
		this.saveCurrentUser(jedis, map);
	}
	/**
	 * @deprecated
	 * @Title: findCurrentUser
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @return 返回类型 Map<String,String>
	 */
	public Map<String, String> findCurrentUser(Jedis jedis) {
		jedis.select(this.dbIndex);
		return findForMap(jedis, SysConfig.CurrentUser);
	}
	/**
	 * @deprecated
	 * @Title: findCurrentUserType
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @return 返回类型 String
	 */
	public String findCurrentUserType(Jedis jedis) {
		Map<String, String> map = findCurrentUser(jedis);
		if (map != null && map.size() > 0) {
			return map.get(SysConfig.commLocalType);
		}
		return null;
	}
	/**
	 * @deprecated
	 * @Title: findCurrentUserTenant
	 * @Description:
	 *
	 * 				参数说明
	 * @param jedis
	 * @return 返回类型 String
	 */
	public String findCurrentUserTenant(Jedis jedis) {
		Map<String, String> map = findCurrentUser(jedis);
		if (map != null && map.size() > 0) {
			return map.get(SysConfig.commLocalTenant);
		}
		return null;
	}
}
