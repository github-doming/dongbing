package all.app.common.service;
import all.gen.app_token.t.entity.AppTokenT;
import all.gen.app_token.t.service.AppTokenTService;
import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
public class AppTokenService extends AppTokenTService {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 
	 * 查询AppTokenT
	 * 
	 * @return
	 * @throws Exception
	 */
	public AppTokenT findAppToken(String tenantCode, String accountName, String password, String channelType)
			throws Exception {
		String commLocalASE = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE"), "");
		String commLocalASEKey = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE_key"),
				"");
		if("true".equals(commLocalASE)){
			password= Objects.requireNonNull(CommASEUtil.encode(commLocalASEKey, password.trim())).trim();
		}
		List<Object> parameterList = new ArrayList<Object>();
		String sql = "SELECT a.ACCOUNT_NAME_,t.* FROM app_token t "
				+ "LEFT JOIN app_user u ON u.APP_USER_ID_ = t.APP_USER_ID_ "
				+ "LEFT JOIN app_account a ON a.APP_USER_ID_ = u.APP_USER_ID_ "
				+ "WHERE t.TENANT_CODE_ =? AND a.ACCOUNT_NAME_ =?  AND a.PASSWORD_ =? AND t.CHANNEL_TYPE_ =?";
		parameterList.add(tenantCode);
		parameterList.add(accountName);
		parameterList.add(password);
		parameterList.add(channelType);
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
        return (AppTokenT) jdbcTool.findObjectUnique(AppTokenT.class, conn, sql, parameterList);
	}
	/**
	 * 
	 * 查询AppTokenT
	 * 
	 * @deprecated
	 * @return
	 * @throws Exception
	 */
	public AppTokenT findAppToken_v1(String accountName, String password, String registerType) throws Exception {
		AppTokenT appTokenT = new AppTokenT();
		List<Object> parameterList = new ArrayList<>();
		String sql = "SELECT t.VALUE_ as token,t.APP_USER_ID_,t.APP_TOKEN_ID_ ,t.REGISTER_TYPE_ " + " FROM app_user u "
				+ " LEFT JOIN app_account a ON u.APP_USER_ID_ = a.APP_USER_ID_ "
				+ " LEFT JOIN app_token t ON a.APP_USER_ID_ = t.APP_USER_ID_  "
				+ " WHERE a.ACCOUNT_NAME_ = ? AND a.PASSWORD_ = ? AND t.REGISTER_TYPE_ =?";
		parameterList.add(accountName);
		parameterList.add(password);
		parameterList.add(registerType);
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		Map<String, Object> map = jdbcUtil.findMap(conn, sql, parameterList);
		if (map == null || map.size() == 0) {
			return null;
		}
		appTokenT.setValue(map.get("token").toString());
		appTokenT.setAppUserId(map.get("APP_USER_ID_").toString());
		appTokenT.setAppTokenId(map.get("APP_TOKEN_ID_").toString());
		appTokenT.setChannelType(map.get("CHANNEL_TYPE_").toString());
		return appTokenT;
	}
	/**
	 * 
	 * 查询AppTokenT
	 * 
	 * @return
	 * @throws Exception
	 */
	public AppTokenT findAppTokenByToken(String token) throws Exception {
		List<Object> parameterList = new ArrayList<>();
		String sql = "SELECT * FROM app_token WHERE VALUE_ =?";
		parameterList.add(token.trim());
        return (AppTokenT) this.dao.findObject(AppTokenT.class, sql, parameterList);
	}
	/**
	 * 
	 * 查询AppTokenT
	 * 
	 * @return
	 * @throws Exception
	 */
	public AppTokenT findAppTokenByAppUserId(String appUserId) throws Exception {
		List<Object> parameterList = new ArrayList<>();
		String sql = "SELECT * FROM app_token WHERE APP_USER_ID_ =?";
		parameterList.add(appUserId.trim());
        return (AppTokenT) this.dao.findObject(AppTokenT.class, sql, parameterList);
	}

    /**
     * 获取登录用户的ip信息
     * @return
     */
    public Map<Object, Object> findUsers(String state) throws SQLException {
        String sql="select APP_USER_ID_ as key_,IP_ as value_ from app_token where STATE_=?"
                + " GROUP BY APP_USER_ID_ ORDER BY UPDATE_TIME_LONG_ DESC";
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(state);
        return super.findKVMap(sql,parameterList);
    }
}
