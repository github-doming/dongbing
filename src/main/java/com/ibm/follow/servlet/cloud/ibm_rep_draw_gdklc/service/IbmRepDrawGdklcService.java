package com.ibm.follow.servlet.cloud.ibm_rep_draw_gdklc.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_gdklc.entity.IbmRepDrawGdklc;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * IBM_结果数据_广东快乐十分 服务类
 *
 * @author Robot
 */
public class IbmRepDrawGdklcService extends BaseServiceProxy {

	/**
	 * 保存IBM_结果数据_广东快乐十分 对象数据
	 *
	 * @param entity IbmRepDrawGdklc对象数据
	 */
	public String save(IbmRepDrawGdklc entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_rep_draw_gdklc 的 IBM_REP_DRAW_GDKLC_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_rep_draw_gdklc set state_='DEL' where IBM_REP_DRAW_GDKLC_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_REP_DRAW_GDKLC_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibm_rep_draw_gdklc 的 IBM_REP_DRAW_GDKLC_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_rep_draw_gdklc set state_='DEL' where IBM_REP_DRAW_GDKLC_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibm_rep_draw_gdklc  的 IBM_REP_DRAW_GDKLC_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_rep_draw_gdklc where IBM_REP_DRAW_GDKLC_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_REP_DRAW_GDKLC_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_rep_draw_gdklc 的 IBM_REP_DRAW_GDKLC_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibm_rep_draw_gdklc where IBM_REP_DRAW_GDKLC_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmRepDrawGdklc实体信息
	 *
	 * @param entity IBM_结果数据_广东快乐十分 实体
	 */
	public void update(IbmRepDrawGdklc entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_rep_draw_gdklc表主键查找 IBM_结果数据_广东快乐十分 实体
	 *
	 * @param id ibm_rep_draw_gdklc 主键
	 * @return IBM_结果数据_广东快乐十分 实体
	 */
	public IbmRepDrawGdklc find(String id) throws Exception {
		return (IbmRepDrawGdklc) this.dao.find(IbmRepDrawGdklc.class, id);

	}

	/**
	 * 查询该期数是否存在数据
	 *
	 * @param period 期数
	 */
	public boolean isExist(String period, String type) throws SQLException {
		String sql = "SELECT DRAW_TIME_ FROM ibm_rep_draw_gdklc where PERIOD_ = ? and TYPE_ = ? and state_ = ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(period);
		parameters.add(type);
		parameters.add(IbmStateEnum.OPEN.name());
		return StringTool.notEmpty(super.dao.findString("DRAW_TIME_", sql, parameters));
	}
}
