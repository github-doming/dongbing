package com.ibm.old.v1.pc.app_user.t.service;
import all.gen.app_token.t.service.AppTokenTService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @Description:
 * @Author: zjj
 * @Date: 2019-05-05 11:24
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class AppTokenService extends AppTokenTService {

	public void delByUserId(String id) throws SQLException {
		String sql = "update app_token set state_='DEL',UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where APP_USER_ID_=?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	void delAllByUserId(String[] ids) throws SQLException {
		if(ids!=null){
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : ids) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update app_token set state_='DEL' where APP_USER_ID_ in("
					+ stringBuilder.toString() + ")";

			dao.execute(sql, null);
		}
	}
}
