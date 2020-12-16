package com.ibm.old.v1.app.t.service;
import all.app_admin.app.app_account.t.service.AppAccountTService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * @Description:
 * @Author: zjj
 * @Date: 2019-05-05 11:19
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class AppAccountService extends AppAccountTService {

	public void delByUserId(String id) throws SQLException {
		String sql = "update app_account set state_='DEL' where APP_USER_ID_=?";
		List<Object> parameters = new ArrayList<Object>();
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
			String sql = "update app_account set state_='DEL' where APP_USER_ID_ in("
					+ stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}
}
