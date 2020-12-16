package com.ibm.old.v1.cloud.ibm_role_resource.w.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_role_resource.w.entity.IbmRoleResourceW;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;

import java.sql.SQLException;
import java.util.*;

public class IbmRoleResourceWService extends BaseService {

	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String  save(IbmRoleResourceW entity) throws Exception {

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

		String sql = "update ibm_role_resource set state_='DEL' where IBM_ROLE_RESOURCE_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}
	/**
	 * 
	 * 逻辑删除所有
	 * 
	 * @param idArray
	 * @throws Exception
	 */
	public void delAll(String[] idArray) throws Exception {
		if(idArray!=null){
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : idArray) {
				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "update ibm_role_resource set state_='DEL' where IBM_ROLE_RESOURCE_ID_ in("
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

		String sql = "delete from ibm_role_resource where IBM_ROLE_RESOURCE_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}
	
/**
	 * 
	 * 物理删除所有
	 * 
	 * @param idArray
	 * @throws Exception
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if(idArray!=null){
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : idArray) {
				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "delete from ibm_role_resource where IBM_ROLE_RESOURCE_ID_ in("
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
	public void update(IbmRoleResourceW entity) throws Exception {
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
	public IbmRoleResourceW find(String id) throws Exception {
		return (IbmRoleResourceW) this.dao.find(IbmRoleResourceW.class, id);

	}

	

	

	/**
	 * 
	 * 分页
	 * 
	 * @param sortFieldName
	 * @param sortOrderName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<Map<String, Object>> find(String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sqlCount  ;
		String sql  ;
		ArrayList<Object> parameters = new ArrayList<Object>();
		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
			sqlCount = "SELECT count(*) FROM ibm_role_resource where state_!='DEL'";
			sql = "SELECT * FROM ibm_role_resource  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {

			sqlCount = "SELECT count(*) FROM ibm_role_resource where state_!='DEL'";
			sql = "SELECT * FROM ibm_role_resource  where state_!='DEL' order by " + sortFieldName
					+ " " + sortOrderName;
		}
				
		return  dao.page(sql, parameters, pageIndex.intValue(), pageSize
				.intValue(), sqlCount);
	}
	
	
	
	/**
	 * 
	 * 分页
	 * 
	 * @param sortFieldName
	 * @param sortOrderName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<IbmRoleResourceW> findObject(String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sqlCount ;
		String sql ;

		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
			sqlCount = "SELECT count(*) FROM ibm_role_resource where state_!='DEL'";
			sql = "SELECT * FROM ibm_role_resource  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {

			sqlCount = "SELECT count(*) FROM ibm_role_resource where state_!='DEL'";
			sql = "SELECT * FROM ibm_role_resource  where state_!='DEL' order by " +sortFieldName
					+ " " + sortOrderName;
		}


		return dao.page(IbmRoleResourceW.class,
				sql, null, pageIndex, pageSize, sqlCount);
	}
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAll() throws Exception {

		String sql = "SELECT * FROM ibm_role_resource  where state_!='DEL' order by UPDATE_TIME_ desc";

		return this.dao.findMapList( sql,null);
	}
	
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List findObjectAll() throws Exception {

		String sql = "SELECT * FROM ibm_role_resource  where state_!='DEL' order by UPDATE_TIME_ desc";

		return this.dao.findObjectList(IbmRoleResourceW.class, sql);
	}
	
	/**
	 * 
	 * @Description: 保存角色资源
	 *
	 * @param roleId 角色ID
	 * @param resourceIds 资源ID
	 * @param resourceType 资源类型
	 */
	public void saveRoleResource(String roleId, List<String> resourceIds, String resourceType) throws SQLException {
		Date nowTime = new Date();
		StringBuilder sql = new StringBuilder("INSERT into ibm_role_resource(IBM_ROLE_RESOURCE_ID_,ROLE_ID_,RESOURCE_ID_,"
				+ "RESOURCE_TYPE_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) VALUES ");
		List<Object> parameterList = new ArrayList<>();
		for (String resourceId : resourceIds) {
			sql.append("(?,?,?,?,?,?,?,?,?),");
			parameterList.add(UUID.randomUUID().toString().replace("-", ""));
			parameterList.add(roleId);
			parameterList.add(resourceId);
			parameterList.add(resourceType);
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add(IbmStateEnum.OPEN.name());
		}
		sql.replace(sql.length()-1, sql.length(), "");
		super.dao.execute(sql.toString(), parameterList);
	}
	
}
