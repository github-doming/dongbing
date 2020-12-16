package com.ibm.follow.servlet.cloud.ibm_rep_grab_pk10.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_pk10.entity.IbmRepGrabPk10;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmRepGrabPk10Service extends BaseService {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 * @param entity IbmRepGrabPk10对象数据
	 */
	public String save(IbmRepGrabPk10 entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_rep_grab_pk10的 IBM_REP_GRAB_PK10_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_rep_grab_pk10 set state_='DEL' where IBM_REP_GRAB_PK10_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_REP_GRAB_PK10_ID_主键id数组的数据
	 * @param idArray 要删除ibm_rep_grab_pk10的 IBM_REP_GRAB_PK10_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_rep_grab_pk10 set state_='DEL' where IBM_REP_GRAB_PK10_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除ibm_rep_grab_pk10的 IBM_REP_GRAB_PK10_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_rep_grab_pk10 where IBM_REP_GRAB_PK10_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_REP_GRAB_PK10_ID_主键id数组的数据
	 * @param idArray 要删除ibm_rep_grab_pk10的 IBM_REP_GRAB_PK10_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_rep_grab_pk10 where IBM_REP_GRAB_PK10_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmRepGrabPk10实体信息
	 * @param entity IbmRepGrabPk10实体
	 */
	public void update(IbmRepGrabPk10 entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_rep_grab_pk10表主键查找IbmRepGrabPk10实体
	 * @param id ibm_rep_grab_pk10 主键
	 * @return IbmRepGrabPk10实体
	 */
	public IbmRepGrabPk10 find(String id) throws Exception {
		return (IbmRepGrabPk10) this.dao.find(IbmRepGrabPk10. class,id);

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
		String sqlCount = "SELECT count(*) FROM ibm_rep_grab_pk10 where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_rep_grab_pk10  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_rep_grab_pk10  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmRepGrabPk10数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页IbmRepGrabPk10数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_rep_grab_pk10 where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_rep_grab_pk10  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_rep_grab_pk10  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmRepGrabPk10. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_rep_grab_pk10  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmRepGrabPk10数据信息
	 * @return 可用<IbmRepGrabPk10>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_rep_grab_pk10  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmRepGrabPk10. class,sql);
	}

	/**
	 *  查询该期数是否存在数据
	 * @param period 期数
	 * @return 存在true
	 */
	public boolean isExist(String period,String type) throws SQLException {
		String sql = "SELECT DRAW_TIME_ FROM ibm_rep_grab_pk10  where PERIOD_ = ? and TYPE_=? and state_ = ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(period);
        parameters.add(type);
		parameters.add(IbmStateEnum.OPEN.name());
		return ContainerTool.notEmpty(super.dao.findMap(sql,parameters));
	}
}
