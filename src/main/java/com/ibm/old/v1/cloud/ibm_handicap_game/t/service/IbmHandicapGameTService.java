package com.ibm.old.v1.cloud.ibm_handicap_game.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_handicap_game.t.entity.IbmHandicapGameT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmHandicapGameTService extends BaseService {



	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmHandicapGameT对象数据
	 */
	public String save(IbmHandicapGameT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_handicap_game的 IBM_HANDICAP_GAME_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_handicap_game set state_='DEL' where IBM_HANDICAP_GAME_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_HANDICAP_GAME_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_handicap_game的 IBM_HANDICAP_GAME_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_handicap_game set state_='DEL' where IBM_HANDICAP_GAME_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_handicap_game的 IBM_HANDICAP_GAME_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_handicap_game where IBM_HANDICAP_GAME_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_HANDICAP_GAME_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_handicap_game的 IBM_HANDICAP_GAME_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibm_handicap_game where IBM_HANDICAP_GAME_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmHandicapGameT实体信息
	 *
	 * @param entity IbmHandicapGameT实体
	 */
	public void update(IbmHandicapGameT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_handicap_game表主键查找IbmHandicapGameT实体
	 *
	 * @param id ibm_handicap_game 主键
	 * @return IbmHandicapGameT实体
	 */
	public IbmHandicapGameT find(String id) throws Exception {
		return (IbmHandicapGameT) this.dao.find(IbmHandicapGameT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_handicap_game where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_handicap_game  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_handicap_game  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmHandicapGameT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmHandicapGameT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_handicap_game where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_handicap_game  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_handicap_game  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmHandicapGameT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_handicap_game  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmHandicapGameT数据信息
	 *
	 * @return 可用<IbmHandicapGameT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_handicap_game  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmHandicapGameT.class, sql);
	}

	/**
	 * 根据盘口code,游戏code获取盘口游戏对象
	 *
	 * @param handicapCode 盘口code
	 * @param gameCode     游戏code
	 * @return 盘口游戏对象
	 */
	public IbmHandicapGameT findByCode(String handicapCode, String gameCode) throws Exception {
		String sql = "SELECT * FROM ibm_handicap_game  where HANDICAP_CODE_ = ? and GAME_CODE_ = ? and state_ !='DEL'";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapCode);
		parameterList.add(gameCode);
		Object obj = super.dao.findObject(IbmHandicapGameT.class, sql, parameterList);
		return obj == null ? null : (IbmHandicapGameT) obj;
	}

	/**
	 * 通过盘口id和游戏id获取盘口游戏信息
	 * @param handicapId 盘口id
	 * @param gameCode 游戏code
	 * @return 盘口游戏信息
	 */
	public String find(String handicapId, String gameCode) throws Exception {
		String sql="select IBM_HANDICAP_GAME_ID_ from ibm_handicap_game where HANDICAP_ID_=? and GAME_CODE_=? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapId);
		parameterList.add(gameCode);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("IBM_HANDICAP_GAME_ID_",sql,parameterList);
	}

	/**
	 * 根据盘口id获取游戏id
	 * @param handicapId 盘口id
	 * @return 游戏id
	 */
	public List<String> listGameId(String handicapId) throws SQLException {
		String sql = "SELECT GAME_ID_ FROM ibm_handicap_game WHERE HANDICAP_ID_ = ? AND STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findStringList("GAME_ID_", sql, parameterList);
	}

	/**
	 * 
	 * @Description: 根据盘口id逻辑删除
	 *
	 * 参数说明 
	 * @param handicapId 盘口id
	 * 返回类型 void
	 */
	public void delByHandicapId(String handicapId ) throws SQLException {
		String sql = "update ibm_handicap_game set state_= ? ,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where HANDICAP_ID_ = ? ";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(handicapId);
		dao.execute(sql, parameterList);
	}

	/**
	 * 
	 * @Description: 根据盘口id逻辑批量删除
	 *
	 * 参数说明 
	 * @param idArray 盘口id数组
	 * 返回类型 void
	 */
	public void delAllByHandicapId(String[] idArray ) throws SQLException {
		if (idArray != null) {
			StringBuilder sql = new StringBuilder("update ibm_handicap_game set state_= ?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where HANDICAP_ID_ in(");
			List<Object> parameterList = new ArrayList<>(2);
			parameterList.add(IbmStateEnum.DEL.name());
			parameterList.add(new Date());
			parameterList.add(System.currentTimeMillis());
			for (String id : idArray) {
				sql.append("?,");
				parameterList.add(id);
			}
			sql.replace(sql.length()-1, sql.length(), ")");
			dao.execute(sql.toString(), parameterList);
		}
	}

	/**
	 * 根据盘口会员id获取盘口游戏信息
	 * @param handicapMemberId 盘口会员id
	 * @return 盘口游戏信息
	 */
	public List<Map<String, Object>> listInfo4InitGameSet(String handicapMemberId) throws SQLException {
		String sql = "SELECT ihm.HANDICAP_ID_,ihm.APP_USER_ID_,ihg.IBM_HANDICAP_GAME_ID_,ihg.GAME_ID_,ihg.DESC_ "
				+ " FROM ibm_handicap_game ihg LEFT JOIN ibm_handicap_member ihm ON ihg.HANDICAP_ID_ = ihm.HANDICAP_ID_ "
				+ " WHERE IBM_HANDICAP_MEMBER_ID_ = ? AND ihm.STATE_ != ? AND ihg.STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMapList(sql,parameterList);
	}

	/**
	 *
	 * @Description: 获取封盘时间
	 *
	 * 参数说明
	 * @param gameCode 游戏code
	 * @param handicapId 盘口code
	 * @return 封盘时间
	 */
	public String findSealTimeById(String gameCode, String handicapId) throws SQLException {
		String sql = "SELECT SEAL_TIME_ FROM ibm_handicap_game where STATE_ != ? AND GAME_CODE_ = ? AND HANDICAP_ID_ = ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(gameCode);
		parameterList.add(handicapId);
		return super.dao.findString("SEAL_TIME_", sql, parameterList);
	}
	/**
	 * 根据游戏code和盘口code获取投注执行表名
	 * @param gameCode 游戏code
	 * @param handicapCode 盘口code
	 * @return 投注执行表名
	 */
	public String findSubIebiTableName(String gameCode, String handicapCode) throws SQLException {
		String sql="select SUB_IEBI_TABLE_NAME_ from ibm_handicap_game where HANDICAP_CODE_=? and GAME_CODE_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapCode);
		parameterList.add(gameCode);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("SUB_IEBI_TABLE_NAME_",sql,parameterList);
	}
}
