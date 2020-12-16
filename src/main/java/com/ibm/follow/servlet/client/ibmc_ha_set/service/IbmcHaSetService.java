package com.ibm.follow.servlet.client.ibmc_ha_set.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.client.ibmc_ha_set.entity.IbmcHaSet;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * IBMC_客户端代理设置 服务类
 *
 * @author Robot
 */
public class IbmcHaSetService extends BaseServicePlus {

	/**
	 * 保存IBMC_客户端代理设置 对象数据
	 *
	 * @param entity IbmcHaSet对象数据
	 */
	public String save(IbmcHaSet entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibmc_ha_set 的 IBMC_HA_SET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibmc_ha_set set state_='DEL' where IBMC_HA_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBMC_HA_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibmc_ha_set 的 IBMC_HA_SET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibmc_ha_set set state_='DEL' where IBMC_HA_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibmc_ha_set  的 IBMC_HA_SET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibmc_ha_set where IBMC_HA_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBMC_HA_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_ha_set 的 IBMC_HA_SET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibmc_ha_set where IBMC_HA_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmcHaSet实体信息
	 *
	 * @param entity IBMC_客户端代理设置 实体
	 */
	public void update(IbmcHaSet entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibmc_ha_set表主键查找 IBMC_客户端代理设置 实体
	 *
	 * @param id ibmc_ha_set 主键
	 * @return IBMC_客户端代理设置 实体
	 */
	public IbmcHaSet find(String id) throws Exception {
		return (IbmcHaSet) this.dao.find(IbmcHaSet.class, id);

	}

	/**
	 * 获取跟投信息
	 *
	 * @param existHaId 客户端代理设置主键
	 * @return 跟投信息
	 */
	public Map<String, Object> findFollowInfo(String existHaId) throws SQLException {
		String sql = "SELECT FOLLOW_MEMBER_TYPE_,MEMBER_LIST_INFO_ FROM `ibmc_ha_set` where EXIST_HA_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(existHaId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findMap(sql, parameterList);
	}

	/**
	 * 更新盘口会员设置信息
	 *
	 * @param existHaId            客户端代理设置主键
	 * @param followMemberListInfo 会员列表信息
	 * @param followMemberType     跟随会员类型
	 * @param nowTime              更新时间
	 */
	public void update(String existHaId, Object followMemberListInfo, String followMemberType, Date nowTime)
			throws SQLException {
		String sql = "UPDATE ibmc_ha_set SET FOLLOW_MEMBER_TYPE_ = ?,MEMBER_LIST_INFO_ = ?,UPDATE_TIME_ = ?, "
				+ " UPDATE_TIME_LONG_ = ?,DESC_ = ? where EXIST_HA_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(followMemberType);
		parameterList.add(followMemberListInfo);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add("设置盘口详情");
		parameterList.add(existHaId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.execute(sql, parameterList);
	}

    /**
     * 批量新增
     * @param haSetInfos    代理设置信息
     * @param existInfos    存在信息
     * @return
     */
    public void save(JSONArray haSetInfos, Map<String, Object> existInfos) throws SQLException {
        StringBuilder sql=new StringBuilder();
        Date nowTime=new Date();
        sql.append("insert into ibmc_ha_set(IBMC_HA_SET_ID_,EXIST_HA_ID_,FOLLOW_MEMBER_TYPE_,CREATE_TIME_,CREATE_TIME_LONG_,"
                + "UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) VALUES");
        List<Object> parameters = new ArrayList<>(7);
        for(int i=0;i<haSetInfos.size();i++){
            JSONObject setInfos=haSetInfos.getJSONObject(i);
            sql.append("(?,?,?,?,?,?,?,?),");
            parameters.add(RandomTool.getNumLetter32());
            parameters.add(existInfos.get(setInfos.getString("HANDICAP_AGENT_ID_")));
            parameters.add(setInfos.get("FOLLOW_MEMBER_TYPE_"));
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
