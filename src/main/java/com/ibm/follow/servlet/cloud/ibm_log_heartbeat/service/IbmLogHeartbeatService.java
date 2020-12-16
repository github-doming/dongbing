package com.ibm.follow.servlet.cloud.ibm_log_heartbeat.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_log_heartbeat.entity.IbmLogHeartbeat;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* IBM_心跳检测日志IBM_LOG_HEARTBEAT 服务类
 * @author Robot
 */
public class IbmLogHeartbeatService extends BaseServicePlus {

	/**
	 * 保存IBM_心跳检测日志IBM_LOG_HEARTBEAT 对象数据
	 * @param entity IbmLogHeartbeat对象数据
	 */
	public String save(IbmLogHeartbeat entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_log_heartbeat 的 IBM_LOG_HEARTBEAT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_log_heartbeat set state_='DEL' where IBM_LOG_HEARTBEAT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_LOG_HEARTBEAT_ID_主键id数组的数据
	 * @param idArray 要删除 ibm_log_heartbeat 的 IBM_LOG_HEARTBEAT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_log_heartbeat set state_='DEL' where IBM_LOG_HEARTBEAT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibm_log_heartbeat  的 IBM_LOG_HEARTBEAT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_log_heartbeat where IBM_LOG_HEARTBEAT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_LOG_HEARTBEAT_ID_主键id数组的数据
	 * @param idArray 要删除ibm_log_heartbeat 的 IBM_LOG_HEARTBEAT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_log_heartbeat where IBM_LOG_HEARTBEAT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmLogHeartbeat实体信息
	 * @param entity IBM_心跳检测日志IBM_LOG_HEARTBEAT 实体
	 */
	public void update(IbmLogHeartbeat entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_log_heartbeat表主键查找 IBM_心跳检测日志IBM_LOG_HEARTBEAT 实体
	 * @param id ibm_log_heartbeat 主键
	 * @return IBM_心跳检测日志IBM_LOG_HEARTBEAT 实体
	 */
	public IbmLogHeartbeat find(String id) throws Exception {
		return (IbmLogHeartbeat) this.dao.find(IbmLogHeartbeat. class,id);

	}

    /**
     * 心跳结果
     * @param heartbeatResult   心跳结果
     * @return
     */
    public List<Map<String, Object>> findAll(String heartbeatResult) throws SQLException {
        StringBuilder sql=new StringBuilder();
        List<Object> parameters=new ArrayList<>();
        sql.append("select CLIENT_CODE_,HEARTBEAT_RESULT_,ERROR_CONTEXT_,CREATE_TIME_ from ibm_log_heartbeat");
        if(StringTool.notEmpty(heartbeatResult)){
            sql.append(" where HEARTBEAT_RESULT_=? ");
            parameters.add(heartbeatResult);
        }
        sql.append(" ORDER BY CREATE_TIME_LONG_ DESC LIMIT 20");
        return super.dao.findMapList(sql.toString(),parameters);
    }
}
