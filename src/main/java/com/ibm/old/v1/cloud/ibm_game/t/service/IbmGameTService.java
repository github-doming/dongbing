package com.ibm.old.v1.cloud.ibm_game.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_game.t.entity.IbmGameT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmGameTService extends BaseService {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmGameT对象数据
	 */
	public String save(IbmGameT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_game的 IBM_GAME_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_game set state_='DEL' where IBM_GAME_ID_=?" ;
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_GAME_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_game的 IBM_GAME_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_game set state_='DEL' where IBM_GAME_ID_ in(" + stringBuilder.toString() + ")" ;
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_game的 IBM_GAME_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_game where IBM_GAME_ID_=?" ;
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_GAME_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_game的 IBM_GAME_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_game where IBM_GAME_ID_ in(" + stringBuilder.toString() + ")" ;
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmGameT实体信息
	 *
	 * @param entity IbmGameT实体
	 */
	public void update(IbmGameT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_game表主键查找IbmGameT实体
	 *
	 * @param id ibm_game 主键
	 * @return IbmGameT实体
	 */
	public IbmGameT find(String id) throws Exception {
		return (IbmGameT) this.dao.find(IbmGameT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_game where state_!='DEL'" ;
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_game  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		} else {
			sql = "SELECT * FROM ibm_game  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmGameT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmGameT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_game where state_!='DEL'" ;
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_game  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		} else {
			sql = "SELECT * FROM ibm_game  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmGameT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_game  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmGameT数据信息
	 *
	 * @return 可用<IbmGameT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_game  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		return this.dao.findObjectList(IbmGameT.class, sql);
	}

	/**
	 * 根据游戏编码查找可用IbmGameT数据信息
	 *
	 * @param gameCode 游戏编码
	 * @return 可用<IbmGameT>数据信息
	 */
	public IbmGameT findByCode(String gameCode) throws Exception {
		String sql = "SELECT * FROM ibm_game  where GAME_CODE_ = ? and state_!='DEL' " ;
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(gameCode);
		Object obj = super.dao.findObject(IbmGameT.class, sql, parameterList);
		return obj == null ? null : (IbmGameT) obj;
	}

	/**
	 * 根据游戏编码查找可用GameID数据
	 *
	 * @param gameCode 游戏编码
	 * @return 可用GameID数据
	 */
	public String findId(String gameCode) throws Exception {
		String sql = "SELECT IBM_GAME_ID_ FROM ibm_game  where GAME_CODE_ = ? and state_!='DEL' " ;
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(gameCode);
		return super.dao.findString("IBM_GAME_ID_", sql, parameterList);
	}
	/**
	 * 根据可用GameID数据 查找 游戏编码
	 *
	 * @param gameId 可用GameID数据
	 * @return 游戏编码
	 */
	public String findCode(String gameId) throws SQLException {
		String sql = "SELECT GAME_CODE_ FROM ibm_game  where IBM_GAME_ID_ = ? and state_!='DEL' " ;
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(gameId);
		return super.dao.findString("GAME_CODE_", sql, parameterList);
	}

	/**
	 * 获取游戏基本信息
	 *
	 * @return 游戏基本信息
	 */
	public List<Map<String, Object>> listInfo() throws SQLException {
		String sql = "SELECT IBM_GAME_ID_,GAME_NAME_,GAME_CODE_,ICON_,SN_ FROM ibm_game  where state_ = ?  ORDER BY SN_" ;
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * @return 所有游戏ID和名称
	 * @Description: 查询所有游戏ID和名称
	 * <p>
	 * 参数说明
	 */
	public List<Map<String, Object>> findAllGame() throws SQLException {
		String sql = "SELECT IBM_GAME_ID_,GAME_NAME_ from ibm_game where STATE_ != ? " ;
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * @param gameIds 游戏id数组
	 * @return 游戏信息
	 * 返回类型 List<Map<String,Object>>
	 * @Description: 通过id数组获取游戏信息
	 * <p>
	 * 参数说明
	 */
	public List<Map<String, Object>> listByIds(String[] gameIds) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT * FROM ibm_game WHERE STATE_ != ? AND IBM_GAME_ID_ in (");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbmStateEnum.DEL.name());
		for (String gameId : gameIds) {
			sql.append("?,");
			parameterList.add(gameId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.dao.findMapList(sql.toString(), parameterList);
	}

	/**
	 * 查询游戏开奖表名
	 *
	 * @param gameCode 游戏code
	 * @return 游戏开奖表名
	 */
	public String findTableName(String gameCode) throws SQLException {
		String sql = "SELECT REP_DRAW_TABLE_NAME_ FROM ibm_game WHERE GAME_CODE_ = ? AND STATE_ != ? " ;
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(gameCode);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("REP_DRAW_TABLE_NAME_", sql, parameterList);
	}

	/**
	 * 获取开奖信息
	 *
	 * @param sql    开奖信息sql语句
	 * @param period 期数
	 * @return 开奖信息
	 */
	public Map<String, Object> findDrawInfo(String sql, Object period) throws Exception {
		super.doTransactionPost();
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(period.toString());
		parameterList.add(IbmStateEnum.OPEN.name());
		Map<String, Object> map = super.dao.findMap(sql, parameterList);
		if (ContainerTool.isEmpty(map)) {
			return null;
		}
		//返回结果
		Map<String, Object> drawMap = new HashMap<>(2);
		drawMap.put("DRAW_NUMBER_", map.get("DRAW_NUMBER_"));
		map.remove("DRAW_NUMBER_");
		map.remove("ROW_NUM");
		drawMap.put("DRAW_ITEMS_", map.values());
		return drawMap;

	}
	/**
	 * 获取开奖信息
	 *
	 * @param sql     开奖信息sql语句
	 * @param periods 期数数组
	 * @return 开奖信息
	 */
	public Map<Object, Map<String, Object>> findDrawInfoMap(String sql, Object[] periods) throws SQLException {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbmStateEnum.OPEN.name());
		for (Object period : periods) {
			sqlBuilder.append("?,");
			parameterList.add(period.toString());
		}
		sqlBuilder.replace(sqlBuilder.length() - 1, sqlBuilder.length(), ") ORDER BY PERIOD_");

		List<Map<String, Object>> list = super.dao.findMapList(sql.concat(sqlBuilder.toString()), parameterList);
		if (ContainerTool.isEmpty(list)) {
			return null;
		}
		Map<Object, Map<String, Object>> drawMap = new HashMap<>(periods.length);
		Map<String, Object> draw;
		for (Map<String, Object> map : list) {
			draw = new HashMap<>(3);
			map.remove("ROW_NUM");
			String period = map.get("PERIOD_").toString();
			map.remove("PERIOD_");
			draw.put("DRAW_NUMBER_", map.get("DRAW_NUMBER_"));
			map.remove("DRAW_NUMBER_");
			draw.put("DRAW_TIME_", map.get("DRAW_TIME_"));
			map.remove("DRAW_TIME_");
			draw.put("DRAW_ITEMS_", map.values());
			drawMap.put(period, draw);
		}
		return drawMap;
	}
}
