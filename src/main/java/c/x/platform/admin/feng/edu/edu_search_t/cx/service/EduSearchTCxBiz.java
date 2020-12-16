package c.x.platform.admin.feng.edu.edu_search_t.cx.service;

import java.util.ArrayList;
import java.util.List;

import c.x.platform.admin.feng.edu.edu_search_t.cx.entity.EduSearchTCx;

public class EduSearchTCxBiz extends EduSearchTCxService {

	/**
	 * 
	 * 保存或更新
	 * 
	 * @return
	 * @throws Exception
	 */
	public void saveOrUpdate(EduSearchTCx entity) throws Exception {
		EduSearchTCx cEduSearchTCx = this.query(entity.getUid());
		if (cEduSearchTCx != null) {
			this.update(cEduSearchTCx);
		} else {
			this.save(entity);
		}
	}
	/**
	 * 通过uid查询
	 * 
	 * @Title: query
	 * @Description:
	 * @param uid
	 * @return
	 * @throws Exception 参数说明
	 * @return EduSearchTCx 返回类型
	 * @throws
	 */
	public EduSearchTCx query(String uid) throws Exception {
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(uid);
		String sql = "SELECT * FROM edu_search_t where uid=?";
		EduSearchTCx bean = (EduSearchTCx) this.dao.findObject(
				EduSearchTCx.class, sql, parameterList);
		return bean;
	}

}
