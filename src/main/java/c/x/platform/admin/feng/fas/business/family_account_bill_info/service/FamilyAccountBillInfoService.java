package c.x.platform.admin.feng.fas.business.family_account_bill_info.service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import c.x.platform.admin.feng.fas.business.family_account_bill_info.entity.FamilyAccountBillInfo;
import c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
public class FamilyAccountBillInfoService extends BaseService {
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public FamilyAccountBillInfo find(String id) throws Exception {
		return (FamilyAccountBillInfo) this.dao.find(
				FamilyAccountBillInfo.class, id);
	}
	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(FamilyAccountBillInfo entity) throws Exception {
		dao.update(entity);
	}
	/**
	 * 
	 * 删除所有
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delAll(String[] ids) throws Exception {
		if (ids != null) {
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : ids) {
				stringBuffer.append(id).append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "delete from fas_bill_info where id in("
					+ stringBuffer.toString() + ")";
			dao.execute(sql, null);
		}
	}
	/**
	 * 
	 * 删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void del(String id) throws Exception {
		String sql = "delete from fas_bill_info where id=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}
	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String insert(FamilyAccountBillInfo entity) throws Exception {
		// 查找上一条分录的最大数据ID
		// 查找上一条分录的余额
		String sql = "select * from fas_bill_info i where i.business_id="
				+ entity.getBusiness_id() + " order by  id desc";
		Map<String, Object> map = dao.findMap(sql, null);
		Long did = 0l;
		java.math.BigDecimal account_balance = new BigDecimal(0);
		if (map != null) {
			// 查找上一条分录的最大数据ID
			did = (Long) map.get("id");
			// 查找上一条分录的余额
			account_balance = (java.math.BigDecimal) map.get("account_balance");
		}
		// 保存
		entity.setAccount_begin(account_balance);
		entity.setAccount_begin_id(did);
		entity.setAccount_balance(account_balance.add(entity
				.getAccount_amount()));
		// 保存时间
		Calendar calendar = Calendar.getInstance();
		entity.setCreate_time(calendar.getTimeInMillis());
		entity.setCreate_time_dt(calendar.getTime());
		// 保存
		String pk = dao.save(entity);
		// 重新更新
		dao.update(entity);
		return pk;
	}
	/**
	 * 
	 * 分页
	 * 
	 * @param order
	 * @param order_property
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<Map<String, Object>> find(String sortOrderName,
			String sortFieldName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameterList = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) FROM fas_bill_info ";
			// sql = "SELECT * FROM fas_bill_info order by id desc";
			sql = "SELECT b.*,bu.name as business_name FROM fas_bill_info b "
					+ "left join " + "fas_business_info bu "
					+ "on b.business_id=bu.id " + "order by b.id desc";
		} else {
			sql_count = "SELECT count(*) FROM fas_bill_info ";
			// sql = "SELECT * FROM fas_bill_info order by " +
			// order_property
			// + " " + sortOrderName;
			sql = "SELECT b.*,bu.name as business_name FROM fas_bill_info b "
					+ "left join " + "fas_business_info bu "
					+ "on b.business_id=bu.id " + "order by b."
					+ sortFieldName + " " + sortOrderName;
		}
		PageCoreBean<Map<String, Object>> pageBean = dao.page(sql,
				parameterList, pageIndex.intValue(), pageSize.intValue(),
				sql_count);
		return pageBean;
	}
	/**
	 * 
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> listAll() throws Exception {
		String sql = "SELECT * FROM fas_bill_info order by id desc";
		List<Map<String, Object>> listMap = this.dao.findMapList(sql, null);
		return listMap;
	}
}
