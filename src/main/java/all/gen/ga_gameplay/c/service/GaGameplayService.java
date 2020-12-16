package all.gen.ga_gameplay.c.service;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import all.gen.ga_gameplay.c.entity.GaGameplayT;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.bean.BeanUtil;
import c.a.util.redis.RedisUtil;
import c.x.platform.root.common.service.BaseService;
import redis.clients.jedis.Jedis;
/**
 * @Description: 游戏玩法表 系统维护
 * @date 2018年5月16日 下午4:05:18
 * @Copyright © 2018-2018 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class GaGameplayService extends BaseService {
	protected Logger log = LogManager.getLogger(this.getClass());
	public List<Map<String, Object>> findAllByRedisJson() throws Exception {
		List<Map<String, Object>> listMap = null;
		RedisUtil redisUtil = null;
		Jedis jedis = null;
		try {
			redisUtil = RedisUtil.findInstance();
			jedis = redisUtil.findJedis();
			if (jedis != null) {
				jedis.select(RedisUtil.dbIndex);
				listMap = redisUtil.findMapListByJson(jedis, "ga_gameplay");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			redisUtil.close(jedis);
		}
		return listMap;
	}
	public List<Map<String, String>> findAllByRedis() throws Exception {
		List<Map<String, String>> listMap = null;
		RedisUtil redisUtil = null;
		Jedis jedis = null;
		try {
			redisUtil = RedisUtil.findInstance();
			jedis = redisUtil.findJedis();
			if (jedis != null) {
				jedis.select(RedisUtil.dbIndex);
				listMap = redisUtil.findMapList(jedis, "ga_gameplay");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			redisUtil.close(jedis);
		}
		return listMap;
	}
	public List<Map<String, Object>> findAllByRedis_v3(RedisUtil redisUtil, Jedis jedis) throws Exception {
		List<Map<String, Object>> listMap = null;
		if (jedis != null) {
			jedis.select(RedisUtil.dbIndex);
			listMap = redisUtil.findMapListByJson(jedis, "ga_gameplay");
		}
		return listMap;
	}
	public List<Map<String, String>> findAllByRedis_v2(RedisUtil redisUtil, Jedis jedis) throws Exception {
		List<Map<String, String>> listMap = null;
		if (jedis != null) {
			jedis.select(RedisUtil.dbIndex);
			listMap = redisUtil.findMapList(jedis, "ga_gameplay");
		}
		return listMap;
	}
	public List<Map<String, String>> findAllByRedis_v1() throws Exception {
		List<Map<String, String>> listMap = null;
		String redisLocalStart = SysConfig.findInstance().findMap().get("redis.local.start").toString();
		if ("true".equals(redisLocalStart)) {
			RedisUtil redisUtil = null;
			Jedis jedis = null;
			try {
				redisUtil = RedisUtil.findInstance();
				jedis = redisUtil.findJedis();
				if (jedis != null) {
					jedis.select(RedisUtil.dbIndex);
					listMap = redisUtil.findMapList(jedis, "ga_gameplay");
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				redisUtil.close(jedis);
			}
			return listMap;
		}
		return null;
	}
	/**
	 * 保存在redis
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param gaGameplayT
	 * @return
	 * @throws Exception
	 *             返回类型 Map<String,String>
	 */
	public Map<String, String> saveAppUserByRedisJson(GaGameplayT gaGameplayT) throws Exception {
		String redisLocalStart = SysConfig.findInstance().findMap().get("redis.local.start").toString();
		if ("true".equals(redisLocalStart)) {
			Map<String, String> appUserMap = null;
			RedisUtil redisUtil = null;
			Jedis jedis = null;
			try {
				// 去redis查找token
				redisUtil = RedisUtil.findInstance();
				jedis = redisUtil.findJedis();
				if (jedis != null) {
					jedis.select(RedisUtil.dbIndex);
					BeanUtil beanUtil = BeanThreadLocal.findThreadLocal().get();
					Map<String, Object> dataMap = beanUtil.doBean2Map(gaGameplayT);
					redisUtil.saveJson(jedis, "ga_gameplay", gaGameplayT.getGaGameplayId(),
							gaGameplayT.getCreateTime().getTime(), dataMap);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				redisUtil.close(jedis);
			}
			return appUserMap;
		}
		return null;
	}
	/**
	 * 保存在redis
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param gaGameplayT
	 * @return
	 * @throws Exception
	 *             返回类型 Map<String,String>
	 */
	public Map<String, String> saveAppUserByRedis(GaGameplayT gaGameplayT) throws Exception {
		String redisLocalStart = SysConfig.findInstance().findMap().get("redis.local.start").toString();
		if ("true".equals(redisLocalStart)) {
			Map<String, String> appUserMap = null;
			RedisUtil redisUtil = null;
			Jedis jedis = null;
			try {
				// 去redis查找token
				redisUtil = RedisUtil.findInstance();
				jedis = redisUtil.findJedis();
				if (jedis != null) {
					jedis.select(RedisUtil.dbIndex);
					BeanUtil beanUtil = BeanThreadLocal.findThreadLocal().get();
					Map<String, String> dataMap = beanUtil.doBean2MapStr(gaGameplayT);
					redisUtil.save(jedis, "ga_gameplay", gaGameplayT.getGaGameplayId(),
							gaGameplayT.getCreateTime().getTime(), dataMap);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				redisUtil.close(jedis);
			}
			return appUserMap;
		}
		return null;
	}
	/**
	 * 
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<GaGameplayT> findAll() throws Exception {
		String sql = "SELECT * FROM ga_gameplay order by CREATE_TIME_ desc";
		List<GaGameplayT> list = this.dao.findObjectList(GaGameplayT.class, sql);
		return list;
	}
}
