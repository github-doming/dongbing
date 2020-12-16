package c.x.platform.admin.feng.fas.business.family_account_bill_info.service;
import java.util.ArrayList;
import java.util.List;

import c.x.platform.admin.feng.fas.business.family_account_bill_info.entity.FamilyAccountBillInfo;
import c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
public class FamilyAccountBillInfoService_listObject extends BaseService {
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
		StringBuilder stringBuffer = new StringBuilder();
		for (String id : ids) {
			stringBuffer.append(id).append(",");
		}
		stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		String sql = "delete from family_account_bill_info where id in("
				+ stringBuffer.toString() + ")";
		dao.execute(sql, null);
	}
	/**
	 * 
	 * 删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void del(String id) throws Exception {
		String sql = "delete from family_account_bill_info where id=?";
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
		return dao.save(entity);
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
	public PageCoreBean<FamilyAccountBillInfo> find(String sortOrderName,
			String sortFieldName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sql_count = null;
		String sql = null;
		if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) FROM family_account_bill_info ";
			sql = "SELECT * FROM family_account_bill_info order by id desc";
		} else {
			sql_count = "SELECT count(*) FROM family_account_bill_info ";
			sql = "SELECT * FROM family_account_bill_info order by "
					+ sortFieldName + " " + sortOrderName;
		}
		PageCoreBean<FamilyAccountBillInfo> pageBean = dao.page(
				FamilyAccountBillInfo.class, sql, null, pageIndex.intValue(),
				pageSize.intValue(), sql_count);
		return pageBean;
	}
	/**
	 * 
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<FamilyAccountBillInfo> listAll() throws Exception {
		String sql = "SELECT * FROM family_account_bill_info order by id desc";
		List<FamilyAccountBillInfo> list = this.dao.findObjectList(
				FamilyAccountBillInfo.class, sql);
		return list;
	}
}
