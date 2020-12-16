package com.ibm.old.v1.client.ibm_client_hm.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.client.ibm_client_hm.t.entity.IbmClientHmT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmClientHmTService extends BaseService {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmClientHmT对象数据
	 */
	public String save(IbmClientHmT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_client_hm的 IBM_CLIENT_HM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_client_hm set state_='DEL' where IBM_CLIENT_HM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CLIENT_HM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client_hm的 IBM_CLIENT_HM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_client_hm set state_='DEL' where IBM_CLIENT_HM_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_client_hm的 IBM_CLIENT_HM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_client_hm where IBM_CLIENT_HM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CLIENT_HM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client_hm的 IBM_CLIENT_HM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_client_hm where IBM_CLIENT_HM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmClientHmT实体信息
	 *
	 * @param entity IbmClientHmT实体
	 */
	public void update(IbmClientHmT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_client_hm表主键查找IbmClientHmT实体
	 *
	 * @param id ibm_client_hm 主键
	 * @return IbmClientHmT实体
	 */
	public IbmClientHmT find(String id) throws Exception {
		return (IbmClientHmT) this.dao.find(IbmClientHmT.class, id);

	}

	/**
	 * 获取分页Map数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页Map数据
	 */
	public PageCoreBean find(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_client_hm where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_hm  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmClientHmT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmClientHmT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_client_hm where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_hm  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmClientHmT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmClientHmT数据信息
	 *
	 * @return 可用<IbmClientHmT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmClientHmT.class, sql);
	}

	/**
	 * 找到用户的会员信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param handicapId       盘口id
	 * @param memberAccount    会员账号
	 * @return 条件的会员信息
	 */
	public IbmClientHmT findExist(String handicapMemberId, String handicapId, String memberAccount) throws Exception {
		String sql = "SELECT * FROM ibm_client_hm where HANDICAP_MEMBER_ID_ = ? and HANDICAP_ID_ = ? and "
				+ "MEMBER_ACCOUNT_ = ? and state_ !='DEL'";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(handicapMemberId);
		parameters.add(handicapId);
		parameters.add(memberAccount);
		return (IbmClientHmT) super.dao.findObject(IbmClientHmT.class, sql, parameters);
	}


	/**
	 * 根据盘口会员已存在id，会员账户 获取存在盘口会员信息
	 *
	 * @param existHmId     盘口会员已存在id
	 * @param memberAccount 会员账户
	 * @return 获取存在盘口会员信息
	 */
	public IbmClientHmT findExist(String existHmId, String memberAccount) throws Exception {
		String sql = "SELECT * FROM ibm_client_hm where CLIENT_EXIST_HM_ID_ = ? and MEMBER_ACCOUNT_ = ? and state_ != 'DEL'";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(existHmId);
		parameters.add(memberAccount);
		return (IbmClientHmT) super.dao.findObject(IbmClientHmT.class, sql, parameters);
	}

	/**
	 * 更新登陆状态
	 * @param clientHmId 客户端盘口会员Id
	 */
	public void updateLogin(String clientHmId) throws SQLException {
		String sql = "UPDATE `ibm_client_hm` SET STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? WHERE "
				+ " IBM_CLIENT_HM_ID_ = ? AND state_ != ?";
		List<Object> parameters = new ArrayList<>(5);
		parameters.add(IbmStateEnum.LOGIN.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(clientHmId);
		parameters.add(IbmStateEnum.DEL.name());
		super.dao.execute(sql,parameters);
	}

	/**
	 * 更新登陆状态
	 * @param existHmId 存在盘口会员Id
	 * @param state 状态
	 */
	public void updateLogin(String existHmId,IbmStateEnum state) throws SQLException {
		String sql = "UPDATE `ibm_client_hm` SET STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? WHERE "
				+ " CLIENT_EXIST_HM_ID_ = ? AND state_ != ?";
		List<Object> parameters = new ArrayList<>(5);
		parameters.add(state.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(existHmId);
		parameters.add(IbmStateEnum.DEL.name());
		super.dao.execute(sql,parameters);
	}

	/**
	 * 是否处于登陆状态
	 * @param existHmId 存在盘口会员Id
	 * @return 登陆 TRUE
	 */
	public boolean isLogin(String existHmId) throws SQLException {
		String sql = "SELECT STATE_ FROM `ibm_client_hm` WHERE CLIENT_EXIST_HM_ID_ = ? AND state_ = ?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(existHmId);
		parameters.add(IbmStateEnum.LOGIN.name());
		return ContainerTool.notEmpty(super.dao.findMapList(sql,parameters));
	}
}
