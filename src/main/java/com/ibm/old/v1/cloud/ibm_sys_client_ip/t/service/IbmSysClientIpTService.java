package com.ibm.old.v1.cloud.ibm_sys_client_ip.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_sys_client_ip.t.entity.IbmSysClientIpT;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmSysClientIpTService extends BaseService {



	/**
	 * 保存{ay_table_class}对象数据
	 * @param entity IbmSysClientIpT对象数据
	 */
	public String save(IbmSysClientIpT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_sys_client_ip的 IBM_SYS_CLIENT_IP_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_sys_client_ip set state_='DEL' where IBM_SYS_CLIENT_IP_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_SYS_CLIENT_IP_ID_主键id数组的数据
	 * @param idArray 要删除ibm_sys_client_ip的 IBM_SYS_CLIENT_IP_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_sys_client_ip set state_='DEL' where IBM_SYS_CLIENT_IP_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除ibm_sys_client_ip的 IBM_SYS_CLIENT_IP_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_sys_client_ip where IBM_SYS_CLIENT_IP_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_SYS_CLIENT_IP_ID_主键id数组的数据
	 * @param idArray 要删除ibm_sys_client_ip的 IBM_SYS_CLIENT_IP_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_sys_client_ip where IBM_SYS_CLIENT_IP_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmSysClientIpT实体信息
	 * @param entity IbmSysClientIpT实体
	 */
	public void update(IbmSysClientIpT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_sys_client_ip表主键查找IbmSysClientIpT实体
	 * @param id ibm_sys_client_ip 主键
	 * @return IbmSysClientIpT实体
	 */
	public IbmSysClientIpT find(String id) throws Exception {
		return (IbmSysClientIpT) this.dao.find(IbmSysClientIpT. class,id);

	}

	/**
	 * 获取分页Map数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页Map数据
	 */
	public PageCoreBean find(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_sys_client_ip where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_sys_client_ip  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_sys_client_ip  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmSysClientIpT数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页IbmSysClientIpT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_sys_client_ip where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_sys_client_ip  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_sys_client_ip  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmSysClientIpT. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_sys_client_ip  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmSysClientIpT数据信息
	 * @return 可用<IbmSysClientIpT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_sys_client_ip  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmSysClientIpT. class,sql);
	}
	/**
	 * 结果IP获取注册IP信息
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public IbmSysClientIpT findByIP(String ip) throws Exception {
		String sql = "SELECT * FROM ibm_sys_client_ip  where IP_ = ? and state_!='DEL' ";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(ip);
		return (IbmSysClientIpT) super.dao.findObject(IbmSysClientIpT.class,sql,parameters);
	}
}
