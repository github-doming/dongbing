package all.gen.app_token.t.service;

import all.gen.app_token.t.entity.AppTokenT;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AppTokenTService extends BaseServiceProxy {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 *
	 * 保存
	 *
	 * @param entity
	 * @throws Exception
	 */
	public String  save(AppTokenT entity) throws Exception {

		return dao.save(entity);
	}
	/**
	 *
	 * 逻辑删除
	 *
	 * @param id
	 * @throws Exception
	 */
	public void del(String id) throws Exception {

		String sql = "update app_token set state_='DEL' where APP_TOKEN_ID_=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}
	/**
	 *
	 * 逻辑删除所有
	 *
	 * @throws Exception
	 */
	public void delAll(String[] idArray) throws Exception {
		if(idArray!=null){
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : idArray) {
				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "update app_token set state_='DEL' where APP_TOKEN_ID_ in("
					+ stringBuffer.toString() + ")";

			dao.execute(sql, null);
		}


	}


	/**
	 *
	 * 物理删除
	 *
	 * @param id
	 * @throws Exception
	 */
	public void delPhysical(String id) throws Exception {

		String sql = "delete from app_token where APP_TOKEN_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 *
	 * 物理删除所有
	 *
	 * @throws Exception
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if(idArray!=null){
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : idArray) {
				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "delete from app_token where APP_TOKEN_ID_ in("
					+ stringBuffer.toString() + ")";

			dao.execute(sql, null);
		}


	}

	/**
	 *
	 * 更新
	 *
	 * @param entity
	 * @throws Exception
	 */
	public void update(AppTokenT entity) throws Exception {
		dao.update(entity);
	}
	/**
	 *
	 * 查找实体
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public AppTokenT find(String id) throws Exception {
		return (AppTokenT) this.dao.find(AppTokenT.class, id);

	}





	/**
	 *
	 * 分页
	 *
	 * @param sortFieldName
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<Map<String, Object>> find(String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sqlCount ;
		String sql ;
		ArrayList<Object> parameters = new ArrayList<>();
		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
            sqlCount = "SELECT count(*) FROM app_token where state_!='DEL'";
			sql = "SELECT * FROM app_token  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {

            sqlCount = "SELECT count(*) FROM app_token where state_!='DEL'";
			sql = "SELECT * FROM app_token  where state_!='DEL' order by " + sortFieldName
					+ " " + sortOrderName;
		}
        return  dao.page(sql, parameters, pageIndex, pageSize, sqlCount);
	}



	/**
	 *
	 * 分页
	 *
	 * @param sortFieldName
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<AppTokenT> findObject(String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sqlCount  ;
		String sql ;

		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
            sqlCount = "SELECT count(*) FROM app_token where state_!='DEL'";
			sql = "SELECT * FROM app_token  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {

            sqlCount = "SELECT count(*) FROM app_token where state_!='DEL'";
			sql = "SELECT * FROM app_token  where state_!='DEL' order by " +sortFieldName
					+ " " + sortOrderName;
		}

        return dao.page(AppTokenT.class,
				sql, null, pageIndex, pageSize, sqlCount);
	}
	/**
	 *
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM app_token  where state_!='DEL' order by UPDATE_TIME_ desc";
        return this.dao.findMapList( sql,null);
	}

	/**
	 *
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<AppTokenT> findObjectAll() throws Exception {
		String sql = "SELECT * FROM app_token  where state_!='DEL' order by UPDATE_TIME_ desc";
        return this.dao.findObjectList(AppTokenT.class, sql);
	}
}
