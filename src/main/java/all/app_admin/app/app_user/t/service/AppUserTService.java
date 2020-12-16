package all.app_admin.app.app_user.t.service;

import all.gen.app_user.t.entity.AppUserT;
import c.a.util.core.enums.bean.UserStateEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AppUserTService extends BaseService {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 通过用户名，找出用户是否存在
	 *
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public boolean isUserByName(String name) throws Exception {
		String sql = "select APP_USER_NAME_ FROM APP_USER where APP_USER_NAME_=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(name);
		List<Map<String, Object>> listMap = this.dao.findMapList(sql, parameterList);
		return listMap.size() != 0;
	}
	/**
	 * 保存
	 *
	 * @param entity
	 * @throws Exception
	 */
	public String save(AppUserT entity) throws Exception {
		entity.setState(UserStateEnum.OPEN.getCode());
		return dao.save(entity);
	}
	/**
	 * 逻辑删除
	 *
	 * @param id
	 * @throws Exception
	 */
	public void del(String id) throws Exception {
		String sql = "update app_user set state_='DEL',UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where APP_USER_ID_=?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(id);
		dao.execute(sql, parameters);
	}
	/**
	 * 逻辑删除所有
	 *
	 * @param ids
	 * @throws Exception
	 */
	public void delAll(String[] ids) throws Exception {
		if (ids != null) {
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : ids) {
				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "update app_user set state_='DEL' where APP_USER_ID_ in(" + stringBuffer.toString() + ")";

			dao.execute(sql, null);
		}

	}

	/**
	 * 物理删除
	 *
	 * @param id
	 * @throws Exception
	 */
	public void delPhysical(String id) throws Exception {

		String sql = "delete from app_user where APP_USER_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除所有
	 *
	 * @param ids
	 * @throws Exception
	 */
	public void delAllPhysical(String[] ids) throws Exception {
		if (ids != null) {
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : ids) {
				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "delete from app_user where APP_USER_ID_ in(" + stringBuffer.toString() + ")";

			dao.execute(sql, null);
		}

	}

	/**
	 * 更新
	 *
	 * @param entity
	 * @throws Exception
	 */
	public void update(AppUserT entity) throws Exception {
		dao.update(entity);
	}
	/**
	 * 查找实体
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public AppUserT find(String id) throws Exception {
		return (AppUserT) this.dao.find(AppUserT.class, id);

	}

	/**
	 * 分页
	 *
	 * @param sortFieldName
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean find(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize, String tenantCode) throws Exception {
		String sqlCount  ;
		String sql ;
		ArrayList<Object> parameters = new ArrayList<>();
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sqlCount = "SELECT count(*) FROM app_user where state_!='DEL' and TENANT_CODE_=? ";
			sql = "SELECT * FROM app_user  where state_!='DEL' and TENANT_CODE_=? order by UPDATE_TIME_ desc";
		} else {

			sqlCount = "SELECT count(*) FROM app_user where state_!='DEL' and TENANT_CODE_=? ";
			sql = "SELECT * FROM app_user  where state_!='DEL' and TENANT_CODE_=?  order by " + sortFieldName + " "
					+ sortOrderName;
		}
		parameters.add(tenantCode);

		return dao.page(sql, parameters, pageIndex, pageSize, sqlCount, parameters);
	}

}
