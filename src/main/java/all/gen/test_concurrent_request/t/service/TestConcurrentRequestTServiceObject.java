package all.gen.test_concurrent_request.t.service;

import java.util.ArrayList;
import java.util.List;
import all.gen.test_concurrent_request.t.entity.TestConcurrentRequestT;
import  c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;

public class TestConcurrentRequestTServiceObject extends BaseService {
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TestConcurrentRequestT find(String id) throws Exception {
		return (TestConcurrentRequestT) this.dao.find(TestConcurrentRequestT.class, id);

	}

	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(TestConcurrentRequestT entity) throws Exception {
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
		String sql = "delete from test_concurrent_request where TEST_CONCURRENT_REQUEST_ID_ in("
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

		String sql = "delete from test_concurrent_request where TEST_CONCURRENT_REQUEST_ID_=?";
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
	public String  save(TestConcurrentRequestT entity) throws Exception {

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
	public PageCoreBean<TestConcurrentRequestT> find(String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;

		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
			sql_count = "SELECT count(*) FROM test_concurrent_request ";
			sql = "SELECT * FROM test_concurrent_request order by UPDATE_TIME_ desc";
		} else {

			sql_count = "SELECT count(*) FROM test_concurrent_request ";
			sql = "SELECT * FROM test_concurrent_request order by " +sortFieldName
					+ " " + sortOrderName;
		}

		PageCoreBean<TestConcurrentRequestT> basePage = dao.page(TestConcurrentRequestT.class,
				sql, null, pageIndex.intValue(), pageSize.intValue(), sql_count);
		return basePage;
	}
	
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<TestConcurrentRequestT> findAll() throws Exception {
		//String sql = "SELECT * FROM test_concurrent_request order by TEST_CONCURRENT_REQUEST_ID_ desc";
		String sql = "SELECT * FROM test_concurrent_request order by UPDATE_TIME_ desc";
		List<TestConcurrentRequestT> list = this.dao.findObjectList(TestConcurrentRequestT.class, sql);

		return list;
	}
}
