package com.ibm.follow.servlet.cloud.ibm_manage_point.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_manage_point.entity.IbmManagePoint;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * IBM_用户点数 的服务类
 *
 * @author Robot
 */
public class IbmManagePointService extends BaseServicePlus {

	/**
	 * 保存IBM_用户点数对象数据
	 *
	 * @param entity IbmManagePoint对象数据
	 */
	public String save(IbmManagePoint entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_manage_point 的 IBM_MANAGE_POINT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_manage_point set state_='DEL' where IBM_MANAGE_POINT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_MANAGE_POINT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibm_manage_point 的 IBM_MANAGE_POINT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibm_manage_point set state_='DEL' where IBM_MANAGE_POINT_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibm_manage_point  的 IBM_MANAGE_POINT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_manage_point where IBM_MANAGE_POINT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_MANAGE_POINT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除IBM_用户点数的 IBM_MANAGE_POINT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_manage_point where IBM_MANAGE_POINT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmManagePoint实体信息
	 *
	 * @param entity IBM_用户点数实体
	 */
	public void update(IbmManagePoint entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_manage_point表主键查找IbmManagePoint实体
	 *
	 * @param id ibm_manage_point 主键
	 * @return IBM_用户点数实体
	 */
	public IbmManagePoint find(String id) throws Exception {
		return (IbmManagePoint) this.dao.find(IbmManagePoint.class, id);

	}

	/**
	 * 根据userAppId删除数据
	 *
	 * @param appUserId
	 * @throws SQLException
	 */
	public void delByAppUserId(String appUserId) throws SQLException {
		String sql = "delete from ibm_manage_point where APP_USER_ID_= ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(appUserId);
		dao.execute(sql, parameterList);
	}

	/**
	 * 根据条件查询数据
	 *
	 * @return 数据
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findUserPoint() throws SQLException {
		String sql = "select IBM_MANAGE_POINT_ID_,"
				+ "(SELECT APP_USER_NAME_ FROM app_user WHERE APP_USER_ID_ = managePoint.APP_USER_ID_) as APP_USER_NAME_,"
				+ "TOTAL_POINT_T_,USEABLE_POINT_T_,FROZEN_POINT_T_ from ibm_manage_point as managePoint where STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbmStateEnum.OPEN.name());
		return dao.findMapList(sql, parameterList);
	}

	/**
	 * 修改点数表中的 总点数，可用点数，冻结点数
	 *
	 * @param managePointId 主键ID
	 * @param totalPoint    总点数
	 * @param useablePoint  可用点数
	 * @param frozenPoint   冻结点数
	 * @throws SQLException
	 */
	public void updatePoint(String managePointId, long totalPoint, long useablePoint, long frozenPoint)
			throws SQLException {
		String sql = "update ibm_manage_point set TOTAL_POINT_T_ = ? , USEABLE_POINT_T_ = ? , FROZEN_POINT_T_ = ? where IBM_MANAGE_POINT_ID_ = ? ";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(totalPoint);
		parameterList.add(useablePoint);
		parameterList.add(frozenPoint);
		parameterList.add(managePointId);
		dao.execute(sql, parameterList);
	}

	/**
	 * 修改点数表中的 总点数，可用点数
	 *
	 * @param appUserId 用户ID
	 * @param useablePoint  可用点数
	 * @throws SQLException
	 */
	public void updatePointByAppUserId(String appUserId,String repPointId, long useablePoint)
			throws SQLException {
		String sql = "update ibm_manage_point set TOTAL_POINT_T_ = TOTAL_POINT_T_+ ? , USEABLE_POINT_T_ = ?,REP_POINT_ID_=?  where APP_USER_ID_ = ? ";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(useablePoint);
		parameterList.add(useablePoint);
		parameterList.add(repPointId);
		parameterList.add(appUserId);
		dao.execute(sql, parameterList);
	}

	/**
	 * 获取可用积分
	 *
	 * @param userId 用户id
	 * @return 可用积分
	 */
	public double getUseablePoint(String userId) throws SQLException {
		String sql = "SELECT USEABLE_POINT_T_  FROM `ibm_manage_point` where APP_USER_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		Map<String, Object> map = super.findMap(sql, parameterList);
		return NumberTool.getDouble(map.get("USEABLE_POINT_T_"));
	}

    /**
     * 根据用户id获取实体对象
     * @param appUserId     用户id
     * @return
     */
    public IbmManagePoint findByUserId(String appUserId) throws Exception {
        String sql = "SELECT *  FROM `ibm_manage_point` where APP_USER_ID_ = ? and STATE_ != ?";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(appUserId);
        parameterList.add(IbmStateEnum.DEL.name());
        return (IbmManagePoint) super.dao.findObject(IbmManagePoint.class,sql,parameterList);
    }
}
