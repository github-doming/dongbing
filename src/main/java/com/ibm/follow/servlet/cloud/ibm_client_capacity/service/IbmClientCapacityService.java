package com.ibm.follow.servlet.cloud.ibm_client_capacity.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_client_capacity.entity.IbmClientCapacity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmClientCapacityService extends BaseService {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmClientCapacity对象数据
	 */
	public String save(IbmClientCapacity entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_client_capacity的 IBM_CLIENT_CAPACITY_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_client_capacity set state_='DEL' where IBM_CLIENT_CAPACITY_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CLIENT_CAPACITY_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client_capacity的 IBM_CLIENT_CAPACITY_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_client_capacity set state_='DEL' where IBM_CLIENT_CAPACITY_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_client_capacity的 IBM_CLIENT_CAPACITY_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_client_capacity where IBM_CLIENT_CAPACITY_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CLIENT_CAPACITY_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client_capacity的 IBM_CLIENT_CAPACITY_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_client_capacity where IBM_CLIENT_CAPACITY_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmClientCapacity实体信息
	 *
	 * @param entity IbmClientCapacity实体
	 */
	public void update(IbmClientCapacity entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_client_capacity表主键查找IbmClientCapacity实体
	 *
	 * @param id ibm_client_capacity 主键
	 * @return IbmClientCapacity实体
	 */
	public IbmClientCapacity find(String id) throws Exception {
		return (IbmClientCapacity) this.dao.find(IbmClientCapacity.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_client_capacity where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_capacity  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_capacity  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmClientCapacity数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmClientCapacity数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_client_capacity where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_capacity  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_capacity  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmClientCapacity.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_capacity  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmClientCapacity数据信息
	 *
	 * @return 可用<IbmClientCapacity>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_capacity  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmClientCapacity.class, sql);
	}
	/**
	 * 更新容量
	 *
	 * @param clientId    客户端主键
	 * @param clientCode  客户端编码
	 * @param exitsCount  客户端使用容量
	 * @param capacityMax 客户端最大容量
	 * @param nowTime     更新时间
	 * @return 客户端容量id clientCapacityId
	 */
	public String updateCapacity(String clientId, String clientCode, int capacityMax, int exitsCount, Date nowTime)
			throws Exception {
		String sql = "SELECT IBM_CLIENT_CAPACITY_ID_ FROM ibm_client_capacity WHERE CLIENT_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(clientId);
		parameterList.add(IbmStateEnum.DEL.name());
		String id = super.dao.findString("IBM_CLIENT_CAPACITY_ID_", sql, parameterList);

		if (StringTool.isEmpty(id)) {
			IbmClientCapacity capacity = new IbmClientCapacity();
			capacity.setClientId(clientId);
			capacity.setClientCode(clientCode);
			capacity.setClientCapacityMax(capacityMax);
			capacity.setClientCapacity(exitsCount);
			capacity.setCreateTime(nowTime);
			capacity.setCreateTimeLong(System.currentTimeMillis());
			capacity.setState(capacityMax > exitsCount ? IbmStateEnum.OPEN.name() : IbmStateEnum.FULL.name());
			id = this.save(capacity);
		} else {
			sql = "UPDATE ibm_client_capacity SET CLIENT_CAPACITY_MAX_ = ?,CLIENT_CAPACITY_ = ?,UPDATE_TIME_ = ?,"
					+ " UPDATE_TIME_LONG_ = ?,STATE_ = ? WHERE IBM_CLIENT_CAPACITY_ID_ = ?";
			parameterList.clear();
			parameterList.add(capacityMax);
			parameterList.add(exitsCount);
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			if (capacityMax > exitsCount) {
				parameterList.add(IbmStateEnum.OPEN.name());
			} else {
				parameterList.add(IbmStateEnum.FULL.name());
			}
			parameterList.add(id);
			super.dao.execute(sql, parameterList);
		}
		return id;
	}

	/**
	 * 更新容量
	 *
	 * @param clientId    客户端id
	 * @param capacityMax 最大容量
	 * @param exitsCount  使用容量
	 * @param nowTime     更新时间
	 */
	public void updateCapacity(String clientId, int capacityMax, int exitsCount, Date nowTime) throws SQLException {
		String sql = "UPDATE ibm_client_capacity SET CLIENT_CAPACITY_MAX_ = ?,CLIENT_CAPACITY_ = ?,UPDATE_TIME_ = ?,"
				+ " UPDATE_TIME_LONG_ = ?,DESC_ = ? WHERE CLIENT_ID_ = ? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(capacityMax);
		parameterList.add(exitsCount);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add("客户端心跳检测-更新容量");
		parameterList.add(clientId);
        parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 更新容量
	 *
	 * @param clientCode    客户端Code
	 * @param capacityMax 最大容量
	 */
	public void updateCapacity(String clientCode, int capacityMax) throws SQLException {
		String sql = "UPDATE ibm_client_capacity SET CLIENT_CAPACITY_MAX_ = ?,UPDATE_TIME_ = ?,"
				+ " UPDATE_TIME_LONG_ = ?,DESC_ = ? WHERE CLIENT_CODE_ = ? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(capacityMax);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add("平台-修改容量");
		parameterList.add(clientCode);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 注销客户端
	 *
	 * @param clientId 客户端主键
	 * @param time     注销时间
	 */
	public void cancelClient(Object clientId, Date time) throws SQLException {
		String sql = "UPDATE ibm_client_capacity SET STATE_ = ?,UPDATE_TIME_ = ?,"
				+ " UPDATE_TIME_LONG_ = ?,DESC_ = ? WHERE CLIENT_ID_ = ? and  STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(time);
		parameterList.add(System.currentTimeMillis());
		parameterList.add("注销客户端");
		parameterList.add(clientId);
		parameterList.add(IbmStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 获取容量信息
	 *
	 * @param handicapCode 盘口信息
	 * @param type         用户类别
	 * @return 容量信息
	 */
	public List<Map<String, Object>> listCapacityInfo(String handicapCode, IbmTypeEnum type) throws SQLException {
		String sql = "SELECT icc.CLIENT_ID_, icc.CLIENT_CODE_, icc.CLIENT_CAPACITY_, icc.CLIENT_CAPACITY_MAX_, "
				+ " ichc.CAPACITY_HANDICAP_, ichc.CAPACITY_HANDICAP_MAX_";
		switch (type) {
			case MEMBER:
				sql += " ,ichc.CAPACITY_HM_ AS CAPACITY_,ichc.CAPACITY_HM_MAX_ AS CAPACITY_MAX_ ";
				break;
			case AGENT:
				sql += " ,ichc.CAPACITY_HA_ AS CAPACITY_,ichc.CAPACITY_HA_MAX_ AS CAPACITY_MAX_ ";
				break;
			default:
				throw new IllegalArgumentException("错误的用户类型传输".concat(type.name()));
		}
		sql += "FROM ibm_client_capacity icc LEFT JOIN ibm_client_handicap_capacity ichc ON "
				+ " icc.IBM_CLIENT_CAPACITY_ID_ = ichc.CLIENT_CAPACITY_ID_ AND ichc.HANDICAP_CODE_ = ? WHERE "
				+ " icc.CLIENT_CAPACITY_ < icc.CLIENT_CAPACITY_MAX_ AND ichc.CAPACITY_HANDICAP_ < ichc.CAPACITY_HANDICAP_MAX_ ";
		switch (type) {
			case MEMBER:
				sql += " AND ichc.CAPACITY_HM_ < ichc.CAPACITY_HM_MAX_ ";
				break;
			case AGENT:
				sql += " AND ichc.CAPACITY_HA_ < ichc.CAPACITY_HA_MAX_  ";
				break;
			default:
				throw new IllegalArgumentException("错误的用户类型传输".concat(type.name()));
		}
		sql += " AND icc.STATE_ = ? AND ichc.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(handicapCode);
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取验证数据容量信息
	 *
	 * @param handicapCode 盘口信息
	 * @param type         用户类别
	 * @return 容量信息
	 */
	public List<Map<String, Object>> listVerifyCapacityInfo(String handicapCode, IbmTypeEnum type) throws SQLException {
		String sql = "SELECT icc.CLIENT_ID_, icc.CLIENT_CODE_, icc.CLIENT_CAPACITY_, icc.CLIENT_CAPACITY_MAX_, "
				+ " ichc.CAPACITY_HANDICAP_, ichc.CAPACITY_HANDICAP_MAX_";
		switch (type) {
			case MEMBER:
				sql += " ,ichc.CAPACITY_HM_ AS CAPACITY_,ichc.CAPACITY_HM_MAX_ AS CAPACITY_MAX_ ";
				break;
			case AGENT:
				sql += " ,ichc.CAPACITY_HA_ AS CAPACITY_,ichc.CAPACITY_HA_MAX_ AS CAPACITY_MAX_ ";
				break;
			default:
				throw new IllegalArgumentException("错误的用户类型传输".concat(type.name()));
		}
		sql += "FROM ibm_client_capacity icc LEFT JOIN ibm_client_handicap_capacity ichc ON "
				+ " icc.IBM_CLIENT_CAPACITY_ID_ = ichc.CLIENT_CAPACITY_ID_ AND ichc.HANDICAP_CODE_ = ? WHERE "
				+ " icc.CLIENT_CAPACITY_ < icc.CLIENT_CAPACITY_MAX_ - 3 AND ichc.CAPACITY_HANDICAP_ < ichc.CAPACITY_HANDICAP_MAX_ - 3 ";
		switch (type) {
			case MEMBER:
				sql += " AND ichc.CAPACITY_HM_ < ichc.CAPACITY_HM_MAX_ ";
				break;
			case AGENT:
				sql += " AND ichc.CAPACITY_HA_ < ichc.CAPACITY_HA_MAX_  ";
				break;
			default:
				throw new IllegalArgumentException("错误的用户类型传输".concat(type.name()));
		}
		sql += " AND icc.STATE_ = ? AND ichc.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(handicapCode);
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取可用的客户端信息
	 *
	 * @param handicapCode 盘口code
	 * @param type         客户类型
	 * @return 客户端信息
	 */
	public Map<String, Object> findUsable(String handicapCode, IbmTypeEnum type) throws SQLException {
		String sql = "SELECT icc.CLIENT_ID_, icc.CLIENT_CODE_ FROM ibm_client_capacity icc "
				+ " LEFT JOIN ibm_client_handicap_capacity ichc ON "
				+ " icc.IBM_CLIENT_CAPACITY_ID_ = ichc.CLIENT_CAPACITY_ID_ AND ichc.HANDICAP_CODE_ = ? WHERE "
				+ " icc.CLIENT_CAPACITY_ < icc.CLIENT_CAPACITY_MAX_ AND ichc.CAPACITY_HANDICAP_ < ichc.CAPACITY_HANDICAP_MAX_ ";
		switch (type) {
			case MEMBER:
				sql += " AND ichc.CAPACITY_HM_ < ichc.CAPACITY_HM_MAX_ ";
				break;
			case AGENT:
				sql += " AND ichc.CAPACITY_HA_ < ichc.CAPACITY_HA_MAX_  ";
				break;
			default:
				throw new IllegalArgumentException("错误的用户类型传输".concat(type.name()));
		}
		sql += " AND icc.STATE_ = ? AND ichc.STATE_ = ?  LIMIT 1";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(handicapCode);
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 获取验证可用的客户端信息
	 *
	 * @param handicapCode 盘口code
	 * @param type         客户类型
	 * @return 客户端信息
	 */
	public Map<String, Object> findVerifyUsable(String handicapCode, IbmTypeEnum type) throws SQLException {
		String sql = "SELECT icc.CLIENT_ID_, icc.CLIENT_CODE_ FROM ibm_client_capacity icc "
				+ " LEFT JOIN ibm_client_handicap_capacity ichc ON "
				+ " icc.IBM_CLIENT_CAPACITY_ID_ = ichc.CLIENT_CAPACITY_ID_ AND ichc.HANDICAP_CODE_ = ? WHERE "
				+ " icc.CLIENT_CAPACITY_ < icc.CLIENT_CAPACITY_MAX_ - 3 AND ichc.CAPACITY_HANDICAP_ < ichc.CAPACITY_HANDICAP_MAX_  - 3 ";
		switch (type) {
			case MEMBER:
				sql += " AND ichc.CAPACITY_HM_ < ichc.CAPACITY_HM_MAX_ ";
				break;
			case AGENT:
				sql += " AND ichc.CAPACITY_HA_ < ichc.CAPACITY_HA_MAX_  ";
				break;
			default:
				throw new IllegalArgumentException("错误的用户类型传输".concat(type.name()));
		}
		sql += " AND icc.STATE_ = ? AND ichc.STATE_ = ?  LIMIT 1";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(handicapCode);
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}

    /**
     * 清除过期客户端的容量信息
     * @param expiredClientIds      过期客户端ids
     */
    public void clearClientInfo(Set<Object> expiredClientIds) throws SQLException {
        StringBuilder sql=new StringBuilder();
        List<Object> parameterList = new ArrayList<>();
        sql.append("update ibm_client_capacity set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,DESC_=? where STATE_!=? and CLIENT_ID_ in (");
        parameterList.add(IbmStateEnum.DEL.name());
        parameterList.add(new Date());
        parameterList.add(System.currentTimeMillis());
        parameterList.add("客户端管理清除容量信息");
        parameterList.add(IbmStateEnum.DEL.name());
        for(Object clientId:expiredClientIds) {
            sql.append("?,");
            parameterList.add(clientId);
        }
        sql.replace(sql.length()-1,sql.length(),")");
        super.dao.execute(sql.toString(),parameterList);
    }

    /**
     * 获取客户机容量信息
     * @param clientCode      客户端编码
     * @return
     */
    public Map<String, Object> findcapacityInfo(String clientCode) throws SQLException {
        String sql="select ic.REGISTER_IP_,ic.CLIENT_CODE_,icc.CLIENT_CAPACITY_MAX_,ic.STATE_ from ibm_client ic"
                + " LEFT JOIN ibm_client_capacity icc ON ic.IBM_CLIENT_ID_=icc.CLIENT_ID_"
                + " where icc.CLIENT_CODE_=? ";
        List<Object> parameterList = new ArrayList<>(1);
        parameterList.add(clientCode);
        return super.dao.findMap(sql,parameterList);
    }
}
