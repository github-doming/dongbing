package com.ibm.old.v1.client.ibm_client_exist_hm.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.client.ibm_client_exist_hm.t.entity.IbmClientExistHmT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmClientExistHmTService extends BaseService {


	/**
	 * 保存{ay_table_class}对象数据
	 * @param entity IbmClientExistHmT对象数据
	 */
	public String save(IbmClientExistHmT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_client_exist_hm的 IBM_CLIENT_EXIST_HM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_client_exist_hm set state_='DEL' where IBM_CLIENT_EXIST_HM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CLIENT_EXIST_HM_ID_主键id数组的数据
	 * @param idArray 要删除ibm_client_exist_hm的 IBM_CLIENT_EXIST_HM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_client_exist_hm set state_='DEL' where IBM_CLIENT_EXIST_HM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除ibm_client_exist_hm的 IBM_CLIENT_EXIST_HM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_client_exist_hm where IBM_CLIENT_EXIST_HM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CLIENT_EXIST_HM_ID_主键id数组的数据
	 * @param idArray 要删除ibm_client_exist_hm的 IBM_CLIENT_EXIST_HM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_client_exist_hm where IBM_CLIENT_EXIST_HM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmClientExistHmT实体信息
	 * @param entity IbmClientExistHmT实体
	 */
	public void update(IbmClientExistHmT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_client_exist_hm表主键查找IbmClientExistHmT实体
	 * @param id ibm_client_exist_hm 主键
	 * @return IbmClientExistHmT实体
	 */
	public IbmClientExistHmT find(String id) throws Exception {
		return (IbmClientExistHmT) this.dao.find(IbmClientExistHmT. class,id);

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
		String sqlCount = "SELECT count(*) FROM ibm_client_exist_hm where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_exist_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_exist_hm  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmClientExistHmT数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页IbmClientExistHmT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_client_exist_hm where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_exist_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_exist_hm  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmClientExistHmT. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_exist_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmClientExistHmT数据信息
	 * @return 可用<IbmClientExistHmT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_exist_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmClientExistHmT. class,sql);
	}

	/**
	 * 获取已存在的盘口会员
	 *
	 * @param handicapCode 盘口code
	 * @return 已存在的盘口会员数量信息 <br>
	 * exitsData    存在信息<br>
	 * exitsCount 存在数量 <br>
	 * handicapExitsCount 盘口存在数量
	 */
	public Map<String, Object> findExitsCount(String handicapCode) throws SQLException {
		Map<String, Object> exitsCount = new HashMap<>(2);
		String sql = "SELECT %s FROM ibm_client_exist_hm  where state_ = ? ";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(IbmStateEnum.OPEN.name());
		//查询出已开启的盘口会员数量
		List<Map<String, Object>> exitsData = super.dao
				.findMapList(String.format(sql, " IBM_CLIENT_EXIST_HM_ID_ as CLIENT_EXIST_HM_ID_,HANDICAP_MEMBER_ID_ "),
						parameters);
		exitsCount.put("exitsData", exitsData);
		exitsCount.put("exitsCount", exitsData == null ? 0 : exitsData.size());

		sql += " and HANDICAP_CODE_ = ? ";
		parameters.add(handicapCode);
		//查询出已开启的盘口中会员数量
		String cnt = super.dao.findString("cnt", String.format(sql, "count(*)  cnt"), parameters);
		exitsCount.put("handicapExitsCount", cnt);
		return exitsCount;
	}

	/**
	 * 判断该服务中是否开启某盘口会员
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 包含  IBM_CLIENT_EXIST_HM_ID_
	 */
	public String findExits(String handicapMemberId) throws SQLException {
		String sql = "SELECT IBM_CLIENT_EXIST_HM_ID_ FROM ibm_client_exist_hm where HANDICAP_MEMBER_ID_ = ? and state_ = ?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(handicapMemberId);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("IBM_CLIENT_EXIST_HM_ID_", sql, parameters);

	}

	/**
	 * 关闭盘口会员的客户机
	 * @param clientExistHmId  客户端已存投注信息主键
	 * @return  关闭结果 true 成功
	 */
	public boolean closeExistHm(String clientExistHmId) throws SQLException {
		String sql = "update ibm_client_exist_hm set state_= 'DEL' where IBM_CLIENT_EXIST_HM_ID_ = ?";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(clientExistHmId);
		int col = dao.execute(sql, parameters);
		if (col == 1){
			sql = "update ibm_client_hm set state_='DEL' where CLIENT_EXIST_HM_ID_ = ?";
			dao.execute(sql, parameters);
			sql = "update ibm_client_hm_game_set set state_='DEL'  where CLIENT_EXIST_HM_ID_ = ? ";
			dao.execute(sql, parameters);
			sql = "update ibm_client_exist_bet set state_='DEL'  where CLIENT_EXIST_HM_ID_ = ? ";
			dao.execute(sql, parameters);
			sql = "update ibm_client_bet set state_='DEL'  where CLIENT_EXIST_HM_ID_ = ? ";
			dao.execute(sql, parameters);
			sql = "update ibm_client_hm_info set state_='DEL'  where CLIENT_EXIST_HM_ID_ = ? ";
			dao.execute(sql, parameters);
			return true;
		}
		return false;

	}

	/**
	 * 获取盘口会员id
	 * @param existHmId 已存在盘口会员id
	 * @return 盘口会员id
	 */
	public String findHmId(String existHmId) throws SQLException {
		String sql = "SELECT HANDICAP_MEMBER_ID_ FROM ibm_client_exist_hm WHERE IBM_CLIENT_EXIST_HM_ID_ = ? AND STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(existHmId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("HANDICAP_MEMBER_ID_",sql,parameterList);
	}
}
