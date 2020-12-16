package com.ibm.follow.servlet.cloud.ibm_game.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_game.entity.IbmGame;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IBM_游戏 服务类
 *
 * @author Robot
 */
public class IbmGameService extends BaseServicePlus {

    /**
     * 保存IBM_游戏 对象数据
     *
     * @param entity IbmGame对象数据
     */
    public String save(IbmGame entity) throws Exception {
        return super.dao.save(entity);
    }

    /**
     * 逻辑删除
     *
     * @param id 要删除ibm_game 的 IBM_GAME_ID_主键id
     */
    public void del(String id) throws Exception {
        String sql = "update ibm_game set state_='DEL' where IBM_GAME_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }

    /**
     * 逻辑删除IBM_GAME_ID_主键id数组的数据
     *
     * @param idArray 要删除 ibm_game 的 IBM_GAME_ID_数组
     */
    public void delAll(String[] idArray) throws Exception {
        if (idArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : idArray) {
                stringBuilder.append("'").append(id).append("'").append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String sql = "update ibm_game set state_='DEL' where IBM_GAME_ID_ in(" + stringBuilder.toString() + ")";
            dao.execute(sql, null);
        }
    }

    /**
     * 物理删除
     *
     * @param id 要删除 ibm_game  的 IBM_GAME_ID_
     */
    public void delPhysical(String id) throws Exception {
        String sql = "delete from ibm_game where IBM_GAME_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }

    /**
     * 物理删除IBM_GAME_ID_主键id数组的数据
     *
     * @param idArray 要删除ibm_game 的 IBM_GAME_ID_数组
     */
    public void delAllPhysical(String[] idArray) throws Exception {
        if (idArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : idArray) {
                stringBuilder.append("'").append(id).append("'").append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String sql = "delete from ibm_game where IBM_GAME_ID_ in(" + stringBuilder.toString() + ")";
            dao.execute(sql, null);
        }
    }

    /**
     * 更新IbmGame实体信息
     *
     * @param entity IBM_游戏 实体
     */
    public void update(IbmGame entity) throws Exception {
        dao.update(entity);
    }

    /**
     * 根据ibm_game表主键查找 IBM_游戏 实体
     *
     * @param id ibm_game 主键
     * @return IBM_游戏 实体
     */
    public IbmGame find(String id) throws Exception {
        return (IbmGame) this.dao.find(IbmGame.class, id);

    }

    /**
     * 获取 游戏d为key，游戏code为value的集合
     *
     * @return 游戏集合
     */
    public Map<String, String> mapIdCode() throws SQLException {
        String sql = "SELECT IBM_GAME_ID_ as key_, GAME_CODE_ as value_ FROM `ibm_game` where STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(1);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.findKVMap(sql, parameterList);
    }



    /**
     * 获取游戏ID
     *
     * @param gameCode 游戏编码
     * @return 游戏ID
     */
    public String findId(String gameCode) throws SQLException {
        String sql = "SELECT IBM_GAME_ID_ FROM `ibm_game` where GAME_CODE_ = ? and STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(gameCode);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findString("IBM_GAME_ID_", sql, parameterList);
    }

    /**
     * 获取游戏ID
     *
     * @param gameCode 游戏编码
     * @return 游戏ID
     */
    public IbmGame findEntity(Object gameCode) throws Exception {
        String sql = "SELECT * FROM `ibm_game` where GAME_CODE_ = ? and STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(gameCode);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findObject(IbmGame.class, sql, parameterList);
    }

    /**
     * 获取游戏编码
     *
     * @param gameId 游戏id
     * @return 游戏编码
     */
    public String findCode(String gameId) throws SQLException {
        String sql = "SELECT GAME_CODE_ FROM `ibm_game` where IBM_GAME_ID_ = ? and STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(gameId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findString("GAME_CODE_", sql, parameterList);
    }

    /**
     * 获取游戏信息
     * @param gameId 游戏id
     * @return 游戏信息
     */
    public Map<String, Object> findInfo(String gameId) throws SQLException {
        String sql = "SELECT GAME_CODE_,GAME_NAME_ FROM `ibm_game` where IBM_GAME_ID_ = ? and STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(gameId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.findMap( sql, parameterList);
    }

    /**
     * 获取 开奖时间
     *
     * @param gameId 游戏id
     * @return 开奖时间
     */
    public Integer findDrawTime(String gameId) throws SQLException {
        String sql = "SELECT DRAW_TIME_ FROM `ibm_game` where IBM_GAME_ID_ = ? and STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(gameId);
        parameterList.add(IbmStateEnum.OPEN.name());
        Map<String, Object> info = super.dao.findMap(sql, parameterList);
        return NumberTool.getInteger(info.getOrDefault("DRAW_TIME_", "0"));
    }

    /**
     * 获取开奖信息
     *
     * @param sql    开奖信息sql语句
     * @param period 期数
     * @return 开奖信息
     */
    public Map<String, Object> findDrawInfo(String sql, Object period, String type) throws Exception {
        super.commitTransactionCommit();
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(period.toString());
        parameterList.add(IbmStateEnum.OPEN.name());
        parameterList.add(type);
        Map<String, Object> map = super.findMap(sql, parameterList);
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
     * 获取所有游戏列表
     *
     * @return 所有游戏信息
     */
    public List<Map<String, Object>> findAll() throws SQLException {
        String sql = "select * from ibm_game where STATE_ = ? ";
        List<Object> parameterList = new ArrayList<>(1);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findMapList(sql, parameterList);
    }

    /**
     * 更新游戏信息
     *
     * @param gameId   游戏ID
     * @param gameCode 游戏编码
     * @param gameName 游戏名称
     * @param gameSn   游戏次序
     * @param drawTime 开奖时间
     * @throws SQLException
     */
    public void updateInfo(String gameId, String gameCode, String gameName, String gameIcon, String gameSn, String drawTime) throws SQLException {
        String sql = "update ibm_game set GAME_NAME_ = ? ,GAME_CODE_ = ? ,ICON_ = ? ,DRAW_TIME_ = ? ,SN_ = ?  where IBM_GAME_ID_ = ? ";
        List<Object> parameterList = new ArrayList<>(5);
        parameterList.add(gameName);
        parameterList.add(gameCode);
        parameterList.add(gameIcon);
        parameterList.add(drawTime);
        parameterList.add(gameSn);
        parameterList.add(gameId);
        dao.execute(sql, parameterList);
    }

    /**
     * 查询所有拥有 GAME_ID_ 字段的表名
     * @return 所有表名的集合
     * @throws SQLException
     */
    public List<Map<String, Object>> findAllTableGameID() throws SQLException {
        String sql = "SELECT table_name FROM information_schema.columns WHERE " +
                "column_name LIKE 'GAME_ID_' AND table_schema = 'ibm_cloud'";
        return dao.findMapList(sql, null);
    }

    /**
     * 查询所有拥有 GAME_CODE_ 字段的表名
     * @return 所有表名的集合
     * @throws SQLException
     */
    public List<Map<String, Object>> findAllTableGameCode() throws SQLException {
        String sql = "SELECT table_name FROM information_schema.columns WHERE " +
                "column_name LIKE 'GAME_CODE_' AND table_schema = 'ibm_cloud'";
        return dao.findMapList(sql, null);
    }


    /**
     * 更新游戏信息
     *
     * @param tableName 表名
     * @param gameId    游戏id
     * @throws SQLException
     */
    public void delAllInfoByGameId(String tableName,String gameId) throws SQLException {
        String sql = "update "+tableName+" set STATE_ = ? where GAME_ID_ = ? ";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(IbmStateEnum.DEL.name());
        parameterList.add(gameId);
        dao.execute(sql, parameterList);
    }

    /**
     * 更新游戏信息
     *
     * @param tableName 表名
     * @param gameCode    游戏Code
     * @throws SQLException
     */
    public void delAllInfoByGameCode(String tableName,String gameCode) throws SQLException {
        String sql = "update "+tableName+" set STATE_ = ? where GAME_CODE_ = ? ";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(IbmStateEnum.DEL.name());
        parameterList.add(gameCode);
        dao.execute(sql, parameterList);
    }

    /**
     * 获取 游戏d为key，游戏code为value的集合
     *
     * @return 游戏集合
     */
    public List<String> findAllGameCode() throws SQLException {
        String sql = "SELECT GAME_CODE_ FROM `ibm_game` where STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(1);
        parameterList.add(IbmStateEnum.OPEN.name());
        return dao.findStringList("GAME_CODE_",sql,parameterList);
    }

    /**
     *
     * @return
     */
    public List<Map<String, Object>> findGameInfo() throws SQLException {
        String sql="select GAME_CODE_,GAME_NAME_ from ibm_game where STATE_=? ORDER BY SN_ ";
        List<Object> parameterList = new ArrayList<>(1);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findMapList(sql,parameterList);
    }

    /**
     * 获取游戏列表
     * @param gameName  游戏名称
     * @return
     */
    public List<Map<String, Object>> listShow(String gameName) throws SQLException {
        String sql="select IBM_GAME_ID_ as GAME_ID_,GAME_NAME_,GAME_CODE_,ICON_,DRAW_TIME_,STATE_,GAME_WORTH_T_ from ibm_game where STATE_!=?";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(IbmStateEnum.DEL.name());
        if(StringTool.notEmpty(gameName)){
            sql+=" and GAME_NAME_ like ?";
            parameterList.add("%"+gameName+"%");
        }
        sql+=" order by SN_";
        return super.dao.findMapList(sql,parameterList);
    }

    /**
     * 获取游戏信息
     * @param gameId    游戏id
     * @return
     */
    public Map<String, Object> findInfoById(String gameId) throws SQLException {
        String sql="select IBM_GAME_ID_ as GAME_ID_,GAME_NAME_,GAME_CODE_,ICON_,DRAW_TIME_,STATE_,GAME_WORTH_T_,REP_GRAB_TABLE_NAME_,"
                + "REP_DRAW_TABLE_NAME_,SN_ from ibm_game where IBM_GAME_ID_=?";
        List<Object> parameterList = new ArrayList<>(1);
        parameterList.add(gameId);
        return super.dao.findMap(sql,parameterList);
    }

}
