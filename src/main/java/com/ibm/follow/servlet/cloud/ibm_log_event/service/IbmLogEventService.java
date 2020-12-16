package com.ibm.follow.servlet.cloud.ibm_log_event.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_log_event.entity.IbmLogEvent;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* IBM_事件错误日志IBM_LOG_EVENT 服务类
 * @author Robot
 */
public class IbmLogEventService extends BaseServicePlus {

	/**
	 * 保存IBM_事件错误日志IBM_LOG_EVENT 对象数据
	 * @param entity IbmLogEvent对象数据
	 */
	public String save(IbmLogEvent entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_log_event 的 IBM_LOG_EVENT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_log_event set state_='DEL' where IBM_LOG_EVENT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_LOG_EVENT_ID_主键id数组的数据
	 * @param idArray 要删除 ibm_log_event 的 IBM_LOG_EVENT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_log_event set state_='DEL' where IBM_LOG_EVENT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibm_log_event  的 IBM_LOG_EVENT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_log_event where IBM_LOG_EVENT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_LOG_EVENT_ID_主键id数组的数据
	 * @param idArray 要删除ibm_log_event 的 IBM_LOG_EVENT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_log_event where IBM_LOG_EVENT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmLogEvent实体信息
	 * @param entity IBM_事件错误日志IBM_LOG_EVENT 实体
	 */
	public void update(IbmLogEvent entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_log_event表主键查找 IBM_事件错误日志IBM_LOG_EVENT 实体
	 * @param id ibm_log_event 主键
	 * @return IBM_事件错误日志IBM_LOG_EVENT 实体
	 */
	public IbmLogEvent find(String id) throws Exception {
		return (IbmLogEvent) this.dao.find(IbmLogEvent. class,id);

	}

	/**
	 * 保存错误日志
	 * @param logEvent 错误日志
	 */
	public void saveError(IbmLogEvent logEvent) throws SQLException {
		String sql = "INSERT INTO ibm_log_event (IBM_LOG_EVENT_ID_,THREAD_NAME_,EVENT_CODE_,EXECUTE_CLASS_CODE_,"
				+ "ERROR_CONTEXT_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_LONG_,STATE_) VALUES (?,?,?,?,?,?,?,?,?)";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(RandomTool.getNumLetter32());
		parameterList.add(logEvent.getThreadName());
		parameterList.add(logEvent.getEventCode());
		parameterList.add(logEvent.getExecuteClassCode());
		parameterList.add(logEvent.getErrorContext());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(IbmStateEnum.OPEN.name());
		super.execute(sql,parameterList);
	}

    /**
     * 事件日志信息
     * @param eventCode
     * @return
     */
    public List<Map<String, Object>> findAll(String eventCode) throws SQLException {
        StringBuilder sql=new StringBuilder();
        List<Object> parameters=new ArrayList<>();
        sql.append("select THREAD_NAME_,EVENT_CODE_,EXECUTE_CLASS_CODE_,ERROR_CONTEXT_,CREATE_TIME_ from ibm_log_event");
        if(StringTool.notEmpty(eventCode)){
            sql.append(" where EVENT_CODE_=? ");
            parameters.add(eventCode);
        }
        sql.append(" ORDER BY CREATE_TIME_LONG_ DESC LIMIT 20");
        return super.dao.findMapList(sql.toString(),parameters);
    }
}
