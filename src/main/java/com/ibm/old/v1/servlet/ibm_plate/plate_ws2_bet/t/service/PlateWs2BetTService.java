package com.ibm.old.v1.servlet.ibm_plate.plate_ws2_bet.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.servlet.ibm_plate.plate_ws2_bet.t.entity.PlateWs2BetT;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class PlateWs2BetTService extends BaseService {



	/**
	 * 保存{ay_table_class}对象数据
	 * @param entity PlateWs2BetT对象数据
	 */
	public String save(PlateWs2BetT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除plate_ws2_bet的 PLATE_WS2_BET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update plate_ws2_bet set state_='DEL' where PLATE_WS2_BET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除PLATE_WS2_BET_ID_主键id数组的数据
	 * @param idArray 要删除plate_ws2_bet的 PLATE_WS2_BET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update plate_ws2_bet set state_='DEL' where PLATE_WS2_BET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除plate_ws2_bet的 PLATE_WS2_BET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from plate_ws2_bet where PLATE_WS2_BET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除PLATE_WS2_BET_ID_主键id数组的数据
	 * @param idArray 要删除plate_ws2_bet的 PLATE_WS2_BET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from plate_ws2_bet where PLATE_WS2_BET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新PlateWs2BetT实体信息
	 * @param entity PlateWs2BetT实体
	 */
	public void update(PlateWs2BetT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据plate_ws2_bet表主键查找PlateWs2BetT实体
	 * @param id plate_ws2_bet 主键
	 * @return PlateWs2BetT实体
	 */
	public PlateWs2BetT find(String id) throws Exception {
		return (PlateWs2BetT) this.dao.find(PlateWs2BetT. class,id);

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
		String sqlCount = "SELECT count(*) FROM plate_ws2_bet where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM plate_ws2_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM plate_ws2_bet  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页PlateWs2BetT数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页PlateWs2BetT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM plate_ws2_bet where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM plate_ws2_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM plate_ws2_bet  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(PlateWs2BetT. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM plate_ws2_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用PlateWs2BetT数据信息
	 * @return 可用<PlateWs2BetT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM plate_ws2_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(PlateWs2BetT. class,sql);
	}
}
