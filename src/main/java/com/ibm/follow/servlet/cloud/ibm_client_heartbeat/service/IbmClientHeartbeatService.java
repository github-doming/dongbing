package com.ibm.follow.servlet.cloud.ibm_client_heartbeat.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_client_heartbeat.entity.IbmClientHeartbeat;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* IBM_客户端心跳IBM_CLIENT_HEARTBEAT 服务类
 * @author Robot
 */
public class IbmClientHeartbeatService extends BaseServicePlus {

	/**
	 * 保存IBM_客户端心跳IBM_CLIENT_HEARTBEAT 对象数据
	 * @param entity IbmClientHeartbeat对象数据
	 */
	public String save(IbmClientHeartbeat entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_client_heartbeat 的 IBM_CLIENT_HEARTBEAT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_client_heartbeat set state_='DEL' where IBM_CLIENT_HEARTBEAT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CLIENT_HEARTBEAT_ID_主键id数组的数据
	 * @param idArray 要删除 ibm_client_heartbeat 的 IBM_CLIENT_HEARTBEAT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_client_heartbeat set state_='DEL' where IBM_CLIENT_HEARTBEAT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibm_client_heartbeat  的 IBM_CLIENT_HEARTBEAT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_client_heartbeat where IBM_CLIENT_HEARTBEAT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CLIENT_HEARTBEAT_ID_主键id数组的数据
	 * @param idArray 要删除ibm_client_heartbeat 的 IBM_CLIENT_HEARTBEAT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_client_heartbeat where IBM_CLIENT_HEARTBEAT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmClientHeartbeat实体信息
	 * @param entity IBM_客户端心跳IBM_CLIENT_HEARTBEAT 实体
	 */
	public void update(IbmClientHeartbeat entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_client_heartbeat表主键查找 IBM_客户端心跳IBM_CLIENT_HEARTBEAT 实体
	 * @param id ibm_client_heartbeat 主键
	 * @return IBM_客户端心跳IBM_CLIENT_HEARTBEAT 实体
	 */
	public IbmClientHeartbeat find(String id) throws Exception {
		return (IbmClientHeartbeat) this.dao.find(IbmClientHeartbeat. class,id);

	}

    /**
     * 获取客户机心跳信息
     * @param clientCode    客户端编码
     * @return
     */
    public IbmClientHeartbeat findByClientCode(String clientCode) throws Exception {
        String sql="select * from ibm_client_heartbeat where CLIENT_CODE_=? and STATE_=?";
        List<Object> parameters=new ArrayList<>();
        parameters.add(clientCode);
        parameters.add(IbmStateEnum.OPEN.name());
        return (IbmClientHeartbeat) super.dao.findObject(IbmClientHeartbeat.class,sql,parameters);
    }
    /**
     * 获取最后一次心跳检测结果
     * @param clientCode        客户端编码
     * @return
     */
    public Map<String,Object> findResult(String clientCode) throws SQLException {
        String sql="select HEARTBEAT_RESULT_,UPDATE_TIME_LONG_ from ibm_log_heartbeat WHERE CLIENT_CODE_=? and STATE_=? ";
        List<Object> parameters=new ArrayList<>();
        parameters.add(clientCode);
        parameters.add(IbmStateEnum.OPEN.name());
        return super.dao.findMap(sql,parameters);
    }

    /**
     * 注销客户端
     * @param clientCode    客户机编码
     * @param nowTime       当前时间
     */
    public void cancelClient(Object clientCode, Date nowTime) throws SQLException {
        String sql = "UPDATE ibm_client_heartbeat SET STATE_ = ?,UPDATE_TIME_ = ?,"
                + " UPDATE_TIME_LONG_ = ?,DESC_ = ? WHERE CLIENT_CODE_ = ? and  STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(6);
        parameterList.add(IbmStateEnum.DEL.name());
        parameterList.add(nowTime);
        parameterList.add(nowTime.getTime());
        parameterList.add("注销客户端");
        parameterList.add(clientCode);
        parameterList.add(IbmStateEnum.OPEN.name());
        super.dao.execute(sql, parameterList);
    }

	/**
	 * 更新心跳状态 开启/停止心跳
	 * @param clientCode 客服端code
	 * @param state 状态
	 */
    public void updateState(String clientCode,String state) throws SQLException {
		String sql = "UPDATE ibm_client_heartbeat SET STATE_ = ?,UPDATE_TIME_ = ?,"
				+ " UPDATE_TIME_LONG_ = ?  WHERE CLIENT_CODE_ = ? and  STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(state);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(clientCode);
		parameterList.add(IbmStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);
	}
}
