package com.ibm.follow.servlet.client.ibmc_hm_set.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.client.ibmc_hm_set.entity.IbmcHmSet;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * IBMC_客户端会员设置 服务类
 *
 * @author Robot
 */
public class IbmcHmSetService extends BaseServicePlus {

	/**
	 * 保存IBMC_客户端会员设置 对象数据
	 *
	 * @param entity IbmcHmSet对象数据
	 */
	public String save(IbmcHmSet entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibmc_hm_set 的 IBMC_HM_SET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibmc_hm_set set state_='DEL' where IBMC_HM_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBMC_HM_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibmc_hm_set 的 IBMC_HM_SET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibmc_hm_set set state_='DEL' where IBMC_HM_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibmc_hm_set  的 IBMC_HM_SET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibmc_hm_set where IBMC_HM_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBMC_HM_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_hm_set 的 IBMC_HM_SET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibmc_hm_set where IBMC_HM_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmcHmSet实体信息
	 *
	 * @param entity IBMC_客户端会员设置 实体
	 */
	public void update(IbmcHmSet entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibmc_hm_set表主键查找 IBMC_客户端会员设置 实体
	 *
	 * @param id ibmc_hm_set 主键
	 * @return IBMC_客户端会员设置 实体
	 */
	public IbmcHmSet find(String id) throws Exception {
		return (IbmcHmSet) this.dao.find(IbmcHmSet.class, id);

	}

	/**
	 * 获取配置信息
	 *
	 * @param existHmId 存在盘口会员主键
	 * @return 配置信息
	 */
	public Map<String, Object> findInfo(String existHmId) throws SQLException {
		String sql = "select BET_RATE_T_,BET_MERGER_ from ibmc_hm_set where EXIST_HM_ID_ = ? and STATE_ = ?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(existHmId);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findMap(sql, parameters);
	}

	/**
	 * 更新盘口会员设置信息
	 *
	 * @param existHmId 存在盘口会员主键
	 * @param betRateTh 投注比例
	 * @param betMerger 投注合并
	 * @param nowTime   更新时间
	 */
	public void update(String existHmId, int betRateTh, String betMerger, Date nowTime) throws SQLException {
		String sql = "UPDATE ibmc_hm_set set  BET_RATE_T_ = ?, BET_MERGER_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?, "
				+ " DESC_ = ? where  EXIST_HM_ID_ = ? and STATE_ = ? ";
		List<Object> parameters = new ArrayList<>(7);
		parameters.add(betRateTh);
		parameters.add(betMerger);
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add("设置盘口详情");
		parameters.add(existHmId);
		parameters.add(IbmStateEnum.OPEN.name());
		super.execute(sql, parameters);
	}

    /**
     * 批量添加会员设置信息
     * @param hmSetInfos    会员设置信息
     * @param hmInfos       会员信息
     * @return
     */
    public void save(JSONArray hmSetInfos, Map<String, Object> hmInfos) throws SQLException {
        StringBuilder sql=new StringBuilder();
        Date nowTime=new Date();
        sql.append("insert into ibmc_hm_set(IBMC_HM_SET_ID_,EXIST_HM_ID_,BET_RATE_T_,BET_MERGER_,CREATE_TIME_,CREATE_TIME_LONG_,"
                + "UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) VALUES");
        List<Object> parameters = new ArrayList<>(7);
        for(int i=0;i<hmSetInfos.size();i++){
            JSONObject setInfos=hmSetInfos.getJSONObject(i);
            sql.append("(?,?,?,?,?,?,?,?,?),");
            parameters.add(RandomTool.getNumLetter32());
            parameters.add(hmInfos.get(setInfos.getString("HANDICAP_MEMBER_ID_")));
            parameters.add(setInfos.get("BET_RATE_T_"));
            parameters.add(setInfos.get("BET_MERGER_"));
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
