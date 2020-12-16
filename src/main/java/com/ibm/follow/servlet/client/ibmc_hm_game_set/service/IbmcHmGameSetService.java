package com.ibm.follow.servlet.client.ibmc_hm_game_set.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.client.ibmc_hm_game_set.entity.IbmcHmGameSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmcHmGameSetService extends BaseServicePlus {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmcHmGameSet对象数据
	 */
	public String save(IbmcHmGameSet entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibmc_hm_game_set的 IBMC_HM_GAME_SET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibmc_hm_game_set set state_='DEL' where IBMC_HM_GAME_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBMC_HM_GAME_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_hm_game_set的 IBMC_HM_GAME_SET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibmc_hm_game_set set state_='DEL' where IBMC_HM_GAME_SET_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibmc_hm_game_set的 IBMC_HM_GAME_SET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibmc_hm_game_set where IBMC_HM_GAME_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBMC_HM_GAME_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_hm_game_set的 IBMC_HM_GAME_SET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibmc_hm_game_set where IBMC_HM_GAME_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmcHmGameSet实体信息
	 *
	 * @param entity IbmcHmGameSet实体
	 */
	public void update(IbmcHmGameSet entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibmc_hm_game_set表主键查找IbmcHmGameSet实体
	 *
	 * @param id ibmc_hm_game_set 主键
	 * @return IbmcHmGameSet实体
	 */
	public IbmcHmGameSet find(String id) throws Exception {
		return (IbmcHmGameSet) this.dao.find(IbmcHmGameSet.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibmc_hm_game_set where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_hm_game_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_hm_game_set  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmcHmGameSet数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmcHmGameSet数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibmc_hm_game_set where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_hm_game_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_hm_game_set  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmcHmGameSet.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibmc_hm_game_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmcHmGameSet数据信息
	 *
	 * @return 可用<IbmcHmGameSet>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibmc_hm_game_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmcHmGameSet.class, sql);
	}
	/**
	 * 获取盘口会员游戏设置信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param gameCode  游戏code
	 * @return 游戏设置信息
	 */
	public IbmcHmGameSet findExist(String existHmId, String gameCode) throws Exception {
		String sql = "select * from ibmc_hm_game_set where EXIST_HM_ID_=? and GAME_CODE_=? and STATE_=?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(existHmId);
		parameters.add(gameCode);
		parameters.add(IbmStateEnum.OPEN.name());
		return (IbmcHmGameSet) super.dao.findObject(IbmcHmGameSet.class, sql, parameters);
	}

	/**
	 * 获取配置信息
	 *
	 * @param existHmId 存在盘口会员主键
	 * @param gameCode  游戏编码
	 * @return 配置信息
	 */
	public Map<String, Object> findInfo(String existHmId, String gameCode) throws SQLException {
		String sql = "select BET_STATE_,BET_SECOND_ from ibmc_hm_game_set where EXIST_HM_ID_ = ? and GAME_CODE_ = ? and STATE_ = ?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(existHmId);
		parameters.add(gameCode);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameters);
	}

	/**
	 * 获取配置信息
	 *
	 * @param existHmId 存在盘口会员主键
	 * @param gameCode  游戏编码
	 * @return 配置信息
	 */
	public String findId(String existHmId, String gameCode) throws SQLException {
		String sql = "select IBMC_HM_GAME_SET_ID_ from ibmc_hm_game_set where EXIST_HM_ID_ = ? and GAME_CODE_ = ? and STATE_ = ?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(existHmId);
		parameters.add(gameCode);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("IBMC_HM_GAME_SET_ID_", sql, parameters);
	}
	/**
	 * 修改游戏限额
	 *
	 * @param hmGameSetId 盘口会员游戏设置id
	 * @param quotaInfo   游戏限额信息
	 */
	public void updateGameLimit(String hmGameSetId, String quotaInfo) throws SQLException {
		String sql = "update ibmc_hm_game_set set BET_LIMIT_=? where IBMC_HM_GAME_SET_ID_=?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(quotaInfo);
		parameters.add(hmGameSetId);
		super.dao.execute(sql, parameters);
	}
	/**
	 * 获取投注限额设置
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param gameCode  游戏code
	 * @return 投注限额设置
	 */
	public Map<String, Object> findLimit(String existHmId, GameUtil.Code gameCode) throws SQLException {
		String sql = "select SPLIT_TWO_SIDE_AMOUNT_,SPLIT_NUMBER_AMOUNT_,BET_LIMIT_ from ibmc_hm_game_set "
				+ " where EXIST_HM_ID_=? and GAME_CODE_=? and STATE_=?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(existHmId);
		parameters.add(gameCode.name());
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameters);
	}

	/**
	 * 更新盘口会员游戏设置信息
	 *
	 * @param hmGameSetId        盘口会员游戏设置主键
	 * @param betState           投注状态
	 * @param betSecond          投注时间
	 * @param splitTwoSideAmount 双面分投额度
	 * @param splitNumberAmount  号码分投额度
	 * @param nowTime            更新时间
	 */
	public void update(String hmGameSetId, String betState, Integer betSecond, Integer splitTwoSideAmount,
			Integer splitNumberAmount, Date nowTime) throws SQLException {
		String sql = "UPDATE ibmc_hm_game_set SET BET_STATE_ = ?,BET_SECOND_ = ?,SPLIT_TWO_SIDE_AMOUNT_ = ?, "
				+ " SPLIT_NUMBER_AMOUNT_ = ?,UPDATE_TIME_= ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? WHERE IBMC_HM_GAME_SET_ID_ = ? AND STATE_ = ?";
		List<Object> parameters = new ArrayList<>(9);
		parameters.add(betState);
		parameters.add(betSecond);
		parameters.add(splitTwoSideAmount);
		parameters.add(splitNumberAmount);
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add("设置游戏详情");
		parameters.add(hmGameSetId);
		parameters.add(IbmStateEnum.OPEN.name());
		super.execute(sql, parameters);
	}

	/**
	 * 更新盘口会员投注状态
	 *
	 * @param hmGameSetId 盘口会员游戏设置主键
	 * @param betState    投注状态
	 */
	public void update(String hmGameSetId, String betState) throws SQLException {
		String sql = "UPDATE ibmc_hm_game_set SET BET_STATE_ = ?,UPDATE_TIME_= ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
				+ "WHERE IBMC_HM_GAME_SET_ID_ = ? AND STATE_ = ?";
		List<Object> parameters = new ArrayList<>(9);
		parameters.add(betState);
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add("设置投注状态");
		parameters.add(hmGameSetId);
		parameters.add(IbmStateEnum.OPEN.name());
		super.execute(sql, parameters);
	}
	/**
	 * 获取盘口会员所有游戏设置
	 *
	 * @param existHmId 已存在盘口会员id
	 * @return
	 */
	public List<String> findIds(String existHmId) throws SQLException {
		String sql = "select IBMC_HM_GAME_SET_ID_ from ibmc_hm_game_set where EXIST_HM_ID_=? and STATE_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(existHmId);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findStringList("IBMC_HM_GAME_SET_ID_", sql, parameters);
	}

    /**
     * 批量添加游戏设置信息
     * @param memberGameInfos   会员游戏设置信息
     * @param hmInfos           会员信息
     */
    public void save(JSONArray memberGameInfos, Map<String, Object> hmInfos) throws SQLException {
        StringBuilder sql=new StringBuilder();
        List<Object> parameters = new ArrayList<>();
        Date nowTime=new Date();
        sql.append("insert into ibmc_hm_game_set (IBMC_HM_GAME_SET_ID_,EXIST_HM_ID_,GAME_CODE_,BET_STATE_,BET_SECOND_,"
                + "SPLIT_TWO_SIDE_AMOUNT_,SPLIT_NUMBER_AMOUNT_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,"
                + "UPDATE_TIME_LONG_,STATE_) VALUES");
        for(int i=0;i<memberGameInfos.size();i++){
            JSONObject gameInfos=memberGameInfos.getJSONObject(i);
            sql.append("(?,?,?,?,?,?,?,?,?,?,?,?),");
            parameters.add(RandomTool.getNumLetter32());
            parameters.add(hmInfos.get(gameInfos.get("HANDICAP_MEMBER_ID_")));
            parameters.add(gameInfos.get("GAME_CODE_"));
            parameters.add(gameInfos.get("BET_STATE_"));
            parameters.add(gameInfos.get("BET_SECOND_"));
            parameters.add(gameInfos.get("SPLIT_TWO_SIDE_AMOUNT_"));
            parameters.add(gameInfos.get("SPLIT_NUMBER_AMOUNT_"));
            parameters.add(nowTime);
            parameters.add(nowTime.getTime());
            parameters.add(nowTime);
            parameters.add(nowTime.getTime());
            parameters.add(IbmStateEnum.OPEN.name());
        }
        sql.delete(sql.length()-1,sql.length());
        super.dao.execute(sql.toString(),parameters);
    }
}
