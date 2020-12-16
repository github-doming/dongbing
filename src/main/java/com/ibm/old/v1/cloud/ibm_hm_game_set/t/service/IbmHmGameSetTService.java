package com.ibm.old.v1.cloud.ibm_hm_game_set.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.old.v1.app.ibm_hm_set.action.IbmHandicapEditAction;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.entity.IbmHmGameSetT;
import com.ibm.old.v1.common.doming.core.BaseServicePlus;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmHmGameSetTService extends BaseServicePlus {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmHmGameSetT对象数据
	 */
	public String save(IbmHmGameSetT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_hm_game_set的 IBM_HM_GAME_SET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_hm_game_set set state_='DEL' where IBM_HM_GAME_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_HM_GAME_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_hm_game_set的 IBM_HM_GAME_SET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibm_hm_game_set set state_='DEL' where IBM_HM_GAME_SET_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}
	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_hm_game_set的 IBM_HM_GAME_SET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_hm_game_set where IBM_HM_GAME_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 根据游戏id和handicapId获取盘口游戏设置
	 *
	 * @param gameId   游戏id
	 * @param handicapMemberId  会员id
	 * @return   盘口游戏设置
	 */
	public IbmHmGameSetT finHmGameSetById(String handicapMemberId, String gameId) throws Exception {
		String sql = "SELECT * FROM `ibm_hm_game_set` where "
				+ " HANDICAP_MEMBER_ID_ = ? and GAME_ID_ = ?  and STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapMemberId);
		parameterList.add(gameId);
		parameterList.add(IbmStateEnum.DEL.name());
		return (IbmHmGameSetT)super.dao.findObject(IbmHmGameSetT.class, sql, parameterList);
	}

	/**
	 * 物理删除IBM_HM_GAME_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_hm_game_set的 IBM_HM_GAME_SET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_hm_game_set where IBM_HM_GAME_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 查找出用户盘口游戏设置主键
	 *
	 * @param handicapMemberId 盘口会员主键id
	 * @param userId           用户id
	 * @param handicapGameId   盘口游戏id
	 * @return 用户方案信息
	 */
	public String findHmameSetIdHmId(String handicapMemberId, String userId, String handicapGameId)
			throws SQLException {
		String sql = "SELECT IBM_HM_GAME_SET_ID_ FROM `ibm_hm_game_set` where "
				+ " APP_USER_ID_ = ? and HANDICAP_MEMBER_ID_ = ? and HANDICAP_GAME_ID_ =? and STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(userId);
		parameterList.add(handicapMemberId);
		parameterList.add(handicapGameId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("IBM_HM_GAME_SET_ID_", sql, parameterList);
	}

	/**
	 * 查找显示项
	 *
	 * @param hmGameSetId 盘口游戏设置主键id
	 * @return 盘口设置显示项
	 */
	public Map<String, Object> findShow(String hmGameSetId) throws SQLException {
		String sql = "select * from ibm_hm_game_set where IBM_HM_GAME_SET_ID_=? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(hmGameSetId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
	}
	/**
	 * 更新IbmHmGameSetT实体信息
	 *
	 * @param entity IbmHmGameSetT实体
	 */
	public boolean update(IbmHmGameSetT entity) throws Exception {
		return dao.update(entity)==1;
	}

	/**
	 * 根据ibm_hm_game_set表主键查找IbmHmGameSetT实体
	 *
	 * @param id ibm_hm_game_set 主键
	 * @return IbmHmGameSetT实体
	 */
	public IbmHmGameSetT find(String id) throws Exception {
		return (IbmHmGameSetT) this.dao.find(IbmHmGameSetT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_hm_game_set where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_hm_game_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_hm_game_set  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmHmGameSetT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmHmGameSetT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_hm_game_set where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_hm_game_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_hm_game_set  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmHmGameSetT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_hm_game_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmHmGameSetT数据信息
	 *
	 * @return 可用<IbmHmGameSetT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_hm_game_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmHmGameSetT.class, sql);

	}

	/**
	 * 根据盘口会员id，盘口游戏id获取盘口会员游戏设置
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param handicapGameId   盘口游戏id
	 * @return 盘口会员游戏设置
	 */
	public IbmHmGameSetT find(String handicapMemberId, String handicapGameId) throws Exception {
		String sql = "SELECT * FROM ibm_hm_game_set  where HANDICAP_MEMBER_ID_ = ? and HANDICAP_GAME_ID_ = ? and state_!='DEL' ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(handicapMemberId);
		parameters.add(handicapGameId);
		return (IbmHmGameSetT) super.dao.findObject(IbmHmGameSetT.class, sql, parameters);

	}

	/**
	 * 获取开启的项目
	 *
	 * @param gameId     游戏id
	 * @param planId     方案id
	 * @param handicapId 盘口id
	 * @return 开启详情表id
	 */
	public Map<String, String> mapOpenItemId(String gameId, String planId, String handicapId) throws SQLException {
		String sql = "SELECT iph.PLAN_ITEM_TABLE_ID_ AS key_,ihg.HANDICAP_MEMBER_ID_ AS value_ FROM `ibm_hm_game_set` "
				+ " ihg LEFT JOIN ibm_handicap_member ihm ON ihg.HANDICAP_MEMBER_ID_ = ihm.IBM_HANDICAP_MEMBER_ID_"
				+ " LEFT JOIN IBM_PLAN_HM iph ON iph.HANDICAP_MEMBER_ID_ = ihg.HANDICAP_MEMBER_ID_ WHERE ihg.BET_STATE_ = ? "
				+ " AND ihg.HANDICAP_ID_ = ? AND ihg.GAME_ID_ = ?  AND iph.PLAN_ID_ = ? AND ihm.LOGIN_STATE_ = ? AND iph.STATE_ = ?";
		List<Object> parameters = new ArrayList<>(6);
		parameters.add(IbmTypeEnum.TRUE.name());
		parameters.add(handicapId);
		parameters.add(gameId);
		parameters.add(planId);
		parameters.add(IbmStateEnum.LOGIN.name());
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findKVsMap(sql, parameters);
	}

	/**
	 * 自动停止，自动停止设置在5分钟内的开启投注的用户
	 */
	public List<Map<String, Object>> autoStop() throws SQLException {
		return autoBetState("BET_AUTO_STOP_", IbmTypeEnum.TRUE, IbmTypeEnum.FALSE);
	}

	/**
	 * 自动开始，自动开始设置在5分钟内的停止投注的用户
	 */
	public List<Map<String, Object>> autoStart() throws SQLException {
		return autoBetState("BET_AUTO_START_", IbmTypeEnum.FALSE, IbmTypeEnum.TRUE);
	}
	/**
	 * 更新自动开始或停止状态
	 *
	 * @param key       更新类型
	 * @param typeBegin 开始类型
	 * @param typeEnd   更新类型
	 * @return 盘口会员信息
	 */
	private List<Map<String, Object>> autoBetState(String key, IbmTypeEnum typeBegin, IbmTypeEnum typeEnd)
			throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT IBM_HM_GAME_SET_ID_, HANDICAP_MEMBER_ID_, GAME_ID_");
		sql.append(" FROM ibm_hm_game_set WHERE BET_STATE_ = ? AND ").append(key).append(" = ? AND ").append(key)
				.append("TIME_ BETWEEN DATE_SUB(curtime(), INTERVAL 3 MINUTE) AND curtime() ")
				.append("AND STATE_ != ? AND HANDICAP_MEMBER_ID_ IN ( SELECT HANDICAP_MEMBER_ID_ FROM ")
				.append(" ibm_handicap_member WHERE LOGIN_STATE_ = ? AND STATE_ != ?)");
		List<Object> parameters = new ArrayList<>(5);
		parameters.add(typeBegin.name());
		parameters.add(IbmTypeEnum.TRUE.name());
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(IbmStateEnum.LOGIN.name());
		parameters.add(IbmStateEnum.DEL.name());

		List<Map<String, Object>> autoStartList = super.dao.findMapList(sql.toString(), parameters);
		if (ContainerTool.isEmpty(autoStartList)) {
			return null;
		}
		//获取游戏设置
		List<String> hmGameSetIdList = new ArrayList<>(autoStartList.size());
		for (Map<String, Object> autoStartInfo : autoStartList) {
			hmGameSetIdList.add(autoStartInfo.remove("IBM_HM_GAME_SET_ID_").toString());
		}
		parameters.clear();
		//更新投注状态
		sql.setLength(0);
		sql.append("UPDATE ibm_hm_game_set SET BET_STATE_ = ? WHERE IBM_HM_GAME_SET_ID_ IN ( ");
		parameters.add(typeEnd.name());
		for (String hmGameSetId : hmGameSetIdList) {
			sql.append("?,");
			parameters.add(hmGameSetId);
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		super.dao.execute(sql.toString(), parameters);
		return autoStartList;
	}

	/**
	 * 自动调整新增情况
	 */
	public void autoIncrease() throws SQLException {
		String sql = "UPDATE ibm_hm_game_set SET INCREASE_STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? WHERE "
				+ " INCREASE_STATE_ = ? AND INCREASE_STOP_TIME_ BETWEEN DATE_SUB(curtime(), INTERVAL 3 MINUTE) AND curtime()";
		List<Object> parameters = new ArrayList<>(5);
		parameters.add(IbmStateEnum.STOP.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(IbmStateEnum.AUTO.name());
		super.dao.execute(sql, parameters);
	}

	/**
	 * @return 盘口游戏设置默认数据
	 * @Description: 获取盘口游戏设置默认数据实体类
	 * <p>
	 * 参数说明
	 */
	public IbmHmGameSetT findDefEntity() throws Exception {
		String sql = "SELECT * from ibm_hm_game_set where STATE_ = ? limit 1";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbmStateEnum.DEF.name());
		return (IbmHmGameSetT) super.dao.findObject(IbmHmGameSetT.class, sql, parameterList);
	}

	/**
	 * 获取游戏投注配置信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param gameId           游戏id
	 * @return 投注状态信息
	 */
	public Map<String, Object> findGameSetInfo(String handicapMemberId, String gameId) throws SQLException {
		String sql = "SELECT ihs.GAME_ID_, ihg.GAME_CODE_, ihs.BET_STATE_, ihg.SEAL_TIME_, ihs.BET_SECOND_, "
				+ " ihs.SPLIT_TWO_SIDE_AMOUNT_, ihs.SPLIT_NUMBER_AMOUNT_ FROM `ibm_hm_game_set` ihs "
				+ " LEFT JOIN ibm_handicap_game ihg ON ihs.HANDICAP_GAME_ID_ = ihg.IBM_HANDICAP_GAME_ID_ "
				+ " WHERE ihs.HANDICAP_MEMBER_ID_ = ? AND ihs.GAME_ID_ = ? AND ihs.state_ != ? AND ihg.state_ != ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(handicapMemberId);
		parameterList.add(gameId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 获取游戏投注设置详情
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param gameId           游戏id
	 * @return 游戏投注设置详情
	 */
	public Map<String, Object> findGameSetDetail(String handicapMemberId, String gameId) throws SQLException {
		String sql = "SELECT ihs.GAME_ID_, ihg.SEAL_TIME_, ihs.BET_SECOND_, "
				+ " ihs.SPLIT_TWO_SIDE_AMOUNT_, ihs.SPLIT_NUMBER_AMOUNT_ FROM `ibm_hm_game_set` ihs "
				+ " LEFT JOIN ibm_handicap_game ihg ON ihs.HANDICAP_GAME_ID_ = ihg.IBM_HANDICAP_GAME_ID_ "
				+ " WHERE ihs.HANDICAP_MEMBER_ID_ = ? AND ihs.GAME_ID_ = ? AND ihs.state_ != ? AND ihg.state_ != ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(handicapMemberId);
		parameterList.add(gameId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 获取盘口会员已存在的游戏id列表
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 游戏id列表
	 */
	public List<String> listHmGameId(String handicapMemberId) throws SQLException {
		String sql = "select GAME_ID_ from ibm_hm_game_set where HANDICAP_MEMBER_ID_=? and STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findStringList("GAME_ID_", sql, parameterList);

	}

	/**
	 * 更新投注状态
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param nowTime          更新时间
	 */
	public void updateBetState(String handicapMemberId, Date nowTime,String className) throws SQLException {
		String sql = "UPDATE ibm_hm_game_set SET BET_STATE_ =?,DESC_=? ,UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? "
				+ " WHERE HANDICAP_MEMBER_ID_ = ? AND BET_STATE_ = ? AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbmTypeEnum.FALSE.name());
		parameterList.add(className+"更新投注状态");
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(handicapMemberId);
		parameterList.add(IbmTypeEnum.TRUE.name());
		parameterList.add(IbmStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 获取 盘口会员投注模式 map
	 *
	 * @param handicapMemberIds 盘口会员id 集合
	 * @return 盘口会员投注模式  map
	 */
	public Map<Object, Object> mapHmBetMode(Set<Object> handicapMemberIds, String gameId) throws SQLException {
		StringBuilder sql = new StringBuilder("select HANDICAP_MEMBER_ID_ as key_, BET_MODE_ as value_ from ");
		sql.append(" ibm_hm_game_set where GAME_ID_ = ? AND BET_STATE_ = ? AND STATE_= ? and HANDICAP_MEMBER_ID_ in (");
		List<Object> parameterList = new ArrayList<>(handicapMemberIds.size() + 2);
		parameterList.add(gameId);
		parameterList.add(IbmTypeEnum.TRUE.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		for (Object handicapMemberId : handicapMemberIds) {
			sql.append("?,");
			parameterList.add(handicapMemberId);
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(") ");
		return super.findKVMap(sql.toString(), parameterList);
	}
	/**
	 * 根据id查找盘口会员设置信息
	 *
	 * @param handicapMemberId 盘口会员ID
	 * @param gameId           游戏ID
	 * @return 盘口会员设置信息
	 */
	public Map<String, Object> findInfoById(String handicapMemberId, String gameId)
			throws SQLException {
		String sql = "SELECT BET_AUTO_STOP_,BET_AUTO_STOP_TIME_,BET_AUTO_START_,BET_AUTO_START_TIME_,INCREASE_STATE_,"
				+ " INCREASE_STOP_TIME_,BET_SECOND_,SPLIT_NUMBER_AMOUNT_,SPLIT_TWO_SIDE_AMOUNT_ FROM `ibm_hm_game_set` "
				+ " where HANDICAP_MEMBER_ID_ = ? and GAME_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(gameId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
	}
	/**
	 * 修改投注模式
	 *
	 * @param betMode          投注模式
	 * @param gameId           游戏id
	 * @param handicapMemberId 盘口会员ID
	 * @return 修改结果
	 */
	public boolean updateBetModel(String betMode, String gameId, String handicapMemberId) throws SQLException {
		String sql = "update ibm_hm_game_set set BET_MODE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_=?,DESC_ = ?"
				+ " where HANDICAP_MEMBER_ID_ = ? and GAME_ID_ = ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(betMode);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(IbmHandicapEditAction.class.getName() + "修改投注模式");
		parameterList.add(handicapMemberId);
		parameterList.add(gameId);
		return super.dao.execute(sql, parameterList) == 1;
	}
	/**
	 * 通过盘口会员id删除所有盘口游戏设置
	 * @param handicapMemberList			盘口会员id
	 */
	public void delByHmId(List<String> handicapMemberList) throws SQLException {
		StringBuilder sql=new StringBuilder();
		sql.append("update ibm_hm_game_set set STATE_='DEL',UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where HANDICAP_MEMBER_ID_ in (");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		for(String handicapMemberId:handicapMemberList){
			sql.append("?,");
			parameterList.add(handicapMemberId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(),parameterList);
	}
}
