package all.app.common.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import all.gen.app_user.t.entity.AppUserT;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.bean.BeanUtil;
import c.a.util.core.enums.bean.UserStateEnum;
import c.a.util.core.string.StringUtil;
import c.a.util.redis.RedisUtil;
/**
 * 
 * 
 *   AppUser
 * 
 * @Description:
 * @ClassName:
 * @date 2011年9月19日 下午5:37:58
 * @author cxy
 * @Copyright (c)
 *
 */
import redis.clients.jedis.Jedis;
public class AppUserRedisService extends AppUserService {
	/**
	 * 数据初始化到redis
	 * 
	 * @Title: init
	 * @Description:
	 *
	 * 				参数说明
	 * @throws Exception
	 *             返回类型 void
	 */
	public void init() throws Exception {
		List<AppUserT> listObject = findAllObjectByRedis();
		for (AppUserT entity : listObject) {
			delAppUserByRedis(entity.getAppUserId());
		}
		listObject = findObjectAll();
		for (AppUserT entity : listObject) {
			saveAppUserByRedis(entity);
		}
	}
	/**
	 * 打印redis数据
	 * 
	 * @Title: print
	 * @Description:
	 *
	 * 				参数说明
	 * @throws Exception
	 *             返回类型 void
	 */
	public void print() throws Exception {
		List<AppUserT> listObject = findAllObjectByRedis();
		System.out.println("size=" + listObject.size());
		for (AppUserT entity : listObject) {
			System.out.println("id=" + entity.getAppUserId());
			System.out.println("name=" + entity.getAppUserName());
		}
	}
	/**
	 * 同时新增数据库和redis
	 * 
	 * @Title: saveAppUser
	 * @Description:
	 *
	 * 				参数说明
	 * @param entity
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String saveAppUser(String commLocalTenantValue, AppUserT entity) throws Exception {
		Date date = new Date();
		entity.setTenantCode(commLocalTenantValue);
		entity.setState(UserStateEnum.OPEN.getCode());
		entity.setCreateTime(date);
		entity.setCreateTimeLong(date.getTime());
		entity.setUpdateTime(date);
		entity.setUpdateTimeLong(date.getTime());
		String appUserId = save(entity);
		entity.setAppUserId(appUserId);
		saveAppUserByRedis(entity);
		return appUserId;
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
	public void delAppUser(String id) throws Exception {
		delPhysical(id);
		delAppUserByRedis(id);
	}
	/**
	 * 同时更新数据库和redis
	 * 
	 * @Title: updateAppUser
	 * @Description:
	 *
	 * 				参数说明
	 * @param entity
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String updateAppUser(String commLocalTenantValue, AppUserT entity) throws Exception {
		String appUserId = entity.getAppUserId();
		if (StringUtil.isNotBlank(appUserId)) {
			Date date = new Date();
			entity = find(appUserId);
			entity.setTenantCode(commLocalTenantValue);
			entity.setUpdateTime(date);
			entity.setUpdateTimeLong(date.getTime());
			update(entity);
			this.updateAppUserByRedis(entity);
		}
		return appUserId;
	}
	/**
	 * 查询
	 * 
	 * @Title: findAppUser
	 * @Description:
	 *
	 * 				参数说明
	 * @param appUserId
	 * @return
	 * @throws Exception
	 *             返回类型 AppUserT
	 */
	public AppUserT findAppUser(String appUserId) throws Exception {
		AppUserT appUserT = null;
		if (StringUtil.isNotBlank(appUserId)) {
			// 去redis查找appUserT(通过用户id查找对象)
			appUserT = findAppUserByRedis(appUserId);
			if (appUserT == null) {
				// 去数据库查找appUserT
				appUserT = findAppUserByAppUserId(appUserId);
				if (appUserT != null) {
					// AppUser保存在redis
					saveAppUserByRedis(appUserT);
				}
			}
		}
		return appUserT;
	}
	/**
	 * AppUser保存在redis
	 * 
	 * @Title: saveAppUserByRedis
	 * @Description:
	 *
	 * 				参数说明
	 * @param appUser
	 * @return
	 * @throws Exception
	 *             返回类型 Map<String,String>
	 */
	public Map<String, String> saveAppUserByRedis(AppUserT appUser) throws Exception {
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
					Map<String, String> dataMap = beanUtil.doBean2MapStr(appUser);
					// dataMap.put("CREATE_TIME_",
					// dateUtil.doUtilDate2String(appUser.getCreateTime()));
					// dataMap.put("UPDATE_TIME_",
					// dateUtil.doUtilDate2String(appUser.getCreateTime()));
					redisUtil.save(jedis, "app_user", appUser.getAppUserId(), appUser.getUpdateTime().getTime(),
							dataMap);
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
	 * 从redis删除AppUserT
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param appUser
	 * @throws Exception
	 *             返回类型 void
	 */
	public void delAppUserByRedis(String appUserId) throws Exception {
		String redisLocalStart = SysConfig.findInstance().findMap().get("redis.local.start").toString();
		if ("true".equals(redisLocalStart)) {
			RedisUtil redisUtil = null;
			Jedis jedis = null;
			try {
				redisUtil = RedisUtil.findInstance();
				jedis = redisUtil.findJedis();
				if (jedis != null) {
					jedis.select(RedisUtil.dbIndex);
					redisUtil.del(jedis, "app_user", appUserId);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				redisUtil.close(jedis);
			}
		}
	}
	/**
	 * 从redis删除AppUserT
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param appUser
	 * @throws Exception
	 *             返回类型 void
	 */
	public void delAppUserByRedis(AppUserT appUser) throws Exception {
		String redisLocalStart = SysConfig.findInstance().findMap().get("redis.local.start").toString();
		if ("true".equals(redisLocalStart)) {
			RedisUtil redisUtil = null;
			Jedis jedis = null;
			try {
				redisUtil = RedisUtil.findInstance();
				jedis = redisUtil.findJedis();
				if (jedis != null) {
					jedis.select(RedisUtil.dbIndex);
					redisUtil.del(jedis, "app_user", appUser.getAppUserId());
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				redisUtil.close(jedis);
			}
		}
	}
	/**
	 * AppUser更新在redis
	 * 
	 * @Title: updateAppUserByRedis
	 * @Description:
	 *
	 * 				参数说明
	 * @param entity
	 * @throws Exception
	 *             返回类型 void
	 */
	public void updateAppUserByRedis(AppUserT entity) throws Exception {
		this.delAppUserByRedis(entity.getAppUserId());
		this.saveAppUserByRedis(entity);
	}
	/**
	 * 去redis查找AppUser;
	 * 
	 * 去redis查找appUserT(通过用户查找对象);
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public AppUserT findAppUserByRedis(String appUserId) throws Exception {
		BeanUtil beanUtil = BeanThreadLocal.findThreadLocal().get();
		Map<String, String> mapStr = this.findAppUserMapByRedis(appUserId);
		AppUserT appUserT = (AppUserT) beanUtil.doMapStr2Bean(mapStr, AppUserT.class);
		return appUserT;
	}
	/**
	 * 去redis查找AppUser
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public Map<String, String> findAppUserMapByRedis(String appUserId) throws Exception {
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
					appUserMap = redisUtil.findMap(jedis, "app_user", appUserId);
				}
				String appUserIdReturn = BeanThreadLocal.find(appUserMap.get("appUserId"), "");
				if (StringUtil.isBlank(appUserIdReturn)) {
					return null;
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
	public List<AppUserT> findAllObjectByRedis() throws Exception {
		List<AppUserT> listObject = new ArrayList<AppUserT>();
		BeanUtil beanUtil = BeanThreadLocal.findThreadLocal().get();
		List<Map<String, String>> listMap = this.findAllMapByRedis();
		if (listMap != null) {
			for (Map<String, String> map : listMap) {
				AppUserT appUserT = (AppUserT) beanUtil.doMapStr2Bean(map, AppUserT.class);
				listObject.add(appUserT);
			}
		}
		return listObject;
	}
	public List<Map<String, String>> findAllMapByRedis() throws Exception {
		String redisLocalStart = SysConfig.findInstance().findMap().get("redis.local.start").toString();
		if ("true".equals(redisLocalStart)) {
			List<Map<String, String>> listMap = null;
			RedisUtil redisUtil = null;
			Jedis jedis = null;
			try {
				// 去redis查找
				redisUtil = RedisUtil.findInstance();
				jedis = redisUtil.findJedis();
				if (jedis != null) {
					jedis.select(RedisUtil.dbIndex);
					listMap = redisUtil.findMapList(jedis, "app_user");
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
	public List<AppUserT> findPageObjectByRedis(int pageIndex, int pageSize) throws Exception {
		String redisLocalStart = SysConfig.findInstance().findMap().get("redis.local.start").toString();
		if ("true".equals(redisLocalStart)) {
			List<AppUserT> listObject = null;
			RedisUtil redisUtil = null;
			Jedis jedis = null;
			try {
				// 去redis查找
				redisUtil = RedisUtil.findInstance();
				jedis = redisUtil.findJedis();
				if (jedis != null) {
					jedis.select(RedisUtil.dbIndex);
					listObject = redisUtil.findPageObject(jedis, AppUserT.class, pageIndex, pageSize);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				redisUtil.close(jedis);
			}
			return listObject;
		}
		return null;
	}
	public List<Map<String, String>> findPageMapByRedis(int pageIndex, int pageSize) throws Exception {
		String redisLocalStart = SysConfig.findInstance().findMap().get("redis.local.start").toString();
		if ("true".equals(redisLocalStart)) {
			List<Map<String, String>> listMap = null;
			RedisUtil redisUtil = null;
			Jedis jedis = null;
			try {
				// 去redis查找
				redisUtil = RedisUtil.findInstance();
				jedis = redisUtil.findJedis();
				if (jedis != null) {
					jedis.select(RedisUtil.dbIndex);
					listMap = redisUtil.findPageMap(jedis, AppUserT.class, pageIndex, pageSize);
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
	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/
}
