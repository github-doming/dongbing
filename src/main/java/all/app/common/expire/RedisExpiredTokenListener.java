package all.app.common.expire;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import all.app.common.service.AppTokenRedisService;
import all.gen.app_token.t.entity.AppTokenT;
import c.a.config.SysConfig;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.UserStateEnum;
import c.a.util.core.jdbc.nut.IJdbcUtil;
/**
 * 
 * 不能长久等待JDBC连接;
 * 
 * 
 * token过期监听;
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
public class RedisExpiredTokenListener extends RedisJedisPubSub {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute(String token) throws Exception {
		String commLocalProject = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.project"),"");
		if ("game".equals(commLocalProject)) {
			log.trace("token=" + token);
			// 更改用户为离线状态
			AppTokenRedisService appTokenRedisService = new AppTokenRedisService();
			String appUserId = appTokenRedisService.findAppUserIdByToken(token);
			log.trace("appUserId=" + appUserId);
			IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
			IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
			Connection conn = jdbcUtil.getConnection();
			// 更新ga_user_game的状态
			String sql = "update ga_user_game set STATE_=? where APP_USER_ID_=?";
			List<Object> parameterList = new ArrayList<Object>();
			parameterList.add(UserStateEnum.OFF_LINE.getCode());
			parameterList.add(appUserId);
			jdbcUtil.execute(conn, sql, parameterList);
			// 更新状态为离线
			// 查找AppTokenT
			AppTokenT appTokenT = appTokenRedisService.findAppTokenByToken(token);
			if (appTokenT != null) {
				appTokenT.setValue(null);
				appTokenT.setState(UserStateEnum.OFF_LINE.getCode());
				// 更新数据库的token
				appTokenRedisService.update(appTokenT);
				// 从redis删除Token
				appTokenRedisService.delTokenByRedis(token);
			}
			
		}
	
		return null;
	}
}
