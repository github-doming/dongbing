package com.ibm.follow.servlet.cloud.ibm_handicap.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_handicap.entity.IbmHandicap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmHandicapService extends BaseServicePlus {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmHandicap对象数据
	 */
	public String save(IbmHandicap entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_handicap的 IBM_HANDICAP_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_handicap set state_='DEL' where IBM_HANDICAP_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_HANDICAP_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_handicap的 IBM_HANDICAP_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibm_handicap set state_='DEL' where IBM_HANDICAP_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_handicap的 IBM_HANDICAP_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_handicap where IBM_HANDICAP_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_HANDICAP_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_handicap的 IBM_HANDICAP_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_handicap where IBM_HANDICAP_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmHandicap实体信息
	 *
	 * @param entity IbmHandicap实体
	 */
	public void update(IbmHandicap entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_handicap表主键查找IbmHandicap实体
	 *
	 * @param id ibm_handicap 主键
	 * @return IbmHandicap实体
	 */
	public IbmHandicap find(String id) throws Exception {
		return (IbmHandicap) this.dao.find(IbmHandicap.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_handicap where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_handicap  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_handicap  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmHandicap数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmHandicap数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_handicap where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_handicap  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_handicap  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmHandicap.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_handicap  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmHandicap数据信息
	 *
	 * @return 可用<IbmHandicap>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_handicap  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmHandicap.class, sql);
	}
	/**
	 * 获取 盘口id为key，盘口code为value的集合
	 *
	 * @return 盘口id为key，盘口code为value
	 */
	public Map<String, String> mapIdCode() throws SQLException {
		String sql = "SELECT IBM_HANDICAP_ID_ as key_, HANDICAP_CODE_ as value_ FROM `ibm_handicap` where STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findKVMap(sql, parameterList);
	}
	/**
	 * 查找盘口id
	 *
	 * @param handicapCode     盘口code
	 * @param handicapCategory 盘口类别
	 * @return 盘口id
	 */
	public String findId(String handicapCode, String handicapCategory) throws SQLException {
		String sql = "SELECT IBM_HANDICAP_ID_ FROM `ibm_handicap` where HANDICAP_CODE_ = ? and HANDICAP_CATEGORY_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapCode);
		parameterList.add(handicapCategory);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("IBM_HANDICAP_ID_", sql, parameterList);
	}
	/**
	 * 查找盘口code
	 *
	 * @param handicapId 盘口id
	 * @return 盘口code
	 */
	public Map<String, Object> findCodeAndCategory(String handicapId) throws SQLException {
		String sql = "SELECT HANDICAP_CODE_,HANDICAP_CATEGORY_ FROM `ibm_handicap` where IBM_HANDICAP_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}
	/**
	 * 查找 盘口id为key，盘口类型为value的集合
	 *
	 * @return 盘口code
	 */
	public Map mapIdCategory() throws SQLException {
		String sql = "SELECT IBM_HANDICAP_ID_ as key_, HANDICAP_CATEGORY_ as value_ FROM `ibm_handicap` where STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findKVMap(sql, parameterList);
	}

	/**
	 * 通过盘口ids查询盘口
	 *
	 * @param handicapIds 盘口ids
	 * @return 盘口信息
	 */
	public List<Map<String, Object>> findHandicap(String handicapIds) throws SQLException {
		StringBuilder sql = new StringBuilder(
				"SELECT IBM_HANDICAP_ID_,HANDICAP_CODE_,HANDICAP_NAME_ from ibm_handicap where STATE_ = ? and IBM_HANDICAP_ID_ in (");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbmStateEnum.OPEN.name());
		for (String handicapId : handicapIds.split(",")) {
			sql.append("?,");
			parameterList.add(handicapId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.dao.findMapList(sql.toString(), parameterList);
	}
    /**
     * 通过盘口ids查询盘口
     *
     * @param handicapCodes 盘口codes
     * @param customerType  用户类型
     * @return 盘口信息
     */
    public List<Map<String, Object>> findHandicapByCode(String handicapCodes,IbmTypeEnum customerType) throws SQLException {
        StringBuilder sql = new StringBuilder(
                "SELECT IBM_HANDICAP_ID_,HANDICAP_CODE_,HANDICAP_NAME_ from ibm_handicap where STATE_ = ? and HANDICAP_CATEGORY_=? and IBM_HANDICAP_ID_ in (");
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(IbmStateEnum.OPEN.name());
        parameterList.add(customerType.name());
        for (String handicapCode : handicapCodes.split(",")) {
            sql.append("?,");
            parameterList.add(handicapCode);
        }
        sql.replace(sql.length() - 1, sql.length(), ")");
        return super.dao.findMapList(sql.toString(), parameterList);
    }

    /**
     * 获取盘口信息
     * @param usable   可用盘口codes
     * @param customerType  客户类型
     * @return
     */
    public List<Map<String, Object>> findHandicap(Set<String> usable, IbmTypeEnum customerType) throws SQLException {
        StringBuilder sql = new StringBuilder(
                "SELECT IBM_HANDICAP_ID_,HANDICAP_CODE_,HANDICAP_NAME_ from ibm_handicap where STATE_ = ? and HANDICAP_CATEGORY_=?"
                        + " and HANDICAP_CODE_ in (");
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(IbmStateEnum.OPEN.name());
        parameterList.add(customerType.name());
        for (String handicapCode : usable) {
            sql.append("?,");
            parameterList.add(handicapCode);
        }
        sql.replace(sql.length() - 1, sql.length(), ")");
        return super.dao.findMapList(sql.toString(), parameterList);
    }

	/**
	 * 获取开启的id列表
	 *
	 * @return 盘口id列表
	 */
	public List<String> listOpenId() throws SQLException {
		String sql = "SELECT IBM_HANDICAP_ID_ as key_ from ibm_handicap where STATE_ = ? and HANDICAP_CATEGORY_ = ? ";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(IbmTypeEnum.MEMBER.name());
		return super.findStringList(sql, parameterList);
	}

	/**
	 * 通过盘口ids查询盘口名字
	 *
	 * @param handicapIds 盘口ids
	 * @return 盘口信息
	 */
	public String findHandicapName(String handicapIds) throws SQLException {
		StringBuilder sql = new StringBuilder(
				"SELECT group_concat(HANDICAP_NAME_) handicapNames from ibm_handicap where STATE_ = ? and IBM_HANDICAP_ID_ in (");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbmStateEnum.OPEN.name());
		for (String handicapId : handicapIds.split(",")) {
			sql.append("?,");
			parameterList.add(handicapId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.dao.findString("handicapNames",sql.toString(),parameterList);
	}


	/**
	 * 获取用户没有的盘口信息
	 *
	 * @param handicapIds 盘口ids
	 * @param category  盘口主键
	 * @return 盘口信息列表
	 */
	public List<Map<String, Object>> listNoHandicap(String handicapIds, IbmTypeEnum category) throws SQLException {
		StringBuilder sql = new StringBuilder(  "SELECT IBM_HANDICAP_ID_,HANDICAP_NAME_,HANDICAP_CODE_,HANDICAP_CATEGORY_ FROM ibm_handicap "
				+ " WHERE HANDICAP_CATEGORY_ = ?  AND STATE_ != ? AND IBM_HANDICAP_ID_ NOT IN ( ");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(category.name());
		parameterList.add(IbmStateEnum.DEL.name());

		for (String handicapId : handicapIds.split(",")) {
			sql.append("?,");
			parameterList.add(handicapId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.findMapList(sql.toString(), parameterList);
	}

	public String findTypeById(String handicapId) throws SQLException {
		String sql = "SELECT HANDICAP_TYPE_ FROM `ibm_handicap` WHERE IBM_HANDICAP_ID_ = ? AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("HANDICAP_TYPE_",sql,parameterList);
	}

	public void updateByHandicapIdAndType(String handicapId,String userName,String desc,IbmTypeEnum type, Date nowtime) throws SQLException {
		String sql = "UPDATE `ibm_handicap` SET `HANDICAP_TYPE_`=?,`UPDATE_USER_`=?,`UPDATE_TIME_`=?,`UPDATE_TIME_LONG_`=?,`DESC_`=? WHERE `IBM_HANDICAP_ID_`=? AND STATE_!=? ";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(type.name());
		parameterList.add(userName);
		parameterList.add(nowtime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(desc);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.DEL.name());
		super.dao.execute(sql,parameterList);
	}

    /**
     * 获取盘口状态
     * @param handicapId    盘口id
     * @return
     */
    public String findState(String handicapId) throws SQLException {
        String sql="select STATE_ from ibm_handicap where IBM_HANDICAP_ID_=? and STATE_!=?";
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(handicapId);
        parameterList.add(IbmStateEnum.DEL.name());
        return super.dao.findString("STATE_",sql,parameterList);
    }

    /**
     * 获取免费可用盘口
     * @param category
     * @return
     */
    public List<Map<String, Object>> listUsableInfo(String category) throws SQLException {
        String sql="select HANDICAP_NAME_,HANDICAP_CODE_, from ibm_handicap where HANDICAP_CATEGORY_=? and HANDICAP_TYPE_=? and STATE_=?";
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(category);
        parameterList.add(IbmTypeEnum.FREE.name());
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findMapList(sql,parameterList);
    }

    /**
     * 获取指定类型所有盘口codes
     * @param type  客户类型
     * @return
     */
    public List<String> findHandicapCode(IbmTypeEnum type) throws SQLException {
        String sql="select HANDICAP_CODE_ from ibm_handicap where HANDICAP_CATEGORY_=? and STATE_=?";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(type.name());
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findStringList("HANDICAP_CODE_",sql,parameterList);
    }
    /**
     * 获取指定类型所有盘口codes
     * @return
     */
    public List<String> findHandicapCode() throws SQLException {
        String sql="select HANDICAP_CODE_ from ibm_handicap where STATE_=?";
        List<Object> parameterList = new ArrayList<>(1);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findStringList("HANDICAP_CODE_",sql,parameterList);
    }

    /**
     * 获取分页信息
     * @param handicapName      盘口名称
     * @param handicapCategory  盘口类别
     * @param handicapType      盘口类型
     * @param pageIndex         页数
     * @param pageSize          条数
     * @return
     */
    public PageCoreBean<Map<String, Object>> listShow(String handicapName, String handicapCategory, String handicapType, Integer pageIndex, Integer pageSize) throws SQLException {
        String sqlCount="select count(IBM_HANDICAP_ID_) from ibm_handicap where STATE_!=? ";
        String sql="SELECT IBM_HANDICAP_ID_,HANDICAP_NAME_,HANDICAP_CODE_,HANDICAP_CATEGORY_,HANDICAP_TYPE_,HANDICAP_WORTH_T_,ieh.CAPACITY_,ieh.CAPACITY_MAX_,ih.STATE_"
                + " from ibm_handicap ih LEFT JOIN ibm_exp_handicap ieh ON ih.IBM_HANDICAP_ID_=ieh.HANDICAP_ID_"
                + " where ih.STATE_!=? ";
        ArrayList<Object> parameterList = new ArrayList<>(4);
        parameterList.add(IbmStateEnum.DEL.name());
        String sqlPlus = "";
        if(StringTool.notEmpty(handicapName)){
            sqlPlus+=" and HANDICAP_NAME_ like ?";
            parameterList.add("%"+handicapName+"%");
        }
        if(StringTool.notEmpty(handicapCategory)){
            sqlPlus+=" and HANDICAP_CATEGORY_=?";
            parameterList.add(handicapCategory);
        }
        if(StringTool.notEmpty(handicapType)){
            sqlPlus+=" and HANDICAP_TYPE_=?";
            parameterList.add(handicapType);
        }
        sqlPlus += " order by SN_";
        sqlCount += sqlPlus;
        sql += sqlPlus;
        return dao.page(sql, parameterList, pageIndex, pageSize, sqlCount, parameterList);
    }

    /**
     * 获取盘口信息
     * @param handicapId        盘口id
     * @return
     */
    public Map<String, Object> findInfo(String handicapId) throws SQLException {
        String sql="select IBM_HANDICAP_ID_,HANDICAP_NAME_,HANDICAP_CODE_,HANDICAP_CATEGORY_,HANDICAP_TYPE_,HANDICAP_WORTH_T_,"
                + "STATE_ from ibm_handicap where IBM_HANDICAP_ID_=?";
        ArrayList<Object> parameterList = new ArrayList<>(2);
        parameterList.add(handicapId);
        return super.dao.findMap(sql,parameterList);
    }
	/**
	 * 根据类别获取盘口信息
	 * @param category 盘口类别
	 * @return  HANDICAP_NAME_,HANDICAP_CODE_
	 */
	public List<Map<String, Object>> listHandicap(IbmTypeEnum category) throws SQLException {
		String sql="select HANDICAP_NAME_,HANDICAP_CODE_ from ibm_handicap where HANDICAP_CATEGORY_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(category.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMapList(sql,parameterList);

	}
}
