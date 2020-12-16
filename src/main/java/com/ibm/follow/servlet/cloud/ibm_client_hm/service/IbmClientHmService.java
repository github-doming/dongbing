package com.ibm.follow.servlet.cloud.ibm_client_hm.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_client_hm.entity.IbmClientHm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmClientHmService extends BaseServicePlus {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmClientHm对象数据
	 */
	public String save(IbmClientHm entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_client_hm的 IBM_CLIENT_HM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_client_hm set state_='DEL' where IBM_CLIENT_HM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CLIENT_HM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client_hm的 IBM_CLIENT_HM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_client_hm set state_='DEL' where IBM_CLIENT_HM_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_client_hm的 IBM_CLIENT_HM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_client_hm where IBM_CLIENT_HM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CLIENT_HM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client_hm的 IBM_CLIENT_HM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_client_hm where IBM_CLIENT_HM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmClientHm实体信息
	 *
	 * @param entity IbmClientHm实体
	 */
	public void update(IbmClientHm entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_client_hm表主键查找IbmClientHm实体
	 *
	 * @param id ibm_client_hm 主键
	 * @return IbmClientHm实体
	 */
	public IbmClientHm find(String id) throws Exception {
		return (IbmClientHm) this.dao.find(IbmClientHm.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_client_hm where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_hm  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmClientHm数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmClientHm数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_client_hm where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_hm  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmClientHm.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmClientHm数据信息
	 *
	 * @return 可用<IbmClientHm>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmClientHm.class, sql);
	}

	/**
	 * 保存客户端盘口会员
	 *
	 * @param clientId         客户端主键
	 * @param handicapMemberId 盘口会员主键
	 * @param existHmId        已存在盘口会员主键
	 * @param clientCode       客户端编码
	 * @param handicapCode     盘口编码
	 * @return 客户端盘口会员id
	 */
	public String save(String clientId, String handicapMemberId, String existHmId, String clientCode,
			String handicapCode) throws Exception {
		IbmClientHm clientHm = new IbmClientHm();
		clientHm.setHandicapMemberId(handicapMemberId);
		clientHm.setExistHmId(existHmId);
		clientHm.setClientId(clientId);
		clientHm.setClientCode(clientCode);
		clientHm.setHandicapCode(handicapCode);
		clientHm.setCreateTime(new Date());
		clientHm.setCreateTimeLong(System.currentTimeMillis());
		clientHm.setUpdateTimeLong(System.currentTimeMillis());
		clientHm.setState(IbmStateEnum.OPEN.name());
		return this.save(clientHm);
	}
	/**
	 * 通过盘口会员主键查询客户端已存在盘口会员主键
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 客户端已存在盘口会员
	 */
	public Map<String, Object> findExistHmId(String handicapMemberId) throws SQLException {
		String sql = "select EXIST_HM_ID_,CLIENT_CODE_ from ibm_client_hm where HANDICAP_MEMBER_ID_= ? and STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}
	/**
	 * 通过盘口会员主键查询并删除已存在盘口会员信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param desc             更新描述
	 * @return 客户端已存在盘口会员主键
	 */
	public void updateByHmId(String handicapMemberId, Date nowTime, String desc) throws SQLException {
		List<Object> parameterList = new ArrayList<>(6);
		//更新数据
		String sql = "update ibm_client_hm set UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,STATE_ = ?,DESC_ = ? "
				+ " where HANDICAP_MEMBER_ID_ = ? and STATE_ = ?";
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(IbmStateEnum.CLOSE.name());
		parameterList.add(desc);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);
	}
    /**
     * 批量修改
     * @param handicapMemberIds     盘口会员ids
     */
    public void updateByHmIds(List<String> handicapMemberIds) throws SQLException {
	    StringBuilder sql=new StringBuilder();
	    sql.append("update ibm_client_hm set UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,STATE_ = ?,DESC_ = ?"
                + " where STATE_=? and HANDICAP_MEMBER_ID_ in(");
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(new Date());
        parameterList.add(System.currentTimeMillis());
        parameterList.add(IbmStateEnum.CLOSE.name());
        parameterList.add("切换客户机登出");
        parameterList.add(IbmStateEnum.OPEN.name());
	    for(String handicapMemberId:handicapMemberIds){
	        sql.append("?,");
	        parameterList.add(handicapMemberId);
        }
        sql.replace(sql.length()-1,sql.length(),")");
	    super.dao.execute(sql.toString(),parameterList);
    }
	/**
	 * 通过盘口会员信息获取已存在盘口会员信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 已存在盘口会员信息
	 */
	public Map<String, Object> findByHmId(String handicapMemberId) throws SQLException {
		String sql = "select IBM_CLIENT_HM_ID_,CLIENT_ID_,CLIENT_CODE_,HANDICAP_CODE_ from ibm_client_hm where HANDICAP_MEMBER_ID_=? and STATE_=? limit 1";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 获取存在会员ID列表
	 *
	 * @param clientId     客户端主键
	 * @param handicapCode 盘口编码
	 * @return 存在会员ID列表
	 */
	public List<String> listExitsId(String clientId, String handicapCode) throws SQLException {
		String sql = "SELECT ich.EXIST_HM_ID_ FROM ibm_client_hm ich WHERE ich.CLIENT_ID_=? AND ich.HANDICAP_CODE_=? AND ich.STATE_=?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(clientId);
		parameterList.add(handicapCode);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findStringList("EXIST_HM_ID_", sql, parameterList);
	}

	/**
	 * 心跳检测登出
	 *
	 * @param exitsHmId 存在会员id
	 * @param nowTime   更新时间
	 * @return 盘口代理主键
	 */
	public String logout2Heartbeat(String exitsHmId, Date nowTime) throws SQLException {
		String sql = "select HANDICAP_MEMBER_ID_ from ibm_client_hm where EXIST_HM_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(exitsHmId);
		parameterList.add(IbmStateEnum.OPEN.name());
		String handicapMemberId = super.dao.findString("HANDICAP_MEMBER_ID_", sql, parameterList);
		if (ContainerTool.isEmpty(handicapMemberId)) {
			return null;
		}
		updateByHmId(handicapMemberId, nowTime, "心跳检测-中心端存在客户端不存在的数据");
		return handicapMemberId;
	}

	/**
	 * 根据存在会员ID获取盘口会员信息
	 *
	 * @param existHmId 存在会员ID
	 * @return 盘口会员信息
	 */
	public Map<String, Object> findHmInfo(String existHmId) throws SQLException {
		String sql = "select HANDICAP_MEMBER_ID_,HANDICAP_CODE_ from ibm_client_hm where "
				+ " EXIST_HM_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(existHmId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findMap(sql, parameterList);
	}

	/**
	 * 获取绑定信息
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @return 绑定信息
	 */
	public Map<String, Object> findBindInfo(String handicapMemberId) throws SQLException {
		String sql = "SELECT EXIST_HM_ID_,CLIENT_CODE_ FROM `ibm_client_hm` where HANDICAP_MEMBER_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findMap(sql, parameterList);
	}
	/**
	 * 获取盘口会员id
	 *
	 * @param existHmId 已存在盘口会员id
	 * @return
	 */
	public String findHmId(String existHmId) throws SQLException {
		String sql = "select HANDICAP_MEMBER_ID_ from ibm_client_hm where EXIST_HM_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(existHmId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("HANDICAP_MEMBER_ID_", sql, parameterList);
	}
	/**
	 * 获取会员ID列表
	 * @param clientId	中心端客户端主键
	 */
	public List<String> listHmIds(Object clientId) throws SQLException {
		String sql="select HANDICAP_MEMBER_ID_ from ibm_client_hm where CLIENT_ID_=? and STATE_=? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(clientId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findStringList("HANDICAP_MEMBER_ID_",sql,parameterList);
	}

    /**
     * 获取会员使用中的客户端ids
     * @return
     */
    public List<String> findUsingClientIds() throws SQLException {
        String sql="select CLIENT_ID_ from ibm_client_hm where STATE_=? GROUP BY CLIENT_ID_";
        List<Object> parameters=new ArrayList<>();
        parameters.add(IbmStateEnum.OPEN.name());
        return super.dao.findStringList("CLIENT_ID_",sql,parameters);
    }

    /**
     * 获取需要清除的盘口会员ids
     * @param expiredHmClientIds       客户端ids
     * @return
     */
    public List<String> findHmIdByClientIds(Set<Object> expiredHmClientIds) throws SQLException {
        StringBuilder sql=new StringBuilder();
        List<Object> parameters=new ArrayList<>();
        sql.append("select HANDICAP_MEMBER_ID_ from ibm_client_hm where STATE_=? and CLIENT_ID_ in(");
        parameters.add(IbmStateEnum.OPEN.name());
        for(Object clientId:expiredHmClientIds){
            sql.append("?,");
            parameters.add(clientId);
        }
        sql.replace(sql.length()-1,sql.length(),")");
        return super.dao.findStringList("HANDICAP_MEMBER_ID_",sql.toString(),parameters);
    }
    /**
     * 获取客户机会员列表
     * @param clientCode    客户端编码
     * @return
     */
    public List<String> findHmIds(String clientCode) throws SQLException {
        String sql="select HANDICAP_MEMBER_ID_ from ibm_client_hm where CLIENT_CODE_=? and STATE_=? ";
        List<Object> parameterList=new ArrayList<>(2);
        parameterList.add(clientCode);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findStringList("HANDICAP_MEMBER_ID_",sql,parameterList);
    }
    /**
     * 获取客户机会员列表
     * @param clientCode    客户端编码
     * @return
     */
    public  List<String> findHmIds(String clientCode,String handicapCode) throws SQLException {
        String sql="select HANDICAP_MEMBER_ID_ from ibm_client_hm where CLIENT_CODE_=? and HANDICAP_CODE_=? and STATE_=? ";
        List<Object> parameterList=new ArrayList<>(3);
        parameterList.add(clientCode);
        parameterList.add(handicapCode);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.findStringList("HANDICAP_MEMBER_ID_",sql,parameterList);
    }

    /**
     * 批量新增
     * @param hmInfos       会员信息
     * @param clientId      客户端id
     * @param clientCode    客户端编码
     */
    public List<String> save(JSONArray hmInfos, String clientId, String clientCode) throws SQLException {
        List<String> handicapMemberIds=new ArrayList<>();
        StringBuilder sql=new StringBuilder();
        Date nowTime=new Date();
        sql.append("insert into ibm_client_hm(IBM_CLIENT_HM_ID_,HANDICAP_MEMBER_ID_,EXIST_HM_ID_,CLIENT_ID_,CLIENT_CODE_,HANDICAP_CODE_,"
                + "CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) VALUES");
        List<Object> parameterList = new ArrayList<>();
        for (int i = 0; i < hmInfos.size(); i++) {
            sql.append("(?,?,?,?,?,?,?,?,?,?,?),");
            JSONObject resultObj = hmInfos.getJSONObject(i);
            String handicapMemberId = resultObj.getString("HANDICAP_MEMBER_ID_");
            parameterList.add(UUID.randomUUID().toString().replace("-", ""));
            parameterList.add(handicapMemberId);
            parameterList.add(resultObj.getString("EXIST_HM_ID_"));
            parameterList.add(clientId);
            parameterList.add(clientCode);
            parameterList.add(resultObj.getString("HANDICAP_CODE_"));
            parameterList.add(nowTime);
            parameterList.add(nowTime.getTime());
            parameterList.add(nowTime);
            parameterList.add(nowTime.getTime());
            parameterList.add(IbmStateEnum.OPEN.name());

            handicapMemberIds.add(handicapMemberId);
        }
        sql.deleteCharAt(sql.length() - 1);
        super.dao.execute(sql.toString(), parameterList);

        return handicapMemberIds;
    }

	/**
	 * 修改会员客户端信息
	 * @param clientHmInfo	会员客户端信息
	 * @param existHmId		已存在会员id
	 * @param clientId		客户端id
	 * @param clientCode		客户端编码
	 */
	public void update(Map<String, Object> clientHmInfo, String existHmId, String clientId, String clientCode) throws SQLException {
		String sql="update ibm_client_hm set EXIST_HM_ID_=?,CLIENT_ID_=?,CLIENT_CODE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=?"
				+ " where IBM_CLIENT_HM_ID_=?";
		List<Object> parameters=new ArrayList<>();
		parameters.add(existHmId);
		parameters.add(clientId);
		parameters.add(clientCode);
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(clientHmInfo.get("IBM_CLIENT_HM_ID_"));
		super.dao.execute(sql,parameters);
	}
}
