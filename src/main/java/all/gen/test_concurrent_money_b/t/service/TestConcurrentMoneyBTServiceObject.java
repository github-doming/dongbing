package all.gen.test_concurrent_money_b.t.service;

import java.util.ArrayList;
import java.util.List;
import all.gen.test_concurrent_money_b.t.entity.TestConcurrentMoneyBT;
import  c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;

public class TestConcurrentMoneyBTServiceObject extends BaseService {
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TestConcurrentMoneyBT find(String id) throws Exception {
		return (TestConcurrentMoneyBT) this.dao.find(TestConcurrentMoneyBT.class, id);

	}

	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(TestConcurrentMoneyBT entity) throws Exception {
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
		String sql = "delete from test_concurrent_money_b where TEST_CONCURRENT_MONEY_B_ID_ in("
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

		String sql = "delete from test_concurrent_money_b where TEST_CONCURRENT_MONEY_B_ID_=?";
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
	public String  save(TestConcurrentMoneyBT entity) throws Exception {

		return dao.save(entity);
	}

	/**
	 * 
	 * 分页
	 * 
	 * @param order
	 * @param sortFieldName
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<TestConcurrentMoneyBT> find(String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;

		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
			sql_count = "SELECT count(*) FROM test_concurrent_money_b ";
			sql = "SELECT * FROM test_concurrent_money_b order by UPDATE_TIME_ desc";
		} else {

			sql_count = "SELECT count(*) FROM test_concurrent_money_b ";
			sql = "SELECT * FROM test_concurrent_money_b order by " +sortFieldName
					+ " " + sortOrderName;
		}

		PageCoreBean<TestConcurrentMoneyBT> basePage = dao.page(TestConcurrentMoneyBT.class,
				sql, null, pageIndex.intValue(), pageSize.intValue(), sql_count);
		return basePage;
	}
	
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<TestConcurrentMoneyBT> findAll() throws Exception {
		//String sql = "SELECT * FROM test_concurrent_money_b order by TEST_CONCURRENT_MONEY_B_ID_ desc";
		String sql = "SELECT * FROM test_concurrent_money_b order by UPDATE_TIME_ desc";
		List<TestConcurrentMoneyBT> list = this.dao.findObjectList(TestConcurrentMoneyBT.class, sql);

		return list;
	}
}
