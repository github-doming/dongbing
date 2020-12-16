package com.ibm.old.v1.cloud.ibm_handicap.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_handicap.t.entity.IbmHandicapT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmHandicapTService extends BaseService {



	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmHandicapT对象数据
	 */
	public String save(IbmHandicapT entity) throws Exception {
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
	public void delAll(String[] idArray,String className) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibm_handicap set state_='DEL',DESC_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where IBM_HANDICAP_ID_ in(" + stringBuilder.toString() + ")";
			List<Object> parameters = new ArrayList<>();
			parameters.add(className+"删除ibm_handicap的 IBM_HANDICAP_ID_数组");
			parameters.add(new Date());
			parameters.add(System.currentTimeMillis());
			dao.execute(sql, parameters);
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
	 * 更新IbmHandicapT实体信息
	 *
	 * @param entity IbmHandicapT实体
	 */
	public void update(IbmHandicapT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_handicap表主键查找IbmHandicapT实体
	 *
	 * @param id ibm_handicap 主键
	 * @return IbmHandicapT实体
	 */
	public IbmHandicapT find(String id) throws Exception {
		return (IbmHandicapT) this.dao.find(IbmHandicapT.class, id);

	}

	/**
	 * 获取分页Map数据
	 * @param handicapName   盘口名
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页Map数据
	 */
	public PageCoreBean find(String handicapName,Integer pageIndex, Integer pageSize)
			throws Exception {
		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append("SELECT count(*) FROM ibm_handicap where state_!='DEL' ");
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ibm_handicap where state_!='DEL' ");
		ArrayList<Object> parameters = null;
		ArrayList<Object> parametersCount =null;

		if(ContainerTool.notEmpty(handicapName)){
			parameters = new ArrayList<>();
			parametersCount = new ArrayList<>();
			parameters.add("%"+handicapName+"%");
			parametersCount.add("%"+handicapName+"%");
			sql.append("and HANDICAP_NAME_ like ? ");
			sqlCount.append("and HANDICAP_NAME_ like ? ");
		}
		sql.append("order by UPDATE_TIME_ desc");
		return dao.page(sql.toString(), parameters, pageIndex, pageSize, sqlCount.toString(),parametersCount);
	}

	/**
	 * 获取分页IbmHandicapT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmHandicapT数据
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
		return dao.page(IbmHandicapT.class, sql, null, pageIndex, pageSize, sqlCount);
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
	 * 按照更新顺序查询所有可用IbmHandicapT数据信息
	 *
	 * @return 可用<IbmHandicapT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_handicap  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmHandicapT.class, sql);
	}

	/**
	 * 根据盘口code获取盘口对象
	 *
	 * @param code 盘口code
	 * @return 盘口对象
	 */
	public IbmHandicapT findByCode(String code) throws Exception {
		String sql = "SELECT * FROM ibm_handicap  where HANDICAP_CODE_ = ? and state_ !='DEL'";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(code);
		Object obj = super.dao.findObject(IbmHandicapT.class, sql, parameterList);
		return obj == null ? null : (IbmHandicapT) obj;
	}

	/**
	 * 获取开启的盘口id数组
	 * @return 开启的盘口id数组
	 */
	public List<String> listOpenId() throws SQLException {
		String sql = "SELECT IBM_HANDICAP_ID_ FROM ibm_handicap  where state_ = 'OPEN' order by UPDATE_TIME_LONG_ desc";
		return super.dao.findStringList("IBM_HANDICAP_ID_",sql, null);
	}

	/**
	 * 根据code获取Id
	 * @param code 盘口code
	 * @return 盘口Id
	 */
	public String findIdByCode(String code) throws SQLException {
		String sql = "SELECT IBM_HANDICAP_ID_ FROM ibm_handicap  where HANDICAP_CODE_ = ? and state_ !='DEL'";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(code);
		return super.dao.findString("IBM_HANDICAP_ID_",sql,parameterList);
	}
	
	/**
	 * 
	 * @Description: 通过盘口ID集合查询盘口
	 *
	 * 参数说明 
	 * @param handicapIds 盘口ID集合
	 * @return 盘口集合
	 */
	public List<Map<String, Object>> findHandicap(String handicapIds) throws Exception {
		StringBuilder sql = new StringBuilder("SELECT * from ibm_handicap where STATE_ != ? and IBM_HANDICAP_ID_ in (");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbmStateEnum.DEL.name());
		for (String handicapId : handicapIds.split(",")) {
			sql.append("?,");
			parameterList.add(handicapId);
		}
		sql.replace(sql.length()-1, sql.length(), ")");
		return super.dao.findMapList(sql.toString(), parameterList);
	}
	
	/**
	 * 
	 * @Description: 查询免费盘口ID
	 *
	 * @return 盘口ID集合
	 */
	public List<String> findFreeHandicap() throws SQLException {
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmTypeEnum.FREE.name());
		return super.dao.findStringList("IBM_HANDICAP_ID_",
				"select IBM_HANDICAP_ID_ from ibm_handicap where STATE_!=? AND HANDICAP_WORTH_T_ = ? ", parameterList);
	}

    public Map<String, Object> findNameAndCode(String handicapId) throws SQLException {
		String sql="select HANDICAP_NAME_,HANDICAP_CODE_ from ibm_handicap where IBM_HANDICAP_ID_=? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
    }
	/**
	 * 查询符合校验时间盘口CODE
	 * @return 符合校验时间盘口CODE
	 */
	public List<String> findAllCode() throws SQLException {
		String sql="SELECT ish.HANDICAP_CODE_ FROM ibm_sys_handicap ish "
				+ " WHERE(ish.LAST_CHECK_TIME_ + ish.HANDICAP_DETECTION_TIME_ * 1000) < unix_timestamp(now()) * 1000 AND ish.STATE_=?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findStringList("HANDICAP_CODE_",sql,parameterList);
	}

	/**
	 * 根据盘口code获取盘口对象
	 *
	 * @param code 盘口code
	 * @return 盘口对象
	 */
	public Map<String, Object> findHandicapInfoByCode(String code) throws Exception {
		String sql = "SELECT IBM_HANDICAP_ID_,HANDICAP_NAME_ FROM ibm_handicap  where HANDICAP_CODE_ = ? and state_ !='DEL'";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(code);
		return super.dao.findMap(sql,parameterList);
	}
	/**
	 * 根据盘口id获取盘口code
	 * @param handicapId 盘口id
	 * @return 盘口code
	 */
	public String findCode(String handicapId) throws SQLException {
		String sql="select HANDICAP_CODE_ from ibm_handicap where IBM_HANDICAP_ID_=? and state_!='DEL' ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(handicapId);
		return super.dao.findString("HANDICAP_CODE_", sql, parameterList);
	}
}
