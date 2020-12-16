package com.ibm.follow.servlet.cloud.ibm_hm_game_set.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.entity.IbmHmGameSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmHmGameSetService extends BaseServicePlus {

    protected Log log = LogFactory.getLog(this.getClass());

    /**
     * 保存{ay_table_class}对象数据
     *
     * @param entity IbmHmGameSet对象数据
     */
    public String save(IbmHmGameSet entity) throws Exception {
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
     * 更新IbmHmGameSet实体信息
     *
     * @param entity IbmHmGameSet实体
     */
    public void update(IbmHmGameSet entity) throws Exception {
        dao.update(entity);
    }

    /**
     * 根据ibm_hm_game_set表主键查找IbmHmGameSet实体
     *
     * @param id ibm_hm_game_set 主键
     * @return IbmHmGameSet实体
     */
    public IbmHmGameSet find(String id) throws Exception {
        return (IbmHmGameSet) this.dao.find(IbmHmGameSet.class, id);

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
     * 获取分页IbmHmGameSet数据
     *
     * @param sortFieldName 排序字段名
     * @param sortOrderName 排序顺序
     * @param pageIndex     页面索引
     * @param pageSize      页面大小
     * @return 分页IbmHmGameSet数据
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
        return dao.page(IbmHmGameSet.class, sql, null, pageIndex, pageSize, sqlCount);
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
     * 按照更新顺序查询所有可用IbmHmGameSet数据信息
     *
     * @return 可用<IbmHmGameSet>数据信息
     */
    public List findObjectAll() throws Exception {
        String sql = "SELECT * FROM ibm_hm_game_set  where state_!='DEL' order by UPDATE_TIME_ desc";
        return this.dao.findObjectList(IbmHmGameSet.class, sql);
    }

    /**
     * 修改盘口会员所有游戏状态
     *
     * @param handicapMemberId 盘口会员id
     * @param nowTime          当前时间
     */
    public void updateBetState(String handicapMemberId, Date nowTime) throws SQLException {
        String sql = "UPDATE ibm_hm_game_set SET BET_STATE_ =?,UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? "
                + " WHERE HANDICAP_MEMBER_ID_ = ? AND BET_STATE_ = ? AND STATE_ != ?";
        List<Object> parameterList = new ArrayList<>(6);
        parameterList.add(IbmTypeEnum.FALSE.name());
        parameterList.add(nowTime);
        parameterList.add(nowTime.getTime());
        parameterList.add(handicapMemberId);
        parameterList.add(IbmTypeEnum.TRUE.name());
        parameterList.add(IbmStateEnum.DEL.name());
        super.dao.execute(sql, parameterList);
    }

    /**
     * 获取展示信息
     *
     * @param handicapMemberId 盘口会员id
     * @param gameId           游戏主键
     * @return 会员游戏展示信息
     */
    public Map<String, Object> findShowInfo(String handicapMemberId, String gameId) throws SQLException {
        String sql = "SELECT BET_STATE_,BET_AUTO_STOP_,BET_AUTO_STOP_TIME_LONG_,BET_AUTO_START_,"
                + " BET_AUTO_START_TIME_LONG_,BET_MODE_,BET_SECOND_, "
                + " SPLIT_TWO_SIDE_AMOUNT_,SPLIT_NUMBER_AMOUNT_ FROM `ibm_hm_game_set` "
                + " where  HANDICAP_MEMBER_ID_ = ? and GAME_ID_ = ? and STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(handicapMemberId);
        parameterList.add(gameId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findMap(sql, parameterList);
    }

    /**
     * 获取盘口会员游戏设置初始化信息
     *
     * @return 初始化信息
     */
    public Map<String, Object> findInitInfo() throws SQLException {
        String sql = "select BET_STATE_,BET_MODE_,BET_AUTO_STOP_,BET_AUTO_START_,BET_SECOND_,SPLIT_TWO_SIDE_AMOUNT_, "
                + " SPLIT_NUMBER_AMOUNT_ from ibm_hm_game_set where STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(1);
        parameterList.add(IbmStateEnum.DEF.name());
        return super.dao.findMap(sql, parameterList);
    }

    /**
     * 获取盘口会员游戏设置初始化信息
     *
     * @return 初始化信息
     */
    public IbmHmGameSet findDef() throws Exception {
        String sql = "select * from ibm_hm_game_set where STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(1);
        parameterList.add(IbmStateEnum.DEF.name());
        return super.dao.findObject(IbmHmGameSet.class,sql, parameterList);
    }

    /**
     * 获取游戏设置信息
     *
     * @param handicapMemberId 盘口会员id
     * @param gameId           游戏Id
     * @return 游戏设置信息
     */
    public IbmHmGameSet findByHmIdAndGameCode(String handicapMemberId, String gameId) throws Exception {
        String sql = "select * from ibm_hm_game_set where HANDICAP_MEMBER_ID_=? and GAME_ID_=? and STATE_=?";
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(handicapMemberId);
        parameterList.add(gameId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findObject(IbmHmGameSet.class, sql, parameterList);
    }

    /**
     * 获取盘口会员的游戏设置信息
     *
     * @param handicapMemberId 盘口会员id
     * @return 游戏设置信息
     */
    public List<Map<String, Object>> listInfo(String handicapMemberId) throws SQLException {
        String sql = "SELECT GAME_CODE_,BET_STATE_,BET_MODE_,BET_AUTO_STOP_,BET_AUTO_START_ FROM ibm_hm_game_set ihgs"
                + " LEFT JOIN ibm_handicap_game ihg ON ihgs.HANDICAP_ID_=ihg.HANDICAP_ID_ AND  ihgs.GAME_ID_=ihg.GAME_ID_"
                + " WHERE HANDICAP_MEMBER_ID_ = ? AND ihg.STATE_ = ? AND ihgs.STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(handicapMemberId);
        parameterList.add(IbmStateEnum.OPEN.name());
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findMapList(sql, parameterList);
    }

    /**
     * 获取配置信息
     *
     * @param handicapMemberId 盘口会员id
     * @param gameId           游戏id
     * @return 配置信息
     */
    public Map<String, Object> findInfo(String handicapMemberId, String gameId) throws SQLException {
        String sql = "select BET_STATE_,BET_SECOND_,BET_MODE_ from ibm_hm_game_set where HANDICAP_MEMBER_ID_ = ? and GAME_ID_ = ? and STATE_ = ?";
        List<Object> parameters = new ArrayList<>(3);
        parameters.add(handicapMemberId);
        parameters.add(gameId);
        parameters.add(IbmStateEnum.OPEN.name());
        return super.dao.findMap(sql, parameters);
    }
	/**
	 * 获取配置信息
	 *
	 * @param handicapMemberIds 盘口会员id
	 * @param gameId           游戏id
	 * @return 配置信息
	 */
	public List<Map<String, Object>> findInfos(Set<String> handicapMemberIds, String gameId) throws SQLException {
		StringBuilder sql = new StringBuilder("select HANDICAP_MEMBER_ID_,BET_SECOND_,BET_MODE_ from ibm_hm_game_set where GAME_ID_ = ?"
				+ " and BET_STATE_=? and STATE_ = ? and HANDICAP_MEMBER_ID_ in(");
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(gameId);
		parameters.add(IbmTypeEnum.TRUE.name());
		parameters.add(IbmStateEnum.OPEN.name());
		for(String handicapMemberId:handicapMemberIds){
			sql.append("?,");
			parameters.add(handicapMemberId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		return super.dao.findMapList(sql.toString(), parameters);
	}

    /**
     * 获取投注模式信息
     *
     * @param handicapMemberId 盘口会员id
     * @return 模式信息
     */
    public List<Map<String, Object>> listBetModeInfo(String handicapMemberId) throws SQLException {
        String sql = "SELECT GAME_CODE_,BET_MODE_,TYPE_ FROM ibm_handicap_game ihg"
                + " LEFT JOIN ibm_hm_game_set ihgs ON ihg.HANDICAP_ID_ = ihgs.HANDICAP_ID_ AND ihg.GAME_ID_ = ihgs.GAME_ID_"
                + " WHERE HANDICAP_MEMBER_ID_=? and ihg.STATE_=? and ihgs.STATE_=?";
        List<Object> parameters = new ArrayList<>(3);
        parameters.add(handicapMemberId);
        parameters.add(IbmStateEnum.OPEN.name());
        parameters.add(IbmStateEnum.OPEN.name());
        return super.findMapList(sql, parameters);
    }

    /**
     * 获取盘口会员所有游戏设置信息
     *
     * @param handicapMemberId 盘口会员id
     * @return 游戏设置信息
     */
    public List<Map<String, Object>> findByHmId(String handicapMemberId) throws SQLException {
        String sql = "SELECT ig.GAME_CODE_,ihgs.BET_STATE_,ihgs.BET_SECOND_,ihgs.SPLIT_TWO_SIDE_AMOUNT_,"
                + "ihgs.SPLIT_NUMBER_AMOUNT_ FROM ibm_hm_game_set ihgs LEFT JOIN ibm_game ig ON ihgs.GAME_ID_=ig.IBM_GAME_ID_"
                + " WHERE HANDICAP_MEMBER_ID_ =? AND ihgs.STATE_ =?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(handicapMemberId);
        parameters.add(IbmStateEnum.OPEN.name());
        return super.findMapList(sql, parameters);
    }

    /**
     * 盘口游戏设置信息
     * @param handicapMemberIds     盘口会员ids
     * @return
     */
    public List<Map<String, Object>> findByHmIds(List<String> handicapMemberIds) throws SQLException {
        StringBuilder sql=new StringBuilder();
        sql.append("SELECT HANDICAP_MEMBER_ID_,ig.GAME_CODE_,ihgs.BET_STATE_,ihgs.BET_SECOND_,ihgs.SPLIT_TWO_SIDE_AMOUNT_,"
                + "ihgs.SPLIT_NUMBER_AMOUNT_ FROM ibm_hm_game_set ihgs LEFT JOIN ibm_game ig ON ihgs.GAME_ID_=ig.IBM_GAME_ID_"
                + " WHERE ihgs.STATE_ =? and HANDICAP_MEMBER_ID_ in(");
        List<Object> parameters = new ArrayList<>();
        parameters.add(IbmStateEnum.OPEN.name());
        for(String handicapMemberId:handicapMemberIds){
            sql.append("?,");
            parameters.add(handicapMemberId);
        }
        sql.replace(sql.length()-1,sql.length(),")");
        return super.findMapList(sql.toString(), parameters);
    }

    /**
     * 获取自动开始会员信息
     *
     * @return 会员信息
     */
    public List<Map<String, Object>> listAutoStart() throws SQLException {
        String sql = "SELECT IBM_HM_GAME_SET_ID_,GAME_ID_,ihgs.HANDICAP_MEMBER_ID_,ihgs.USER_ID_,ihgs.HANDICAP_ID_,ihi.MEMBER_ACCOUNT_ FROM `ibm_hm_game_set` ihgs "
                + " LEFT JOIN ibm_hm_info ihi ON ihgs.HANDICAP_MEMBER_ID_ = ihi.HANDICAP_MEMBER_ID_ "
                + " WHERE ihi.STATE_ = ? AND BET_STATE_ = ? AND BET_AUTO_START_ = ? AND BET_AUTO_START_TIME_LONG_ BETWEEN "
                + " ? AND ? AND ihgs.STATE_ = ?";
        List<Object> parameters = new ArrayList<>(6);
        parameters.add(IbmStateEnum.LOGIN.name());
        parameters.add(IbmTypeEnum.FALSE.name());
        parameters.add(IbmTypeEnum.TRUE.name());
        parameters.add(System.currentTimeMillis());
        parameters.add(System.currentTimeMillis() + 60000L);
        parameters.add(IbmStateEnum.OPEN.name());
        return super.findMapList(sql, parameters);
    }

    /**
     * 获取自动停止会员信息
     *
     * @return 会员信息
     */
    public List<Map<String, Object>> listAutoStop() throws SQLException {
        String sql = "SELECT IBM_HM_GAME_SET_ID_,GAME_ID_,ihgs.HANDICAP_MEMBER_ID_,ihgs.USER_ID_,ihgs.HANDICAP_ID_,ihi.MEMBER_ACCOUNT_ "
                + " FROM `ibm_hm_game_set` ihgs "
                + " LEFT JOIN ibm_hm_info ihi ON ihgs.HANDICAP_MEMBER_ID_ = ihi.HANDICAP_MEMBER_ID_ "
                + " WHERE ihi.STATE_ = ? AND BET_STATE_ = ? AND BET_AUTO_STOP_ = ? AND BET_AUTO_STOP_TIME_LONG_ BETWEEN "
                + " ? AND ? AND ihgs.STATE_ = ?";
        List<Object> parameters = new ArrayList<>(6);
        parameters.add(IbmStateEnum.LOGIN.name());
        parameters.add(IbmTypeEnum.TRUE.name());
        parameters.add(IbmTypeEnum.TRUE.name());
        parameters.add(System.currentTimeMillis());
        parameters.add(System.currentTimeMillis() + 60000L);
        parameters.add(IbmStateEnum.OPEN.name());
        return super.findMapList(sql, parameters);

    }

    /**
     * 更新自动开始
     *
     * @param gameSetIds 游戏设置主键
     * @param nowTime    更新时间
     * @param desc       更新描述
     */
    public void updateAutoStart(List<String> gameSetIds, Date nowTime, String desc) throws SQLException {
        StringBuilder sql = new StringBuilder("UPDATE ibm_hm_game_set set BET_STATE_ = ?")
                .append(",UPDATE_TIME_ = ?,  UPDATE_TIME_LONG_ = ?,DESC_ = ? where IBM_HM_GAME_SET_ID_ in(");
        List<Object> parameters = new ArrayList<>(5 + gameSetIds.size());
        parameters.add(IbmTypeEnum.TRUE.name());
        parameters.add(nowTime);
        parameters.add(System.currentTimeMillis());
        parameters.add(desc);
        for (String gameSetId : gameSetIds) {
            sql.append("?,");
            parameters.add(gameSetId);
        }
        sql.deleteCharAt(sql.lastIndexOf(",")).append(") and BET_STATE_ = ?");
        parameters.add(IbmTypeEnum.FALSE.name());
        super.execute(sql.toString(), parameters);
    }

    /**
     * 更新自动停止
     *
     * @param gameSetIds 游戏设置主键
     * @param nowTime    更新时间
     * @param desc       更新描述
     */
    public void updateAutoStop(List<String> gameSetIds, Date nowTime, String desc) throws SQLException {
        StringBuilder sql = new StringBuilder("UPDATE ibm_hm_game_set set BET_STATE_ = ?")
                .append(",UPDATE_TIME_ = ?,  UPDATE_TIME_LONG_ = ?,DESC_ = ? where IBM_HM_GAME_SET_ID_ in(");
        List<Object> parameters = new ArrayList<>(5 + gameSetIds.size());
        parameters.add(IbmTypeEnum.FALSE.name());
        parameters.add(nowTime);
        parameters.add(System.currentTimeMillis());
        parameters.add(desc);
        for (String gameSetId : gameSetIds) {
            sql.append("?,");
            parameters.add(gameSetId);
        }
        sql.deleteCharAt(sql.lastIndexOf(",")).append(") and BET_STATE_ = ?");
        parameters.add(IbmTypeEnum.TRUE.name());
        super.execute(sql.toString(), parameters);
    }

    /**
     * 获取游戏信息
     *
     * @param handicapMemberId 盘口会员主键
     * @return 游戏信息
     */
    public List<Map<String, Object>> listGameInfo(String handicapMemberId) throws SQLException {
        String sql = "SELECT BET_STATE_,GAME_NAME_,GAME_CODE_,ICON_ FROM ibm_hm_game_set ihs"
                + " LEFT JOIN ibm_handicap_game ihg ON ihs.HANDICAP_ID_=ihg.HANDICAP_ID_"
                + " AND ihs.GAME_ID_=ihg.GAME_ID_ WHERE ihs.HANDICAP_MEMBER_ID_ = ? AND ihg.STATE_ =?"
                + " AND ihs.STATE_ = ? ORDER BY SN_";
        List<Object> parameters = new ArrayList<>(3);
        parameters.add(handicapMemberId);
        parameters.add(IbmStateEnum.OPEN.name());
        parameters.add(IbmStateEnum.OPEN.name());
        return super.findMapList(sql, parameters);
    }

    public void delData(String handicapMemberId, String gameId) throws SQLException {
        String sql = "update ibm_hm_game_set set STATE_ = ? WHERE HANDICAP_MEMBER_ID_ = ? and GAME_ID_ = ? and STATE_ = ? ";
        List<Object> parameters = new ArrayList<>(4);
        parameters.add(IbmStateEnum.DEL.name());
        parameters.add(handicapMemberId);
        parameters.add(gameId);
        parameters.add(IbmStateEnum.OPEN.name());
        dao.execute(sql, parameters);

    }

    /**
     * 删除盘口游戏的所有数据
     *
     * @param handicapId 盘口主键
     * @param gameId     游戏主键
     * @param desc       描述
     * @param nowTime    更新时间
     */
    public void delData(String handicapId, String gameId, String desc, Date nowTime) throws SQLException {
        String sql = "update ibm_hm_game_set set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
                + " WHERE HANDICAP_ID_ = ? and GAME_ID_ = ? and STATE_ = ? ";
        List<Object> parameters = new ArrayList<>(7);
        parameters.add(IbmStateEnum.DEL.name());
        parameters.add(nowTime);
        parameters.add(System.currentTimeMillis());
        parameters.add(desc);
        parameters.add(handicapId);
        parameters.add(gameId);
        parameters.add(IbmStateEnum.OPEN.name());
        dao.execute(sql, parameters);
    }

    /**
     * 获取正在托管的盘口会员主键
     *
     * @param handicapId 盘口主键
     * @param gameId     游戏主键
     * @return 盘口会员主键列表
     */
    public List<String> listHostingHmId(String handicapId, String gameId) throws SQLException {
        String sql = "SELECT ihs.HANDICAP_MEMBER_ID_ as key_ FROM `ibm_hm_game_set` ihs LEFT JOIN ibm_hm_info ihi ON "
                + " ihs.HANDICAP_MEMBER_ID_ = ihi.HANDICAP_MEMBER_ID_ WHERE ihi.STATE_ = ? AND ihs.BET_STATE_ = ? AND "
                + " ihs.HANDICAP_ID_ = ? AND ihs.GAME_ID_ = ? AND ihs.STATE_ = ?";
        List<Object> parameters = new ArrayList<>(5);
        parameters.add(IbmStateEnum.LOGIN.name());
        parameters.add(IbmTypeEnum.TRUE.name());
        parameters.add(handicapId);
        parameters.add(gameId);
        parameters.add(IbmStateEnum.OPEN.name());
        return super.findStringList(sql, parameters);
    }

    /**
     * 修改盘口会员所有游戏投注状态
     *
     * @param list 盘口会员游戏设置信息
     */
    public void updateHmBetState(List<Map<String, Object>> list) throws SQLException {
        List<Object> parameterList = new ArrayList<>(8);
        StringBuilder sql = new StringBuilder();
        sql.append("update ibm_hm_game_set set BET_STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? where")
                .append(" IBM_HM_GAME_SET_ID_ in(");
        parameterList.add(IbmTypeEnum.FALSE.name());
        parameterList.add(new Date());
        parameterList.add(System.currentTimeMillis());
        parameterList.add("会员检验达到限制的盘口会员,停止投注状态");
        for (Map<String, Object> map : list) {
            parameterList.add(map.get("IBM_HM_GAME_SET_ID_"));
            sql.append("?,");
        }
        sql.replace(sql.length()-1,sql.length(),")");
        super.execute(sql.toString(), parameterList);
    }

    /**
     * 获取会员开启的游戏id
     * @param handicapMemberId  盘口会员id
     * @return
     */
    public List<Map<String, Object>> findHmGameIds(String handicapMemberId) throws SQLException {
        List<Object> parameterList = new ArrayList<>(4);
        String sql="select IBM_HM_GAME_SET_ID_,GAME_ID_ from ibm_hm_game_set where BET_STATE_ = ? and BET_MODE_ = ? and STATE_ = ? and HANDICAP_MEMBER_ID_=?";
        parameterList.add(IbmTypeEnum.TRUE.name());
        parameterList.add(IbmTypeEnum.REAL.name());
        parameterList.add(IbmStateEnum.OPEN.name());
        parameterList.add(handicapMemberId);
        return super.dao.findMapList(sql, parameterList);
    }

    /**
     * 修改游戏投注模式
     * @param handicapMemberId
     * @param gameId
     * @param betMode
     * @return
     */
    public void updateHmBetMode(String handicapMemberId, String gameId,String betMode) throws SQLException {
        String sql = "update ibm_hm_game_set set BET_MODE_=?,UPDATE_TIME_LONG_ = ? WHERE HANDICAP_MEMBER_ID_=? and GAME_ID_=? and STATE_=?";
        List<Object> parameterList = new ArrayList<>(5);
        parameterList.add(betMode);
        parameterList.add(System.currentTimeMillis());
        parameterList.add(handicapMemberId);
        parameterList.add(gameId);
        parameterList.add(IbmStateEnum.OPEN.name());
        super.dao.execute(sql,parameterList);
    }

    /**
     * 修改游戏投注状态
     * @param handicapMemberId
     * @param gameId
     * @param betState
     * @return
     */
    public void updateHmBetState(String handicapMemberId, String gameId,String betState) throws SQLException {
        String sql = "update ibm_hm_game_set set BET_STATE_=?,UPDATE_TIME_LONG_ = ? WHERE HANDICAP_MEMBER_ID_=? and GAME_ID_=? and STATE_=?";
        List<Object> parameterList = new ArrayList<>(5);
        parameterList.add(betState);
        parameterList.add(System.currentTimeMillis());
        parameterList.add(handicapMemberId);
        parameterList.add(gameId);
        parameterList.add(IbmStateEnum.OPEN.name());
        super.dao.execute(sql,parameterList);
    }

    /**
     * 新增会员游戏设置信息
     * @param appUserId     用户id
     * @param memberInfo    会员信息
     * @param gameIds       游戏ids
     * @param initGameSet   初始化信息
     */
    public void save(String appUserId, Map<String, Object> memberInfo, List<String> gameIds, Map<String, Object> initGameSet) throws SQLException {
        StringBuilder sql = new StringBuilder("insert into ibm_hm_game_set(IBM_HM_GAME_SET_ID_,HANDICAP_MEMBER_ID_,USER_ID_,"
                + "HANDICAP_ID_,GAME_ID_,BET_STATE_,BET_MODE_,BET_AUTO_STOP_,BET_AUTO_STOP_TIME_LONG_,BET_AUTO_START_,"
                + "BET_AUTO_START_TIME_LONG_,BET_SECOND_,SPLIT_TWO_SIDE_AMOUNT_,SPLIT_NUMBER_AMOUNT_,CREATE_TIME_,CREATE_TIME_LONG_,"
                + "UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) VALUES");
        List<Object> parameterList = new ArrayList<>();
        Date nowTime = new Date();
        for(String gameId:gameIds){
            sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
            parameterList.add(UUID.randomUUID().toString().replace("-", ""));
            parameterList.add(memberInfo.get("IBM_HANDICAP_MEMBER_ID_"));
            parameterList.add(appUserId);
            parameterList.add(memberInfo.get("HANDICAP_ID_"));
            parameterList.add(gameId);
            parameterList.add(initGameSet.get("BET_STATE_"));
            parameterList.add(initGameSet.get("BET_MODE_"));
            parameterList.add(initGameSet.get("BET_AUTO_STOP_"));
            parameterList.add(initGameSet.get("BET_AUTO_STOP_TIME_LONG_"));
            parameterList.add(initGameSet.get("BET_AUTO_START_"));
            parameterList.add(initGameSet.get("BET_AUTO_START_TIME_LONG_"));
            parameterList.add(initGameSet.get("BET_SECOND_"));
            parameterList.add(initGameSet.get("SPLIT_TWO_SIDE_AMOUNT_"));
            parameterList.add(initGameSet.get("SPLIT_NUMBER_AMOUNT_"));
            parameterList.add(nowTime);
            parameterList.add(nowTime.getTime());
            parameterList.add(nowTime);
            parameterList.add(nowTime.getTime());
            parameterList.add(IbmStateEnum.OPEN.name());
        }
        sql.deleteCharAt(sql.length() - 1);
        super.dao.execute(sql.toString(), parameterList);
    }

    /**
     * 删除会员游戏设置信息
     * @param memberInfo    会员信息
     * @param gameIds       游戏ids
     */
    public void delGameSet(Map<String, Object> memberInfo, List<String> gameIds) throws SQLException {
        List<Object> parameterList = new ArrayList<>();
        StringBuilder sql=new StringBuilder();
        sql.append("update ibm_hm_game_set set UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,STATE_=? where HANDICAP_MEMBER_ID_=? "
                + " and HANDICAP_ID_ = ? and GAME_ID_ in(");
        parameterList.add(new Date());
        parameterList.add(System.currentTimeMillis());
        parameterList.add(IbmStateEnum.DEL.name());
        parameterList.add(memberInfo.get("IBM_HANDICAP_MEMBER_ID_"));
        parameterList.add(memberInfo.get("HANDICAP_ID_"));
        for(String gameId:gameIds){
            sql.append("?,");
            parameterList.add(gameId);
        }
        sql.replace(sql.length()-1,sql.length(),")");
        super.dao.execute(sql.toString(),parameterList);
    }

    /**
     * 获取投注中游戏信息
     * @param memberInfo    会员信息
     * @param gameIds       游戏ids
     * @return
     */
    public List<Map<String, Object>> findBetInfo(Map<String, Object> memberInfo, List<String> gameIds) throws SQLException {
        List<Object> parameterList = new ArrayList<>();
        StringBuilder sql=new StringBuilder();
        sql.append("select HANDICAP_MEMBER_ID_,GAME_ID_ from ibm_hm_game_set where HANDICAP_MEMBER_ID_=? "
                + " and HANDICAP_ID_=? and BET_STATE_=? and GAME_ID_ in(");
        parameterList.add(memberInfo.get("IBM_HANDICAP_MEMBER_ID_"));
        parameterList.add(memberInfo.get("HANDICAP_ID_"));
        parameterList.add(IbmTypeEnum.TRUE.name());
        for(String gameId:gameIds){
            sql.append("?,");
            parameterList.add(gameId);
        }
        sql.replace(sql.length()-1,sql.length(),")");
        return super.dao.findMapList(sql.toString(),parameterList);
    }

    /**
     * 获取投注信息
     * @param handicapMemberId  盘口会员id
     * @return
     */
    public List<Map<String, Object>> findBetInfoByHmId(String handicapMemberId) throws SQLException {
        String sql="SELECT ig.GAME_NAME_,ig.GAME_CODE_,ihgs.BET_STATE_,ihgs.BET_AUTO_START_,ihgs.BET_AUTO_START_TIME_LONG_,"
                + "ihgs.BET_AUTO_STOP_,ihgs.BET_AUTO_STOP_TIME_LONG_ from ibm_hm_game_set ihgs "
                + " LEFT JOIN ibm_game ig ON ihgs.GAME_ID_=ig.IBM_GAME_ID_ WHERE ihgs.HANDICAP_MEMBER_ID_=? and ihgs.STATE_=?";
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(handicapMemberId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findMapList(sql,parameterList);
    }

    /**
     * 获取会员游戏codes
     * @param handicapMemberId  盘口会员id
     * @return
     */
    public List<String> listGameCodes(String handicapMemberId) throws SQLException {
        String sql = "SELECT GAME_CODE_ FROM ibm_handicap_game ihg LEFT JOIN "
                + " ibm_hm_game_set ihs ON ihg.HANDICAP_ID_ = ihs.HANDICAP_ID_ AND ihg.GAME_ID_ = ihs.GAME_ID_ "
                + " WHERE ihs.HANDICAP_MEMBER_ID_ = ? and ihg.STATE_ = ? AND ihs.STATE_ = ? ORDER BY SN_";
        List<Object> parameters = new ArrayList<>(3);
        parameters.add(handicapMemberId);
        parameters.add(IbmStateEnum.OPEN.name());
        parameters.add(IbmStateEnum.OPEN.name());
        return super.findStringList("GAME_CODE_",sql, parameters);
    }

    /**
     * 获取游戏设置主键
     *
     * @param handicapMemberId 盘口会员id
     * @param gameId           游戏Id
     * @return 游戏设置主键
     */
    public String findId(String handicapMemberId, String gameId) throws Exception {
        String sql = "select IBM_HM_GAME_SET_ID_ as key_ from ibm_hm_game_set where HANDICAP_MEMBER_ID_ = ? and GAME_ID_ = ? and STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(handicapMemberId);
        parameterList.add(gameId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.findString( sql, parameterList);
    }

}
