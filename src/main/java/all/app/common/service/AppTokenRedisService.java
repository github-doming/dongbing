package all.app.common.service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import all.gen.app_token.t.entity.AppTokenT;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.UserStateEnum;
import c.a.util.core.string.StringUtil;
import c.a.util.core.uuid.Uuid;
import c.a.util.redis.RedisUtil;
/**
 * 
 * 
 * AppToken
 * 
 * @Description:
 * @ClassName:
 * @date 2011年9月19日 下午5:37:58
 * @author cxy
 * @Copyright (c)
 *
 */
import redis.clients.jedis.Jedis;
public class AppTokenRedisService extends AppTokenService {
	/**
	 * 同时 新增数据库和redis
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param entity
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public AppTokenT saveAppToken(String commLocalTenantValue, AppTokenT entity) throws Exception {
		Date date = new Date();
		// 新增Token
		String token = Uuid.create().toString();
		entity.setTenantCode(commLocalTenantValue);
		if (StringUtil.isBlank(entity.getState())) {
			entity.setState(UserStateEnum.OPEN.getCode());
		}
		entity.setValue(token);
		entity.setCreateTime(date);
		entity.setCreateTimeLong(date.getTime());
		entity.setUpdateTime(date);
		entity.setUpdateTimeLong(date.getTime());
		save(entity);
		// Token保存在redis
		saveTokenByRedis(token, entity.getAppUserId());
		return entity;
	}
	/**
	 * 同时删除数据库和redis
	 * 
	 * @Title: delAppUser
	 * @Description:
	 *
	 * 				参数说明
	 * @param id
	 * @throws Exception
	 *             返回类型 void
	 */
	public void delAppToken(String id) throws Exception {
		delPhysical(id);
		this.delTokenByRedis(id);
	}
	/**
	 * 同时 更新数据库和redis
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param entity
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public AppTokenT updateAppToken(String commLocalTenantValue, AppTokenT entity) throws Exception {
		Date date = new Date();
		// 数据库原来的token
		String tokenOld = entity.getValue();
		// 更新Token
		String token = Uuid.create().toString();
			entity.setTenantCode(commLocalTenantValue);
		entity.setValue(token);
		entity.setUpdateTime(date);
		entity.setUpdateTimeLong(date.getTime());
		update(entity);
		// 从redis删除Token
		if (StringUtil.isNotBlank(tokenOld)) {
			// value sent to redis cannot be null
			delTokenByRedis(tokenOld);
		}
		// Token保存在redis
		saveTokenByRedis(token, entity.getAppUserId());
		return entity;
	}
	/**
	 * 查询
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public String findAppUserIdByToken(String token) throws Exception {
		String appUserId = null;
		AppTokenT appTokenT = null;
		if (StringUtil.isNotBlank(token)) {
			// 去redis查找
			appUserId = findAppUserIdByRedisToken(token);
			if (StringUtil.isBlank(appUserId)) {
				// 去数据库查找appTokenT
				appTokenT = this.findAppTokenByToken(token);
				if (appTokenT != null) {
					// appTokenT保存在redis
					this.saveTokenByRedis(appTokenT.getValue(), appTokenT.getAppUserId());
					appUserId = appTokenT.getAppUserId();
				}
			}
		}
		return appUserId;
	}
	/**
	 * Token保存在redis
	 * 
	 * @Title: saveToken2Redis
	 * @Description:
	 *
	 * 				参数说明
	 * @param tokenValue
	 * @param appUserIdInput
	 * @throws Exception
	 *             返回类型 void
	 */
	public void saveTokenByRedis(String tokenValue, String appUserIdInput) throws Exception {
		String redisLocalStart = SysConfig.findInstance().findMap().get("redis.local.start").toString();
		if ("true".equals(redisLocalStart)) {
			RedisUtil redisUtil = null;
			Jedis jedis = null;
			try {
				// Token保存在redis
				redisUtil = RedisUtil.findInstance();
				jedis = redisUtil.findJedis();
				if (jedis != null) {
					jedis.select(RedisUtil.dbIndex);
					Map<String, String> tokenRedisMap = new HashMap<String, String>();
					tokenRedisMap.put(tokenValue, appUserIdInput);
					redisUtil.saveForQueryExact(jedis, "app_token", "token", tokenRedisMap);
				}
				// Token保存在redis(过期)
				redisUtil.doExpireToken(jedis, tokenValue);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				redisUtil.close(jedis);
			}
		}
	}
	/**
	 * 从redis删除Token
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param tokenValue
	 * @param appUserIdInput
	 * @throws Exception
	 *             返回类型 void
	 */
	public void delTokenByRedis(String tokenValue) throws Exception {
		if (StringUtil.isNotBlank(tokenValue)) {
			String redisLocalStart = SysConfig.findInstance().findMap().get("redis.local.start").toString();
			if ("true".equals(redisLocalStart)) {
				RedisUtil redisUtil = null;
				Jedis jedis = null;
				try {
					redisUtil = RedisUtil.findInstance();
					jedis = redisUtil.findJedis();
					if (jedis != null) {
						jedis.select(RedisUtil.dbIndex);
						redisUtil.delForQueryExact(jedis, "app_token", "token", tokenValue);
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				} finally {
					redisUtil.close(jedis);
				}
			}
		}
	}
	/**
	 * 去redis查找token(返回appUesId)
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param tokenValue
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String findAppUserIdByRedisToken(String tokenValue) throws Exception {
		String redisLocalStart = SysConfig.findInstance().findMap().get("redis.local.start").toString();
		if ("true".equals(redisLocalStart)) {
			String appUserIdRedis = null;
			RedisUtil redisUtil = null;
			Jedis jedis = null;
			try {
				// 去redis查找token
				redisUtil = RedisUtil.findInstance();
				jedis = redisUtil.findJedis();
				if (jedis != null) {
					jedis.select(RedisUtil.dbIndex);
					appUserIdRedis = redisUtil.findForQueryExact(jedis, "app_token", "token", tokenValue);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				redisUtil.close(jedis);
			}
			return appUserIdRedis;
		}
		return null;
	}
	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/
}
