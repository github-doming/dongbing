package all.sys.ay.sys_request_token.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import all.gen.sys_request_token.t.service.SysRequestTokenTService;

public class SysRequestTokenService extends SysRequestTokenTService {
	/**
	 * 更新版本
	 * 
	 * @Description:
	 * @Title: updateVersion 
	 * @param token
	 * @param version
	 * @return
	 * @throws Exception  参数说明 
	 * @return Integer    返回类型 
	 * @throws
	 */
	public Integer updateVersion(String token, int version) throws Exception {
		String sql = "UPDATE SYS_REQUEST_TOKEN set  VERSION_=VERSION_+1 where TOKEN_=? and VERSION_=?";
		ArrayList<Object> parameterList = new ArrayList<Object>();
		parameterList.add(token);
		parameterList.add(version);
		int count = dao.execute(sql, parameterList);
		return count;
	}

	/**
	 * 
	 * 通过令牌找出对象
	 * @Description:
	 * @Title: findByToken 
	 * @param token
	 * @return
	 * @throws Exception  参数说明 
	 * @return Map<String,Object>    返回类型 
	 * @throws
	 */
	public Map<String, Object> findByToken(String token) throws Exception {
		String sql = "select *  FROM SYS_REQUEST_TOKEN where TOKEN_=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(token);
		Map<String, Object> map = dao.findMap(sql, parameterList);
		return map;
	}

}
