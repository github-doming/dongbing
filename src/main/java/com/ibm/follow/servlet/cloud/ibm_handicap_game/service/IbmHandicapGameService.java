package com.ibm.follow.servlet.cloud.ibm_handicap_game.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.entity.IbmHandicapGame;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmHandicapGameService extends BaseServicePlus {

    protected Log log = LogFactory.getLog(this.getClass());

    /**
     * 保存{ay_table_class}对象数据
     *
     * @param entity IbmHandicapGame对象数据
     */
    public String save(IbmHandicapGame entity) throws Exception {
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
     * 更新IbmHandicapGame实体信息
     *
     * @param entity IbmHandicapGame实体
     */
    public void update(IbmHandicapGame entity) throws Exception {
        dao.update(entity);
    }

    /**
     * 根据ibm_handicap_game表主键查找IbmHandicapGame实体
     *
     * @param id ibm_handicap_game 主键
     * @return IbmHandicapGame实体
     */
    public IbmHandicapGame find(String id) throws Exception {
        return (IbmHandicapGame) this.dao.find(IbmHandicapGame.class, id);

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
     * 获取分页IbmHandicapGame数据
     *
     * @param sortFieldName 排序字段名
     * @param sortOrderName 排序顺序
     * @param pageIndex     页面索引
     * @param pageSize      页面大小
     * @return 分页IbmHandicapGame数据
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
        return dao.page(IbmHandicapGame.class, sql, null, pageIndex, pageSize, sqlCount);
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
     * 按照更新顺序查询所有可用IbmHandicapGame数据信息
     *
     * @return 可用<IbmHandicapGame>数据信息
     */
    public List findObjectAll() throws Exception {
        String sql = "SELECT * FROM ibm_handicap_game  where state_!='DEL' order by UPDATE_TIME_ desc";
        return this.dao.findObjectList(IbmHandicapGame.class, sql);
    }

    /**
     * 获取封盘时间
     *
     * @param handicapId 盘口id
     * @param gameId     游戏id
     * @return 封盘时间
     */
    public Integer findCloseTime(String handicapId, String gameId) throws SQLException {
        String sql = "SELECT CLOSE_TIME_ FROM `ibm_handicap_game` where HANDICAP_ID_ = ? and GAME_ID_ = ? and STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(handicapId);
        parameterList.add(gameId);
        parameterList.add(IbmStateEnum.OPEN.name());
        Map<String, Object> info = super.dao.findMap(sql, parameterList);
        return NumberTool.getInteger(info.get("CLOSE_TIME_"), null);
    }

    /**
     * 获取游戏ids
     *
     * @param handicapId 盘口id
     * @return 游戏ids
     */
    public List<String> findGameIds(String handicapId, String[] availableGame) throws SQLException {
        StringBuilder sql = new StringBuilder("select GAME_ID_ from ibm_handicap_game where HANDICAP_ID_=? and STATE_=? and GAME_CODE_ in(");
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(handicapId);
        parameterList.add(IbmStateEnum.OPEN.name());
        for (String game : availableGame) {
            sql.append("?,");
            parameterList.add(game);
        }
        sql.replace(sql.length() - 1, sql.length(), ")");

        return super.dao.findStringList("GAME_ID_", sql.toString(), parameterList);
    }

    /**
     * 获取code和类型
     *
     * @return
     */
    public List<Map<String, Object>> listCodeType(String customerType) throws SQLException {
        String sql = "SELECT ihg.HANDICAP_CODE_,ihg.GAME_CODE_,ihg.TYPE_,ihg.HANDICAP_ID_ FROM `ibm_handicap_game`"
                + " ihg LEFT JOIN ibm_handicap ih on ihg.HANDICAP_ID_=ih.IBM_HANDICAP_ID_ where ih.HANDICAP_CATEGORY_=? and ihg.STATE_ != ?";
        List<Object> parameterList = new ArrayList<>(1);
        parameterList.add(customerType);
        parameterList.add(IbmStateEnum.DEL.name());
        return super.dao.findMapList(sql, parameterList);
    }

    /**
     * 获取盘口code和类型
     *
     * @param gameCode 游戏code
     * @return
     */
    public List<Map<String, Object>> findByGameCode(String gameCode) throws SQLException {
        String sql = "SELECT ihg.HANDICAP_CODE_,ihg.TYPE_ FROM `ibm_handicap_game` ihg "
                + " LEFT JOIN ibm_handicap ih on ihg.HANDICAP_ID_=ih.IBM_HANDICAP_ID_ where ih.HANDICAP_CATEGORY_=?"
                + " and GAME_CODE_=? and ihg.STATE_!= ?";
        List<Object> parameterList = new ArrayList<>(1);
        parameterList.add(IbmTypeEnum.MEMBER.name());
        parameterList.add(gameCode);
        parameterList.add(IbmStateEnum.DEL.name());
        return super.dao.findMapList(sql, parameterList);
    }

    /**
     * 获取类型
     *
     * @param handicapCode 盘口code
     * @return 类型
     */
    public List<Map<String, Object>> findByHandicapCode(String handicapCode) throws SQLException {
        String sql = "SELECT ihg.GAME_CODE_,ihg.TYPE_ FROM `ibm_handicap_game` ihg"
                + " LEFT JOIN ibm_handicap ih on ihg.HANDICAP_ID_=ih.IBM_HANDICAP_ID_ where ih.HANDICAP_CATEGORY_=?"
                + " and ihg.HANDICAP_CODE_=? and ihg.STATE_!= ?";
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(IbmTypeEnum.MEMBER.name());
        parameterList.add(handicapCode);
        parameterList.add(IbmStateEnum.DEL.name());
        return super.dao.findMapList(sql, parameterList);
    }

    /**
     * 获取ids
     *
     * @param gameCode 游戏code
     * @param type     类型
     * @return
     */
    public List<String> findIds(String gameCode, String type) throws SQLException {
        String sql = "SELECT ihg.HANDICAP_ID_ FROM `ibm_handicap_game` ihg"
                + " LEFT JOIN ibm_handicap ih on ihg.HANDICAP_ID_=ih.IBM_HANDICAP_ID_ where ih.HANDICAP_CATEGORY_=?"
                + " and GAME_CODE_=? and TYPE_=? and ihg.STATE_!= ?";
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(IbmTypeEnum.MEMBER.name());
        parameterList.add(gameCode);
        parameterList.add(type);
        parameterList.add(IbmStateEnum.DEL.name());
        return super.dao.findStringList("HANDICAP_ID_", sql, parameterList);
    }

	/**
	 * 获取盘口codes
	 * @param gameCode	游戏code
	 * @param type			类型
	 * @return
	 */
	public List<String> findCodes(String gameCode, String type) throws SQLException {
		String sql = "SELECT ihg.HANDICAP_CODE_ FROM `ibm_handicap_game` ihg"
				+ " LEFT JOIN ibm_handicap ih on ihg.HANDICAP_ID_=ih.IBM_HANDICAP_ID_ where ih.HANDICAP_CATEGORY_=?"
				+ " and GAME_CODE_=? and TYPE_=? and ihg.STATE_!= ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(IbmTypeEnum.MEMBER.name());
		parameterList.add(gameCode);
		parameterList.add(type);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findStringList("HANDICAP_CODE_", sql, parameterList);
	}

    /**
     * 根据盘口和游戏ids获取盘口游戏信息
     *
     * @param handicapId 盘口ids
     * @param gameIds    游戏ids
     * @return
     */
    public List<String> findHandicapGameInfo(String handicapId, List<String> gameIds) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("select GAME_ID_ from ibm_handicap_game where HANDICAP_ID_=? and GAME_ID_ in(");
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(handicapId);
        for (String gameId : gameIds) {
            sql.append("?,");
            parameterList.add(gameId);
        }
        sql.replace(sql.length() - 1, sql.length(), ")");
        return super.dao.findStringList("GAME_ID_", sql.toString(), parameterList);
    }

    /**
     * 获取指定盘口所有盘口游戏
     *
     * @param handicapId 盘口id
     * @return
     */
    public List<Map<String, Object>> findHandicapInfo(Object handicapId) throws SQLException {
        String sql = "select ihg.GAME_NAME_,ig.GAME_CODE_ from ibm_handicap_game ihg LEFT JOIN ibm_game ig ON ihg.GAME_ID_=ig.IBM_GAME_ID_"
                + " LEFT JOIN ibm_handicap ih ON ihg.HANDICAP_ID_=ih.IBM_HANDICAP_ID_ WHERE ihg.HANDICAP_ID_=?"
                + " and ihg.STATE_=? and ig.STATE_=?";
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(handicapId);
        parameterList.add(IbmStateEnum.OPEN.name());
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findMapList(sql, parameterList);
    }

    /**
     * 修改盘口游戏状态
     *
     * @param gameId     游戏id
     * @param state      状态
     * @param updateUser 修改人
     */
    public void updateByGameId(String gameId, String state, String updateUser) throws SQLException {
        String sql = "UPDATE ibm_handicap_game ihg LEFT JOIN ibm_handicap ih ON ihg.HANDICAP_ID_ = ih.IBM_HANDICAP_ID_"
                + " SET ihg.STATE_ =?, ihg.UPDATE_USER_ =?, ihg.UPDATE_TIME_ =?, ihg.UPDATE_TIME_LONG_ =?"
                + " WHERE ihg.GAME_ID_ =? AND ih.STATE_ =? AND ihg.STATE_ !=?";
        List<Object> parameterList = new ArrayList<>(7);
        parameterList.add(state);
        parameterList.add(updateUser);
        parameterList.add(new Date());
        parameterList.add(System.currentTimeMillis());
        parameterList.add(gameId);
        parameterList.add(IbmStateEnum.OPEN.name());
        parameterList.add(IbmStateEnum.DEL.name());
        super.dao.execute(sql, parameterList);
    }

    /**
     * 修改盘口游戏状态
     *
     * @param handicapId 盘口id
     * @param state      状态
     * @param updateUser 修改人
     */
    public void updateByHandicapId(String handicapId, String state, String updateUser) throws SQLException {
        String sql = "UPDATE ibm_handicap_game ihg LEFT JOIN ibm_game ig ON ihg.GAME_ID_=ig.IBM_GAME_ID_"
                + " SET ihg.STATE_ =?, ihg.UPDATE_USER_ =?, ihg.UPDATE_TIME_ =?, ihg.UPDATE_TIME_LONG_ =?"
                + " WHERE ihg.HANDICAP_ID_ =? AND ig.STATE_ =? AND ihg.STATE_ !=?";
        List<Object> parameterList = new ArrayList<>(7);
        parameterList.add(state);
        parameterList.add(updateUser);
        parameterList.add(new Date());
        parameterList.add(System.currentTimeMillis());
        parameterList.add(handicapId);
        parameterList.add(IbmStateEnum.OPEN.name());
        parameterList.add(IbmStateEnum.DEL.name());
        super.dao.execute(sql, parameterList);
    }

    /**
     * 获取盘口游戏列表
     *
     * @param handicapId 盘口id
     * @return
     */
    public List<Map<String, Object>> findByHandicapId(String handicapId) throws SQLException {
        String sql = "SELECT ihg.IBM_HANDICAP_GAME_ID_ as HANDICAP_GAME_ID_,ihg.HANDICAP_CODE_,ihg.GAME_NAME_ HANDICAP_GAME_NAME_,ihg.GAME_CODE_,ihg.TYPE_,ihg.CLOSE_TIME_,"
                + "ihg.SUB_IHBI_TABLE_NAME_,ihg.ICON_,ihg.STATE_,ihg.SN_,ig.GAME_NAME_ " +
                " from ibm_handicap_game  ihg LEFT JOIN ibm_game ig ON ihg.GAME_ID_ = ig.IBM_GAME_ID_" +
                " WHERE ihg.HANDICAP_ID_ = ? and ihg.STATE_ != ? AND ig.STATE_ != ? ORDER BY ihg.SN_";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(handicapId);
        parameterList.add(IbmStateEnum.DEL.name());
        parameterList.add(IbmStateEnum.DEL.name());
        return super.dao.findMapList(sql, parameterList);
    }

    /**
     * 获取盘口游戏信息
     *
     * @param handicapGameId 盘口游戏id
     * @return
     */
    public Map<String, Object> findInfoById(String handicapGameId) throws SQLException {
        String sql = "SELECT IBM_HANDICAP_GAME_ID_ as HANDICAP_GAME_ID_,HANDICAP_CODE_,GAME_NAME_,GAME_CODE_,TYPE_,CLOSE_TIME_,"
                + "SUB_IHBI_TABLE_NAME_,ICON_,STATE_,SN_ from ibm_handicap_game WHERE IBM_HANDICAP_GAME_ID_=? and STATE_!=?";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(handicapGameId);
        parameterList.add(IbmStateEnum.DEL.name());
        return super.dao.findMap(sql, parameterList);
    }
    /**
     * 查找是否存在
     * @param handicapCode 盘口编码
     * @param gameCode 游戏编码
     * @param handicapCategory 盘口类型
     * @return 存在true
     */
    public boolean isExist(String handicapCode, String gameCode, String handicapCategory) throws SQLException {
        String sql = "SELECT IBM_HANDICAP_GAME_ID_ FROM `ibm_handicap_game` ihg "
                + " LEFT JOIN ibm_handicap ih ON ihg.HANDICAP_ID_ = ih.IBM_HANDICAP_ID_ "
                + " WHERE ihg.HANDICAP_CODE_ = ? AND GAME_CODE_ = ? AND HANDICAP_CATEGORY_ = ? AND ihg.STATE_ != ? AND ih.STATE_ != ?";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(handicapCode);
        parameterList.add(gameCode);
        parameterList.add(handicapCategory);
        parameterList.add(IbmStateEnum.DEL.name());
        parameterList.add(IbmStateEnum.DEL.name());
        return ContainerTool.notEmpty(super.findMap(sql,parameterList));
    }

	/**
	 * 获取盘口游戏信息
	 * @return
	 */
	public Map<String, Map<String, Object>> findHandicapGameNames() throws SQLException {
		String sql="select HANDICAP_CODE_,GAME_NAME_,GAME_CODE_ from ibm_handicap_game where STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbmStateEnum.OPEN.name());
		List<Map<String,Object>> list=super.dao.findMapList(sql,parameterList);

		Map<String, Map<String, Object>> handicapGameInfos=new HashMap<>(list.size());
		if(ContainerTool.isEmpty(list)){
			return handicapGameInfos;
		}
		for(Map<String,Object> map:list){
			String handicapCode=map.get("HANDICAP_CODE_").toString();
			String gameCode=map.get("GAME_CODE_").toString();
			Object gameName=map.get("GAME_NAME_");
			if(handicapGameInfos.containsKey(handicapCode)){
				handicapGameInfos.get(handicapCode).put(gameCode,gameName);
			}else{
				Map<String, Object> gameMap=new HashMap<>(2);
				gameMap.put(gameCode,gameName);
				handicapGameInfos.put(handicapCode,gameMap);
			}
		}
		return handicapGameInfos;
	}
    /**
     * 查询盘口拥有的游戏
     * @param handicapCode 盘口编码
     * @return 游戏编码集合
     */
    public List<String> findHandicapGameCode(String handicapCode) throws SQLException {
        String sql = "SELECT DISTINCT GAME_CODE_ key_ FROM `ibm_handicap_game` where HANDICAP_CODE_ = ? AND STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(1);
        parameterList.add(handicapCode);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.findStringList(sql,parameterList);
    }


    /**
     * 获取盘口游戏信息
     *
     * @param handicapCode 盘口编码
     * @param gameCode 游戏编码
     * @return
     */
    public String findGameType(String handicapCode, String gameCode) throws SQLException {
        String sql = "select TYPE_ key_ from ibm_handicap_game where HANDICAP_CODE_=? and GAME_CODE_=?";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(handicapCode);
        parameterList.add(gameCode);
        return super.findString(sql, parameterList);
    }
}
