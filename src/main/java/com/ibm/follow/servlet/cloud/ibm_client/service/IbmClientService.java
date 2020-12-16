package com.ibm.follow.servlet.cloud.ibm_client.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_client.action.CancelClientAction;
import com.ibm.follow.servlet.cloud.ibm_client.entity.IbmClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmClientService extends BaseServiceProxy {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmClient对象数据
	 */
	public String save(IbmClient entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_client的 IBM_CLIENT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_client set state_='DEL' where IBM_CLIENT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CLIENT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client的 IBM_CLIENT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_client set state_='DEL' where IBM_CLIENT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_client的 IBM_CLIENT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_client where IBM_CLIENT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CLIENT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client的 IBM_CLIENT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_client where IBM_CLIENT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmClient实体信息
	 *
	 * @param entity IbmClient实体
	 */
	public void update(IbmClient entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_client表主键查找IbmClient实体
	 *
	 * @param id ibm_client 主键
	 * @return IbmClient实体
	 */
	public IbmClient find(String id) throws Exception {
		return (IbmClient) this.dao.find(IbmClient.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_client where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmClient数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmClient数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_client where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmClient.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用IbmClient数据信息
	 *
	 * @return 可用<IbmClient>数据信息
	 */
	public List<String> findObjectAll() throws Exception {
		String sql = "SELECT CLIENT_CODE_ FROM ibm_client where state_=? order by CREATE_TIME_LONG_ desc";
        List<Object> parameterList = new ArrayList<>(1);
        parameterList.add(IbmStateEnum.OPEN.name());
		return this.dao.findStringList("CLIENT_CODE_",sql,parameterList);
	}
	/**
	 * 获取可用的客户端信息
	 *
	 * @param handicapCode 盘口code
	 * @param type       客户类型
	 * @return 客户端信息
	 */
	public Map<String,Object>  findUsable(String handicapCode, IbmTypeEnum type) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT IBM_CLIENT_ID_,ic.CLIENT_CODE_ FROM `ibm_client` ic "
				+ " LEFT JOIN ibm_client_capacity icc ON ic.IBM_CLIENT_ID_ = icc.CLIENT_ID_ "
				+ " LEFT JOIN ibm_client_handicap_capacity ichc ON ic.IBM_CLIENT_ID_ = ichc.CLIENT_ID_ AND ichc.HANDICAP_CODE_ = ?");
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(handicapCode);
		switch (type) {
			case MEMBER:
				sql.append(" and ichc.CAPACITY_HM_MAX_>ichc.CAPACITY_HM_");
				break;
			case AGENT:
				sql.append(" and ichc.CAPACITY_HA_MAX_>ichc.CAPACITY_HA_");
				break;
			default:
				return null;
		}
		sql.append(" and ichc.CAPACITY_HANDICAP_MAX_>ichc.CAPACITY_HANDICAP_ WHERE ic.STATE_ = ? AND icc.STATE_ = ? AND ichc.STATE_ = ?  LIMIT 1");
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql.toString(), parameterList);
	}

	/**
	 * 获取客户端ID
	 * @param clientCode 客户端编码
	 * @return 客户端ID
	 */
	public String findId(String clientCode) throws SQLException {
		String sql = "SELECT IBM_CLIENT_ID_ FROM `ibm_client` where CLIENT_CODE_ = ? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(clientCode);
        parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("IBM_CLIENT_ID_",sql, parameterList);
	}
	/**
	 * 获取客户端IP
	 * @param clientCode 客户端编码
	 * @return IP
	 */
	public String findIp(String clientCode) throws SQLException {
		String sql = "SELECT REGISTER_IP_ as key_ FROM `ibm_client` where CLIENT_CODE_ = ? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(clientCode);
        parameterList.add(IbmStateEnum.DEL.name());
		return super.findString(sql, parameterList);
	}

	/**
	 * 查找客户端编码是否存在
	 *
	 * @param clientCode 客户端编码
	 * @return 存在 true
	 */
	public boolean findExist(Object clientCode) throws SQLException {
		String sql = "SELECT STATE_ FROM `ibm_client` where CLIENT_CODE_ = ?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(clientCode);
		Map<String, Object> map = super.dao.findMap(sql, parameterList);
		return ContainerTool.notEmpty(map) && IbmStateEnum.OPEN.equal(map.get("STATE_"));
	}

    /**
     * 查找客户端编码状态和是否存在
     * @param clientCode       客户端编码
     * @return 状态
     */
    public String findState(Object clientCode) throws SQLException {
        String sql = "SELECT STATE_ FROM `ibm_client` where CLIENT_CODE_ = ?";
        List<Object> parameterList = new ArrayList<>(1);
        parameterList.add(clientCode);
        return super.dao.findString("STATE_",sql,parameterList);
    }


	/**
	 * 查找客户端信息
	 *
	 * @param clientCode 客户端编码
	 * @return 客户端信息
	 */
	public Map<String, Object> findInfo(Object clientCode) throws SQLException {
		String sql = "SELECT IBM_CLIENT_ID_,STATE_ FROM `ibm_client` where CLIENT_CODE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(clientCode);
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 查找客户端信息
	 *
	 * @param clientCode 客户端编码
	 * @param ip         注册IP
	 * @return 客户端信息
	 */
	public Map<String, Object> findInfo(Object clientCode, String ip) throws SQLException {
		String sql = "SELECT IBM_CLIENT_ID_, REGISTER_IP_ID_,END_TIME_LONG_ FROM `ibm_client` where "
				+ " CLIENT_CODE_ = ? and REGISTER_IP_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(clientCode);
		parameterList.add(ip);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 注销客户端
	 * @param clientId 客户端主键
	 * @param time 注销时间
	 */
	public void cancelClient(Object clientId, Date time,IbmStateEnum state) throws SQLException {
		String sql = "UPDATE `ibm_client` set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
				+ "where IBM_CLIENT_ID_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(state.name());
		parameterList.add(time);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(CancelClientAction.class.getName().concat("注销客户端"));
		parameterList.add(clientId);
		super.dao.execute(sql,parameterList);
	}
    /**
     * 按照更新顺序查询所有可用Map信息
     *
     * @return 可用Map信息
     */
    public List<String> findAll() throws Exception {
        String sql = "SELECT IBM_CLIENT_ID_ FROM ibm_client where STATE_=?";
        List<Object> parameterList = new ArrayList<>(1);
        parameterList.add(IbmStateEnum.OPEN.name());
        return this.dao.findStringList("IBM_CLIENT_ID_",sql,parameterList);
    }

    /**
     * 激活客户端
     * @param clientCode    客户端编码
     */
    public void activateClient(Object clientCode) throws SQLException {
        String sql="update ibm_client set STATE_=?,UPDATE_TIME_LONG_=?,DESC_=? where CLIENT_CODE_=? and STATE_!=?";
        List<Object> parameterList = new ArrayList<>(5);
        parameterList.add(IbmStateEnum.OPEN.name());
        parameterList.add(System.currentTimeMillis());
        parameterList.add("激活客户端");
        parameterList.add(clientCode);
        parameterList.add(IbmStateEnum.DEL.name());
        super.dao.execute(sql,parameterList);
    }

    /**
     * 获取所有客户机的信息
     * @return
     */
    public List<Map<String, Object>> listShow(String key) throws SQLException {
        String sql="SELECT ic.REGISTER_IP_,ic.CLIENT_CODE_,ic.START_TIME_,ic.END_TIME_,ic.STATE_,icc.CLIENT_CAPACITY_MAX_,icc.CLIENT_CAPACITY_ FROM ibm_client ic"
                + " LEFT JOIN ibm_client_capacity icc ON ic.IBM_CLIENT_ID_=icc.CLIENT_ID_ WHERE ic.STATE_!=? AND icc.STATE_!=? ";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(IbmStateEnum.DEL.name());
        parameterList.add(IbmStateEnum.DEL.name());
        if(StringTool.notEmpty(key)){
            sql+=" and ic.REGISTER_IP_ like ?";
            key = "%" + key + "%";
            parameterList.add(key);
        }
        sql+="ORDER BY ic.CREATE_TIME_LONG_ ";
        return super.dao.findMapList(sql,parameterList);
    }

    /**
     * 分页获取客户机
     *
     * @param ip         ip
     * @param clientCode 客户机编码
     * @param pageIndex  页数
     * @param pageSize   条数
     * @return
     */
    public PageCoreBean listShow(String ip,	String clientCode ,Integer pageIndex, Integer pageSize) throws SQLException {
        String sqlCount="SELECT count(*) FROM (";
        String sql="SELECT ic.REGISTER_IP_,ic.CLIENT_CODE_,ic.STATE_,icc.CLIENT_CAPACITY_MAX_,icc.CLIENT_CAPACITY_,ich.UPDATE_TIME_LONG_,ich.STATE_ hearBeatState FROM ibm_client ic"
                + " LEFT JOIN ibm_client_capacity icc ON ic.IBM_CLIENT_ID_=icc.CLIENT_ID_ LEFT JOIN ibm_client_heartbeat ich ON ic.CLIENT_CODE_=ich.CLIENT_CODE_"
                + " WHERE (ic.STATE_=? or ic.STATE_=?) AND icc.STATE_!=? AND ich.STATE_!=?";
        List<Object> parameterList = new ArrayList<>(4);
        parameterList.add(IbmStateEnum.OPEN.name());
        parameterList.add(IbmStateEnum.CLOSE.name());
        parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
        String sqlPlus = "";
        if(StringTool.notEmpty(ip)){
            sqlPlus+=" and ic.REGISTER_IP_ like ?";
            ip = "%" + ip + "%";
            parameterList.add(ip);
        }
        if(StringTool.notEmpty(clientCode)){
            sqlPlus+=" and ic.CLIENT_CODE_ like ?";
            clientCode = "%" + clientCode + "%";
            parameterList.add(clientCode);
        }
        sqlPlus += " order by ic.UPDATE_TIME_LONG_ desc";
        sql += sqlPlus;
		sqlCount += sql+") t";
        return dao.page(sql, parameterList, pageIndex, pageSize, sqlCount, parameterList);
    }

    /**
     * 修改客户机状态
     * @param clientCode    客户端编码
     * @param state         状态
     */
    public void updateState(String clientCode, String state) throws SQLException {
        String sql="update ibm_client set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where CLIENT_CODE_=?";
        List<Object> parameterList = new ArrayList<>(4);
        parameterList.add(state);
        parameterList.add(new Date());
        parameterList.add(System.currentTimeMillis());
        parameterList.add(clientCode);
        super.dao.execute(sql,parameterList);
    }

    /**
     * 获取客户机列表
     * @param state
     * @return
     */
    public List<String> findByState(String state) throws SQLException {
        String sql = "SELECT CLIENT_CODE_ FROM ibm_client where state_=?";
        List<Object> parameterList = new ArrayList<>(1);
        parameterList.add(state);
        return this.dao.findStringList("CLIENT_CODE_",sql,parameterList);
    }
}
