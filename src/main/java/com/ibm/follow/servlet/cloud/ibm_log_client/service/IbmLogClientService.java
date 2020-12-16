package com.ibm.follow.servlet.cloud.ibm_log_client.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.follow.servlet.cloud.ibm_log_client.entity.IbmLogClient;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* IBM_客户端操作日志IBM_LOG_CLIENT 服务类
 * @author Robot
 */
public class IbmLogClientService extends BaseServicePlus {

	/**
	 * 保存IBM_客户端操作日志IBM_LOG_CLIENT 对象数据
	 * @param entity IbmLogClient对象数据
	 */
	public String save(IbmLogClient entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_log_client 的 IBM_LOG_CLIENT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_log_client set state_='DEL' where IBM_LOG_CLIENT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_LOG_CLIENT_ID_主键id数组的数据
	 * @param idArray 要删除 ibm_log_client 的 IBM_LOG_CLIENT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_log_client set state_='DEL' where IBM_LOG_CLIENT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibm_log_client  的 IBM_LOG_CLIENT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_log_client where IBM_LOG_CLIENT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_LOG_CLIENT_ID_主键id数组的数据
	 * @param idArray 要删除ibm_log_client 的 IBM_LOG_CLIENT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_log_client where IBM_LOG_CLIENT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmLogClient实体信息
	 * @param entity IBM_客户端操作日志IBM_LOG_CLIENT 实体
	 */
	public void update(IbmLogClient entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_log_client表主键查找 IBM_客户端操作日志IBM_LOG_CLIENT 实体
	 * @param id ibm_log_client 主键
	 * @return IBM_客户端操作日志IBM_LOG_CLIENT 实体
	 */
	public IbmLogClient find(String id) throws Exception {
		return (IbmLogClient) this.dao.find(IbmLogClient. class,id);

	}

    /**
     * 获取前20条日志信息
     * @param method      方法类型
     * @return
     */
    public List<Map<String, Object>> findAll(String method) throws SQLException {
        StringBuilder sql=new StringBuilder();
        List<Object> parameters=new ArrayList<>();
        sql.append("select CLIENT_CODE_,METHOD_,RESULT_,CREATE_TIME_ from ibm_log_client");
        if(StringTool.notEmpty(method)){
            sql.append(" where METHOD_=? ");
            parameters.add(method);
        }
        sql.append(" ORDER BY CREATE_TIME_LONG_ DESC LIMIT 20");
        return super.dao.findMapList(sql.toString(),parameters);
    }
}
