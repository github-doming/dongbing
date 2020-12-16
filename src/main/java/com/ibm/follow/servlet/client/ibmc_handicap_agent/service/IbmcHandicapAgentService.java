package com.ibm.follow.servlet.client.ibmc_handicap_agent.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.client.ibmc_handicap_agent.entity.IbmcHandicapAgent;
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
public class IbmcHandicapAgentService extends BaseService {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 * @param entity IbmcHandicapAgent对象数据
	 */
	public String save(IbmcHandicapAgent entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibmc_handicap_agent的 IBMC_HANDICAP_AGENT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibmc_handicap_agent set state_='DEL' where IBMC_HANDICAP_AGENT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBMC_HANDICAP_AGENT_ID_主键id数组的数据
	 * @param idArray 要删除ibmc_handicap_agent的 IBMC_HANDICAP_AGENT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibmc_handicap_agent set state_='DEL' where IBMC_HANDICAP_AGENT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除ibmc_handicap_agent的 IBMC_HANDICAP_AGENT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibmc_handicap_agent where IBMC_HANDICAP_AGENT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBMC_HANDICAP_AGENT_ID_主键id数组的数据
	 * @param idArray 要删除ibmc_handicap_agent的 IBMC_HANDICAP_AGENT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibmc_handicap_agent where IBMC_HANDICAP_AGENT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmcHandicapAgent实体信息
	 * @param entity IbmcHandicapAgent实体
	 */
	public void update(IbmcHandicapAgent entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibmc_handicap_agent表主键查找IbmcHandicapAgent实体
	 * @param id ibmc_handicap_agent 主键
	 * @return IbmcHandicapAgent实体
	 */
	public IbmcHandicapAgent find(String id) throws Exception {
		return (IbmcHandicapAgent) this.dao.find(IbmcHandicapAgent. class,id);

	}

	/**
	 * 获取分页Map数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页Map数据
	 */
	public PageCoreBean find(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibmc_handicap_agent where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_handicap_agent  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_handicap_agent  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmcHandicapAgent数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页IbmcHandicapAgent数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibmc_handicap_agent where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_handicap_agent  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_handicap_agent  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmcHandicapAgent. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibmc_handicap_agent  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmcHandicapAgent数据信息
	 * @return 可用<IbmcHandicapAgent>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibmc_handicap_agent  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmcHandicapAgent. class,sql);
	}
	/**
	 * 获取盘口代理信息
	 * @param existHaId	已存在盘口代理id
	 * @return
	 */
	public IbmcHandicapAgent findExist(String existHaId) throws Exception {
		String sql="select * from ibmc_handicap_agent where EXIST_HA_ID_=? and STATE_!=?";
		List<Object> parameters=new ArrayList<>(2);
		parameters.add(existHaId);
		parameters.add(IbmStateEnum.DEL.name());
		return (IbmcHandicapAgent)super.dao.findObject(IbmcHandicapAgent.class,sql,parameters);
	}

    /**
     * 批量新增
     * @param handicapAgentInfos    代理信息
     * @param existInfos            存在信息
     * @return
     */
    public void save(JSONArray handicapAgentInfos, Map<String, Object> existInfos) throws SQLException {
        StringBuilder sql=new StringBuilder();
        List<Object> parameters = new ArrayList<>();
        Date nowTime=new Date();
        sql.append("insert into ibmc_handicap_agent (IBMC_HANDICAP_AGENT_ID_,EXIST_HA_ID_,HANDICAP_AGENT_ID_,AGENT_ACCOUNT_,"
                + "AGENT_PASSWORD_,HANDICAP_URL_,HANDICAP_CAPTCHA_,CREATE_TIME_,CREATE_TIME_LONG_,"
                + "UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) VALUES");
        for(int i=0;i<handicapAgentInfos.size();i++){
            JSONObject handicapAgentInfo=handicapAgentInfos.getJSONObject(i);
            sql.append("(?,?,?,?,?,?,?,?,?,?,?,?),");
            parameters.add(RandomTool.getNumLetter32());
            parameters.add(existInfos.get(handicapAgentInfo.get("HANDICAP_AGENT_ID_")));
            parameters.add(handicapAgentInfo.get("HANDICAP_AGENT_ID_"));
            parameters.add(handicapAgentInfo.get("AGENT_ACCOUNT_"));
            parameters.add(handicapAgentInfo.get("AGENT_PASSWORD_"));
            parameters.add(handicapAgentInfo.get("HANDICAP_URL_"));
            parameters.add(handicapAgentInfo.get("HANDICAP_CAPTCHA_"));
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
