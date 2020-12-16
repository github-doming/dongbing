package c.x.platform.admin.feng.fas.business_all.family_account_bill_info.service;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.data_row.JdbcDataDto;
import c.a.util.core.jdbc.bean.data_row.JdbcRowDto;
public class FamilyAccountBillInfoService extends BaseService {
	public void delAll(String[] ids) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (String id : ids) {
			sb.append(id).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		String sql = "delete from fas_bill_info where id in(" + sb.toString()
				+ ")";
		dao.execute(sql, null);
	}
	public void del(String id) throws Exception {
		String sql = "delete  from fas_bill_info where id=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}
	public void update(HttpServletRequest request) throws Exception {
		String sql = "update fas_bill_info set business_name=?  where id=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(request.getParameter("sys_type_info.name"));
		parameterList.add(request.getParameter("sys_type_info.id"));
		dao.execute(sql, parameterList);
	}
	public void insert(HttpServletRequest request) throws Exception {
		String sql = "insert fas_bill_info(business_name) values(?)";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(request.getParameter("sys_type_info.name"));
		dao.execute(sql, parameterList);
	}

	public JdbcRowDto query(String id) throws Exception {
		String sql = "select * from fas_bill_info where id=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(id);
		return this.dao.findJdbcRow(sql, parameterList);
	}
	public JdbcDataDto list() throws Exception {
		// String sql =
		// "select id,did,account_begin_did,account_begin,account_amount from fas_bill_info";
		String sql = "SELECT b.business_name as 业务,b.id,b.account_begin_id as begin_id,"
				+ "account_begin as begin,b.account_amount as 增减,"
				+ "account_balance as 余额 "
				+ "FROM fas_bill_info b "
				+ "left join "
				+ "fas_business_info bu "
				+ "on b.business_id=bu.id " + "order by b.id desc";
		JdbcDataDto data = dao.findJdbcData(sql, null);
		return data;
	}
}
